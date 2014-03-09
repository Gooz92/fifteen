package fifteen.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import fifteen.Fifteen;

public class FifteenPanel extends JPanel {
	private static final long serialVersionUID = 9332984001543960L;
	
	private Fifteen fifteen;
	
	private class Tile extends JButton {

		private static final long serialVersionUID = 1638273954966744563L;
		private int rowIndex;
		private int colIndex;
		
		public Tile(int number, int rowIndex, int colIndex) {;
			this.rowIndex = rowIndex;
			this.colIndex = colIndex;			
			
			setText(String.valueOf(number));
			addActionListener(new ActionListener() {				
				@Override
				public void actionPerformed(ActionEvent e) {
					move();
				}					
			});
		}

		public boolean move() {
			if (!fifteen.isTileCanMove(rowIndex, colIndex)) {
				return false;
			}
		
			FifteenPanel.this.remove(this);
			
			GridBagConstraints gbc = new GridBagConstraints();
			
			int newRowIndex = fifteen.getZeroTileRowIndex();
			int newColIndex = fifteen.getZeroTileColIndex();
			gbc.gridx = newColIndex;
			gbc.gridy = newRowIndex;

			FifteenPanel.this.add(this, gbc);
			FifteenPanel.this.revalidate();
			FifteenPanel.this.repaint();
			
			boolean hasMoved = fifteen.moveTile(rowIndex, colIndex);
			
			rowIndex = newRowIndex;
			colIndex = newColIndex;
			
			if (fifteen.isWin()) {
				JOptionPane.showMessageDialog(null, "You are win!");
			}

			return hasMoved;
		}
	}
	
	public FifteenPanel(Fifteen fifteen) {
		this.fifteen = fifteen;
		setupUI();
	}
	
	public FifteenPanel(int rowsCount, int colsCount) {
		Fifteen fifteen = new Fifteen(rowsCount, colsCount);
		fifteen.mix();
		this.fifteen = fifteen;
		setupUI();
	}
	
	public FifteenPanel() {
		this(Fifteen.DEFAULT_ROWS_COUNT, Fifteen.DEFAULT_COLS_COUNT);
	}
	
	private void setupUI() {
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		for (int i = 0; i < fifteen.getRowsCount(); i++) {
			gbc.gridy = i;
			for (int j = 0; j < fifteen.getColsCount(); j++) {
				if (fifteen.getZeroTileColIndex() != j || 
						fifteen.getZeroTileRowIndex() != i) {
					gbc.gridx = j;
					int tileNumber = fifteen.getTileNumber(i, j);
					JButton btn = new Tile(tileNumber, i, j);
					btn.setFont(new Font("Arial", Font.BOLD, 24));
					btn.setPreferredSize(new Dimension(75, 75));
					add(btn, gbc);
				}
			}
		}
	}
	
}
