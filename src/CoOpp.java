import java.util.Random;
import java.util.ArrayList;
import java.util.List;
public class CoOpp implements Bot {
	private Random generator = new Random();
    /** Plays the strategy modified constant by pujara in A Cognitive Hierarchy Model Applied to the Lemonade Game {5}.
      * 
      * @param player1LastMove the action that was selected by Player 1 on the
      *                        last round.
      * @param player2LastMove the action that was selected by Player 2 on the
      *                        last round.
      * 
      * @return the next action to play.
      */
	
	List<Integer> scores = new ArrayList<Integer>();
    List<int []>  otherMoves = new ArrayList<int []> ();
	private int totalScore;
	private int lastPosition = 12;
    boolean mainGame = false;
    boolean sandwichingp1 = false;
    boolean sandwichingp2 = false;
    boolean p1Across = false;
    boolean p2Across = false;
    boolean signaling;
    public int getNextMove(int player1LastMove, int player2LastMove) {
        //add the last players moves to our list of recorded moves, remove moves older than 10
        int [] others = new int [2];
        others[0] = player1LastMove;
        others[1] = player2LastMove;
        otherMoves.add(others);
    	int size = otherMoves.size() -1;
        int rand = generator.nextInt(2);
       
        //we have enough turns to look for sticking
        if (size >= 3)
            mainGame = true;
        
        //in the sandwhich process
        if (sandwichingp1) {
            //signaling that we're trying to sandwich
            if (signaling){
                //are they still sticking?
                if (lastPosition == (player1LastMove + 1) % 12){
                    //go for the sandwich
                    signaling = false;
					if (lastPosition % 12 == 0)
						return 1;
                    return lastPosition % 12;
                }
                else {
                    //leave and proceed with across
                    signaling = false;
                    sandwichingp1 = false;
                }
            }
            //already signaled, going for the actual sandwich, did p3 pick up?
            else if ((player2LastMove == player1LastMove - 1) && (lastPosition == player1LastMove + 1))
                //we have made a sandwich, keep going
                return lastPosition;
            else{
                //no sandwich, we're leaving, proceed with across
                sandwichingp1 = false;
            }    
        }
        
        //in the sandwhich process
        if (sandwichingp2) {
            //signaling that we're trying to sandwich
            if (signaling){
                //are they still sticking?
                if (lastPosition == (player2LastMove + 1) % 12){
                    //go for the sandwich
                    signaling = false;
					if (lastPosition % 12 == 0)
						return 1;
                    return lastPosition % 12;
                }
                else {
                    //leave and proceed with across
                    signaling = false;
                    sandwichingp1 = false;
                }
            }
            //already signaled, going for the actual sandwich, did p3 pick up?
            else if ((player1LastMove == player2LastMove - 1) && (lastPosition == player2LastMove + 1)){
                //we have made a sandwich, keep going
					if (lastPosition % 12 == 0)
						return 1;
                return lastPosition % 12;
				}
            else{
                //no sandwich, we're leaving, proceed with across
                sandwichingp2 = false;
            }    
        }
        
        if (mainGame){
            //if player1 is sticking (has stayed in the same place for three turns), try to sandwich them
        	if((otherMoves.get(size)[0] == otherMoves.get(size - 1)[0]) && (otherMoves.get(size - 1)[0] == otherMoves.get(size - 2)[0])){
                sandwichingp1 = true;
                signaling = true;
                lastPosition = (player1LastMove + 1) % 12;
				if(lastPosition % 12 == 0)
					return 1;
        		return lastPosition % 12 ;
        	}
            //if player2 is sticking (has stayed in the same place for three turns), try to sandwich them
            if((otherMoves.get(size)[1] == otherMoves.get(size - 1)[1]) && (otherMoves.get(size - 1)[1] == otherMoves.get(size - 2)[1])) {
                sandwichingp2 = true;
                signaling = true;
                lastPosition = (player2LastMove + 1) % 12;
					if((player2LastMove + 1) % 12 == 0)
						return 1;
            	return (player2LastMove + 1) % 12;
            }
    	}
        
        //see if accross worked last turn
        if (p1Across) {
            if ((player1LastMove + 6) % 12 == lastPosition){
                //they are sticking
				if(lastPosition % 12 == 0)
					return 1;
                 return lastPosition % 12;
            }
            else {
                p1Across = false;
            }
        }

        if (p2Across) {
            if ((player2LastMove + 6) % 12 == lastPosition){
                //they are sticking, continue to play across
				if(lastPosition % 12 == 0)
					return 1;
                 return lastPosition % 12;
            }
            else {
                p2Across = false;
            }
        }
        
        //look for sandwich opportunity, make sure to sandwich the sticky one, find out who's been there longest
        //order is p1, p2
        if (player1LastMove + 1 == player2LastMove){
            if(otherMoves.get(size)[0] == player1LastMove){
                //player1 is sticky, go to his left
                lastPosition = player1LastMove - 1;
					if(lastPosition % 12 == 0)
						return 1;
                return lastPosition % 12;
            }
            else if(otherMoves.get(size)[1] == player2LastMove){
                lastPosition = player2LastMove + 1;
					if(lastPosition % 12 == 0)
						return 1;
                return lastPosition % 12; 
            }
        }
        //order is p2, p1
        if (player1LastMove - 1 == player2LastMove){
            if(otherMoves.get(size)[0] == player1LastMove){
                //player1 is sticky, go to his left
                lastPosition = player1LastMove + 1;
					if(lastPosition % 12 == 0)
						return 1;
                return lastPosition % 12;
            }
            else if(otherMoves.get(size)[1] == player2LastMove){
                lastPosition = player2LastMove - 1;
					if(lastPosition % 12 == 0)
						return 1;
                return lastPosition % 12; 
            }
        }      
        
        //flip a coin to see who to pair with
        if (rand == 1){
            lastPosition = (player1LastMove + 6) % 12; 
            p1Across = true; 
        } 
        else{
            lastPosition = (player2LastMove + 6) % 12;
            p2Across = true; 
        }
		if(lastPosition % 12 == 0)
			return 1;
    	return lastPosition % 12;
    }
}
