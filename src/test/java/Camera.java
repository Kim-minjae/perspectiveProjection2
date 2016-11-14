import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameConverter;

/**
 * Created by 김민재 on 2016-11-03.
 */
public class Camera {

    FrameGrabber grabber;
    static OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();

    public void Camera() throws FrameGrabber.Exception {

            grabber = FrameGrabber.createDefault(0);



    }

    public void start() throws FrameGrabber.Exception {

            grabber.start();

    }



}
