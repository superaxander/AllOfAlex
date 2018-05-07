package alexanders.mods.aoa

import de.ellpeck.rockbottom.api.construction.resource.ResUseInfo
import java.util.*

val PLAYER_UUID = AllOfAlex.createRes("playerUUID")
val COOLDOWN = AllOfAlex.createRes("cooldown")

fun propertiesOf(vararg pairs: Pair<String, String>): Properties {
    val p = Properties()
    for ((first, second) in pairs)
        p.setProperty(first, second)
    return p
}

infix fun Int.of(name: String) = ResUseInfo(name, this)
