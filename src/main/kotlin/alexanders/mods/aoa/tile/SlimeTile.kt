package alexanders.mods.aoa.tile

import alexanders.mods.aoa.AllOfAlex.createRes
import alexanders.mods.aoa.init.Resources
import de.ellpeck.rockbottom.api.data.set.ModBasedDataSet
import de.ellpeck.rockbottom.api.entity.Entity
import de.ellpeck.rockbottom.api.item.ToolType
import de.ellpeck.rockbottom.api.tile.TileBasic
import de.ellpeck.rockbottom.api.tile.state.TileState
import de.ellpeck.rockbottom.api.util.BoundBox
import de.ellpeck.rockbottom.api.world.IWorld
import de.ellpeck.rockbottom.api.world.layer.TileLayer


class SlimeTile : TileBasic(Resources.slimeResource) {
    val LAST_TIME_BOUNCED = createRes("lastTimeBounced")

    init {
        this.setHardness(.5f)
        this.addEffectiveTool(ToolType.PICKAXE, 0)
        this.setForceDrop()
    }

    override fun onCollideWithEntity(world: IWorld, x: Int, y: Int, layer: TileLayer, state: TileState, entityBox: BoundBox, entityBoxMotion: BoundBox, tileBoxes: List<BoundBox>,
                                     entity: Entity) {
        if (entity.additionalData == null)
            entity.additionalData = ModBasedDataSet()
        val lastTimeBounced = entity.additionalData.getLong(LAST_TIME_BOUNCED)
        val now = System.currentTimeMillis()
        if (lastTimeBounced + 100 < now) {
            if (entity.motionY < 0) {
                entity.motionY = -entity.motionY
                entity.isFalling = false

                entity.applyMotion()
            }
            entity.additionalData.addLong(LAST_TIME_BOUNCED, now)
        }
    }

}
