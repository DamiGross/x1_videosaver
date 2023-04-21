 package org.example;

import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        TikTokBot bot = new TikTokBot();
        SendVideo sendVideo = new SendVideo();
        InputFile video = new InputFile(new File("video.mp4"));
        sendVideo.setVideo(video);
        sendVideo.setChatId("748059876");
        try {
            bot.execute(sendVideo);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
