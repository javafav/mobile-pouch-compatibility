package com.sastaybrands.mobiles.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.*;
import java.util.Random;

public class GSMArenaImageDownloaderDB {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/mobile_pouch_db";
    private static final String DB_USER = "devuser";
    private static final String DB_PASSWORD = "Format#1";
    private static final String SAVE_DIRECTORY = "images/";
    private static final String FAILED_DOWNLOADS_FILE = "failed_downloads.txt";

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
             PreparedStatement statement = connection.prepareStatement("SELECT id, name FROM mobiles WHERE id >= 260");
             ResultSet resultSet = statement.executeQuery()) {

            Random random = new Random();

            while (resultSet.next()) {
                int mobileId = resultSet.getInt("id");
                String mobileName = resultSet.getString("name");

                try {
                    boolean success = downloadImage(mobileId, mobileName, SAVE_DIRECTORY);
                    if (!success) {
                        logFailedDownload(mobileId, mobileName);
                    }
                    int delay = random.nextInt(1000) + 2000;
                    Thread.sleep(delay);
                } catch (IOException | InterruptedException e) {
                    System.err.println("Error downloading image for " + mobileName + ": " + e.getMessage());
                    logFailedDownload(mobileId, mobileName);
                }
            }
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }

    public static boolean downloadImage(int mobileId, String mobileName, String saveDirectory) throws IOException, InterruptedException {
        Random random = new Random();
        String encodedMobileName = URLEncoder.encode(mobileName, "UTF-8");
        String searchUrl = "https://www.gsmarena.com/results.php3?sQuickSearch=yes&sName=" + encodedMobileName;
        int retryCount = 0;
        int delay = 5000;

        while (retryCount < 5) {
            try {
                String userAgent = USER_AGENTS[random.nextInt(USER_AGENTS.length)];
                Document doc = Jsoup.connect(searchUrl)
                        .userAgent(userAgent)
                        .timeout(15000)
                        .get();

                Elements phoneLinks = doc.select(".makers li a");
                if (phoneLinks.isEmpty()) {
                    System.err.println("No results found for " + mobileName);
                    return false;
                }

                String phoneUrl = "https://www.gsmarena.com/" + phoneLinks.first().attr("href");
                Document phoneDoc = Jsoup.connect(phoneUrl)
                        .userAgent(userAgent)
                        .timeout(25000)
                        .get();

                Element imageElement = phoneDoc.select(".specs-photo-main a img").first();
                if (imageElement == null) {
                    System.err.println("Image not found for " + mobileName);
                    return false;
                }

                String imageUrl = imageElement.attr("src");
                String imageName = "mobile_" + mobileId + ".jpg";
                URL url = new URL(imageUrl);
                BufferedImage image = ImageIO.read(url);

                File directory = new File(saveDirectory);
                if (!directory.exists()) {
                    directory.mkdirs();
                }

                File outputFile = new File(saveDirectory + imageName);
                ImageIO.write(image, "jpg", outputFile);

                System.out.println("Downloaded image for: " + mobileName);
                return true;
            } catch (IOException e) {
                if (e.getMessage().contains("429") || e.getMessage().contains("Too Many Requests")) {
                    System.out.println("Rate limited, retrying after " + (delay / 1000) + " seconds...");
                    Thread.sleep(delay);
                    delay *= 2;
                    retryCount++;
                } else {
                    throw e;
                }
            }
        }
        System.err.println("Failed to download image after multiple retries: " + mobileName);
        return false;
    }

    private static void logFailedDownload(int mobileId, String mobileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FAILED_DOWNLOADS_FILE, true))) {
            writer.write("Mobile ID: " + mobileId + " | Name: " + mobileName);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing to failed downloads log: " + e.getMessage());
        }
    }
}
