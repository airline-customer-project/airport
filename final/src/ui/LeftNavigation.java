package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class LeftNavigation extends JPanel{
	public LeftNavigation(MainFrame parent) {
		// TODO Auto-generated constructor stub
		this.setPreferredSize(new Dimension(140, 768));
		
		//this.setBackground(Color.black);
		//배경색 추가
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(Box.createHorizontalStrut(1));
		this.add(new LeftNavigationButton("공항검색", parent));
		this.add(Box.createHorizontalStrut(1));
		this.add(new LeftNavigationButton("즐겨찾기", parent));
		this.add(Box.createHorizontalStrut(1));
		this.add(new LeftNavigationButton("대륙별 통계", parent));
		this.add(Box.createHorizontalStrut(1));
		this.add(new LeftNavigationButton("국가별 통계", parent));
		this.add(Box.createHorizontalStrut(1));
		//빈 컴포넌트를 추가해서 박스 레이아웃에서 동일한 간격으로 벌어지게 설정함
		

		setVisible(true);
	}
}
