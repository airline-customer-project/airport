package controller;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
//import javax.swing.text.Document;

public class WebCrawlController {
	Document document = null;
	public ArrayList<String> country_list = new ArrayList<>();
	
	public void setCountry(ArrayList<String> Arr,Document doc) {
		Elements country_name = doc.select("tbody tr > td.center:first-child");
		for (int j = 0; j < country_name.size(); j++) {
            final String title = country_name.get(j).text();
            //System.out.println(title);
            Arr.add(title);
        }
	}
	
	public ArrayList<String> CrawlingData(int index){
		ArrayList<String> country_data = new ArrayList<>();
		Elements country_name = document.select("tbody tr:nth-child("+index+") td.center");
		for (int j = 0; j < country_name.size(); j++) {
            final String data  = country_name.get(j).text();
            country_data.add(data);
        }
		return country_data;
		//[국가이름, 비자, 백신증명서, 코로나음성확인서, 코로나 회복증명서, 자가격리]
		//코로나 회복증명서와 자가 격리는 주마다 상이하며 바뀌는 경우가 잦아 0~3 인덱스의 데이터가 주로 쓰일 예정
	}
	
	public static void JSoupConnection() {
		
	}
	
	public WebCrawlController() {
		final String inflearnUrl = "https://www.airport.kr/ap_cnt/ko/svc/covid19/kdca/kdca.do";
		//https://www.data.go.kr/data/15085787/openapi.do 에서 국가별 해외입국자 조치사항 표를 만들 수 있음
        Connection conn = Jsoup.connect(inflearnUrl);
       
        try {
            this.document = conn.get();
            if(country_list.isEmpty())
            setCountry(country_list, document);            
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}