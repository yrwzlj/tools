package com.yrw_.retry.dal;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class DalEntityModifier {

    public static void main(String[] args) throws IOException {
        // 绝对路径，改成你的实体类源码目录
        String sourceDir = "D:\\Users\\yangrw\\IdeaProjects\\flight\\orderstatuspushservice\\orderstatuschange-service\\src\\main\\java\\com\\ctrip\\corp\\orderstatuschange\\service\\entity\\fltordersharddb";

        scanJavaFiles(new File(sourceDir));
    }

    public static void scanJavaFiles(File folder) throws IOException {
        if (!folder.exists()) {
            System.err.println("❌ 路径不存在: " + folder.getAbsolutePath());
            return;
        }

        File[] files = folder.listFiles();
        if (files == null) return;

        for (File file : files) {
            if (file.isDirectory()) {
                scanJavaFiles(file); // 递归扫描子目录
            } else if (file.getName().endsWith(".java")) {
                checkJavaFile(file);
            }
        }
    }

    public static void checkJavaFile(File javaFile) throws IOException {
        List<String> lines = Files.readAllLines(javaFile.toPath());
        String lastColumnName = null;
        List<String> mismatches = new ArrayList<>();

        for (String line : lines) {
            line = line.trim();

            // 找到 @Column(name="...")
            if (line.startsWith("@Column") && line.contains("name")) {
                int start = line.indexOf("name");
                int eq = line.indexOf("=", start);
                int firstQuote = line.indexOf("\"", eq);
                int secondQuote = line.indexOf("\"", firstQuote + 1);
                if (firstQuote > 0 && secondQuote > firstQuote) {
                    lastColumnName = line.substring(firstQuote + 1, secondQuote);
                }
            }

            // 找到字段定义
            if (lastColumnName != null && line.startsWith("private")) {
                String[] parts = line.split("\\s+");
                if (parts.length >= 3) {
                    String fieldName = parts[2].replace(";", "");

                    // 规则：忽略大小写
                    String normalizedColumnName = lastColumnName.toLowerCase();
                    String normalizedFieldName = fieldName.toLowerCase();

                    if (!normalizedColumnName.equals(normalizedFieldName)) {
                        mismatches.add(String.format("  @Column(name=\"%s\") ↔ 字段名: %s", lastColumnName, fieldName));
                    }
                }
                lastColumnName = null; // 重置
            }
        }

        if (!mismatches.isEmpty()) {
            System.out.println("文件: " + javaFile.getName());
            mismatches.forEach(System.out::println);
            System.out.println("--------------------------------------");
        }
    }
}