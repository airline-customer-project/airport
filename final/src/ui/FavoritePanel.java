package ui;

import java.awt.BorderLayout;
import java.sql.ResultSet;
import java.sql.Statement;

import controller.AirportController;
import controller.FavoriteController;
import controller.MysqlConnection;

public class FavoritePanel extends SearchPanel {
	
	public void init() {
		try {
			Statement mysql = MysqlConnection.getInstance().mysql;
			ResultSet rs =  mysql.executeQuery(FavoriteController.GET_ALL);
			this.resultToData(rs, this.airportList);
			
			this.filteredAirportList = this.airportList;
			
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}
	
	public FavoritePanel() {
		// TODO Auto-generated constructor stub
		init();
		setLayout(new BorderLayout());
		buildDropdown();
		buildTable();
		buildCSVButton();
		setVisible(true);
	}
}
