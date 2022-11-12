package lib;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;

import dto.Airport;

public class CSVBuilder {
	Path relativePath = Paths.get("");
	String path = relativePath.toAbsolutePath().toString();
	File csv = new File(path + "test.csv");
	BufferedWriter bw = null;
	
	public void downloadAirport(ArrayList<Airport> airportList) {
		try {
			this.bw = new BufferedWriter(new FileWriter(this.csv, true));
			for(Airport airport : airportList) {
				String line = "";
				line = 
					airport.id + ","
					+ airport.airport_name_eng + ","
					+ airport.airport_name_kor + ","
					+ airport.iata + ","
					+ airport.leco + ","
					+ airport.region + ","
					+ airport.country_name_eng + ","
					+ airport.country_name_kor + ","
					+ airport.state_name_eng;
				bw.write(line);
				bw.newLine();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(this.bw != null) {
					this.bw.flush();
					this.bw.close();
				}
			} catch( IOException e) {
				e.printStackTrace();
			}
		}
	}

}
