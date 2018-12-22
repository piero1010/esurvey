package hk.gov.housingauthority.model;

import java.util.Date;

public class QuerySrvyRec {

	private Integer srvyGrp;

	private Date bgnFmDate;
	private Date bgnToDate;
	private Integer srvyYear;
	private String coorId;	
	private String srvyTmplName;
	private Integer srvySts;
	
	public Integer getSrvyGrp() {
		return srvyGrp;
	}
	public void setSrvyGrp(Integer srvyGrp) {
		this.srvyGrp = srvyGrp;
	}
	

	public Date getBgnFmDate() {
		return bgnFmDate;
	}
	public void setBgnFmDate(Date bgnFmDate) {
		this.bgnFmDate = bgnFmDate;
	}
	public Date getBgnToDate() {
		return bgnToDate;
	}
	public void setBgnToDate(Date bgnToDate) {
		this.bgnToDate = bgnToDate;
	}
	public Integer getSrvyYear() {
		return srvyYear;
	}
	public void setSrvyYear(Integer srvyYear) {
		this.srvyYear = srvyYear;
	}
	public String getCoorId() {
		return coorId;
	}
	public void setCoorId(String coorId) {
		this.coorId = coorId;
	}
	public String getSrvyTmplName() {
		return srvyTmplName;
	}
	public void setSrvyTmplName(String srvyTmplName) {
		this.srvyTmplName = srvyTmplName;
	}
	public Integer getSrvySts() {
		return srvySts;
	}
	public void setSrvySts(Integer srvySts) {
		this.srvySts = srvySts;
	}
	@Override
	public String toString() {
		return "QuerySrvyRec [srvyGrp=" + srvyGrp + ", bgnFmDate=" + bgnFmDate + ", bgnToDate=" + bgnToDate
				+ ", srvyYear=" + srvyYear + ", coorId=" + coorId + ", srvyTmplName=" + srvyTmplName + ", srvySts="
				+ srvySts + "]";
	}

	

	



}
