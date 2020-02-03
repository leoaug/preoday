package gov.nasa.api.bean;

import java.io.Serializable;

public class Temperature implements Serializable {


	private static final long serialVersionUID = 1L;
	
	private Double averageTemperature;

	public Double getAverageTemperature() {
		return averageTemperature;
	}

	public void setAverageTemperature(Double averageTemperature) {
		this.averageTemperature = averageTemperature;
	}
	
	

}
