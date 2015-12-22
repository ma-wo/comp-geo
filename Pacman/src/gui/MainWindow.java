package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import game.GameController;


public class MainWindow extends JFrame {
	// TODO
	
	private static final long serialVersionUID = -7253423557232817170L;
	
	private GameController controller;
	

	public MainWindow(GameController controller) {		
		super("Ghosts Smelling Pacman");		        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.controller = controller;
        
        addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				controller.keyPressed(e.getKeyCode());
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				controller.keyReleased(e.getKeyCode());
			}
		});
	}
	
	
	public void paintComponent(Graphics draw){
		getContentPane().setBackground(Color.WHITE);
	}
	
}
