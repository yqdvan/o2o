package com.dyq.o2o.util;

import com.mysql.cj.util.DnsSrv;
import net.coobird.thumbnailator.Thumbnailator;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class ImageUtil{
    private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    private static final SimpleDateFormat sDataFormat = new SimpleDateFormat("yyyyMMddHHHHmmss");
    private static final Random r = new Random();

//    public static String generateThumbnail(InputStream thumbnail , String fileName, String  targetAddr){
//        String realFileName = getRandomFileName();
//        String extension = getFileExtension(fileName);
//        makeDirPath(targetAddr);
//        String relativeAddr = targetAddr + realFileName + extension;
//        System.out.println("dyq-debug :"+"relativeAddr: " + relativeAddr);
//        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
//        try{
//            System.out.println("dyq-debug :"+"basePath: " + basePath);
//            System.out.println("dyq-debug :"+"dest: " + dest.getPath());
//            Thumbnails.of(thumbnail).size(200,200)
//                    .watermark(
//                            Positions.BOTTOM_RIGHT,
//                            ImageIO.read(new File(basePath + "logo.png")),
//                            0.25f)
//                    .outputQuality(0.8f).toFile(dest);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//        return relativeAddr;
//    }
    public static String generateThumbnail(CommonsMultipartFile thumbnail, String targetAddr) {
        String realFileName = FileUtil.getRandomFileName();
        String extension = getFileExtension(thumbnail);
        makeDirPath(targetAddr);
        String relativeAddr = targetAddr + realFileName + extension;
        File dest = new File(FileUtil.getImgBasePath() + relativeAddr);
        try {
            Thumbnails.of(thumbnail.getInputStream()).size(200, 200).outputQuality(0.25f).toFile(dest);
        } catch (IOException e) {
            throw new RuntimeException("创建缩略图失败：" + e.toString());
        }
        return relativeAddr;
    }

    /***
     * 创建目标路径上的目录, /home/work/xiangze/xxx.jpg,
     * 三个文件夹都要自动创
     * @param targetAddr
     */
    private static void makeDirPath(String targetAddr) {
        String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;
        File dirFile = new File(realFileParentPath);
        if(!dirFile.exists()){
            dirFile.mkdirs();
        }
    }

    /***
     * 获取输入文件流的扩展名
     * @param fileName
     * @return
     */
    private static String getFileExtension(String  fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    public static String getRandomFileName() {
        // get random 5
        int rannum = r.nextInt(89999) + 10000;
        String nowTimeStr = sDataFormat.format(new Date());
        return nowTimeStr + rannum;
    }

    public static void main(String[] args) throws IOException {
        Thumbnails.of(new File("/home/yqduan/Pictures/sky.jpg"))
                .size(300, 300).watermark(Positions.BOTTOM_RIGHT,
                ImageIO.read(new File(basePath + "/logo.png")), 0.25f)
                .outputQuality(0.8f).toFile("/home/yqduan/Pictures/skyLogo.jpg");

    }

    public static void deleteFileOrPath(String storePath){
        File fileOrPath = new File(PathUtil.getImgBasePath() + storePath);
        if(fileOrPath.exists()) {
            if (fileOrPath.isDirectory()) {
                File[] files = fileOrPath.listFiles();
                for (File file : files) {
                    file.delete();
                }
            }
            fileOrPath.delete();
        }
    }

    public static List<String> generateNormalImgs(List<CommonsMultipartFile> imgs, String targetAddr) {
        int count = 0;
        List<String> relativeAddrList = new ArrayList<String>();
        if (imgs != null && imgs.size() > 0) {
            makeDirPath(targetAddr);
            for (CommonsMultipartFile img : imgs) {
                String realFileName = FileUtil.getRandomFileName();
                String extension = getFileExtension(img);
                String relativeAddr = targetAddr + realFileName + count + extension;
                File dest = new File(FileUtil.getImgBasePath() + relativeAddr);
                count++;
                try {
                    Thumbnails.of(img.getInputStream()).size(600, 300).outputQuality(0.5f).toFile(dest);
                } catch (IOException e) {
                    throw new RuntimeException("创建图片失败：" + e.toString());
                }
                relativeAddrList.add(relativeAddr);
            }
        }
        return relativeAddrList;
    }
    private static String getFileExtension(CommonsMultipartFile cFile) {
        String originalFileName = cFile.getOriginalFilename();
        return originalFileName.substring(originalFileName.lastIndexOf("."));
    }
}
