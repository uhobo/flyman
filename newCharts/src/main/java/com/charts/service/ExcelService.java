package com.charts.service;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xddf.usermodel.XDDFColor;
import org.apache.poi.xddf.usermodel.XDDFLineProperties;
import org.apache.poi.xddf.usermodel.XDDFShapeProperties;
import org.apache.poi.xddf.usermodel.XDDFSolidFillProperties;
import org.apache.poi.xddf.usermodel.chart.AxisCrossBetween;
import org.apache.poi.xddf.usermodel.chart.AxisCrosses;
import org.apache.poi.xddf.usermodel.chart.AxisPosition;
import org.apache.poi.xddf.usermodel.chart.BarDirection;
import org.apache.poi.xddf.usermodel.chart.ChartTypes;
import org.apache.poi.xddf.usermodel.chart.LegendPosition;
import org.apache.poi.xddf.usermodel.chart.XDDFBarChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFCategoryAxis;
import org.apache.poi.xddf.usermodel.chart.XDDFChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFChartLegend;
import org.apache.poi.xddf.usermodel.chart.XDDFDataSource;
import org.apache.poi.xddf.usermodel.chart.XDDFDataSourcesFactory;
import org.apache.poi.xddf.usermodel.chart.XDDFLineChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFNumericalDataSource;
import org.apache.poi.xddf.usermodel.chart.XDDFValueAxis;
import org.apache.poi.xssf.usermodel.XSSFChart;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTDPt;
import org.openxmlformats.schemas.drawingml.x2006.main.CTSolidColorFillProperties;
import org.springframework.stereotype.Service;

import com.charts.domain.FileData;
import com.charts.domain.ImportExcelData;
import com.charts.domain.charts.ChartData;
import com.charts.domain.charts.ChartDataset;
import com.charts.domain.charts.DiagramDataset;
import com.charts.domain.charts.PieDataset;

@Service
public class ExcelService {
	

	
	public String exportBarChart2Excel(ChartData diagramChart,boolean onlyExportData) throws IOException {
		
		final String  fileName = "ooxml-bar-chart.xlsx";
		
		try (XSSFWorkbook wb = new XSSFWorkbook()) {
			 	XSSFSheet sheet = wb.createSheet("barchart");
			 	List<ChartDataset> statisticsList = new ArrayList<>();
	          	Integer maxDataColIndex = writeData(sheet, diagramChart, statisticsList );
			 	

	          	// 1 for the series label row;
	          	Integer achorNumRows = diagramChart.getDatasets().size() + 1;
	          	if(onlyExportData) {
	          		try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
		                wb.write(fileOut);
		            }
	          		return fileName;
	          	}
	            //set minimal drawing plat size
	            if(achorNumRows < 15) {
	            	achorNumRows = 15;
	            }
	            
	            XSSFDrawing drawing = sheet.createDrawingPatriarch();
	            XSSFClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 0, achorNumRows+ 5, maxDataColIndex+10, achorNumRows*2 + 5 );

	            XSSFChart chart = drawing.createChart(anchor);
	            chart.setTitleText("BarChart");
	            chart.setTitleOverlay(false);
	            
	            XDDFChartLegend legend = chart.getOrAddLegend();
	            legend.setPosition(LegendPosition.TOP_RIGHT);
	            
	            // Use a category axis for the bottom axis.
	            XDDFCategoryAxis bottomAxis = chart.createCategoryAxis(AxisPosition.BOTTOM);
	            bottomAxis.setTitle("x"); 

	            XDDFValueAxis leftAxis = chart.createValueAxis(AxisPosition.LEFT);
	            leftAxis.setTitle("y");
	            leftAxis.setCrosses(AxisCrosses.AUTO_ZERO);
	            
	            //NOT DELETE IT
	            //================================================
	            leftAxis.setCrossBetween(AxisCrossBetween.BETWEEN);
	            
	            
	            XDDFDataSource<String> xs = XDDFDataSourcesFactory.fromStringCellRange(sheet, new CellRangeAddress(0, 0, 1, diagramChart.getLabels().size())); //set the catagory X 
	            
	            XDDFChartData data = chart.createData(ChartTypes.BAR, bottomAxis, leftAxis);
	            Integer rowIndex = 0;
	            
	            for (ChartDataset chartDataset: diagramChart.getDatasets()) { 
	            	
	            	if(chartDataset.isStatistics()) {
	            		continue;
	            	}
	            	XDDFNumericalDataSource<Double> ys = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(rowIndex +1 , rowIndex +1, 1, chartDataset.getData().size()));
	            	XDDFChartData.Series series = data.addSeries(xs, ys);
	            
	            	series.setTitle(diagramChart.getDatasets().get(rowIndex).getLabel(), null);
	            	solidFillSeries(series, ((DiagramDataset)chartDataset).getBackgroundColor());
	            	rowIndex++;
	            }
	            chart.plot(data);
	         // in order to transform a bar chart into a column chart, you just need to change the bar direction
	            XDDFBarChartData bar = (XDDFBarChartData) data;
	            bar.setBarDirection(BarDirection.COL);
	            chart.getCTChart().getPlotArea().getBarChartArray(0).addNewGapWidth().setVal((byte)100); 
	            
	            
	            // looking for "Stacked Bar Chart"? uncomment the following line
	            // bar.setBarGrouping(BarGrouping.STACKED);

	            if(!statisticsList.isEmpty()) {
	            	data = chart.createData(ChartTypes.LINE, bottomAxis, leftAxis);
	            	
	            	Integer staticesIndex = 0;
	            	for(ChartDataset statisticsElm : statisticsList) {
	            		XDDFNumericalDataSource<Double> scatterY = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(1, diagramChart.getLabels().size(), maxDataColIndex+staticesIndex, maxDataColIndex+staticesIndex));
	            		staticesIndex++;
		            	 XDDFLineChartData.Series series2 = (XDDFLineChartData.Series)data.addSeries(xs, scatterY);
		            	 series2.setTitle(statisticsElm.getLabel(), null);
		            	 series2.setSmooth(false); 
		            	 solidLineSeries(series2, ((DiagramDataset)statisticsElm).getBorderColor());
		            	 
	            	}
	            	chart.plot(data);
	            	 
	            }
	            
	            	
	            //chart.plot(data);
	           
	            Path path = null;
	            // Write the output to a file
	            try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
	                wb.write(fileOut);
	                path = Paths.get(fileName);
	            }
	            
	            return path.toFile().getAbsolutePath();
		}
	}
	
	private Integer writeData(XSSFSheet sheet, ChartData diagramChart, List<ChartDataset> statisticsList) {
		Row row;
        Cell cell;
        
        short rowIndex =0;
        short colIndex = 1;
        row = sheet.createRow((short) rowIndex);
        diagramChart.getDatasets().stream().filter(x -> x.isStatistics()).forEach(x -> statisticsList.add(x));
        
        for(String seriesLabel : diagramChart.getLabels()) {	
        	cell = row.createCell((short) colIndex++);
        	cell.setCellValue(seriesLabel);
        }
        int maxDataColIndex = colIndex;
        
        for(ChartDataset statistics: statisticsList) {
        	cell = row.createCell((short) colIndex++);
        	cell.setCellValue(statistics.getLabel());
        }
        
        rowIndex = 1;
        
        for (ChartDataset chartDataset :  diagramChart.getDatasets()) {
        	if(chartDataset.isStatistics()) {
        		//statisticsList.add(chartDataset);
        		continue;
        	}
        	
            row = sheet.createRow((short) rowIndex++);
            colIndex =0;
            cell = row.createCell((short) colIndex++);
            cell.setCellValue(chartDataset.getLabel());
            for (Double value: chartDataset.getData() ){
            	cell = row.createCell((short) colIndex++);
                cell.setCellValue(value);
            }
            for(ChartDataset statistics: statisticsList) {
            	cell = row.createCell((short) colIndex++);
            	cell.setCellValue(statistics.getData().get(0)); 
            }
        }
        
        
        
//        for(ChartDataset statistics: statisticsList) {
//        	row = sheet.createRow((short) rowIndex++);
//            colIndex =0;
//            cell = row.createCell((short) colIndex++);
//            cell.setCellValue(statistics.getLabel());
//            for (Double value: statistics.getData() ){
//            	cell = row.createCell((short) colIndex++);
//                cell.setCellValue(value);  
//            }
//        }
        
       return maxDataColIndex;
		
	}

	public String exportPieChart2Excel(ChartData diagramChart) throws IOException{
	
		try (XSSFWorkbook wb = new XSSFWorkbook()) {
			XSSFSheet sheet = wb.createSheet("piechart");
			List<ChartDataset> statisticsList = new ArrayList<>();
          	Integer maxDataColIndex = writeData(sheet, diagramChart, statisticsList );
          	
          	Integer achorNumRows = diagramChart.getDatasets().size() + 1;
          	
            //set minimal drawing plat size
            if(achorNumRows < 15) {
            	achorNumRows = 15;
            }
            
            XSSFDrawing drawing = sheet.createDrawingPatriarch();
            XSSFClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 0, achorNumRows+ 5, maxDataColIndex+10, achorNumRows*2 + 5 );

            XSSFChart chart = drawing.createChart(anchor);
            chart.setTitleText("Pie Chart");
            chart.setTitleOverlay(false);
            XDDFChartLegend legend = chart.getOrAddLegend();
            legend.setPosition(LegendPosition.TOP_RIGHT);
            XDDFChartData data = chart.createData(ChartTypes.PIE, null, null);
            data.setVaryColors(false);
            
           // XDDFDataSource<String> cat  = XDDFDataSourcesFactory.fromStringCellRange(sheet, new CellRangeAddress(0, 0, 1, diagramChart.getLabels().size())); //set the catagory X 
            Integer rowIndex =0;
            
            
            for (ChartDataset chartDataset: diagramChart.getDatasets()) { 
            	
            	if(chartDataset.isStatistics()) {
            		continue;
            	}
            	//Integer colIndex =1;
            	
        		//XDDFNumericalDataSource<Double> ys = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(1 , 1, colIndex, diagramChart.getLabels().size()));
            	//XDDFChartData.Series series = data.addSeries(cat, ys);
            	
            	//int pointCount = series.getCategoryData().getPointCount();
            	
            //	List<CTPieSer> list;
            	Long index= Long.valueOf(0) ;
            	for(String colorCat : ((PieDataset)chartDataset).getBackgroundColor()) {
            		CTDPt dpt = chart.getCTChart().getPlotArea().getPieChartArray(0).getSerList().get(0).addNewDPt();
            		dpt.addNewIdx().setVal(index);
            		setSolidFillProperties(dpt.addNewSpPr().addNewSolidFill(), colorCat );
            		index++;
            	}
            	
            	chart.plot(data);
            	rowIndex++;
            	
            }
            String fileName = "C:\\roni\\ooxml-bar-chart.xlsx";
            
            // Write the output to a file
            try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
                wb.write(fileOut);
            }
            return fileName;
		}
		
	}
	
	private static void setSolidFillProperties(CTSolidColorFillProperties ctSolidColorFillProperties, String colorT) {
		
		if(colorT.startsWith("#")) {
			colorT = colorT.substring(1);
		}
		
		
		Color realColor = new Color(Integer.parseInt(colorT, 16));
		XDDFSolidFillProperties fill = new XDDFSolidFillProperties(XDDFColor.from(new byte[]{(byte)realColor.getRed(), (byte)realColor.getGreen(),  (byte)realColor.getBlue()}));
		//byte colorByte[] = new byte[]{(byte)realColor.getRed(), (byte)realColor.getGreen(),  (byte)realColor.getBlue()};
		ctSolidColorFillProperties.set(fill.getXmlObject());
//		CTScRgbColor scrgbClr = ctSolidColorFillProperties.addNewScrgbClr();
//		scrgbClr.addNewRed().setVal( realColor.getRed());
//		scrgbClr.addNewBlue().setVal(realColor.getBlue());
//		scrgbClr.addNewGreen().setVal(realColor.getGreen());
//		
		//scrgbClr.setR(realColor.getRed());
		//scrgbClr.setG(realColor.getGreen());
		//scrgbClr.setB(realColor.getBlue());
		
		//XDDFColor color = XDDFColor.from(new byte[]{(byte)realColor.getRed(), (byte)realColor.getGreen(),  (byte)realColor.getBlue()});
		//ctSolidColorFillProperties.setColor(XDDFColor.from(new byte[]{(byte)realColor.getRed(), (byte)realColor.getGreen(),  (byte)realColor.getBlue()}));
	}
	
	private static void solidFillSeries( XDDFChartData.Series series, String colorT) {
		
		//String colorT = ColorUtil.allocateColor(seriesIndex);
		if(colorT.startsWith("#")) {
			colorT = colorT.substring(1);
		}
		
		Color realColor = new Color(Integer.parseInt(colorT, 16));
		XDDFSolidFillProperties fill = new XDDFSolidFillProperties(XDDFColor.from(new byte[]{(byte)realColor.getRed(), (byte)realColor.getGreen(),  (byte)realColor.getBlue()}));
		
        XDDFShapeProperties properties = series.getShapeProperties();
        if (properties == null) {
            properties = new XDDFShapeProperties();
        }
       
        properties.setFillProperties(fill);
        series.setShapeProperties(properties);
    }
	
	
	
	private void solidLineSeries(XDDFChartData.Series series, String colorT) {
		  
		if(colorT.startsWith("#")) {
			colorT = colorT.substring(1);
		}
		
		Color realColor = new Color(Integer.parseInt(colorT, 16));
		
		XDDFSolidFillProperties fill = new XDDFSolidFillProperties(XDDFColor.from(new byte[]{(byte)realColor.getRed(), (byte)realColor.getGreen(),  (byte)realColor.getBlue()}));
		XDDFLineProperties line = new XDDFLineProperties();
		line.setFillProperties(fill);
		XDDFShapeProperties properties = series.getShapeProperties();
		if (properties == null) {
		   properties = new XDDFShapeProperties();
		 }
		 properties.setLineProperties(line);
		 series.setShapeProperties(properties);
	}
	
	public  List<ImportExcelData> parseExcelData(String fileLocation) throws IOException {
		
		FileInputStream excelFile = new FileInputStream(fileLocation);
		List<ImportExcelData> importExcelDataList = new ArrayList<>();
		Map<Double, Integer> seriesMap = new HashMap<>();
		
		try(Workbook workbook = new XSSFWorkbook(excelFile)){
	        for(int index = 0; index < workbook.getNumberOfSheets(); index++) {
	        	Sheet sheet = workbook.getSheetAt(index);
	        	ImportExcelData importExcelData = new ImportExcelData();
	        	importExcelData.setSheetName(sheet.getSheetName());
	        	for (Row row : sheet) {
	        		importExcelData.getData().add(new ArrayList<Object>());
	        		for (Cell cell : row) {
	        			switch (cell.getCellType()) {
	                    case STRING: 
	                    	importExcelData.getData().get(index).add(cell.getStringCellValue());
	                    	if(StringUtils.isEmpty(cell.getStringCellValue())) {
	                    		importExcelData.getErrorSuspect().put(row.getRowNum()+ "_" +cell.getColumnIndex(),"EMPTY");	
                    	}
	                    break;
	                    case NUMERIC: 
	                    	importExcelData.getData().get(index).add(cell.getNumericCellValue()); 
	                    	updateSeriesList(seriesMap, cell.getNumericCellValue());
	                    	break;
	                    case BOOLEAN: 
	                    	importExcelData.getData().get(index).add(cell.getBooleanCellValue()); 
	                    	updateSeriesList(seriesMap, cell.getBooleanCellValue()? Double.valueOf(1): Double.valueOf(0));
	                    	break;
	                    default: 
	                    	importExcelData.getErrorSuspect().put(row.getRowNum()+ "_" +cell.getColumnIndex(), "UNKNOWN_TYPE");
	                    	importExcelData.getData().get(index).add("");
	                }
	        		}
	        		index++;
	        	}
	        	List<Double> seriesList = seriesMap.entrySet().stream().filter(x -> {return x.getValue() > 2;}).map(map -> map.getKey()).sorted().collect(Collectors.toList());
	        	importExcelData.setSeriesList(seriesList);
	        	importExcelDataList.add(importExcelData);
	        	
	        }
		}
		
        return importExcelDataList;
        
	}
	
	 private void updateSeriesList(Map<Double , Integer> seriesMap, Double doubleCellValue) {
		Integer count = seriesMap.get(doubleCellValue);
		if(count == null) {
			count = 0;
		}
		seriesMap.put(doubleCellValue, ++count);
	}

	public static void main(String[] args){
		 ExcelService excelService = new ExcelService();
		 
//		 try {
//			excelService.exportBarChart2Excel(null, true);
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		 
		 List<ImportExcelData> importExcelDataList;
		try {
			FileData fileData = new FileData();
			importExcelDataList = excelService.parseExcelData("C:\\roni\\docs\\first.xlsx");
			if(!importExcelDataList.isEmpty()) {
	        	fileData.setImportData(importExcelDataList.get(0));
	        }
	        
			 System.out.println("list size %d=" + importExcelDataList.size());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
