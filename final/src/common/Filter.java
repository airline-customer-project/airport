package common;

public class Filter {
	String label = "";
	String value = "";
	
	public Filter(String label, String value) {
		this.label = label;
		this.value = value;
	}
	
	public String getLabel() {
		return label;
	}
	
	public String getValue() {
		return value;
	}
}