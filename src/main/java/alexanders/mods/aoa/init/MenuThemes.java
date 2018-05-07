package alexanders.mods.aoa.init;

import alexanders.mods.aoa.render.LoadedMenuTheme;
import alexanders.mods.aoa.render.RandomMenuTheme;
import alexanders.mods.aoa.render.SpiralMenuTheme;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import static de.ellpeck.rockbottom.api.RockBottomAPI.MAIN_MENU_THEMES;
import static de.ellpeck.rockbottom.api.RockBottomAPI.TILE_STATE_REGISTRY;

public class MenuThemes {
    public static void init() {
        MAIN_MENU_THEMES.register(MAIN_MENU_THEMES.getNextFreeId(), new RandomMenuTheme());
        MAIN_MENU_THEMES.register(MAIN_MENU_THEMES.getNextFreeId(), new SpiralMenuTheme());
        Path themeDir = Paths.get("rockbottom", "themes");
        if (Files.isDirectory(themeDir)) {
            try {
                Files.list(themeDir).filter(path -> !path.getFileName().startsWith(".")).forEach(path -> {
                    File file = path.toFile();
                    if (file.isFile()) {
                        try {
                            char[][] charArray = new char[16][16];
                            DataInputStream dataInputStream = new DataInputStream(new FileInputStream(file));
                            for (int x = 0; x < 16; x++) {
                                for (int y = 0; y < 16; y++) {
                                    char c = dataInputStream.readChar();
                                    if (c == '\n') throw new IOException("Invalid file size!");
                                    charArray[x][y] = c;
                                }
                            }
                            char c3 = dataInputStream.readChar();
                            if (c3 != '\n') throw new IOException("Invalid file format!");
                            HashMap<Character, ResourceName> translationMap = new HashMap<>();
                            translationMap.put('-', null);
                            StringBuilder builder = null;
                            char c = '\n';
                            try {
                                while (true) {
                                    c = dataInputStream.readChar();
                                    if (dataInputStream.readChar() != '=') {
                                        throw new IOException("Invalid file format!");
                                    }
                                    builder = new StringBuilder();
                                    char c2;
                                    while ((c2 = dataInputStream.readChar()) != '\n') builder.append(c2);
                                    translationMap.put(c, new ResourceName(builder.toString()));
                                    builder = null;
                                }
                            } catch (EOFException e) {
                                if (builder != null) translationMap.put(c, new ResourceName(builder.toString()));
                            }
                            TileState[][] map = new TileState[16][16];
                            for (int x = 0; x < 16; x++) {
                                for (int y = 0; y < 16; y++) {
                                    map[x][y] = TILE_STATE_REGISTRY.get(translationMap.get(charArray[x][y]));
                                }
                            }
                            MAIN_MENU_THEMES.register(MAIN_MENU_THEMES.getNextFreeId(), new LoadedMenuTheme(0, map));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
