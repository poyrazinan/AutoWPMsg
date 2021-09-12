package tr.com.poyrazinan.services;

import it.auties.whatsapp4j.protobuf.chat.Chat;
import it.auties.whatsapp4j.whatsapp.WhatsappAPI;

import java.util.concurrent.ExecutionException;

public class CacheCreatorService {

    public CacheCreatorService(WhatsappAPI api) {
        try {
            Chat chat = api.queryChat(api.manager().phoneNumberJid()).get().data();
            api.sendMessage(chat, "Modüller aktif edildi. Veriler güncellendi.");
        }
        catch (ExecutionException | InterruptedException e) {
            System.out.println("Cache verisi oluşturulamadı.");
        }

    }
}
