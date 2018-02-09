package alexanders.mods.aoa.net;

import alexanders.mods.aoa.DataRecipe;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.construction.resource.ItemUseInfo;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.net.NetUtil;
import de.ellpeck.rockbottom.api.net.packet.IPacket;
import de.ellpeck.rockbottom.api.world.IWorld;
import io.netty.buffer.ByteBuf;
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
    public void toBuffer(ByteBuf buf) throws IOException {
        NetUtil.writeStringToBuffer(recipe.getName().toString(), buf);
        boolean hasData = item.hasAdditionalData();
        NetUtil.writeStringToBuffer(item.getItem().getName().toString(), buf);
        buf.writeInt(item.getMeta());
        buf.writeBoolean(hasData);
        if (hasData) NetUtil.writeSetToBuffer(item.getAdditionalData(), buf);
        buf.writeLong(uuid.getMostSignificantBits());
        buf.writeLong(uuid.getLeastSignificantBits());
    }

    @Override
    public void fromBuffer(ByteBuf buf) throws IOException {
        RockBottomAPI.ALL_CONSTRUCTION_RECIPES.get(RockBottomAPI.createRes(NetUtil.readStringFromBuffer(buf)));
        item = new ItemInstance(RockBottomAPI.ITEM_REGISTRY.get(RockBottomAPI.createRes(NetUtil.readStringFromBuffer(buf))), 1, buf.readInt());
        if (buf.readBoolean()) {
            NetUtil.readSetFromBuffer(item.getOrCreateAdditionalData(), buf);
        }
        uuid = new UUID(buf.readLong(), buf.readLong());
    }

    @Override
    public void handle(IGameInstance game, ChannelHandlerContext context) {
        IWorld world = game.getWorld();
        AbstractEntityPlayer player = world.getPlayer(uuid);
        ItemInstance out = recipe.getOutputs().get(0).copy();
        out.setAdditionalData(item.getAdditionalData());
        RockBottomAPI.getApiHandler().construct(world, player.x, player.y, player.getInv(), recipe, 1, Collections.singletonList(new ItemUseInfo(item)), itemInstances -> Collections.singletonList(out));
    }
}
