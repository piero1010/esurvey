
package hk.gov.housingauthority.dao;

import java.util.ArrayList;

import hk.gov.housingauthority.model.QuerySrvyRec;
import hk.gov.housingauthority.model.ResultDashboard;
import hk.gov.housingauthority.model.ResultSrvyRec;
import hk.gov.housingauthority.model.SrvyRec;

public interface SrvyRecDao {
	
	public int insertSrvyRec(SrvyRec srvyRec);
	
	public int updateSrvyRec(SrvyRec srvyRec);
	
	public int cloneSrvyRec(SrvyRec srvyRec);
	
	public SrvyRec getSrvyRecByID(int srvyRecId);
	
	// Use by Coordinator
	public ArrayList<ResultSrvyRec> searchSrvyRec(QuerySrvyRec querySrvyRec);

	public ArrayList<SrvyRec> getSrvyRecList(SrvyRec srvyRec,String colOrder, int start, int length);
	
	// batch job
	public int UpdateCmpltSts();
	
	// dashboard
	public ArrayList<ResultDashboard> getDashboardData(String srvyGrp);
	
	public int getTotal();

	public SrvyRec getSrvyRecBySrvyPptId(int srvyPptId);
	
	public ArrayList<SrvyRec> getTotPpt(ArrayList<SrvyRec> srvyRecList);
	
}
