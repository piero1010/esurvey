package hk.gov.housingauthority.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

public class SrvyRec extends Record{
	
	public SrvyRec(){
		this.srvyTmpl = new SrvyTmpl();
		this.srvySts = new SrvySts();
		this.rmdrTmpl = new SrvyEmailTmpl();
		this.invtTmpl = new SrvyEmailTmpl();
		this.tmpTmpl = new SrvyEmailTmpl();
		this.srvyEmailHist = new SrvyEmailHist();
		this.coor = new User();
		this.srvyPptCatg = new SrvyPptCatg();
		this.srvyPpt = new ArrayList<SrvyPpt>();
	}
	
	private Integer srvyRecId;
	private Date creDate;
	private User coor;
	
	@NotNull(message = "The \"Year\" is mandatory.")
	@Min(value=1990, message = "The \"Year\" is not proper. Please check.")
	@Max(value=2046, message = "The \"Year\" is not proper. Please check.")
	private Integer srvyYear;
	
	private SrvyTmpl srvyTmpl;
	
	@NotEmpty(message = "The \"Survey Title\" is mandatory.")
	@Size(max=255, message = "The \"Survey Title\" must be less than or equal to 256 characters.")
	private String srvyTtl;
	private SrvySts srvySts;
	
	@NotNull(message = "The \"Start Date\" is mandatory.")
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date bgnDate;
	
	@NotNull(message = "The \"End Date\" is mandatory.")
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date endDate;
	private SrvyPptCatg srvyPptCatg;
		
	private String pptDivCode;
	
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date autoRmdrDate;
	
	@Size(max=1000, message = "The \"Survey Remark\" must be less than or equal to 1000 characters.")
	private String srvyRmk;
	private String lastRecTxnUserId;
	private String lastRecTxnTypeCode;
	private Timestamp lastRecTxnDate;
	private Integer numOfRespd;
	private Integer numOfPtcl;
	private SrvyEmailHist srvyEmailHist;
	
	private SrvyEmailTmpl rmdrTmpl;
	private SrvyEmailTmpl invtTmpl;
	private SrvyEmailTmpl tmpTmpl;
	
	private Timestamp startDateFrom;
	private Timestamp startDateTo;
	
	private ArrayList<SrvyPpt> srvyPpt;
	
	public Integer getNumOfRespd() {
		return numOfRespd;
	}
	public void setNumOfRespd(Integer numOfRespd) {
		this.numOfRespd = numOfRespd;
	}
	public Integer getNumOfPtcl() {
		return numOfPtcl;
	}
	public void setNumOfPtcl(Integer numOfPtcl) {
		this.numOfPtcl = numOfPtcl;
	}
	public Integer getSrvyRecId() {
		return srvyRecId;
	}
	public void setSrvyRecId(Integer srvyRecId) {
		this.srvyRecId = srvyRecId;
	}
	public Date getCreDate() {
		return creDate;
	}
	public void setCreDate(Date creDate) {
		this.creDate = creDate;
	}
	public Integer getSrvyYear() {
		return srvyYear;
	}
	public void setSrvyYear(Integer srvyYear) {
		this.srvyYear = srvyYear;
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
	public String getPptDivCode() {
		return pptDivCode;
	}
	public void setPptDivCode(String pptDivCode) {
		this.pptDivCode = pptDivCode;
	}
	public Date getAutoRmdrDate() {
		return autoRmdrDate;
	}
	public void setAutoRmdrDate(Date autoRmdrDate) {
		this.autoRmdrDate = autoRmdrDate;
	}
	public String getSrvyRmk() {
		return srvyRmk;
	}
	public void setSrvyRmk(String srvyRmk) {
		this.srvyRmk = srvyRmk;
	}
	public String getLastRecTxnUserId() {
		return lastRecTxnUserId;
	}
	public void setLastRecTxnUserId(String lastRecTxnUserId) {
		this.lastRecTxnUserId = lastRecTxnUserId;
	}
	public String getLastRecTxnTypeCode() {
		return lastRecTxnTypeCode;
	}
	public void setLastRecTxnTypeCode(String lastRecTxnTypeCode) {
		this.lastRecTxnTypeCode = lastRecTxnTypeCode;
	}
	public Timestamp getLastRecTxnDate() {
		return lastRecTxnDate;
	}
	public void setLastRecTxnDate(Timestamp lastRecTxnDate) {
		this.lastRecTxnDate = lastRecTxnDate;
	}
	public SrvyTmpl getSrvyTmpl() {
		return srvyTmpl;
	}
	public void setSrvyTmpl(SrvyTmpl srvyTmpl) {
		this.srvyTmpl = srvyTmpl;
	}
	public String getSrvyTtl() {
		return srvyTtl;
	}
	public void setSrvyTtl(String srvyTtl) {
		this.srvyTtl = srvyTtl;
	}
	public SrvySts getSrvySts() {
		return srvySts;
	}
	public void setSrvySts(SrvySts srvySts) {
		this.srvySts = srvySts;
	}
	public SrvyEmailTmpl getRmdrTmpl() {
		return rmdrTmpl;
	}
	public void setRmdrTmpl(SrvyEmailTmpl rmdrTmpl) {
		this.rmdrTmpl = rmdrTmpl;
	}
	public SrvyEmailTmpl getInvtTmpl() {
		return invtTmpl;
	}
	public void setInvtTmpl(SrvyEmailTmpl invtTmpl) {
		this.invtTmpl = invtTmpl;
	}
	public SrvyEmailTmpl getTmpTmpl() {
		return tmpTmpl;
	}
	public void setTmpTmpl(SrvyEmailTmpl tmpTmpl) {
		this.tmpTmpl = tmpTmpl;
	}
	public SrvyEmailHist getSrvyEmailHist() {
		return srvyEmailHist;
	}
	public void setSrvyEmailHist(SrvyEmailHist srvyEmailHist) {
		this.srvyEmailHist = srvyEmailHist;
	}
	public Timestamp getStartDateFrom() {
		return startDateFrom;
	}
	public void setStartDateFrom(Timestamp startDateFrom) {
		this.startDateFrom = startDateFrom;
	}
	public Timestamp getStartDateTo() {
		return startDateTo;
	}
	public void setStartDateTo(Timestamp startDateTo) {
		this.startDateTo = startDateTo;
	}
	public User getCoor() {
		return coor;
	}
	public void setCoor(User coor) {
		this.coor = coor;
	}
	public ArrayList<SrvyPpt> getSrvyPpt() {
		return srvyPpt;
	}
	public void setSrvyPpt(ArrayList<SrvyPpt> srvyPpt) {
		this.srvyPpt = srvyPpt;
	}
	
	public String toJsonString() {
		return "[\"" + srvyRecId + "\",\"" + Record.getDateFormat().format(creDate) + "\",\"" + coor.getUserId() + "\",\"" + srvyYear + "\",\"" + srvyTtl + "\",\"" + srvySts.toString() + "\",\"" + (bgnDate!=null?Record.getDateFormat().format(bgnDate):"") + "\",\"" + (endDate!=null?Record.getDateFormat().format(endDate):"") + "\",\"" + numOfPtcl  + "\",\"" + numOfRespd + "\"]";
	}
	public SrvyPptCatg getSrvyPptCatg() {
		return srvyPptCatg;
	}
	public void setSrvyPptCatg(SrvyPptCatg srvyPptCatg) {
		this.srvyPptCatg = srvyPptCatg;
	}
	
	
}
