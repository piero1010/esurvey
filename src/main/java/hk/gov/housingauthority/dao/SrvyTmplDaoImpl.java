package hk.gov.housingauthority.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import hk.gov.housingauthority.model.SrvyTmpl;
import hk.gov.housingauthority.util.Constant;

public class SrvyTmplDaoImpl implements SrvyTmplDao {

	private DataSource dataSource;

	public int insertSrvyTmpl(SrvyTmpl srvyTmpl) {
		int last_inserted_id = 0;
		PreparedStatement preparedStatement = null;
		Connection conn = null;
		String insertSql = "INSERT [dbo].[ESV_SRVY_TMPL] ("
				+ "[SRVY_TMPL_NAME], [SRVY_TMPL_CNFG], [SRVY_TMPL_FRZ_IND], "
				+ "[SRVY_GRP_ID], [SRVY_USER_ID], [SRVY_RPT_CAT], [SRVY_CRE_DATE],"
				+ "[LAST_REC_TXN_USER_ID], [LAST_REC_TXN_TYPE_CODE], [LAST_REC_TXN_DATE]"
				+ ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			conn = dataSource.getConnection();
			preparedStatement = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setNString(1, srvyTmpl.getSrvyTmplName());
			preparedStatement.setNString(2, srvyTmpl.getSrvyTmplCnfg());
			preparedStatement.setNString(3, srvyTmpl.getSrvyTmplFrzInd().getSrvyTmplFrzIndId());
			preparedStatement.setInt(4, srvyTmpl.getSrvyGrp().getSrvyGrpId());
			preparedStatement.setNString(5, srvyTmpl.getSrvyUser().getUserId());
			preparedStatement.setInt(6, srvyTmpl.getSrvyRptCat());
			preparedStatement.setObject(7, new Date());
			preparedStatement.setNString(8, srvyTmpl.getLastRecTxnUserId());
			preparedStatement.setNString(9, "A"); // A - Add, U - Update, D - Delete
			preparedStatement.setObject(10, new Date());
			preparedStatement.execute();
			ResultSet rs = preparedStatement.getGeneratedKeys();
			if (rs.next()) {
				last_inserted_id = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
		return last_inserted_id;
	}

	@Override
	public int updateSrvyTmpl(SrvyTmpl srvyTmpl) {
		int updateCount = 0;
		PreparedStatement preparedStatement = null;
		Connection conn = null;
		String updateSql = "UPDATE [dbo].[ESV_SRVY_TMPL] SET "
				+ "[SRVY_TMPL_NAME]=?, [SRVY_TMPL_CNFG]=?, [SRVY_TMPL_FRZ_IND]=?, "
				+ "[SRVY_GRP_ID]=?,"
				+ "[LAST_REC_TXN_USER_ID]=?, [LAST_REC_TXN_TYPE_CODE]=?, [LAST_REC_TXN_DATE]=? "
				+ "WHERE [SRVY_TMPL_ID]=?";
		try {
			conn = dataSource.getConnection();
			preparedStatement = conn.prepareStatement(updateSql);
			preparedStatement.setNString(1, srvyTmpl.getSrvyTmplName());
			preparedStatement.setNString(2, srvyTmpl.getSrvyTmplCnfg());
			preparedStatement.setNString(3, srvyTmpl.getSrvyTmplFrzInd().getSrvyTmplFrzIndId());
			preparedStatement.setInt(4, srvyTmpl.getSrvyGrp().getSrvyGrpId());
			preparedStatement.setNString(5, srvyTmpl.getLastRecTxnUserId());
			preparedStatement.setNString(6, "U"); // A - Add, U - Update, D - Delete
			preparedStatement.setObject(7, new Date());
			preparedStatement.setInt(8, srvyTmpl.getSrvyTmplId());
			updateCount = preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
		return updateCount;
	}

	public SrvyTmpl getSrvyTmplByID(int srvyTmplID) {
		Connection conn;
		ResultSet rs = null;
		SrvyTmpl srvyTmpl = null;
		String sql = "select EST.*,EU.USER_DSGN, ESG.SRVY_GRP_NAME from [dbo].[ESV_SRVY_TMPL] AS EST "
				+ "INNER JOIN [dbo].[ESV_SRVY_GRP] AS ESG ON EST.SRVY_GRP_ID=ESG.SRVY_GRP_ID "
				+ "INNER JOIN [dbo].[ESV_USER] AS EU ON EU.USER_ID=EST.SRVY_USER_ID "
				+ "WHERE EST.SRVY_TMPL_ID = ?";
		try {
			conn = dataSource.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, srvyTmplID);
			rs = preparedStatement.executeQuery();
			if (rs.next()) {
				srvyTmpl = getSrvyTmpl(rs);
			}
			conn.close();

			return srvyTmpl;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("[SrvyTmplDao] Cannot get Survey Template : " + srvyTmplID);
			return null;
		}
	}

	public int getRptCatBySrvyID(int srvyRecId) {
		Connection conn;
		ResultSet rs = null;
		int srvyRptCat = 0;
		String sql = "SELECT SRVY_RPT_CAT FROM ESV_SRVY_TMPL t, ESV_SRVY_REC r "
				+ "where t.SRVY_TMPL_ID = r.SRVY_TMPL_ID AND r.SRVY_REC_ID = ? ";
		try {
			conn = dataSource.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, srvyRecId);
			rs = preparedStatement.executeQuery();
			if (rs.next()) {
				srvyRptCat = rs.getInt("SRVY_RPT_CAT");
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("[SrvyTmplDao] Cannot get report category of the survey : " + srvyRecId);
		}
		return srvyRptCat;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public List<SrvyTmpl> getSrvyTmplList(SrvyTmpl srvyTmplFilter, String colOrder, int start, int length) {
		Connection conn = null;
		ResultSet rs = null;
		String filter=" 1=1 ";
		
		if(srvyTmplFilter.getSrvyTmplId()!=null){
			filter+=" AND SRVY_TMPL_ID = "+srvyTmplFilter.getSrvyTmplId();
		}
		if(srvyTmplFilter.getSrvyTmplName()!=null && !"".equals(srvyTmplFilter.getSrvyTmplName())){
			filter+=" AND SRVY_TMPL_NAME like '%"+srvyTmplFilter.getSrvyTmplName()+"%' ";
		}
		if(srvyTmplFilter.getSrvyGrp().getSrvyGrpId()!=null){
			filter+=" AND ESG.SRVY_GRP_ID = '"+srvyTmplFilter.getSrvyGrp().getSrvyGrpId()+"' ";
		}
		if(srvyTmplFilter.getSrvyTmplFrzInd().getSrvyTmplFrzIndId()!=null && !"".equals(srvyTmplFilter.getSrvyTmplFrzInd().getSrvyTmplFrzIndId())){
			filter+=" AND SRVY_TMPL_FRZ_IND = '"+srvyTmplFilter.getSrvyTmplFrzInd().getSrvyTmplFrzIndId()+"' ";
		}
		if(srvyTmplFilter.getFilterList().get("SSN_USER_SRVY_GRP")!=null){ /* list surveys in survey group only */
			filter+=" AND ESG.SRVY_GRP_ID in ("+srvyTmplFilter.getFilterList().get("SSN_USER_SRVY_GRP")+")";
		}
		if("".equals(colOrder)){
			colOrder = " ORDER BY SRVY_TMPL_ID DESC ";
		}else{
			colOrder = " ORDER BY "+colOrder;
		}
		
		List<SrvyTmpl> srvyTmplList = new ArrayList<SrvyTmpl>();
		String condition = "SELECT ROW_NUMBER() OVER(" + colOrder + ") AS _ROW_, COUNT(*) OVER() AS _TOTAL_, EST.*,EU.USER_DSGN, ESG.SRVY_GRP_NAME "
				+ "FROM [dbo].[ESV_SRVY_TMPL] AS EST "
				+ "INNER JOIN [dbo].[ESV_SRVY_GRP] AS ESG ON EST.SRVY_GRP_ID=ESG.SRVY_GRP_ID "
				+ "INNER JOIN [dbo].[ESV_USER] AS EU ON EU.USER_ID=EST.SRVY_USER_ID "
				+ "WHERE "+filter;
		String sql = "SELECT * FROM ("+condition+") AS PAGE WHERE _ROW_ BETWEEN ? AND ? "+colOrder;
		
		try {
			conn = dataSource.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, start + 1);
			preparedStatement.setInt(2, start + length);
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				srvyTmplList.add(this.getSrvyTmpl(rs));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
		return srvyTmplList;
	}
	
	@Override
	public List<SrvyTmpl> getSrvyTmplListByCoorGrpList(List<String> coorGrpList) {
		Connection conn = null;
		ResultSet rs = null;
		List<SrvyTmpl> srvyTmplList = new ArrayList<SrvyTmpl>();
		String sql = "select * from [dbo].[ESV_SRVY_TMPL] AS EST "
				+ "INNER JOIN [dbo].[ESV_SRVY_GRP] AS ESG ON EST.SRVY_GRP_ID=ESG.SRVY_GRP_ID "
				+ "INNER JOIN [dbo].[ESV_USER] AS EU ON EU.USER_ID=EST.SRVY_USER_ID "
				+ "WHERE EST.SRVY_GRP_ID IN ("+String.join(", ", coorGrpList)+") ORDER BY SRVY_TMPL_ID DESC";
		try {
			conn = dataSource.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				srvyTmplList.add(this.getSrvyTmpl(rs));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
		return srvyTmplList;
	}
	
	public static SrvyTmpl getSrvyTmpl(ResultSet rs){
		SrvyTmpl srvyTmpl = new SrvyTmpl();
		try{srvyTmpl.setTotRec(rs.getInt("_TOTAL_"));}catch(Exception ex){}
		try{srvyTmpl.setSrvyTmplId(rs.getInt("SRVY_TMPL_ID"));}catch(Exception ex){}
		try{srvyTmpl.setSrvyTmplName(rs.getString("SRVY_TMPL_NAME"));}catch(Exception ex){}
		try{srvyTmpl.setSrvyTmplCnfg(rs.getString("SRVY_TMPL_CNFG"));}catch(Exception ex){}
		try{srvyTmpl.getSrvyTmplFrzInd().setSrvyTmplFrzIndId(rs.getString("SRVY_TMPL_FRZ_IND"));}catch(Exception ex){}
		try{srvyTmpl.getSrvyGrp().setSrvyGrpId(rs.getInt("SRVY_GRP_ID"));}catch(Exception ex){}
		try{srvyTmpl.getSrvyGrp().setSrvyGrpName(rs.getString("SRVY_GRP_NAME"));}catch(Exception ex){}
		try{srvyTmpl.getSrvyUser().setUserId(rs.getString("SRVY_USER_ID"));}catch(Exception ex){}
		try{srvyTmpl.getSrvyUser().setUserDsgn(rs.getString("USER_DSGN"));}catch(Exception ex){}
		try{srvyTmpl.setSrvyRptCat(rs.getInt("SRVY_RPT_CAT"));}catch(Exception ex){}
		try{srvyTmpl.setSrvyCreDate(rs.getDate("SRVY_CRE_DATE"));}catch(Exception ex){}
		try{srvyTmpl.setLastRecTxnUserId(rs.getString("LAST_REC_TXN_USER_ID"));}catch(Exception ex){}
		try{srvyTmpl.setLastRecTxnTypeCode(rs.getString("LAST_REC_TXN_TYPE_CODE"));}catch(Exception ex){}
		try{srvyTmpl.setLastRecTxnDate(rs.getTimestamp("LAST_REC_TXN_DATE"));}catch(Exception ex){}
		try{srvyTmpl.setSrvyTmplCss(rs.getString("SRVY_TMPL_CSS"));}catch(Exception ex){}
		return srvyTmpl;
	}

	@Override
	public int cloneSrvyTmpl(SrvyTmpl srvyTmpl) {
		int last_inserted_id = 0;
		PreparedStatement preparedStatement = null;
		Connection conn = null;
		String sql = "INSERT INTO [ESV_SRVY_TMPL]("
				+ "[SRVY_TMPL_CSS],[SRVY_TMPL_NAME],[SRVY_TMPL_CNFG],"
				+ "[SRVY_TMPL_FRZ_IND],[SRVY_GRP_ID],[SRVY_USER_ID],[SRVY_RPT_CAT],"
				+ "[LAST_REC_TXN_USER_ID],[LAST_REC_TXN_TYPE_CODE],[LAST_REC_TXN_DATE],[SRVY_CRE_DATE]) "
				+ "SELECT "
				+ "[SRVY_TMPL_CSS],[SRVY_TMPL_NAME],[SRVY_TMPL_CNFG],"
				+ "'A',[SRVY_GRP_ID],'"+srvyTmpl.getLastRecTxnUserId()+"',[SRVY_RPT_CAT],"
				+ "'"+srvyTmpl.getLastRecTxnUserId()+"','"+Constant.LAST_REC_TXN_TYPE_CODE_ACTIVE+"',GETDATE(),GETDATE() "
				+ "FROM [ESV_SRVY_TMPL] WHERE [SRVY_TMPL_ID]=?";
		try {
			conn = dataSource.getConnection();
			preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, srvyTmpl.getSrvyTmplId());
			preparedStatement.execute();
			ResultSet rs = preparedStatement.getGeneratedKeys();
		if (rs.next()) {
				last_inserted_id = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
		return last_inserted_id;
	}
}
