package tr.com.poyrazinan;

import it.auties.whatsapp4j.manager.WhatsappDataManager;
import it.auties.whatsapp4j.whatsapp.WhatsappAPI;
import tr.com.poyrazinan.objects.ExcelInput;
import tr.com.poyrazinan.services.ExcelInputReaderService;
import tr.com.poyrazinan.utils.MessageTimer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    // Connection is valid or expired boolean.
    public static boolean isConnected = false;

    // Imported excel values which is used in timer.
    public static List<ExcelInput> inputs = new ArrayList<>();

    // WhatsApp Data Manager instance.
    public static WhatsappDataManager manager;

    /**
     * Main stuffs lies here.
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        new ExcelInputReaderService();
        var api = new WhatsappAPI();
        manager = api.manager();
        api.registerListener(new WhatsAppListener(api));
        api.connect();
        new MessageTimer(api);
        // Do other stuffs on WhatsAppListener#onLoggedIn
        // Because if you do here it wont sync to whatsapp connection.
        // If you want async then execute here
    }
}
