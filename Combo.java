
import java.util.ArrayList;
import java.util.TreeSet;
import java.lang.Comparable;

class Combo implements Comparable<Combo>{
    /*
     * a combo is made up of exclusivly 5 cards 
     */

    public TreeSet<Card> comboHand;
    comboTypes type;



    public static enum comboTypes{
        /*
         * enum of combo types
         * order: royal flush, streight flush, bomb, full house, flush, streight
         */
        Royal_Flush(6), Streight_Flush(5), Bomb(4), Full_House(3), Flush(2), Streight(1);

        private final int priority;

        comboTypes(int priority){
            this.priority = priority;
        }

        public int getPriority(){ // for getting the combo type priority rank
            return priority;
        }
    }

    public static int tripleQuadChecker(TreeSet<Card> hand, boolean mode){
        /*
         * checks for what value the triple/quad is by finding it in the hand 
         * True = Quadruples, False = Triples
         */
        ArrayList<Card>convertedHand = new ArrayList<Card>(hand);
        int counter = 0;
        int checker = 0;
        if (mode == true){
            checker = 4;
        } else {
            checker = 3;
        }
        
        for (Card check: convertedHand){ // checking if there is a triple/quad in hand 
            Card trackerCard = check;
            for (Card card: convertedHand){
                if (trackerCard.equals(card)){
                    counter++;
                }
            }
            if (counter == checker){ // if you found where the triple/quad is
                return trackerCard.value; // returns what the value of the triple/quad is 
            } else {
                counter = 0;
            }
        }
        return 0;
    }


    public Combo(TreeSet<Card>hand, Combo.comboTypes type){ //combo constructor
        comboHand = hand;
        this.type = type;

    }

    public int compareTo(Combo otherHand){
        /*
         * this compares which combo is higher/ which combo is lower 
         */
        if (this.type.getPriority() > otherHand.type.getPriority()){ // check if one combo is generally bigger than the other combo
            return 1;
        } else if (this.type.getPriority() < otherHand.type.getPriority()){
            return -1;
        } else { // when the combos are the same type of combo
            if (otherHand.type == Combo.comboTypes.Royal_Flush){ //royal flush checking 

                if (this.comboHand.first().suite.getPriority() > otherHand.comboHand.first().suite.getPriority()){// check which hand's suite is bigger
                    return 1;
                } else if (this.comboHand.first().suite.getPriority() < otherHand.comboHand.first().suite.getPriority()){
                    return -1;
                }

            } else if (otherHand.type == Combo.comboTypes.Streight_Flush){ // strieght flush checking

                if (this.comboHand.first().suite.getPriority() > otherHand.comboHand.first().suite.getPriority()){ // check which hand's suite is bigger
                    return 1;
                } else if (this.comboHand.first().suite.getPriority() < otherHand.comboHand.first().suite.getPriority()){
                    return -1;
                } else { //if they are the same suite then check which one is the bigger streight(check the first card of both hands)
                    if (this.comboHand.first().value > otherHand.comboHand.first().value){
                        return 1;
                    } else if (this.comboHand.first().value < otherHand.comboHand.first().value){
                        return -1;
                    }
                }

            } else if (otherHand.type == Combo.comboTypes.Bomb){ // bomb checking

                if (tripleQuadChecker(this.comboHand, true) > tripleQuadChecker(otherHand.comboHand, true)){ // check which quad is bigger
                    return 1;
                } else if (tripleQuadChecker(this.comboHand, true) < tripleQuadChecker(this.comboHand, true)){
                    return -1;
                }

            } else if (otherHand.type == Combo.comboTypes.Full_House){ // full house chekcing 

                if (tripleQuadChecker(this.comboHand, false) > tripleQuadChecker(otherHand.comboHand, false)){ // check which triple is bigger
                    return 1;
                } else if (tripleQuadChecker(this.comboHand, false) < tripleQuadChecker(this.comboHand, false)){
                    return -1;
                }

            } else if (otherHand.type == Combo.comboTypes.Flush){ // flush checking

                if (this.comboHand.first().suite.getPriority() > otherHand.comboHand.first().suite.getPriority()){ // check which flush's suite is bigger
                    return 1;
                } else if (this.comboHand.first().suite.getPriority() < otherHand.comboHand.first().suite.getPriority()){
                    return -1;
                }
            } else if (otherHand.type == Combo.comboTypes.Streight){//strieght checking

                if (this.comboHand.first().value > otherHand.comboHand.first().value){ // check which streight's starting value is bigger
                    return 1;
                } else if (this.comboHand.first().value > otherHand.comboHand.first().value){
                    return -1;
                }
            } else {
                return 0;
            }
            return 0;
        }
    }

    public String toString(){
        return comboHand + ": " + type; 
    }
}