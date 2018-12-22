package hk.gov.housingauthority.model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MySrvy  extends Record {
	
	public MySrvy(){
		this.coor = new User();
		this.srvySts = new SrvySts();
	}
	
	private Integer srvyPptId;
	private Integer srvyRecId;
	private SrvySts srvySts;
	private User coor;
	private String srvyTtl;
	private String itSrvcCode;
	private Date bgnDate;
	private Date endDate;
	private Timestamp sbmtDate;
	private String sbmtSts;
	
	public Integer getSrvyPptId() {
		return srvyPptId;
	}
	public void setSrvyPptId(Integer srvyPptId) {
		this.srvyPptId = srvyPptId;
	}
	public Integer getSrvyRecId() {
		return srvyRecId;
	}
	public void setSrvyRecId(Integer srvyRecId) {
		this.srvyRecId = srvyRecId;
	}
	public User getCoor() {
		return coor;
	}
	public void setCoor(User coor) {
		this.coor = coor;
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
	public String getSbmtSts() {
		return sbmtSts;
	}
	public void setSbmtSts(String sbmtSts) {
		this.sbmtSts = sbmtSts;
	}	
	public String toStringSbmtSts(String sbmtSts) {
		if (sbmtSts!=null) {
			return sbmtSts.equalsIgnoreCase("Y")?"Yes":"No";
		} else {
			return "No";
		}
	}
	
	public String toJsonString() {
		return "[\"" + srvyPptId
				+ "\",\"" + srvyTtl + (itSrvcCode != null && !"".equals(itSrvcCode) ?" (" + itSrvcCode + ")":"")  
				+ "\",\"" + (srvySts != null ?srvySts.toString():"") 
				+ "\",\"" + (bgnDate != null ?Record.getDateFormat().format(bgnDate):"") 
				+ "\",\"" + (endDate != null ?Record.getDateFormat().format(endDate):"")
				+ "\",\"" + coor.getUserId() 
				+ "\",\"" + (sbmtDate != null ?Record.getDatetimeFormat().format(sbmtDate):"")
				+ "\",\"" + toStringSbmtSts(sbmtSts) + "\"]";
	}
	public SrvySts getSrvySts() {
		return srvySts;
	}
	public void setSrvySts(SrvySts srvySts) {
		this.srvySts = srvySts;
	}

}
