package com.mobilematching.admin.util;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class AdobeStockImageDownloader {

    private static final String SEARCH_TERM = "mobile case"; // Search query
    private static final int PAGE_NUMBER = 5; // Page number to fetch
    private static final int IMAGE_LIMIT = 200; // Number of images to download
    private static final String SAVE_DIR = "downloaded_pouches/"; // Folder to save images

    public static void main(String[] args) {
        createDirectory(SAVE_DIR);
        String searchUrl = "https://stock.adobe.com/search?k=" + SEARCH_TERM + "&page=" + PAGE_NUMBER;

        try {
            List<String> imageUrls = scrapeImageUrls(searchUrl, IMAGE_LIMIT);
            downloadImages(imageUrls);
        } catch (IOException e) {
            System.err.println("Error fetching images: " + e.getMessage());
        }
    }

    private static List<String> scrapeImageUrls(String url, int limit) throws IOException {
        List<String> imageUrls = new ArrayList<>();
        Document doc = Jsoup.connect(url).get();

        Elements imgElements = doc.select("img[src]"); // Adobe Stock image selector

        for (Element img : imgElements) {
            if (imageUrls.size() >= limit) break;
            String imgSrc = img.absUrl("src");

            if (!imgSrc.isEmpty()) {
                imageUrls.add(imgSrc);
            }
        }
        return imageUrls;
    }

    private static void downloadImages(List<String> imageUrls) {
        int count = 114; // Start naming from Pouch40

        for (String imageUrl : imageUrls) {
            String fileName = SAVE_DIR + "Pouch" + count;
            if (saveImageProperly(imageUrl, fileName)) {
                count++; // Only increase count if an image is successfully downloaded
            }
        }

        System.out.println("Download completed! Total valid images: " + (count - 40));
    }

    private static void createDirectory(String directory) {
        try {
            Files.createDirectories(Paths.get(directory));
        } catch (IOException e) {
            System.err.println("Failed to create directory: " + e.getMessage());
        }
    }

    private static boolean saveImageProperly(String imageUrl, String fileName) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            connection.connect();

            String contentType = connection.getContentType();
            String extension = getExtensionFromContentType(contentType);
            
            // Skip if the image is not JPG or PNG
            if (extension == null || (!extension.equals("jpg") && !extension.equals("png"))) {
                System.out.println("Skipped (unsupported format): " + imageUrl);
                return false;
            }

            BufferedImage image = ImageIO.read(connection.getInputStream());
            if (image != null) {
                ImageIO.write(image, extension, new File(fileName + "." + extension));
                System.out.println("Saved: " + fileName + "." + extension);
                return true;
            } else {
                System.err.println("Failed to decode image: " + imageUrl);
                return false;
            }
        } catch (IOException e) {
            System.err.println("Failed to download: " + imageUrl + " - " + e.getMessage());
            return false;
        }
    }

    private static String getExtensionFromContentType(String contentType) {
        if (contentType == null) return null;
        if (contentType.contains("jpeg")) return "jpg";
        if (contentType.contains("png")) return "png";
        return null; // Skip unsupported formats
    }
}
