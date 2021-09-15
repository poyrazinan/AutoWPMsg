package tr.com.poyrazinan.utils;

import it.auties.whatsapp4j.whatsapp.WhatsappAPI;
import org.jetbrains.annotations.NotNull;
import tr.com.poyrazinan.Main;
import tr.com.poyrazinan.model.Task;
import tr.com.poyrazinan.services.CacheCreator;

import java.util.ConcurrentModificationException;
import java.util.Timer;
import java.util.TimerTask;

public class MessageTimer {

    private static WhatsappAPI api;

    private static Timer timer = new Timer();

    public MessageTimer(WhatsappAPI apiInstance) {
        api = apiInstance;
    }

    /**
     * Timer starter and body
     *
     */
    public static void startTimer() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    Main.inputs.stream().forEach(MessageTimer::checkDate);
                }
                catch (ConcurrentModificationException e)  {
                    System.out.println
                            ("Zamanlanan bütün işlemler gerçekleştirildiğinden, zamanlayıcı askıya alındı.");
                    stopTimer();
                }
            }
        }, 0, 800);
    }

    public static void stopTimer() {
        timer.cancel();
    }

    /**
     * Date checker with converting unix
     *
     * @param task
     */
    private static void checkDate(@NotNull Task task) {

        long unixNow = System.currentTimeMillis() / 1000L;

        if (task.isExecuted())
            return;

        if (task.getUnix() == unixNow) {
            task.setExecuted(true);
            CacheCreator.awaitTasks.add(task);
            api.connect();
            return;
        }
        else if (task.getUnix() < unixNow) {
            task.setExecuted(true);
            return;
        }

        else return;
    }
}
