package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.sql.ResultSet;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public interface Chart {
	//Map<String, Integer> chartBar = new LinkedHashMap<String, Integer>();
	
	void addBar(String name, int value, Map<String, Integer> bar);
	void resultToData(ResultSet rs, Map<String, Integer> bar);
	
	default public Color selectColor(String name, Map<String, Integer> bar) {
        Integer maxKey = Collections.max(bar.values());
        Integer minKey  = Collections.min(bar.values());
        //return bar.get(name) == maxKey || bar.get(name) == minKey ? Color.red : Color.blue;
        return bar.get(name) == maxKey || bar.get(name) == minKey ? new Color(230,57,70) : new Color(69,123,157);
		//색깔 설정 메서드를 디폴트로 설정
	}

}
