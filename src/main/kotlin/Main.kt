fun main() {
    val items = mutableListOf<Object>()
    if (IS_NEW_GAME) {
        repeat(COUNT_OF_MINE_ITEM) { items.add(MineObject()) }
        repeat(COUNT_OF_HELP_ITEM) { items.add(HelpObject()) }
        repeat(COUNT_OF_SINGLE_DECK) { items.add(StaticShip(size = 1)) }
        repeat(COUNT_OF_DOUBLE_DECK) { items.add(StaticShip(size = 2)) }
        repeat(COUNT_OF_THREE_DECK) { items.add(StaticShip(size = 3)) }
        repeat(COUNT_OF_FOUR_DECK) { items.add(StaticShip(size = 4)) }
    }
    val battleField by Delegate(items)
    battleField.printToConsole()
}