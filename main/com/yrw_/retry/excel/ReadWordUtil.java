package com.yrw_.retry.excel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @Author: rw.yang
 * @DateTime: 2025/6/2
 **/
public class ReadWordUtil {

    public static void main(String[] args) throws InterruptedException {
        String savePath = "sound.mp3";

        String word = "hotel";
        fetchYouDaoPronunciation(word, savePath);
        JLayerMP3Player.playMP3(savePath);

        Thread.sleep(1000);

        word = "meal";
        fetchYouDaoPronunciation(word, savePath);
        JLayerMP3Player.playMP3(savePath);
    }

    /**
     * 获取有道单词发音并保存为MP3文件
     * @param word     要发音的单词
     * @param filePath 保存MP3的路径（如 "hello.mp3"）
     */
    public static void fetchYouDaoPronunciation(String word, String filePath) {
        try {
            // 1. 创建请求URL
            String apiUrl = "http://dict.youdao.com/dictvoice?type=0&audio=" + word;
            URL url = new URL(apiUrl);

            // 2. 打开HTTP连接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // 3. 检查响应状态
            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                System.err.println("请求失败，响应码: " + responseCode);
                return;
            }

            // 4. 获取音频流并保存文件
            try (InputStream in = connection.getInputStream();
                 FileOutputStream out = new FileOutputStream(filePath)) {

                byte[] buffer = new byte[4096];
                int bytesRead;

                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }

                System.out.println("发音已保存到: " + new File(filePath).getAbsolutePath());
            }

            // 5. 关闭连接
            connection.disconnect();

        } catch (IOException e) {
            System.err.println("发生错误: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
