package hk.gov.housingauthority.service;

import java.util.ArrayList;
import java.util.List;

import hk.gov.housingauthority.model.QueryMySrvyRec;
import hk.gov.housingauthority.model.QuerySrvyRec;
import hk.gov.housingauthority.model.ResultDashboard;
import hk.gov.housingauthority.model.ResultMySrvyRec;
import hk.gov.housingauthority.model.ResultSrvyRec;
import hk.gov.housingauthority.model.SrvyRec;

public interface SrvyRecService {
	public int insertSrvyRec(SrvyRec srvyRec);
	
	public int updateSrvyRec(SrvyRec srvyRec);
	
	public int cloneSrvyRec(SrvyRec srvyRec);
	
	public SrvyRec getSrvyRecByID(int srvyRecId);
	
	public SrvyRec getSrvyRecBySrvyPptId(int srvyPptId);
	
	// Use by Coordinator
	public ArrayList<ResultSrvyRec> searchSrvyRec(QuerySrvyRec querySrvyRec);

	public ArrayList<ResultDashboard> getDashboardData(String string);

	public int getTotal();
	
	public ArrayList<SrvyRec> getSrvyRecList(SrvyRec srvyRec,String colOrder, int start, int length);
	
	public String listToJsonStr(List<SrvyRec> srvyRecList);
	
	public ArrayList<SrvyRec> getTotPpt(ArrayList<SrvyRec> srvyRecList);
	
	public SrvyRec getTotPpt(SrvyRec srvyRec);

	// Use by Participant 
	//public ArrayList<ResultMySrvyRec> searchMySrvyRec(QueryMySrvyRec queryMySrvyRec);

}
