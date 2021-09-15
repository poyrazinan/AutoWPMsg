package tr.com.poyrazinan;

import it.auties.whatsapp4j.whatsapp.WhatsappAPI;
import it.auties.whatsapp4j.whatsapp.WhatsappConfiguration;
import lombok.SneakyThrows;
import tr.com.poyrazinan.model.Task;
import tr.com.poyrazinan.services.ExcelInputReader;
import tr.com.poyrazinan.services.MessageSender;
import tr.com.poyrazinan.utils.MessageTimer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    // Imported excel values which is used in timer.
    public static List<Task> inputs = new ArrayList<>();

    public static void main(String[] args) throws IOException {

        new ExcelInputReader();

        var configuration = WhatsappConfiguration.builder()
                .whatsappUrl("wss://web.whatsapp.com/ws")
                .requestTag("requestTag")
                .description("AutoWPMsg")
                .shortDescription("AutoWPMsg")
                .reconnectWhenDisconnected((reason) -> false)
                .async(true)
                .build();

        var api = new WhatsappAPI(configuration);
        api.registerListener(new WhatsAppListener(api));
        new MessageTimer(api);
        new MessageSender(api);
        api.connect();
        // Do other stuffs on WhatsAppListener#onLoggedIn
        // Because if you do here it wont sync to whatsapp connection.
        // If you want async then execute here
    }
}
