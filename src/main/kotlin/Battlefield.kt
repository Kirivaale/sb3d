import kotlin.IllegalArgumentException
import kotlin.random.Random

interface Field {
    operator fun get(x: Int, y: Int, z: Int): ObjectType
    operator fun set(x: Int, y: Int, z: Int, objectType: ObjectType)
    fun getFieldState(): List<IndexedValue<ObjectType>>
    fun getNeighbors(p: Point): MutableList<Point>
}

class BattleField(private val fieldSize: Int = FIELD_SIZE): Field {
    private val field = Array(fieldSize * fieldSize * fieldSize) { ObjectType.EMPTY }

    override operator fun get(x: Int, y: Int, z: Int): ObjectType {
        if (checkBoundaries(Point(x, y, z))) {
            return field[calculatePosition(Point(x, y, z))]
        } else throw IllegalArgumentException("get incorrect position: $x,$y,$z")
    }

    override operator fun set(x: Int, y: Int, z: Int, objectType: ObjectType) {
        if (checkBoundaries(Point(x, y, z)) && isAvailablePoint(Point(x, y, z))) {
            field[calculatePosition(Point(x, y, z))] = objectType
        } else throw IllegalArgumentException("set incorrect position: $x,$y,$z")
    }

    // Получить соседей
    override fun getNeighbors(p: Point): MutableList<Point> {
        val result = mutableListOf<Point>()
        if(isAvailablePoint(p)) {
            listOf(p.x - 1, p.x, p.x + 1).forEach { first ->
                listOf(p.y - 1, p.y, p.y + 1).forEach { second ->
                    listOf(p.z - 1, p.z, p.z + 1).forEach { third ->
                        if (first >= 0 && second >= 0 && third >= 0) {
                            result.add(Point(first, second, third))
                        }
                    }
                }
            }
            result.remove(Point(p.x, p.y, p.z))
        } else throw IllegalArgumentException("Not available")
        return result
    }

    // Получить состояние поля
    override fun getFieldState(): List<IndexedValue<ObjectType>> {
        return field
            .withIndex()
            .filter { it.value != ObjectType.EMPTY }
    }

    private fun calculatePosition(p:Point) = ((p.x * fieldSize * fieldSize) + (p.y * fieldSize) + p.z)

    // Проверка границ
    private fun checkBoundaries(p: Point): Boolean {
        return p.x in 0 until fieldSize && p.y in 0 until fieldSize && p.z in 0 until fieldSize
    }

    private fun isAvailablePoint(p: Point): Boolean = get(p.x, p.y, p.z) == ObjectType.EMPTY

    fun isAvailableObject(p: Point): Boolean {
        return try {
            val neighbors = getNeighbors(Point(p.x, p.y, p.z))
            neighbors.add(Point(p.x, p.y, p.z))
            neighbors
                .map { point -> isAvailablePoint(Point(point.x, point.y, point.z)) }
                .all { it }
        } catch (e: IllegalArgumentException) {
            println(e.message)
            false
        }
    }
}