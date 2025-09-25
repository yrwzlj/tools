package com.yrw_.retry.dal;

/**
 * @Author: rw.yang
 * @DateTime: 2025/8/12
 **/

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SwitchCodeGenerator {

    public static void main(String[] args) {
        // 指定要扫描的目录路径和前缀
        String directoryPath = "D:\\Users\\yangrw\\IdeaProjects\\flight\\orderstatuspushservice\\orderstatuschange-model\\src\\main\\java\\com\\ctrip\\corp\\orderstatuschange\\model\\corpfltorderdb";
        String prefix = "com.ctrip.corp.orderstatuschange.model.corpfltorderdb";

        // 获取目录中的所有文件
        List<File> files = listFiles(directoryPath);
        int i = 0;
        // 生成代码
        // 01 单读Primary，单写Primary 开关
        System.out.println("01 单读Primary，单写Primary 开关");
        for (File file : files) {
            i++;
            // 获取文件名（不包括扩展名）
            String fileName = file.getName().substring(0, file.getName().lastIndexOf('.'));

            // 生成代码行
            String queryLine = String.format("dto.query.%s.%s=p", prefix, fileName);
            String writingLine = String.format("dto.writing.%s.%s=p", prefix, fileName);

            // 输出生成的代码
            System.out.println(queryLine);
            System.out.println(writingLine);
            System.out.println();
        }
        System.out.println("02 异步双写，不抛异常");
        // 02 异步双写，不抛异常
        for (File file : files) {
            i++;
            // 获取文件名（不包括扩展名）
            String fileName = file.getName().substring(0, file.getName().lastIndexOf('.'));

            // 生成代码行
            String queryLine = String.format("dto.query.%s.%s=p", prefix, fileName);
            String writingLine = String.format("dto.writing.%s.%s=dw", prefix, fileName);
            String asyncLine = String.format("asyncdoublewriting.%s.%s=ac", prefix, fileName);

            // 输出生成的代码
            System.out.println(queryLine);
            System.out.println(writingLine);
            System.out.println(asyncLine);
            System.out.println();
        }
        // 03 同步双写，不抛异常
        System.out.println("03 同步双写，不抛异常");
        for (File file : files) {
            i++;
            // 获取文件名（不包括扩展名）
            String fileName = file.getName().substring(0, file.getName().lastIndexOf('.'));

            // 生成代码行
            String queryLine = String.format("dto.query.%s.%s=p", prefix, fileName);
            String writingLine = String.format("dto.writing.%s.%s=dw", prefix, fileName);
            String asyncLine = String.format("asyncdoublewriting.%s.%s=sc", prefix, fileName);

            // 输出生成的代码
            System.out.println(queryLine);
            System.out.println(writingLine);
            System.out.println(asyncLine);
            System.out.println();
        }
        // 04 同步双写，抛异常
        System.out.println("04 同步双写，抛异常");
        for (File file : files) {
            i++;
            // 获取文件名（不包括扩展名）
            String fileName = file.getName().substring(0, file.getName().lastIndexOf('.'));

            // 生成代码行
            String queryLine = String.format("dto.query.%s.%s=p", prefix, fileName);
            String writingLine = String.format("dto.writing.%s.%s=dw", prefix, fileName);
            String asyncLine = String.format("asyncdoublewriting.%s.%s=st", prefix, fileName);

            // 输出生成的代码
            System.out.println(queryLine);
            System.out.println(writingLine);
            System.out.println(asyncLine);
            System.out.println();
        }
        // 05 单读shard
        System.out.println("05 单读shard");
        for (File file : files) {
            i++;
            // 获取文件名（不包括扩展名）
            String fileName = file.getName().substring(0, file.getName().lastIndexOf('.'));

            // 生成代码行
            String queryLine = String.format("dto.query.%s.%s=s", prefix, fileName);
            String writingLine = String.format("dto.writing.%s.%s=dw", prefix, fileName);
            String asyncLine = String.format("asyncdoublewriting.%s.%s=st", prefix, fileName);

            // 输出生成的代码
            System.out.println(queryLine);
            System.out.println(writingLine);
            System.out.println(asyncLine);
            System.out.println();
        }
        // 06 单写shard
        System.out.println("06 单写shard");
        for (File file : files) {
            i++;
            // 获取文件名（不包括扩展名）
            String fileName = file.getName().substring(0, file.getName().lastIndexOf('.'));

            // 生成代码行
            String queryLine = String.format("dto.query.%s.%s=s", prefix, fileName);
            String writingLine = String.format("dto.writing.%s.%s=s", prefix, fileName);
            String asyncLine = String.format("asyncdoublewriting.%s.%s=st", prefix, fileName);

            // 输出生成的代码
            System.out.println(queryLine);
            System.out.println(writingLine);
            System.out.println(asyncLine);
            System.out.println();
        }

        System.out.println(i);
    }

    // 列出指定目录中的所有文件
    private static List<File> listFiles(String directoryPath) {
        File directory = new File(directoryPath);
        List<File> fileList = new ArrayList<>();

        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        fileList.add(file);
                    }
                }
            }
        }
        return fileList;
    }
}