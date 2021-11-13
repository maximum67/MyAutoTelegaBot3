import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;

import static org.apache.commons.io.FileUtils.getFile;

public class MyAutoTelegaBot extends TelegramLongPollingBot {

    private Update update;

    @Override
    public String getBotUsername(){
        return "@matelegabot";
    }
    @Override
    public String getBotToken(){
        BotKey botKeyToken = new BotKey();
        return botKeyToken.getKey();
    }

    @Override
    public void onRegister() {
        super.onRegister();

        try {
            sendDocument(971823758,
                    "Передача файла",
                    getFile("notes3.txt"));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is called when receiving updates via GetUpdates method
     *
     * @param update Update received
     */
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage message = new SendMessage(); // Создайте объект SendMessage с обязательными полями
            message.setChatId(update.getMessage().getChatId().toString());
            message.setText(update.getMessage().getChatId().toString());

            try {
                execute(message); // Способ вызова для отправки сообщения
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        if (update.getMessage().getText().equals("stop bot")){
            SendMessage message = new SendMessage(); // Создайте объект SendMessage с обязательными полями
            message.setChatId(update.getMessage().getChatId().toString());
            message.setText("Ну, пока.");
            try {
                execute(message); // Способ вызова для отправки сообщения
                System.exit(0);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }

        }

    }
    public void sendDocument(long chatId, String caption, File sendFile) throws TelegramApiException {
        SendDocument sendDocument = new SendDocument();
        sendDocument.setChatId(String.valueOf(chatId));
        sendDocument.setCaption(caption);
        sendDocument.setDocument(new InputFile(sendFile));
        execute(sendDocument);

    }

}
