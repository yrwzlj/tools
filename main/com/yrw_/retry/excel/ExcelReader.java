package com.yrw_.retry.excel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: rw.yang
 * @DateTime: 2025/6/2
 **/
import io.netty.util.internal.ObjectUtil;
import io.swagger.models.auth.In;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.StringUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.ObjectUtils;

public class ExcelReader {

    /**
     * 标题所在行
     */
    static final Integer TITLE_ROW = 1;
    /**
     * 开始读取数据所在的行
     */
    static final Integer DATA_ROW = 2;

    /**
     * 当前读取第x个工作表
     */
    static final Integer SHEET_INDEX = 0;

    /**
     * 当前读取第x个列
     */
    static final Integer START_COLUMN = 3;

    public static void main(String[] args) throws InterruptedException {
        String excelPath = "./excel01.xlsx"; // 替换为实际路径

        List<String> records = readExcel(excelPath);
        //writeExcel(wordList, excelPath);

        for (String word : records) {
            String path = "./sound.mp3";
            ReadWordUtil.fetchYouDaoPronunciation(word, path);
            JLayerMP3Player.playMP3(path);
            Thread.sleep(1000);
        }
    }

//    // 将单词列表写入Excel
//    public static void writeExcel(List<String> wordList, String filePath) {
//        try (Workbook workbook = new XSSFWorkbook();
//             FileOutputStream fileOut = new FileOutputStream(filePath)) {
//
//            // 创建工作表
//            Sheet sheet = workbook.createSheet("单词列表");
//
//            // 创建标题行样式
//            CellStyle headerStyle = createHeaderStyle(workbook);
//
//            // 创建标题行
//            Row headerRow = sheet.createRow(0);
//            String[] headers = {"序号", "单词", "释义", "听写1", "错词提示", "听写2", "错词提示", "听写3", "错词提示"};
//
//            for (int i = 0; i < headers.length; i++) {
//                Cell cell = headerRow.createCell(i);
//                cell.setCellValue(headers[i]);
//                cell.setCellStyle(headerStyle);
//            }
//
//            // 填充数据
//            for (int i = 0; i < wordList.size(); i++) {
//                WordRecord record = wordList.get(i);
//                Row dataRow = sheet.createRow(i + 1);
//
//                // 基础信息
//                dataRow.createCell(0).setCellValue(record.id);
//                dataRow.createCell(1).setCellValue(record.word);
//                dataRow.createCell(2).setCellValue(record.meaning);
//
//                // 听写结果和错词提示
//                int colIndex = 3;
//                for (int j = 0; j < 3; j++) {
//                    if (record.dictationResults[j][0] != null) {
//                        dataRow.createCell(colIndex++).setCellValue(record.dictationResults[j][0]);
//                    } else {
//                        colIndex++;
//                    }
//
//                    if (record.dictationResults[j][1] != null) {
//                        dataRow.createCell(colIndex++).setCellValue(record.dictationResults[j][1]);
//                    } else {
//                        colIndex++;
//                    }
//                }
//            }
//
//            // 自动调整列宽
//            for (int i = 0; i < headers.length; i++) {
//                sheet.autoSizeColumn(i);
//            }
//
//            // 写入文件
//            workbook.write(fileOut);
//
//        } catch (Exception e) {
//            System.err.println("写入Excel失败: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }

    public static List<String> readExcel(String filePath) {
        List<String> words = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(SHEET_INDEX); // 获取第一个工作表

            // 创建列名映射字典
            Map<String, Integer> columnIndexMap = createColumnIndexMap(sheet);

            // 从第二行开始遍历（跳过标题行）
            for (int rowNum = DATA_ROW; rowNum <= sheet.getLastRowNum(); rowNum++) {
                Row row = sheet.getRow(rowNum);
                if (row == null) continue;

                // 读取基本列
                // int id = getIntCellValue(row, "序号", columnIndexMap);
                String word = getStringCellValue(row, "单词", columnIndexMap);
                String meaning = getStringCellValue(row, "释义", columnIndexMap);

                Cell indexCell = row.getCell(START_COLUMN);
                String wordByUser = getCellStringValue(indexCell);

                Cell indexCellAddOne = row.getCell(START_COLUMN + 1);
                String errorAlert = getCellStringValue(indexCellAddOne);
                if (!ObjectUtils.nullSafeEquals(wordByUser, word)) {
                    words.add(word);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return words;
    }

    // 创建列名到索引的映射字典
    private static Map<String, Integer> createColumnIndexMap(Sheet sheet) {
        Map<String, Integer> map = new HashMap<>();
        Row headerRow = sheet.getRow(TITLE_ROW);

        if (headerRow != null) {
            for (int cellNum = 0; cellNum < headerRow.getLastCellNum(); cellNum++) {
                Cell cell = headerRow.getCell(cellNum);
                if (cell != null) {
                    String cellValue = getCellStringValue(cell);
                    map.put(cellValue, cellNum);
                }
            }
        }
        return map;
    }

    // 获取整型单元格值
    private static int getIntCellValue(Row row, String columnName, Map<String, Integer> columnMap) {
        Cell cell = row.getCell(columnMap.getOrDefault(columnName, -1));
        if (cell == null) return 0;

        if (cell.getCellType() == CellType.NUMERIC) {
            return (int) cell.getNumericCellValue();
        } else {
            return Integer.parseInt(getCellStringValue(cell));
        }
    }

    // 获取字符串单元格值（重载1 - 单列）
    private static String getStringCellValue(Row row, String columnName, Map<String, Integer> columnMap) {
        Integer index = columnMap.get(columnName);
        if (index == null) return null;

        return getCellStringValue(row.getCell(index));
    }

    // 获取字符串单元格值（重载2 - 多列处理）
    private static String getStringCellValue(Row row, String columnName, Map<String, Integer> columnMap, int repeatIndex) {
        // 查找所有匹配的列索引
        List<Integer> indices = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : columnMap.entrySet()) {
            if (entry.getKey().equals(columnName)) {
                indices.add(entry.getValue());
            }
        }

        // 获取对应序列的列
        if (repeatIndex <= indices.size()) {
            return getCellStringValue(row.getCell(indices.get(repeatIndex - 1)));
        }
        return null;
    }

    // 通用单元格值获取
    private static String getCellStringValue(Cell cell) {
        if (cell == null) return null;

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                return String.valueOf((int) cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }
}