package ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class LeftNavigationButton extends JButton implements MouseListener {
	MainFrame parent;
 	public LeftNavigationButton(String name, MainFrame parent) {
 		this.parent = parent;
		// TODO Auto-generated constructor stub
 		setText(name);

		setPreferredSize(new Dimension(820, 26));
		
		setVisible(true);
		addMouseListener(this);
	}
 	
 	@Override
 	public void mouseClicked(MouseEvent e) {
 		// TODO Auto-generated method stub
 		this.parent.changePanel(this.getText());
 	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
