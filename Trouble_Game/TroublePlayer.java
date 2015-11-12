public class TroublePlayer{
	public String name;
	public int piecesToBePlayed;
	public int number;
	public int piecesOnBoard;
	public int piecesInHome;
	
	public TroublePlayer(int playerNumber){
		name = null;
		number = playerNumber;
		piecesToBePlayed = 4;
		piecesOnBoard = 0;
		piecesInHome = 0;	
	}
	public String getPlayerName()
	{
		return name;
	}
	public void setPlayerName(String playerName)
	{
		name = playerName;
	}
	public int getPiecesToBePlayed()
	{
		return piecesToBePlayed;
	}	
	public void setPiecesToBePlayed(int pieceMoved)
	{
		piecesToBePlayed = pieceMoved;
	}
	public int getPiecesOnBoard()
	{
		return piecesOnBoard;
	}
	public void setPiecesOnBoard(int piecesMoving)
	{
		piecesOnBoard = piecesMoving;
	}
	public int getPiecesInHome()
	{
		return piecesInHome;
	}
	public void setPiecesInHome()
	{
		piecesInHome ++;
	}


}