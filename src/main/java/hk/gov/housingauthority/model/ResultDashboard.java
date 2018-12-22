package hk.gov.housingauthority.model;

import java.util.ArrayList;
import java.util.Date;


public class ResultDashboard {

	public ResultDashboard(){
		srvyDateList = new ArrayList<String>();
		srvyCntList = new ArrayList<Integer>();
	}
	
	private int srvyRecId;
	private String SrvyTtl;
	private Date bgnDate;
	private Date endDate;
	private int sbmtPct;
	private int notSbmtPct;
	
	private ArrayList<String> srvyDateList;
	private ArrayList<Integer> srvyCntList;

	public int getSrvyRecId() {
		return srvyRecId;
	}
	public void setSrvyRecId(int srvyRecId) {
		this.srvyRecId = srvyRecId;
	}
	public String getSrvyTtl() {
		return SrvyTtl;
	}
	public void setSrvyTtl(String srvyTtl) {
		SrvyTtl = srvyTtl;
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
	public int getSbmtPct() {
		return sbmtPct;
	}
	public void setSbmtPct(int sbmtPct) {
		this.sbmtPct = sbmtPct;
	}
	public int getNotSbmtPct() {
		return notSbmtPct;
	}
	public void setNotSbmtPct(int notSbmtPct) {
		this.notSbmtPct = notSbmtPct;
	}
	public ArrayList<String> getSrvyDateList() {
		return srvyDateList;
	}
	public void setSrvyDateList(ArrayList<String> srvyDateList) {
		this.srvyDateList = srvyDateList;
	}
	public ArrayList<Integer> getSrvyCntList() {
		return srvyCntList;
	}
	public void setSrvyCntList(ArrayList<Integer> srvyCntList) {
		this.srvyCntList = srvyCntList;
	}




}
