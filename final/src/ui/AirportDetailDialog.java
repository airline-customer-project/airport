package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.AirportController;
import controller.FavoriteController;
import controller.MysqlConnection;
import dto.Airport;
import javafx.embed.swing.JFXPanel;

public class AirportDetailDialog extends JFrame {
	Airport airport;
	Boolean isFavorite = false;
	
	JButton favoriteButton = new JButton("즐겨찾기 등록");
	JButton mapButton = new JButton("지도");
	
	
	private void init(String id){
		try {
			Statement mysql = MysqlConnection.getInstance().mysql;
			ResultSet rs =  mysql.executeQuery(AirportController.GET_ONE(id));
			

			while(rs.next()) {
				String airport_name_eng = rs.getString("airport_name_eng");
				String airport_name_kor = rs.getString("airport_name_kor");
				String iata = rs.getString("iata");
			    String leco = rs.getString("leco");
				String region = rs.getString("region");
				String country_name_eng = rs.getString("country_name_eng");
				String country_name_kor = rs.getString("country_name_kor");
				String state_name_eng = rs.getString("state_name_eng");
				
				this.airport = new Airport(
						id, 
						airport_name_eng, 
						airport_name_kor,
						iata, 
						leco, 
						region, 
						country_name_eng, 
						country_name_kor, 
						state_name_eng
				);
			}
			
			rs.close();
			
			ResultSet rs2 = mysql.executeQuery(FavoriteController.GET_ONE(id));
			while(rs2.next()) {
				if(rs2.getString("id").equals(id)) {
					this.isFavorite = true;
					this.favoriteButton.setText("즐겨찾기 해제");
				}
			}
			
			rs2.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void handleClickFavorite() {
		try {
			if(this.favoriteButton.getText() == "즐겨찾기 등록") {
				Statement mysql = MysqlConnection.getInstance().mysql;
				mysql.executeUpdate(FavoriteController.WRITE_ONE(this.airport.id));
				this.favoriteButton.setText("즐겨찾기 해제");
				
			}else {
				Statement mysql = MysqlConnection.getInstance().mysql;
				mysql.executeUpdate(FavoriteController.DELETE_ONE(this.airport.id));
				this.favoriteButton.setText("즐겨찾기 등록");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public AirportDetailDialog(String id) {
		// TODO Auto-generated constructor stub
		init(id);
		setSize(480, 300);
		JLabel label = new JLabel(airport.toString());
		setLayout(new BorderLayout());
		add(label, BorderLayout.CENTER);
		JPanel panel = new JPanel(new FlowLayout());
		
		
		this.favoriteButton.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				handleClickFavorite();
				
			}
		});
		
		this.mapButton.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				JFrame frame = new JFrame();
				frame.setLayout(new BorderLayout());
				
				try {
					final GoogleMapsScene api = GoogleMapsScene.launch(new File("C:\\Users\\hansi\\git\\airport\\final\\src\\ui\\map.html"), null);
				    JFXPanel fxPanel = new JFXPanel();
				    api.attach(fxPanel);
				    frame.setSize(1024, 768);
				    frame.add(fxPanel, BorderLayout.CENTER);
				    frame.setVisible(true);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace(); 
				}    

			}
		});
		
		panel.add(this.favoriteButton);
		panel.add(this.mapButton);
		add(panel, BorderLayout.SOUTH);
		
		setVisible(true);
	}
}
