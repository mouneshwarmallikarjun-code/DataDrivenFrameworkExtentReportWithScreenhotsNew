package utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

public final class ExcelWriter {
    private static final Path OUTPUT_DIR = Paths.get("test-data");
    private static final String FILE_NAME =
            "Coursera_OutputSheet" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".xlsx";
    private static final XSSFWorkbook WORKBOOK = new XSSFWorkbook();
    private static final Path OUTPUT_FILE = OUTPUT_DIR.resolve(FILE_NAME);
    private ExcelWriter() {}
    public static void writeList(String sheetName, List<String> data, String columnName) throws IOException {
        Files.createDirectories(OUTPUT_DIR);
        Sheet sheet = WORKBOOK.getSheet(sheetName);
        if (sheet == null) {
            sheet = WORKBOOK.createSheet(sheetName);
        }
        Row header = sheet.getRow(0);
        if (header == null) {
            header = sheet.createRow(0);
        }
        int colIndex = findOrCreateColumn(header, columnName);
        for (int i = 0; i < data.size(); i++) {
            Row row = sheet.getRow(i + 1);
            if (row == null) {
                row = sheet.createRow(i + 1);
            }
            row.createCell(colIndex).setCellValue(data.get(i));
        }
        sheet.autoSizeColumn(colIndex);
        try (FileOutputStream out = new FileOutputStream(OUTPUT_FILE.toFile())) {
            WORKBOOK.write(out);
        }
    }

    private static int findOrCreateColumn(Row header, String name) {
        short last = header.getLastCellNum();  // -1 if empty
        int limit = Math.max(last, 0);
        for (int i = 0; i < limit; i++) {
            Cell c = header.getCell(i);
            if (c != null && name.equalsIgnoreCase(c.getStringCellValue())) {
                return i;
            }
        }
        header.createCell(limit).setCellValue(name);
        return limit;
    }
}