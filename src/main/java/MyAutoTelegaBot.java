import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.util.*;


public class MyAutoTelegaBot extends TelegramLongPollingBot {

    @Override
    public String getBotUsername() {
        return "MyAutoTelegaBot";
    }

    @Override
    public String getBotToken() {
        BotKey botKeyToken = new BotKey();
        return botKeyToken.getKey();
    }

    @Override
    public void onUpdateReceived(Update update) {


        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage message = new SendMessage(); // Создайте объект SendMessage с обязательными полями
            message.setChatId(update.getMessage().getChatId().toString());
            try {
                message.setText(logicBots.BotResponse(update.getMessage().getText()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            setKeybord(message);
            try {
                execute(message); // Способ вызова для отправки сообщения
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
    public void setKeybord(SendMessage sendMessage) {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setOneTimeKeyboard(false);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setSelective(true);
        ArrayList<KeyboardRow> keybord = new ArrayList<>();
        KeyboardRow kbr1 = new KeyboardRow();
        KeyboardRow kbr2 = new KeyboardRow();
        KeyboardRow kbr3 = new KeyboardRow();
        kbr1.add(new KeyboardButton("Привет")) ;
        kbr2.add(new KeyboardButton("Работаете"));
        kbr3.add(new KeyboardButton("Когда"));
        keybord.add(kbr1);
        keybord.add(kbr2);
        keybord.add(kbr3);
        keyboardMarkup.setKeyboard(keybord);
        sendMessage.setReplyMarkup(keyboardMarkup);
    }

}
