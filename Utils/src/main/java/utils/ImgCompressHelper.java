package utils;

import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by whuan on 2017/9/15.
 */
public class ImgCompressHelper
{
    private Image img;
    private int width;
    private int height;
    private String type;

    /**
     * 构造函数
     */
    public ImgCompressHelper(String fileName) throws IOException
    {
        URL url = new URL(fileName);
        img = ImageIO.read((url.openStream()));
       /* File file = new File(fileName);// 读入文件
        img = ImageIO.read(file);      // 构造Image对象*/
        String [] strings = fileName.split("\\.");
        type = strings[strings.length-1];      //文件类型
        width = img.getWidth(null);    // 得到源图宽
        height = img.getHeight(null);  // 得到源图长
    }

    /**
     * 按照宽度还是高度进行压缩
     * @param w int 最大宽度
     * @param h int 最大高度
     */
    public String resizeFix(int w, int h) throws IOException {
        if(w > width || h > height)
        {
            return resizeByWidth(width);
        }
        if (width / height > w / h) {
            return resizeByWidth(w);
        } else {
            return resizeByHeight(h);
        }
    }
    /**
     * 按照原尺寸进行压缩
     */
    public  String resizeOrigen() throws  IOException
    {
        return resizeByWidth(width);
    }

    /**
     * 以宽度为基准，等比例放缩图片
     * @param w int 新宽度
     */
    public String resizeByWidth(int w) throws IOException {
        int h = (int) (height * w / width);
        return resize(w, h);
    }

    /**
     * 以高度为基准，等比例缩放图片
     * @param h int 新高度
     */
    public String resizeByHeight(int h) throws IOException {
        int w = (int) (width * h / height);
        return resize(w, h);
    }

    /**
     * 强制压缩/放大图片到固定的大小
     * @param w int 新宽度
     * @param h int 新高度
     */
    public String resize(int w, int h) throws IOException {
        // SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢
        BufferedImage image = new BufferedImage(w, h,BufferedImage.TYPE_INT_RGB );
        image.getGraphics().drawImage(img, 0, 0, w, h, null); // 绘制缩小后的图
        InputStream in = null;
        byte[] data = null;
        //读取图片字节数组
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, type, baos);  //经测试转换的图片是格式这里就什么格式，否则会失真
        byte[] bytes = baos.toByteArray();
        //对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        String baseimg = encoder.encodeBuffer(bytes).trim();
        return baseimg;
    }
}
