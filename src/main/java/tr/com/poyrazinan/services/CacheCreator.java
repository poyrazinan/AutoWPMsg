package tr.com.poyrazinan.services;

import it.auties.whatsapp4j.protobuf.chat.Chat;
import it.auties.whatsapp4j.whatsapp.WhatsappAPI;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutionException;

public class CacheCreator {

    /**
     * Creating cache with sending message to owner.
     * With this method WhatsAppWeb4J thought it's a message event.
     * And caching all the chats to the memory.
     *
     * @param api
     */
    public CacheCreator(@NotNull WhatsappAPI api) {
        try {
            Chat chat = api.queryChat(api.manager().phoneNumberJid()).get().data();
            api.sendMessage(chat, "Bağlantı gerçekleştirildi.");
        }
        catch (ExecutionException | InterruptedException e) {
            System.out.println("Cache verisi oluşturulamadı.");
        }
    }
}
