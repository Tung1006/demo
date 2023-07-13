package com.demo.component.commune;


import com.demo.component.commune.entity.Commune;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class ImportCommune {
    public static final int COLUMN_INDEX_ID = 0;
    public static final int COLUMN_INDEX_CODE = 1;
    public static final int COLUMN_INDEX_NAME = 2;
    public static final int COLUMN_INDEX_FOUNDEDYEAR = 3;
//    public static final int COLUMN_INDEX_ACREAGE = 4;

    public static void main(String[] args) throws IOException {
        final String excelFilePath = "C:/Users/dieul/Downl/users_.xlsx";
        final List<Commune> listCommune = readExcel(excelFilePath);
        for (Commune commune : listCommune) {
            System.out.println(commune);
        }
    }

    public static List<Commune> readExcel2(String filePath) {
        List<Commune> listCommune = new ArrayList<>();
        File file = new File(filePath);
        try {
            FileInputStream inputStream = new FileInputStream(file);
            Workbook workbook = new XSSFWorkbook(inputStream);
            Commune commune = new Commune();
            for (Sheet sheet : workbook) {
                int firstRow = sheet.getFirstRowNum();
                int lastRow = sheet.getLastRowNum();
                for (int index = firstRow + 1; index <= lastRow; index++) {
                    Row row = sheet.getRow(index);
                    for (int cellIndex = row.getFirstCellNum(); cellIndex < row.getLastCellNum(); cellIndex++) {
                        Cell cell = row.getCell(cellIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                        getCellValue(cell);
                        commune.setCode((cell.getStringCellValue()) + "|");
                    }
                }

                System.out.println("kkkkkkkkkkkkkkkkkkkkk" + commune);
                listCommune.add(commune);
            }
            inputStream.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return listCommune;
    }

    public static List<Commune> readExcel(String excelFilePath) throws IOException {
        List<Commune> listCommune = new ArrayList<>();

        // Get file
        InputStream inputStream = new FileInputStream(new File(excelFilePath));

        // Get workbook
        Workbook workbook = getWorkbook(inputStream, excelFilePath);

        // Get sheet
        Sheet sheet = workbook.getSheetAt(0);

        // Get all rows
        Iterator<Row> iterator = sheet.iterator();
        while (iterator.hasNext()) {
            Row nextRow = iterator.next();
            if (nextRow.getRowNum() == 0) {
                // Ignore header
                continue;
            }

            // Get all cells
            Iterator<Cell> cellIterator = nextRow.cellIterator();

            // Read cells and set value for book object
            Commune commune = new Commune();
            while (cellIterator.hasNext()) {
                //Read cell
                Cell cell = cellIterator.next();
//                Object cellValue = getCellValue(cell);
//                if (cellValue == null || cellValue.toString().isEmpty()) {
//                    continue;
//                }
                // Set value for book object
                int columnIndex = cell.getColumnIndex();
                switch (columnIndex) {
                    case COLUMN_INDEX_ID:
                        commune.setId((UUID) getCellValue(cell));
                        break;
                    case COLUMN_INDEX_CODE:
                        commune.setCode(cell.getNumericCellValue() + "");
                        break;
                    case COLUMN_INDEX_NAME:
                        commune.setName(cell.getStringCellValue());
                        System.out.println("jjjjjjjjjj" + cell.getStringCellValue());
                        break;
                    case COLUMN_INDEX_FOUNDEDYEAR:
                        commune.setFoundedYear((int) cell.getNumericCellValue());
                        System.out.println(" híahisaiss" + cell.getNumericCellValue());
                        break;
//                    case COLUMN_INDEX_ACREAGE:
//                        commune.setAcreage((Double)getCellValue(cell));
//                        System.out.println(" híahisaiss"+ getCellValue(cell));
//                        break;
                    default:
                        break;
                }

            }
            listCommune.add(commune);
        }

        workbook.close();
        inputStream.close();

        return listCommune;
    }

    // Get Workbook
    private static Workbook getWorkbook(InputStream inputStream, String excelFilePath) throws IOException {
        Workbook workbook = null;
        if (excelFilePath.endsWith("xlsx")) {
            workbook = new XSSFWorkbook(inputStream);
        } else if (excelFilePath.endsWith("xls")) {
            workbook = new HSSFWorkbook(inputStream);
        } else {
            throw new IllegalArgumentException("The specified file is not Excel file");
        }

        return workbook;
    }

    // Get cell value
    private static Object getCellValue(Cell cell) {
        CellType cellType = cell.getCellTypeEnum();
        Object cellValue = null;
        switch (cellType) {
            case BOOLEAN:
                cellValue = cell.getBooleanCellValue();
                break;
            case FORMULA:
                Workbook workbook = cell.getSheet().getWorkbook();
                FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
                cellValue = evaluator.evaluate(cell).getNumberValue();
                break;
            case NUMERIC:
                cellValue = cell.getNumericCellValue();
                break;
            case STRING:
                cellValue = cell.getStringCellValue();
                break;
            case _NONE:
            case BLANK:
            case ERROR:
                break;
            default:
                break;
        }

        return cellValue;
    }
}

