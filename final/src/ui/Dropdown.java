package ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Dropdown extends JComboBox<String>{

	 public Dropdown(String[] s, JPanel parent) {
		 super(s);
		 setLayout(new BorderLayout());
		   
		   
		 setSize(400, 300);
		 setVisible(true);
	}

}
