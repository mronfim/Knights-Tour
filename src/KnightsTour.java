import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.io.FileInputStream;
import java.net.URL;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JRadioButton;


public class KnightsTour extends Canvas implements Runnable {
	
	public static final String TITLE = "Knights Tour";
	public static int fWidth = 480;
	public static int fHeight = 480;
	
	public static int WIDTH = 8;
	public static int HEIGHT = 8;
	
	private Board board;
	private int[][] history;
	private Knight knight;
	private int knightX;
	private int knightY;
	private boolean placed = false; 
	private boolean toDraw = false; 
	
	private Thread thread;
	private boolean running = false;
	
	private Image img = null;
	private Font font = null;
	
	UserEvent input = new UserEvent(this);
	
	public KnightsTour() {
		Dimension size = new Dimension(fWidth, fHeight);
		setPreferredSize(size);
		setMaximumSize(size);
		setMinimumSize(size);
		setVisible(true);
		
		board = new Board(WIDTH, HEIGHT);
		history = board.getBoard();
		
		running = false;
		
		img = getImage("knight.png");
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("res/fonts/FlappyBirdFont.TTF")).deriveFont(30f);
		} catch (Exception e) {
			System.out.println("-- An error occured while getting font --\n" + e.getMessage());
		}
		
		addMouseListener(input);
	}
	
	public void reset(int x, int y) {
		board = new Board(WIDTH, HEIGHT);
		knightX = knight.getX();
		knightY = knight.getY();
		history = board.getBoard();
		running = false;
		placed = false;
		toDraw = false;
		repaint();
	}
	
	public void setKnight(int x, int y) {
		knight = new Knight(this, board, x/60, y/60);
		knightX = knight.getX();
		knightY = knight.getY();
		history = board.getBoard();
		toDraw = true;
		repaint();
	}
	
	public void setPlaced() {
		placed = true;
	}
	
	// Loads the image for the knight
	public Image getImage(String path) {
		Image img = null;
		try {
			URL imgURL = KnightsTour.class.getResource(path);
			img = Toolkit.getDefaultToolkit().getImage(imgURL);
		} catch (Exception e) {
			System.out.println("-- An error occured when loading image --\n" + e.getMessage());
		}
		return img;
	}
	
	public synchronized void start() {
		if (running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop() {
		if (!running)
			return;
		running = false;
		try {
			thread.join(1);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}
	
	public void run() {
		//knight.displayAccess();
		while (running) {
			if (placed) {
				//knight.shuffleMoves();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	
				if (knight.move()) {
					knightX = knight.getX();
					knightY = knight.getY();
					draw();
				}
				else
					running = false;
			}
		}
		stop();
		//knight.displayAccess();
	}
	
	public void draw() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(2);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		paint(g);
		g.dispose();
		bs.show();
	}
	
	public void paint(Graphics g) {
		
		// draw board
		for (int j = 0; j < 8; ++j) {
			for (int i = 0; i < 8; ++i) {
				if ((j % 2 == 0 && i % 2 == 0) || (j % 2 != 0 && i % 2 != 0))
					g.setColor(new Color(255, 219, 161));
				else
					g.setColor(new Color(158, 97, 0));
				g.fillRect(i*60, j*60, 60, 60);
			}
		}
		
		if (toDraw) {
			// draw history
			g.setFont(font);
			FontMetrics fm = g.getFontMetrics();
			for (int j = 0; j < history.length; ++j) {
				for (int i = 0; i < history[j].length; ++i) {
					if (history[j][i] != 0) {
						String count = Integer.toString(history[j][i]);
						int x = (60 - fm.stringWidth(count)) / 2;
						int y =  (60 + fm.getHeight()) / 2;
						g.setColor(Color.WHITE);
						g.drawString(count, i * 60 + x, j * 60 + y);
					}
				}
			}
		}
		
		// draw knight
		if (toDraw) {
			if ((knight.getBoardY() % 2 == 0 && knight.getBoardX() % 2 == 0) || (knight.getBoardY() % 2 != 0 && knight.getBoardX() % 2 != 0))
				g.setColor(new Color(255, 219, 161));
			else
				g.setColor(new Color(158, 97, 0));
			g.fillRect(knight.getBoardX()*60, knight.getBoardY()*60, 60, 60);
			g.drawImage(img, knightX, knightY, 40, 40, this);
		}
	}
	
	public void display() {
		//knight.displayAccess();
		knight.move();
		knight.displayAccess();
	}
}
