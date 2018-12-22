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

import javax.sql.DataSource;

import hk.gov.housingauthority.model.SrvyAdtnInfo;
import hk.gov.housingauthority.model.SysCnfg;
import hk.gov.housingauthority.util.Constant;

public class SysCnfgDaoImpl implements SysCnfgDao {

	private DataSource dataSource;

	@Override
	public HashMap<String, SysCnfg> getHashMapByGroup(String group) {
		Connection conn = null;
		ResultSet rs = null;
		HashMap<String, SysCnfg> hashMap = new HashMap<String, SysCnfg>();

		String sql = "SELECT * FROM ESV_SYS_CNFG WHERE SYS_CNFG_GRP=?";

		try {
			conn = dataSource.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, group);
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				hashMap.put(rs.getString("SYS_CNFG_ID"), getSysCnfg(rs));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
		return hashMap;
	}

	@Override
	public List<SysCnfg> getArrayListByGroup(String group) {
		Connection conn = null;
		ResultSet rs = null;
		List<SysCnfg> resultList = new ArrayList<SysCnfg>();

		String sql = "SELECT * FROM ESV_SYS_CNFG WHERE SYS_CNFG_GRP=?";

		try {
			conn = dataSource.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, group);
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				resultList.add(getSysCnfg(rs));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
		return resultList;
	}

	public ArrayList<SysCnfg> getSysCnfgList(SysCnfg SysCnfg, String colOrder, int start, int length) {
		Connection conn = null;
		ResultSet rs = null;
		String filter = " LAST_REC_TXN_TYPE_CODE!='" + Constant.LAST_REC_TXN_TYPE_CODE_DELETED + "' ";
		if ("".equals(colOrder)) {
			colOrder = " ORDER BY [SYS_CNFG_GRP], [SYS_CNFG_VAL] ";
		} else {
			colOrder = " ORDER BY " + colOrder;
		}

		ArrayList<SysCnfg> sysCnfgList = new ArrayList<SysCnfg>();
		String condition = "SELECT ROW_NUMBER() OVER(" + colOrder + ") AS _ROW_, COUNT(*) OVER() AS _TOTAL_, ESC.* "
				+ "FROM [dbo].[ESV_SYS_CNFG] AS ESC " + "WHERE " + filter;
		String sql = "SELECT * FROM (" + condition + ") AS PAGE WHERE _ROW_ BETWEEN ? AND ? " + colOrder;
		try {
			conn = dataSource.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, start + 1);
			preparedStatement.setInt(2, start + length);
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				sysCnfgList.add(this.getSysCnfg(rs));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
		return sysCnfgList;
	}

	public SysCnfg getSysCnfg(ResultSet rs) {
		SysCnfg sysCnfg = new SysCnfg();
		try {
			sysCnfg.setTotRec(rs.getInt("_TOTAL_"));
		} catch (Exception ex) {
		}
		try {
			sysCnfg.setSysCnfgId(rs.getNString("SYS_CNFG_ID"));
		} catch (Exception ex) {
		}
		try {
			sysCnfg.setSysCnfgId(rs.getNString("SYS_CNFG_ID"));
		} catch (Exception ex) {
		}
		try {
			sysCnfg.setSysCnfgDesc(rs.getNString("SYS_CNFG_DESC"));
		} catch (Exception ex) {
		}
		try {
			sysCnfg.setSysCnfgVal(rs.getNString("SYS_CNFG_VAL"));
		} catch (Exception ex) {
		}
		try {
			sysCnfg.setSysCnfgGrp(rs.getNString("SYS_CNFG_GRP"));
		} catch (Exception ex) {
		}
		try {
			sysCnfg.setLastRecTxnUserId(rs.getNString("LAST_REC_TXN_USER_ID"));
		} catch (Exception ex) {
		}
		try {
			sysCnfg.setLastRecTxnTypeCode(rs.getNString("LAST_REC_TXN_TYPE_CODE"));
		} catch (Exception ex) {
		}
		try {
			sysCnfg.setLastRecTxnDate(rs.getTimestamp("LAST_REC_TXN_DATE"));
		} catch (Exception ex) {
		}
		return sysCnfg;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public int insertSysCnfg(SysCnfg sysCnfg) {
		int last_inserted_id = 0;
		PreparedStatement preparedStatement = null;
		Connection conn = null;
		String insertSql = "INSERT [dbo].[ESV_SYS_CNFG] ("
				+ "[SYS_CNFG_ID],[SYS_CNFG_DESC],[SYS_CNFG_VAL],"
				+ "[SYS_CNFG_GRP],[LAST_REC_TXN_USER_ID],[LAST_REC_TXN_TYPE_CODE]"
				+ ",[LAST_REC_TXN_DATE]"
				+ ") VALUES (?, ?, ?, ?, ?, ?, ?)";
		try {
			conn = dataSource.getConnection();
			preparedStatement = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setNString(1, sysCnfg.getSysCnfgId());
			preparedStatement.setNString(2, sysCnfg.getSysCnfgDesc());
			preparedStatement.setNString(3, sysCnfg.getSysCnfgVal());
			preparedStatement.setNString(4, sysCnfg.getSysCnfgGrp());
			preparedStatement.setNString(5, sysCnfg.getLastRecTxnUserId());
			preparedStatement.setNString(6, Constant.LAST_REC_TXN_TYPE_CODE_ACTIVE);
			preparedStatement.setObject(7, new Date());
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
	public int updateSysCnfg(SysCnfg sysCnfg) {
		int updateCount = 0;
		PreparedStatement preparedStatement = null;
		Connection conn = null;
		String updateSql = "UPDATE [dbo].[ESV_SYS_CNFG] SET "
				+ "[SYS_CNFG_DESC]=?,"
				+ "[SYS_CNFG_VAL]=?,"
				+ "[SYS_CNFG_GRP]=?,"
				+ "[LAST_REC_TXN_USER_ID]=?,"
				+ "[LAST_REC_TXN_TYPE_CODE]=?,"
				+ "[LAST_REC_TXN_DATE]=? "
				+ "WHERE [SYS_CNFG_ID]=? AND [LAST_REC_TXN_TYPE_CODE]!=?";
		try {
			conn = dataSource.getConnection();
			preparedStatement = conn.prepareStatement(updateSql);
			preparedStatement.setNString(1, sysCnfg.getSysCnfgDesc());
			preparedStatement.setNString(2, sysCnfg.getSysCnfgVal());
			preparedStatement.setNString(3, sysCnfg.getSysCnfgGrp());
			preparedStatement.setNString(4, sysCnfg.getLastRecTxnUserId());
			preparedStatement.setNString(5, Constant.LAST_REC_TXN_TYPE_CODE_UPDATE);
			preparedStatement.setObject(6, new Date());
			preparedStatement.setNString(7, sysCnfg.getSysCnfgId());
			preparedStatement.setNString(8, Constant.LAST_REC_TXN_TYPE_CODE_DELETED);
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
	
	@Override
	public int deleteSysCnfg(SysCnfg sysCnfg) {
		int updateCount = 0;
		PreparedStatement preparedStatement = null;
		Connection conn = null;
		String updateSql = "UPDATE [dbo].[ESV_SYS_CNFG] SET "
				+ "[LAST_REC_TXN_USER_ID]=?,"
				+ "[LAST_REC_TXN_TYPE_CODE]=?,"
				+ "[LAST_REC_TXN_DATE]=? "
				+ "WHERE [SYS_CNFG_ID]=? AND [LAST_REC_TXN_TYPE_CODE]!=?";
		try {
			conn = dataSource.getConnection();
			preparedStatement = conn.prepareStatement(updateSql);
			preparedStatement.setNString(1, sysCnfg.getLastRecTxnUserId());
			preparedStatement.setNString(2, Constant.LAST_REC_TXN_TYPE_CODE_DELETED);
			preparedStatement.setObject(3, new Date());
			preparedStatement.setNString(4, sysCnfg.getSysCnfgId());
			preparedStatement.setNString(5, Constant.LAST_REC_TXN_TYPE_CODE_DELETED);
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

	@Override
	public String getVaueByCode(String code) {
		String result = "";
		Connection conn = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM [dbo].[ESV_SYS_CNFG] WHERE [SYS_CNFG_ID]=? AND LAST_REC_TXN_TYPE_CODE!=?";
		try {
			conn = dataSource.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setNString(1, code);
			preparedStatement.setNString(2, Constant.LAST_REC_TXN_TYPE_CODE_DELETED);
			rs = preparedStatement.executeQuery();
			if (rs.next()) {
				result = rs.getNString("SYS_CNFG_VAL");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
		return result;
	}
}
