package hk.gov.housingauthority.service;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import hk.gov.housingauthority.model.ExcelTemplate;
import hk.gov.housingauthority.model.User;
import hk.gov.housingauthority.dao.FileUploadDao;
import hk.gov.housingauthority.dao.SrvyPptDao;
import hk.gov.housingauthority.dao.UserDao;

public class FileUploadServiceImpl implements FileUploadService {
	
	private FileUploadDao fileUploadDao;
	private SrvyPptDao srvyPptDao;
	private UserDao userDao;	
	
	public FileUploadDao getFileUploadDao() {
		return fileUploadDao;
	}

	public void setFileUploadDao(FileUploadDao fileUploadDao) {
		this.fileUploadDao = fileUploadDao;
	}
	
	public SrvyPptDao getSrvyPptDao() {
		return srvyPptDao;
	}

	public void setSrvyPptDao(SrvyPptDao srvyPptDao) {
		this.srvyPptDao = srvyPptDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public String uploadFileData(File file, int srvyRecId) {
		Workbook workbook = null;
		Sheet sheet = null;
		String userExistList = "";
		String userNotFoundList = "";
		boolean saveResult = false;
		String tempReturnMsg = "";
		
		try {
			workbook = getWorkBook(file);
			sheet = workbook.getSheetAt(0);
			
			/*Build the header portion of the Output File*/
			String headerDetails= "TO_EMAIL,IT_SERVICE_NAME,IT_SERVICE_CODE,NAME_OF_CONTRACTOR";
			String headerNames[] = headerDetails.split(",");
			 
			 /*Read and process each Row*/
			 Iterator<Row> rowIterator = sheet.iterator();
			 ArrayList<ExcelTemplate> saveList = new ArrayList<>();
			 while(rowIterator.hasNext()) 
			 {
			        Row row = rowIterator.next();
			        ExcelTemplate excelRecord = new ExcelTemplate();
			        int count=0;
			        User tempUser = null;
			        
			        //Read and process each column start from 2nd row 
			        if(row.getRowNum()==0){
			        	continue;
		        	}
			        while(count<headerNames.length){
			        	String methodName = "set"+headerNames[count];
			        	String inputCellValue = getCellValueBasedOnCellType(row,count++);
			        	setValueIntoObject(excelRecord, ExcelTemplate.class, methodName, "java.lang.String", inputCellValue);
			        }
			        
			        try {
			        	tempUser = userDao.getUserByEmail(excelRecord.getTO_EMAIL());
			        } catch (Exception ex) {
			        	ex.printStackTrace();
			        }
			        
			        if (tempUser==null || tempUser.getUserId().isEmpty()) {
			        	if (excelRecord.getTO_EMAIL() != null) {
			        		if (!userNotFoundList.contains(excelRecord.getTO_EMAIL())) {
			        			userNotFoundList += " [" + excelRecord.getTO_EMAIL() + "]";
			        		} 			        		
			        	}
			        } else if (srvyPptDao.checkExistPptRecord(srvyRecId, tempUser.getUserId(), excelRecord.getIT_SERVICE_CODE())>0) {
			        	if (excelRecord.getIT_SERVICE_CODE()==null) {
			        		if (!userExistList.contains(" [" + excelRecord.getTO_EMAIL() + "]")) {
				        		userExistList  += " [" + excelRecord.getTO_EMAIL() + "]";
				        	}
			        	} else {
			        		if (!userExistList.contains(" [" + excelRecord.getTO_EMAIL() + "(" + excelRecord.getIT_SERVICE_CODE() + ")]")) {
				        		userExistList  += " [" + excelRecord.getTO_EMAIL() + "(" + excelRecord.getIT_SERVICE_CODE() + ")]";
				        	}
			        	}				        	
			        } else {
			        	//Save USER_ID into ESV_SRVY_PPT instead of USER_EMAIL
			        	excelRecord.setUserId(tempUser.getUserId());
			        	saveResult = srvyPptDao.insertSrvyPptByExcel(srvyRecId, excelRecord);
			        	saveList.add(excelRecord);
			        }
			 }
			 
			 if (!userNotFoundList.isEmpty()) {
				tempReturnMsg = " The following user(s) cannot be found: " + userNotFoundList + ".";
			 }
			
			 if (!userExistList.isEmpty()) {
				tempReturnMsg += " The following user(s) exist and would not be saved again: " + userExistList + ".";
			 }
			 
			 if (saveList.isEmpty()) {
				 return "No record has been saved!" + tempReturnMsg;
			 }
			 
		} catch (Exception ex) {
			ex.printStackTrace();
			return "Fail... Got problem during upload the file!";
		}		
			
		if (saveResult) {
			return "Success! The file has been saved!" + tempReturnMsg;
		} else {
			return "Fail! The file cannot be saved!" + tempReturnMsg;
		}
	}
	
	public static Workbook getWorkBook(File fileName) {
		Workbook workbook = null;
		try {	
			String myFileName=fileName.getName();
			String extension = myFileName.substring(myFileName.lastIndexOf("."));
			if(extension.equalsIgnoreCase(".xls")){
				workbook = new HSSFWorkbook(new FileInputStream(fileName));
			}
			else if(extension.equalsIgnoreCase(".xlsx")){
				workbook = new XSSFWorkbook(new FileInputStream(fileName));
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return workbook;
	}
	
	public static String getCellValueBasedOnCellType(Row rowData,int columnPosition) {
		String cellValue=null;
		Cell cell = rowData.getCell(columnPosition);
		if(cell!=null){
			if(cell.getCellType()==Cell.CELL_TYPE_STRING)
			{
				String inputCellValue=cell.getStringCellValue();
				if(inputCellValue.endsWith(".0")){
					inputCellValue=inputCellValue.substring(0, inputCellValue.length()-2);
				}
		 		cellValue=inputCellValue;
			}
			else if (cell.getCellType()==Cell.CELL_TYPE_NUMERIC)
			{
		 		Double doubleVal = new Double(cell.getNumericCellValue());
		 		cellValue= Integer.toString(doubleVal.intValue());
			}
		}
		return cellValue;
	}
	
	private static <T> void  setValueIntoObject(Object obj, Class<T> clazz, String methodNameForField, String dataType, String inputCellValue) 
		throws SecurityException, NoSuchMethodException, ClassNotFoundException, NumberFormatException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		
		
		Method meth = clazz.getMethod(methodNameForField, Class.forName(dataType));
		T t = clazz.cast(obj);
		 
		 if("java.lang.Double".equalsIgnoreCase(dataType))
		{
			meth.invoke(t, Double.parseDouble(inputCellValue));
		}else if(!"java.lang.Integer".equalsIgnoreCase(dataType))
		{
			meth.invoke(t, inputCellValue);
		}
		else
		{
			meth.invoke(t, Integer.parseInt(inputCellValue));
		}		 		
	}

}
