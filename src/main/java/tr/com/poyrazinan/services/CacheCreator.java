package tr.com.poyrazinan.services;

import it.auties.whatsapp4j.protobuf.chat.Chat;
import it.auties.whatsapp4j.whatsapp.WhatsappAPI;
import org.jetbrains.annotations.NotNull;
import tr.com.poyrazinan.model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CacheCreator {

    // Tasks which isn't executed yet and waiting for connection.
    public static List<Task> awaitTasks = new ArrayList<>();

    public CacheCreator(@NotNull WhatsappAPI api) {
        try {
            Chat chat = api.queryChat(api.manager().phoneNumberJid()).get().data();
            api.sendMessage(chat, "Sohbet modülleri yükleniyor.");
        }
        catch (ExecutionException | InterruptedException e) {
            System.out.println("Cache verisi oluşturulamadı.");
        }

        if (!awaitTasks.isEmpty())
            new ArrayList<>(awaitTasks).forEach(MessageSender::sendMessage);

    }
}
