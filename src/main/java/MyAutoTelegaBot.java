import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.util.ArrayList;

import static org.apache.commons.io.FileUtils.getFile;

public class MyAutoTelegaBot extends TelegramLongPollingBot {

    private Update update;
    private long usedChatId;
    public static ArrayList <String> workChats = new ArrayList<String>();
    public static ArrayList <Thread> threadArrayList = new ArrayList<Thread>();

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
        long usedChatId = update.getMessage().getChatId();
        /*if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage message = new SendMessage(); // Создайте объект SendMessage с обязательными полями
            message.setChatId(update.getMessage().getChatId().toString());
            message.setText("Передаем файл");
            try {
                //execute(message); // Способ вызова для отправки сообщения
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }*/
        if (update.getMessage().getText().toLowerCase().equals("start bot")) {
            SendMessage message = new SendMessage();
            message.setChatId(update.getMessage().getChatId().toString());
            message.setText("Я телеграм-бот, я ежедневно буду присылать вам отчёт. " +
                    "Если в этом нет необходимости наберите: stop bot");
            if (!workChats.contains(update.getMessage().getChatId().toString())) {
                workChats.add(update.getMessage().getChatId().toString());
                Thread thread = new MyThread("Thread " + update.getMessage().getChatId().toString(), usedChatId);
                thread.start();
                threadArrayList.add(thread);
                //System.out.println("Запущен поток "+ thread.getId());
            } else {
                message.setText("Я уже работаю, отчет будет доставлен вовремя. " +
                        "Если в этом нет необходимотси наберите: stop bot");
            }
            try {
                execute(message); // Способ вызова для отправки сообщения
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        if (update.getMessage().getText().toLowerCase().equals("stop bot")) {
            SendMessage message = new SendMessage();
            message.setChatId(update.getMessage().getChatId().toString());
            message.setText("Ну, пока. Не надо - значит не надо, я старался((");
            try {
                execute(message); // Способ вызова для отправки сообщения
                //System.exit(0);

            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
            for (Thread th : threadArrayList) {
                //System.out.print("Поток с номер ID "+th.getId()+" под именем "+th.getName()+ " ");
                //System.out.println(th.isAlive()? "Активенs": "Не авктивенs");
                if (th.getName().equals("Thread " + update.getMessage().getChatId().toString())) {
                    threadArrayList.remove(th);
                    // System.out.println("Поток с номер ID "+th.getId()+" под именем "+th.getName()+ " удаляем");
                    if (th.isAlive()) {
                        th.stop();
                    }
                }
            }
        }
        if (update.getMessage().getText().toLowerCase().equals("stop bot stop")) {
            SendMessage message = new SendMessage();
            message.setChatId(update.getMessage().getChatId().toString());
            message.setText("Ну, пока. Не надо - значит не надо, я старался((");
            try {
                execute(message); // Способ вызова для отправки сообщения
                System.exit(0);

            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

        if (update.getMessage().getText().equals("sending a report")) {

            try {
                sendDocument(update.getMessage().getChatId().toString(),
                        "Файл отчета",
                        getFile("notes3.txt"));

            } catch (TelegramApiException e) {
                e.printStackTrace();
            }

            for (Thread th : threadArrayList) {
                System.out.print("Поток с номер ID " + th.getId() + " под именем " + th.getName());
                System.out.println(th.isAlive() ? "Активен" : "Не активен");
                if (!th.isAlive()) {
                    threadArrayList.remove(th);
                }
            }
            Thread thread = new MyThread("Thread " + update.getMessage().getChatId().toString(), usedChatId);
            thread.start();
            threadArrayList.add(thread);
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
