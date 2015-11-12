import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;
import java.util.Scanner;

public class crazyEightsMain {

	public static void main(String []args){	
		//creates the deck of cards to be used
		ArrayList<Card> deck = new ArrayList<Card>();
		Card[] deckCards = new Card[52];
		// creates arraylists to hold the two players cards, discard pile, and the top card
		ArrayList<Card> computerHand = new ArrayList<Card>();
		ArrayList<Card> testerHand = new ArrayList<Card>();
		ArrayList<Card> discardPile = new ArrayList<Card>();
		ArrayList<Card> topCard = new ArrayList<Card>();
		ArrayList<Card> computerPlaying = new ArrayList<Card>();
		//ints used by the computer to store the scoring options
      int yes = 1;
      int no = 0;
      int eight = 8;
      int topCardRank;
		String topCardSuit = null;
		//creates two players
		Player computer = new Player("Computer");
		Player tester = new Player("Tester");
		Scanner keyboard = new Scanner(System.in);
      int playerSelection;//player selection of card to be played
      int multipleCards; //player selection to play more than one card
      int multipleCardSelection; //player selection of the multiple cards to be played
      boolean playersTurn = true; //if true its the players turn 
      int selectionNumber;
		//assigns all the cards to an interger value for later use
		for(int i = 0; i < 52; i++){
			deckCards[i] = new Card(i);
		}
		//loads the arraylist with cards to be assigned values	
		for(int i = 0; i <= 51; i++){
			deck.add(deckCards[i]);
		}
		//Sets the first thirteen cards to the Spades set and assigns a rank
		for(int i = 0; i <= 13; i++){
			deck.get(i).setSuit("Spades");
			deck.get(i).setRank(i + 1);
			//System.out.println(deck[i].getSuit());
		}
		//Sets the second group of thirteen cards to the Hearts set and assigns a rank
		for(int i = 13; i <= 25; i++){
			deck.get(i).setSuit("Hearts");
			deck.get(i).setRank(i - 12);
		}
			
		//Sets the third group of thirteen cards to the Clubs set and assigns a rank
		for(int i = 26; i <= 39; i++){
			deck.get(i).setSuit("Clubs");
			deck.get(i).setRank(i - 25);
	
		}
		//Sets the last group of thirteen cards to the Hearts set and assigns a rank
		for(int i = 39; i <= 51; i++){
			deck.get(i).setSuit("Diamonds");
			deck.get(i).setRank(i - 38);
		}
		//randomizes the deck
		Collections.shuffle(deck);
		//deals the first six cards to the computer
		for(int i = 0; i < 6; i ++){
			computerHand.add(deck.get(i));
		}
		//removes the cards dealt to the computer from the deck
		for(int i = 0; i < 6; i ++){
			deck.remove(0);
		}
		//set computer hand int size
		computer.setHand(computerHand.size());
		//deals the next six cards to the player
		for(int i = 0; i < 6; i++){
			testerHand.add(deck.get(i));		
		}
		//removes the cards dealt to the player from the deck
		for(int i = 0; i < 6; i ++){
			deck.remove(0);
		}
		System.out.println("");
		//sets player hand int size
		tester.setHand(testerHand.size());
		System.out.println("");	
		//prints out the cards in the deck
// 		for(int i = 0; i < deck.size(); i++)
// 		{
// 			System.out.print("Counter: " + i + " ");
// 			System.out.println(deck.get(i).getSuit() + " " + deck.get(i).getRank());
// 		}
// 		//prints the top card of the deck
// 		System.out.println("");
// 		System.out.println("Top card is " + deck.get(0).getSuit() + " " + deck.get(0).getRank());
		//moves the top card from the deck arraylist to the top card arraylist
		topCard.add(deck.get(0));
		deck.remove(0);
      while(tester.getHand() != 0  && computer.getHand() != 0){   
			//prints out the top card 
         System.out.println("Top card is now: " + topCard.get(0).getSuit() + " " + topCard.get(0).getRank());

         int drawThreeTimes = 0; //after the player draws three times and is unable to play the turn is passed
 
			while(playersTurn == true && drawThreeTimes < 3){
            System.out.println("Players Hand");
            for(int p = 0; p < testerHand.size() ; p ++){
               System.out.print(" " + (p + 1) + " (" + testerHand.get(p).getSuit() + " " + testerHand.get(p).getRank() + ") ");
      		}
            System.out.println(" ");
            System.out.println("Player's turn. Please select the card(s) you wish to play: ");
            System.out.println("To draw a card enter 0");
            System.out.println("To select mulitple cards enter 9");
            playerSelection = keyboard.nextInt();
            // player draws another card
            if(playerSelection == 0){
               testerHand.add(deck.get(0));
               deck.remove(0);
               System.out.println("Player's Hand: " );
   		      for(int i = 0; i < testerHand.size() ; i ++){
   			      System.out.print(" " + (i + 1) + " (" + testerHand.get(i).getSuit() + " " + testerHand.get(i).getRank() + ") ");
   		      }
               drawThreeTimes ++; // prevents player from drawing more than the max number of cards in one round
               System.out.println(" ");
               //sets player hand int size
   		      tester.setHand(testerHand.size());
            }
            // allows player to select one card
            else if(playerSelection != 0 && playerSelection != 9){
               selectionNumber = playerSelection - 1;
               discardPile.add(topCard.get(0));
               topCard.remove(0);
               topCard.add(testerHand.get(selectionNumber));
               testerHand.remove(selectionNumber);
               playersTurn = false;
					tester.setHand(testerHand.size());
            }
            else if(playerSelection == 9){
               System.out.println("How many cards would you like to play? ");
               multipleCards = keyboard.nextInt();
               for(int i = 1; i <= multipleCards; i ++){
                  System.out.println("Please select the card you wish to play: ");
                  multipleCardSelection = keyboard.nextInt();
						discardPile.add(topCard.get(0));
						topCard.remove(0);
                  topCard.add(testerHand.get(multipleCardSelection - 1));
                  testerHand.remove(multipleCardSelection - 1);
                  for(int p = 0; p < testerHand.size() ; p ++){
      			      System.out.print(" " + (p + 1) + " (" + testerHand.get(p).getSuit() + " " + testerHand.get(p).getRank() + ") ");
      		      }
                  System.out.println(" ");
						tester.setHand(testerHand.size());
               }
               playersTurn = false;    
            }
            if(drawThreeTimes == 3){
               playersTurn = false;
               System.out.println("Player has drawn three times, passing turn.");
            }       
         }
         //end of players turn
			if(testerHand.size() == 0){
				break;
			}
			else{
				System.out.println("Computers turn.");
			}
// 	      System.out.println("Computer's Hand: " );
//    		for(int i = 0; i < computerHand.size() ; i ++){
//    			System.out.print(" " + (i + 1) + " (" + computerHand.get(i).getSuit() + " " + computerHand.get(i).getRank() + ") ");
//    		}  
//          System.out.println(" ");
         //computer's turn
			int drawThreeTimesComputer = 0;
         while(playersTurn == false && drawThreeTimesComputer < 3){
				//creates arraylist to hold the integers for the computers scoring options for comparison
				ArrayList<Integer> suitScoringOptions = new ArrayList<Integer>();
      		ArrayList<Integer> rankScoringOptions = new ArrayList<Integer>();
      		ArrayList<Integer> eightScoringOption = new ArrayList<Integer>();
				boolean computerPlayed = false;
				//prints out the top card 
          	System.out.println("Top card is now: " + topCard.get(0).getSuit() + " " + topCard.get(0).getRank());
				//top card is asessed for comparison
            topCardSuit = topCard.get(0).getSuit();
            topCardRank = topCard.get(0).getRank();
				//compares the computers hand to the top card rank for possible scoring options
            for(int i = 0; i < computerHand.size(); i++){
               if(topCardRank == computerHand.get(i).getRank()){
                  rankScoringOptions.add(yes);
               }
               else{
                  rankScoringOptions.add(no);
               }
                 
            }   
				//compares the computers hand to the top card suit for possible scoring options
            for(int i = 0; i < computerHand.size(); i++){
               if(topCardSuit.equals(computerHand.get(i).getSuit())){
                  suitScoringOptions.add(yes);
               }
               else{
                  suitScoringOptions.add(no);
               }
                 
            } 
				//asesses the computers hand if it contains any eights
            for(int i = 0; i < computerHand.size(); i++){
               if(eight == computerHand.get(i).getRank()){
                  eightScoringOption.add(yes);
               }
               else{
                  eightScoringOption.add(no);
               }
                 
            }  
				//checks the frequency of "yes" of scoring options
            int occurrencesRank = Collections.frequency(rankScoringOptions, yes);
            int occurrencesSuit = Collections.frequency(suitScoringOptions, yes);
            int occurrencesEight = Collections.frequency(eightScoringOption, yes);
				int computerHandSize = computerHand.size();
				//System.out.println("Computer Hand size: " + computerHandSize);
				//System.out.println(computerHand.size());
				//System.out.println("Testing");
				//if the computer has more than zero of the matching rank if will play it
            if(occurrencesRank >= 1){
					//System.out.println("Testing");
               for(int i = 0; i < computerHand.size(); i++){ 	
						int e = 0;
                  if(topCardRank == computerHand.get(i - e).getRank()){
                     System.out.println("The computer is playing: " + computerHand.get(i).getSuit() + "  " + computerHand.get(i).getRank());
                     discardPile.add(topCard.get(0));
                     topCard.remove(0);
                     topCard.add(computerHand.get(i));
							//computerPlaying.add(computerHand.get(i));
                     computerHand.remove(i - e);
							e++;
                  }
               
					}
					computer.setHand(computerHand.size());
               playersTurn = true; 
            }
				//if the computer has more than zero of the matching suit and none of the matching rank it will play a matching suit
            else if(occurrencesSuit > 0){ 
					//System.out.println("Testing 2");
					for(int i = 0; i < computerHand.size(); i++){
						int e = 0; 
						if(computerPlayed == false){    
							if(topCardSuit.equals(computerHand.get(i).getSuit())){
				            System.out.println("The computer is playing: " + computerHand.get(i).getSuit() + "  " + computerHand.get(i).getRank());
				            discardPile.add(topCard.get(0));
				            topCard.remove(0);
				            topCard.add(computerHand.get(i - e));
				            computerHand.remove(i - e);
								computerPlayed = true;
								e++;
				         }
						}
						else{
							break;
						}
							
						computer.setHand(computerHand.size());	
	            }
					playersTurn = true;
				}
				//if neither option of the above it will play an eight if possible
            else if(occurrencesEight > 0){
					//System.out.println("Testing 3");
               for(int i = 0; i < computerHandSize; i++){
						int e = 0;
						if(computerPlayed == false){
		            	if(eight == computerHand.get(i).getRank()) {
		               	System.out.println("The computer is playing: " + computerHand.get(i).getSuit() + "  " +computerHand.get(i).getRank());
		                  discardPile.add(topCard.get(0));
		                  topCard.remove(0);
		                  topCard.add(computerHand.get(i));
		                  computerHand.remove(i - e);
								e ++;
									//computerPlaying.add(computerHand.get(i));
							}
						}
						else{
							break;
						}	
					}
					computer.setHand(computerHand.size());
               playersTurn = true;
    			}        
				//if none of the options above, computer will draw
            else{
               System.out.println("Computer has drawn a card.");
               computerHand.add(deck.get(0));
               deck.remove(0);
            	for(int i = 0; i < (computerHandSize + 1); i ++){
      		      System.out.print(" " + (i + 1) + " (" + computerHand.get(i).getSuit() + " " + computerHand.get(i).getRank() + ") ");
      		   }
               drawThreeTimesComputer ++; // prevents computer from drawing more than the max number of cards in one round
               System.out.println(" ");
               //sets computer hand int size
      		   computer.setHand(computerHand.size());
            }
				//allows the computer to draw only three times
            if(drawThreeTimesComputer == 3){
               playersTurn = true;
               System.out.println("Computer has drawn three times, passing turn.");
            }
//         		 //prints out the computers hand
//          	System.out.println("Computer's Hand: " );
//    			for(int i = 0; i < computerHand.size() ; i ++){
//    			   System.out.print(" " + (i + 1) + " (" + computerHand.get(i).getSuit() + " " + computerHand.get(i).getRank() + ") ");
//    			}          
			}
		}
		if(tester.getHand() == 0){
			System.out.println("Congrats Player you have won!");
		}
		else{
			System.out.println("Player you have lost.");
		}
	}
}