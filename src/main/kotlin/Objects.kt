/////////////////// Игровые объекты
interface Action {
    fun touch()
}

abstract class Object(
    open val size: Int = 1,
    open val direction: Direction,
    private var found: Boolean = false,
) : Action {
    override fun touch() {
        found = true
    }
}

data class HelpObject(
    override val direction: Direction = Direction.EMPTY,
) : Object(direction = direction)

data class MineObject(
    override val direction: Direction = Direction.EMPTY,
) : Object(direction = direction)

data class StaticShip(
    override val size: Int,
    override val direction: Direction = Direction.EMPTY,
    val head: Point = Point(Int.MIN_VALUE, Int.MIN_VALUE, Int.MIN_VALUE),
) : Object(direction = direction)

data class MovingShip(
    override val size: Int,
    override val direction: Direction = Direction.EMPTY,
    val head: Point = Point(Int.MIN_VALUE, Int.MIN_VALUE, Int.MIN_VALUE),
) : Object(direction = direction)
