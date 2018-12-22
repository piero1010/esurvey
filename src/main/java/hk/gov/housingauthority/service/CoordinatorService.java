package hk.gov.housingauthority.service;

import java.util.ArrayList;

import hk.gov.housingauthority.model.Coordinator;
import hk.gov.housingauthority.model.SrvyGrp;

public interface CoordinatorService {
	public void insertCoordinator(Coordinator coordinator);
	public void updateCoordinator(Coordinator coordinator);
	public int deleteCoordinator(Coordinator coordinator);
	public void inactiveCoordinator(Coordinator coordinator);
	public int updateCoorLastLoginDate(String userId);
	
	public Coordinator getCoordinatorByID(String userId);
	public ArrayList<Coordinator> getCoordinatorList(String userId);
	public ArrayList<Coordinator> getCoordinatorList();
	public int getTotal();
	/*
	public ArrayList<Coordinator> getCoordinatorList(String userId);
	public String getSrvyGrpStr(ArrayList<Coordinator> coordinatorList);
	*/
	public ArrayList<String> getSrvyGrpArr(ArrayList<SrvyGrp> srvyGrpList);
	public Boolean isUserInGrp(String srvyGrpStr,int srvyGrpId);
	public Boolean isSuryCoor(ArrayList<SrvyGrp> srvyGrpList);
	public Boolean isSysAdmin(ArrayList<SrvyGrp> srvyGrpList);
	
}
