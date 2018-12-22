package hk.gov.housingauthority.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import hk.gov.housingauthority.dao.SrvyGrpDao;
import hk.gov.housingauthority.model.SrvyGrp;

public class SrvyGrpServiceImpl implements SrvyGrpService {

	private SrvyGrpDao srvyGrpDao;

	public void setSrvyGrpDao(SrvyGrpDao srvyGrpDao) {
		this.srvyGrpDao = srvyGrpDao;
	}
	
	public ArrayList<SrvyGrp> getSrvyGrpList(){
		return srvyGrpDao.getSrvyGrpList();
	}
	
	public Map<String, String> getSrvyGrpHashMap() {
		ArrayList<SrvyGrp> srvyGrpList = srvyGrpDao.getSrvyGrpList();
		Map<String, String> srvyGrpMap = new HashMap<String, String>();
		for (SrvyGrp srvyGrp : srvyGrpList) {
			srvyGrpMap.put(Integer.toString(srvyGrp.getSrvyGrpId()),srvyGrp.getSrvyGrpName());
		}
		return srvyGrpMap;
	}

}
