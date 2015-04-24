import java.awt.BorderLayout;

import javax.swing.JFrame;


public class Main {

	public static void main(String[] args) {
		KnightsTour kt = new KnightsTour();
		JFrame frame = new JFrame();
		frame.setLayout(new BorderLayout());
		frame.add(kt, BorderLayout.NORTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.pack();
		frame.setVisible(true);

	}

}
