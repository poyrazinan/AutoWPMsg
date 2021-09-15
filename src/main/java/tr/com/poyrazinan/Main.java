package tr.com.poyrazinan;

import it.auties.whatsapp4j.manager.WhatsappDataManager;
import it.auties.whatsapp4j.whatsapp.WhatsappAPI;
import tr.com.poyrazinan.model.Task;
import tr.com.poyrazinan.services.ExcelInputReader;
import tr.com.poyrazinan.services.MessageSender;
import tr.com.poyrazinan.utils.MessageTimer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static boolean isConnected = false;

    // Imported excel values which is used in timer.
    public static List<Task> inputs = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        new ExcelInputReader();
        var api = new WhatsappAPI();
        api.registerListener(new WhatsAppListener(api));
        new MessageTimer(api);
        new MessageSender(api);
        MessageTimer.startTimer();
        // Do other stuffs on WhatsAppListener#onLoggedIn
        // Because if you do here it wont sync to whatsapp connection.
        // If you want async then execute here
    }
}
