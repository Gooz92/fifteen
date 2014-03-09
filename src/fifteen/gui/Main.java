package fifteen.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main {
	
	private static void createAndShowGUI() {
		JFrame frame = new JFrame("Fifteen");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel fifteenPanel = new FifteenPanel();
		frame.getContentPane().add(fifteenPanel);
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {			
			@Override
			public void run() {
				createAndShowGUI();			
			}
		});
	}
}
