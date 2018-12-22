package hk.gov.housingauthority.dao;

import java.util.ArrayList;

import hk.gov.housingauthority.model.SrvyAdtnInfo;

public interface SrvyAdtnInfoDao {
	public int insertSrvyAdtnInfo(SrvyAdtnInfo srvyAdtnInfo);
	public int updateSrvyAdtnInfo(SrvyAdtnInfo srvyAdtnInfo);
	public void deleteSrvyAdtnInfo(SrvyAdtnInfo srvyAdtnInfo);
	public ArrayList<SrvyAdtnInfo> getSrvyAdtnInfoList(SrvyAdtnInfo srvyAdtnInfo, String colOrder, int start, int length);
	public ArrayList<SrvyAdtnInfo> getSrvyAdtnInfoList(Integer srvyRecId);
	public boolean isSrvyAdtnInfoExists(SrvyAdtnInfo srvyAdtnInfo);
}
