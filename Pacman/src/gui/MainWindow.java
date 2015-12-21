package gui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;


public class MainWindow extends JFrame {
	// TODO
	
	private static final long serialVersionUID = -7253423557232817170L;


	public MainWindow() {		
		super("Ghosts Smelling Pacman");		        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
	public void paint(Graphics draw){
		getContentPane().setBackground(Color.WHITE);
	}
	
}
