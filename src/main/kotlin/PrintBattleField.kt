private const val SIZE = FIELD_SIZE

fun BattleField.printToConsole() {
    repeat(SIZE) { z ->
        printHeader(z)
        repeat(SIZE) { y ->
            repeat(SIZE) { x ->
                when (this[x, y, z]) {
                    ObjectType.EMPTY -> printSymbol(x, y, z, "-")
                    ObjectType.SINGLE_DECK -> printSymbol(x, y, z, "1")
                    ObjectType.DOUBLE_DECK -> printSymbol(x, y, z, "2")
                    ObjectType.THREE_DECK -> printSymbol(x, y, z, "3")
                    ObjectType.FOUR_DECK -> printSymbol(x, y, z, "4")
                    ObjectType.MINE -> printSymbol(x, y, z, "*")
                    ObjectType.HELP -> printSymbol(x, y, z, "H")
                }
            }
            println()
        }
    }

}

private fun printSymbol(x: Int, y: Int, z: Int, symbol: String) = print(" $symbol ")

private fun printHeader(z: Int) {
    var layer = z
    println()
    println("Слой №${++layer}")
}