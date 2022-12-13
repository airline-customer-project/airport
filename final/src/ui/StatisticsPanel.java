  package ui;

import java.awt.BorderLayout;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.AirportController;
import controller.MysqlConnection;
import controller.StatisticsController;
import dto.Airport;
import dto.Statistic;
import javafx.scene.layout.Border;

public class StatisticsPanel extends JPanel{
	public static String QUERY_COUNTRY = "country";
	public static String QUERY_REGION = "region";
	ArrayList<Statistic> statisticList = new ArrayList<Statistic>();
	public void getStatisticsData(String type) {
		try {
			String query = null;
			if(type == StatisticsPanel.QUERY_COUNTRY) {
				query = StatisticsController.GET_GROUP_BY_COUNTRY;
			}else {
				query = StatisticsController.GET_GROUP_BY_REGION;
			}
			Statement mysql = MysqlConnection.getInstance().mysql;
			ResultSet rs =  mysql.executeQuery(query);
			while(rs.next()) {
				int value = rs.getInt("value");
				String name = rs.getString("name");
				statisticList.add(new Statistic(name, value));
			}
			
			rs.close();
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}
	public StatisticsPanel(JPanel jpanel) {
		// TODO Auto-generated constructor stub
		this.setLayout(new BorderLayout());
		add(new JLabel("통계 창"), BorderLayout.NORTH);
		add(jpanel, BorderLayout.CENTER);
		
		getStatisticsData(QUERY_COUNTRY);
		
		for(Statistic s : this.statisticList) {
			System.out.println(s.label + " : " +  s.number);
		}

	}
}