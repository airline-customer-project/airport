package ui;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

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
	
	public Color setPanelColor(ArrayList<String> data) {
		if(data.isEmpty()) {
			return Color.green;
		}else if(data.size() <= 2 && !data.isEmpty()){
			if(data.get(1).contains("해제")||data.get(1).contains("불요")||data.get(1).contains("면제")) {
				return Color.green;
			}else {
				return Color.red;
			}
		}else{
			if(data.get(5).contains("자가격리 면제")) {
				return Color.green;
			}else if(data.get(5).contains("접종자")){
				return Color.orange;
			}else {
				return Color.red;
			}
		}
		//return Color.red;
		//데이터 상태에 따라 색깔을 변경시켜 주는 메서드,
	}
	
	public AirportDetailDialog(String id) {
		// TODO Auto-generated constructor stub
		init(id);
		setSize(480, 300);
		JLabel label = new JLabel(airport.toString());
		setLayout(new BorderLayout());
		add(label, BorderLayout.CENTER);
		JPanel panel = new JPanel(new FlowLayout());
		Color color = setPanelColor(crawlData);
		//색깔 인스턴스
		
		if(!this.crawlData.isEmpty()) {
			JPanel cpanel = new JPanel(new GridLayout(1, 3));
			JPanel leftPanel = new JPanel(new BorderLayout());
			JLabel visaLabel = new JLabel();
			//leftpanel.add();
			try {
				System.out.println(this.crawlData.size());
				if(this.crawlData.size() <= 2) {
					System.out.println("short data list");
					
					JPanel centerPanel = new JPanel(new BorderLayout());
					JLabel name = new JLabel("입국 조치 변동");
					name.setHorizontalAlignment(JLabel.CENTER);
					name.setBackground(color);
					name.setForeground(color.WHITE);
					name.setOpaque(true);
					JLabel value = new JLabel(this.crawlData.get(1));
					value.setHorizontalAlignment(JLabel.CENTER);
					
					centerPanel.add(name, BorderLayout.NORTH);
					centerPanel.add(value, BorderLayout.CENTER);
					centerPanel.setBorder(new LineBorder(color,5));
					cpanel.add(centerPanel);
				}else {
					System.out.println("normal data");
					JLabel visa = new JLabel("비자 확인 여부");
					visa.setBackground(color);
					visa.setForeground(Color.WHITE);
					visa.setOpaque(true);
					
					visa.setHorizontalAlignment(JLabel.CENTER);
					leftPanel.add(visa, BorderLayout.NORTH);
					JLabel visa_data = new JLabel(this.crawlData.get(1));
					visa_data.setHorizontalAlignment(JLabel.CENTER);
					leftPanel.add(visa_data, BorderLayout.CENTER);
					
					JPanel centerPanel = new JPanel(new BorderLayout());
					JLabel vaccine = new JLabel("백신 증명서 확인 여부");
					vaccine.setBackground(color);
					vaccine.setForeground(Color.WHITE);
					vaccine.setHorizontalAlignment(JLabel.CENTER);
					vaccine.setOpaque(true);
					
					centerPanel.add(vaccine, BorderLayout.NORTH);
					JLabel vaccine_data = new JLabel(this.crawlData.get(2));
					vaccine_data.setHorizontalAlignment(JLabel.CENTER);
					centerPanel.add(vaccine_data, BorderLayout.CENTER);
					
					JPanel rightPanel = new JPanel(new BorderLayout());
					JLabel isNegative = new JLabel("음성 확인서 확인 여부");
					isNegative.setBackground(color);
					isNegative.setForeground(Color.WHITE);
					isNegative.setOpaque(true);
					
					isNegative.setHorizontalAlignment(JLabel.CENTER);
					rightPanel.add(isNegative, BorderLayout.NORTH);
					JLabel isNegative_data = new JLabel(this.crawlData.get(3));
					isNegative_data.setHorizontalAlignment(JLabel.CENTER);
					rightPanel.add(isNegative_data, BorderLayout.CENTER);
					
					leftPanel.setBorder(new LineBorder(color,5));
					rightPanel.setBorder(new LineBorder(color,5));
					centerPanel.setBorder(new LineBorder(color,5));
					//테두리 색 설정
					cpanel.add(leftPanel);
					cpanel.add(centerPanel);
					cpanel.add(rightPanel);
				}
				/*
				System.out.println("normal data");
				leftPanel.add(new JLabel("비자 확인 여부"), BorderLayout.NORTH);
				leftPanel.add(new JLabel(this.crawlData.get(1)), BorderLayout.CENTER);
				JPanel centerPanel = new JPanel(new BorderLayout());
				centerPanel.add(new JLabel("백신 증명서 확인 여부"), BorderLayout.NORTH);
				centerPanel.add(new JLabel(this.crawlData.get(2)), BorderLayout.CENTER);
				JPanel rightPanel = new JPanel(new BorderLayout());
				rightPanel.add(new JLabel("음성 확인서 확인 여부"), BorderLayout.NORTH);
				rightPanel.add(new JLabel(this.crawlData.get(3)), BorderLayout.CENTER);
				
				cpanel.add(leftPanel);
				cpanel.add(centerPanel);
				cpanel.add(rightPanel);
				*/
				//데이터가 적은 경우를 다음과 같이 나타냈음 좀 비효율적이여서 추후 수정 예정
				this.add(cpanel, BorderLayout.CENTER);
			} catch (Exception e) {
				// TODO: handle exception
			}
		
		}else{
			//여기에 비었을 시 다른 데이터 제공
			JPanel cpanel = new JPanel(new GridLayout(1, 3));
			JPanel leftPanel = new JPanel(new BorderLayout());
			JLabel visaLabel = new JLabel();
			
			System.out.println("null");
			
			JPanel centerPanel = new JPanel(new BorderLayout());
			JLabel name = new JLabel("입국 조치 변동");
			name.setBackground(color);
			name.setForeground(Color.WHITE);
			name.setOpaque(true);
			
			name.setHorizontalAlignment(JLabel.CENTER);
			JLabel value = new JLabel("내국인 대상 코로나 관련 입국 제한조치 해제 (상세사항 대사관 등 문의)");
			value.setHorizontalAlignment(JLabel.CENTER);
			
			centerPanel.add(name, BorderLayout.NORTH);
			centerPanel.add(value, BorderLayout.CENTER);
			centerPanel.setBorder(new LineBorder(color,5));
			cpanel.add(centerPanel);
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
							new File("C://Users//hansa//git//airport//hsh//final//src//ui//map.html"), 
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
		JLabel isolation = new JLabel(color == Color.red ? "● 모든 입국자 자가격리" : 
			(color == Color.orange ? "● 백신 접종자 자가격리 면제" : "● 모든 입국자 자가격리 면제"));
		isolation.setForeground(color);
		isolation.setAlignmentX(LEFT_ALIGNMENT);
		panel.add(isolation);
		//색깔에 따른 자가격리 현황 설명 라벨
		panel.add(this.favoriteButton);
		panel.add(this.mapButton);
		add(panel, BorderLayout.SOUTH);
		
		setVisible(true);
	}
}
