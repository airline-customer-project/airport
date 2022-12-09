package ui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.mysql.cj.x.protobuf.MysqlxCrud.Collection;

import controller.MysqlConnection;
import controller.StatisticsController;
import dto.Airport;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.ScrollPane;
import java.security.KeyStore.Entry;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;

public class CountryChartPanel extends JPanel implements Chart{
	private JScrollPane scroll;
	private Map<String, Integer> country = new LinkedHashMap<String, Integer>();
	//국가별 데이터를 저장
	//메인에서 테스트로 활용하기 위해 static으로 하였다. 
	
	/*
	private Map<String, Integer> favorite_region = new LinkedHashMap<String, Integer>();
	//즐겨찾기에서의 지역별 데이터를 저장
	private Map<String, Integer> favorite_country = new LinkedHashMap<String, Integer>();
	//즐겨찾기에서의 국가별 데이터를 저장
	*/
	
	public void addBar(String name, int value, Map<String, Integer> bar) {
		bar.put(name, value);
		//repaint();
	}
	
	public void resultToData(ResultSet rs, Map<String, Integer> bar) {
		try {
			while (rs.next()) {		
				String name = rs.getString("name");;
				int value = rs.getInt("value");
				//bar.put(name,value);
				addBar(name,value,bar);
			}
			
			repaint();
		} catch(Exception e) {
			e.printStackTrace();
		}

	}
	/*
	public Color selectColor(String name, Map<String, Integer> bar) {
        Integer maxKey = Collections.max(bar.values());
        Integer minKey  = Collections.min(bar.values());
        return bar.get(name) == maxKey || bar.get(name) == minKey ? Color.red : Color.blue;
        //최대, 최솟값의 색을 다르게 설정
	} //인터페이스를 활용하여 재구성
	*/
	@Override
	protected void paintComponent(Graphics g) {
		
		int max = Integer.MIN_VALUE;
		for(Integer value : country.values()) {
			max = Math.max(max,  value);
		}
		//최대크기를 설정
		
		int height_w = (getHeight() / country.size())- 10;
		//창의 전체 크기 / 그래프 요소의 개수 - 원하는 정도(정도만큼 공백이 생김. 안그럼 꽉참)
		
		int y = 10;
		
		for(String name : country.keySet()) {
			int value = country.get(name);
			
			//value = value <= 10 ? 50 : value;
			//1인경우 아예 그래프가 나오질 않아서 간단하게 조절해주는 코드, 50부분을 변경하면 원하는 크기로 설정 가능
			//즐겨찾기 같은 경우는 개수가 적으니 즐겨찾기 그래프 기능 추가 시 해당 코드 삭제 요망 
			
			int width_h = (int)((getWidth()-20)*((double)value / max));
			
			g.setColor(selectColor(name, country));
		
			g.fillRect(0, y , width_h, height_w);
			//채우기
			//g.fillRect(getWidth() - width_h, y , width_h , height_w); 이건 오른쪽에서 나오는 그래프, 아래도 똑같이 하면 됨
			g.setColor(selectColor(name, country));
			g.drawRect(0, y , width_h, height_w);
			//윤곽선
			y += (height_w + 5);
			
		}
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(country.size() * 10, 600);
		//여기 크기만큼 기본적인 창의 크기 결정
		//지금은 최솟값이다
	}
	
	public CountryChartPanel() {
		try {
			Statement mysql = MysqlConnection.getInstance().mysql;
			ResultSet rs =  mysql.executeQuery(StatisticsController.GET_GROUP_BY_COUNTRY);

			if(this.country.isEmpty())
				//데이터가 추가로 들어가는 것을 막기 위함, 또는 버튼을 누를 때 마다 비우고 다시 그리는 식으로 만드는 것도 방법도 만들 계획이다.
				this.resultToData(rs, country);
			
			//scroll = new JScrollPane(regionChart, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			//scroll.setBounds(4, 4, 340, 330);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}
}
