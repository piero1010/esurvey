package hk.gov.housingauthority.service;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import hk.gov.housingauthority.dao.SrvyPptDao;
import hk.gov.housingauthority.dao.SrvyTmplDao;
import hk.gov.housingauthority.model.SrvyPpt;
import hk.gov.housingauthority.util.Constant;

public class ExportServiceImpl implements ExportService {

	private SrvyPptDao srvyPptDao;
	private SrvyTmplDao srvyTmplDao;

	public void setSrvyPptDao(SrvyPptDao srvyPptDao) {
		this.srvyPptDao = srvyPptDao;
	}

	public void setSrvyTmplDao(SrvyTmplDao srvyTmplDao) {
		this.srvyTmplDao = srvyTmplDao;
	}

	public ByteArrayOutputStream exportSrvy(int srvyRecId) {
		ByteArrayOutputStream byteStream = null;
		ArrayList<SrvyPpt> srvyPptList = srvyPptDao.getSrvyPptListBySrvyRecId(srvyRecId);
		
		if (srvyPptList.size() > 0) {
			int srvyRptCat =srvyTmplDao.getRptCatBySrvyID(srvyRecId);
			switch (srvyRptCat) {
			case Constant.REPORT_CATEGORY_BAROMETER:
				byteStream = exportBarometer(srvyPptList);
				break;
			case Constant.REPORT_CATEGORY_APP_SUPPORT:
				byteStream = exportDefault(srvyPptList, true, true);
				break;
			case Constant.REPORT_CATEGORY_IT_SUPPORT:
				byteStream = exportDefault(srvyPptList, true, false);
				break;
			default:
				byteStream = exportDefault(srvyPptList, false, false);
				break;
			}
			System.out.println("Constructed outputStream !");
			return byteStream;
		}else{
			return null;
		}
	}
	
	public ByteArrayOutputStream exportDefault(ArrayList<SrvyPpt> srvyPptList, boolean boolShowName, boolean boolShowSrvcCode) {

		System.out.println("Creating excel (default)");

		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Survey Data");
		int rowNum = 0;
		int colNum = 0;
		Row row ;
		Cell cell;
		boolean hdrFlag = true;
		
		try {
			ArrayList<String> colHdrList = null;

			for (SrvyPpt srvyPpt : srvyPptList) {
				String srvyCntn = srvyPpt.getSrvyCntn();

				// process filled survey 
				if (!("D".equals(srvyPpt.getLastRecTxnTypeCode())) && "Y".equals(srvyPpt.getSbmtSts()) && srvyCntn != null) {
					
					Object cntnObj = new JSONParser().parse("{" + srvyCntn + "}");
					JSONObject cntnJson = (JSONObject) cntnObj;

					Map<String,String> cntnMap = Json2map(cntnJson);
					
					Map<String, String> treeMap = new TreeMap<>(cntnMap);
					
					// Create Header list
					if (hdrFlag) {
						
						// Export Header
						row = sheet.createRow(rowNum++);
						colNum = 0;
						cell = row.createCell(colNum++);
						cell.setCellValue("Record ID");
						cell = row.createCell(colNum++);
						cell.setCellValue("Submit Time");
						if (boolShowName){
							cell = row.createCell(colNum++);
							cell.setCellValue("Participant");
						}
						if (boolShowSrvcCode){
							cell = row.createCell(colNum++);
							cell.setCellValue("IT Service");
						}
						for (String colHeader : treeMap.keySet()) {
							cell = row.createCell(colNum++);
							cell.setCellValue(colHeader);
						}
						hdrFlag = false;
					}

					// Data
					colNum = 0;
					row = sheet.createRow(rowNum++);
					cell = row.createCell(colNum++);
					cell.setCellValue(srvyPpt.getSrvyPptId());
					cell = row.createCell(colNum++);
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String sSbmtDate = dateFormat.format(srvyPpt.getSbmtDate());
					cell.setCellValue(sSbmtDate);
					if (boolShowName){
						cell = row.createCell(colNum++);
						if (srvyPpt.getUser() == null){
							cell.setCellValue("");
						}else{
							if (srvyPpt.getUser().getUserId()==null){
								cell.setCellValue("");
							}else{
								cell.setCellValue(srvyPpt.getUser().getUserId());
							}
						}
					}
					if (boolShowSrvcCode){
						cell = row.createCell(colNum++);
						cell.setCellValue(srvyPpt.getItSrvcCode());
					}
					for (String colHeader : treeMap.keySet()) {
						cell = row.createCell(colNum++);
						cell.setCellValue(treeMap.get(colHeader));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
		try {
			workbook.write(outByteStream);
			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return outByteStream;
	}

	public Map<String,String> Json2map(JSONObject srcJson){
		// for convert response only
		Map<String,String> m = new HashMap<String,String>();  

		try {
			for (Iterator iterator = srcJson.keySet().iterator(); iterator.hasNext();) {
				String key = (String) iterator.next();

				if (srcJson.get(key) instanceof JSONObject) {
					// matrix
					JSONObject subJo = (JSONObject) srcJson.get(key);
					for (Iterator iterator2 = subJo.keySet().iterator(); iterator2.hasNext();) {
						String key2 = (String) iterator2.next();
						m.put(key2, (String) subJo.get(key2));
					}
				} else {
					m.put(key, (String) srcJson.get(key));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return m;
	}
		
	public ByteArrayOutputStream exportBarometer(ArrayList<SrvyPpt> srvyPptList) {

		int rowNum = 0;
		int colNum = 0;
		Row row ;
		Cell cell;
		System.out.println("Creating excel (Barometer)");

		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Survey Data");

		try {

			ArrayList<String> colHdrList = null;

			for (SrvyPpt srvyPpt : srvyPptList) {
				String srvyCntn = (String) srvyPpt.getSrvyCntn();

				// process filled survey 
				if (!("D".equals(srvyPpt.getLastRecTxnTypeCode())) && "Y".equals(srvyPpt.getSbmtSts()) && srvyCntn != null) {
					
					// Create Header list
					if (colHdrList == null) {
						colHdrList = generateBarometerHdr( srvyCntn);
						row = sheet.createRow(rowNum++);
						colNum = 0;
						cell = row.createCell(colNum++);
						cell.setCellValue("Record ID");
						cell = row.createCell(colNum++);
						cell.setCellValue("Submit Time");
						for (String colHeader : colHdrList) {
							cell = row.createCell(colNum++);
							cell.setCellValue(colHeader);
						}
					}
					
					// Data
					row = sheet.createRow(rowNum++);
					colNum = 0;

					cell = row.createCell(colNum++);
					cell.setCellValue(srvyPpt.getSrvyPptId());
					cell = row.createCell(colNum++);
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String sSbmtDate = dateFormat.format(srvyPpt.getSbmtDate());
					cell.setCellValue(sSbmtDate);
					
					Object obj1 = new JSONParser().parse("{" + srvyCntn + "}");
					JSONObject jo1 = (JSONObject) obj1;

					for (String colHeader : colHdrList) {
						cell = row.createCell(colNum++);
						if (jo1.get(colHeader) instanceof JSONObject) {
							// "question_1":{"Ans":"Strongly Agree",}
							JSONObject jAnswer = (JSONObject) jo1.get(colHeader);
							cell.setCellValue((String) jAnswer.get("Ans"));
						} else if (jo1.get(colHeader) instanceof String) {
							// "question_a":"1-5 years",
							cell.setCellValue((String) jo1.get(colHeader));
						}else{
							cell.setCellValue(jo1.get(colHeader).toString());
						}	
					}
				}
			}

			// comment sheet
			XSSFSheet sheet2 = workbook.createSheet("Survey Comment");
			rowNum = 0;
			boolean hdrFlag = true;  
			
			for (SrvyPpt srvyPpt : srvyPptList) {
				String srvyCntn = (String) srvyPpt.getSrvyCntn();

				// process filled survey 
				if (!("D".equals(srvyPpt.getLastRecTxnTypeCode())) && "Y".equals(srvyPpt.getSbmtSts()) && srvyCntn != null) {
					
					// Create Header list
					if (hdrFlag) {
						// create column header when first record
						row = sheet2.createRow(rowNum++);
						colNum = 0;
						cell = row.createCell(colNum++);
						cell.setCellValue("Record ID");
						cell = row.createCell(colNum++);
						cell.setCellValue("Question No.");
						cell = row.createCell(colNum++);
						cell.setCellValue("Comment Code");
						cell = row.createCell(colNum++);
						cell.setCellValue("Comment");
						hdrFlag = false;
					}
					
					int i = 1;
					while (srvyCntn.contains("ignore_"+i)) {
						Object obj2 = new JSONParser().parse("{" + srvyCntn + "}");
						JSONObject jo2 = (JSONObject) obj2;
						if ("Checked".equals((String) jo2.get("ignore_"+i))){
							JSONObject multiLine = (JSONObject) jo2.get("comment_"+i);
							
							for (Iterator iterator = multiLine.keySet().iterator(); iterator.hasNext();) {
								String key = (String) iterator.next();

								row = sheet2.createRow(rowNum++);
								colNum = 0;
								cell = row.createCell(colNum++);
								cell.setCellValue(srvyPpt.getSrvyPptId());
								cell = row.createCell(colNum++);
								cell.setCellValue("PbQ" + i);
								cell = row.createCell(colNum++);
								cell.setCellValue(key);
								cell = row.createCell(colNum++);
								cell.setCellValue((String) multiLine.get(key));
							}
						}
						i++;
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
		try {
			workbook.write(outByteStream);
			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return outByteStream;
	}

	public ArrayList<String> generateBarometerHdr(String srvyCntn) {
		ArrayList<String> colHdrList = new ArrayList<String>();

		String[] arrayQuestion = srvyCntn.split(",");
		for (int i = 0; i < arrayQuestion.length; i++) {
			String questionAns = arrayQuestion[i];
			String[] questionPair = questionAns.split(":");

			// remove the quotation mark  ("question_1" -> question_1)
			String qName = questionPair[0].replaceAll("\"", "");
			try{
			if (qName!=null && "questio".equals(qName.substring(0, 7))) {  
				colHdrList.add(qName);
			}
			}catch(Exception ex){}
		}
		
		return colHdrList;
	}	



}
