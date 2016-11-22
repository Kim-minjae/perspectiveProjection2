/**
 * Created by pose2 on 11/13/2016.
 */
import static org.bytedeco.javacpp.helper.opencv_core.CV_RGB;
import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;
import  org.bytedeco.javacpp.Pointer;


import org.bytedeco.javacpp.opencv_core.CvPoint;
import org.bytedeco.javacpp.opencv_core.CvSeq;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameConverter;

import java.util.ArrayList;

public class Main {
    static OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();

    public static void main(String[] args) throws Exception
    {
        FrameGrabber grabber = FrameGrabber.createDefault(0);
        grabber.start();

        IplImage grabbedImage= converter.convert(grabber.grab());

        ArrayList<Integer> maxLine = new ArrayList<Integer>();
        int startpoint = 0;
        int i =0;


        CanvasFrame frame = new CanvasFrame("My Face", CanvasFrame.getDefaultGamma()/grabber.getGamma());
        BorderLine border = new BorderLine(0,0,startpoint,grabbedImage);

        while (frame.isVisible() && (grabbedImage = converter.convert(grabber.grab())) != null) {


            IplImage gray = cvCreateImage(cvGetSize(grabbedImage), 8, 1);

            //cvCanny(gray, gray, 50, 100, 3);
            //cvCvtColor(gray,grabbedImage,CV_GRAY2BGR);
            border.moveAndDraw(grabbedImage,grabbedImage,i);
            //grabbedImage = redDetector(grabbedImage);
            Frame rotatedFrame = converter.convert(grabbedImage);


            //border moving factor
            if(i > grabbedImage.width()){
                i=0;
            }
            else
            {
                i++;
            }
            //


            frame.waitKey(30);

            frame.showImage(rotatedFrame);


        }
        frame.dispose();

        grabber.stop();
    }


    public static void FindAndDrawLines(IplImage src, CvSeq lines ,IplImage dst, IplImage colorDst)
    {

        CvMemStorage storage = cvCreateMemStorage(0);

        if (src == null) {
            System.out.println("Couldn't load source image.");
            return;
        }
        cvCvtColor(src, src, CV_BGR2GRAY);
        cvCanny(src, dst, 50, 100, 3);
        cvCvtColor(dst, colorDst, CV_GRAY2BGR);

        lines = cvHoughLines2(dst, storage, CV_HOUGH_PROBABILISTIC, 1, Math.PI / 180, 40, 50, 10, 0, CV_PI);

        for (int i = 0; i < lines.total(); i++) {
            // Based on JavaCPP, the equivalent of the C code:
            // CvPoint* line = (CvPoint*)cvGetSeqElem(lines,i);
            // CvPoint first=line[0], second=line[1]
            // is:
            Pointer line = cvGetSeqElem(lines, i);
            CvPoint pt1  = new CvPoint(line).position(0);
            CvPoint pt2  = new CvPoint(line).position(1);

            cvLine(colorDst, pt1, pt2, CV_RGB(255, 0, 0), 3, CV_AA, 0); // draw the segment on the image
        }
    }

    public static IplImage redDetector(IplImage src)
    {


        //color range of red like color
        CvScalar min = cvScalar(0, 0, 130, 0);//BGR-A
        CvScalar max= cvScalar(140, 110, 255, 0);//BGR-A

        IplImage imgThreshold = cvCreateImage(cvGetSize(src), 8, 1);
        //apply thresholding
        cvInRangeS(src, min, max, imgThreshold);

        return imgThreshold;
    }

    public static double lineLength(CvPoint pt1, CvPoint pt2) {
        double dx = pt1.x() - pt2.x();
        double dy = pt1.y() - pt1.y();

        return Math.sqrt(dx*dx + dy*dy);
    }
}