package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;

public class TikTokBot extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {
        String message = update.getMessage().getText();
        if (message.startsWith("https://www.tiktok.com/")) {
            try {
                String videoUrl = extractVideoUrl(message);
                downloadVideo(videoUrl);
                sendVideo(update);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private String extractVideoUrl(String url) throws Exception {
        Document document = Jsoup.connect(url).get();
        Elements elements = document.getElementsByAttributeValue("property", "og:video");
        if (elements.isEmpty()) {
            throw new Exception("Video not found");
        }
        Element element = elements.get(0);
        return element.attr("content");
    }
    private void downloadVideo(String url) throws Exception {
        URL videoUrl = new URL(url);
        BufferedInputStream in = new BufferedInputStream(videoUrl.openStream());
        FileOutputStream fileOutputStream = new FileOutputStream("video.mp4");
        byte[] dataBuffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
            fileOutputStream.write(dataBuffer, 0, bytesRead);
        }
        in.close();
        fileOutputStream.close();
    }

    private void sendVideo(Update update) throws Exception {
        SendVideo sendVideo = new SendVideo();
        sendVideo.setChatId(update.getMessage().getChatId());
        InputFile inputFile = new InputFile(new File("video.mp4"));
        sendVideo.setVideo(inputFile);
        execute(sendVideo);
    }

    @Override
    public String getBotUsername() {
        return "x1_videosaverbot";
    }

    @Override
    public String getBotToken() {
        return System.getenv("6049240601:AAHZHBzopOvZ1BvchpaXMu9gw7AYZ8tWfhI");
    }
}
