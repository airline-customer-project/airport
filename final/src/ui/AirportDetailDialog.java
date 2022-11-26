package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.AirportController;
import controller.FavoriteController;
import controller.MysqlConnection;
import controller.WebCrawlController;
import dto.Airport;
import javafx.embed.swing.JFXPanel;

public class AirportDetailDialog extends JFrame {
	Airport airport;
	Boolean isFavorite = false;
	
	JButton favoriteButton = new JButton("즐겨찾기 등록");
	JButton mapButton = new JButton("지도");
	
	ArrayList<String> crawlData = new ArrayList<String>();
	
	
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
				String lat = rs.getString("lat");
				String lng = rs.getString("lng");
				
				this.airport = new Airport(
						id, 
						airport_name_eng, 
						airport_name_kor,
						iata, 
						leco, 
						region, 
						country_name_eng, 
						country_name_kor, 
						state_name_eng,
						lat,
						lng
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
			
			WebCrawlController webCrawlController = new WebCrawlController();
			if(webCrawlController.country_list.indexOf(this.airport.country_name_kor) != -1) {
				this.crawlData = webCrawlController.CrawlingData(webCrawlController.country_list.indexOf(this.airport.country_name_kor)+1);
				System.out.println(this.crawlData);
			}
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
		
		if(!this.crawlData.isEmpty()) {
			JPanel cpanel = new JPanel(new GridLayout(1, 3));
			JPanel leftPanel = new JPanel(new BorderLayout());
			leftPanel.add(new JLabel("비자 확인 여부"), BorderLayout.NORTH);
			leftPanel.add(new JLabel(this.crawlData.get(2)), BorderLayout.CENTER);
			JPanel centerPanel = new JPanel(new BorderLayout());
			centerPanel.add(new JLabel("백신 증명서 확인 여부"), BorderLayout.NORTH);
			centerPanel.add(new JLabel(this.crawlData.get(3)), BorderLayout.CENTER);
			JPanel rightPanel = new JPanel(new BorderLayout());
			rightPanel.add(new JLabel("음성 확인서 확인 여부"), BorderLayout.NORTH);
			rightPanel.add(new JLabel(this.crawlData.get(4)), BorderLayout.CENTER);
			
			cpanel.add(leftPanel);
			cpanel.add(centerPanel);
			cpanel.add(rightPanel);
			
			this.add(cpanel, BorderLayout.CENTER);
		}
		
		
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
		
		Airport currentAirport = this.airport;
		
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
					final GoogleMapsScene api = GoogleMapsScene.launch(
							new File("C:\\Users\\hansi\\git\\airport\\final\\src\\ui\\map.html"), 
							currentAirport.lat,
							currentAirport.lng,
							null
					);
					
					
				    JFXPanel fxPanel = new JFXPanel();
				    api.attach(fxPanel);
				    frame.setSize(1024, 768);
				    frame.add(fxPanel, BorderLayout.CENTER);
				    frame.setVisible(true);
				    Thread.sleep(3000);
				    api.setCenter(Double.parseDouble(currentAirport.lat), Double.parseDouble(currentAirport.lng));
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
