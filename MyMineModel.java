import java.io.*;
import java.util.Random;

public class MyMineModel implements MineModel{

	private int numRows;
	private int numCols;
	private int numMines;
	private int numFlags;
	ThisCell[][] mineData;
	private long startTime = System.currentTimeMillis();
	private boolean gameStart,gameEnd,dead;
	
	//starts a new game back end - creates the array
	//1. make the array
		//a. make cells
	//2. start the timer
	//3  place the mines
	//4. restart the game
	public void newGame(int numRows, int numCols, int numMines) {	
		Random randGen = new Random();
		int minesPlaced = 0;
		int rMine, cMine;
		
		//this block sets the forms of the class
		this.numRows = numRows;
		this.numCols = numCols;
		this.numMines = numMines;
		this.gameStart = false;
		this.gameEnd = false;
		this.dead = false;
		
		//initialize array
		mineData = new ThisCell[numRows][numCols];
		for(int rowIndex = 0; rowIndex < numRows; rowIndex++) {
			for(int colIndex = 0; colIndex < numCols; colIndex++) {
				mineData[rowIndex][colIndex] = new ThisCell(rowIndex,colIndex,this);
			}
		}
		
		//this loop places mines in random locations
		while(minesPlaced < numMines) {
			rMine = randGen.nextInt(numRows);
			cMine = randGen.nextInt(numCols);
			
			if(!mineData[rMine][cMine].isMine()) {
				 mineData[rMine][cMine].setMine();
				 minesPlaced++;
			}
		}
		//gameEnd = false;
		gameStart = true;
		System.out.println("game started");
		this.startTime = System.currentTimeMillis();
	}

	public int getNumRows() {
		return numRows;
	}
	
	public int getNumCols() {
		return numCols;
	}
	
	public int getNumMines() {
		return numMines;
	}

	public int getNumFlags() {
		return numFlags;
	}
	
	public int getElapsedSeconds() {
		long elapsedTime = (int) (System.currentTimeMillis() - startTime)/1000;
		return (int) elapsedTime;
	}
	
	public ThisCell getCell(int row, int col) {
		ThisCell cell = mineData[row][col];
		return cell;
	}
	
	public void stepOnCell(int row, int col) {
		int zeroCell = 0;
		//escape method if visible
		if(mineData[row][col].isVisible()) {
			if(mineData[row][col].getNeighborFlags() == mineData[row][col].getNeighborMines()) {
				clearClick(row, col);
			}
			return;
		}

		if(mineData[row][col].isFlagged()) {
			return;
		}
		else if(mineData[row][col].isMine()) {
			mineData[row][col].makeVisible();
			dead = true;
			gameEnd = true;
		}
		else{
			//zero flooding
			mineData[row][col].makeVisible();
			if(mineData[row][col].getNeighborMines() == 0) {
				
				zeroCell = zeroFlood(row, col, zeroCell);
				
				if(zeroCell == 0) {
					return;
				}
			}
		}
	}
	
	//alters flagged state
	public void placeOrRemoveFlagOnCell(int row, int col) {
		if(!mineData[row][col].isVisible())
			mineData[row][col].setFlag(); //dont flag visible cells
	}

	public boolean isGameStarted() {
		return gameStart;
	}
	
	public boolean isGameOver() {
		return gameEnd;
	}

	public boolean isPlayerDead() {
		return dead;
	}

	public boolean isGameWon() {
		int clearCells = 0;
		
		for(int rowIndex = 0; rowIndex < numRows; rowIndex++) {
			for(int colIndex = 0; colIndex < numCols; colIndex++) {
				if(mineData[rowIndex][colIndex].isVisible() && !mineData[rowIndex][colIndex].isMine()) {
					clearCells++;
				}
			}
		}
		
		if((numRows*numCols) - clearCells == numMines) {
			return true;
		}
		return false;
	}
	//floods all zeros
	private int zeroFlood(int row, int col, int zeroCell) {
		//if it exists and is not visible and is surrounded by no mines
		if(row > 0 && col > 0 && !mineData[row-1][col-1].isVisible()) {
			stepOnCell(row-1,col-1);
			zeroCell++;
		}
		
		if(row > 0 && !mineData[row-1][col].isVisible()){
			stepOnCell(row-1,col);
			zeroCell++;
		}
		
		if(row > 0 && col < numCols-1 && !mineData[row-1][col+1].isVisible()){
			stepOnCell(row-1,col+1);
			zeroCell++;
		}
		
		if(col > 0 && !mineData[row][col-1].isVisible()){
			stepOnCell(row,col-1);
			zeroCell++;
		}
		
		if(col < numCols-1 && !mineData[row][col+1].isVisible()){
			stepOnCell(row,col+1);
			zeroCell++;
		}
		
		if(row < numRows-1 && col > 0 && !mineData[row+1][col-1].isVisible()){
			stepOnCell(row+1,col-1);
			zeroCell++;
		}
		
		if(row < numRows-1 && !mineData[row+1][col].isVisible()){
			stepOnCell(row+1,col);
			zeroCell++;
		}
		
		if(row < numRows-1 && col < numCols-1 && !mineData[row+1][col+1].isVisible()){
			stepOnCell(row+1,col+1);
			zeroCell++;
		}
		
		return zeroCell;
	}

	//in case you click on visible cell with neigh. flags = neigh. mines
	private void clearClick(int row, int col) {
		if(row > 0 && col > 0 && !mineData[row-1][col-1].isVisible()) {
			stepOnCell(row-1,col-1);
		}

		if(row > 0 && !mineData[row-1][col].isVisible()){
			stepOnCell(row-1,col);
		}

		if(row > 0 && col < numCols-1 && !mineData[row-1][col+1].isVisible()){
			stepOnCell(row-1,col+1);
		}

		if(col > 0 && !mineData[row][col-1].isVisible()){
			stepOnCell(row,col-1);
		}

		if(col < numCols-1 && !mineData[row][col+1].isVisible()){
			stepOnCell(row,col+1);
		}

		if(row < numRows-1 && col > 0 && !mineData[row+1][col-1].isVisible()){
			stepOnCell(row+1,col-1);
		}

		if(row < numRows-1 && !mineData[row+1][col].isVisible()){
			stepOnCell(row+1,col);
		}

		if(row < numRows-1 && col < numCols-1 && !mineData[row+1][col+1].isVisible()){
			stepOnCell(row+1,col+1);	
		}
	}

	/*private int[] hiScore() {
		//makes or finds save file
		FileOutputStream saveFile = new FileOutputStream("HiScore.sav"); 
		
		//object stream
		ObjectOutputStream save = new ObjectOutputStream(saveFile);
		
		if(time < save.score[1])
		return;
	}*/
}

