public class Player{
	public String name;
	public int hand;
	
	public Player(String playerName){
		name = playerName;
		hand = 0;	
	}
	
	public int getHand()
	{
		return hand;
	}	
	public void setHand(int cardsHeld)
	{
		hand = cardsHeld;
	}


}