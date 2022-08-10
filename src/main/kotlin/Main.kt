
fun main() {
    println("Connect Four")
    println("First player's name:")
    val player1: Player = Player(readln(), PIECE.CIRCLE.piece)
    println("Second player's name:")
    val player2: Player = Player(readln(), PIECE.STAR.piece)
    val board = ConnectFour()
    board.start(player1, player2)
}

