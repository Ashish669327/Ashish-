package utils;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbookFactory;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import lombok.val;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;

public class FileOperations {

	Path currentRelativePath = Paths.get("");
	String s = currentRelativePath.toAbsolutePath().toString();
	String basePath = s + File.separator;
	GenericMethods genericMethods = new GenericMethods();
	static String data;
	
	public int getReadDataFromDocFile(String filePath, String fileName) {
		int line=0;
		 try {
		File file = new File(filePath+fileName);
        FileInputStream fis = new FileInputStream(file.getAbsolutePath());
        XWPFDocument document = new XWPFDocument(fis);
        List<XWPFParagraph> paragraphs = document.getParagraphs();
        for (XWPFParagraph para : paragraphs) {
            System.out.println(para.getText());
            String values[] = para.getText().replaceAll("^[.,\\s]+", "").split("[.,\\s]+");
            System.out.println("fghj "+values.length);
            line = values.length;
        }
       
    	fis.close();
	}
     catch (Exception e) {
        e.printStackTrace();
    }   
	return line;
	}
	
	
	@SuppressWarnings("resource")
	public String getReadDataFromDocFilee(String filePath, String fileName, String x) {
		String value = null;
		 try {
		File file = new File(filePath+fileName);
        FileInputStream fis = new FileInputStream(file.getAbsolutePath());
        XWPFDocument document = new XWPFDocument(fis);
        List<XWPFParagraph> paragraphs = document.getParagraphs();
        for (XWPFParagraph para : paragraphs) {
            System.out.println("--------------------"+para.getText());
            if(para.getText().trim().equals("Veoci Stage Print View"));{
                value = para.getText();
                System.out.println("Inside if loop "+value);
                break;
                 }
         //   System.out.println("fghj "+values.length);
        }
       
    	fis.close();
	}
     catch (Exception e) {
        e.printStackTrace();
    }   
	return value;
	}	
	
	/**
	 * @param filePath
	 * @param key
	 * @return
	 * @throws InterruptedException 
	 */
	public String getValueFromPropertyFile(String filePath, String key) throws InterruptedException {
		String keyValue = null;
		Thread.sleep(5000);
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(filePath));
			keyValue = prop.getProperty(key);
		} catch (IOException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		return keyValue;
	}

	/**
	 * @param filePath
	 * @param key
	 * @param value
	 * @throws IOException
	 * @throws ConfigurationException
	 */
	public void updateValueToPropertyFile(String filePath, String key, String value)
			throws IOException, ConfigurationException {
		// FileInputStream in = new FileInputStream(filePath);
		// Properties props = new Properties();
		// props.load(in);
		// in.close();
		//
		// FileOutputStream out = new FileOutputStream(filePath);
		// props.setProperty(key, value);
		// props.store(out, null);
		// out.close();

		PropertiesConfiguration config = new PropertiesConfiguration(filePath);
		config.setProperty(key, value);
		config.save();
	}
	
	public void concateValueToPropertyFile(String filePath, String key, String value)
			throws IOException, ConfigurationException {
		
		String keyValue = null;
		String roomKeyValue = null;
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(filePath));
			keyValue = prop.getProperty(key);
			roomKeyValue = prop.getProperty("roomName");
			
		} catch (IOException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		if(!(keyValue.contains(value+" "+roomKeyValue)) &&  (!keyValue.contains(value.replace("Failed suite ", "").replace("[", "").replace("]", "")))) {
		PropertiesConfiguration config = new PropertiesConfiguration(filePath);
		config.setProperty(key, keyValue.replace(".", "<>").split("<>")[0]+". "+value.replace("Failed suite ", "").replace("[", "").replace("]", "")+" "+roomKeyValue);
		config.save();
		}
		
	}

	public LinkedHashSet<Map<String, String>> readCompleteDataFromExcel(String filePath, String fileName,
			String sheetName) {
		File file = new File(filePath + fileName);
		FileInputStream inputStream;
		XSSFWorkbook workbook = null;
		Row row = null;
		LinkedHashSet<Map<String, String>> myLinkedHashSet = new LinkedHashSet<Map<String, String>>();
		LinkedHashMap<String, String> objLinkedHashMap = new LinkedHashMap<String, String>();
		try {
			inputStream = new FileInputStream(file);
			workbook = new XSSFWorkbookFactory().create(inputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Sheet sheet = workbook.getSheet(sheetName);
		Row firstRow = sheet.getRow(0);
		int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
		for (int i = 1; i <= rowCount; i++) {
			objLinkedHashMap = new LinkedHashMap<String, String>();
			row = sheet.getRow(i);
			for (int j = 0; j < sheet.getRow(i).getLastCellNum(); j++) {
				if ((firstRow.getCell(j) != null) && (!firstRow.getCell(j).getStringCellValue().trim().equals(""))) {
					if ((row.getCell(j) != null)) {
						row.getCell(j).setCellType(CellType.STRING);
						if (!row.getCell(j).getStringCellValue().trim().equals("")) {
							objLinkedHashMap.put(firstRow.getCell(j).getStringCellValue().trim(),
									row.getCell(j).getStringCellValue().trim());
						}
					}
				}
			}
			if (!objLinkedHashMap.isEmpty())
				myLinkedHashSet.add(objLinkedHashMap);
			objLinkedHashMap = null;
		}
		return myLinkedHashSet;
	}

	public String readSingleValueFromExcel(String filePath, String fileName, String sheetName, String attributeName) {
		File file = new File(filePath + File.separator + fileName);
		String attributeValue = null;
		FileInputStream inputStream;
		XSSFWorkbook workbook = null;
		Row row = null;
		try {
			inputStream = new FileInputStream(file);
			workbook = new XSSFWorkbookFactory().create(inputStream);
		} catch (Exception e) {

		}
		Sheet sheet = workbook.getSheet(sheetName);
		Row firstRow = sheet.getRow(0);
		int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
		for (int i = 1; i <= rowCount; i++) {

			row = sheet.getRow(i);
			for (int j = 0; j < firstRow.getLastCellNum(); j++) {
				if ((row.getCell(j) != null)) {
					
					 if (firstRow.getCell(j).getStringCellValue().equals(attributeName)) {
					if (row.getCell(j).getCellType() == CellType.NUMERIC) {
						  double attribute = sheet.getRow(1).getCell(j).getNumericCellValue();
						  int value = (int)attribute;
						 attributeValue = Integer.toString(value);
						 
						
					}}
					 if (firstRow.getCell(j).getStringCellValue().equals(attributeName)) { 
						 if (row.getCell(j).getCellType() == CellType.STRING) {
						
							attributeValue = sheet.getRow(1).getCell(j).getStringCellValue();
					}
					}}
				
			}

		}

		return attributeValue;
	}

	public List<String> readColumnDataFromExcel(String filePath, String fileName, String sheetName,
			String attributeName) {
		File file = new File(filePath + File.separator + fileName);
		List<String> attributeValue = new ArrayList<String>();
		FileInputStream inputStream;
		XSSFWorkbook workbook = null;
		Row row = null;
		int columnNo = 0;
		try {
			inputStream = new FileInputStream(file);
			workbook = new XSSFWorkbookFactory().create(inputStream);
		} catch (Exception e) {

		}
		Sheet sheet = workbook.getSheet(sheetName);
		Row firstRow = sheet.getRow(0);
		int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
		for (int i = 1; i <= rowCount; i++) {

			row = sheet.getRow(i);
			if (i == 1) {
				for (int j = 0; j < firstRow.getLastCellNum(); j++) {
					if ((row.getCell(j) != null)) {
						row.getCell(j).setCellType(CellType.STRING);
						if (firstRow.getCell(j).getStringCellValue().equals(attributeName)) {
							columnNo = j;
							break;
						}
					}
				}
			}
			if ((row.getCell(columnNo) != null)) {
				row.getCell(columnNo).setCellType(CellType.STRING);
				if ((firstRow.getCell(columnNo).getStringCellValue().equals(attributeName))
						&& (!row.getCell(columnNo).getStringCellValue().trim().equals(""))) {
					attributeValue.add(row.getCell(columnNo).getStringCellValue().trim());

				}
			}

		}
		return attributeValue;
	}
	
	public List<String> getNumericDataFromExcelInISTTiming(String format,String filePath, String fileName, String sheetName,
			String attributeName) throws Exception {//added by sel+6
		File file = new File(filePath + "\\" + fileName);
		List<String> attributeValue = new ArrayList<String>();
		FileInputStream inputStream;
		XSSFWorkbook workbook = null;
		Row row = null;
		int columnNo = 0;
		try {
			inputStream = new FileInputStream(file);
			workbook = new XSSFWorkbookFactory().create(inputStream);
		} catch (Exception e) {

		}
		Sheet sheet = workbook.getSheet(sheetName);
		Row firstRow = sheet.getRow(0);
		int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
		for (int i = 1; i <= rowCount; i++) {

			row = sheet.getRow(i);
			if (i == 1) {
				for (int j = 0; j < firstRow.getLastCellNum(); j++) {
					if ((row.getCell(j) != null)) {
						row.getCell(j).setCellType(CellType.STRING);
						if (firstRow.getCell(j).getStringCellValue().equals(attributeName)) {
							columnNo = j;
							break;
						}
					}
				}
			}
			if ((row.getCell(columnNo) != null)) {
				if ((firstRow.getCell(columnNo).getStringCellValue().equals(attributeName))) {
					row.getCell(columnNo).setCellType(CellType.NUMERIC);
					switch (format) {
					case "Integer":
						attributeValue.add(String.valueOf((int) row.getCell(columnNo).getNumericCellValue()));
						break;
					case "float":
						attributeValue.add(String.valueOf((float) row.getCell(columnNo).getNumericCellValue()));
						break;
					case "Double":
						attributeValue.add(String.valueOf((Double)row.getCell(columnNo).getNumericCellValue()));
						break;
					case "Date":
						// genericMethods.getTimeWithTimeZoneIST(String.valueOf(row.getCell(columnNo).getDateCellValue()), "MM/dd/yyyy HH:MM");
						 attributeValue.add(genericMethods.getTimeWithTimeZoneIST(String.valueOf(row.getCell(columnNo).getDateCellValue()), "MM/dd/yyyy HH:MM"));
						System.out.println("String.valueOf(row.getCell(columnNo).getDateCellValue()) :  "+attributeValue);
						break;
					}
				}

			}
		}

		return attributeValue;
	}
	
	public List<String> getNumericDataFromExcel(String format,String filePath, String fileName, String sheetName,
			String attributeName) {//added by sel+3
		File file = new File(filePath + "\\" + fileName);
		List<String> attributeValue = new ArrayList<String>();
		FileInputStream inputStream;
		XSSFWorkbook workbook = null;
		Row row = null;
		int columnNo = 0;
		try {
			inputStream = new FileInputStream(file);
			workbook = new XSSFWorkbookFactory().create(inputStream);
		} catch (Exception e) {

		}
		Sheet sheet = workbook.getSheet(sheetName);
		Row firstRow = sheet.getRow(0);
		int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
		for (int i = 1; i <= rowCount; i++) {

			row = sheet.getRow(i);
			if (i == 1) {
				for (int j = 0; j < firstRow.getLastCellNum(); j++) {
					if ((row.getCell(j) != null)) {
						row.getCell(j).setCellType(CellType.STRING);
						if (firstRow.getCell(j).getStringCellValue().equals(attributeName)) {
							columnNo = j;
							break;
						}
					}
				}
			}
			if ((row.getCell(columnNo) != null)) {
				if ((firstRow.getCell(columnNo).getStringCellValue().equals(attributeName))) {
					row.getCell(columnNo).setCellType(CellType.NUMERIC);
					switch (format) {
					case "Integer":
						attributeValue.add(String.valueOf((int) row.getCell(columnNo).getNumericCellValue()));
						break;
					case "float":
						attributeValue.add(String.valueOf((float) row.getCell(columnNo).getNumericCellValue()));
						break;
					case "Double":
						attributeValue.add(String.valueOf((Double)row.getCell(columnNo).getNumericCellValue()));
						break;
					case "Date":
						 attributeValue.add(String.valueOf(row.getCell(columnNo).getDateCellValue()));
						break;

					}
				}

			}
		}

		return attributeValue;
	}

	@SuppressWarnings("deprecation")
	public String readColumnValueUsingKeyFromExcel(String filePath, String fileName,String sheetName,String keyName) {
		File file = new File(filePath + File.separator + fileName);
		String value = null;
		FileInputStream inputStream;
		XSSFWorkbook workbook = null;
		Row row =null;

		try{
			inputStream = new FileInputStream(file);
			workbook = new XSSFWorkbookFactory().create(inputStream);
		}
		catch(Exception e){
		}
		Sheet sheet = workbook.getSheet(sheetName);
		Row firstRow = sheet.getRow(0);
		int rowCount = sheet.getLastRowNum()-sheet.getFirstRowNum();
		for (int i = 1; i <=rowCount; i++) {
			row = sheet.getRow(i);
			for (int j = 0; j < firstRow.getLastCellNum(); j++) {
				if(row.getCell(j).getStringCellValue().equals(keyName)) {
					row.getCell(j).setCellType(CellType.STRING);
					if(!row.getCell(j).getStringCellValue().trim().equals("")){
						if(firstRow.getCell(j).getStringCellValue()!=null) {
							//value = row.getCell(j+1).getStringCellValue().trim();
							Cell cell =row.getCell(j+1);
							switch (cell.getCellType()) {
							case STRING:
								value=cell.getRichStringCellValue().getString();
								break;
							case NUMERIC:
								value=Integer.toString(((int)cell.getNumericCellValue()));
								break;
							}
							break;
						}
					}
				}
				else
					break;
			}
		}
		return value;
  }

	public void writeColumnValueUsingKeyInExcel(String filePath, String fileName,String sheetName,String attributeName,String textvalue) throws IOException {
		File file = new File(filePath + File.separator + fileName);// added by sel+3
		FileInputStream inputStream;
		XSSFWorkbook workbook = null;
		Row row = null;
		try {
		inputStream = new FileInputStream(file);
		workbook = new XSSFWorkbookFactory().create(inputStream);
		} catch (Exception e) {

		}
		Sheet sheet = workbook.getSheet(sheetName);
		Row firstRow = sheet.getRow(0);
		int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
		for (int i = 1; i <= rowCount; i++) {

		row = sheet.getRow(i);
		for (int j = 0; j < firstRow.getLastCellNum(); j++) {
		if ((row.getCell(j) != null)) {
		if (firstRow.getCell(j).getStringCellValue().equals(attributeName)) {
		sheet.getRow(1).getCell(j).setCellValue(textvalue);
		break;
	             }

	     	}
	   }
	}
		FileOutputStream fos = new FileOutputStream(file);
		workbook.write(fos);
		fos.close();
	}

	public void writeMultipleRowValueInColumnUsingKeyInExcel(String filePath, String fileName, String sheetName,
			String attributeName, String textvalue, int rowNumber) throws IOException {
		File file = new File(filePath + File.separator + fileName);
		FileInputStream inputStream;
		XSSFWorkbook workbook = null;
		int columnNumber = 0;
		try {
			inputStream = new FileInputStream(file);
			workbook = new XSSFWorkbookFactory().create(inputStream);
		} catch (Exception e) {

		}
		Sheet sheet = workbook.getSheet(sheetName);
		Row firstRow = sheet.getRow(0);
		int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
		for (int i = 0; i < rowCount; i++) {
			if (firstRow.getCell(i).getStringCellValue().equals(attributeName)) {
				columnNumber = i;
				break;
			}
		}
		try {
			sheet.getRow(rowNumber).getCell(columnNumber).setCellValue(textvalue);
			FileOutputStream fos = new FileOutputStream(file);
			workbook.write(fos);
			fos.close();
		}catch(Exception e){
			
		}
	}

	// App Specific Method
	public void createZipFileForReport() throws Exception {
		System.out.println("============================" + basePath + "Extent_Reports");
		copyDir(basePath + "Extent_Reports",
				basePath + "Zipped Report" + File.separator);
//		copyDir(basePath + "Failure Screenshots",
//				basePath + "Zipped Report" + File.separator);
//		copyDir(basePath + "Passed Screenshots",
//				basePath + "Zipped Report" + File.separator);

		File directoryToZip = new File(basePath + "Zipped Report" + File.separator);

//		List<File> fileList = new ArrayList<File>();
		System.out.println("---Getting references to all files in: " + directoryToZip.getCanonicalPath());
//		getAllFiles(directoryToZip, fileList);
		attachScreenshotInEmailReport();
		System.out.println("---Creating zip file");
//		writeZipFile(directoryToZip, fileList);
		writeZipFile(directoryToZip);
		System.out.println("---Done");
		moveFile(basePath + "Zipped Report.zip",
				basePath + "Zipped Report/Zipped Report.zip");
	}

	public void copyDir(String source, String destination) {

		File fromDir = new File(source);
		File toDir = new File(destination);


		try {
			FileUtils.copyDirectoryToDirectory(fromDir, toDir);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void copyFile(String source, String destination) {
		File content[] = new File(source).listFiles();

		for (int i = 0; i < content.length; i++) {

			String destiny = destination + content[i].getName();
			File desc = new File(destiny);
			try {
				Files.copy(content[i].toPath(), desc.toPath(), StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				e.printStackTrace();

			}
		}
	}

	public void getAllFiles(File dir, List<File> fileList) {
		try {
			File[] files = dir.listFiles();
			for (File file : files) {
				fileList.add(file);
				if (file.isDirectory()) {
					System.out.println("directory:" + file.getCanonicalPath());
					getAllFiles(file, fileList);
				} else {
					System.out.println("file:" + file.getCanonicalPath());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeZipFile(File directoryToZip) {

		try {
			new ZipFile("Zipped Report.zip").addFolder(new File(basePath + "Zipped Report"));
		} catch (ZipException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		try {
//			FileOutputStream fos = new FileOutputStream(directoryToZip.getName() + ".zip");
//			ZipOutputStream zos = new ZipOutputStream(fos);
//
//			for (File file : fileList) {
//				if (!file.isDirectory()) { // we only zip files, not directories
//					addToZip(directoryToZip, file, zos);
//				}
//			}
//
//			zos.close();
//			fos.close();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

	public void addToZip(File directoryToZip, File file, ZipOutputStream zos)
			throws FileNotFoundException, IOException {

		FileInputStream fis = new FileInputStream(file);

		// we want the zipEntry's path to be a relative path that is relative
		// to the directory being zipped, so chop off the rest of the path
		String zipFilePath = file.getCanonicalPath().substring(directoryToZip.getCanonicalPath().length() + 1,
				file.getCanonicalPath().length());
		System.out.println("Writing '" + zipFilePath + "' to zip file");
		ZipEntry zipEntry = new ZipEntry(zipFilePath);
		zos.putNextEntry(zipEntry);

		byte[] bytes = new byte[1024];
		int length;
		while ((length = fis.read(bytes)) >= 0) {
			zos.write(bytes, 0, length);
		}

		zos.closeEntry();
		fis.close();
	}

	public void moveFile(String source, String destination) throws IOException {
		Path temp = Files.move(Paths.get(source), Paths.get(destination));
		if (temp != null) {
			System.out.println("File renamed and moved successfully");
		} else {
			System.out.println("Failed to move the file");
		}
	}

	public void cleanDir(String dirName) {
		File directory = new File(dirName);

		// Get all files in directory
		File[] files = directory.listFiles();
		for (File file : files) {
			// Delete each file
			if (!file.delete()) {
				// Failed to delete file
				System.out.println("Failed to delete " + file);
			}
		}
	}

	public void cleanFolder(String dirName) {//created by sele+4
		File directory = new File(dirName);

		// Get all files in directory
		File[] files = directory.listFiles();
		for (File file : files) {
				// Delete each file
				file.delete();
			try {
				FileUtils.deleteDirectory(file);
			} catch (Exception e) {
				System.err.print("Failed to delete " + file);
			}

		}
	}

	public void attachScreenshotInEmailReport() throws Exception {
		String OS = System.getProperty("os.name", "generic").toLowerCase();

		Path currentRelativePath = Paths.get("");
		String s = currentRelativePath.toAbsolutePath().toString();
		String zipCandidates = s + File.separator + "Zipped Report" + File.separator + "Extent_Reports" + File.separator;

		File input = new File(zipCandidates + "extentreport.html");
		Document doc = Jsoup.parse(input, "UTF-8");

		for (Element img : doc.select("img[src]")) {
			String fileName = img.attr("src");
			String[] bits;
			if ((OS.indexOf("mac") >= 0)) {
				bits = fileName.split("/");
			} else {
				fileName.replace("\\", "\\\\");
				bits = fileName.split("\\\\");
			}

			String imgName = bits[bits.length - 1];
			img.attr("src", imgName);
		}

		for (Element img : doc.select("img[data-featherlight]")) {
			String fileName = img.attr("data-featherlight");
			String[] bits;
			if ((OS.indexOf("mac") >= 0)) {
				bits = fileName.split("/");
			} else {
				fileName.replace("\\", "\\\\");
				bits = fileName.split("\\\\");
			}
			String imgName = bits[bits.length - 1];
			img.attr("data-featherlight", imgName);
		}

		for (Element img : doc.select("img[data-src]")) {
			String fileName = img.attr("data-src");
			String[] bits;
			if ((OS.indexOf("mac") >= 0)) {
				bits = fileName.split("/");
			} else {
				fileName.replace("\\", "\\\\");
				bits = fileName.split("\\\\");
			}
			String imgName = bits[bits.length - 1];
			img.attr("data-src", imgName);
		}

		try {
			String strmb = doc.outerHtml();
			BufferedWriter bw = new BufferedWriter(new FileWriter(zipCandidates + "extentreport.html"));
			bw.write(strmb);
			bw.close();
		} catch (Exception ex) {
			System.out.println("There is an exception " + "\"" + ex.getMessage() + "\"");
		}
	}

	public List<String> getFilesNames(String filePath) {
		List<String> fileNames = new ArrayList<String>();
		File[] files = new File(filePath).listFiles();
		for (File file : files) {
			if (file.isFile()) {
				fileNames.add(file.getName().toString());
			}
		}
		return fileNames;
	}

	public boolean isStringPresentInList(List<String> fileList, String fileName) {
		boolean sts = false;
		for (String file : fileList) {
			if (file.equals(fileName)) {
				sts = true;
				break;
			}
		}
		return sts;
	}

	public boolean isExistColumnKeyFromCSV(String filePath, String fileName,String keyName) {
		boolean keyExist = false;
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";

		try {

			br = new BufferedReader(new FileReader(filePath + File.separator + fileName));
			while ((line = br.readLine()) != null) {
				// use comma as separator
				String[] fileData = line.split(cvsSplitBy);
				for (int i = 0; i < fileData.length; i++) {
					if(fileData[i].contains(keyName)) {
						keyExist = true;
						break;
					}
				}
				if(keyExist)
					break;

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return keyExist;
	}

	public boolean isMultipleStringPresentInList(List<String> fileList, String firstFile, String secondFile) {
		boolean downloadedFileStatus = false;
		for (String firstDownloadedFile : fileList) {
			if (firstDownloadedFile.equals(firstFile)) {
				// downloadedFileStatus = true;
				for (String secondDownloadedFile : fileList) {
					if (secondDownloadedFile.equals(secondFile)) {
						downloadedFileStatus = true;
						break;
					}
				}
			}
		}
		return downloadedFileStatus;
	}

	public synchronized void makeDirectoryEmpty(String filePath)  {
		try {
			FileUtils.cleanDirectory(new File(filePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public  String readQRCodeAndBarCode(String filePath) throws NotFoundException, IOException {
	    File file = new File(filePath);
	    String decodedText = null;
        BufferedImage bufferedImage = ImageIO.read(file);
        LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Result result = new MultiFormatReader().decode(bitmap);
        decodedText =  result.getText();
        return decodedText;

	}

	public void takeScreenShot(RemoteWebDriver driver,String filePath) throws IOException {
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File(filePath));
	}


	public  String readDataFromTextFile(String filePath,String fileName) {
		String value=null;

		try {
			FileReader fr=new FileReader(filePath+fileName);
			BufferedReader br= new BufferedReader(fr);

			String sCurrentLine="";
			String data="";
			while((sCurrentLine=br.readLine()) != null) {
				  data=data+sCurrentLine;
			}
			value=data;
			}
			catch (Exception e) {
			}

		return value;
	}
	
	public  JSONObject updateDataFromJSONFile(String filePath,String jsonFileName,String keyName,String updatevalue) {
		Object obj =null;
		JSONObject jsonObject ;
		String key = "";
		String value = "";
		Set<Map.Entry<String, String>> entrySet = new HashSet<Map.Entry<String,String>>();
		try {
			File[] files = new File(filePath).listFiles();
			for (File file : files) {
				if (file.isFile()) {
					if(file.getName().toString().contains("solution-86178.json")) {
					obj = new JSONParser().parse(new FileReader(filePath+file.getName().toString()));
					break;
					}

				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//String str  = String.valueOf(obj);
		jsonObject=  (JSONObject) obj;
		JSONObject getObject = (JSONObject) jsonObject.get("taskTypes");
		
		entrySet = getObject.entrySet();
		 for(Map.Entry<String, String> entryObj:entrySet) {
				System.out.println(entryObj.getKey().trim());
				//System.out.println(entryObj.getValue().trim());
			if(entryObj.getKey().trim().equals("taskTypes")) {
				System.out.println(entryObj.getValue().trim());
			}
		 }
		
		
		//jo.put(keyName, updatevalue);
		
		return jsonObject;
	}


	/**
	 * This method is reading data from JSON format file
	 * created by Sel+4
	 * @param filePath
	 * @param jsonFileName
	 * @return
	 */
	public JSONObject getReadDataFromJson(String filePath, String jsonFileName)  {
		
		Object obj =null;
		try {
			File[] files = new File(filePath).listFiles();
			for (File file : files) {
				if (file.isFile()) {
					if(file.getName().toString().contains(jsonFileName)) {
					obj = new JSONParser().parse(new FileReader(filePath+file.getName().toString()));
					break;
					}

				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		JSONObject jo = (JSONObject) obj; 
		return jo;
	}
	
	public void deleteAllRowDataFromSelectedColumnByKeyInExcel(String filePath, String fileName, String sheetName, String attributeName) throws Exception {
		File file = new File(filePath + "\\" + fileName);
		FileInputStream inputStream;
		XSSFWorkbook workbook = null;
		int requiredColumnNumber=0;
		try {
			inputStream = new FileInputStream(file);
			workbook = new XSSFWorkbookFactory().create(inputStream);
		} catch (Exception e) {

		}
		Sheet sheet = workbook.getSheet(sheetName);
		Row firstRow = sheet.getRow(0);
		int totalColumnAvailable=firstRow.getLastCellNum()-firstRow.getLastCellNum();
		for(int i=0;i<totalColumnAvailable;i++){
			if(firstRow.getCell(i).getStringCellValue().equals(attributeName)){
				System.out.println(requiredColumnNumber);
				requiredColumnNumber=i;
				break;
			}
		}
		int rowCount=sheet.getLastRowNum()-sheet.getFirstRowNum();
	    for(int j=1; j<=rowCount;j++){
	    	Row row=sheet.getRow(j);
	    	row.getCell(requiredColumnNumber).setCellValue("");;
	    }
	    FileOutputStream fos = new FileOutputStream(file);
		workbook.write(fos);
		fos.close();
	}
	




	public void writeDataInExcel(String filePath, List<String> listOfRoomName,String sheetName,String attribute,String fileName) throws FileNotFoundException, IOException {
		System.out.println("writeDataInExcel");
		@SuppressWarnings("resource")
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet(sheetName);
		int rowCount = 0;
		XSSFCell sr = sheet.createRow(0).createCell(0);
		sr.setCellValue((String)attribute);
		XSSFCellStyle cellStyle = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		cellStyle.setFont(font);
		sr.setCellStyle(cellStyle);
		for (String attributeName : listOfRoomName) {
			Row row = sheet.createRow(++rowCount);
			int columnCount = 0;
			Cell cell = row.createCell(columnCount++);
			if (attributeName instanceof String) {
				cell.setCellValue((String) attributeName);
			}
		}

		try (FileOutputStream outputStream = new FileOutputStream(filePath+fileName)) {
			workbook.write(outputStream);
		}
	}
	
public void writeDataInExcelSheet(String filePath, String listOfRoomName,String sheetName,String attribute,String fileName) throws FileNotFoundException, IOException {
	System.out.println("writeDataInExcel");
	@SuppressWarnings("resource")
	XSSFWorkbook workbook = new XSSFWorkbook();
	XSSFSheet sheet = workbook.createSheet(sheetName);
	int rowCount = 0;
	XSSFCell sr = sheet.createRow(0).createCell(0);
	sr.setCellValue((String)attribute);
	XSSFCellStyle cellStyle = workbook.createCellStyle();
	XSSFFont font = workbook.createFont();
	font.setBold(true);
	cellStyle.setFont(font);
	sr.setCellStyle(cellStyle);
		Row row = sheet.createRow(++rowCount);
		int columnCount = 0;
		Cell cell = row.createCell(columnCount++);
		if (listOfRoomName instanceof String) {
			cell.setCellValue((String) listOfRoomName);
		}
	

	try (FileOutputStream outputStream = new FileOutputStream(filePath+fileName)) {
		workbook.write(outputStream);
	}
}

	
	public String readData(String filePath, String sheetName,int rownum ,int cellnum ) throws IOException
	{
	
		FileInputStream fis = null;
		try 
		{
			fis = new FileInputStream(new File(filePath));
			XSSFWorkbook wb = new XSSFWorkbook(fis);
			XSSFSheet sh=wb.getSheet(sheetName);
			XSSFCell cell = sh.getRow(rownum).getCell(cellnum);

			if(sh.getRow(rownum).getCell(cellnum)!=null)
			{
				if(sh.getRow(rownum).getCell(cellnum).getCellType()==CellType.STRING)
				{
					data=sh.getRow(rownum).getCell(cellnum).getStringCellValue();
				}
				else if(sh.getRow(rownum).getCell(cellnum).getCellType()==CellType.NUMERIC)
				{
					if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(sh.getRow(rownum).getCell(cellnum)))
					{
						SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy HH:mm");
						data=sdf.format(sh.getRow(rownum).getCell(cellnum).getDateCellValue());
					}
					else
					{
						double ndata = sh.getRow(rownum).getCell(cellnum).getNumericCellValue();	
						if(ndata%1==0)
						{
							long datanew = (long)ndata;	
							data=String.valueOf(datanew);
						}
						else
						{
							data=String.valueOf(ndata);	
						}
					}
				}
				else if(sh.getRow(rownum).getCell(cellnum).getCellType()==CellType.BLANK)
				{
					data=sh.getRow(rownum).getCell(cellnum).getStringCellValue();	
				}
			}
			else
			{
				data="";
			}
		} 
		catch (Exception e) 
		{
			data="";
		}
		return data;
	}
	public String   entryCountFromExcel (String filePath, String fileName) {
		File file = new File(filePath + File.separator + fileName);
		FileInputStream inputStream;
		XSSFWorkbook workbook = null;
		int entryCount = 0;
		String entryCountOne = "";
		try {
			inputStream = new FileInputStream(file);
           workbook = new XSSFWorkbookFactory().create(inputStream);
           Sheet sheet = workbook.getSheetAt(0); 
			int rowCount = sheet.getPhysicalNumberOfRows(); 
             entryCount = rowCount - 1;
           System.out.println("Number of entries (excluding header): " + entryCount);

		} catch (IOException e) {
			e.printStackTrace();
		}
		entryCountOne= Integer.toString(entryCount); 
		return entryCountOne;
			    }
	
}
