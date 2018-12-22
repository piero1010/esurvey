package hk.gov.housingauthority.service;

import java.util.ArrayList;
import java.util.List;

import hk.gov.housingauthority.dao.SrvyAdtnInfoDao;
import hk.gov.housingauthority.model.SrvyAdtnInfo;

public class SrvyAdtnInfoServiceImpl implements SrvyAdtnInfoService {

	private SrvyAdtnInfoDao srvyAdtnInfoDao;

	@Override
	public boolean isSrvyAdtnInfoExists(SrvyAdtnInfo srvyAdtnInfo) {
		return srvyAdtnInfoDao.isSrvyAdtnInfoExists(srvyAdtnInfo);
	}
	
	@Override
	public int insertSrvyAdtnInfo(SrvyAdtnInfo srvyAdtnInfo) {
		return srvyAdtnInfoDao.insertSrvyAdtnInfo(srvyAdtnInfo);
	}

	@Override
	public int updateSrvyAdtnInfo(SrvyAdtnInfo srvyAdtnInfo) {
		return srvyAdtnInfoDao.updateSrvyAdtnInfo(srvyAdtnInfo);
	}

	@Override
	public void deleteSrvyAdtnInfo(SrvyAdtnInfo srvyAdtnInfo) {
		srvyAdtnInfoDao.deleteSrvyAdtnInfo(srvyAdtnInfo);		
	}

	@Override
	public ArrayList<SrvyAdtnInfo> getSrvyAdtnInfoList(SrvyAdtnInfo srvyAdtnInfo, String colOrder, int start, int length) {		
		return srvyAdtnInfoDao.getSrvyAdtnInfoList(srvyAdtnInfo, colOrder, start, length);
	}

	public SrvyAdtnInfoDao getSrvyAdtnInfoDao() {
		return srvyAdtnInfoDao;
	}

	public void setSrvyAdtnInfoDao(SrvyAdtnInfoDao srvyAdtnInfoDao) {
		this.srvyAdtnInfoDao = srvyAdtnInfoDao;
	}

	@Override
	public String listToJsonStr(List<SrvyAdtnInfo> srvyAdtnInfoList) {
		String result = new String("[");
		for (int i = 0; i < srvyAdtnInfoList.size(); i++) {
			result += srvyAdtnInfoList.get(i).toJsonString();
			if (i + 1 != srvyAdtnInfoList.size()) {
				result += ",";
			}
		}
		result += "]";
		return result;
	}

	@Override
	public ArrayList<SrvyAdtnInfo> getSrvyAdtnInfoList(Integer srvyRecId) {
		return srvyAdtnInfoDao.getSrvyAdtnInfoList(srvyRecId);
	}
}
