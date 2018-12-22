package hk.gov.housingauthority.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hk.gov.housingauthority.model.ExcelTemplate;
import hk.gov.housingauthority.util.Constant;

public class FileUploadDaoImpl implements FileUploadDao {
	
	private static final Logger logger = LoggerFactory.getLogger(FileUploadDaoImpl.class);
	
	private DataSource dataSource;

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public boolean saveFileDataInDB(List<ExcelTemplate> recordList) {
		
		PreparedStatement preparedStatement = null;
		Connection conn = null;
		String insertSql = "insert into TT_TABLE (TO_EMAIL, IT_SERVICE_NAME, IT_SERVICE_CODE, NAME_OF_CONTRACTOR) "
				+ " VALUES (?, ?, ?, ?)";
		
		try {
			conn = dataSource.getConnection();

			preparedStatement = conn.prepareStatement(insertSql);
			for (int i = 0; i < recordList.size(); i++) {
				try {
					preparedStatement.setNString(1, recordList.get(i).getTO_EMAIL());
					preparedStatement.setNString(2, recordList.get(i).getIT_SERVICE_NAME());
					preparedStatement.setNString(3, recordList.get(i).getIT_SERVICE_CODE());
					preparedStatement.setNString(4, recordList.get(i).getNAME_OF_CONTRACTOR());

					preparedStatement.execute();
				} catch (Exception ex) {
					logger.error("Cannot insert the following record:");
					logger.error(recordList.get(i).getTO_EMAIL());
					logger.error(recordList.get(i).getIT_SERVICE_NAME());
					logger.error(recordList.get(i).getIT_SERVICE_CODE());
					logger.error(recordList.get(i).getNAME_OF_CONTRACTOR());
					return false;
				} 
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
		
		return true;
		
	}

}
