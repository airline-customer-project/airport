package dto;

public class Airport {
	public String id;
	public String airport_name_eng;
	public String airport_name_kor;
	public String iata;
	public String leco;
	public String region;
	public String country_name_eng;
	public String country_name_kor;
	public String state_name_eng;
	
	public Airport (
			String id, 
			String airport_name_eng, 
			String airport_name_kor,
			String iata,
			String leco,
			String region,
			String country_name_eng,
			String country_name_kor,
			String state_name_eng
	) {
		this.id = id;
		this.airport_name_eng = airport_name_eng;
		this.airport_name_kor = airport_name_kor;
		this.iata = iata;
		this.leco = leco;
		this.region = region;
		this.country_name_eng = country_name_eng;
		this.country_name_kor = country_name_kor;
		this.state_name_eng = state_name_eng;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "id : " + id + " name : " + airport_name_kor;
	}
	
	public String[] getArray() {
		return new String[] {
			id, 
			airport_name_eng, 
			airport_name_kor, 
			iata, 
			leco, 
			region, 
			country_name_eng, 
			country_name_kor, 
			state_name_eng
		};
	}
}
