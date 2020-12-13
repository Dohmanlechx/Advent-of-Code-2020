import FileReader.inputFromDay
import kotlin.math.abs

object Day12RainRisk {
    data class Ship(
        var x: Int = 0,
        var y: Int = 0,
        var facing: Char = 'E',
        var waypoint: Waypoint = Waypoint(x = 10, y = 1)
    )

    data class Waypoint(
        var x: Int = 0,
        var y: Int = 0
    )

    private lateinit var ship: Ship

    fun solve(isFirstPart: Boolean): Int {
        ship = Ship()
        inputFromDay(12).forEach { handleAction(it, isShip = isFirstPart) }
        return abs(ship.x) + abs(ship.y)
    }

    private fun handleAction(action: String, isShip: Boolean, ownValue: Int? = null) {
        val value = ownValue ?: action.filter(Char::isDigit).toInt()

        if (isShip) {
            when (action[0]) {
                'N' -> ship.y += value
                'S' -> ship.y -= value
                'E' -> ship.x += value
                'W' -> ship.x -= value
                'L' -> turnShip(isLeft = true, value)
                'R' -> turnShip(isLeft = false, value)
                'F' -> handleAction(ship.facing.toString(), isShip, value)
            }
        } else {
            when (action[0]) {
                'N' -> ship.waypoint.y += value
                'S' -> ship.waypoint.y -= value
                'E' -> ship.waypoint.x += value
                'W' -> ship.waypoint.x -= value
                'L' -> moveWaypoint(isLeft = true, value)
                'R' -> moveWaypoint(isLeft = false, value)
                'F' -> moveShipToWaypoint(value)
            }
        }
    }

    private fun moveShipToWaypoint(value: Int) {
        ship.x += ship.waypoint.x * value
        ship.y += ship.waypoint.y * value
    }

    private fun turnShip(isLeft: Boolean, angle: Int) {
        ship.facing =
            when (ship.facing) {
                'N' -> if (isLeft) 'W' else 'E'
                'W' -> if (isLeft) 'S' else 'N'
                'S' -> if (isLeft) 'E' else 'W'
                else -> if (isLeft) 'N' else 'S'
            }

        (angle - 90).let { if (it > 0) turnShip(isLeft, it) }
    }

    private fun moveWaypoint(isLeft: Boolean, angle: Int) {
        when (angle) {
            90 -> {
                if (isLeft) {
                    val oldX = ship.waypoint.x
                    ship.waypoint.x = -ship.waypoint.y
                    ship.waypoint.y = oldX
                } else {
                    val oldX = ship.waypoint.x
                    ship.waypoint.x = ship.waypoint.y
                    ship.waypoint.y = -oldX
                }
            }
            180 -> {
                if (isLeft) {
                    ship.waypoint.x = -ship.waypoint.x
                    ship.waypoint.y = -ship.waypoint.y
                } else {
                    ship.waypoint.x = -ship.waypoint.x
                    ship.waypoint.y = -ship.waypoint.y
                }
            }
            270 -> {
                if (isLeft) {
                    val oldX = ship.waypoint.x
                    ship.waypoint.x = ship.waypoint.y
                    ship.waypoint.y = -oldX
                } else {
                    val oldX = ship.waypoint.x
                    ship.waypoint.x = -ship.waypoint.y
                    ship.waypoint.y = oldX
                }
            }
        }

    }
}