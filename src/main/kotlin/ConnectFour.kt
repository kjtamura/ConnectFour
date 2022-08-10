const val MIN = 5
const val MAX = 9

enum class PIECE (val piece: Char){
    CIRCLE('o'),
    STAR('*')
}
class ConnectFour {
    private var player1 = ""
    private var player2 = ""
    private val board = mutableListOf<MutableList<Char>>()
    private var numOfRows = 0
    private var numOfCols = 0
    private var turn = 0

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

        numOfRows = row
        numOfCols = col
        repeat(row) {
            val list = mutableListOf<Char>()
            repeat(col * 2 + 1) {
                list.add(if (it % 2 == 0) '|' else ' ')
            }
            board.add(list)
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

    private fun printBoard() {
        println(" ${(1..numOfCols).joinToString(" ")}")
        repeat(numOfRows) {
            println(board[it].joinToString(""))
        }
        println("=".repeat(board.first().size))
    }

    private fun fillBoard(shape: Int, move: Int): Boolean {
        if(board.first()[move * 2 + 1] != ' ') {
            println("Column ${move + 1} is full")
            return false
        }
        for (i in board.lastIndex downTo 0) {
            if (board[i][move * 2 + 1] == ' ') {
                board[i][move * 2 + 1] = if (shape > 0) PIECE.CIRCLE.piece else PIECE.STAR.piece
                break
            }
        }
        return true
    }

    private fun validInp(inp: String): Boolean {
        try {
            val col = inp.toInt()
            if (col < 1 || col > numOfCols) {
                throw Exception("The column number is out of range (1 - $numOfCols)")
            }
        } catch (e: NumberFormatException) {
            println("Incorrect column number")
            return false
        } catch (e: Exception){
            println(e.message)
            return false
        }
        return true
    }
    private fun playGame() {
        var shape = 1
        do {
            when (turn%2) {
                0 -> println("$player1's turn:")
                1 -> println("$player2's turn:")
            }
            val action = readln()
            if(action != "end" && validInp(action) && fillBoard(shape, action.toInt() - 1)) {
                shape *= -1
                turn++
                printBoard()
            }

        } while(action != "end")
    }
    fun start() {
        println("Connect Four")
        println("First player's name:")
        player1 = readln()
        println("Second player's name:")
        player2 = readln()
        buildBoard()
        println("$player1 VS $player2")
        println("$numOfRows X $numOfCols board")
        printBoard()
        playGame()
        println("Game over!")
    }
}