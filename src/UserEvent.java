

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class UserEvent implements MouseListener {

	private int[] mousePos = new int[2];
	private KnightsTour m;
	int clickCounter = 0;
	
	public UserEvent(KnightsTour m) {
		this.m = m;
	}
	
	public void mouseClicked(MouseEvent e) {
		if (clickCounter == 0) {
			mousePos[0] = e.getX();
			mousePos[1] = e.getY();
			m.setKnight(mousePos[0], mousePos[1]);
			m.setPlaced();
		}
		if (clickCounter == 1)
			m.start();
		if (clickCounter == 2) {
			mousePos[0] = e.getX();
			mousePos[1] = e.getY();
			m.reset(mousePos[0], mousePos[1]);
			clickCounter = 0;
			return;
		}
		clickCounter++;
	}
	
	public int getMouseX() { return mousePos[0]; }
	public int getMouseY() { return mousePos[1]; }

	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
}
