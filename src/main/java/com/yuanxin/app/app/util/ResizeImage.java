package com.yuanxin.app.app.util;



import java.awt.image.BufferedImage;

public class ResizeImage {
	
	/**
     * @param im            原始图像
     * @param resizeTimes   需要缩小的倍数，缩小2倍为原来的1/2 ，这个数值越大，返回的图片越小
     * @return              返回处理后的图像
     */
    public static BufferedImage resizeImage(BufferedImage im, float resizeTimes) {
        /*原始图像的宽度和高度*/
        int width = im.getWidth();
        int height = im.getHeight();
 
        /*调整后的图片的宽度和高度*/
        int toWidth = (int) (Float.parseFloat(String.valueOf(width)) / resizeTimes);
        int toHeight = (int) (Float.parseFloat(String.valueOf(height)) / resizeTimes);
 
        /*新生成结果图片*/
        BufferedImage result = new BufferedImage(toWidth, toHeight, BufferedImage.TYPE_INT_RGB);
 
        result.getGraphics().drawImage(im.getScaledInstance(toWidth, toHeight, java.awt.Image.SCALE_SMOOTH), 0, 0, null);
        return result;
    }
 
    /**
     * @param im            原始图像
     * @param resizeTimes   倍数,比如0.5就是缩小一半,0.98等等double类型
     * @return              返回处理后的图像
     */
    public static BufferedImage zoomImage(BufferedImage im) {
        /*原始图像的宽度和高度*/
 
        /*调整后的图片的宽度和高度*/
        int toWidth=64;
        int toHeight=64;
        /*新生成结果图片*/
        BufferedImage result = new BufferedImage(toWidth, toHeight, BufferedImage.TYPE_INT_RGB);
 
        result.getGraphics().drawImage(im.getScaledInstance(toWidth, toHeight, java.awt.Image.SCALE_SMOOTH), 0, 0, null);
        return result;
    }
 


 
 



}
