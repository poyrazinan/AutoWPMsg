package tr.com.poyrazinan.services;

import it.auties.whatsapp4j.protobuf.chat.Chat;
import it.auties.whatsapp4j.whatsapp.WhatsappAPI;
import tr.com.poyrazinan.Main;
import tr.com.poyrazinan.exceptions.NotInMemoryException;
import tr.com.poyrazinan.objects.ExcelInput;

import java.util.concurrent.ExecutionException;

public class MessageHandlerService {

    /**
     * Send message to cached person
     * if the person isn't exist in cache
     * (it usually because of started application just now)
     * it create a fake cache for success.
     *
     * @param api
     * @param value
     */
    public MessageHandlerService(WhatsappAPI api, ExcelInput value) throws ExecutionException, InterruptedException {

        // Check if whatsapp connection valid.
        if (!Main.isConnected)
            return;

        // Check cache values is inserted else inserts cache
        if (api.manager().chats() == null || api.manager().chats().isEmpty())
            new CacheCreatorService(api);

        // If value tagged everyone it send message to everyone on whatsapp contacts
        if (value.isEveryone()) {
            // Streams the chats
            api.manager().chats().stream().forEach(chat -> {
                api.sendMessage(chat, value.getMessage());
                System.out.println("Mesaj başarıyla gönderildi. Kişi: " + chat.displayName());
            });
        }

        else {

            Chat chat;
            try {
                // Trying to search chat by contacts phone number (Whatsapp JID)
                chat = api.manager().findChatByJid(value.getNumber() + "@s.whatsapp.net")
                        .orElseThrow(()->
                                        new NotInMemoryException("Chat isn't cached yet, trying to insert fake cache.")
                                // Custom NotInMemoryException for catch it when doesn't exist in cache list.
                        );
            }
            catch (NotInMemoryException e1) {
                // Execute if neither cache nor contacts has the jid
                chat = api.queryChat(value.getNumber() + "@s.whatsapp.net").get().data();
            }

            // Send message to person.
            api.sendMessage(chat, value.getMessage());
            System.out.println("Mesaj başarıyla gönderildi. Numara: " + value.getNumber() +
                    " Görev: " + value.getName() +
                    " Mesaj: " + value.getMessage());

        }

        // Set task executed tag to executed
        value.setExecuted(true);

    }
}
