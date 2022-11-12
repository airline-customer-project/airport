package controller;

public class AirportController {
	static public String GET_ALL = "SELECT * FROM airport";
	
	static public String GET_ONE(String s) {
		return "SELECT * FROM airport WHERE id = " + s;
	}
	
	static public String GET_ALL_BY_FILTER(String filter, String keyword) {
		System.out.println("SELECT * FROM airport WHERE " + filter + " LIKE " + "'%" + keyword + "%'");
		return "SELECT * FROM airport WHERE " + filter + " LIKE " + "'%" + keyword + "%'" ;
	}
}
