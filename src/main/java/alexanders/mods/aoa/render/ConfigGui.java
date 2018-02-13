package alexanders.mods.aoa.render;

import alexanders.mods.aoa.HiddenSettings;
import alexanders.mods.aoa.JukeboxRunner;
import alexanders.mods.aoa.Util;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.data.settings.ModSettings;
import de.ellpeck.rockbottom.api.gui.Gui;
import de.ellpeck.rockbottom.api.gui.component.ComponentButton;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Paths;
import java.security.SecureRandom;

import static alexanders.mods.aoa.init.Resources.resourceConfigGui;

public class ConfigGui extends Gui {
    private static final SpotifyApi spotifyApi = JukeboxRunner.spotify;
    private static final AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi.authorizationCodeUri()
            .state(Util.getSecureRandomState())
            .scope("user-modify-playback-state,user-read-playback-state")
            .show_dialog(true)
            .build();
    
    public ConfigGui(Gui gui) {
        super(100, 20, gui);
    }

    @Override
    public void init(IGameInstance game) {
        super.init(game);
        components.add(new ComponentButton(this, 0, 0, 100, 20, this::onLogin, "Log in on Spotify"));
    }
    
    private boolean onLogin() {
        try {
            ServerSocket server = new ServerSocket(14389); // completely arbitrarily chose portnumber
            Desktop.getDesktop().browse(authorizationCodeUriRequest.getUri());
            Socket socket = server.accept();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String getLine = reader.readLine();
            String propertiesString = getLine.substring(6);
            String[] properties = propertiesString.split("&");
            String authCode = null;
            String state = null;
            if(properties.length != 2)
                throw new RuntimeException("Invalid response from spotify");
            String[] property = properties[0].split("=");
            if(property.length != 2)
                throw new RuntimeException("Invalid response from spotify");
            if(property[0].equals("code"))
                authCode = property[1];
            else if(property[0].equals("state"))
                state = property[1];
            property = properties[1].split("=");
            if(property.length != 2)
                throw new RuntimeException("Invalid response from spotify");
            if(property[0].equals("code"))
                authCode = property[1];
            else if(property[0].equals("state"))
                state = property[1];
            if(state == null || authCode == null)
                throw new RuntimeException("Invalid response from spotify");

            writer.write("HTTP/1.1 200 OK\n" +
                    "Server: AllOfAlex Simple builtin server\n" +
                    "Content-Language: en\n" +
                    "Content-Type: text/html; charset=iso-8859-1\n" +
                    "Connection: close\n" +
                    "Content-Type: text/html\n" +
                    "Content-Length: 82\n");
            writer.write("<html>\n" +
                    "<head>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "<h1>Please return to RockBottom</h1>\n" +
                    "</body>\n" +
                    "</html>\n");
            writer.flush();
            reader.close();
            writer.close();
            socket.close();
            AuthorizationCodeCredentials credentials = spotifyApi.authorizationCode(authCode).build().execute();
            spotifyApi.setAccessToken(credentials.getAccessToken());
            spotifyApi.setRefreshToken(credentials.getRefreshToken());
            File file = Paths.get(".", "rockbottom", "aoa.dat").toFile();
            if(!file.exists())
                if(!file.createNewFile())
                    throw new IOException("Couldn't create token storage file");
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file));
            fileWriter.write(credentials.getRefreshToken());
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } catch (SpotifyWebApiException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public IResourceName getName() {
        return resourceConfigGui;
    }
}
