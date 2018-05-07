package alexanders.mods.aoa.tile.entity;

import alexanders.mods.aoa.JukeboxRunner;
import alexanders.mods.aoa.net.JukeboxStartPacket;
import alexanders.mods.aoa.net.JukeboxStopPacket;
import alexanders.mods.aoa.net.JukeboxSyncPacket;
import alexanders.mods.aoa.net.URLUpdatePacket;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.io.IOException;
import java.util.Objects;

import static de.ellpeck.rockbottom.api.RockBottomAPI.getGame;
import static de.ellpeck.rockbottom.api.RockBottomAPI.getNet;

public class JukeboxTileEntity extends TileEntity {
    public String url = null;
    public boolean isPlaying = false;
    private String lastUrl = null;
    private String lastLastUrl = null;
    private long startTime = 0;
    private long endTime = 0;
    private long lastSync = 0;
    private long now = 0;

    public JukeboxTileEntity(IWorld world, int x, int y, TileLayer layer) {
        super(world, x, y, layer);
    }

    @Override
    public void update(IGameInstance game) {
        super.update(game);
        if (!getNet().isClient()) {
            if (isPlaying) {
                if (!Objects.equals(url, lastUrl)) {
                    stopPlayback();
                    lastUrl = url;
                    startPlayback();
                    isPlaying = true;
                }
                if (url == null) stopPlayback();

                if (lastSync + 5000 < (now = System.currentTimeMillis())) {
                    lastSync = now;
                    syncPlayback();
                }
                if (now > endTime) {
                    stopPlayback();
                    isPlaying = false;
                }
            } else {
                if (url != null) {
                    startPlayback();
                    lastUrl = url;
                    isPlaying = true;
                }
            }
        } else {
            if (!Objects.equals(url, lastUrl)) {
                lastUrl = url;
                getNet().sendToServer(new URLUpdatePacket(layer, x, y, url));
            }
        }
    }

    @Override
    public void save(DataSet set, boolean forSync) {
        super.save(set, forSync);
        //if(url)
        //    set.addString("url", url);
    }

    @Override
    public void load(DataSet set, boolean forSync) {
        super.load(set, forSync);
    }

    @Override
    public boolean doesTick() {
        return true;
    }

    private void syncPlayback() {
        JukeboxSyncPacket packet = new JukeboxSyncPacket(url, now - startTime);
        System.out.println(endTime - now);
        IGameInstance game = getGame();
        if (getNet().isActive()) getNet().sendToAllPlayersWithLoadedPos(world, packet, x, y);
        if (!game.isDedicatedServer()) packet.handle(game, null);
    }

    private long getPlayTime() {
        if (JukeboxRunner.spotify.getAccessToken() == null) throw new RuntimeException("Jukeboxes are disabled on a server without spotify connection");
        try {
            long duration = JukeboxRunner.spotify.getTrack(url.substring(14)).build().execute().getDurationMs();
            startTime = lastSync = System.currentTimeMillis();
            System.out.println(duration);
            endTime = startTime + duration;
            return duration;
        } catch (IOException | SpotifyWebApiException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private void startPlayback() {
        JukeboxStartPacket packet = new JukeboxStartPacket(url);
        IGameInstance game = getGame();
        if (getNet().isActive()) getNet().sendToAllPlayersWithLoadedPos(world, packet, x, y);
        if (!game.isDedicatedServer()) packet.handle(game, null);
        getPlayTime();
    }

    private void stopPlayback() {
        JukeboxStopPacket packet = new JukeboxStopPacket(lastUrl);
        IGameInstance game = getGame();
        if (getNet().isActive()) getNet().sendToAllPlayersWithLoadedPos(world, packet, x, y);
        if (!game.isDedicatedServer()) packet.handle(game, null);
        startTime = lastSync = endTime = 0;
    }
}
