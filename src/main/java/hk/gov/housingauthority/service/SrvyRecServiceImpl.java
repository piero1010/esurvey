package hk.gov.housingauthority.service;

import java.util.ArrayList;
import java.util.List;

import hk.gov.housingauthority.dao.SrvyRecDao;
import hk.gov.housingauthority.model.QueryMySrvyRec;
import hk.gov.housingauthority.model.QuerySrvyRec;
import hk.gov.housingauthority.model.ResultDashboard;
import hk.gov.housingauthority.model.ResultMySrvyRec;
import hk.gov.housingauthority.model.ResultSrvyRec;
import hk.gov.housingauthority.model.SrvyRec;

public class SrvyRecServiceImpl implements SrvyRecService {

	private SrvyRecDao srvyRecDao;

	public void setSrvyRecDao(SrvyRecDao srvyRecDao) {
		this.srvyRecDao = srvyRecDao;
	}
	
	public int insertSrvyRec(SrvyRec srvyRec){
		return	srvyRecDao.insertSrvyRec( srvyRec);
	}
	
	public int updateSrvyRec(SrvyRec srvyRec){
		return srvyRecDao.updateSrvyRec(srvyRec);
	}
	
	public SrvyRec getSrvyRecByID(int srvyRecId){
		return srvyRecDao.getSrvyRecByID(srvyRecId);
	}
	
	public ArrayList<ResultSrvyRec> searchSrvyRec(QuerySrvyRec querySrvyRec){
		return srvyRecDao.searchSrvyRec(querySrvyRec);
	}
	
	
	public ArrayList<ResultDashboard> getDashboardData(String srvyGrp){
		return srvyRecDao.getDashboardData(srvyGrp);
	}
	
	public int getTotal(){
		return srvyRecDao.getTotal();
	}

	@Override
	public ArrayList<SrvyRec> getSrvyRecList(SrvyRec srvyRec, String colOrder, int start, int length) {
		return srvyRecDao.getSrvyRecList(srvyRec,colOrder, start, length);
	}

	@Override
	public String listToJsonStr(List<SrvyRec> srvyRecList) {
		String result = new String("[");
		for (int i = 0; i < srvyRecList.size(); i++) {
			result += srvyRecList.get(i).toJsonString();
			if (i + 1 != srvyRecList.size()) {
				result += ",";
			}
		}
		result += "]";
		return result;
	}

	@Override
	public SrvyRec getSrvyRecBySrvyPptId(int srvyPptId) {
		return srvyRecDao.getSrvyRecBySrvyPptId(srvyPptId);
	}

	@Override
	public ArrayList<SrvyRec> getTotPpt(ArrayList<SrvyRec> srvyRecList) {
		return srvyRecDao.getTotPpt(srvyRecList);
	}

	@Override
	public SrvyRec getTotPpt(SrvyRec srvyRec) {
		ArrayList<SrvyRec> srvyRecList = new ArrayList<SrvyRec>();
		srvyRecList.add(srvyRec);
		srvyRecList = srvyRecDao.getTotPpt(srvyRecList);
		return srvyRecList.get(0);
	}

	@Override
	public int cloneSrvyRec(SrvyRec srvyRec) {
		return srvyRecDao.cloneSrvyRec(srvyRec);
	}

	//public ArrayList<ResultMySrvyRec> searchMySrvyRec(QueryMySrvyRec queryMySrvyRec){
	//	return srvyRecDao.searchMySrvyRec(queryMySrvyRec);
	//}

}
