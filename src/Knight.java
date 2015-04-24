import java.util.Random;


public class Knight {
	
	private int currentX;
	private int currentY;
	private int nextX;
	private int nextY;
	
	public int[][] movement = {{1, 2, 2, 1, -1, -2, -2, -1},
								{2, 1,-1,-2, -2, -1,  1,  2}};
	private int[][] access;
	private KnightsTour tour;
	private Board board;
	private int bWidth;
	private int bHeight;
	
	private int method;
	
	public Knight(KnightsTour tour, Board board, int startX, int startY) {
		currentX = startX;
		currentY = startY;
		this.tour = tour;
		this.board = board;
		board.updateBoard(currentX, currentY);
		
		bWidth = KnightsTour.WIDTH;
		bHeight = KnightsTour.HEIGHT;
		
		access = new int[bHeight][bWidth];
		initAccess();
	}
	
	private void initAccess() {
		for (int j = 0; j <bHeight; ++j) {
			for (int i = 0; i < bWidth; ++i) {
				access[j][i] = 0;
				if ( board.checkMove(i, j) ) {
					for (int k = 0; k < movement[0].length; ++k) {
						int x = i + movement[0][k];
						int y = j + movement[1][k];
						if( board.checkMove(x, y) )
							access[j][i]++;
					}
				}
			}
		}
	}
	
	public void updateAccess() {
		access[currentY][currentX] = 0;
		for (int k = 0; k < movement[0].length; k++) {
			int x = currentX + movement[0][k];
			int y = currentY + movement[1][k];
			if ( board.checkMove(x, y) ) {
				if (access[y][x] != 1) 
					access[y][x]--;
			}
		}
	}
	
	public int getBoardX() { return currentX; }
	public int getBoardY() { return currentY; }
	public int getX() { return ((currentX * 60) + 10); }
	public int getY() { return ((currentY * 60) + 10); }
	public int getNextX() { return ((nextX * 60) + 10); }
	public int getNextY() { return ((nextY * 60) + 10); }
	
	public boolean findMove() {
		int lowest = 9;
		boolean b = false;
		for(int k = 0; k < movement[0].length; ++k) {
			int x = currentX + movement[0][k];
			int y = currentY + movement[1][k];
			if ( board.checkMove(x, y) && access[y][x] < lowest && access[y][x] > 0) {
				nextX = x;
				nextY = y;
				lowest = access[y][x];
				b = true;
			}
		}
		return b;
	}
	
	public boolean move() {
		
		if( findMove() ) {
			currentX = nextX;
			currentY = nextY;
			board.updateBoard(currentX, currentY);
			
			updateAccess();
			return true;
		}
		else {
			return false;
		}
	}
	
	private void shuffle(int[][] a) {
		Random rand = new Random();
		for (int i = a[0].length - 1; i > 0; i--) {
			int index = rand.nextInt(i + 1);
			int temp = a[0][index];
			int temp2 = a[1][index];
			a[0][index] = a[0][i];
			a[1][index] = a[1][i];
			a[0][i] = temp;
			a[1][i] = temp2;
		}
	}
	
	public void shuffleMoves() {
		shuffle(movement);
	}
	
	public void displayAccess() {
		System.out.println("\n Accessibility:\n");
		for(int[] arr : access) {
			for (int a : arr) {
				System.out.print(" " + a);
			}
			System.out.println();
		}
		
		//board.displayBoard();
	}
	
}
