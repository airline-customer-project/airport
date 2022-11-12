package ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import controller.AirportController;
import controller.MysqlConnection;
import dto.Airport;
import lib.CSVBuilder;

public class SearchPanel extends JPanel {
	String filter[] = {
			"iata", 
			"airport_name_eng", 
			"airport_name_kor", 
			"country_name_eng", 
			"country_name_kor", 
			"region",
			"state_name_eng"
	};
	
	ArrayList<Airport> airportList = new ArrayList<Airport>();
	ArrayList<Airport> filteredAirportList = new ArrayList<Airport>();
	JScrollPane table = null;
	
	String header[] = {
			"id", 
			"airport_name_neg", 
			"airport_name_kor", 
			"iata", 
			"leco", 
			"region", 
			"country_name_eng",
			"country_name_kor",
			"state_name_eng",
	};
	
	
	private void init() {
		try {
			Statement mysql = MysqlConnection.getInstance().mysql;
			ResultSet rs =  mysql.executeQuery(AirportController.GET_ALL);

			while (rs.next()) {		
				String id = rs.getString("id");;
				String airport_name_eng = rs.getString("airport_name_eng");
				String airport_name_kor = rs.getString("airport_name_kor");
				String iata = rs.getString("iata");
			    String leco = rs.getString("leco");
				String region = rs.getString("region");
				String country_name_eng = rs.getString("country_name_eng");
				String country_name_kor = rs.getString("country_name_kor");
				String state_name_eng = rs.getString("state_name_eng");
				
				this.airportList.add(new Airport(
						id, 
						airport_name_eng, 
						airport_name_kor,
						iata, 
						leco, 
						region, 
						country_name_eng, 
						country_name_kor, 
						state_name_eng
				));
				this.filteredAirportList = this.airportList;
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}
	
	void buildTable() {
		if(this.table != null) {
			remove(this.table);
		}
		
		String[][] content = new String[this.filteredAirportList.size()][];
		
		int index = 0;
		for(Airport airport : this.filteredAirportList) {
			content[index++] = airport.getArray();
		}

		JTable table = new JTable(content, header);
		this.table = new JScrollPane(table);
		
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				new AirportDetailDialog(table.getValueAt(table.getSelectedRow(), 0).toString());
			}
		});
		
		add(this.table, BorderLayout.CENTER);
		revalidate();
		repaint();
	}
	
	void filterData (String filter, String keyword) {
		try {
			Statement mysql = MysqlConnection.getInstance().mysql;
			ResultSet rs =  mysql.executeQuery(AirportController.GET_ALL_BY_FILTER(filter, keyword));
			this.filteredAirportList = new ArrayList<Airport>();
			while (rs.next()) {
				String id = rs.getString("id");;
				String airport_name_eng = rs.getString("airport_name_eng");
				String airport_name_kor = rs.getString("airport_name_kor");
				String iata = rs.getString("iata");
			    String leco = rs.getString("leco");
				String region = rs.getString("region");
				String country_name_eng = rs.getString("country_name_eng");
				String country_name_kor = rs.getString("country_name_kor");
				String state_name_eng = rs.getString("state_name_eng");

				this.filteredAirportList.add(new Airport(
						id, 
						airport_name_eng, 
						airport_name_kor,
						iata, 
						leco, 
						region, 
						country_name_eng, 
						country_name_kor, 
						state_name_eng
				));
			}
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}
	
	void buildDropdown() {
		JPanel jpanel = new JPanel();
		jpanel.setLayout(new BorderLayout());
		Dropdown dropdown = new Dropdown(filter, this);
		
		JTextField textField = new JTextField(20);
		JButton button = new JButton("검색");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				filterData(
						dropdown.getItemAt(dropdown.getSelectedIndex()),
						textField.getText()
				);
				buildTable();
			}
		});
		jpanel.add(dropdown, BorderLayout.WEST);
		jpanel.add(textField, BorderLayout.CENTER);
		jpanel.add(button, BorderLayout.EAST);
		add(jpanel, BorderLayout.NORTH);
	}
	
	void buildCSVButton() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		JButton button = new JButton("CSV 다운로드");
		CSVBuilder cb = new CSVBuilder();
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				cb.downloadAirport(filteredAirportList);
			}
		});
		panel.add(button, BorderLayout.EAST);
		add(panel, BorderLayout.SOUTH);
	}
	
	public SearchPanel() {
		init();
		setLayout(new BorderLayout());
		buildDropdown();
		buildTable();
		buildCSVButton();
		setVisible(true);
	}
}
