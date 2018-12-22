package hk.gov.housingauthority.model;

import java.sql.Timestamp;
import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class SrvyTmpl extends Record{
	
	public SrvyTmpl(){
		this.srvyGrp = new SrvyGrp();
		this.srvyTmplFrzInd = new SrvyTmplFrzInd();
		this.srvyUser = new User();
	}
	
	private Record record;
	private SrvyGrp srvyGrp;
	
	private Integer srvyTmplId;
	
	@NotEmpty(message = "The \"Template Name\" is mandatory.")
	@Size(max=255, message = "The \"Template Name\" must be less than or equal to 256 characters.")
	private String srvyTmplName;
	
	private String srvyTmplCnfg;
	
	private SrvyTmplFrzInd srvyTmplFrzInd;
		
	private Integer srvyRptCat;
	private User srvyUser;
	
	private Date srvyCreDate;
	
	private String lastRecTxnUserId;
	
	private String lastRecTxnTypeCode;
	
	private Timestamp lastRecTxnDate;

	private String srvyTmplCss;
	
	public Integer getSrvyTmplId() {
		return srvyTmplId;
	}
	public void setSrvyTmplId(Integer srvyTmplId) {
		this.srvyTmplId = srvyTmplId;
	}
	public String getSrvyTmplName() {
		return srvyTmplName;
	}
	public void setSrvyTmplName(String srvyTmplName) {
		this.srvyTmplName = srvyTmplName;
	}
	public String getSrvyTmplCnfg() {
		return srvyTmplCnfg;
	}
	public void setSrvyTmplCnfg(String srvyTmplCnfg) {
		this.srvyTmplCnfg = srvyTmplCnfg;
	}
	public Integer getSrvyRptCat() {
		return srvyRptCat;
	}
	public void setSrvyRptCat(Integer srvyRptCat) {
		this.srvyRptCat = srvyRptCat;
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
	@Override
	public String toString() {
		return "SrvyTmpl [srvyTmplId=" + srvyTmplId + ", srvyTmplName=" + srvyTmplName + ", srvyTmplCnfg="
				+ srvyTmplCnfg + ", srvyTmplFrzInd=" + srvyTmplFrzInd + ", srvyGrpId=" + srvyGrp.getSrvyGrpId() + ", srvyRptCat="
				+ srvyRptCat + ", srvyUserId=" + srvyUser.getUserDsgn() + ", lastRecTxnUserId=" + lastRecTxnUserId
				+ ", lastRecTxnTypeCode=" + lastRecTxnTypeCode + ", lastRecTxnDate=" + lastRecTxnDate + "]";
	}

	public String toJsonString() {
		return "[\"" + srvyTmplId + "\",\"" + srvyTmplName + "\",\"" + srvyGrp.getSrvyGrpName() + "\",\"" + srvyUser.getUserDsgn() + "\",\"" + srvyCreDate + "\",\"" + srvyTmplFrzInd.toString() + "\"]";
	}
	
	public Date getSrvyCreDate() {
		return srvyCreDate;
	}
	public void setSrvyCreDate(Date srvyCreDate) {
		this.srvyCreDate = srvyCreDate;
	}
	public SrvyGrp getSrvyGrp() {
		return srvyGrp;
	}
	public void setSrvyGrp(SrvyGrp srvyGrp) {
		this.srvyGrp = srvyGrp;
	}
	public Record getRecord() {
		return record;
	}
	public void setRecord(Record record) {
		this.record = record;
	}
	public SrvyTmplFrzInd getSrvyTmplFrzInd() {
		return srvyTmplFrzInd;
	}
	public void setSrvyTmplFrzInd(SrvyTmplFrzInd srvyTmplFrzInd) {
		this.srvyTmplFrzInd = srvyTmplFrzInd;
	}
	public User getSrvyUser() {
		return srvyUser;
	}
	public void setSrvyUser(User srvyUser) {
		this.srvyUser = srvyUser;
	}
	public String getSrvyTmplCss() {
		return srvyTmplCss;
	}
	public void setSrvyTmplCss(String srvyTmplCss) {
		this.srvyTmplCss = srvyTmplCss;
	}
	
}
