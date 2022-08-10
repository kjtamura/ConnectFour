const val MIN = 5
const val MAX = 9
class ConnectFour {
    private var player1 = ""
    private var player2 = ""
    private val board = mutableListOf<MutableList<Char>>()

    private fun buildBoard() {
        var row: Int
        var col: Int
        do {
            println("Set the board dimensions (Rows x Columns)")
            println("Press Enter for default (6 x 7)")
            val inp = readln().replace("\\s".toRegex(), "").lowercase()
            try {
                if (inp.isEmpty()) {
                    row = 6
                    col = 7
                } else {
                    val (a, b) = inp.split('x')
                    row = a.toInt()
                    col = b.toInt()
                }
            } catch (e: Exception) {
                println("Invalid input")
                row = 0
                col = 0
            }
        } while(!validBoardSize(row, col))
        repeat(row) {
            board.add(" ".repeat(col).toMutableList())
        }
    }

    private fun validBoardSize(row: Int, col: Int): Boolean {
        return when {
            row == 0 && col == 0 -> return false
            row < MIN || row > MAX -> {
                println("Board rows should be from $MIN to $MAX")
                false
            }
            col < MIN || col > MAX -> {
                println("Board columns should be from $MIN to $MAX")
                false
            }
            else -> true
        }
    }

    fun start() {
        println("Connect Four")
        println("First player's name:")
        player1 = readln()
        println("Second player's name:")
        player2 = readln()
        buildBoard()
        println("$player1 VS $player2")
        println("${board.size} X ${board.first().size} board")
    }
}