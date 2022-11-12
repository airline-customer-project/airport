package controller;

public class FavoriteController {
	static public String GET_ALL = "SELECT * FROM favorite LEFT JOIN airport ON id = airport_id";
	static public String WRITE_ONE(String id) {
		return "INSERT INTO favorite (airport_id) VALUES ( " + id + " )";
	}
	static public String GET_ONE(String id) {
		return "SELECT * FROM favorite LEFT JOIN airport ON id = airport_id WHERE airport_id = " + id;
	}
}
 