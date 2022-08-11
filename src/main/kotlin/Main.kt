
fun main() {
    println("Connect Four")
    println("First player's name:")
    val player1: Player = Player(readln(), PIECE.CIRCLE.piece)
    println("Second player's name:")
    val player2: Player = Player(readln(), PIECE.STAR.piece)
    val board = ConnectFour()
    board.buildBoard()
    var numOfGames = 0
    do {
        println(
            """
        Do you want to play single or multiple games?
        For a single game, input 1 or press Enter
        Input a number of games:
        """.trimIndent()
        )
        val inp = readln()
        try {
            when  {
                inp.isEmpty() ->  numOfGames = 1
                inp.toInt() < 1 -> throw Exception()
                else -> numOfGames = inp.toInt()
            }
        } catch (e: Exception) {
            println("Invalid input")
        }
    } while(numOfGames == 0)
    println("${player1.name} VS ${player2.name}")
    println("${board.getNumOfRows()} X ${board.getNumOfCols()}")
    when {
        numOfGames == 1 -> {
            println("Single Game")
            board.start(player1, player2)
        }
        else -> {
            println("Total $numOfGames games")
            var curGame = 1
            var p1 = player1
            var p2 = player2
            while(curGame <= numOfGames) {

                println("Game #$curGame")
                board.start(p1, p2)
                if(board.getEndOfGame()) break
                println("Score")
                println("${player1.name}: ${player1.getWins()} ${player2.name}: ${player2.getWins()}")
                curGame++
                board.clear()
                p1 = if (player1 == p1) player2 else player1
                p2 = if (player1 == p2) player2 else player1

            }
        }
    }
    println("Game Over!")
}


