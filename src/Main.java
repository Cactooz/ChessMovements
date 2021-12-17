import java.util.Random;

public class Main {
	public static void main(String[] args) throws NotValidFieldException, InterruptedException {
		Chessboard    chessBoard = new Chessboard ();
		System.out.println (chessBoard + "\n");
		
		Chessboard.Chesspiece[]  pieces = new Chessboard.Chesspiece[6];
		pieces[0] = chessBoard.new Pawn ('w');
		pieces[1] = chessBoard.new Rook ('b');
		pieces[2] = chessBoard.new Queen ('w');
		pieces[3] = chessBoard.new Bishop ('w');
		pieces[4] = chessBoard.new King ('b');
		pieces[5] = chessBoard.new Knight ('w');
		
		Random random = new Random();
		for(Chessboard.Chesspiece chesspiece: pieces) {
			char x = (char) (random.nextInt(8) + 'a');
			byte y = (byte) (random.nextInt(8) + 1);
			
			//Move the piece onto the board and mark the possible movements
			chesspiece.moveTo(x, y);
			chesspiece.markReachableFields();
			System.out.println (chessBoard + "\n");
			
			//Sleep for 1 second
			Thread.sleep(1000);
			
			//Remove the piece and unmark the movements
			chesspiece.unmarkReachableFields();
			chesspiece.moveOut();
			
		}
	}
}
