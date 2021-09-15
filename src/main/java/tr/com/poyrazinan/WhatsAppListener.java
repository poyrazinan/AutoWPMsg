package tr.com.poyrazinan;

import com.google.zxing.common.BitMatrix;
import it.auties.whatsapp4j.listener.WhatsappListener;

import it.auties.whatsapp4j.response.impl.json.UserInformationResponse;
import it.auties.whatsapp4j.whatsapp.WhatsappAPI;
import lombok.NonNull;
import lombok.SneakyThrows;
import tr.com.poyrazinan.services.CacheCreator;
import tr.com.poyrazinan.utils.MessageTimer;
import tr.com.poyrazinan.utils.QRAuthorize;

import javax.swing.*;
import java.awt.*;

public class WhatsAppListener implements WhatsappListener {

    WhatsappAPI api;
    private JFrame QRCodeFrame = new JFrame();

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
        if (QRCodeFrame.isEnabled())
            QRCodeFrame.dispose();

        new CacheCreator(api);
        MessageTimer.startTimer();

        System.out.println("Whatsapp bağlantısı yapıldı.");
    }

    /**
     * Socket connection disconnect event.
     *
     */
    @Override
    public void onDisconnected() {
        System.out.println("Whatsapp bağlantısı kesildi.");
    }

    /**
     * This method for only initial run. It create QRCode on screen.
     *
     * @param matrix
     */
    @SneakyThrows
    @Override
    public void onQRCode(@NonNull BitMatrix matrix) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        // Removing frame if already opened.
        // Execute only when updating QR
        if (QRCodeFrame.isEnabled())
            QRCodeFrame.dispose();

        QRCodeFrame.setUndecorated(true);

        // Fetching QRCode and scaling it
        ImageIcon image = new ImageIcon(
                QRAuthorize.generateQRCodeImage(matrix)
                        .getScaledInstance(256, 256,  java.awt.Image.SCALE_SMOOTH));

        JLabel lbl = new JLabel(image);
        QRCodeFrame.getContentPane().add(lbl);
        // Sets the frame size to match with QRCode
        QRCodeFrame.setSize(256, 256);

        int x = (screenSize.width - QRCodeFrame.getSize().width)/2;
        int y = (screenSize.height - QRCodeFrame.getSize().height)/2;

        QRCodeFrame.setLocation(x, y);
        QRCodeFrame.setVisible(true);
    }
}