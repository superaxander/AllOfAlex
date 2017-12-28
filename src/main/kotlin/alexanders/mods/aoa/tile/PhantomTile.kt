package alexanders.mods.aoa.tile

import alexanders.mods.aoa.init.Resources.phantomTileResource
import de.ellpeck.rockbottom.api.tile.TileBasic
import de.ellpeck.rockbottom.api.tile.entity.TileEntity
import de.ellpeck.rockbottom.api.world.IWorld
import de.ellpeck.rockbottom.api.world.layer.TileLayer


class PhantomTile : TileBasic(phantomTileResource) {
    override fun provideTileEntity(world: IWorld, x: Int, y: Int, layer: TileLayer): TileEntity {
        val e = world.getTileEntity(x, y)
        if (e != null)
            return e
        else
            return PhantomTileEntity(world, x, y, layer)
    }

    override fun canProvideTileEntity() = true
}
