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
		
		public void take() {
			piece = null;
		}
		
		public void mark() {
			marked = true;
		}
		
		public void unmark() {
			marked = false;
		}
		
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
	
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		
		//Add all the numbers
		for(int i = 0; i < numberOfColumns; i++) {
			stringBuilder.append("   ").append(i+1);
		}
		//Move to a new row
		stringBuilder.append("\n");
		
		//Loop through all rows
		for(int i = 0; i < numberOfRows; i++) {
			
			//Convert the int to an ASCII char
			char name = (char) (firstRow + i);
			
			//Append the row name
			stringBuilder.append(name).append("  ");
			
			//Loop through all columns
			for(int j = 0; j < numberOfColumns; j++) {
				//Add the fields
				stringBuilder.append(fields[i][j].toString()).append("  ");
			}
			//Move down to a new row
			stringBuilder.append("\n");
		}
		return stringBuilder.toString();
	}
	
	public boolean isValidField(char row, byte column) {
		if(row >= 'a' && row <= 'h' && column > 0 && column <= numberOfColumns)
			return true;
		else
			return false;
	}
	
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
				
			int r = this.row - firstRow;
			int c = this.column - firstColumn;
			Chessboard.this.fields[r][c].put(this);
		}
		
		public void moveOut() {
			Chessboard.this.fields[this.row - firstRow][this.column - firstColumn].take();
		}
		
		public abstract void markReachableFields();
		
		public abstract void unmarkReachableFields();
		
		public void markDiagonals() {
			//Mark a line down right
			for(int i = 0; i < numberOfColumns; i++) {
				int r = (char) (row + i);
				int c = (byte) (column + i);
				if(Chessboard.this.isValidField((char) r, (byte) c)) {
					Chessboard.this.fields[r - firstRow][c - firstColumn].mark();
				}
			}
			//Mark a line down left
			for(int i = 0; i < numberOfColumns; i++) {
				int r = (char) (row + i);
				int c = (byte) (column - i);
				if(Chessboard.this.isValidField((char) r, (byte) c)) {
					Chessboard.this.fields[r - firstRow][c - firstColumn].mark();
				}
			}
			//Mark a line up right
			for(int i = 0; i < numberOfColumns; i++) {
				int r = (char) (row - i);
				int c = (byte) (column + i);
				if(Chessboard.this.isValidField((char) r, (byte) c)) {
					Chessboard.this.fields[r - firstRow][c - firstColumn].mark();
				}
			}
			//Mark a line up left
			for(int i = 0; i < numberOfColumns; i++) {
				int r = (char) (row - i);
				int c = (byte) (column - i);
				if(Chessboard.this.isValidField((char) r, (byte) c)) {
					Chessboard.this.fields[r - firstRow][c - firstColumn].mark();
				}
			}
		}
		
		public void unmarkDiagonals() {
			//Unmark a line down right
			for(int i = 0; i < numberOfColumns; i++) {
				int r = (char) (row + i);
				int c = (byte) (column + i);
				if(Chessboard.this.isValidField((char) r, (byte) c)) {
					Chessboard.this.fields[r - firstRow][c - firstColumn].unmark();
				}
			}
			//Unmark a line down left
			for(int i = 0; i < numberOfColumns; i++) {
				int r = (char) (row + i);
				int c = (byte) (column - i);
				if(Chessboard.this.isValidField((char) r, (byte) c)) {
					Chessboard.this.fields[r - firstRow][c - firstColumn].unmark();
				}
			}
			//Unmark a line up right
			for(int i = 0; i < numberOfColumns; i++) {
				int r = (char) (row - i);
				int c = (byte) (column + i);
				if(Chessboard.this.isValidField((char) r, (byte) c)) {
					Chessboard.this.fields[r - firstRow][c - firstColumn].unmark();
				}
			}
			//Unmark a line up left
			for(int i = 0; i < numberOfColumns; i++) {
				int r = (char) (row - i);
				int c = (byte) (column - i);
				if(Chessboard.this.isValidField((char) r, (byte) c)) {
					Chessboard.this.fields[r - firstRow][c - firstColumn].unmark();
				}
			}
		}
		
		public void markLines() {
			int r = this.row - firstRow;
			int c = this.column - firstColumn;
			
			for(int i = 0; i < numberOfColumns; i++) {
				Chessboard.this.fields[r][i].mark();
			}
			for(int i = 0; i < numberOfRows; i++) {
				Chessboard.this.fields[i][c].mark();
			}
		}
		
		public void unmarkLines() {
			int r = this.row - firstRow;
			int c = this.column - firstColumn;
			
			for(int i = 0; i < numberOfColumns; i++) {
				Chessboard.this.fields[r][i].unmark();
			}
			for(int i = 0; i < numberOfRows; i++) {
				Chessboard.this.fields[i][c].unmark();
			}
		}
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
		
		public void markReachableFields() {
			markLines();
		}
		
		public void unmarkReachableFields() {
			unmarkLines();
		}
	}
	public class Knight extends Chesspiece {
		public Knight(char color) {
			super(color, 'N');
		}
		
		public void markReachableFields() {
			int[] moveRow = {-2, -2, -1, 1, 2, 2, 1, -1};
			int[] moveCol = {1, -1, -2, -2, -1, 1, 2, 2};
			
			for(int i = 0; i < numberOfColumns; i++) {
				if(Chessboard.this.isValidField((char) (row + moveRow[i]), (byte) (column + moveCol[i]))) {
					int r = (char) (row - firstRow + moveRow[i]);
					int c = (byte) (column - firstColumn + moveCol[i]);
					
					Chessboard.this.fields[r][c].mark();
				}
			}
		}
		
		public void unmarkReachableFields() {
			int[] moveRow = {-2, -2, -1, 1, 2, 2, 1, -1};
			int[] moveCol = {1, -1, -2, -2, -1, 1, 2, 2};
			
			for(int i = 0; i < moveRow.length; i++) {
				if(Chessboard.this.isValidField((char) (row + moveRow[i]), (byte) (column + moveCol[i]))) {
					int r = (char) (row - firstRow + moveRow[i]);
					int c = (byte) (column - firstColumn + moveCol[i]);
					
					Chessboard.this.fields[r][c].unmark();
				}
			}
		}
	}
	
	public class Bishop extends Chesspiece {
		public Bishop(char color) {
			super(color, 'B');
		}
		
		public void markReachableFields() {
			markDiagonals();;
		}
		public void unmarkReachableFields() {
			unmarkDiagonals();
		}
	}
	
	public class Queen extends Chesspiece {
		public Queen(char color) {
			super(color, 'Q');
		}
		
		public void markReachableFields() {
			markLines();
			markDiagonals();
		}
		
		public void unmarkReachableFields() {
			unmarkLines();
			unmarkDiagonals();
		}
	}
	
	public class King extends Chesspiece {
		public King(char color) {
			super(color, 'K');
		}
		
		public void markReachableFields() {
			int[] moveRow = {-1, -1, -1, 0, 0, 1, 1, 1};
			int[] moveCol = {-1, 0, 1, -1, 1, -1, 0, 1};
			
			for(int i = 0; i < moveRow.length; i++) {
				if(Chessboard.this.isValidField((char) (row + moveRow[i]), (byte) (column + moveCol[i]))) {
					int r = (char) (row - firstRow + moveRow[i]);
					int c = (byte) (column - firstColumn + moveCol[i]);
					
					Chessboard.this.fields[r][c].mark();
				}
			}
		}
		
		public void unmarkReachableFields() {
			int[] moveRow = {-1, -1, -1, 0, 0, 1, 1, 1};
			int[] moveCol = {-1, 0, 1, -1, 1, -1, 0, 1};
			
			for(int i = 0; i < moveRow.length; i++) {
				if(Chessboard.this.isValidField((char) (row + moveRow[i]), (byte) (column + moveCol[i]))) {
					int r = (char) (row - firstRow + moveRow[i]);
					int c = (byte) (column - firstColumn + moveCol[i]);
					
					Chessboard.this.fields[r][c].unmark();
				}
			}
		}
		
	}
}
