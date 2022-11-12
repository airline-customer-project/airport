package ui;

import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JLabel;

import controller.AirportController;
import controller.MysqlConnection;
import dto.Airport;

public class AirportDetailDialog extends JFrame {
	Airport airport;
	
	
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
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public AirportDetailDialog(String id) {
		// TODO Auto-generated constructor stub
		init(id);
		setSize(480, 300);
		JLabel label = new JLabel(airport.toString());
		add(label);
		
		setVisible(true);
	}
}
