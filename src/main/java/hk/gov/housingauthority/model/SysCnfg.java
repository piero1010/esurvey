package hk.gov.housingauthority.model;

import java.sql.Timestamp;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class SysCnfg extends Record{

	@NotEmpty(message = "The \"Code\" is mandatory.")
	@Size(max=30, message = "The \"Code\" must be less than or equal to 30 characters.")
	private String sysCnfgId;
	
	@Size(max=50, message = "The \"Description\" must be less than or equal to 50 characters.")
	private String sysCnfgDesc;
	
	@NotEmpty(message = "The \"Value\" is mandatory.")
	@Size(max=50, message = "The \"Value\" must be less than or equal to 500 characters.")
	private String sysCnfgVal;
	
	@NotEmpty(message = "The \"Group\" is mandatory.")
	@Size(max=10, message = "The \"Group\" must be less than or equal to 10 characters.")
	private String sysCnfgGrp;
	
	private String lastRecTxnUserId;
	private String lastRecTxnTypeCode;
	private Timestamp lastRecTxnDate;
	
	public String getSysCnfgId() {
		return sysCnfgId;
	}
	public void setSysCnfgId(String sysCnfgId) {
		this.sysCnfgId = sysCnfgId;
	}
	public String getSysCnfgDesc() {
		return sysCnfgDesc;
	}
	public void setSysCnfgDesc(String sysCnfgDesc) {
		this.sysCnfgDesc = sysCnfgDesc;
	}
	public String getSysCnfgVal() {
		return sysCnfgVal;
	}
	public void setSysCnfgVal(String sysCnfgVal) {
		this.sysCnfgVal = sysCnfgVal;
	}
	public String getSysCnfgGrp() {
		return sysCnfgGrp;
	}
	public void setSysCnfgGrp(String sysCnfgGrp) {
		this.sysCnfgGrp = sysCnfgGrp;
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
	public String toJsonString() {
		return "[\"" + this.getSysCnfgId() + "\",\"" + this.getSysCnfgVal() + "\",\"" + this.getSysCnfgGrp() + "\",\"" + this.getSysCnfgDesc() + "\"]";
	}
}
