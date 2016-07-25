/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package opencv_ext;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;
import org.opencv.utils.Converters;

/**
 *
 * @author Nicolas
 */
public class TGG_OpenCV_Util {
    
    static{ 
        File opencv_dll = new File(Core.NATIVE_LIBRARY_NAME + ".dll");
        if(!opencv_dll.exists()){
            InputStream dll_in = TGG_OpenCV_Util.class.getResourceAsStream("/resources/" + Core.NATIVE_LIBRARY_NAME + "_x" + System.getProperty("sun.arch.data.model") + ".dll");
            try {
                Files.copy(dll_in, opencv_dll.toPath());
            } catch (IOException ex) {
                Logger.getLogger(TGG_OpenCV_Util.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
    
    private static Mat bufferedImage2Mat(BufferedImage bufferedImage){
        byte[] pixels = ((DataBufferByte) bufferedImage.getRaster().getDataBuffer()).getData();
        Mat im = null;
        if(bufferedImage.getType() == BufferedImage.TYPE_INT_ARGB){
            //convert argb to rgba
            for(int i = 0; i < pixels.length/4; i++){
                byte temp = pixels[i*4];
                pixels[i*4] = pixels[i*4+3];
                pixels[i*4+3] = temp;
            }
            im = new Mat(bufferedImage.getWidth(), bufferedImage.getHeight(), CvType.CV_8UC4);
        }else if(bufferedImage.getType() == BufferedImage.TYPE_BYTE_GRAY){
            im = new Mat(bufferedImage.getWidth(), bufferedImage.getHeight(), CvType.CV_8UC1);
        }
        im.put(0, 0, pixels);
        return im;
    }
    
    private static MatOfPoint hull2Points(MatOfInt hull, MatOfPoint contour) {
        // Contains indexes of pointing to corner points of contours
        ArrayList<Integer> indexes = new ArrayList<>(hull.toList());
        // Contains list of points found in contour
        List<Point> pointList = contour.toList();
        // Destination for corner points
        ArrayList<Point> points = new ArrayList<>();
        // Reads corner points into `points`
        for(Integer index:indexes) {
            points.add(pointList.get(index));
        }
        // Converts list to Mat representation
        MatOfPoint mop = new MatOfPoint();
        mop.fromList(points);
        return mop;
    }   
    
    public static Point[][] getExternalConvexHullPoints(BufferedImage bufferedImage){
        // Initialize
        Mat hierarchy = new Mat();
        Mat image = TGG_OpenCV_Util.bufferedImage2Mat(bufferedImage);
        Point[][] pointResults;
        // Get Contours
        MatOfInt points = new MatOfInt();
        ArrayList<MatOfPoint> contours = new ArrayList<>();
        Imgproc.findContours(image, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
        // Initialize rows of point results
        pointResults = new Point[contours.size()][0];
        // Iterate over contours
        for(int contour = 0; contour < contours.size(); contour++){
            // Get convex hull from contour
            Imgproc.convexHull(contours.get(contour), points, true);
            // Convert convex hull to mat of points
            MatOfPoint mopOut = hull2Points(points, contours.get(contour));
            // Convert mat of points to list of points
            ArrayList<Point> pointList = new ArrayList<>();
            Converters.Mat_to_vector_Point2d(mopOut, pointList);
            // Set column size for contour
            pointResults[contour] = new Point[pointList.size()];
            // Fill result array with points
            for(int p = 0; p < pointList.size(); p++){
                pointResults[contour][p] = pointList.get(p);
            }
        }
        // Return result
        return pointResults;
    }
}
