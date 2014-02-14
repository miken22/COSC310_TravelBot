import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;
import javax.swing.plaf.basic.BasicBorders;
import opennlp.tools.util.InvalidFormatException;

/**
 * This class will create the GUI for the chat agent
 * and will send and recieve the I/O for the
 * conversation to the necessary classes.
 * 
 * @author Mike-Laptop
 *
 */
public class ChatBox{

	// Main frame components
	private JFrame frame;
	private Container c;
	private JScrollPane scroll1;
	private JScrollPane scroll2;
	private JTextArea convo;
	private JTextArea input;

	private JButton send;
	private JButton clear;
	
	private TravelAgent agent;
	
	public ChatBox(){
		
		convo = new JTextArea();
		input = new JTextArea();
		scroll1 = new JScrollPane(convo);
		scroll2 = new JScrollPane(input);
			
		send = new JButton("Send");
		clear = new JButton("Clear");
		send.addActionListener(new ButtonListener());
		clear.addActionListener(new ButtonListener());
		
		input.addKeyListener(new TextListener());
		
		buildFrame();
	}
	
	private void buildFrame(){
		
		agent = new TravelAgent();
		
		frame = new JFrame("TravelBot Chat Agency");
		frame.setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setSize(550, 450);
		c = frame.getContentPane();
		
		convo.setEditable(false);
		convo.setLineWrap(true);
		convo.setBorder(BasicBorders.getTextFieldBorder());
		convo.setBounds(2,2,frame.getWidth()-23,300);
		scroll1.setBounds(2,2,frame.getWidth()-23,300);
		scroll1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Chat History"));
		c.add(scroll1);
		
		input.setLineWrap(true);
		input.setBorder(BasicBorders.getTextFieldBorder());	
		input.setBounds(4, 305, frame.getWidth()-115, 80);
		scroll2.setBounds(4, 305, frame.getWidth()-115, 95);
		scroll2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Talk to TravelBot:"));
		c.add(scroll2);
		
		send.setBounds(frame.getWidth()-105,312,80,40);
		clear.setBounds(frame.getWidth()-105,355,80,40);
		c.add(send);
		c.add(clear);
		
		frame.repaint();
		convo.setText("TravelBot started at " + Utils.getCurrentDateFull() + "\r\n" + "Powered by Google" + "\r\n\r\n" +
		 "Travel Bot: " + agent.getGreeting()+ "\r\n");
	}
	
	public class TextListener implements KeyListener{

		@Override
		public void keyPressed(KeyEvent e) {
			// Identifies the user's use of return to submit a question.
			if(e.getKeyCode() == KeyEvent.VK_ENTER){
				e.consume();
				send.doClick();
			}
		}

		// Unused methods
		@Override
		public void keyReleased(KeyEvent arg0) {
		}

		@Override
		public void keyTyped(KeyEvent arg0) {	
		}
	}
	
	public class ButtonListener implements ActionListener{
		String in = "";
		String conversation = "";
		String out = "";
		
		@Override
		public void actionPerformed(ActionEvent e) {
			in = input.getText();
			// If user does not enter anything the chatbot will not do anything.
			if(e.getSource() == clear){
				input.setText("");
			} else if(in.length() == 0){
				return;
			} else {
				conversation = convo.getText();
				input.setText("");
				convo.setText(conversation + "\n\r" + "User: " + in);	// Prints user message
				out = agent.buildResponse(in);	// Use user input to determine response to display
				conversation = convo.getText();							
				convo.setText(conversation + out);						
			}
		}
	}
	
	public static void main(String[] args){
		// Initialize parser for conversation
		try {
			new Parser();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		new ChatBox();
	}	
}