package controller;

public class StatisticsController {
	static public String GET_GROUP_BY_REGION = "SELECT region AS name, COUNT(iata) AS value FROM airport.airport GROUP BY region";
	static public String GET_GROUP_BY_COUNTRY = "SELECT country_name_kor AS name, COUNT(iata) AS value FROM airport.airport GROUP BY country_name_kor ORDER BY COUNT(iata) DESC LIMIT 0, 10";
}
