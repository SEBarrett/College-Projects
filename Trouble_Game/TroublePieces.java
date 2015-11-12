public class TroublePieces{
	
	public int location;
	public int number;
	
	public TroublePieces(int pieceNumber){
		number = pieceNumber;
		location = 0;
	}
	public int getLocation()
	{
		return location;
	}
	public void setLocation(int diceRoll)
	{
		location = (location + diceRoll);
	}
	
}
