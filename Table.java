
import javax.swing.JPanel;

public class Table extends JPanel{
    /*
     * this is the table where cards will be placed after they have been selected and played
     */
    public Table(){
        this.setBounds(0, 0, 1200, 368); // the table is only in the upper half of the window screen
        this.setOpaque(false);
        this.setLayout(null);
        this.setVisible(true);          
    }
}
