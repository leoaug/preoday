package gov.nasa.api.bean;

import java.io.Serializable;

public class AT implements Serializable {

	
	private static final long serialVersionUID = 1L;
	private Double av;
	private String ct;
	private String mn;
	private String mx;
	public Double getAv() {
		return av;
	}
	public void setAv(Double av) {
		this.av = av;
	}
	public String getCt() {
		return ct;
	}
	public void setCt(String ct) {
		this.ct = ct;
	}
	public String getMn() {
		return mn;
	}
	public void setMn(String mn) {
		this.mn = mn;
	}
	public String getMx() {
		return mx;
	}
	public void setMx(String mx) {
		this.mx = mx;
	}
	
	
}
