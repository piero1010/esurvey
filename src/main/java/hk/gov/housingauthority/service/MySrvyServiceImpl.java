package hk.gov.housingauthority.service;

import java.util.List;

import hk.gov.housingauthority.dao.MySrvyDao;
import hk.gov.housingauthority.model.MySrvy;

public class MySrvyServiceImpl implements MySrvyService {
	
	private MySrvyDao mySrvyDao;
	
	public MySrvyDao getMySrvyDao() {
		return mySrvyDao;
	}

	public void setMySrvyDao(MySrvyDao mySrvyDao) {
		this.mySrvyDao = mySrvyDao;
	}

	@Override
	public List<MySrvy> getMySrvyList(MySrvy mySrvy,String colOrder, int start, int length) {
		return mySrvyDao.getMySrvyList(mySrvy, colOrder, start, length);
	}

	@Override
	public String mySrvyListToJsonStr(List<MySrvy> mySrvyList) {
		String result = new String("[");
		for (int i = 0; i < mySrvyList.size(); i++) {
			result += mySrvyList.get(i).toJsonString();
			if (i + 1 != mySrvyList.size()) {
				result += ",";
			}
		}
		result += "]";
		return result;
	}

}
