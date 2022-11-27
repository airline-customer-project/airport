package ui;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class LeftNavigation extends JPanel{
	public LeftNavigation(MainFrame parent) {
		// TODO Auto-generated constructor stub
		this.setPreferredSize(new Dimension(140, 768));
	
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		this.add(new LeftNavigationButton("공항검색", parent));
		this.add(new LeftNavigationButton("즐겨찾기", parent));
		this.add(new LeftNavigationButton("대륙별 통계", parent));
		this.add(new LeftNavigationButton("국가별 통계", parent));


		setVisible(true);
	}
}
