package tr.com.poyrazinan.utils;

import it.auties.whatsapp4j.whatsapp.WhatsappAPI;
import org.jetbrains.annotations.NotNull;
import tr.com.poyrazinan.Main;
import tr.com.poyrazinan.objects.ExcelInput;
import tr.com.poyrazinan.services.MessageHandlerService;

import java.util.ConcurrentModificationException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

public class MessageTimer {

    /**
     * Whatsapp API instance
     *
     */
    private static WhatsappAPI api;

    /**
     * Timer const
     *
     */
    private static Timer timer = new Timer();

    /**
     * Constructor which insert
     * Whatsapp API
     *
     * @param apiInstance
     */
    public MessageTimer(WhatsappAPI apiInstance) {
        api = apiInstance;
    }

    /**
     * Timer starter and timer body
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

    /**
     * Timer stopper
     *
     */
    public static void stopTimer() {
        timer.cancel();
    }

    /**
     * Date checker with converting unix
     *
     * @param excelInput
     */
    private static void checkDate(@NotNull ExcelInput excelInput) {
        // Unix of current time
        long unix = System.currentTimeMillis() / 1000L;
        // Unix of task
        long inputUnix = excelInput.getDate().getTime() / 1000L;
        // If current unix and task unix equal and task isn't executed yet
        // it execute the task
        if (inputUnix == unix && !excelInput.isExecuted()) {
            try {
                new MessageHandlerService(api, excelInput);
            }
            catch (ExecutionException | InterruptedException e) {}
            return;
        }
        // If task unix smaller then current unix
        // setting executed task.
        else if (inputUnix < unix && !excelInput.isExecuted()) {
            excelInput.setExecuted(true);
            System.out.println(excelInput.getName() + " Tarihi geçmiş bir işlem olduğından geçildi.");
            return;
        }
        else return;
    }
}
