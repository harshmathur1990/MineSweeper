package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Win extends JFrame implements ActionListener{
	JButton jn1,jn2;
	Win(){
		super("Congratulations!!");
		setSize(300,100);
		jn1 = new JButton("New Game");
		jn2 = new JButton("Exit");
	}
	
	public void actionPerformed(ActionEvent ae){
		String s = ae.getActionCommand();
		if(s.equals("New Game")){
			setVisible(false);
			new Game().setVisible(true);
		}
		if(s.equals("Exit")){
			System.exit(0);
		}
	}
}
