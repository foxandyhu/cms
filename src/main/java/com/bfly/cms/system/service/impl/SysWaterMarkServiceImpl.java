package com.bfly.cms.system.service.impl;

import com.bfly.cms.system.entity.SysWaterMark;
import com.bfly.cms.system.service.ISysWaterMarkService;
import com.bfly.common.FileUtil;
import com.bfly.common.StringUtil;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import com.bfly.core.config.ResourceConfig;
import com.bfly.core.enums.WaterMarkPos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/20 9:31
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class SysWaterMarkServiceImpl extends BaseServiceImpl<SysWaterMark, Integer> implements ISysWaterMarkService {

    private Logger logger = LoggerFactory.getLogger(SysWaterMarkServiceImpl.class);
    @Autowired
    @Qualifier("taskExecutor")
    private TaskExecutor taskExecutor;

    @Override
    public SysWaterMark getWaterMark() {
        List<SysWaterMark> list = getList();
        return (list != null && !list.isEmpty()) ? list.get(0) : null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean edit(SysWaterMark sysWaterMark) {
        SysWaterMark waterMark = getWaterMark();

        String img = ResourceConfig.getUploadTempFileToDestDirForRelativePath(sysWaterMark.getImg(), ResourceConfig.getSysImgDir());
        sysWaterMark.setImg(img);
        //不存在则新增 存在则修改
        if (waterMark == null) {
            return super.save(sysWaterMark);
        }
        if (img == null) {
            sysWaterMark.setImg(waterMark.getImg());
        }
        sysWaterMark.setId(waterMark.getId());
        return super.edit(sysWaterMark);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(SysWaterMark waterMark) {
        return edit(waterMark);
    }

    @Override
    public void waterMarkFile(String imgRelativePath) {
        SysWaterMark waterMark = getWaterMark();
        if (waterMark == null || !waterMark.isOpenWaterMark()) {
            return;
        }
        if (!StringUtils.hasLength(imgRelativePath)) {
            return;
        }
        File file = new File(ResourceConfig.getAbsolutePathForRoot(imgRelativePath));
        if (!file.exists()) {
            return;
        }
        taskExecutor.execute(() -> {
            if (StringUtils.hasLength(waterMark.getImg())) {
                File waterMarkImg = new File(ResourceConfig.getAbsolutePathForRoot(waterMark.getImg()));
                if (waterMarkImg.exists()) {
                    waterMarkByImg(waterMark, file);
                } else {
                    waterMarkByFont(waterMark, file);
                }
            } else {
                waterMarkByFont(waterMark, file);
            }
        });
    }

    /**
     * 文字水印
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/23 11:26
     */
    private void waterMarkByFont(SysWaterMark waterMark, File imgFile) {
        if (!StringUtils.hasLength(waterMark.getText())) {
            return;
        }
        try {
            Image image = ImageIO.read(imgFile);
            int width = image.getWidth(null);
            int height = image.getHeight(null);
            float alpha = (float) waterMark.getAlpha() / 100;

            BufferedImage bufImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics2D = bufImage.createGraphics();
            graphics2D.drawImage(image, 0, 0, width, height, null);

            Font font = new Font("宋体", Font.PLAIN, waterMark.getSize() <= 0 ? 12 : waterMark.getSize());
            Color color = Color.BLACK;
            if (StringUtils.hasLength(waterMark.getColor())) {
                color = Color.decode(waterMark.getColor());
            }
            graphics2D.setColor(color);
            graphics2D.setFont(font);
            graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));

            int fontWidth = graphics2D.getFontMetrics(graphics2D.getFont()).charsWidth(waterMark.getText().toCharArray(), 0, waterMark.getText().length());
            int fontHeight = graphics2D.getFontMetrics(graphics2D.getFont()).getHeight();
            int x = width - fontWidth - waterMark.getOffsetX();
            int y = height - fontHeight - waterMark.getOffsetY();
            if (x < 0 || y < 0) {
                return;
            }
            Point point = getOffset(waterMark, width, height, fontWidth, fontHeight);
            graphics2D.drawString(waterMark.getText(), (int) point.getX(), (int) point.getY());
            graphics2D.dispose();

            ImageIO.write(bufImage, FileUtil.getImageContentType(imgFile), imgFile);
        } catch (IOException e) {
            logger.error("文字水印出错", e);
            return;
        }
    }

    /**
     * 图片水印
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/23 11:28
     */
    private void waterMarkByImg(SysWaterMark waterMark, File imgFile) {
        try {
            Image image = ImageIO.read(imgFile);
            int width = image.getWidth(null);
            int height = image.getHeight(null);
            if (width < waterMark.getImgWidth() || height < waterMark.getImgHeight()) {
                return;
            }
            float alpha = (float) waterMark.getAlpha() / 100;
            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            File waterFile = new File(ResourceConfig.getAbsolutePathForRoot(waterMark.getImg()));
            Image waterImage = ImageIO.read(waterFile);

            int width_1 = waterImage.getWidth(null);
            int height_1 = waterImage.getHeight(null);
            int x = width - width_1 - waterMark.getOffsetX();
            int y = height - height_1 - waterMark.getOffsetY();
            if (x < 0 || y < 0) {
                return;
            }
            Graphics2D g = bufferedImage.createGraphics();

            Point point = getOffset(waterMark, width, height, width_1, height_1);

            g.drawImage(image, 0, 0, width, height, null);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            g.drawImage(waterImage, (int) point.getX(), (int) point.getY(), width_1, height_1, null);
            g.dispose();
            ImageIO.write(bufferedImage, FileUtil.getImageContentType(imgFile), imgFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 得到水印坐标
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/23 13:57
     */
    private Point getOffset(SysWaterMark waterMark, int imgWidth, int imgHeight, int waterWidth, int waterHeight) {
        WaterMarkPos pos = WaterMarkPos.getPos(waterMark.getPos());
        int x = waterMark.getOffsetX(), y = waterMark.getOffsetX();
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
