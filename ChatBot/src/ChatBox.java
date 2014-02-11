import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.plaf.basic.BasicBorders;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

/**
 * This class will create the GUI for the chat agent
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
	private JMenu settings;
	private JMenuItem newChat;
	private JMenuItem exit;
	
	private ChatAgent chat;
	
	public ChatBox(){
		
		chat = new ChatAgent();
		
		frame = new JFrame("TravelBot Chat Agency");
		frame.setPreferredSize(new Dimension(700,520));
		frame.setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		convo = new JTextArea();
		input = new JTextArea();
		scroll = new JScrollPane(convo);
		
		menuPanel = new JMenuBar();
		file = new JMenu("File");
		settings = new JMenu("Settings");
		newChat = new JMenuItem("New Chat");
		exit = new JMenuItem("Exit");
		
		send = new JButton("Send");
		send.addActionListener(new ButtonListener());
		
		input.addKeyListener(new TextListener());
		
		buildFrame();
	}
	
	private void buildFrame(){
		
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
		convo.setText("TravelBot started at " + Utils.getCurrentDateFull() + "\r\n" + "Powered by Google"+ "\r\n" );		
	}
	
	public class TextListener implements KeyListener{

		@Override
		public void keyPressed(KeyEvent e) {

			if(e.getKeyCode() == KeyEvent.VK_ENTER){
				e.consume();
				send.doClick();
			}
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
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
				new ChatBox();
			} else if(id==2){
				System.exit(0);
			}
		}
	}
	
	public class ButtonListener implements ActionListener{
		String in = "";
		String conversation = "";
		String out = "";
		String tagged;
		MaxentTagger mt;
		
		@Override
		public void actionPerformed(ActionEvent e) {
//			in = input.getText();
//			// If user does not enter anything the chatbot will not do anything.
//			if(in.length() == 0){
//				return;
//			} else {
//				conversation = convo.getText();
//				input.setText("");
//				out = chat.buildResponse(in);	// Use user input to determine response to display.
//				convo.setText(conversation + "\n\r" + "User: " + in + out);
//			}
			
			// Testing for StanfordNLP methods
			mt = new MaxentTagger(MaxentTagger.BASE_TAGGER_HOME);
			in = input.getText();
			tagged = mt.tagString(in);
			convo.setText(tagged);
			
			
		}
	}
	public static void main(String[] args){
		new ChatBox();
	}	
}