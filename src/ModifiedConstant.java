import java.util.Random;
import java.util.ArrayList;
import java.util.List;
public class ModifiedConstant implements Bot {
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
	private int totalScore;
	private int lastPosition = 12;
    public int getNextMove(int player1LastMove, int player2LastMove) {
    	int size = scores.size() -1;
    	//if three scores are below seven, move randomly, or if it is the first round
    	if (size >= 3) {
	    	if((scores.get(size) < 7 && scores.get(size - 1) < 7 && scores.get(size - 2) < 7) || scores.isEmpty()) {
	    		int rand = generator.nextInt(12) + 1;
	    		//calculate what my score will be and add my score to my scores array
	    		scores.add(scoreRound(rand, player1LastMove, player2LastMove));
	    		lastPosition = rand;
	    		//gives back the random move
	    		return rand;
	    	}
    	}
    	//otherwise, stick
    	scores.add(scoreRound(lastPosition, player1LastMove, player2LastMove));
    	return lastPosition;
    }
    
    
	/** Scores the current round from the perspective of the first player.
	 * 
	 * @param action1 the action taken by the first player.
	 * @param action2 the action taken by the second player.
	 * @param action3 the action taken by the third player.
	 * 
	 * @return the payoff for player 1 on this round.
	 */
	static int scoreRound(int action1, int action2, int action3) {
	    if ((action1 == action2) && (action1 == action3))
	        return 8; // three-way tie
	    else if ((action1 == action2) || (action1 == action3)) {
	        return 6; // two-way tie
	    }
	    else {
	        int score = 0;
	        int i = action1;
	        while ((i != action2) && (i != action3)) { // score clockwise
	            i = (i % 12) + 1;
	            score += 1;
	        }
	        i = action1;
	        while ((i != action2) && (i != action3)) { // score anti-clockwise
	            i = (i-1 > 0) ? i-1 : 12;
	            score += 1;
	        }
	        return score;
	    }
	}
}
