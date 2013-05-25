import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class HangmanSecond  extends JFrame {
  private JLabel line1;
	private JLabel line2;
	JTextField[][] cells;
	static String string = null;
	
	String[] text = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o","p", "q", "r", "s", "t", "w", "u", "v","x", "y", "z", "Restart", "Start"};
	JButton[] button = new JButton[28];
	

	
	HangmanSecond() {
		setSize(800,400);
		line1 = new JLabel("0");
		line2 = new JLabel("0");

		
		
		JPanel mainPanel = new JPanel(new BorderLayout());
		JPanel gamePanel = new JPanel();
		JPanel alphabet = new JPanel(new GridLayout(7, 4));
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


		
	
		for(int i = 0; i<button.length; i++)
			{
				button[i] = new JButton(text[i]);
		        button[i].addActionListener(new KeyActionListener());
				alphabet.add (button[i]);	
			}


	}

	public class KeyActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
		for(int i = 0; i<button.length; i++){
			if (event.getSource() == button[i])
                	line2.setText(text[i]);
			}
			
		for(int i = 0; i<button.length; i++){
			if (event.getSource() == button[i])
			button[i].setEnabled(false);

			}

			
		}
	}
	
	public static void main(String[] args) {
		HangmanSecond example = new HangmanSecond();
		example.setVisible(true);

	}

}
