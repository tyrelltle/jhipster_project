package com.hak.haklist.web.rest.util;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImageUtil {
    public static BufferedImage scaleImage(BufferedImage image, int width, int height) throws IOException {
        int type = image.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : image.getType();
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();

        double scaleX = (double) width / imageWidth;
        double scaleY = (double) height / imageHeight;
        AffineTransform scaleTransform = AffineTransform.getScaleInstance(scaleX, scaleY);
        AffineTransformOp bilinearScaleOp = new AffineTransformOp(scaleTransform, AffineTransformOp.TYPE_BILINEAR);

        image = bilinearScaleOp.filter(image, new BufferedImage(width, height, type));
        return image.getSubimage(0, (height-width)/2, width, width);
    }

    /**
     * validates if the given content type is valid for image, which is image/[jpg/png/gif/bmp]
     * @param type the content type string being validated
     * @return true if is valid
     */
    public static boolean validImageType(String type){
        Pattern pattern=Pattern.compile("(^image/(jpg|png|gif|bmp)$)");
        Matcher matcher = pattern.matcher(type);
        return matcher.matches();
    }
}
