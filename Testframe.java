
// man now thats a lot of imports hehe 
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

//THIS IS NOT USED FOR THE ACTUAL GAME THE ACTUAL GAEM IS RAN ON Gameframe.java


class Testframe extends JFrame implements MouseListener, KeyListener{

    /*
     * change of plans lad
     * we moving everything here to a jpanel to see how that goes
     * just add this to a jframe and see if it works
     * otherwise oof youll have to redo literally everything here 
     * it'll just make it more intuitive to switch between players when we get there eventually
     */

     
    ArrayList<String>cardImages = new ArrayList<String>();
    ArrayList<Card>list; // stores card panels themselves
    ArrayList<Card>ans; // testing output purposes
    private static int spacing = 0; // card placing spacing 
    public boolean boo = false;

    public void cardImageAdd(){ // just adds card images to cards 
        cardImages.add("3_diamonds.png");
        cardImages.add("3_clubs.png");
        cardImages.add("3_hearts.png");
        cardImages.add("3_spades.png");
    }
    public boolean checker(ArrayList<Card> in){ // test input from keypressed 
        for (Card panel : in){
            if (panel.name.equals("3") || panel.name.equals("8")){
                return true;
            }
        }
        return false;
    }

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

    public Testframe(){ // actual game itself 
        list = new ArrayList<Card>();
        cardImageAdd();
        ans = new ArrayList<Card>();
        int a = 0;
        for (int i = 0; i < 13; i++){
            //list.add(new Card(Integer.toString(i), cardImages.get(i%4)));
        }

        this.setSize(1280, 757);  // window size (1280x720)
        this.setTitle("bootleg president");
        this.setIconImage(new ImageIcon("3_spades.png").getImage());
        this.setResizable(false); //you can't resize the window im too lazy to find a way to resize it shush
        this.setLayout(new FlowLayout()); // i dunno if this actually does anything but ill just pretend it does
        this.setContentPane(new backgrd(new ImageIcon("Game_Table_3.jpg").getImage())); // adds background to jframe
        this.addKeyListener(this); // adds a key listener to jframe, detects if any keys have been pressed (methods at bottom)

        for (int i = 0; i < list.size(); i++){ // places cards 
            list.get(i).placePanel(400+(i*30),620);
            list.get(i).origPositionX = 400+(i*30);
            list.get(i).origPositionY = 620;
            list.get(i).addMouseListener(this);
            this.add(list.get(i));
        }
        this.setVisible(true); // makes window visible
        System.out.println(this.getLayout());
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);// closes program when you click the x    
    }
        
    
    @Override
    public void mouseClicked(MouseEvent e) {
        // when you click the mouse (press and release)
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // when you press on the mouse 
        Card comp = (Card)e.getComponent(); // this gets what component has been pressed on 
        //System.out.println("press");
        System.out.println("Space: "+spacing);


        if(!comp.selected && ans.size() < 5){ // if card pressed isnt selected and there is less than 5 cards on the table, add it to the table
            ans.add(comp);
            comp.placePanel(488+spacing, 300);
            spacing += 60;
            comp.selected = true;
            this.setVisible(true);

        } else if(comp.selected == true){ // if the card has been deselected, return it to the original spot

            //instead of duplicating the card, just move the card selected to the middle and if its deselected move it back to the original spot
            comp.selected = false;
            ans.remove(comp);
            comp.setBounds(comp.origPositionX, comp.origPositionY-20, comp.ogBoundWid, comp.ogBoundHei);
            //System.out.println(comp.ogBoundWid+ " " + comp.ogBoundHei);
            spacing-=60;
            this.setVisible(true); // this is some janky moving not gonna lie 
            
        }
        
        //System.out.println(comp.getX()+","+comp.getY());        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // when you release the mouse from being pressed 
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // when mouse has enter a card
        Card comp = (Card)e.getComponent(); // what compoenent has been entered 
        //System.out.println("hello");
        //System.out.println(comp.selected);
        //System.out.println(comp.getWidth() + " " + comp.getHeight()); // width, height of panel bounds 

        if(!comp.selected){ // if it is not selected raise it a little so that you know youre hovering over it 
            comp.setBounds(comp.getX(), comp.getY()-20, comp.getWidth(), comp.getHeight());
        }
       // System.out.println(comp.getX() +","+ comp.getY());
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO when you stop hovering over a component
        //System.out.println("Goodbye");
        Card comp = (Card)e.getComponent(); // what component did you just leave
        //System.out.println(comp.getWidth() + " " + comp.getHeight());

        if(!comp.selected){ // return thge card to its original position
            comp.setBounds(comp.getX(), comp.getY()+20, comp.getWidth(), comp.getHeight());
        }
        //System.out.println(comp.getX() +","+ comp.getY());        
    }


    @Override
    public void keyTyped(KeyEvent e) {
        // when you press and release a key
    }


    @Override
    public void keyPressed(KeyEvent e) {
        // hen you press on a key
        if(e.getKeyCode() == 10){ // keyCode 10 is the enter key (if the key pressed is the enter key)
            if(checker(ans) == true){
                //return cards to hand(represents combo was not ok go back)
                System.out.println(false);
                for (Card panel: ans){
                    this.remove(panel);
                    boo = true;
                    panel.selected = false;
                    this.getContentPane().revalidate();
                    this.getContentPane().repaint();
                }
                ans.clear();
                spacing = 0;
            } else {
                //remove cards on table(represents combo was ok)
                System.out.println("true");
                for (Card panel: ans){
                    panel.placePanel(panel.origPositionX, panel.origPositionY);
                    panel.selected = false;
                    boo = false;
                }
                ans.clear();
                spacing = 0;                
            }
        } else if(e.getKeyCode() == 16){//shift
            System.out.println("h");
            this.dispose();
        }
    }


    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }


}