package top.webra.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.channels.FileChannel;
import java.util.Date;

public class ImageUtil {
    /**
     * 送入一张图片文件,两外两个信息是高宽比,比如   5:3     16:10等等
     * @param image
     * @param width
     * @param height
     * @return
     * @throws IOException
     */
    public static File shearImage(InputStream image, Integer width, Integer height,String imageNewName,String suffixName) throws IOException {
        // 获得文件名和后缀
        String substring = suffixName.substring(suffixName.lastIndexOf(".") + 1);
        File file = new File("E:\\Code\\webra-blog\\imgs\\image\\"+imageNewName+"."+substring);
        // 读取文件 以便对其操作
        BufferedImage bufferedImage = ImageIO.read(image);
        int imageHeight = bufferedImage.getHeight();
        int imagetWidth = bufferedImage.getWidth();
        double proportion = (double) imagetWidth / (double) imageHeight;
        if ((double)width/height > proportion){
            // 高度太高 对高度进行剪切
            double imageYDouble = imagetWidth / (double)width * height;
            int imageYInt = new Double(imageYDouble).intValue();
            BufferedImage subimage = bufferedImage.getSubimage(0, (imageHeight - imageYInt) / 2, imagetWidth, imageYInt);
            ImageIO.write(subimage,substring,file);
        }else if ((double)width/height < proportion){
            // 宽度太宽 对宽度进行剪切
            double imageXDouble = imageHeight /(double)height * width;
            int imageXInt = new Double(imageXDouble).intValue();
            BufferedImage subimage = bufferedImage.getSubimage((imagetWidth - imageXInt) / 2, 0, imageXInt, imageHeight);
            ImageIO.write(subimage,substring,file);
        }else{

        }

        return file;

    }
}
