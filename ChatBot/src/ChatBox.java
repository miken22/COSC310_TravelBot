import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.*;

import opennlp.tools.util.InvalidFormatException;

/**
 * This class will create the GUI for the chat agent
 * and will send and receive the I/O for the conversation 
 * to the necessary classes.
 * 
 * @author Mike Nowicki
 *
 */
public class ChatBox{

	// Main frame components
	private JFrame frame;
	private Container c;
	private JScrollPane scroll1;
	private JScrollPane scroll2;
	private JTextPane convo;
	private JTextArea input;

	private StyledDocument textarea;
	private Style userStyle;
	private Style agentStyle;
	private Style chatStyle;
	private Font font;
	
	private JButton send;
	private JButton clear;
	
	private JMenuBar menu;
	private JMenu file;
	private JMenuItem newConvo;
	private JMenuItem exit;
	private JMenuItem save;
	
	private TravelAgent agent;
	
	public ChatBox() throws FontFormatException, IOException{
		
		convo = new JTextPane();
		input = new JTextArea();
		scroll1 = new JScrollPane(convo);
		scroll2 = new JScrollPane(input);
		
		userStyle = convo.addStyle("userin", null);
		agentStyle = convo.addStyle("agentstyle",null);
		chatStyle = convo.addStyle("chatstyle", null);
		
		menu = new JMenuBar();
		file = new JMenu("File");
		newConvo = new JMenuItem("New Conversation");
		save = new JMenuItem("Save Conversation");
		exit = new JMenuItem("Exit");
		
		newConvo.addActionListener(new MenuListener(1));
		save.addActionListener(new MenuListener(2));
		exit.addActionListener(new MenuListener(3));
		
		
		send = new JButton("Send");
		clear = new JButton("Clear");
		send.addActionListener(new ButtonListener());
		clear.addActionListener(new ButtonListener());
		
		input.addKeyListener(new TextListener());
		
		buildFrame();
	}
	
	private void buildFrame() throws FontFormatException, IOException{
		
		agent = new TravelAgent();
		
		frame = new JFrame("TravelBot Chat Agency");
		frame.setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(530, 455);
		frame.setResizable(false);
		frame.setJMenuBar(menu);
		frame.setIconImage(new ImageIcon(getClass().getClassLoader().getResource("plane.jpg")).getImage());
		menu.setBackground(new Color(244,244,244));
		menu.add(file);
		file.add(newConvo);
		file.add(save);
		file.add(exit);
		
		c = frame.getContentPane();
		c.setBackground(new Color(240,240,240));
		font = Font.createFont(0,this.getClass().getResourceAsStream("/Trebuchet MS.ttf"));
		
		convo.setEditable(false);
		convo.setBounds(2,2,frame.getWidth()-19,300);
		font = font.deriveFont(Font.PLAIN,14);
		Border b = new LineBorder(Color.LIGHT_GRAY,1,true);
		convo.setFont(font);
		convo.setBorder(b);	
		convo.setBackground(new Color(252,252,252));
		scroll1.setBounds(2,2,frame.getWidth()-19,300);
		scroll1.setBackground(new Color(240,240,240));
		scroll1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY,1,true), "Chat History"));
		c.add(scroll1);
		
		textarea = convo.getStyledDocument();
		
		input.setLineWrap(true);
		input.setBorder(b);	
		input.setBounds(4, 305, frame.getWidth()-111, 77);
		input.setBackground(new Color(252,252,252));
		input.setFont(font);
		scroll2.setBounds(4, 305, frame.getWidth()-111, 92);
		scroll2.setBackground(new Color(240,240,240));
		scroll2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY,1,true), "Chat with TravelBot:"));
		c.add(scroll2);
		
		send.setBounds(frame.getWidth()-101,312,80,40);
//		send.setBorder(BorderFactory.createRaisedBevelBorder());
		send.setBorder(b);
		send.setBackground(new Color(250,250,250));
		send.setFocusPainted(false);
		clear.setBounds(frame.getWidth()-101,355,80,40);
//		clear.setBorder(BorderFactory.createRaisedBevelBorder());
		clear.setBorder(b);
		clear.setBackground(new Color(250,250,250));
		clear.setFocusPainted(false);
		c.add(send);
		c.add(clear);
	
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		input.requestFocus();

		StyleConstants.setForeground(chatStyle, Color.black);
        try { textarea.insertString(textarea.getLength(), "TravelBot started at " + Utils.getCurrentDateFull() + "\r\n" + "Powered by Google",chatStyle); }
        catch (BadLocationException e1){}
		
		StyleConstants.setForeground(agentStyle, new Color(0,128,0));
        try { textarea.insertString(textarea.getLength(),"\r\n\r\nTravel Bot: ",agentStyle);}
        catch (BadLocationException e1){}
        
		StyleConstants.setForeground(chatStyle, Color.black);
        try { textarea.insertString(textarea.getLength(), agent.getStartUp(),chatStyle); }
        catch (BadLocationException e1){}
		
	}
	
	public class TextListener implements KeyListener{

		@Override
		public void keyPressed(KeyEvent e) {
			// Identifies the user's use of return to submit a question.
			if(e.getKeyCode() == KeyEvent.VK_ENTER){
				e.consume();
				send.doClick();
			}
			input.setFocusAccelerator(e.getKeyChar());
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
			// Clear user input if clear button is pressed
			JButton b = (JButton)e.getSource();
			b.setBackground(new Color(253,253,253));
			if(e.getSource() == clear){
				input.setText("");
				b.setBackground(new Color(250,250,250));
			// If user does not enter anything the chatbot will not do anything.
			} else if(in.length() == 0){
				input.requestFocus();
				b.setBackground(new Color(250,250,250));
				return;
			} else {
				addText();
				b.setBackground(new Color(250,250,250));
			}
			input.requestFocus();
		}
		
		public void addText(){

			input.setText("");
			
			StyleConstants.setForeground(userStyle, Color.red);
	        try { textarea.insertString(textarea.getLength(), "\r\n\r\nUser: ",userStyle); }
	        catch (BadLocationException e1){}

			StyleConstants.setForeground(chatStyle, Color.black);
	        try { textarea.insertString(textarea.getLength(), in,chatStyle); }
	        catch (BadLocationException e1){}

			out = agent.buildResponse(in);	// Pass user input to TravelAgent for parsing and response generation							
			
			StyleConstants.setForeground(agentStyle, new Color(0,128,0));
	        try { textarea.insertString(textarea.getLength(), "\r\n\r\nTravelBot: ",agentStyle); }
	        catch (BadLocationException e1){}
	        
			StyleConstants.setForeground(chatStyle, Color.black);
	        try { textarea.insertString(textarea.getLength(), out,chatStyle); }
	        catch (BadLocationException e1){}
	        
	        Document d = convo.getDocument();
	        convo.select(d.getLength(), d.getLength());

		}
	}
	
	private class MenuListener implements ActionListener {

		private int id;
		
		public MenuListener(int a){
			this.id = a;
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
		
			if(id==1){
				try {
					new CustomParser();
				} catch (InvalidFormatException e) {e.printStackTrace();} 
				  catch (IOException e) {e.printStackTrace();}
				frame.dispose();
				try {
					new ChatBox();
				} catch (FontFormatException | IOException e) {}
			} else if (id==2){
				String name = (String)JOptionPane.showInputDialog(null, "Save As:\n","Set File Name",JOptionPane.PLAIN_MESSAGE,null,null,"");
				try {
					SaveText.saveConvo(name, convo.getText());
				} catch (FileNotFoundException e) {}
			} else {
				System.exit(0);
			}
			
		}

	}
	
	public static void main(String[] args) throws InvalidFormatException, IOException, FontFormatException{
		new CustomParser();
		new ChatBox();
	}	
}