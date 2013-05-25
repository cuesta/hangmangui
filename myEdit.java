import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class MyEdit extends JFrame {
private JLabel line1;
private JLabel line2;
JTextField[][] cells;
static String string = null;

String[] letters = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o","p", "q", "r", "s", "t", "u", "v","w", x", "y", "z"};
JButton[] letterButtons = new JButton[26];



MyEdit() {
setSize(800,400);
line1 = new JLabel("0");
line2 = new JLabel("0");

//places a title on the frame
setTitle ("Hangman");

//sets the location of the frame to centered on the screen
setLocationRelativeTo (null);

JPanel mainPanel = new JPanel(new BorderLayout());
JPanel gamePanel = new JPanel();
JPanel alphabet = new JPanel(new GridLayout(5, 6));
line1.setText("Keyboard Guess: ");
line2.setText(" ");


//add sub-panels to main panel
mainPanel.add(gamePanel, BorderLayout.WEST);
mainPanel.add(alphabet, BorderLayout.EAST);


//add main panel to frame
add(mainPanel);


//create a panel for game output
gamePanel.add(line1);
gamePanel.add(line2);




for(int i = 0; i<letterButtons.length; i++)
{
letterButtons[i] = new JButton(letters[i]);
letterButtons[i].addActionListener(new KeyActionListener());
letterButtons[i].setEnabled(true);
letterButtons[i].setFocusPainted(false);
alphabet.add (letterButtons[i]);
}


}

public class KeyActionListener implements ActionListener
{
public void actionPerformed(ActionEvent event)
{

for(int i = 0; i<letterButtons.length; i++){
if (event.getSource() == letterButtons[i])
                 line2.setText(letters[i]);

}

for(int i = 0; i<letterButtons.length; i++){
if (event.getSource() == letterButtons[i])
letterButtons[i].setEnabled(false);

}


}
}

public static void main(String[] args) {
MyEdit example = new MyEdit();
example.setVisible(true);

}

}


