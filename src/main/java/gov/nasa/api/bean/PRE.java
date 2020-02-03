package gov.nasa.api.bean;

import java.io.Serializable;

public class PRE implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private String av;
	private String ct;
	private String mn;
	private String mx;
	public String getAv() {
		return av;
	}
	public void setAv(String av) {
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
