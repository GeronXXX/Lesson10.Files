package guru.qa.parsing;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import jdk.jfr.Description;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileZipTest {
    private ClassLoader cl = FileZipTest.class.getClassLoader();

    @Test
    @Description("Проверка PDF файла")
    void zipPdfTest() throws Exception {
        try (InputStream is = cl.getResourceAsStream("File.zip");
             ZipInputStream zi = new ZipInputStream(is)) {
            ZipEntry entry;
            while ((entry = zi.getNextEntry()) != null) {
                if (entry.getName().equals("sample1.pdf")) {
                    PDF pdf = new PDF(zi);
                    assertEquals("Adobe InDesign CC 2015 (Macintosh)", pdf.creator);
                }
            }
        }
    }
    @Test
    @Description("Проверка XLS файла")
    void zipXlsTest() throws Exception {
        try (InputStream is = cl.getResourceAsStream("File.zip");
             ZipInputStream xl = new ZipInputStream(is)) {
            ZipEntry entry;
            while ((entry = xl.getNextEntry()) != null) {
                if (entry.getName().equals("sample1.xlsx")) {
                    XLS xls = new XLS(xl);
                    Assertions.assertTrue(
                            xls.excel.getSheetAt(0).getRow(4).getCell(0).getStringCellValue()
                                    .startsWith("2073"));
                    assertEquals("123.0", xls.excel.getSheetAt(0).getRow(4).getCell(1).toString());
                    assertEquals("John", xls.excel.getSheetAt(0).getRow(4).getCell(2).toString());
                    assertEquals("2011.0", xls.excel.getSheetAt(0).getRow(4).getCell(3).toString());
                    assertEquals("44491.14212108646", xls.excel.getSheetAt(0).getRow(4).getCell(4).toString());
                }
            }
        }
    }
    @Test
    @Description("Проверка CSV файла")
    void zipCsvTest() throws Exception {
        try (InputStream is = cl.getResourceAsStream("File.zip");
             ZipInputStream zi = new ZipInputStream(is)) {
            ZipEntry entry;
            while ((entry = zi.getNextEntry()) != null) {
                if (entry.getName().equals("gaguru.csv")) {
                    CSVReader csvReader = new CSVReader(new InputStreamReader(zi));
                    List<String[]> content = csvReader.readAll();
                    Assertions.assertArrayEquals(new String[] {"Guru", " QA"}, content.get(0));
                }
            }
        }
    }
}



