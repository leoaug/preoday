package gov.nasa.api.bean;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;

public class JSO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JsonProperty("AT")
	private AT at;
	
	@JsonProperty("HWS")
	private HWS hws;
	
	@JsonProperty("PRE")
	private PRE pre;

	
	@JsonProperty("WD")
	private WD wd;
	
	@JsonProperty("Season")
	private String season;
	
	@JsonProperty("First_UTC")
	private String firstUTC;
	
	@JsonProperty("Last_UTC")
	private String lastUTC;

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

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public String getFirstUTC() {
		return firstUTC;
	}

	public void setFirstUTC(String firstUTC) {
		this.firstUTC = firstUTC;
	}

	public String getLastUTC() {
		return lastUTC;
	}

	public void setLastUTC(String lastUTC) {
		this.lastUTC = lastUTC;
	}
	
	
}
