package alexanders.mods.aoa.net;

import alexanders.mods.aoa.DataRecipe;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.construction.resource.ItemUseInfo;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.net.NetUtil;
import de.ellpeck.rockbottom.api.net.packet.IPacket;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;
import java.util.Collections;
import java.util.UUID;

public class DataCraftingPacket implements IPacket {
    private DataRecipe recipe;
    private ItemInstance item;
    private UUID uuid;

    public DataCraftingPacket() {
    }

    public DataCraftingPacket(DataRecipe recipe, ItemInstance item, UUID uuid) {
        this.recipe = recipe;
        this.item = item;
        this.uuid = uuid;
    }

    @Override
    public void toBuffer(ByteBuf buf) {
        NetUtil.writeStringToBuffer(recipe.getName().toString(), buf);
        boolean hasData = item.hasAdditionalData();
        NetUtil.writeStringToBuffer(item.getItem().getName().toString(), buf);
        buf.writeInt(item.getMeta());
        buf.writeBoolean(hasData);
        DataSet temp = new DataSet(); //TODO: Change this back once method is available
        temp.addModBasedDataSet("t", item.getAdditionalData());
        if (hasData) NetUtil.writeSetToBuffer(temp, buf);
        buf.writeLong(uuid.getMostSignificantBits());
        buf.writeLong(uuid.getLeastSignificantBits());
    }

    @Override
    public void fromBuffer(ByteBuf buf) {
        RockBottomAPI.ALL_CONSTRUCTION_RECIPES.get(new ResourceName(NetUtil.readStringFromBuffer(buf)));
        item = new ItemInstance(RockBottomAPI.ITEM_REGISTRY.get(new ResourceName(NetUtil.readStringFromBuffer(buf))), 1, buf.readInt());
        if (buf.readBoolean()) {
            DataSet temp = new DataSet();
            NetUtil.readSetFromBuffer(temp, buf);
            item.setAdditionalData(temp.getModBasedDataSet("t"));
        }
        uuid = new UUID(buf.readLong(), buf.readLong());
    }

    @Override
    public void handle(IGameInstance game, ChannelHandlerContext context) {
        if (!(item == null || uuid == null || recipe == null)) {
            IWorld world = game.getWorld();
            AbstractEntityPlayer player = world.getPlayer(uuid);
            ItemInstance out = recipe.getOutputs().get(0).copy();
            out.setAdditionalData(item.getAdditionalData());
            RockBottomAPI.getApiHandler().construct(world, player.x, player.y, player.getInv(), recipe, 1, Collections.singletonList(new ItemUseInfo(item)),
                                                    itemInstances -> Collections.singletonList(out));
        }
    }
}
