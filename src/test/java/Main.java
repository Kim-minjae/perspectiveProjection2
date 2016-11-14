
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter.ToIplImage;
import static org.bytedeco.javacv.CanvasFrame.getDefaultGamma;


/**
 * Created by 김민재 on 2016-11-03.
 */



public class Main {
    static OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();



    public static void main(String args[]) throws FrameGrabber.Exception, InterruptedException {

       // Camera webcam = new Camera();

      //  opencv_core.CvMemStorage storage = opencv_core.cvCreateMemStorage();

        IplImage grabbedImage;
        FrameGrabber grabber = FrameGrabber.createDefault(0);
        grabber.start();

        int ancho = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
        int alto = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
        CanvasFrame frame;
        frame = new CanvasFrame("originalFrame", getDefaultGamma() / grabber.getGamma());

        while (frame.isVisible() && (grabbedImage = converter.convert(grabber.grab())) != null) {

            Frame rotatedFrame = converter.convert(grabbedImage);
            frame.setCanvasSize(ancho*1920/3440, alto*1080/1440);
            frame.waitKey(30);

            frame.showImage(rotatedFrame);

        }
    }
}
