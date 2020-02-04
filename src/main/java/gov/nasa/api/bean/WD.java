package gov.nasa.api.bean;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WD implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("most_common")
	private MostCommon mostCommon;

	public MostCommon getMostCommon() {
		return mostCommon;
	}

	public void setMostCommon(MostCommon mostCommon) {
		this.mostCommon = mostCommon;
	} 
	
	

}
