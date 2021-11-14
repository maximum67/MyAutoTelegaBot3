import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public class MyThread extends Thread {

    private long usedChatId;

    MyThread(String name,long usedChatId) {
        super(name);
        this.usedChatId = usedChatId;
    }
    public void run(){

        System.out.printf("%s started... \n", Thread.currentThread().getName());
        try{
            Thread.sleep(30000);
        }
        catch(InterruptedException e){
            System.out.println("Thread has been interrupted");
        }
        System.out.printf("%s finished... \n", Thread.currentThread().getName());
        System.out.printf("%s finished... \n", Thread.currentThread().getId());

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



