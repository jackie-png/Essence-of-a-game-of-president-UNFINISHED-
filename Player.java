import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeSet;
import java.util.Iterator;

import javax.swing.*;

class InvalidComboException extends IllegalArgumentException { // exception thrown when an invalid combo has been played
    public InvalidComboException(String message){
        super(message);
    }
}

public class Player extends JPanel implements MouseListener, KeyListener{
    /*
     * represents the player
     * handles placing player hand, combo identifying and combo comparing 
     */

    ArrayList<Card>hand; // stores card panels themselves
    ArrayList<String> out;
    String name;
    TreeSet<Card> cardsOnTable = new TreeSet<Card>(new compareCards()); // represents cards currently on the table
    TreeSet<Card> cardsPlayed; // represents cards that are going to be played
    public static int spacing = 0; // card placing spacing 
    boolean turnComplete = false; // if the player's turn has been fully complete
    boolean turnPassed = false;
    Table table = new Table(); // the table

    public void clearTable(boolean returnHand){ // clear's the player's selected hand

        if (returnHand){ // if cards should be returned to the player's hand 

            for (Card panel: cardsPlayed){ // places the cards back 
                panel.placePanel(panel.origPositionX, panel.origPositionY);
                panel.selected = false;
                System.out.println(panel.toString()+ " cleared");
            }
            cardsPlayed.clear();
            out.clear();
            spacing = 0;  
        } else { // remove the cards from the player's hand 
            cardsOnTable = (TreeSet<Card>)cardsPlayed.clone();            
            Iterator<Card> iter = cardsPlayed.iterator();

            while (iter.hasNext()){ // removes the cards from the player
                Card panel = iter.next(); 
                this.remove(panel);
                panel.selected = false;
                this.getRootPane().revalidate();
                this.getRootPane().repaint();
                System.out.println(panel.toString() + " cleared");                
            }
            cardsPlayed.clear();
            out.clear();

            spacing = 0;
        }
    }

    public static boolean nonComboChecker(TreeSet<Card> hand) throws InvalidComboException{ // for doubles, triples, quadruples, singles
        /*
         * checks for doubles and triples if they are played(not 5 cards)
         * (Move to game frame)
         */
        ArrayList<Card>combo = new ArrayList<Card>(hand);
        
        if (combo.size() == 2){ // if the given hand has two cards 
            if (combo.get(0).value == combo.get(1).value){
                System.out.println(hand + " is a double");
                return true;
            } else {
                throw new InvalidComboException("Not a double");
            }
        } else if (combo.size() == 3){ // if the given hand has three cards
            if (combo.get(0).value == combo.get(1).value && combo.get(1).value == combo.get(2).value){
                return true;
            } else {
                throw new InvalidComboException("Not a triple");
            }
        } else if (combo.size() == 4){ // if the given hand has four cards 
            if(combo.get(0).value == combo.get(1).value && combo.get(1).value == combo.get(2).value && combo.get(2).value == combo.get(3).value){
                return true;
            } else {
                throw new InvalidComboException("Not a quad");
            }
        } 
        return false;

    }

    public static Combo comboIdentifier(TreeSet<Card> hand) throws InvalidComboException{
        /*
         * checks what combo has been played
         */

        ArrayList<Card> convertedHand = new ArrayList<Card>(hand);
        boolean isFlush = true; // a check to see if combo is a flush
        boolean isStreight = true; // a check if the combo is a streight
        
        //check if all card's suites are the same for a flush

        int counter = 0;
        for (Card check: convertedHand){
            Card trackerCard = check;
            for (Card card: convertedHand){
                if (trackerCard.equals(card)){
                    counter++;
                }
            }
            if (counter == 4){
                return new Combo(hand, Combo.comboTypes.Bomb);
            } else {
                System.out.println("not yet");
                counter = 0;
            }
        }

        for (Card card: convertedHand){ // checks if hand is a flush(if all cardes have same suite)
            Card firstCard = hand.first();
            if(!firstCard.suite.equals(card.suite)){
                isFlush = false;
            }
        }

        if (isFlush == true){

            int checkValue = hand.first().value; // chekc if it is a streight flush
            for (int i = 1; i < convertedHand.size(); i++){
                if(convertedHand.get(i).value-1 != checkValue){
                    isStreight = false;
                } else {
                    checkValue = convertedHand.get(i).value;
                }
                
            }
            if (isStreight){
                if (hand.first().value == 8 && hand.last().value == 12){
                    return new Combo(hand, Combo.comboTypes.Royal_Flush);
                }
                return new Combo(hand, Combo.comboTypes.Streight_Flush);
            } else {
                return new Combo(hand, Combo.comboTypes.Flush);
            }
            
        } else {
            // check if it is a regular streight
            int checkValue = hand.first().value;
            for (int i = 1; i < convertedHand.size(); i++){
                if(convertedHand.get(i).value-1 != checkValue){
                    isStreight = false;
                }
                checkValue = convertedHand.get(i).value;
            }
            if (isStreight){
                return new Combo(hand, Combo.comboTypes.Streight);
            } else {

                //check if it is a full house
                counter = 0;
                int tripleValue = 0;
                for (Card check: convertedHand){
                    Card trackerCard = check;
                    for (Card card: convertedHand){
                        if (trackerCard.equals(card)){
                            counter++;
                        }
                    }
                    if (counter == 3){
                        tripleValue = trackerCard.value;
                        break;
                    } else {
                        System.out.println("not yet");
                        counter = 0;
                    }
                }

                if (tripleValue != 0){
                    ArrayList<Card> temp = new ArrayList<Card>();
                    for (Card card: convertedHand){
                        if(card.value != tripleValue){
                            temp.add(card);
                        }
                    }

                    if (temp.get(0).value == temp.get(1).value){
                        return new Combo(hand,Combo.comboTypes.Full_House);
                    } else {
                        throw new InvalidComboException("Invalid Combo");
                    }
                } else {
                    throw new InvalidComboException("Invalid Combo");
                }
            }
        }
        
    }

    
    class compareCards implements Comparator<Card>{ // compares cards
        public int compare(Card card1, Card card2){
            if (card1.value > card2.value){ // checks the value first 
                return 1;
            } else if (card1.value < card2.value){
                return -1;
            } else { // if the values are the same, check the suite of the card
                if (card1.suite.getPriority() > card2.suite.getPriority()){
                    return 1;
                } else if (card1.suite.getPriority() < card2.suite.getPriority()){
                    return -1;
                } else {
                    return 0;
                }
            }
        }
    }

    public Player(ArrayList<Card> hand, String name){ // player panel starts here

        this.hand = hand; // player's hand 
        this.name = name;
        cardsPlayed = new TreeSet<Card>(new compareCards()); // cards that have been selected to be played 
        out = new ArrayList<String>(); //out cards name list 

        // setting the bounds of the player panel
        this.setBounds(0,0,1280,757);
        this.setLayout(null);
        this.addKeyListener(this); // adds t akey listener to the panel 
        this.setFocusable(true);


        this.add(table); // adds a table to display the cards on the table

        JLabel namePlacement = new JLabel(name);
        namePlacement.setBounds(200, 600, 100, 100);
        namePlacement.setVisible(true);
        this.add(namePlacement);


        for (int i = 0; i < this.hand.size(); i++){ // places cards into player's hand
            this.hand.get(i).placePanel(900-(i*40),620);
            this.hand.get(i).origPositionX = 900-(i*40);
            this.hand.get(i).origPositionY = 620;
            this.hand.get(i).addMouseListener(this);
            this.add(this.hand.get(i));
        }
        
        System.out.println(this.getComponentCount());
        System.out.println("cards on table: "+cardsOnTable);
        this.setOpaque(false);
        this.setVisible(true);
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        // when you click the mouse (press and release)
    }

    @Override
    public void mousePressed(MouseEvent e) {
        /*
         * when you click on the card/component
         */

        Card comp = (Card)e.getComponent(); // this gets what component has been pressed on 
        //System.out.println("press");


        if(!comp.selected && cardsPlayed.size() < 5){ // if card pressed isnt selected and there is less than 5 cards on the table, add it to the cardsPlayed list 
            cardsPlayed.add(comp);
            out.add(comp.name);
            comp.placePanel(410+spacing, 450);
            spacing += 100;
            comp.selected = true;
            this.setVisible(true);

        } else if(comp.selected == true){ // if the card has been deselected, return it back to the player's hand 

            clearTable(true);
            System.out.println(comp.origPositionX+ " " + comp.origPositionY);
            this.setVisible(true);
            
        }
        System.out.println("Space: "+spacing);
        
        System.out.println("L: "+comp.getX()+","+comp.getY());        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // when you release the mouse from being pressed 
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        /*
         * when you enter a card's area, it will pop up saying you have hovered over it
         */

        Card comp = (Card)e.getComponent(); // what compoenent has been entered 
        //System.out.println("hello");
        //System.out.println(comp.selected);
        //System.out.println(comp.getWidth() + " " + comp.getHeight()); // width, height of panel bounds 

        if(!comp.selected){ // if it is not selected raise it a little so that you know youre hovering over it 
            comp.placePanel(comp.origPositionX, comp.origPositionY - 20);
        }
      System.out.println(comp.getX() +","+ comp.getY() + ", " + comp.toString());
    }

    @Override
    public void mouseExited(MouseEvent e) {
        /*
         * when you stop hovering over a card
         */
        //System.out.println("Goodbye");
        Card comp = (Card)e.getComponent(); // what component did you just leave
        //System.out.println(comp.getWidth() + " " + comp.getHeight());

        if(!comp.selected){ // return thge card to its original position
            comp.placePanel(comp.origPositionX, comp.origPositionY);
        }
       System.out.println(comp.getX() +","+ comp.getY() + "width,height : " + comp.getWidth() + "," + comp.getHeight());        
    }


    @Override
    public void keyTyped(KeyEvent e) {
        // when you press and release a key
    }


    @Override
    public void keyPressed(KeyEvent e) {
        /*
         * when you press a key on the keyboard
         */
        System.out.println(e.getKeyCode());

        if(e.getKeyCode() == 10){ // keyCode 10 is the enter key (if the key pressed is the enter key)
            System.out.println(cardsPlayed.toString());

            if(cardsPlayed.size() == 0){
                turnPassed = true;
                turnComplete = true;
            } else {
                try {
                    if (cardsOnTable.size() == 0){ // if there are no cards on the table, just put the player's cardsPlayed list on the table

                        if(cardsPlayed.size() == 5){ // check if the 5 cards played is a valid combo 
                            Combo combo = comboIdentifier(cardsPlayed);
                            System.out.println(combo.toString());
                            cardsOnTable = combo.comboHand;
                            turnComplete = true;
                            clearTable(false);

                        } else if (nonComboChecker(cardsPlayed) == true){ // if it is not 5 cards then check if it is a valid double, triple, quad
                            cardsOnTable = (TreeSet<Card>)cardsPlayed.clone();
                            turnComplete = true;
                            clearTable(false);
                        } else { // just place it on the table at this point, single cards 
                            cardsOnTable = (TreeSet<Card>)cardsPlayed.clone();
                            clearTable(false);
                            turnComplete = true; 
                        }


                    } else if(cardsPlayed.size() == 5 && cardsOnTable.size() == 5){ //if there are 5 cards played, find out what combo it is
                        System.out.println("5 cards");

                        Combo combo1 = comboIdentifier(cardsPlayed);
                        Combo comboOnTable = comboIdentifier(cardsOnTable);
                        if(comboOnTable.compareTo(combo1) < 1){ //compares the cards on the table to player's played cards

                            //if the player is higher, replace the cards on table with this player's hand
                            cardsOnTable = (TreeSet<Card>)cardsPlayed.clone();      
                            System.out.println("valid");                  
                            clearTable(false);
                            turnComplete = true;

                        } else { // they played a lower combo
                            System.out.println("invalid");
                            clearTable(true);
                            turnComplete = false;
                        }

                    }else if (nonComboChecker(cardsPlayed) == true && cardsOnTable.size() == cardsPlayed.size()){ // check if it is a valid double, triple, quad

                        if(cardsPlayed.last().compareTo(cardsOnTable.last()) >=1){//since they are a double, triple, quad, just check the last cards
                            //if the player's hand is higher than the table's hand, replace the table's cards 
                            cardsOnTable = (TreeSet<Card>)cardsPlayed.clone();
                            clearTable(false);
                            turnComplete = true;
                        } else { // you played a lower double, triple, quad
                            clearTable(true);
                            turnComplete = false;
                        }
                        
                    } else if (cardsPlayed.size() == cardsOnTable.size()){
                        if (cardsPlayed.last().compareTo(cardsOnTable.last()) >=1){ 
                            // if the single card on table is less than the one being played, replace it 
                            cardsOnTable = (TreeSet<Card>)cardsPlayed.clone();
                            clearTable(false);
                            turnComplete = true;
                        } else { // you played a lower card 
                            clearTable(true);
                            turnComplete = false; 
                        }
                    } else { // you played something you can't play
                        clearTable(true);
                        turnComplete = false;                    
                    }
                } catch (InvalidComboException ex) {// you played something you cant play 
                    System.out.println(ex.getMessage());
                    clearTable(true);
                    turnComplete = false;    
                }          

                System.out.println("woop");
            }
    
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
    }


}