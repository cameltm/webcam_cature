/**
 * 
 */
package webcam;

import java.awt.Dimension;

/**
 * @author camel
 *
 */

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamDevice;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.github.sarxos.webcam.util.ImageUtils;

public class WebCamExample {

	public static void main(String[] args) throws IOException, Exception {

		List<WebcamDevice> devices =  Webcam.getDriver().getDevices();
		System.out.println("List all devices:");
		for(WebcamDevice dev : devices) {
            System.out.println("\t" + dev.getName() + ", resolution " + dev.getResolution().height + " x " + dev.getResolution().height );
        }
		System.out.println("(end)");

		Dimension[] nonStandardResolutions = new Dimension[] { WebcamResolution.PAL.getSize(),
				WebcamResolution.HD.getSize(), new Dimension(2000, 1000), new Dimension(1000, 500), };

		Webcam webcam = Webcam.getWebcamByName("Logitech HD Webcam C270 1");// getDefault();
		if (webcam != null) {
			System.out.println("Webcam: " + webcam.getName());
		} else {
			System.out.println("No webcam detected");
		}
		webcam.setCustomViewSizes(nonStandardResolutions);
		webcam.setViewSize(WebcamResolution.HD.getSize());
		webcam.open();

		BufferedImage image = webcam.getImage();
		System.out.println(image.getWidth() + "x" + image.getHeight());

		ImageIO.write(image, ImageUtils.FORMAT_JPG, new File("selfie.jpg"));

		System.out.println("List devices:");
		for (Webcam webcamx : Webcam.getWebcams()) {
			System.out.println("\t" + webcamx.getName() +
					", FPS: "	+ webcamx.getFPS() +
					", isOpen: " + webcamx.isOpen()
					+ ", view size: " + webcamx.getViewSize()
					);
		}

		
		System.out.println("Done.");
	}

	public void captureWithPanel() {
		Webcam webcam = Webcam.getDefault();
		webcam.setViewSize(WebcamResolution.VGA.getSize());

		WebcamPanel panel = new WebcamPanel(webcam);
		panel.setImageSizeDisplayed(true);

		JFrame window = new JFrame("Webcam");
		window.add(panel);
		window.setResizable(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.pack();
		window.setVisible(true);
	}

}
