package com.campuspick.demo.util;

import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.UUID;

public class UploadThumbnailUtils {

    private static final Logger logger = LoggerFactory.getLogger(UploadThumbnailUtils.class);

    // 파일 업로드
    public static String fileUpload(String uploadPath, String originalName, byte[] fileData) throws Exception {

        // 겹치지 않는 파일명을 위한 유니크한 값
        UUID uid = UUID.randomUUID();

        // 원본 파일과 결합한 유니크한 파일명
        String newFileName = uid.toString() + "_" + originalName;

        //파일 저장할 폴더 생성(년 월 일 기준)
        String imgPath = calcPath(uploadPath);

        // 저장할 파일 생성
        File target = new File(uploadPath + imgPath, newFileName);

        //파일을 저장
        FileCopyUtils.copy(fileData, target);

        String thumbnailName = null;
        thumbnailName = makeThumbnail(uploadPath, imgPath, newFileName);

        return thumbnailName;
    }

    public static void readFile(String path){
        File file = new File("path");
        System.out.println("file 존재 여부: " + file.exists());
        System.out.println("파일의 크기: " +file.length());
    }

    // 폴더 이름 지정
    public static String calcPath(String uploadPath) {
        Calendar cal = Calendar.getInstance();
        String yearPath = File.separator + cal.get(Calendar.YEAR);
        String monthPath = yearPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.MONTH) + 1);
        String datePath = monthPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.DATE));

        // 폴더 생성
        makeDir(uploadPath, yearPath, monthPath, datePath);

        logger.info(datePath);

        return datePath;
    }

    // 폴더 생성
    private static void makeDir(String uploadPath, String... paths) {

        if (new File(uploadPath + paths[paths.length - 1]).exists()) { return; }

        for (String path : paths) {
            File dirPath = new File(uploadPath + path);

            if (!dirPath.exists()) {
                dirPath.mkdir();
            }
        }
    }

    //썸네일 이미지 생성
    private static String makeThumbnail(String uploadPath, String path, String fileName) throws Exception {
        BufferedImage sourceImg = ImageIO.read(new File(uploadPath + path, fileName));
        BufferedImage destImg = Scalr.resize(sourceImg, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_HEIGHT, 100);
        String thumbnailName = uploadPath + path + File.separator + "s_" + fileName;
        File newFile = new File(thumbnailName);
        String formatName = fileName.substring(fileName.lastIndexOf(".")+1);
        ImageIO.write(destImg, formatName.toUpperCase(), newFile);
        return thumbnailName.substring(uploadPath.length()).replace(File.separatorChar, '/');
    }
}
