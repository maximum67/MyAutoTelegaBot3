package autotelega;

import autotelega.MyAutoTelegaBot;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Date;


public class MyThread extends Thread {

    private long usedChatId;

    MyThread(String name,long usedChatId) {
        super(name);
        this.usedChatId = usedChatId;
    }
    MyThread(){
    }

    public void setUsedChatId(long usedChatId) {
        this.usedChatId = usedChatId;
    }


    public void stopThread() {
        this.interrupt();
    }

    public void run(){
        while (!Thread.currentThread().isInterrupted()) {
            //System.out.printf("%s started... \n", Thread.currentThread().getName());
            //System.out.printf("%s started... \n", Thread.currentThread().getId());
            long timesleep = 100000;
            long timestart = 32700000; //09:05
            Date date = new Date();
            //System.out.println(date.getTime()%86400000);
            if (date.getTime() % 86400000 + 10800000 < timestart) {
                timesleep = timestart - (date.getTime() % 86400000 + 10800000);
            } else {
                timesleep = 86400000 - (date.getTime() % 86400000 + 10800000) + timestart;
            }
            //System.out.println(timesleep);
            //System.out.println((date.getTime()));

            try {
                Thread.sleep(15000);
            } catch (InterruptedException e) {
                System.out.println("Thread has been interrupted");
                this.interrupt();
                return;
            }
            //System.out.printf("%s finished... \n", Thread.currentThread().getName());
            //System.out.printf("%s finished... \n", Thread.currentThread().getId());
            Update document = new Update();
            Message text = new Message();
            Chat chat = new Chat();
            chat.setId(usedChatId);
            text.setText("sending a report");
            text.setChat(chat);
            document.setMessage(text);
            MyAutoTelegaBot documentBot = new MyAutoTelegaBot();
            documentBot.onUpdateReceived(document);
        }
    }
}



