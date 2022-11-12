package ui;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.*;

public class MainFrame extends JFrame {
	private JPanel currentPanel;
	
	public MainFrame() {
		setSize(1024, 768);
		setLayout(new BorderLayout());
		add(new LeftNavigation(this), BorderLayout.WEST);
		this.currentPanel = new SearchPanel();
		add(this.currentPanel, BorderLayout.CENTER);
		setBackground(Color.YELLOW);
		setVisible(true);
		this.getContentPane().setBackground(Color.white);
	}
	
	public void changePanel(String name) {		
		this.getContentPane().remove(this.currentPanel);
		if(name == "공항검색") {
			this.currentPanel = new SearchPanel();
		}else if(name == "즐겨찾기") {
			this.currentPanel = new StatisticsPanel();
		}else if(name == "통계") {
			this.currentPanel = new StatisticsPanel();
		}else {
			
		}
		this.getContentPane().add(this.currentPanel);
		setVisible(true);
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MainFrame mf = new MainFrame();
		mf.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}

}
