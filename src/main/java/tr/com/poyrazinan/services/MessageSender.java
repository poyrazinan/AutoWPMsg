package tr.com.poyrazinan.services;

import it.auties.whatsapp4j.protobuf.chat.Chat;
import it.auties.whatsapp4j.whatsapp.WhatsappAPI;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tr.com.poyrazinan.exceptions.NotInMemoryException;
import tr.com.poyrazinan.model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class MessageSender {

    private static WhatsappAPI api;

    public MessageSender(WhatsappAPI api) {
        this.api = api;
    }

    public static void sendMessage(@NotNull Task task) {

        // Will use for sending notification to user via WhatsApp
        Chat notification = getChat(api.manager().phoneNumberJid());

        if (task.isEveryone()) {
            api.manager().chats().stream().forEach(chat -> {
                api.sendMessage(chat, task.getMessage());
                System.out.println("Mesaj başarıyla gönderildi. Kişi: " + chat.displayName());
            });
            api.sendMessage(notification, "Toplu mesaj gönderme işlemi tamamlandı. Mesaj: "
                + task.getMessage());
        }

        else {
            List<String> contactsForNotify = new ArrayList<>();
            task.getNumbers().stream().forEach(number ->{
                Chat chat = getChat(number);

                api.sendMessage(chat, task.getMessage());
                System.out.println("Mesaj başarıyla gönderildi. Kişi:" + chat.displayName() + "(" + number + ")" +
                        " Görev: " + task.getName() +
                        " Mesaj: " + task.getMessage());
                contactsForNotify.add(chat.displayName());
            });
            api.sendMessage(notification, "Mesaj gönderme işlemi tamamlandı. " +
                    "Kişiler: " + contactsForNotify.stream()
                                        .map(Object::toString).collect(Collectors.joining(", "))+
                    " Mesaj: " + task.getMessage());
        }

        CacheCreator.awaitTasks.remove(task);
        api.disconnect();
    }

    /**
     * Getting chat of contact.
     *
     * @param number
     * @return
     */
    private static @Nullable Chat getChat(@NotNull String number) {
        try {
            String contactJID = number + "@s.whatsapp.net";
            if (number.contains("@"))
                contactJID = number;

            try {
                return api.manager().findChatByJid(contactJID)
                        .orElseThrow(() -> new NotInMemoryException("Whatsapp verisi henüz yüklenemedi." +
                                " Sahte whatsapp verisi oluşturuluyor."));
            } catch (NotInMemoryException e) {
                return api.queryChat(contactJID).get().data();
            }
        }
        catch (ExecutionException | InterruptedException ignored) {
            ignored.printStackTrace();
            return null;
        }
    }
}
