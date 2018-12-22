package hk.gov.housingauthority.dao;

import java.util.List;

import hk.gov.housingauthority.model.ExcelTemplate;

public interface FileUploadDao {
	
	public boolean saveFileDataInDB(List<ExcelTemplate> employeeList);

}
