package hk.gov.housingauthority.model;

import java.util.ArrayList;

public class Coordinator extends Record {

	public Coordinator(){
		this.user = new User();
		this.srvyGrpList = new ArrayList<SrvyGrp>();
	}
	
	private User user;
	ArrayList<SrvyGrp> srvyGrpList = new ArrayList<SrvyGrp>();

	// for view use in multiple select list
	String modifyGrpList;
	String addGrpList;
	String divCode;

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public ArrayList<SrvyGrp> getSrvyGrpList() {
		return srvyGrpList;
	}
	public void setSrvyGrpList(ArrayList<SrvyGrp> srvyGrpList) {
		this.srvyGrpList = srvyGrpList;
	}
	public String getModifyGrpList() {
		return modifyGrpList;
	}
	public void setModifyGrpList(String modifyGrpList) {
		this.modifyGrpList = modifyGrpList;
	}
	public String getAddGrpList() {
		return addGrpList;
	}
	public void setAddGrpList(String addGrpList) {
		this.addGrpList = addGrpList;
	}
	public String getDivCode() {
		return divCode;
	}
	public void setDivCode(String divCode) {
		this.divCode = divCode;
	}
	
	@Override
	public String toString() {
		return "Coordinator [user=" + user + ", srvyGrpList=" + srvyGrpList + ", modifyGrpList=" + modifyGrpList + ", addGrpList=" + addGrpList + ", divCode=" + divCode + "]";
	}
	
	public String getAllGrpId(){
		String result = "";
		for (int i = 0; i < srvyGrpList.size(); i++) {
			if (!"".equals(result)){
				result += ", ";	
			}
			result += srvyGrpList.get(i).getSrvyGrpId();
		}
		return result;
	}
	
	public String getAllGrpName(){
		String result = "";
		for (int i = 0; i < srvyGrpList.size(); i++) {
			if (!"".equals(result)){
				result += ", ";	
			}
			result += "" + srvyGrpList.get(i).getSrvyGrpName() + "";
		}
		return result;
	}
	
}
