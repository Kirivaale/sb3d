enum class ObjectType {
    EMPTY,
    HELP,
    MINE,
    SINGLE_DECK,
    DOUBLE_DECK,
    THREE_DECK,
    FOUR_DECK,
}

enum class Direction(move: Point) {
    LEFT(Point(-1, 0, 0)),
    RIGHT(Point(1,0,0)),
    BACK(Point(0, -1,0)),
    FORWARD(Point(0,1,0)),
    DOWN(Point(0,0,-1)),
    UP(Point(0,0,1)),
    EMPTY(Point(Int.MIN_VALUE, Int.MIN_VALUE, Int.MIN_VALUE))
}