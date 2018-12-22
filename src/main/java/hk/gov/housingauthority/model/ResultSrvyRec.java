package hk.gov.housingauthority.model;

import java.sql.Timestamp;
import java.util.Date;


public class ResultSrvyRec {

	private int srvyRecId;
	private Date creDate;
	private String coorId;	
	private int srvyYear;
	private int srvyTmplId;
	private String srvyTmplName;
	private int srvySts;
	private Date bgnDate;
	private Date endDate;
	private int pptCatg;
	
	public int getSrvyRecId() {
		return srvyRecId;
	}
	public void setSrvyRecId(int srvyRecId) {
		this.srvyRecId = srvyRecId;
	}
	public Date getCreDate() {
		return creDate;
	}
	public void setCreDate(Date creDate) {
		this.creDate = creDate;
	}
	public String getCoorId() {
		return coorId;
	}
	public void setCoorId(String coorId) {
		this.coorId = coorId;
	}
	public int getSrvyYear() {
		return srvyYear;
	}
	public void setSrvyYear(int srvyYear) {
		this.srvyYear = srvyYear;
	}
	public int getSrvyTmplId() {
		return srvyTmplId;
	}
	public void setSrvyTmplId(int srvyTmplId) {
		this.srvyTmplId = srvyTmplId;
	}
	public String getSrvyTmplName() {
		return srvyTmplName;
	}
	public void setSrvyTmplName(String srvyTmplName) {
		this.srvyTmplName = srvyTmplName;
	}
	public int getSrvySts() {
		return srvySts;
	}
	public void setSrvySts(int srvySts) {
		this.srvySts = srvySts;
	}
	public Date getBgnDate() {
		return bgnDate;
	}
	public void setBgnDate(Date bgnDate) {
		this.bgnDate = bgnDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public int getPptCatg() {
		return pptCatg;
	}
	public void setPptCatg(int pptCatg) {
		this.pptCatg = pptCatg;
	}
	@Override
	public String toString() {
		return "ResultSrvyRec [srvyRecId=" + srvyRecId + ", creDate=" + creDate + ", coorId=" + coorId + ", srvyYear="
				+ srvyYear + ", srvyTmplId=" + srvyTmplId + ", srvyTmplName=" + srvyTmplName + ", srvySts=" + srvySts
				+ ", bgnDate=" + bgnDate + ", endDate=" + endDate + ", pptCatg=" + pptCatg + "]";
	}
	

		
	



}
