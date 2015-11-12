public class Pieces
{
   public int space; //= new int[28];
	public int round;
   public int move;
   protected boolean winner;
   protected int win;
	public int location;
   Die pop = new Die();

   
// 	public Pieces()
// 	{
// 		for(int i = 0; i < 27; i++)
//       {
//          space[i] = -1;
//       }
// 	}
   
	public void setSpace(int location, int move)
	{
		space = location + move;  
	}
	
	public int getSpace()
   {
      return space;  
   }	
   public void setPieces(int playertotal)
   {
      int[] players = new int[playertotal];
      for(int i = 0; i < playertotal; i++)
      {
         players[i] = i + 1;
//          System.out.println(players[i]); //Test this
      }
      
   }
   
   public int rollDice()
   {
      pop.roll();
      this.move = pop.getFace();
      System.out.println("You rolled a " + move);
      return move;
   }
   
   
   public boolean playGame()
   {
      if(!(winner == true))
      {
			winner = false;
			
      }
      else
      {
      	System.out.println("Congratulations! Player " + win + " won!");
			winner = true;
	   }
      return winner;
   }

}