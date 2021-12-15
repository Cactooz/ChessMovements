public class Chessboard {
	public static class Field {
		private char row;
		private byte column;
		private Chesspiece piece = null;
		private boolean marked = false;
		
		public Field(char row, byte column) {
			this.row = row;
			this.column = column;
		}
		
		public void put(Chesspiece piece) {
			this.piece = piece;
		}
		
		public Chesspiece take() {}
		
		public void mark() {}
		
		public void unmark() {}
		
		public String toString() {
			/*If statements expanded
				String s;
				if(marked)
					s = "xx";
				else
					s = "--";
					
				if(piece == null)
					return s;
				else
					return piece.toString();*/
			
			String s = (marked)? "xx" : "--";
			return (piece == null)? s : piece.toString();
		}
	}
	
	public static final int numberOfRows = 8;
	public static final int numberOfColumns = 8;
	
	public static final int firstRow = 'a';
	public static final int firstColumn = 1;
	
	private Field[][] fields;
	
	public Chessboard() {
		fields = new Field[numberOfRows][numberOfColumns];
		char    row = 0;
		
		byte    column = 0;
		for (int r = 0; r < numberOfRows; r++)
		{
			row = (char) (firstRow + r);
			column = firstColumn;
			for (int c = 0; c < numberOfColumns; c++)
			{
				fields[r][c] = new Field(row, column);
				column++;
			}
		}
	}
	
	public String toString() {}
	
	public boolean isValidField(char row, byte column) {}
	
	public abstract class Chesspiece {
		private char color;
		// w - white, b - black
		
		private char name;
		// K - King, Q - Queen, R - Rook, B - Bishop, N - Knight, P – Pawn
		
		protected char row = 0;
		protected byte column = -1;
		
		protected Chesspiece(char color, char name) {
			this.color = color;
			this.name = name;
		}
		
		public String toString() {
			return "" + color + name;
		}
		
		public boolean isOnBoard() {
			return Chessboard.this.isValidField (row, column);
		}
		
		public void moveTo(char row, byte column) throws NotValidFieldException {
			if(!Chessboard.this.isValidField(row, column))
				throw new NotValidFieldException("bad field: " + row + column );
			
			this.row = row;
			this.column = column;
			
			int r = row - firstRow;
			int c = column - firstColumn;
			Chessboard.this.fields[r][c].put(this);
		}
		
		public void moveOut() {}
		
		public abstract void markReachableFields();
		
		public abstract void unmarkReachableFields();
	}
	
	public class Pawn extends Chesspiece {
		public Pawn(char color) {
			super(color, 'P');
		}
		
		public void markReachableFields() {
			byte col = (byte) (column + 1);
			if(Chessboard.this.isValidField(row, col))
			{
				int r = row - firstRow;
				int c = col - firstColumn;
				Chessboard.this.fields[r][c].mark();
			}
		}
		
		public void unmarkReachableFields() {
			byte col = (byte) (column + 1);
			if(Chessboard.this.isValidField (row, col))
			{
				int r = row - firstRow;
				int c = col - firstColumn;
				Chessboard.this.fields[r][c].unmark();
			}
		}
	}
	
	public class Rook extends Chesspiece {
		public Rook(char color) {
			super(color, 'R');
		}
	}
	
	public class Knight extends Chesspiece {
		public Knight(char color) {
			super(color, 'N');
		}
	}
	
	public class Bishop extends Chesspiece {
		public Bishop(char color) {
			super(color, 'B');
		}
	}
	
	public class Queen extends Chesspiece {
		public Queen(char color) {
			super(color, 'Q');
		}
	}
	
	public class King extends Chesspiece {
		public King(char color) {
			super(color, 'K');
		}
	}
}
