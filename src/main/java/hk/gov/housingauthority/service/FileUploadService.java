package hk.gov.housingauthority.service;

import java.io.File;

public interface FileUploadService {
	
	public String uploadFileData(File file, int srvyRecId);

}
