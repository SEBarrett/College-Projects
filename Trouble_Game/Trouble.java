import java.util.Scanner;

public class Trouble
{
	public static void main(String args[])
	{
      Pieces game = new Pieces();   
		
		Scanner kb = new Scanner(System.in);
		int players;
		
		System.out.println("How many people are playing?");
		
		if(kb.hasNextInt())
		{
			players = kb.nextInt();
			if(players > 4)
            System.out.println("Error, max number of players is 4.");
         else
            System.out.println(players + " players are playing.");
		}
		else
		{
			System.out.println("Error, please input a number of players.");
			players = 0;
		}
	   game.setPieces(players);
//       game.rollDice();  
      System.out.println("Would you like to start (y/n)?");
      String answer = kb.next();
      if(answer.equalsIgnoreCase("y"))
      {
         while(!game.playGame() == true)
			{
				int counter = 1; //player current turn
				int location;
				int rolledNumber;
				for(int i = 0; counter <= players; counter++)
				{
					location = game.getSpace();
					rolledNumber = game.rollDice();
					game.setSpace(location,rolledNumber);
				}
			}
      }
      else
         System.out.println("Maybe next time, then.");
     
	}
}