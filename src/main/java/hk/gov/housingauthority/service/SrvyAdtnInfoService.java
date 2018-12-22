package hk.gov.housingauthority.service;

import java.util.ArrayList;
import java.util.List;

import hk.gov.housingauthority.model.SrvyAdtnInfo;

public interface SrvyAdtnInfoService {	
	public int insertSrvyAdtnInfo(SrvyAdtnInfo srvyAdtnInfo);
	public int updateSrvyAdtnInfo(SrvyAdtnInfo srvyAdtnInfo);
	public void deleteSrvyAdtnInfo(SrvyAdtnInfo srvyAdtnInfo);
	public ArrayList<SrvyAdtnInfo> getSrvyAdtnInfoList(SrvyAdtnInfo srvyAdtnInfo, String colOrder, int start, int length);
	public ArrayList<SrvyAdtnInfo> getSrvyAdtnInfoList(Integer srvyRecId);
	public String listToJsonStr(List<SrvyAdtnInfo> srvyAdtnInfoList);
	public boolean isSrvyAdtnInfoExists(SrvyAdtnInfo srvyAdtnInfo);
}
