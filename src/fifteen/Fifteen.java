package fifteen;

import java.util.Random;

public class Fifteen {
	public static final int DEFAULT_ROWS_COUNT = 4;
	public static final int DEFAULT_COLS_COUNT = 4;

	private int[][] tiles;
	private int zeroTileRowIndex;
	private int zeroTileColIndex;
	private int mixStepsCount;
	
	public enum Direction {
		UP(-1, 0), RIGHT(0, 1), DOWN(1, 0), LEFT(0, -1);
		
		public final int rowOffset;
		public final int colOffset;
		
		private Direction(int rowOffset, int colOffset) {
			this.rowOffset = rowOffset;
			this.colOffset = colOffset;
		}
	}
	
	public Fifteen(int rowsCount, int colsCount) {
		checkFifteenSize(rowsCount, colsCount);
		initializeTiles(rowsCount, colsCount);
		zeroTileRowIndex = rowsCount - 1;
		zeroTileColIndex = colsCount - 1;
		mixStepsCount = rowsCount * colsCount * 10; // ?
	}
	
	public Fifteen() {
		this(DEFAULT_ROWS_COUNT, DEFAULT_COLS_COUNT);
	}
	
	private void initializeTiles(int rowsCount, int colsCount) {		
		tiles = new int[rowsCount][colsCount];
		for (int i = 0; i < rowsCount; i++) {
			for (int j = 0; j < colsCount; j++) {
				tiles[i][j] = getWinTileNumber(i, j);
			}
		}
	}
	
	private void checkIndexes(int rowIndex, int colIndex) {
		boolean isRowIndexValid = rowIndex >= 0 && rowIndex < getRowsCount();
		boolean isColIndexValid = colIndex >= 0 && colIndex < getColsCount();

		if (!isColIndexValid && !isRowIndexValid) {
			// TODO
			//throw new IllegalIndexesException(rowIndex, colIndex);
		}

		if (!isColIndexValid) {
			// TODO
			//throw new IllegalIndexesException(colIndex);
		}

		if (!isRowIndexValid) {
			// TODO
			//throw new IllegalIndexesException(rowIndex);
		}
	}

	private static void checkFifteenSize(int rowsCount, int colsCount) {
		boolean isRowsCountValid = rowsCount > 0;
		boolean isColsCountValid = colsCount > 0;

		if (!isColsCountValid && !isRowsCountValid) {
			// TODO
			//throw new FifteenIllegalSizeExeption(rowsCount, colsCount);
		}

		if (!isRowsCountValid) {
			// TODO
		}

		if (!isColsCountValid) {
			// TODO
		}
	}
	
	public void mix() {
		mix(mixStepsCount);
	}
	
	public void mix(int mixStepsCount) {
		for (int i = 0; i < mixStepsCount; i++) {
			Direction randomDirection = getRandomDirection();
			moveTile(randomDirection);
		}
	}
	
	public boolean isWin() {
		for (int i = 0; i < getRowsCount(); i++) {
			for (int j = 0; j < getColsCount(); j++) {
				if (getTileNumber(i, j) != getWinTileNumber(i, j)) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	private Direction getRandomDirection() {
		Direction[] directions = Direction.values();
		int randomIndex = new Random().nextInt(directions.length);
		return directions[randomIndex];
	}
	
	public boolean isTileCanMove(int rowIndex, int colIndex) {
		checkIndexes(rowIndex, colIndex);
		return ((colIndex == zeroTileColIndex && Math.abs(rowIndex - zeroTileRowIndex) == 1) || 
			(rowIndex == zeroTileRowIndex && Math.abs(colIndex - zeroTileColIndex) == 1)) &&
			isTileExists(rowIndex, colIndex);			
	}
	
	public boolean isTileExists(int rowIndex, int colIndex) {
		return rowIndex >= 0 && rowIndex < getRowsCount() &&
				colIndex >= 0 && colIndex < getColsCount();
	}
	
	public boolean moveTile(int rowIndex, int colIndex) {
		checkIndexes(rowIndex, colIndex);
		
		if (isTileCanMove(rowIndex, colIndex)) {
			tiles[zeroTileRowIndex][zeroTileColIndex] = tiles[rowIndex][colIndex];
			tiles[rowIndex][colIndex] = 0;
			zeroTileRowIndex = rowIndex;
			zeroTileColIndex = colIndex;
			return true;
		}

		return false;
	}

	public boolean moveTile(Fifteen.Direction direction) {
		int rowIndex = zeroTileRowIndex + direction.rowOffset;
		int colIndex = zeroTileColIndex + direction.colOffset;
		
		return moveTile(rowIndex, colIndex);
	}
	
	public int getWinTileNumber(int rowIndex, int colIndex) {
		checkIndexes(rowIndex, colIndex);
		int colsCount = getColsCount();
		int rowsCount = getRowsCount();

		if (colIndex == colsCount - 1  && rowIndex == rowsCount - 1) {
			return 0;
		}

		return rowIndex * colsCount + colIndex + 1;
	}
	
	public int getTileNumber(int rowIndex, int colIndex) {
		checkIndexes(rowIndex, colIndex);
		return tiles[rowIndex][colIndex];
	}
	
	public int getRowsCount() {
		return tiles.length;
	}
	
	public int getColsCount() {
		return tiles[0].length;
	}
	
	public int getMixStepsCount() {
		return mixStepsCount;
	}
	
	public void setMixStepsCount(int mixStepsCount) {
		this.mixStepsCount = mixStepsCount;
	}
	
	public int getZeroTileRowIndex() {
		return zeroTileRowIndex;
	}
	
	public int getZeroTileColIndex() {
		return zeroTileColIndex;
	}
}