const val MIN = 5
const val MAX = 9

enum class PIECE (val piece: Char){
    CIRCLE('o'),
    STAR('*')
}

data class Player (val name: String, val piece: Char)
class ConnectFour {
    private val board = mutableListOf<MutableList<Char>>()
    private var numOfRows = 0
    private var numOfCols = 0
    private var turn = 0
    private var curRow = 0
    private var curCol = 0

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
            repeat(col) {
                list.add(' ')
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
            print("|")
            for (i in board.first().indices) {
                print("${board[it][i]}|")
            }
            println()
        }
        println("=".repeat(numOfCols * 2 + 1))
    }

    private fun fillBoard(player: Player, move: Int): Boolean {
        if(board.first()[move] != ' ') {
            println("Column ${move + 1} is full")
            return false
        }
        for (i in board.lastIndex downTo 0) {
            if (board[i][move] == ' ') {
                board[i][move] = player.piece
                curRow = i
                curCol = move
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

    fun checkDown(row: Int, player: Player): Int {
        if(row >= numOfRows || board[row][curCol] != player.piece) {
            return 0
        }
        return checkDown(row + 1, player) + 1
    }

    fun checkLeft(col: Int, player: Player): Int {
        if(col < 0 || board[curRow][col] != player.piece) {
            return 0
        }
        return checkLeft(col - 1, player) + 1
    }

    fun checkRight(col: Int, player: Player): Int {
        if(col >= numOfCols || board[curRow][col] != player.piece) {
            return 0
        }
        return checkRight(col + 1, player) + 1
    }

    fun checkDiagUp(row: Int, col: Int, player: Player): Int {
        if(row < 0 || col < 0 || board[row][col] != player.piece) {
            return 0
        }
        return checkDiagUp(row - 1, col - 1, player) + 1
    }

    fun checkDiagDown(row: Int, col: Int, player: Player): Int {
        if(row >= numOfRows || col >= numOfCols || board[row][col] != player.piece) {
            return 0
        }
        return checkDiagDown(row + 1, col + 1, player) + 1
    }
    fun checkAntiDiagUp(row: Int, col: Int, player: Player): Int {
        if(row < 0 || col >= numOfCols || board[row][col] != player.piece) {
            return 0
        }
        return checkAntiDiagUp(row - 1, col + 1, player) + 1
    }

    fun checkAntiDiagDown(row: Int, col: Int, player: Player): Int {
        if(row >= numOfRows || col < 0 || board[row][col] != player.piece) {
            return 0
        }
        return checkAntiDiagDown(row + 1, col - 1, player) + 1
    }
    fun checkWin(player: Player): Boolean {

        if((checkDown(curRow, player) == 4) ||
            (checkLeft(curCol, player) + checkRight(curCol, player) - 1 == 4) ||
            (checkDiagUp(curRow, curCol, player) + checkDiagDown(curRow, curCol, player) - 1 == 4) ||
            (checkAntiDiagUp(curRow, curCol, player) + checkAntiDiagDown(curRow, curCol, player) - 1 == 4)){
            return true
        }
        return false
    }

    private fun switchPlayer (curPlayer: Player, player1: Player, player2: Player): Player = if (curPlayer == player1) player2 else player1
    private fun draw(): Boolean = mutableListOf('*', 'o').containsAll(board.first())
    private fun playGame(player1: Player, player2: Player) {
        var curPlayer = player1
        do {
            println("${curPlayer.name}'s turn:")

            var action = readln()
            if(action != "end" && validInp(action) && fillBoard(curPlayer, action.toInt() - 1)) {
                turn++
                printBoard()
                when {
                    checkWin(curPlayer) -> {
                        println("Player ${curPlayer.name} won")
                        action = "end"
                    }
                    draw() -> {
                        println("It is a draw")
                        action = "end"
                    }
                }
                curPlayer = switchPlayer(curPlayer, player1, player2)
            }

        } while(action != "end")
    }
    fun start(player1: Player, player2: Player) {
        buildBoard()
        println("${player1.name} VS ${player2.name}")
        println("$numOfRows X $numOfCols board")
        printBoard()
        playGame(player1, player2)
        println("Game over!")
    }
}