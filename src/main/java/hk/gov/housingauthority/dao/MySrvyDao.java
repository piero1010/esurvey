package hk.gov.housingauthority.dao;

import java.util.List;

import hk.gov.housingauthority.model.MySrvy;

public interface MySrvyDao {
	
	public List<MySrvy> getMySrvyList(MySrvy mySrvy, String colOrder, int start, int length);

}
