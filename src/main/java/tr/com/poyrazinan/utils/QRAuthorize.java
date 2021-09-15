package tr.com.poyrazinan.utils;

import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import java.awt.*;

public class QRAuthorize {

    /**
     * Generate QRCode in image format.
     *
     * @param matrix
     */
    public static Image generateQRCodeImage(BitMatrix matrix) {
        return MatrixToImageWriter.toBufferedImage(matrix);
    }
}
