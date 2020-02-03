package gov.nasa.api.bean;

import java.io.Serializable;

public class WD implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private Double compass_degrees;
	private String compass_point;
	private Double compass_right;
	private Double compass_up;
	private Integer ct;
	public Double getCompass_degrees() {
		return compass_degrees;
	}
	public void setCompass_degrees(Double compass_degrees) {
		this.compass_degrees = compass_degrees;
	}
	public String getCompass_point() {
		return compass_point;
	}
	public void setCompass_point(String compass_point) {
		this.compass_point = compass_point;
	}
	public Double getCompass_right() {
		return compass_right;
	}
	public void setCompass_right(Double compass_right) {
		this.compass_right = compass_right;
	}
	public Double getCompass_up() {
		return compass_up;
	}
	public void setCompass_up(Double compass_up) {
		this.compass_up = compass_up;
	}
	public Integer getCt() {
		return ct;
	}
	public void setCt(Integer ct) {
		this.ct = ct;
	}
	
	
	
}
