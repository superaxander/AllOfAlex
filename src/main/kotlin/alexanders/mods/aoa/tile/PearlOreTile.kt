package alexanders.mods.aoa.tile

import alexanders.mods.aoa.init.Items
import alexanders.mods.aoa.init.Resources
import de.ellpeck.rockbottom.api.entity.Entity
import de.ellpeck.rockbottom.api.item.ItemInstance
import de.ellpeck.rockbottom.api.item.ToolType
import de.ellpeck.rockbottom.api.tile.TileBasic
import de.ellpeck.rockbottom.api.world.IWorld
import de.ellpeck.rockbottom.api.world.layer.TileLayer


class PearlOreTile : TileBasic(Resources.pearlOreResource) {
    init {
        this.setHardness(5f)
        addEffectiveTool(ToolType.PICKAXE, 1)
    }

    override fun getDrops(world: IWorld, x: Int, y: Int, layer: TileLayer, destroyer: Entity?) = mutableListOf(ItemInstance(Items.pearlItem))
}
