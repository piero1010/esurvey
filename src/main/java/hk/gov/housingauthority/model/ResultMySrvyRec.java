package hk.gov.housingauthority.model;

import java.sql.Timestamp;
import java.util.Date;


public class ResultMySrvyRec {

	private int srvyRecId;
	private int srvyPptId;
	private String coorId;	
	private int srvyYear;
	private int srvyTmplId;
	private String srvyTtl;
    private String itSrvcCode;
	private int srvySts;
	private Date bgnDate;
	private Date endDate;
	private Timestamp sbmtDate;
	private String respSts;
	
	public int getSrvyRecId() {
		return srvyRecId;
	}
	public void setSrvyRecId(int srvyRecId) {
		this.srvyRecId = srvyRecId;
	}
	public int getSrvyPptId() {
		return srvyPptId;
	}
	public void setSrvyPptId(int srvyPptId) {
		this.srvyPptId = srvyPptId;
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
	public String getSrvyTtl() {
		return srvyTtl;
	}
	public void setSrvyTtl(String srvyTtl) {
		this.srvyTtl = srvyTtl;
	}
	public String getItSrvcCode() {
		return itSrvcCode;
	}
	public void setItSrvcCode(String itSrvcCode) {
		this.itSrvcCode = itSrvcCode;
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
	public Timestamp getSbmtDate() {
		return sbmtDate;
	}
	public void setSbmtDate(Timestamp sbmtDate) {
		this.sbmtDate = sbmtDate;
	}
	public String getRespSts() {
		return respSts;
	}
	public void setRespSts(String respSts) {
		this.respSts = respSts;
	}

	@Override
	public String toString() {
		return "ResultMySrvyRec [srvyRecId=" + srvyRecId + ", srvyPptId=" + srvyPptId + ", coorId=" + coorId
				+ ", srvyYear=" + srvyYear + ", srvyTmplId=" + srvyTmplId + ", srvyTtl=" + srvyTtl + ", itSrvcCode="
				+ itSrvcCode + ", srvySts=" + srvySts + ", bgnDate=" + bgnDate + ", endDate=" + endDate + ", sbmtDate="
				+ sbmtDate + ", respSts=" + respSts + "]";
	}

		
	



}
