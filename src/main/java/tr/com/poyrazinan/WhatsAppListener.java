package tr.com.poyrazinan;

import it.auties.whatsapp4j.listener.WhatsappListener;

import it.auties.whatsapp4j.response.impl.json.UserInformationResponse;
import it.auties.whatsapp4j.whatsapp.WhatsappAPI;
import lombok.NonNull;
import tr.com.poyrazinan.services.CacheCreatorService;
import tr.com.poyrazinan.utils.MessageTimer;

public class WhatsAppListener implements WhatsappListener {

    // WhatsappAPI instance calls from constructor
    WhatsappAPI api;

    public WhatsAppListener(WhatsappAPI api) {
        this.api = api;
    }

    /**
     * Socket connection success event.
     *
     * @param response
     */
    @Override
    public void onLoggedIn(@NonNull UserInformationResponse response) {
        System.out.println("Whatsapp socket connected.");
        Main.isConnected = true;
        new CacheCreatorService(api);
        MessageTimer.startTimer();
    }

    /**
     * Socket connection disconnect event.
     *
     */
    @Override
    public void onDisconnected() {
        System.out.println("Whatsapp socket disconnected.");
        Main.isConnected = false;
        MessageTimer.stopTimer();
    }

}
