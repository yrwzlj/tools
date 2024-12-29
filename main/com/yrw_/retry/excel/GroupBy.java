package com.yrw_.retry.excel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupBy {

    static String[] lines;

    public static void main(String[] args) {
        Map<String, List<Product>> map = new HashMap<>();
        // 第二个参数为文件路径
        //readCsvByBufferedReader(map, "C:\\Users\\Lenovo\\Desktop\\csv\\202301W1-product.csv");
        readCsvByBufferedReader(map, "C:\\Users\\Lenovo\\Desktop\\csv\\202301W2-product.csv");
        readCsvByBufferedReader(map, "C:\\Users\\Lenovo\\Desktop\\csv\\202301W3-product.csv");
        //readCsvByBufferedReader(map, "C:\\Users\\Lenovo\\Desktop\\csv\\202301W4-product.csv");


        Map<String, Product> result = new HashMap<>();
        merge(map, result);
        System.out.println(result);

        // 结果文件
        write(result);
    }

    private static void write(Map<String, Product> result) {
        try {
            // 1. 创建流
            // 结果文件路径
            String filepath = "C:\\Users\\Lenovo\\Desktop\\csv\\2024-product.csv";
            FileWriter filewriter = new FileWriter(filepath);
            BufferedWriter bufferwriter = new BufferedWriter(filewriter);

            String context = "";
            Map<Integer, String> index = new HashMap<>();
            // 列名
            for (int i = 0; i < lines.length; i++) {
                index.put(i, lines[i]);
                context = context + lines[i];
                if (i != lines.length - 1) {
                    context += ",";
                }
            }
            bufferwriter.write(context);
            bufferwriter.newLine();

            for (Map.Entry<String, Product> entry : result.entrySet()) {
                Product product = entry.getValue();
                context = "";
                
                context += product.商品编码 + ",";
                context += product.卷烟名称 + ",";
                context += product.批发价 + ",";
                context += product.价类 + ",";
                
                Map<String, Integer> temp = product.map;
                for (int i = 4; i < index.size(); i++) {
                    String lineName = index.get(i);
                    Integer val = temp.get(lineName);
                    context += val;
                    if (i != index.size() - 1) {
                        context += ",";
                    }
                }

                bufferwriter.write(context);
                bufferwriter.newLine();
            }

            bufferwriter.close();
            filewriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 相同列合并
     * @param map
     * @param result
     */
    private static void merge(Map<String, List<Product>> map, Map<String, Product> result) {
        for (Map.Entry<String, List<Product>> entry : map.entrySet()) {
            List<Product> products = entry.getValue();
            Map<String, Integer> all =  new HashMap<>();
            for (int i = 0; i < products.size(); i++) {
                Map<String, Integer> ele = products.get(i).map;
                for (Map.Entry<String, Integer> temp : ele.entrySet()) {
                    if (all.containsKey(temp.getKey())) {
                        all.put(temp.getKey(), all.get(temp.getKey()) + temp.getValue());
                    } else {
                        all.put(temp.getKey(), temp.getValue());
                    }
                }
            }
            Product product = new Product();
            product = products.get(0);
            product.map = all;

            result.put(entry.getKey(), product);
        }
    }

    /**
     * BufferedReader 读取
     * @param filePath
     * @return
     */
    public static void readCsvByBufferedReader(Map<String, List<Product>> map, String filePath) {
        File csv = new File(filePath);
        csv.setReadable(true);
        csv.setWritable(true);
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            isr = new InputStreamReader(new FileInputStream(csv), "gb2312");
            br = new BufferedReader(isr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String line = "";

        try {
            line = br.readLine();
            lines = line.split(",");
            String[] name = new String[lines.length - 4];
            for (int i = 4; i < lines.length; i++) {
                name[i - 4] = lines[i];
            }

            while ((line = br.readLine()) != null) {
                String[] split = line.split(",");
                Product product = new Product();
                product.商品编码 = split[0];
                product.卷烟名称 = split[1];
                product.批发价 = split[2];
                product.价类 = split[3];
                Map<String, Integer> temp = new HashMap<>();
                for (int i = 4; i < split.length; i++) {
                    temp.put(name[i - 4], Integer.valueOf(split[i]));
                }
                product.map = temp;
                if (map.containsKey(split[0])) {
                    map.get(split[0]).add(product);
                } else {
                    List<Product> list = new ArrayList<>();
                    list.add(product);
                    map.put(split[0], list);
                }
                //System.out.println(product);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
