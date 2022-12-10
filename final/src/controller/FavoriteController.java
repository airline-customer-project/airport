package controller;

public class FavoriteController {
	static public String GET_ALL = "SELECT * FROM favorite LEFT JOIN airport ON airport.id = airport_id";
	static public String WRITE_ONE(String id) {
		return "INSERT INTO favorite (id, airport_id) VALUES ( " + id + ", " + id + " )";
	}
	static public String GET_ONE(String id) {
		return "SELECT airport.* FROM favorite LEFT JOIN airport ON airport.id = airport_id WHERE airport_id = " + id;
	}
	static public String DELETE_ONE(String id) {
		return "DELETE FROM favorite WHERE airport_id = " + id;
	}
}
 