package com.dyq.o2o.util;

import net.coobird.thumbnailator.Thumbnailator;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class ImageUtil{
    public static void main(String[] args) throws IOException {
        String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();

        Thumbnails.of(new File("/home/yqduan/Pictures/sky.jpg"))
                .size(300, 300).watermark(Positions.BOTTOM_RIGHT,
                ImageIO.read(new File(basePath + "/logo.png")), 0.25f)
                .outputQuality(0.8f).toFile("/home/yqduan/Pictures/skyLogo.jpg");

    }
}
