
public class Board {
	
	private int width;
	private int height;
	
	private int board[][];
	private int count;
	
	public Board(int width, int height) {
		this.width = width;
		this.height = height;
		
		board = new int[height][width];
		initBoard();
		count = 0;
	}
	
	private void initBoard() {
		for (int j = 0; j < height; ++j)
			for (int i = 0; i < width; ++i)
				board[j][i] = 0;
	}
	
	public void updateBoard(int x, int y) {
		board[y][x] = ++count;
	}
	
	public boolean checkMove(int x, int y) {
		if (x >= 0 && x < 8)
			if (y >= 0 && y < 8)
				return (board[y][x] == 0? true : false);
		return false;
	}
	
	public void displayBoard() {
		System.out.println("\n Board:\n");
		for(int[] arr : board) {
			for (int a : arr) {
				System.out.print("\t" + a);
			}
			System.out.println();
			System.out.println();
		}
		System.out.println(count);
	}
	
	public int[][] getBoard() { return board; }
}
