package ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
			// "iata", 
			// "airport_name_eng", 
			// "airport_name_kor", 
			// "country_name_eng", 
			// "country_name_kor", 
			// "region",
			// "state_name_eng"
			"IATA", 
			"공항 이름(영문)", 
			"공항 이름(한글)", 
			"나라 이름(영문)", 
			"나라 이름(한글)", 
			"대륙",
			"도시 이름(영문)"
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
	
	public void resultToData(ResultSet rs, ArrayList<Airport> arraylist) {
		try {
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
				String lat = rs.getString("lat");
				String lng = rs.getString("lng");
				
				arraylist.add(new Airport(
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
				));
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void init() {
		try {
			Statement mysql = MysqlConnection.getInstance().mysql;
			ResultSet rs =  mysql.executeQuery(AirportController.GET_ALL);
			resultToData(rs, this.airportList);
			this.filteredAirportList = this.airportList;
			
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

		JTable table = new JTable(content, header) {
			 public boolean editCellAt(int row, int column, java.util.EventObject e) {
	            return false;
	        }
		};
		this.table = new JScrollPane(table);
		
		
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				if(me.getClickCount() == 2) {
					new AirportDetailDialog(table.getValueAt(table.getSelectedRow(), 0).toString());
				}
			}
		});
		
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				
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
			resultToData(rs, this.filteredAirportList);
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
				String[] temp = new String[]{
					"iata", 
					"airport_name_eng", 
					"airport_name_kor", 
					"country_name_eng", 
					"country_name_kor", 
					"region",
					"state_name_eng"
				};
				
				filterData(
						temp[dropdown.getSelectedIndex()],
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
