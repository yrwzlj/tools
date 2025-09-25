package com.yrw_.retry.dal;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class ShardPojo {

    // 旧值和新值
    private static final String OLD_PACKAGE = "package com.ctrip.corp.orderstatuschange.service.entity.corpfltorderdb;";
    private static final String NEW_PACKAGE = "package com.ctrip.corp.orderstatuschange.service.entity.fltordersharddb;";

    private static final String OLD_DATABASE = "@Database(name = \"corpfltordermsdb_dalcluster\")";
    private static final String NEW_DATABASE = "@Database(name = \"corpflightordershardbasedb_dalcluster\")";

    public static void main(String[] args) throws IOException {
        Path sourceDir = Paths.get("D:\\Users\\yangrw\\IdeaProjects\\flight\\orderstatuspushservice\\orderstatuschange-service\\src\\main\\java\\com\\ctrip\\corp\\orderstatuschange\\service\\entity\\corpfltorderdb");
        Path targetDir = Paths.get("D:\\Users\\yangrw\\IdeaProjects\\flight\\orderstatuspushservice\\orderstatuschange-service\\src\\main\\java\\com\\ctrip\\corp\\orderstatuschange\\service\\entity\\fltordersharddb");

        if (!Files.exists(sourceDir) || !Files.isDirectory(sourceDir)) {
            System.out.println("原始目录不存在或不是目录: " + sourceDir);
            return;
        }

        // 创建目标目录
        if (!Files.exists(targetDir)) {
            Files.createDirectories(targetDir);
        }

        try (Stream<Path> paths = Files.walk(sourceDir)) {
            paths.filter(p -> p.toString().endsWith(".java"))
                    .forEach(filePath -> processFile(filePath, sourceDir, targetDir));
        }

        System.out.println("✅ 替换并复制完成");
    }

    private static void processFile(Path filePath, Path sourceDir, Path targetDir) {
        try {
            String content = new String(Files.readAllBytes(filePath), StandardCharsets.UTF_8);

            // 替换内容
            String newContent = content.replace(OLD_PACKAGE, NEW_PACKAGE)
                    .replace(OLD_DATABASE, NEW_DATABASE);

            // 计算新文件路径（保持原目录结构）
            Path relativePath = sourceDir.relativize(filePath);
            Path newFilePath = targetDir.resolve(relativePath);

            // 确保目录存在
            Files.createDirectories(newFilePath.getParent());

            // 写入新文件
            Files.write(newFilePath, newContent.getBytes(StandardCharsets.UTF_8));

            System.out.println("已生成: " + newFilePath);
        } catch (IOException e) {
            System.err.println("处理文件失败: " + filePath + " - " + e.getMessage());
        }
    }
}