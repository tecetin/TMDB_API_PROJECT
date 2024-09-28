package utilities;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelUtility {

    private Workbook workbook;
    private String excelFilePath;

    public Sheet excelDosyasiOlustur(String dosyaAdi, String sheetName, List<String> baslikSatiri){

        dosyaAdi = dosyaAdi.replaceAll(" ", "");  // Dosya adındaki boşlukları kaldırıyoruz
        excelFilePath = "C:/Users/tugba/IdeaProjects/TMDB_APIProject/" + dosyaAdi + ".xlsx";

        // Excel Workbook'u ve Sheet'i oluşturuyoruz
        workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(sheetName);

        // Başlık satırını ekliyoruz
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < baslikSatiri.size() ; i++) {
            String baslik = baslikSatiri.get(i);
            headerRow.createCell(i).setCellValue(baslik);

        }
        return sheet;  // Sheet'i geri döndürüyoruz
    }

    // Var olan dosyayı açma ve veri eklemek için Sheet döndürme
    public Sheet excelDosyasiniAc(String dosyaAdi, String sheetName) throws IOException {

        dosyaAdi = dosyaAdi.replaceAll(" ", "");  // Dosya adındaki boşlukları kaldırıyoruz
        excelFilePath = "C:/Users/tugba/IdeaProjects/TMDB_APIProject/" + dosyaAdi + ".xlsx";

        // Var olan Excel dosyasını açıyoruz
        FileInputStream fileInputStream = new FileInputStream(excelFilePath);
        workbook = new XSSFWorkbook(fileInputStream);

        Sheet sheet = workbook.getSheet(sheetName);

        // Sayfa mevcut değilse yeni bir sayfa oluşturuyoruz
        if (sheet == null) {
            sheet = workbook.createSheet(sheetName);
        }

        return sheet;
    }

    // Verileri kaydedip dosyayı kapatma metodu
    public void excelDosyasiniKapat() throws IOException {

        // Verileri dosyaya geri yazıyoruz
        try (FileOutputStream fileOut = new FileOutputStream(excelFilePath)) {
            workbook.write(fileOut);
        }

        // Workbook'u kapatıyoruz
        workbook.close();
    }
}
