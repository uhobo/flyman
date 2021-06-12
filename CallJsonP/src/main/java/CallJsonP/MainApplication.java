package CallJsonP;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.util.Matrix;
import org.apache.poi.ss.usermodel.Workbook;
import org.jodconverter.JodConverter;
import org.jodconverter.office.LocalOfficeManager;
import org.jodconverter.office.OfficeUtils;


public class MainApplication {
	private static Map<String, Dictionary> dictionary = new HashMap<String, Dictionary>();
	public static void main(String[] args) {
		File inputFile = new File("C:\\roni\\docs\\first.xlsx");
		File outputFile = new File("C:\\roni\\docs\\document.pdf");
		Workbook xls = new  ;
		
		
		
		final LocalOfficeManager officeManager = LocalOfficeManager.install(); 
		try {

		    // Start an office process and connect to the started instance (on port 2002).
		    officeManager.start();
		    // Convert
		    JodConverter
		             .convert(inputFile)
		             .to(outputFile)
		             .execute();
		    
		   PDDocument pdfdoc = PDDocument.load(outputFile);
		   PDPageTree pdfPages = pdfdoc.getPages();
		   PDDocument newpdfdoc = new PDDocument();
		   
		   pdfPages.forEach(page -> {
			   		page.setRotation(90);
//			   		
			   		PDRectangle pageSize = page.getMediaBox(); 
			   		PDPage page1 = new PDPage(PDRectangle.A4);
			   	
//			   		float pageWidth = pageSize.getWidth();
//			   		//PDPageContentStream contentStream = null;
//			   		try (PDPageContentStream contentStream = new PDPageContentStream( pdfdoc, page, AppendMode.OVERWRITE, false);){
//						
//					   	// add the rotation using the current transformation matrix
//					   	// including a translation of pageWidth to use the lower left corner as 0,0 reference
//			   			contentStream.transform(new Matrix(0, 1, -1, 0, pageWidth, 0));
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
			   		newpdfdoc.addPage(page);
		   
		   });
		   newpdfdoc.save("C:\\\\roni\\\\docs\\\\document1.pdf");
		   newpdfdoc.close();
		   pdfdoc.close();
		    
		}catch(Exception e) {
			e.printStackTrace();
		}
		finally {
		    // Stop the office process
		    OfficeUtils.stopQuietly(officeManager);
		}
		
		//CorporationReader reader = new CorporationReader();
		//reader.readData("https://data.gov.il/api/action/datastore_search?language=english&resource_id=b5223cbc-e1b2-4503-a499-97cdcd7190d2");

	}
	

	
}
