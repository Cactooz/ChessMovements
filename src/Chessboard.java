public class Chessboard {
	public static class Field {
		private char row;
		private byte column;
		private Chesspiece piece = null;
		private boolean marked = false;
		
		//Constructor for the current field
		public Field(char row, byte column) {
			this.row = row;
			this.column = column;
		}
		
		//Place the piece on the board
		public void put(Chesspiece piece) {
			this.piece = piece;
		}
		
		//Remove the piece from the board
		public void take() {
			piece = null;
		}
		
		//Mark that a piece is places on the board
		public void mark() {
			marked = true;
		}
		
		//Unmark a piece on the board
		public void unmark() {
			marked = false;
		}
		
		//Prints out the fields on the board
		public String toString() {
			String s = (marked)? "xx" : "--";
			return (piece == null)? s : piece.toString();
		}
	}
	
	//The size of the board
	public static final int numberOfRows = 8;
	public static final int numberOfColumns = 8;
	
	//The first name of the row and columns
	public static final int firstRow = 'a';
	public static final int firstColumn = 1;
	
	//A 2 dimensional array for the board
	private Field[][] fields;
	
	//Creates the board
	public Chessboard() {
		fields = new Field[numberOfRows][numberOfColumns];
		char row;
		byte column;
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
	
	//Printing out the full board
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
	
	//Check if the field is a valid field on to board
	public boolean isValidField(char row, byte column) {
		return row >= 'a' && row <= 'h' && column > 0 && column <= numberOfColumns;
	}
	
	public abstract class Chesspiece {
		private char color;
		// w - white, b - black
		
		private char name;
		// K - King, Q - Queen, R - Rook, B - Bishop, N - Knight, P – Pawn
		
		protected char row = 0;
		protected byte column = -1;
		
		//Constructor for the chesspiece, set the name and color
		protected Chesspiece(char color, char name) {
			this.color = color;
			this.name = name;
		}
		
		//Print the name of the piece
		public String toString() {
			return "" + color + name;
		}
		
		//Check if the piece is on the board
		public boolean isOnBoard() {
			return Chessboard.this.isValidField(row, column);
		}
		
		//Move the piece to a location on the board
		public void moveTo(char row, byte column) throws NotValidFieldException {
			//Check if the movement is to a location on the board
			if(!Chessboard.this.isValidField(row, column))
				throw new NotValidFieldException("bad field: " + row + column );
			
			this.row = row;
			this.column = column;
			
			int r = row - firstRow;
			int c = column - firstColumn;
			//Put the piece on the board
			Chessboard.this.fields[r][c].put(this);
		}
		
		//Remove a piece from the board
		public void moveOut() {
			Chessboard.this.fields[this.row - firstRow][this.column - firstColumn].take();
		}
		
		public abstract void markReachableFields();
		
		public abstract void unmarkReachableFields();
		
		//Marks the diagonals from a piece
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
		
		//Unmarks the diagonals from a piece
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
		
		//Marks the lines from a piece
		public void markLines() {
			int r = this.row - firstRow;
			int c = this.column - firstColumn;
			
			//Mark the row
			for(int i = 0; i < numberOfColumns; i++) {
				Chessboard.this.fields[r][i].mark();
			}
			//Mark the column
			for(int i = 0; i < numberOfRows; i++) {
				Chessboard.this.fields[i][c].mark();
			}
		}
		
		//Unmarks the lines from a piece
		public void unmarkLines() {
			int r = this.row - firstRow;
			int c = this.column - firstColumn;
			
			//Unmark the row
			for(int i = 0; i < numberOfColumns; i++) {
				Chessboard.this.fields[r][i].unmark();
			}
			//Unmark the column
			for(int i = 0; i < numberOfRows; i++) {
				Chessboard.this.fields[i][c].unmark();
			}
		}
	}
	
	//Class for the pawn movements
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
	
	//Class for the Rook movements
	public class Rook extends Chesspiece {
		public Rook(char color) {
			super(color, 'R');
		}
		
		//Mark the row and column around the Rook
		public void markReachableFields() {
			markLines();
		}
		
		//Unmark the row and column around the Rook
		public void unmarkReachableFields() {
			unmarkLines();
		}
	}
	
	//Class for the Knight movements
	public class Knight extends Chesspiece {
		public Knight(char color) {
			super(color, 'N');
		}
		
		public void markReachableFields() {
			//Arrays for the special movements for the Knight
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
			//Arrays for the special movements for the Knight
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
	
	//Class for the Bishop movements
	public class Bishop extends Chesspiece {
		public Bishop(char color) {
			super(color, 'B');
		}
		
		//Mark the diagonals from the Bishop
		public void markReachableFields() {
			markDiagonals();
		}
		
		//Unmark the diagonals from the Bishop
		public void unmarkReachableFields() {
			unmarkDiagonals();
		}
	}
	
	//Class for the Queen movements
	public class Queen extends Chesspiece {
		public Queen(char color) {
			super(color, 'Q');
		}
		
		//Mark the diagonals and lines from the Queen
		public void markReachableFields() {
			markLines();
			markDiagonals();
		}
		
		//Unmark the diagonals and lines from the Queen
		public void unmarkReachableFields() {
			unmarkLines();
			unmarkDiagonals();
		}
	}
	
	//Class for the King movements
	public class King extends Chesspiece {
		public King(char color) {
			super(color, 'K');
		}
		
		//Mark all the fields around the king
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
		
		//Unmark all the fields around the king
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
