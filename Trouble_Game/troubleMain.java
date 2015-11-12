import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;
import java.util.Scanner;
public class troubleMain {

	public static void main(String []args){
		ArrayList<TroublePieces> testing = new ArrayList<TroublePieces>();
		TroublePieces[] totalPieces = new TroublePieces[4];
		for(int i = 0; i < 4; i++)
		{
			totalPieces[i] = new TroublePieces(i);
		}
		testing.add(totalPieces[1]);
		testing.get(0).getLocation();
		//change to take user input
		int numberPlayers = 3;
		int randomNum;
		//strings of players names
		String one = "Player One";
		String two = "Player Two";
		String three = "Player Three";
		String four = "Player Four";
		//array used to load the arraylist
	//	TroublePieces[] totalPieces = new TroublePieces[4];
// 	TroublePieces game = new TroublePieces();
		//loads the array
		for(int i = 0; i < 4; i++)
		{
			totalPieces[i] = new TroublePieces(i);
		}
		ArrayList<TroublePlayer> players = new ArrayList<TroublePlayer>();
		TroublePlayer[] totalPlayers = new TroublePlayer[4];
		for(int i = 0; i < 4; i++)
		{
			totalPlayers[i] = new TroublePlayer(i);
		}
		for(int i = 0; i < 4; i++){
				players.add(totalPlayers[i]);
			}
		if(numberPlayers == 1)
		{	
			players.get(0).setPlayerName(one);
		}
		else if(numberPlayers == 2)
		{
			players.get(0).setPlayerName(one);
			players.get(1).setPlayerName(two);
		}
		else if(numberPlayers == 3)
		{
			players.get(0).setPlayerName(one);
			players.get(1).setPlayerName(two);
			players.get(2).setPlayerName(three);
		}
		else if (numberPlayers == 4)
		{
			players.get(0).setPlayerName(one);
			players.get(1).setPlayerName(two);
			players.get(2).setPlayerName(three);
			players.get(3).setPlayerName(four);

		}
		else
		{
			//error for number greater than
		}
		ArrayList<TroublePieces> playerOnePieces = new ArrayList<TroublePieces>();
		//loads the arraylist
		playerOnePieces.add(totalPieces[0]);
		playerOnePieces.add(totalPieces[1]);
		playerOnePieces.add(totalPieces[2]);
		playerOnePieces.add(totalPieces[3]);
		ArrayList<TroublePieces> playerTwoPieces = new ArrayList<TroublePieces>();
		//loads the arraylist
		playerTwoPieces.add(totalPieces[0]);
		playerTwoPieces.add(totalPieces[1]);
		playerTwoPieces.add(totalPieces[2]);
		playerTwoPieces.add(totalPieces[3]);
		ArrayList<TroublePieces> playerThreePieces = new ArrayList<TroublePieces>();
		//loads the arraylist
		playerThreePieces.add(totalPieces[0]);
		playerThreePieces.add(totalPieces[1]);
		playerThreePieces.add(totalPieces[2]);
		playerThreePieces.add(totalPieces[3]);
		ArrayList<TroublePieces> playerFourPieces = new ArrayList<TroublePieces>();
		//loads the arraylist
		playerFourPieces.add(totalPieces[0]);
		playerFourPieces.add(totalPieces[1]);
		playerFourPieces.add(totalPieces[2]);
		playerFourPieces.add(totalPieces[3]);
		
		while(players.get(0).getPiecesInHome() != 4  && players.get(1).getPiecesInHome() != 4  && players.get(2).getPiecesInHome() != 4 && players.get(3).getPiecesInHome() != 4   )
		{
			int playerTurn = 0;
			if(playerTurn == 0)
			{
				System.out.println("It is " + players.get(playerTurn).getPlayerName() + "'s turn. Rolling Dice!");					
 				randomNum = 1 + (int)(Math.random()*6); 
 				System.out.println(players.get(playerTurn).getPlayerName() + " you rolled a " + randomNum);
				playerTurn = 1;
				System.out.println("Player One, which piece would you like to move?");
				System.out.println("Piece One " + playerOnePieces.get(1).getLocation());
				System.out.println("Piece Two " + playerOnePieces.get(1).getLocation());
 				System.out.println("Piece Three " + playerOnePieces.get(2).getLocation());
 				System.out.println("Piece Four " + playerOnePieces.get(3).getLocation());

			}
			if(playerTurn == 1)
			{
				System.out.println("It is " + players.get(playerTurn).getPlayerName() + "'s turn. Rolling Dice!");
				randomNum = 1 + (int)(Math.random()*6); 
				System.out.println(players.get(playerTurn).getPlayerName() + " you rolled a " + randomNum); 
				playerTurn = 2;
				System.out.println("Player Two, which piece would you like to move?");
				System.out.println("Piece One " + playerTwoPieces.get(0).getLocation());
				System.out.println("Piece Two " + playerTwoPieces.get(1).getLocation());
				System.out.println("Piece Three " + playerTwoPieces.get(2).getLocation());
				System.out.println("Piece Four " + playerTwoPieces.get(3).getLocation());
			}
			if(playerTurn == 2)
			{
				System.out.println("It is " + players.get(playerTurn).getPlayerName() + "'s turn. Rolling Dice!");
 				randomNum = 1 + (int)(Math.random()*6); 
				System.out.println(players.get(playerTurn).getPlayerName() + " you rolled a " + randomNum); 
				playerTurn = 3;
				System.out.println("Player Three, which piece would you like to move?");
				System.out.println("Piece One " + playerThreePieces.get(0).getLocation());
				System.out.println("Piece Two " + playerThreePieces.get(1).getLocation());
				System.out.println("Piece Three " + playerThreePieces.get(2).getLocation());
				System.out.println("Piece Four " + playerThreePieces.get(3).getLocation());
			}
			else
			{
				System.out.println("It is " + players.get(playerTurn).getPlayerName() + "'s turn. Rolling Dice!");
				randomNum = 1 + (int)(Math.random()*6); 
				System.out.println(players.get(playerTurn).getPlayerName() + " you rolled a " + randomNum); 
				playerTurn++;
				System.out.println("Player Two, which piece would you like to move?");
				System.out.println("Piece One " + playerFourPieces.get(0).getLocation());
				System.out.println("Piece Two " + playerFourPieces.get(1).getLocation());
				System.out.println("Piece Three " + playerFourPieces.get(2).getLocation());
				System.out.println("Piece Four " + playerFourPieces.get(3).getLocation());

				}
			break;
		}
	}
}