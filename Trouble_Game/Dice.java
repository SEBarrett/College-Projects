public class Dice()
{
	
	public int rollDice()
   {
      pop.roll();
      this.move = pop.getFace();
      System.out.println("You rolled a " + move);
      return move;
   }
}