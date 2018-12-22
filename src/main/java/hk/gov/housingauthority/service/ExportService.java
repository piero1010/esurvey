package hk.gov.housingauthority.service;

import java.io.ByteArrayOutputStream;

public interface ExportService {
	public ByteArrayOutputStream exportSrvy(int srvyRecId);
}
