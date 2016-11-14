/**
 * Created by pose2 on 11/13/2016.
 */

import org.bytedeco.javacpp.opencv_core.*;

import static org.bytedeco.javacpp.helper.opencv_core.CV_RGB;
import static org.bytedeco.javacpp.opencv_core.cvPoint;
import static org.bytedeco.javacpp.opencv_imgproc.cvLine;

public class BorderLine {
    // init the top and bottm pointer
    int top = 0;
    int bottom = 0;
    int startPoint = 0;
    IplImage src;

    // keep moving while move is false

    public BorderLine(int top, int bottom, int startPoint, IplImage src) {

        this.top = top;
        this.bottom = bottom;
        this.src = src;
        this.startPoint = startPoint;
    }

    public void moveAndDraw(IplImage src, IplImage dst, int i) {

        CvPoint pt0 = cvPoint( top + i,0);
        CvPoint pt1 = cvPoint(bottom + i, dst.width());
        cvLine(dst, pt0, pt1, CV_RGB(255, 0, 0), 2, 8, 0);

    }

}
