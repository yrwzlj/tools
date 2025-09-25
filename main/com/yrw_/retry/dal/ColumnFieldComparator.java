package com.yrw_.retry.dal;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

/**
 * @Author: rw.yang
 * @DateTime: 2025/9/23
 **/
public class ColumnFieldComparator {

    public static void main(String[] args) throws IOException {
        // 修改为你的路径
        File rootDir = new File("D:\\Users\\yangrw\\IdeaProjects\\flight\\orderstatuspushservice\\orderstatuschange-service\\src\\main\\java\\com\\ctrip\\corp\\orderstatuschange\\service\\entity\\fltordersharddb");

        scanDirectory(rootDir);
    }

    public static void scanDirectory(File dir) throws IOException {
        if (!dir.exists()) {
            System.err.println("目录不存在: " + dir.getAbsolutePath());
            return;
        }

        Files.walk(dir.toPath())
                .filter(p -> p.toString().endsWith(".java"))
                .forEach(path -> {
                    try {
                        compareColumnWithField(path.toFile());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }

    public static void compareColumnWithField(File file) throws IOException {
        List<String> lines = Files.readAllLines(file.toPath());

        String lastColumnName = null;

        for (String rawLine : lines) {
            String line = rawLine.trim();

            // 找 @Column(name = "xxx")
            if (line.startsWith("@Column") && line.contains("name")) {
                int start = line.indexOf("name");
                int eq = line.indexOf("=", start);
                int firstQuote = line.indexOf("\"", eq);
                int secondQuote = line.indexOf("\"", firstQuote + 1);
                if (firstQuote > 0 && secondQuote > firstQuote) {
                    lastColumnName = line.substring(firstQuote + 1, secondQuote);
                }
            }

            // 找字段定义
            if (lastColumnName != null && line.startsWith("private")) {
                String[] parts = line.split("\\s+");
                if (parts.length >= 3) {
                    String fieldName = parts[2].replace(";", "");

                    // 比较：忽略大小写，不忽略下划线
                    if (!lastColumnName.equalsIgnoreCase(fieldName)) {
                        System.out.printf("文件[%s] 列名[%s] 字段名[%s] 不一致%n",
                                file.getName(), lastColumnName, fieldName);
                    }
                }
                lastColumnName = null; // 重置
            }
        }
    }
}