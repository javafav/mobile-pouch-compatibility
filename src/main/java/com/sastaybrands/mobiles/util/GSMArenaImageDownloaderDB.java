package com.sastaybrands.mobiles.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.*;
import java.util.Random;

public class GSMArenaImageDownloaderDB {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/mobile_pouch_db";
    private static final String DB_USER = "devuser";
    private static final String DB_PASSWORD = "Format#1";
    private static final String SAVE_DIRECTORY = "images/";

    // User-Agent list to avoid detection
    private static final String[] USER_AGENTS = {
    		"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Safari/537.36",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 13_2_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36",
            "Mozilla/5.0 (X11; Ubuntu; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.5414.74 Safari/537.36",
            "Mozilla/5.0 (Windows NT 11.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Firefox/110.0",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 12_6) AppleWebKit/537.36 (KHTML, like Gecko) Safari/616.1.21",
            "Mozilla/5.0 (Linux; Android 12; SM-G998B) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.5481.77 Mobile Safari/537.36",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Edge/108.0.1462.54",
            "Mozilla/5.0 (iPhone; CPU iPhone OS 16_0 like Mac OS X) AppleWebKit/537.36 (KHTML, like Gecko) Version/16.0 Mobile/15E148 Safari/537.36",
            "Mozilla/5.0 (iPad; CPU OS 15_5 like Mac OS X) AppleWebKit/537.36 (KHTML, like Gecko) CriOS/110.0.5481.77 Mobile/15E148 Safari/537.36",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:109.0) Gecko/20100101 Firefox/109.0"
    };

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement("SELECT name FROM mobiles WHERE id <= 50 ORDER BY id DESC");
             ResultSet resultSet = statement.executeQuery()) {

            Random random = new Random();

            while (resultSet.next()) {
                String mobileName = resultSet.getString("name");

                try {
                    downloadImage(mobileName, SAVE_DIRECTORY);
                    int delay = random.nextInt(1000) + 20000; // Wait 10-15 seconds
                    Thread.sleep(delay);
                } catch (IOException | InterruptedException e) {
                    System.err.println("Error downloading image for " + mobileName + ": " + e.getMessage());
                }
            }
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }

    public static void downloadImage(String mobileName, String saveDirectory) throws IOException, InterruptedException {
        Random random = new Random();
        String encodedMobileName = URLEncoder.encode(mobileName, "UTF-8");
        String searchUrl = "https://www.gsmarena.com/results.php3?sQuickSearch=yes&sName=" + encodedMobileName;
        int retryCount = 0;
        int delay = 5000;

        while (retryCount < 5) { // Maximum retries
            try {
                // Select a random User-Agent
                String userAgent = USER_AGENTS[random.nextInt(USER_AGENTS.length)];

                Document doc = Jsoup.connect(searchUrl)
                        .userAgent(userAgent)
                        .timeout(15000) // 15 seconds timeout
                        .get();

                Elements phoneLinks = doc.select(".makers li a");
                if (phoneLinks.isEmpty()) {
                    System.err.println("No results found for " + mobileName);
                    return;
                }

                String phoneUrl = "https://www.gsmarena.com/" + phoneLinks.first().attr("href");
                Document phoneDoc = Jsoup.connect(phoneUrl)
                        .userAgent(userAgent)
                        .timeout(25000)
                        .get();

                Element imageElement = phoneDoc.select(".specs-photo-main a img").first();

                if (imageElement == null) {
                    System.err.println("Image not found for " + mobileName);
                    return;
                }

                String imageUrl = imageElement.attr("src");
                String imageName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
                URL url = new URL(imageUrl);
                BufferedImage image = ImageIO.read(url);

                File directory = new File(saveDirectory);
                if (!directory.exists()) {
                    directory.mkdirs();
                }

                File outputFile = new File(saveDirectory + imageName);
                ImageIO.write(image, "jpg", outputFile);

                System.out.println("Downloaded image for: " + mobileName);
                return;
            } catch (IOException e) {
                if (e.getMessage().contains("429") || e.getMessage().contains("Too Many Requests")) {
                    System.out.println("Rate limited, retrying after " + (delay / 1000) + " seconds...");
                    Thread.sleep(delay);
                    delay *= 2; // Exponential backoff (double the wait time)
                    retryCount++;
                } else {
                    throw e; // Rethrow other IOExceptions
                }
            }
        }
        System.err.println("Failed to download image after multiple retries: " + mobileName);
    }
}
