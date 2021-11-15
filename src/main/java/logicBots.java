import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class logicBots {

    public static String BotResponse(String text) throws InterruptedException {

        /*HashMap<String, String> questions = new HashMap<>();
        questions.put("привет", "hello");
        questions.put("зравствуй", "hello");
        questions.put("добрый день", "hello");
        questions.put("добрый", "hello");
        questions.put("сколько стоит", "стоимость");
        questions.put("сколько будет стоит", "стоимость");
        questions.put("делаете", "услуга");
        questions.put("выполняете", "услуга");
        questions.put("когда", "срок");
        questions.put("на какое время", "срок");
        questions.put("работаете", "время");

        HashMap<String, String> answer = new HashMap<>();
        answer.put("hello", "Здравствуйте");
        answer.put("cтоимость", "Для консультации по стоимости позвоните по тел.701-301");
        answer.put("услуга", "Оставьте номер телефона, с Вами свяжется менеджер и проконсультирует");
        answer.put("срок", "Записаться можно по тел. 701-301");
        answer.put("время", "Мы работет с 9:00 до 21:00 без выходных");


        String[] words = text.toLowerCase().split(" ");
        for (String wr : words) {
            if (questions.containsKey(wr)) {
                return answer.get(questions.get(wr));
            }

        }
        */
        Calendar calendar = new GregorianCalendar();
        Date date = calendar.getTime();
        Thread.sleep(TimeUnit.SECONDS.toMillis(10));
        return "Затрудняюсь ответить, оставьте номер телефона "+ date;
    }

}
