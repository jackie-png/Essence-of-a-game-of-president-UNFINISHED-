import java.awt.CardLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.NoRouteToHostException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import java.util.Stack;
import java.util.TreeSet;

public class Gameframe extends JFrame implements KeyListener{
    /*
    * this represents the game window at start up 
    handles players and changing between players  
    */
    ArrayList<Player> players = new ArrayList<Player>(); // list of players 
    ArrayList<String> playerNames = new ArrayList<String>();
    Stack<Card> deck; // deck 
    Stack<Card> orgDeck; // the original deck 
    TreeSet<Card> onTable = new TreeSet<Card>(new compareCards()); // cards that are currently on the table (used by table.java to place cards down)
    int numCard; // number of cards per player
    int space = 0; // spacing between cards when placed on table
    int playerCount = 0; // how many players are playing
    CardLayout screen = new CardLayout(); // cardlayout will be used as layout manager, each player is a card in the card layout
    ArrayList<TreeSet<Card>>playerHands = new ArrayList<TreeSet<Card>>();

    class backgrd extends JComponent { // background image, shows the game board 
        private Image image;
        public backgrd(Image image) {
            this.image = image;
        }
        @Override
        protected void paintComponent(Graphics g) {
            // TODO Auto-generated method stub
            super.paintComponent(g);
            g.drawImage(image,0,0,1280,720,this);
        }
    }

    class compareCards implements Comparator<Card>{ // compares cards
        public int compare(Card card1, Card card2){
            if (card1.value > card2.value){
                return 1;
            } else if (card1.value < card2.value){
                return -1;
            } else { // if both cards have the same value, check the suite
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
    

    public static void shuffle(Stack<Card>deck, int cycles){
        /*
         * shuffles the card deck by picking 2 random cards and switching their places 
         */
        Random random = new Random();
        ArrayList<Card>convertedDeck = new ArrayList<Card>(deck);

        for (int i = 0; i < cycles*10; i++){
            //picks 2 cards 
            int place1 = random.nextInt(deck.size());
            int place2 = random.nextInt(deck.size());

            Card card1 = convertedDeck.get(place1);
            Card card2 = convertedDeck.get(place2);

            //moves to new location
            convertedDeck.remove(place1);
            convertedDeck.add(place1, card2);

            convertedDeck.remove(place2);
            convertedDeck.add(place2,card1);
        }

        deck.clear();
        for (Card card : convertedDeck){
            deck.push(card);
        }
        

    }


    public Gameframe(Stack<Card> deck){
        this.setSize(1280, 757);  // window size (1280x720)
        this.setTitle("bootleg president"); // yes bootleg president
        
        this.setIconImage(new ImageIcon("Game_Images\\Cards\\3_spades.png").getImage()); // game icon
        this.setResizable(false); //you can't resize the window im too lazy to find a way to resize it shush
        this.setContentPane(new backgrd(new ImageIcon("Game_Images\\Backgrounds\\Game_Table_v2.png").getImage())); // adds background to jframe
        this.setLayout(screen); // i dunno if this actually does anything but ill just pretend it does

        this.deck = deck;
        this.orgDeck = deck;
        shuffle(this.deck, 10);

        playerNames.add("North");
        playerNames.add("East");
        playerNames.add("South");        
        playerNames.add("West");

        for (int i = 0; i < 4; i++){
            playerHands.add(new TreeSet<Card>(new compareCards()));
        }

        int p = 0;        
        while (!deck.empty()){ // distribute cards into player hands
            if(p == playerHands.size()){
                p=0;
            }            
            playerHands.get(p).add(deck.pop());
            p++;
        }
        for (int i = 0; i < 4 ; i++){
            players.add(new Player(new ArrayList<Card>(playerHands.get(i)), playerNames.get(i))); // creates new player with hand
            this.add(players.get(i),players.get(i).name);
            players.get(i).addKeyListener(this); // adds key listener to player
            players.get(i).setFocusable(true);
        }





        System.out.println(this.getRootPane().getContentPane().getLayout()); // oh my god, not: when you change the layout you are changing the frame's rootpane's contentpane's layout
        screen.show(this.getRootPane().getContentPane(),"North"); // show the first player
        this.setVisible(true); // makes window visible
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);// closes program when you click the x

    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if(e.getKeyCode()==16){ // if shift key pressed 
            players.get(playerCount).table.removeAll(); // remove all cards on the table
            System.out.println(playerCount);
            players.get(playerCount).spacing = 0; // reset the card spacing back to 0(cards plaing on the players card hand)

            for (Card panel: players.get(playerCount).cardsPlayed){ // for all cards that the player's played
                panel.placePanel(panel.origPositionX, panel.origPositionY); // return the card back to the hand
                panel.selected = false; // card becomes not selected
            }

            players.get(playerCount).cardsPlayed.clear(); // clear the card's played list 
            if(playerCount < 3)
                playerCount++; // increment to the next player
            else {
                playerCount = 0;
            }            

            screen.next(this.getRootPane().getContentPane());
            players.get(playerCount).requestFocusInWindow(); // refocues the window to the next player

        } else if (e.getKeyCode() == 10){ // if the enter key has been pressed 

            if (players.get(playerCount).turnComplete == true){ // if they had played a legit hand 
                
                players.get(playerCount).turnComplete = false;
                System.out.println("huh");
                System.out.println(playerCount);
                players.get(playerCount).spacing = 0;
                players.get(playerCount).table.removeAll(); // removes all cards on the table currently 
                System.out.println(players.get(playerCount).cardsOnTable);
                onTable = (TreeSet<Card>)players.get(playerCount).cardsOnTable.clone(); // cards on table 

                for (Card panel: players.get(playerCount).cardsPlayed){ // return the cards back to the player's hand
                    panel.placePanel(panel.origPositionX, panel.origPositionY);
                    panel.selected = false;
                }


                players.get(playerCount).cardsPlayed.clear();
                playerCount = (playerCount+1)%4;

                System.out.println(playerCount);
                if(players.get(playerCount).turnPassed == true){
                    if(playerCount+1 > 4){
                        playerCount = 0;
                    } else {
                        playerCount++;
                    }
                }
                players.get(playerCount).cardsOnTable = (TreeSet<Card>)onTable.clone();
                for (Card card : players.get(playerCount).cardsOnTable){ //places the cards on the next player's table 
                    players.get(playerCount).table.add(card);
                    card.placePanel(328 + space, 163);   
                    card.selected = true;     
                    space+= 141;        
                }
                space = 0;
                if(players.get(playerCount-1).turnPassed == true){
                    screen.next(this.getRootPane().getContentPane());
                    screen.next(this.getRootPane().getContentPane());
                    players.get(playerCount).requestFocusInWindow();    
                } else {
                    screen.next(this.getRootPane().getContentPane());
                    players.get(playerCount).requestFocusInWindow();                        
                }

            }
        }
            
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
    }

}