package alexanders.mods.aoa;

import alexanders.mods.aoa.init.Keys;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import de.ellpeck.rockbottom.api.util.Util;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Stack;

public class JukeboxRunner implements Runnable {
    private static final Stack<String> queue = new Stack<>();
    private static final ArrayList<String> urls = new ArrayList<>();
    public static SpotifyApi spotify = new SpotifyApi.Builder()
            .setClientId(HiddenSettings.clientID)
            .setClientSecret(HiddenSettings.clientSecret)
            .setRedirectUri(HiddenSettings.redirectURI)
            .build();
    private static URL PLAYBACK_URL;
    private static URL PAUSE_URL;
    private static String SEEK_STRING = "https://api.spotify.com/v1/me/player/seek";
    private static Thread thread = null;
    private static String playing = null;
    private static long nextTokenRefresh = 0;

    static {
        try {
            PLAYBACK_URL = new URL("https://api.spotify.com/v1/me/player/play");
            PAUSE_URL = new URL("https://api.spotify.com/v1/me/player/pause");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static void start(String url) {
        synchronized (queue) {
            queue.add(url);
        }
        if (thread == null || !thread.isAlive()) {
            if (spotify.getAccessToken() == null) {
                File file = Paths.get(".", "rockbottom", "aoa.dat").toFile();
                if (file.exists()) {
                    try {
                        BufferedReader reader = new BufferedReader(new FileReader(file));
                        String refreshToken = reader.readLine();
                        spotify.setRefreshToken(refreshToken);
                        refreshToken();
                    } catch (IOException | SpotifyWebApiException e) {
                        e.printStackTrace();
                    }
                }
            }
            thread = new Thread(new JukeboxRunner(), "Jukebox Runner");
            thread.setDaemon(true);
            thread.start();
        }
    }

    public static void sync(String url, long playTime) {
        if(playing == null) {
            start(url);
        }
        if (url.equals(playing)) {
            try {
                long progress = spotify.getInformationAboutUsersCurrentPlayback().build().execute().getProgress_ms().longValue();
                if (progress + 5000 < playTime || progress - 5000 > playTime)
                    sendPutRequest(new URL(SEEK_STRING+"?position_ms="+String.valueOf(playTime)), "");
                    //spotify.seekToPositionInCurrentlyPlayingTrack((int) playTime).build().execute();
            } catch (IOException | SpotifyWebApiException|IllegalStateException e) {
                e.printStackTrace();
            }
        }
    }

    public static void stop(String url) {
        if (url.equals(playing))
            playing = null;
        synchronized (urls) {
            urls.remove(url);
        }
    }

    public static void refreshToken() throws IOException, SpotifyWebApiException {
        AuthorizationCodeCredentials credentials = spotify.authorizationCodeRefresh().build().execute();
        spotify.setAccessToken(credentials.getAccessToken());
        spotify.setRefreshToken(credentials.getRefreshToken());
        nextTokenRefresh = System.currentTimeMillis() + credentials.getExpiresIn() * 1000;
    }

    @Override
    public void run() {
        boolean wasActuallyPlaying = false;
        while (true) {
            if (nextTokenRefresh < System.currentTimeMillis()) {
                try {
                    refreshToken();
                } catch (IOException | SpotifyWebApiException e) {
                    e.printStackTrace();
                }
            }
            if (!queue.empty()) {
                String url = queue.pop();
                urls.add(url);
                if (playing == null) {
                    playing = url;
                    wasActuallyPlaying = false;
                }
            }
            if (Keys.KEY_CYCLE_JUKEBOX.isPressed()) {
                synchronized (urls) {
                    int i = urls.indexOf(playing);
                    if (!urls.isEmpty())
                        playing = urls.get((i + 1) % urls.size());
                }
            }
            if (playing == null && wasActuallyPlaying) {
                wasActuallyPlaying = false;
                if (spotify.getAccessToken() != null) {
                    try {
                        if (spotify.getInformationAboutUsersCurrentPlayback().build().execute().getIs_playing())
                            sendPutRequest(PAUSE_URL, "");
                    } catch (IOException | SpotifyWebApiException e) {
                        e.printStackTrace();
                    }
                }
            } else if (playing != null && !wasActuallyPlaying) {
                wasActuallyPlaying = true;
                if (spotify.getAccessToken() != null) {
                    try {
                        JsonObject all = new JsonObject();
                        JsonArray a = new JsonArray();
                        a.add(playing);
                        JsonObject p = new JsonObject();
                        p.add("position", new JsonPrimitive(0));
                        all.add("uris", a);
                        all.add("offset", p);
                        if (spotify.getInformationAboutUsersCurrentPlayback().build().execute().getIs_playing())
                            sendPutRequest(PAUSE_URL, "");
                        sendPutRequest(PLAYBACK_URL, Util.GSON.toJson(all));
                        //spotify.startResumeUsersPlayback().uris(a).offset(p).build().execute(); // TODO: check if album or playlist and start playing at same position
                    } catch (IOException | SpotifyWebApiException e) {
                        e.printStackTrace();
                        playing = null;
                        wasActuallyPlaying = false;
                    }
                }
            }
            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void sendPutRequest(URL url, String content) throws IOException {
        HttpsURLConnection httpCon = (HttpsURLConnection) url.openConnection();
        httpCon.setDoOutput(true);
        httpCon.setRequestMethod("PUT");
        httpCon.setRequestProperty("Accept", "application/json");
        httpCon.setRequestProperty("Content-Type", "application/json");
        httpCon.setRequestProperty("Authorization", "Bearer " + spotify.getAccessToken());
        OutputStreamWriter out = new OutputStreamWriter(
                httpCon.getOutputStream());
        out.write(content);
        out.close();
        InputStream stream = httpCon.getInputStream();
        AllOfAlex.instance.logger.config("Got response code: " + httpCon.getResponseCode());
        stream.close();
    }
}
