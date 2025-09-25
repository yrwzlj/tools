package com.yrw_.retry.dal;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @Author: rw.yang
 * @DateTime: 2025/8/25
 **/
public class EntityToDtoConverter {

    public static void main(String[] args) throws IOException {
        String inputFolderPath = "D:\\Users\\yangrw\\IdeaProjects\\flight\\orderstatuspushservice\\orderstatuschange-service\\src\\main\\java\\com\\ctrip\\corp\\orderstatuschange\\service\\entity\\fltordersharddb";  // 输入 entity 文件夹路径
        String outputFolderPath = "D:\\Users\\yangrw\\IdeaProjects\\flight\\orderstatuspushservice\\orderstatuschange-model\\src\\main\\java\\com\\ctrip\\corp\\orderstatuschange\\model\\dto\\fltordersharddb"; // 输出 DTO 文件夹路径
        String outputPackage = "com.ctrip.corp.orderstatuschange.model.dto.fltordersharddb";

        File inputFolder = new File(inputFolderPath);
        File outputFolder = new File(outputFolderPath);


        if (!inputFolder.exists() || !inputFolder.isDirectory()) {
            System.out.println("输入路径不是一个有效的文件夹: " + inputFolderPath);
            return;
        }

        // 如果输出目录不存在，则创建
        if (!outputFolder.exists()) {
            outputFolder.mkdirs();
        }

        modifyJavaFiles(inputFolder, outputFolder, outputPackage);
    }

    private static void modifyJavaFiles(File inputFolder, File outputFolder, String outputPackage) {
        File[] files = inputFolder.listFiles((dir, name) -> name.endsWith(".java"));

        if (files != null) {
            for (File file : files) {
                try {
                    pojoToDTO(file.getPath(), outputFolder.getPath(), outputPackage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void pojoToDTO(String inputPath, String outPath, String outputPackage) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(inputPath)));

        // 1. 删除不需要的注解（支持带参数）
        String[] annotations = {
                "Entity",
                "Database",
                "Table",
                "Column",
                "Type",
                "Id",
                "GeneratedValue"
        };

        for (String ann : annotations) {
            // 保留原来的缩进
            content = content.replaceAll("(?m)^(\\s*)@" + ann + "(?:\\([^)]*\\))?\\s*(\\r?\\n)?", "$1");
        }

        // 2. 修改 package 为参数传入的值
        content = content.replaceFirst("(?m)^package\\s+.*?;", "package " + outputPackage + ";");

        // 3. 修改类名
        String fileName = Paths.get(inputPath).getFileName().toString();
        String className = fileName.substring(0, fileName.lastIndexOf("."));
        String dtoClassName = className + "DTO";
        content = content.replaceAll(
                "public class\\s+" + className,
                "@DalEntity(primaryTypeName = \"com.ctrip.corp.order.flight.entity.orderdb." + className + "\")\npublic class " + dtoClassName
        );

        // 4. 修改接口实现
        content = content.replace("implements DalPojo", "implements DalDto");

        // 5. 确保 import DalDto 和 DalEntity 存在
        if (!content.contains("import com.ctrip.flight.common.dalextension.dto.DalDto;")) {
            content = content.replaceFirst("(?m)^package\\s+.*?;",
                    "$0\n\nimport com.ctrip.flight.common.dalextension.dto.DalDto;");
        }
        if (!content.contains("import com.ctrip.flight.common.dalextension.dto.annotation.DalEntity;")) {
            content = content.replaceFirst("(?m)^package\\s+.*?;",
                    "$0\n\nimport com.ctrip.flight.common.dalextension.dto.annotation.DalEntity;");
        }

        // 6. 保存到输出目录
        Path outputPath = Paths.get(outPath, dtoClassName + ".java");
        Files.write(outputPath, content.getBytes());

        System.out.println("转换完成，生成文件：" + outputPath);
    }
}
