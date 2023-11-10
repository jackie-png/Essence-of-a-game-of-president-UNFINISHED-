import java.awt.Color;
import javax.swing.*;




public class Card extends JPanel{

    public static enum Suites { // enum of card suites 
        Spades(4),Hearts(3),Clubs(2),Diamonds(1); // spades, hearts, clubs, diamonds

        private final int priority;

        Suites(int priority){
            this.priority = priority;
        }

        public int getPriority(){ // for getting the suite of the card 
            return priority;
        }
    }

    String name;
    JLabel cardIcon;
    String imageName;
    boolean selected = false; // if the card had been selected to be played
    public int origPositionX;
    public int origPositionY;
    public int ogBoundWid;
    public int ogBoundHei;
    Suites suite;
    public int value;


    public Card(String name, Card.Suites suite, int value, String image){
        this.value = value;
        this.suite = suite;
        this.name = name;
        this.imageName = image;
        this.setBounds(100, 100, 67, 150);
        this.ogBoundWid = 67;
        this.ogBoundHei = 150;
        ImageIcon cardImg = new ImageIcon(image); // grabs the picture of the card from the cards folder
        cardIcon = new JLabel();
        cardIcon.setBounds(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        cardIcon.setIcon(cardImg);
        this.setBackground(Color.BLUE);
        this.add(cardIcon);
        this.setOpaque(false);
        this.setVisible(true);
    }



    public void placePanel (int x, int y){ // for moving the cards around easier
        this.setBounds(x, y, ogBoundWid, ogBoundHei);
    }

    public boolean equals(Card other){ // 2 cards are equals if the have the same value
        if (this.value == other.value){
            return true;
        } else {
            return false;
        }
    }

    public int compareTo(Card other){ // comparing cards 
        if(this.value > other.value){ // check the value of the cards first
            return 1;
        } else if (this.value < other.value){
            return -1;
        } else { // if the values are the same then check the suite 
            if(this.suite.priority > other.suite.getPriority()){
            return 1;
            } else if(this.suite.priority < other.suite.getPriority()){
                return -1;
            } else {
                return 0;
            } 
        }        

    }

    public Card copy(){ // for copying cards 
        return new Card(this.name, this.suite,this.value, this.imageName);
    }

    public String toString(){ // to string 
        return this.name + " of " + this.suite; 
    }

}