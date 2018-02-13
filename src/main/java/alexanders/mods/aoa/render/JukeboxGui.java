package alexanders.mods.aoa.render;

import alexanders.mods.aoa.tile.entity.JukeboxTileEntity;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.gui.Gui;
import de.ellpeck.rockbottom.api.gui.component.ComponentButton;
import de.ellpeck.rockbottom.api.gui.component.ComponentInputField;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

import static alexanders.mods.aoa.init.Resources.resourceJukeboxGui;

public class JukeboxGui extends Gui {
    private final JukeboxTileEntity te;
    private String url = "";

    public JukeboxGui(JukeboxTileEntity te) {
        super(185, 80);
        this.te = te;
    }

    @Override
    public void init(IGameInstance game) {
        super.init(game);
        components.add(new ComponentInputField(this, 0, 0, 100, 20, true, true, true, 999, false, this::onStringChange));
        components.add(new ComponentButton(this, 105, 0, 80, 20, this::onButtonPress, "Apply"));
    }

    private void onStringChange(String url) {
        this.url = url;
    }

    private boolean onButtonPress() {
        String u = url.trim();
        te.url = u.isEmpty() ? null : u;
        return true;
    }

    @Override
    public IResourceName getName() {
        return resourceJukeboxGui;
    }
}
