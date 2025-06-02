package com.yrw_.retry.excel;

import javazoom.jl.player.Player;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

/**
 * @Author: rw.yang
 * @DateTime: 2025/6/2
 **/
public class JLayerMP3Player {

    public static void main(String[] args) {
        playMP3("sound.mp3");
    }

    public static void playMP3(String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath);
             BufferedInputStream bis = new BufferedInputStream(fis)) {

            System.out.println("开始播放: " + filePath);
            Player player = new Player(bis);
            player.play();
            System.out.println("播放完成");

        } catch (Exception e) {
            System.err.println("播放失败: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
