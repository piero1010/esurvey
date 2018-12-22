package hk.gov.housingauthority.model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;

public class Record {
	
	private int totRec;
	private HashMap<String, String> filterList;
	private String lastRecTxnUserId;
	private String lastRecTxnTypeCode;
	private Timestamp lastRecTxnDate;
	
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

	public Record(){
		filterList = new HashMap<String, String>();
	}	
	
	public HashMap<String, String> getFilterList() {
		return filterList;
	}

	public void setFilterList(HashMap<String, String> filterList) {
		this.filterList = filterList;
	}

	public int getTotRec() {
		return totRec;
	}

	public void setTotRec(int totRec) {
		this.totRec = totRec;
	}

	public static SimpleDateFormat getDateFormat() {
		return new SimpleDateFormat("dd-MM-yyyy");
	}

	public static SimpleDateFormat getDatetimeFormat() {
		return new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	}
}
