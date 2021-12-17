public class Main {
	public static void main(String[] args) {
		Chessboard    chessBoard = new Chessboard ();
		System.out.println (chessBoard + "\n");
		
		Chessboard.Chesspiece[]  pieces = new Chessboard.Chesspiece[6];
		pieces[0] = chessBoard.new Pawn ('w');
		pieces[1] = chessBoard.new Rook ('b');
		pieces[2] = chessBoard.new Queen ('w');
		pieces[3] = chessBoard.new Bishop ('w');
		pieces[4] = chessBoard.new King ('b');
		pieces[5] = chessBoard.new Knight ('w');
	}
}
