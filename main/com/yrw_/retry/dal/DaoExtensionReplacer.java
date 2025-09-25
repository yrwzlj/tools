package com.yrw_.retry.dal;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: rw.yang
 * @DateTime: 2025/8/22
 **/
public class DaoExtensionReplacer {

    public static void main(String[] args) {
        String folderPath = "D:\\Users\\yangrw\\IdeaProjects\\flight\\OrderXProductService\\model\\src\\main\\java\\com\\ctrip\\corp\\order\\xproduct\\model\\detail\\dto";
        File folder = new File(folderPath);

        if (folder.exists() && folder.isDirectory()) {
            processFolder(folder);
        } else {
            System.out.println("指定的路径不是一个有效的文件夹");
        }
    }

    private static void processFolder(File folder) {
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".java"));
        if (files != null) {
            for (File file : files) {
                try {
                    String content = Files.readString(file.toPath(), StandardCharsets.UTF_8);

                    // 1. 识别 GenericDaoExtension 变量名
                    String varName = findGenericDaoExtensionVarName(content);
                    if (varName == null) {
                        continue;
                    }
                    System.out.println(file.getName() + " -> 变量名: " + varName);

                    // 2. 替换方法调用
                    String modifiedContent = replaceDaoExtensionCalls(content, varName);

                    if (!content.equals(modifiedContent)) {
                        Files.writeString(file.toPath(), modifiedContent, StandardCharsets.UTF_8);
                        System.out.println("Modified: " + file.getName());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static String findGenericDaoExtensionVarName(String content) {
        Pattern p = Pattern.compile("GenericDaoExtension\\s+(\\w+)\\s*;");
        Matcher m = p.matcher(content);
        if (m.find()) {
            return m.group(1); // 变量名
        }
        return null;
    }

    private static String replaceDaoExtensionCalls(String content, String varName) {
        Pattern queryLikePattern = Pattern.compile(varName + "\\.queryLike\\((\\w+),\\s*(\\w+)\\)");
        content = queryLikePattern.matcher(content).replaceAll("baseMapper.queryBy($2, $1)");

        Pattern queryByPkPattern = Pattern.compile(varName + "\\.queryByPk\\((\\w+),\\s*(\\w+)\\)");
        content = queryByPkPattern.matcher(content).replaceAll("baseMapper.queryByPk($2, $1)");

        Pattern updatePattern = Pattern.compile(varName + "\\.update\\((\\w+),\\s*(\\w+)\\)");
        content = updatePattern.matcher(content).replaceAll("baseMapper.update($2, $1)");

        Pattern batchUpdatePattern = Pattern.compile(varName + "\\.batchUpdate\\((\\w+),\\s*(\\w+)\\)");
        content = batchUpdatePattern.matcher(content).replaceAll("baseMapper.batchUpdate($2, $1)");

        content = replaceInsertCalls(content, varName);

        return content;
    }

    private static String replaceInsertCalls(String content, String varName) {
        // 匹配 insert(参数1, 参数2)
        Pattern insertPattern = Pattern.compile(varName + "\\.insert\\((\\w+),\\s*(\\w+)\\)");
        Matcher matcher = insertPattern.matcher(content);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String firstArg = matcher.group(1); // 第一个参数
            String secondArg = matcher.group(2); // 第二个参数

            String replacement = "UDLProcessor.fillUDL(new Object[]{" + firstArg + ", " + secondArg + "});\n" +
                    "baseMapper.combinedInsert(" + secondArg + ", " + firstArg + ");";
            matcher.appendReplacement(sb, replacement);
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
}