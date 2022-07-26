package com.amurtigerstudio.boot;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Data
@Component
public class Bot extends TelegramLongPollingBot {

    private String token = "5460267849:AAFHae3kkrJj61aA16N9mcWESYTmpWvdEGM";
    private String username = "Geor2022Bot";

    /**
     * Отправляет сообщение от клиента боту
     *
     * @param message
     * @param text
     */
    public void sendMsg(Message message, String text) {

        SendMessage toSend = new SendMessage();
        toSend.enableMarkdown(false);
        toSend.setChatId(message.getChatId().toString());
        toSend.setReplyToMessageId(message.getMessageId());
        toSend.setText(text);
        try {
            execute(toSend);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }


    @Override
    public void onUpdateReceived(Update update) {
        Model model = new Model();
        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            switch (message.getText()) {
                case "/help":
                    sendMsg(message, "Чем я могу помочь?");
                    break;
                case "/setting":
                    sendMsg(message, "Что будем настраивать?");
                    break;
                default:
                    try {
                        sendMsg(message, Weather.getWeat(message.getText(), model));
                    } catch (Exception e) {
                        e.printStackTrace();
                        sendMsg(message, "Такого города не существует! Попробуйте еще раз!");
                    }
            }
        }
    }

    public void setButton(SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.getResizeKeyboard();
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow keyboardFerstRow = new KeyboardRow();
        keyboardFerstRow.add(new KeyboardButton("/help"));
        keyboardFerstRow.add(new KeyboardButton("/setting"));
        keyboardRowList.add(keyboardFerstRow);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
    }
}

