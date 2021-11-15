import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.apache.commons.io.FileUtils.getFile;

public class MyAutoTelegaBot extends TelegramLongPollingBot {

    private Update update;
    private long usedChatId;
    public static ArrayList <String> workChats = new ArrayList<>();

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
    }

    /**
     * This method is called when receiving updates via GetUpdates method
     *
     * @param update Update received
     */
    @Override
    public void onUpdateReceived(Update update) {
         usedChatId = update.getMessage().getChatId();
         /*if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage message = new SendMessage(); // Создайте объект SendMessage с обязательными полями
            message.setChatId(update.getMessage().getChatId().toString());
            message.setText("Передаем файл");
            try {
                execute(message); // Отправляем сообщение
            } catch (TelegramApiException e) {e.printStackTrace();}
        }*/
        if (update.getMessage().getText().equalsIgnoreCase("start bot")) {
            SendMessage message = new SendMessage();
            message.setChatId(update.getMessage().getChatId().toString());
            message.setText("Я телеграм-бот, я ежедневно буду присылать вам отчёт. " +
                    "Если в этом нет необходимости наберите: stop bot");
            //if (!workChats.contains(update.getMessage().getChatId().toString()))
            if (workChats.size()==0) {
                workChats.add(update.getMessage().getChatId().toString());
                Thread thread = new MyThread("Thread " + update.getMessage().getChatId().toString(), usedChatId);
                thread.start();
            } else {
                message.setText("Я уже работаю.");
            }
            try {
                execute(message); // Отправляем сообщение
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        if (update.getMessage().getText().equalsIgnoreCase("stop bot")) {
            if (workChats.contains(update.getMessage().getChatId().toString())) {
                SendMessage message = new SendMessage();
                message.setChatId(update.getMessage().getChatId().toString());
                message.setText("Ну, пока. Я отключаюсь.");
                try {
                    execute(message); // Отправляем сообщение
                    System.exit(0);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
        if (update.getMessage().getText().equals("sending a report")) {
            PathDocument pathDocument = new PathDocument();
            LocalDate date = LocalDate.now().minusDays(1);
            int dayOfMonth = date.getDayOfMonth();
            int year = date.getYear();
            int month = date.getMonthValue();
            String dataFormat = String.format("Отчет за %d.%d.%d \n", dayOfMonth, month, year);
            try {
                sendDocument(update.getMessage().getChatId().toString(),
                        dataFormat,
                        getFile(pathDocument.getPathDocument()));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
            Thread thread = new MyThread("Thread " + update.getMessage().getChatId().toString(), usedChatId);
            thread.start();
        }
    }

    public void sendDocument(String chatId, String caption, File sendFile) throws TelegramApiException {
        SendDocument sendDocument = new SendDocument();
        sendDocument.setChatId(chatId);
        sendDocument.setCaption(caption);
        sendDocument.setDocument(new InputFile(sendFile));
        execute(sendDocument);

    }
}
