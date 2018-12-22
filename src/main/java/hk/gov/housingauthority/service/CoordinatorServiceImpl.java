package hk.gov.housingauthority.service;

import java.util.ArrayList;

import hk.gov.housingauthority.dao.CoordinatorDao;
import hk.gov.housingauthority.model.Coordinator;
import hk.gov.housingauthority.model.SrvyGrp;

public class CoordinatorServiceImpl implements CoordinatorService {

	private CoordinatorDao coordinatorDao;

	public void setCoordinatorDao(CoordinatorDao coordinatorDao) {
		this.coordinatorDao = coordinatorDao;
	}

	public void updateCoordinator(Coordinator coordinator) {
		coordinatorDao.deleteCoordinator(coordinator);
		coordinatorDao.insertCoordinator(coordinator);
	}

	public void inactiveCoordinator(Coordinator coordinator) {
		coordinatorDao.inactiveCoordinator(coordinator);
	}
	
	public void insertCoordinator(Coordinator coordinator) {
		coordinatorDao.insertCoordinator(coordinator);
	}

	public int deleteCoordinator(Coordinator coordinator) {
		return coordinatorDao.deleteCoordinator(coordinator);
	}

	public Coordinator getCoordinatorByID(String userId) {
		return coordinatorDao.getCoordinatorByID(userId);
	}

	public ArrayList<Coordinator> getCoordinatorList() {
		return coordinatorDao.getCoordinatorList();
	}

	public int getTotal() {
		return coordinatorDao.getTotal();
	}

	@Override
	public ArrayList<Coordinator> getCoordinatorList(String userId) {
		return coordinatorDao.getCoordinatorList(userId);
	}

	/*
	 * @Override public ArrayList<Coordinator> getCoordinatorList(String userId)
	 * { // TODO dummy function ArrayList<Coordinator> coordinatorList = new
	 * ArrayList<Coordinator>(); Coordinator coordinator1 = new Coordinator();
	 * coordinator1.setSrvyGrpId(1); coordinatorList.add(coordinator1);
	 * Coordinator coordinator2 = new Coordinator();
	 * coordinator2.setSrvyGrpId(2); coordinatorList.add(coordinator2);
	 * Coordinator coordinator3 = new Coordinator();
	 * coordinator3.setSrvyGrpId(3); coordinatorList.add(coordinator3);
	 * Coordinator coordinator4 = new Coordinator();
	 * coordinator4.setSrvyGrpId(4); coordinatorList.add(coordinator4); return
	 * coordinatorList; }
	 * 
	 * @Override public String getSrvyGrpStr(ArrayList<Coordinator>
	 * coordinatorList) { ArrayList<String> list = new ArrayList<String>(); for
	 * (Coordinator value : coordinatorList) {
	 * list.add(Integer.toString(value.getSrvyGrpId())); } return
	 * list.toString().replace("[", "").replace("]", ""); }
	 * 
	 * @Override public ArrayList<String> getSrvyGrpArr(ArrayList<Coordinator>
	 * coordinatorList) { ArrayList<String> list = new ArrayList<String>(); for
	 * (Coordinator value : coordinatorList) {
	 * list.add(Integer.toString(value.getSrvyGrpId())); } return list; }
	 * 
	 */

	public ArrayList<String> getSrvyGrpArr(ArrayList<SrvyGrp> srvyGrpList) {
		ArrayList<String> list = new ArrayList<String>();
		for (SrvyGrp srvyGrp : srvyGrpList) {
			list.add(Integer.toString(srvyGrp.getSrvyGrpId()));
		}
		return list;
	}

	public Boolean isSuryCoor(ArrayList<SrvyGrp> srvyGrpList) {
		try {
			for (SrvyGrp srvyGrp : srvyGrpList) {
				if (srvyGrp.getSrvyGrpId() > 1) {
					return true;
				}
			}
		} catch (Exception ex) {
			return false;
		}
		return false;
	}

	@Override
	public Boolean isUserInGrp(String srvyGrpStr, int srvyGrpId) {
		try {
			String strarray[] = srvyGrpStr.split(",");
			for (String value : strarray) {
				if (Integer.parseInt(value) == srvyGrpId) {
					return true;
				}
			}
		} catch (Exception ex) {
			return false;
		}
		return false;
	}

	@Override
	public Boolean isSysAdmin(ArrayList<SrvyGrp> srvyGrpList) {
		try {
			for (SrvyGrp srvyGrp : srvyGrpList) {
				if (srvyGrp.getSrvyGrpId() == 1) {
					return true;
				}
			}
		} catch (Exception ex) {
			return false;
		}
		return false;
	}

	@Override
	public int updateCoorLastLoginDate(String userId) {
		return coordinatorDao.updateCoorLastLoginDate(userId);
	}
}
