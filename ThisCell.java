
public class ThisCell implements Cell {
	
	private boolean flagged = false;
	private boolean mine = false;
	private boolean visible = false;
	private int row, col;
	private MyMineModel model;
	
	public ThisCell(int row, int col, MyMineModel model) {
		this.row = row;
		this.col = col;
		this.model = model;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	public boolean isMine() {
		return mine;
	}
	
	public boolean isFlagged() {
		return flagged;
	}
	
	//checks how many mines are around the cell
		public int getNeighborMines() {
			int counter = 0;

			//upper left
			if(row > 0 && col > 0 && model.mineData[row-1][col-1].isMine()) {
				counter++;
			}

			//upper mid
			if(row > 0 && model.mineData[row-1][col].isMine()) {
				counter++;
			}

			//upper right
			if(row > 0 && col < model.getNumCols()-1 && model.mineData[row-1][col+1].isMine()) {
				counter++;
			}

			//mid left
			if(col > 0 && model.mineData[row][col-1].isMine()) {
				counter++;
			}

			//mid right
			if(col < model.getNumCols()-1 && model.mineData[row][col+1].isMine()) {
				counter++;
			}

			//upper left
			if(row < model.getNumRows()-1 && col > 0 && model.mineData[row+1][col-1].isMine()) {
				counter++;
			}

			//upper left
			if(row < model.getNumRows()-1 && model.mineData[row+1][col].isMine()) {
				counter++;
			}

			//upper left
			if(row < model.getNumRows()-1 && col < model.getNumCols()-1 && model.mineData[row+1][col+1].isMine()) {
				counter++;
			}
			return counter;
		}
	
	public void setMine() {
		mine = true;
	}
	
	public void setFlag() {

		if(!flagged) {
			flagged = true;
		}
		else {
			flagged = false;	
		}
	}
	
	public void makeVisible() {
		visible = true;
	}
	
	//checks how many flags are around the cell
		public int getNeighborFlags() {
			int counter = 0;

			//upper left
			if(row > 0 && col > 0 && model.mineData[row-1][col-1].isFlagged()) {
				counter++;
			}

			//upper mid
			if(row > 0 && model.mineData[row-1][col].isFlagged()) {
				counter++;
			}

			//upper right
			if(row > 0 && col < model.getNumCols()-1 && model.mineData[row-1][col+1].isFlagged()) {
				counter++;
			}

			//mid left
			if(col > 0 && model.mineData[row][col-1].isFlagged()) {
				counter++;
			}

			//mid right
			if(col < model.getNumCols()-1 && model.mineData[row][col+1].isFlagged()) {
				counter++;
			}

			//upper left
			if(row < model.getNumRows()-1 && col > 0 && model.mineData[row+1][col-1].isFlagged()) {
				counter++;
			}

			//upper left
			if(row < model.getNumRows()-1 && model.mineData[row+1][col].isFlagged()) {
				counter++;
			}

			//upper left
			if(row < model.getNumRows()-1 && col < model.getNumCols()-1 && model.mineData[row+1][col+1].isFlagged()) {
				counter++;
			}
			return counter;
		}
}
