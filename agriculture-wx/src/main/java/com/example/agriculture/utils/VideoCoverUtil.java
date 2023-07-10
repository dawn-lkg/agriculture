package com.example.agriculture.utils;

import org.bytedeco.javacv.*;
import org.bytedeco.opencv.opencv_core.Mat;

import static org.bytedeco.opencv.global.opencv_imgcodecs.imwrite;

/**
 * @author chenliming
 * @date 2023/3/19 0:18
 */


public class VideoCoverUtil {

    public static String getVideoCover(String videoPath, String coverPath) {
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(videoPath);
        try {
            grabber.start();
            String videoCoverPath = coverPath + "/" + System.currentTimeMillis() + ".jpg";
            OpenCVFrameConverter.ToMat converter = new OpenCVFrameConverter.ToMat();
            Mat mat = converter.convert(grabber.grabImage());
            if (null != mat) {
                imwrite(videoCoverPath, mat);
            }
            grabber.stop();
            return videoCoverPath;
        } catch (FrameGrabber.Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

