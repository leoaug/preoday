package gov.nasa.api.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JSO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private AT at;
	private HWS hws;
	private PRE pre;
	
	@JsonProperty("WD")
	private List <WD> listaWd;
	
	@JsonProperty("Season")
	private String season;
	
	@JsonProperty("First_UTC")
	private Date firstUTC;
	
	@JsonProperty("Last_UTC")
	private Date lastUTC;

	public AT getAt() {
		return at;
	}

	public void setAt(AT at) {
		this.at = at;
	}

	public HWS getHws() {
		return hws;
	}

	public void setHws(HWS hws) {
		this.hws = hws;
	}

	public PRE getPre() {
		return pre;
	}

	public void setPre(PRE pre) {
		this.pre = pre;
	}

	public List<WD> getListaWd() {
		return listaWd;
	}

	public void setListaWd(List<WD> listaWd) {
		this.listaWd = listaWd;
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public Date getFirstUTC() {
		return firstUTC;
	}

	public void setFirstUTC(Date firstUTC) {
		this.firstUTC = firstUTC;
	}

	public Date getLastUTC() {
		return lastUTC;
	}

	public void setLastUTC(Date lastUTC) {
		this.lastUTC = lastUTC;
	}
	
	
}
