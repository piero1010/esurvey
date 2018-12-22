package hk.gov.housingauthority.dao;

import java.util.ArrayList;

import hk.gov.housingauthority.model.Coordinator;

public interface CoordinatorDao {
	
	public Coordinator getCoordinatorByID(String userId);
	public void insertCoordinator(Coordinator coordinator);
	public int inactiveCoordinator(Coordinator coordinator);
	public int deleteCoordinator(Coordinator coordinator);
	public int updateCoorLastLoginDate(String userId);
	
	public ArrayList<Coordinator> getCoordinatorList();
	public ArrayList<Coordinator> getCoordinatorList(String userId);
	public int getTotal();

}
