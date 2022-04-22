import kotlin.random.Random
import kotlin.reflect.KProperty

//import java.util.*
//import kotlin.random.Random
//import kotlin.reflect.KProperty
//import MineObject as MineObject
//
//// счетчики объектов
//const val MINES_COUNT = 1
//const val HELP_COUNT = 1
//const val SHIPS_LEN_1_COUNT = 0
//const val SHIPS_LEN_2_COUNT = 0
//const val SHIPS_LEN_3_COUNT = 0
//const val SHIPS_LEN_4_COUNT = 1
//
//class BattleFieldDelegate(val gameObjects: MutableList<Objects>) {
//    var battleField: BattleField? = null
//
//    // возвращает true если есть соседи
//    fun checkNeighbors(d: Int, y: Int, x: Int): Boolean {
//        var sum = 0
//        for (dd in (if (d > 1) d - 1 else d)..(if (d < DEPTH - 1) d + 1 else d)) {
//            for (dy in (if (y > 1) y - 1 else y)..(if (y < HEIGHT - 1) y + 1 else y)) {
//                for (dx in (if (x > 1) x - 1 else x)..(if (x < WIDTH - 1) x + 1 else x)) {
//                    if (battleField!![dx, dy, dd] != ObjectType.EMPTY) sum++
//                }
//            }
//        }
//        return sum != 0
//    }
//
//    // генерация рандомной позиции
//    fun generateRandomPosition(): Triple<Int, Int, Int> {
//        val x = Random.nextInt(0, WIDTH)
//        val y = Random.nextInt(0, HEIGHT)
//        val z = Random.nextInt(0, DEPTH)
//        return Triple(x, y, z)
//    }
//
//    // генерация мины
//    fun propagateMines() {
//        var mine = 0
//        while (mine < MINES_COUNT) {
//            val (x, y, z) = generateRandomPosition()
//            if (!checkNeighbors(x, y, z)) {
//                gameObjects.add(MineObject(x, y, z))
//                battleField!![x, y, z] = ObjectType.MINE
//                mine++
//            }
//        }
//    }
//
//    // генерация помощи
//    fun propagateHelp() {
//        var help = 0
//        while (help < HELP_COUNT) {
//            val (x, y, z) = generateRandomPosition()
//            if (!checkNeighbors(x, y, z)) {
//                gameObjects.add(HelpObject(x, y, z))
//                battleField!![x, y, z] = ObjectType.HELP
//                help++
//            }
//        }
//    }
//
//    // добавление новой позиции
//    private fun nextPosition(x: Int, y: Int, z: Int, direction: Direction): Triple<Int, Int, Int>? {
//        return when (direction) {
//            Direction.LEFT -> if (x > 0) Triple(x - 1, y, z) else null
//            Direction.RIGHT -> if (x < WIDTH - 1) Triple(x + 1, y, z) else null
//            Direction.UP -> if (y > 0) Triple(x, y - 1, z) else null
//            Direction.DOWN -> if (y < HEIGHT - 1) Triple(x, y + 1, z) else null
//            Direction.TO_THE_SURFACE -> if (z > 0) Triple(x, y, z - 1) else null
//            Direction.DEEP_INTO -> if (z < DEPTH - 1) Triple(x, y, z + 1) else null
//        }
//    }
//
//    // добавление корабля на карту
//    fun propagateShipToMap(x: Int, y: Int, z: Int, len: Int, direction: Direction, type: ObjectType) {
//        var cx = x
//        var cy = y
//        var cz = z
//        for (i in 1..len) {
//            battleField!![cx, cy, cz] = type
//            val l = nextPosition(x, y, z, direction)
//            l?.let {
//                cx = l.first
//                cy = l.second
//                cz = l.third
//            }
//        }
//    }
//
//    // можно ли добавлять еще корабли?
//    private fun canPropagateShip(x: Int, y: Int, z: Int, len: Int, direction: Direction): Boolean {
//        // текущее положение точки заполнения
//        var cx = x
//        var cy = y
//        var cz = z
//        for (i in 1..len) {
//            if (checkNeighbors(cx, cy, cz)) {
//                return false
//            }
//            val l = nextPosition(cx, cy, cz, direction) ?: return false // попали в стену
//            // продолжаем заполнять
//            cx = l.first
//            cy = l.second
//            cz = l.third
//        }
//        return true
//    }
//
//    fun placeShip(len: Int, type: ObjectType) {
//        while (true) {
//            val direction = Direction.values()[Random.nextInt(Direction.values().size)]
//            val (x, y, z) = generateRandomPosition()
//            if (canPropagateShip(x, y, z, len, direction)) {
//                // сохранить корабль и вывести точку на поле
//                propagateShipToMap(x, y, z, len, direction, type)
//                gameObjects.add(StaticShipObject(x, y, z, len, direction))
//                break
//            }
//        }
//    }
//
//    // генерация кораблей
//    fun propagateShips() {
//        for (i in 1..SHIPS_LEN_4_COUNT) placeShip(4, ObjectType.STATIC_SHIP)
//        for (i in 1..SHIPS_LEN_3_COUNT) placeShip(3, ObjectType.STATIC_SHIP)
//        for (i in 1..SHIPS_LEN_2_COUNT) placeShip(2, ObjectType.STATIC_SHIP)
//        for (i in 1..SHIPS_LEN_1_COUNT) placeShip(1, ObjectType.STATIC_SHIP)
//    }
//
//    operator fun getValue(thisRef: Any?, property: KProperty<*>): BattleField {
//        if (battleField == null) {
//            battleField = BattleField()
//            println("Mines")
//            propagateMines()
//            println("Helps")
//            propagateHelp()
//            println("Ships")
//            propagateShips()
//            println("Battle field is filled")
//        }
//        return battleField!!
//    }
//}

class Delegate(private val items: List<Object>) {
    private lateinit var bf: BattleField

    private fun createStaticShips() {
        items.filterIsInstance<StaticShip>().forEach {
            while (true) {
                val ship = it.copy(
                    head = generateRandomPosition(),
                    direction = Direction.values()[Random.nextInt(Direction.values().size - 1)])

                if (canPlaceShip(ship)) {
                    placeShip(ship)
                    break
                }
            }
        }
    }


    private fun canPlaceShip(ship: StaticShip): Boolean {
        var currentX = ship.head.x
        var currentY = ship.head.y
        var currentZ = ship.head.z
        for (i in 1..ship.size) {
            if(!bf.isAvailableObject(Point(currentX, currentY, currentZ))) return false
            val next = nextPosition(Point(currentX, currentY, currentZ), ship.direction) ?: return false
            currentX = next.x
            currentY = next.y
            currentZ = next.z
        }
        return true
    }

    private fun placeShip(ship: StaticShip) {
        var currentX = ship.head.x
        var currentY = ship.head.y
        var currentZ = ship.head.z
        for (i in 1..ship.size) {
            bf[currentX, currentY, currentZ] = when (ship.size) {
                1 -> ObjectType.SINGLE_DECK
                2 -> ObjectType.DOUBLE_DECK
                3 -> ObjectType.THREE_DECK
                4 -> ObjectType.FOUR_DECK
                else -> ObjectType.EMPTY
            }
            val next = nextPosition(Point(currentX, currentY, currentZ), ship.direction)
            next?.let {
                currentX = next.x
                currentY = next.y
                currentZ = next.z
            }
        }
    }

    private fun createMineObjects() {
        val mineItems = items.filterIsInstance<MineObject>()
        var current = 0
        while (current < mineItems.size) {
            val point = generateRandomPosition()
            if (bf.isAvailableObject(point)) {
                bf[point.x, point.y, point.z] = ObjectType.MINE
                current++
            }
        }
    }

    private fun createHelpObjects() {
        val helpItem = items.filterIsInstance<HelpObject>()
        var current = 0
        while (current < helpItem.size) {
            val point = generateRandomPosition()
            if (bf.isAvailableObject(point)) {
                bf[point.x, point.y, point.z] = ObjectType.HELP
                current++
            }
        }
    }


    operator  fun getValue(thisRef: Any?, property: KProperty<*>): BattleField {
        bf = BattleField()

        createMineObjects()
        createHelpObjects()
        createStaticShips()

        return bf
    }
}



// Генерация рандомного поля и заполнения след позиции
fun generateRandomPosition(sizeUntil: Int = FIELD_SIZE): Point {
    val x = Random.nextInt(0, sizeUntil)
    val y = Random.nextInt(0, sizeUntil)
    val z = Random.nextInt(0, sizeUntil)
    return Point(x, y, z)
}

fun nextPosition(point: Point, direction: Direction): Point? {
    val (x, y, z) = point
    return when (direction) {
        Direction.LEFT -> if (x > 0) Point(x - 1, y, z) else null
        Direction.RIGHT -> if (x < FIELD_SIZE - 1) Point(x + 1, y, z) else null
        Direction.BACK -> if (y > 0) Point(x, y - 1, z) else null
        Direction.FORWARD -> if (y < FIELD_SIZE - 1) Point(x, y + 1, z) else null
        Direction.UP -> if (z > 0) Point(x,y,z - 1) else null
        Direction.DOWN -> if (z < FIELD_SIZE - 1) Point (x, y, z + 1) else null
        Direction.EMPTY -> throw IllegalArgumentException("Empty")
    }
}