package ui;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.AirportController;
import controller.MysqlConnection;
import controller.StatisticsController;
import dto.Airport;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class RegionChartPanel extends JPanel implements Chart{
	private Map<String, Integer> region = new LinkedHashMap<String, Integer>();
	//국가별 데이터를 저장
	//static으로 했으나 get문을 만들거나 해서 변경 여부 있음
	
	/*
	private Map<String, Integer> favorite_region = new LinkedHashMap<String, Integer>();
	//즐겨찾기에서의 지역별 데이터를 저장
	private Map<String, Integer> favorite_country = new LinkedHashMap<String, Integer>();
	//즐겨찾기에서의 국가별 데이터를 저장
	*/
	
	public void addBar(String name, int value, Map<String, Integer> bar) {
		bar.put(name, value);
		repaint();
	}
	
	public void resultToData(ResultSet rs, Map<String, Integer> bar) {
		try {
			//그래프를 다시 그리려면 해당 
			while (rs.next()) {		
				String name = rs.getString("name");;
				int value = rs.getInt("value");
				addBar(name,value,bar);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		repaint();
		//이거 써도 되나
	}
	/*
	public Color selectColor(String name, Map<String, Integer> bar) {
        Integer maxKey = Collections.max(bar.values());
        Integer minKey  = Collections.min(bar.values());
        return bar.get(name) == maxKey || bar.get(name) == minKey ? Color.red : Color.blue;
        //최대, 최솟값의 색을 다르게 설정
	}*/
	@Override
	protected void paintComponent(Graphics g) {
		int max = Integer.MIN_VALUE;
		for(Integer value : region.values()) {
			max = Math.max(max,  value);
		}
		
		int width = (getWidth() / region.size()) - 20;
		//창의 전체 크기 / 그래프 요소의 개수 - 원하는 정도(정도만큼 공백이 생김. 안그럼 꽉참)
		
		int x = 30;
		//이게 0 이면 첫번째 막대기가 왼쪽에 딱 붙음

		
		for(String name : region.keySet()) {
			int value = region.get(name);
			
			System.out.println(name);
			
			value = value <= 10 ? 50 : value;
			//1인경우 아예 그래프가 나오질 않아서 간단하게 조절해주는 코드, 50부분을 변경하면 원하는 크기로 설정 가능
			//즐겨찾기 같은 경우는 개수가 적으니 즐겨찾기 그래프 기능 추가 시 해당 코드 삭제 요망 
			
			int height = (int)((getHeight()-20)*((double)value / max));
			
			g.setColor(selectColor(name, region));
		
			g.fillRect(x, getHeight() - height - 50 , width, height);
			//채우기
			g.setColor(selectColor(name, region));
			g.drawRect(x, getHeight() - height - 50, width, height);
			
			
			g.setColor(Color.black);
			g.drawString(name, x, getHeight() - 30);
			//윤곽선
			x += (width + 10);
		}	
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(region.size() * 10 + 2, 50);
		//여기 크기만큼 기본적인 창의 크기 결정
		//지금은 최솟값이다
	}
	
	public RegionChartPanel() {
		// TODO Auto-generated constructor stub
		try {
			//Map<String, Integer> region = new LinkedHashMap<String, Integer>();
			Statement mysql = MysqlConnection.getInstance().mysql;
			ResultSet rs =  mysql.executeQuery(StatisticsController.GET_GROUP_BY_REGION);
			
			if(this.region.isEmpty())
				//데이터가 추가로 들어가는 것을 막기 위함, 또는 버튼을 누를 때 마다 비우고 다시 그리는 식으로 만드는 것도 방법도 만들 계획이다.
				this.resultToData(rs, region);
			
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}
}
