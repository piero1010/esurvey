package hk.gov.housingauthority.service;

import java.util.List;

import hk.gov.housingauthority.model.MySrvy;

public interface MySrvyService {
	
	public List<MySrvy> getMySrvyList(MySrvy mySrvy,String colOrder, int start, int length);
	public String mySrvyListToJsonStr(List<MySrvy> mySrvyList);

}
