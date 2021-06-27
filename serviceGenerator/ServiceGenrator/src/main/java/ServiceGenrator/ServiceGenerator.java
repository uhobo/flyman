package ServiceGenrator;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ctc.wstx.util.StringUtil;

import ServiceGenrator.beans.MemeberData;
import ServiceGenrator.beans.ObjectData;
import ServiceGenrator.beans.ServiceData;

public class ServiceGenerator {

	private final static String ServiceName = "ServiceName";
	private final static String Input = "Input";
	private final static String Output = "Output";
	private final static String EndDeclare = "end";
	private final static String Declares = "Declares";
	private final static String ParamName = "Param Name";
	private final static String Members = "members";
	
	public static void main(String[] args) {
		String fileName = args[0];
		try {
			extractServiceData(fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static ServiceData extractServiceData(String fileName) throws IOException {
		ServiceData serviceData = new ServiceData();
		//FileInputStream excelFile = new FileInputStream(fileName);
		try(FileInputStream excelFile = new FileInputStream(fileName); 
			Workbook workbook = new XSSFWorkbook(excelFile)){
			 for(int index = 0; index < workbook.getNumberOfSheets(); index++) {
				 Sheet sheet = workbook.getSheetAt(index);
				 ObjectData objectData = null;
				 for (Row row : sheet) {
					 objectData = parseRowData(row, serviceData, objectData);
					 
				 }
			 }
		}
		
		return null;
	}

	private static ObjectData parseRowData(Row row, ServiceData serviceData, ObjectData objectData) {
		
		for (Cell cell : row) {
		
			String cellValue=  cell.getStringCellValue();
			cellValue = cellValue.trim();
			if(ServiceName.equalsIgnoreCase(cellValue)) { 
				serviceData.setServiceName(row.getCell(cell.getColumnIndex()+1).getStringCellValue());
				System.out.println("ServiceName: " + serviceData.getServiceName());
				return objectData;
			}
			
			if(Input.equalsIgnoreCase(cellValue)) { 
				System.out.println("start parsing Input Service");
				objectData = new ObjectData();
				objectData.setName(Input);
				serviceData.getObjectDataMap().put(Input, objectData);
				return objectData;
			}
			
			if(Output.equalsIgnoreCase(cellValue)) {
				System.out.println("start parsing Output Service");
				objectData = new ObjectData();
				objectData.setName(Output);
				serviceData.getObjectDataMap().put(Output, objectData);
				return objectData;
			}
			
			if(EndDeclare.equalsIgnoreCase(cellValue)) {
				System.out.println("End parsing");
				return null;
			}
			if(Members.equalsIgnoreCase(cellValue)) {
				//System.out.println("End parsing");
				return objectData;
			}
			
			if(objectData == null && ParamName.equalsIgnoreCase(cellValue)) {
				objectData = new ObjectData();
				objectData.setName(row.getCell(cell.getColumnIndex()+1).getStringCellValue());
				System.out.println("start parsing declare of " + objectData.getName());
				serviceData.getObjectDataMap().put(objectData.getName(), objectData);
				return objectData;
			}
			
			if(objectData != null && !ParamName.equalsIgnoreCase(cellValue) && !cellValue.isEmpty()) {
				MemeberData member = new MemeberData();
				member.setName(cellValue);
				member.setTypeStr(row.getCell(cell.getColumnIndex()+1).getStringCellValue());

				if(row.getCell(cell.getColumnIndex()+2) != null) {
					String val = row.getCell(cell.getColumnIndex()+2).getStringCellValue();
					member.setMandatory(val != null && ("Y".equalsIgnoreCase(val) || "YES".equalsIgnoreCase(val)) );
				}
				
				if(row.getCell(cell.getColumnIndex()+3) != null) { 
					member.setDefaultVal(row.getCell(cell.getColumnIndex()+3).getStringCellValue());	
				}
				
				if(row.getCell(cell.getColumnIndex()+4) != null) { 
					switch (row.getCell(cell.getColumnIndex()+4).getCellType()) {
					case STRING: 
						member.setExmpleVal(row.getCell(cell.getColumnIndex()+4).getStringCellValue());
						break;
					case NUMERIC:
						member.setExmpleVal(String.valueOf(row.getCell(cell.getColumnIndex()+4).getNumericCellValue()));
						break;
					case BOOLEAN: 
						member.setExmpleVal(String.valueOf(row.getCell(cell.getColumnIndex()+4).getBooleanCellValue()));
						break;
					default:
						System.out.println("UNKNOWN_TYPE: " + cell.getCellType().name()) ;
						break;
					}
					
				
				}
				objectData.getMemebers().add(member);
				return objectData;
			}
			return objectData;
		}
		return objectData;
	}

}
