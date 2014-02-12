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
	private JScrollPane scroll;
	private JTextArea convo;
	private JTextArea input;

	private JButton send;
	
	// Menu items
	private JMenuBar menuPanel;
	private JMenu file;
//	private JMenu settings;
	private JMenuItem newChat;
	private JMenuItem exit;

	private TravelAgent agent;
	
	public ChatBox(){
		
		convo = new JTextArea();
		input = new JTextArea();
		scroll = new JScrollPane(convo);
		
		menuPanel = new JMenuBar();
		file = new JMenu("File");
		newChat = new JMenuItem("New Chat");
		exit = new JMenuItem("Exit");
		
		send = new JButton("Send");
		send.addActionListener(new ButtonListener());
		
		input.addKeyListener(new TextListener());
		
		buildFrame();
	}
	
	private void buildFrame(){
		
		agent = new TravelAgent();
		
		frame = new JFrame("TravelBot Chat Agency");
		frame.setPreferredSize(new Dimension(700,520));
		frame.setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		frame.setSize(760, 550);
		c = frame.getContentPane();
		
		convo.setEditable(false);
		convo.setLineWrap(true);
		convo.setBorder(BasicBorders.getTextFieldBorder());
		convo.setBounds(2,2,frame.getWidth()-23,400);
		scroll.setBounds(2,2,frame.getWidth()-23,400);
		scroll.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Chat History"));
		c.add(scroll);
		
		input.setLineWrap(true);
		input.setBorder(BasicBorders.getTextFieldBorder());	
		input.setBounds(4, 405, frame.getWidth()-115, 80);
		c.add(input);
		
		send.setBounds(frame.getWidth()-105,405,80,80);
		c.add(send);
		
		file.add(newChat);
		file.add(exit);	
		menuPanel.add(file);
		frame.setJMenuBar(menuPanel);
		newChat.addActionListener(new MenuListener(1));
		exit.addActionListener(new MenuListener(2));
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

	// Performs different actions based on which menu item is clicked
	private class MenuListener implements ActionListener{
		private int id;
		public MenuListener(int a){
			this.id = a;
		}
			
		@Override
		public void actionPerformed(ActionEvent e) {				
			if(id==1){
				frame.dispose();
				buildFrame();
			} else if(id==2){
				System.exit(0);
			}
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
			if(in.length() == 0){
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