public class Card{

	public String suit;
	public int rank;
	public boolean drawn;
	public int number;
	public int location;
	
	public Card(int cardNumber)
	{
		number = cardNumber;
		suit =  null;
		rank = 0;	
		drawn = false;
	
	}
	public boolean getDrawn()
	{
		return drawn;
	}
	public void setDrawn(boolean held)
	{
		drawn = held;
	}
	public void setLocation(int cardLocation)
	{
		location = cardLocation;
	}
	public int getLocation()
	{
		return location;
	}
	public void setRank(int givenRank)
	{
		rank = givenRank;
	}
	public int getRank()
	{
		return rank;
	}
	public void setSuit(String givenSuit)
	{
		suit = givenSuit;
	}
	public String getSuit()
	{
		return suit;
	}

}
