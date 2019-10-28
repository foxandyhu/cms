import com.bfly.common.FileUtil;
import com.bfly.common.StringUtil;
import com.bfly.core.enums.WaterMarkPos;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TT2 {

    public static void main(String[] args) {
//        try {
//            File imgFile=new File("D:\\4b918b21h6e5a8e67faa8&690.jpg");
//            File waterFile = new File("D:\\btn_down.gif");
//            int offsetX=50,offsetY=20;float alpha = (float)50/100;
//
//            Image image = ImageIO.read(imgFile);
//            int width = image.getWidth(null);
//            int height = image.getHeight(null);
//            if (width < 0 || height < 0) {
//                return;
//            }
//
//            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//
//            Image waterImage = ImageIO.read(waterFile);
//
//            int width_1 = waterImage.getWidth(null);
//            int height_1 = waterImage.getHeight(null);
//            //左上 0,0 右上 width-width_1,0 左下 0,height-height_1 右下 width-widht_1,height-heigh_1
//            int x = width-width_1-100;
//            int y =height-height_1;
//            if (x < 0 || y < 0) {
//                return;
//            }
//            Graphics2D g = bufferedImage.createGraphics();
//
//            g.drawImage(image, 0, 0, width, height, null);
//            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
//            g.drawImage(waterImage, x, y, width_1, height_1, null);
//            g.dispose();
//            ImageIO.write(bufferedImage, FileUtil.getImageContentType(imgFile), imgFile);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        try {
            File imgFile = new File("D:\\4b918b21h6e5a941e6f5b&690.jpg");
            int fontSize = 14;
            String fontColor = "#bf1b1b";
            String text = "这是水印哦";
            int offsetX = 0, offsetY = 50;
            float alpha = (float) 80 / 100;
            Image image = ImageIO.read(imgFile);
            int width = image.getWidth(null);
            int height = image.getHeight(null);

            BufferedImage bufImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics2D = bufImage.createGraphics();
            graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            graphics2D.drawImage(image, 0, 0, width, height, null);

            Font font = new Font("宋体", Font.BOLD, fontSize <= 0 ? 12 : fontSize);
            graphics2D.setColor(Color.decode(fontColor));
            graphics2D.setFont(font);
            graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));

            int fontWidth = graphics2D.getFontMetrics(graphics2D.getFont()).charsWidth(text.toCharArray(), 0, text.length());
            int fontHeight = graphics2D.getFontMetrics(graphics2D.getFont()).getHeight();
            int x = width - fontWidth - offsetX;
            int y = height - fontHeight - offsetY;
            if (x < 0 || y < 0) {
                return;
            }
            Point point = getOffset(0, offsetX, offsetY, width, height, fontWidth, fontHeight);
            graphics2D.drawString(text, (int) point.getX(), (int) point.getY());
            graphics2D.dispose();

            ImageIO.write(bufImage, FileUtil.getImageContentType(imgFile), imgFile);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }


    }

    private static Point getOffset(int position, int x, int y, int imgWidth, int imgHeight, int waterWidth, int waterHeight) {
        WaterMarkPos pos = WaterMarkPos.getPos(position);
        Point point;
        if (pos == WaterMarkPos.RANDOM) {
            // 随机水印
            pos = WaterMarkPos.getPos(Integer.parseInt(StringUtil.getRandom(WaterMarkPos.MIDDLE.getId())));
        }
        switch (pos) {
            case TOP_LEFT:
                point = new Point(x, y);
                break;
            case BOTTOM_LEFT:
                point = new Point(x, imgHeight - waterHeight - y);
                break;
            case TOP_RIGHT:
                point = new Point(imgWidth - waterWidth - x, y);
                break;
            case MIDDLE:
                point = new Point(imgWidth / 2 - x, imgHeight / 2 - y);
                break;
            case BOTTOM_RIGHT:
            default:
                point = new Point(imgWidth - waterWidth - x, imgHeight - waterHeight - y);
        }
        return point;
    }
}
