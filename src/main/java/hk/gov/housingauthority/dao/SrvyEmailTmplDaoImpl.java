package hk.gov.housingauthority.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.sql.DataSource;

import hk.gov.housingauthority.model.SrvyEmailTmpl;
import hk.gov.housingauthority.model.SrvyPpt;
import hk.gov.housingauthority.util.Constant;

public class SrvyEmailTmplDaoImpl implements SrvyEmailTmplDao {

	private DataSource dataSource;

	public void insertEmailTmpl(SrvyEmailTmpl srvyEmailTmpl){
		PreparedStatement preparedStatement = null;
		Connection conn;
		
			    		  
		String insertSql = "INSERT [dbo].[ESV_SRVY_EMAIL_TMPL] ( [SRVY_REC_ID] ,[EMAIL_TYPE]"
				+ " ,[EMAIL_SUBJ] ,[EMAIL_CNTN] "
				+ ", [LAST_REC_TXN_USER_ID], [LAST_REC_TXN_TYPE_CODE], [LAST_REC_TXN_DATE]) "
				+ "VALUES (?,?,?,?,?, ?,?)";
		try {
			conn = dataSource.getConnection();

			preparedStatement = conn.prepareStatement(insertSql);
			try {
				preparedStatement.setInt(1, srvyEmailTmpl.getSrvyRecId());
				preparedStatement.setNString(2, srvyEmailTmpl.getEmailType());
				preparedStatement.setNString(3, srvyEmailTmpl.getEmailSubj());
				preparedStatement.setNString(4, srvyEmailTmpl.getEmailCntn());

				// todo
				preparedStatement.setNString(5, srvyEmailTmpl.getLastRecTxnUserId());
				preparedStatement.setNString(6, Constant.LAST_REC_TXN_TYPE_CODE_ACTIVE);	
				preparedStatement.setObject(7, new Date());
				preparedStatement.execute();
			} catch (Exception ex) {
				System.out.println("[SrvyEmailTmplDao] Cannot insert : " + srvyEmailTmpl.getSrvyRecId());
			}
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int updateEmailTmpl(SrvyEmailTmpl srvyEmailTmpl){
	
		// fail return = 0 
		PreparedStatement preparedStatement = null;
		Connection conn;
					    		  
		String updateSql = "UPDATE [dbo].[ESV_SRVY_EMAIL_TMPL] SET "
			      +"[EMAIL_SUBJ] = ? ,[EMAIL_CNTN] = ?, "
			      +"[LAST_REC_TXN_USER_ID] = ?, [LAST_REC_TXN_TYPE_CODE] = ? , [LAST_REC_TXN_DATE] = ? "
				+ "WHERE [SRVY_REC_ID] = ? AND [EMAIL_TYPE] = ? ";

		int intResults = 0;
		try {
			conn = dataSource.getConnection();

			preparedStatement = conn.prepareStatement(updateSql);
			try {
				preparedStatement.setNString(1, srvyEmailTmpl.getEmailSubj());
				preparedStatement.setNString(2, srvyEmailTmpl.getEmailCntn());
				preparedStatement.setNString(3, srvyEmailTmpl.getLastRecTxnUserId());
				preparedStatement.setNString(4, Constant.LAST_REC_TXN_TYPE_CODE_UPDATE);
				preparedStatement.setObject(5, new Date());
				preparedStatement.setObject(6, srvyEmailTmpl.getSrvyRecId());
				preparedStatement.setObject(7, srvyEmailTmpl.getEmailType());
				intResults = preparedStatement.executeUpdate();
			} catch (Exception ex) {
				System.out.println("[SrvyEmailTmplDao] Cannot Update : " + srvyEmailTmpl.getSrvyEmailTmplId());
			}
			conn.close();
			 return intResults;

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public SrvyEmailTmpl getEmailTmplByID(int srvyEmailTmplId){
	
		Connection conn;
		ResultSet rs = null;
		SrvyEmailTmpl srvyEmailTmpl = null;
		
		String sql = "select [SRVY_EMAIL_TMPL_ID] ,[SRVY_REC_ID] ,[EMAIL_TYPE] ,[EMAIL_SUBJ] ,[EMAIL_CNTN] "
      + ",[LAST_REC_TXN_USER_ID], [LAST_REC_TXN_TYPE_CODE], [LAST_REC_TXN_DATE] "
      + "from [dbo].[ESV_SRVY_EMAIL_TMPL] where [SRVY_EMAIL_TMPL_ID] = ?";
		try {
			conn = dataSource.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, srvyEmailTmplId);
			rs = preparedStatement.executeQuery();
			if (rs.next()) {
				srvyEmailTmpl = new SrvyEmailTmpl();

				srvyEmailTmpl.setSrvyEmailTmplId(rs.getInt("SRVY_EMAIL_TMPL_ID"));
				srvyEmailTmpl.setSrvyRecId(rs.getInt("SRVY_REC_ID"));
				srvyEmailTmpl.setEmailType(rs.getString("EMAIL_TYPE"));
				srvyEmailTmpl.setEmailSubj(rs.getString("EMAIL_SUBJ"));
				srvyEmailTmpl.setEmailCntn(rs.getString("EMAIL_CNTN"));
				
				srvyEmailTmpl.setLastRecTxnUserId(rs.getString("LAST_REC_TXN_USER_ID"));
				srvyEmailTmpl.setLastRecTxnTypeCode(rs.getString("LAST_REC_TXN_TYPE_CODE"));
				srvyEmailTmpl.setLastRecTxnDate(rs.getTimestamp("LAST_REC_TXN_DATE"));
			}
			conn.close();

			return srvyEmailTmpl;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("[SrvyEmailTmplDao] Cannot get the template of the id : " + srvyEmailTmplId);
			return null;
		}
	}
	
	public ArrayList<SrvyEmailTmpl> getEmailTmplBySrvyRecId(int srvyRecId){
		
		Connection conn;
		ResultSet rs = null;
		ArrayList<SrvyEmailTmpl> srvyEmailTmplList = new ArrayList<SrvyEmailTmpl>();
		
		String sql = "select [SRVY_EMAIL_TMPL_ID] ,[SRVY_REC_ID] ,[EMAIL_TYPE] ,[EMAIL_SUBJ] ,[EMAIL_CNTN] "
      + ",[LAST_REC_TXN_USER_ID], [LAST_REC_TXN_TYPE_CODE], [LAST_REC_TXN_DATE] "
      + "from [dbo].[ESV_SRVY_EMAIL_TMPL] where [SRVY_REC_ID] = ?";
		try {
			conn = dataSource.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, srvyRecId);
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				srvyEmailTmplList.add(getSrvyEmailTmpl(rs));
			}
			conn.close();
			return srvyEmailTmplList;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("[SrvyEmailTmplDao] Cannot get the template of the id : " + srvyRecId);
			return null;
		}
	}
	
	public SrvyEmailTmpl getSrvyEmailTmpl(ResultSet rs) throws SQLException{
		SrvyEmailTmpl srvyEmailTmpl = new SrvyEmailTmpl();

		try{srvyEmailTmpl.setSrvyEmailTmplId(rs.getInt("SRVY_EMAIL_TMPL_ID"));}catch(Exception ex){}
		try{srvyEmailTmpl.setSrvyRecId(rs.getInt("SRVY_REC_ID"));}catch(Exception ex){}
		try{srvyEmailTmpl.setEmailType(rs.getString("EMAIL_TYPE"));}catch(Exception ex){}
		try{srvyEmailTmpl.setEmailSubj(rs.getString("EMAIL_SUBJ"));}catch(Exception ex){}
		try{srvyEmailTmpl.setEmailCntn(rs.getString("EMAIL_CNTN"));}catch(Exception ex){}
		try{srvyEmailTmpl.setLastRecTxnUserId(rs.getString("LAST_REC_TXN_USER_ID"));}catch(Exception ex){}
		try{srvyEmailTmpl.setLastRecTxnTypeCode(rs.getString("LAST_REC_TXN_TYPE_CODE"));}catch(Exception ex){}
		try{srvyEmailTmpl.setLastRecTxnDate(rs.getTimestamp("LAST_REC_TXN_DATE"));}catch(Exception ex){}
		
		return srvyEmailTmpl;
	}

	public SrvyEmailTmpl getEmailTmpl(int srvyRecId, String emailType){
		
		Connection conn;
		ResultSet rs = null;
		SrvyEmailTmpl srvyEmailTmpl = null;
		
		String sql = "select [SRVY_EMAIL_TMPL_ID] ,[SRVY_REC_ID] ,[EMAIL_TYPE] ,[EMAIL_SUBJ] ,[EMAIL_CNTN] "
      + ",[LAST_REC_TXN_USER_ID], [LAST_REC_TXN_TYPE_CODE], [LAST_REC_TXN_DATE] "
      + "from [dbo].[ESV_SRVY_EMAIL_TMPL] where [SRVY_REC_ID] = ? AND [EMAIL_TYPE] = ?";
		try {
			conn = dataSource.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, srvyRecId);
			preparedStatement.setNString(2, emailType);
			
			rs = preparedStatement.executeQuery();
			if (rs.next()) {
				srvyEmailTmpl = new SrvyEmailTmpl();

				srvyEmailTmpl.setSrvyEmailTmplId(rs.getInt("SRVY_EMAIL_TMPL_ID"));
				srvyEmailTmpl.setSrvyRecId(rs.getInt("SRVY_REC_ID"));
				srvyEmailTmpl.setEmailType(rs.getString("EMAIL_TYPE"));
				srvyEmailTmpl.setEmailSubj(rs.getString("EMAIL_SUBJ"));
				srvyEmailTmpl.setEmailCntn(rs.getString("EMAIL_CNTN"));
				
				srvyEmailTmpl.setLastRecTxnUserId(rs.getString("LAST_REC_TXN_USER_ID"));
				srvyEmailTmpl.setLastRecTxnTypeCode(rs.getString("LAST_REC_TXN_TYPE_CODE"));
				srvyEmailTmpl.setLastRecTxnDate(rs.getTimestamp("LAST_REC_TXN_DATE"));
			}
			conn.close();

			return srvyEmailTmpl;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("[SrvyEmailTmplDao] Cannot get the template of the survey Record id : " + srvyRecId);
			return null;
		}
	}
	


	public int getTotal() {
		int total = 0;
		Connection conn;
		ResultSet rs = null;
		String sql = "SELECT COUNT(*) FROM ESV_SRVY_EMAIL_TMPL";
		try {
			conn = dataSource.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			rs = preparedStatement.executeQuery();
			if (rs.next()) {
				total = rs.getInt(1);
			}
			conn.close();
		} catch (Exception e) {
			return total;
		}
		return total;
	}
	
	
	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
}
