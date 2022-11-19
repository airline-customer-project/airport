package ui;
import java.awt.BorderLayout;
import java.awt.Color;
import javafx.embed.swing.JFXPanel;
import javax.swing.*;
import java.io.File;
import ui.GoogleMapsScene;

public class MainFrame extends JFrame {
	private JPanel currentPanel;
	
	public MainFrame(String[] args) {
		setSize(1024, 768);
		setLayout(new BorderLayout());
		add(new LeftNavigation(this), BorderLayout.WEST);
		this.currentPanel = new SearchPanel();
		add(this.currentPanel, BorderLayout.CENTER);
		setBackground(Color.YELLOW);
		this.getContentPane().setBackground(Color.white);
		/*
		try {
			final GoogleMapsScene api = GoogleMapsScene.launch(new File("C:\\Users\\hansi\\git\\airport\\final\\src\\ui\\map.html"), args);
		    JFXPanel fxPanel = new JFXPanel();
		    api.attach(fxPanel);
		    add(fxPanel, BorderLayout.EAST);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 
		}    
	    */
		setVisible(true);
	}
	
	public void changePanel(String name) {		
		this.getContentPane().remove(this.currentPanel);
		if(name == "공항검색") {
			this.currentPanel = new SearchPanel();
		}else if(name == "즐겨찾기") {
			this.currentPanel = new FavoritePanel();
		}else if(name == "통계") {
			this.currentPanel = new StatisticsPanel();
		}else {
			
		}
		this.getContentPane().add(this.currentPanel);
		setVisible(true);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MainFrame mf = new MainFrame(args);
		mf.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}

}
