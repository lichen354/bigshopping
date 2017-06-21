package com.bsy.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import sun.misc.BASE64Encoder;

public class RandromCodeUtil {
	private static char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
            'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
	
	/**
	 * 获取验证码
	 */
	public static char[] getRandomCode(int codeCount) {
        Random r = new Random();
        char[] chars = new char[codeCount];
        
        int len = codeSequence.length;
        for (int i = 0; i < codeCount; i++) {
            chars[i] = codeSequence[r.nextInt(len)];
        }
        return chars;
	}
    
	/**
	 * 获取验证码image对象
	 * @param codes 验证码
	 * @param width 验证码图片宽度
	 * @param height 验证码图片高度
	 * @param lineCount 混淆线个数
	 * @return
	 */
	public static BufferedImage getImg(char[] codes, int width, int height, int lineCount){
		//定义画布
		BufferedImage buffImg = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		 //得到画笔
        Graphics g = buffImg.getGraphics();
        //1.设置颜色,画边框
        g.setColor(Color.black);
        g.drawRect(0,0,width,height);
        //2.设置颜色,填充内部
        g.setColor(Color.white);
        g.fillRect(1,1,width-2,height-2);
        //3.设置干扰线
        g.setColor(Color.gray);
        Random r = new Random();
        for (int i = 0; i < lineCount; i++) {
            g.drawLine(r.nextInt(width),r.nextInt(width),r.nextInt(width),r.nextInt(width));
        }
        //4.设置验证码
        g.setColor(Color.blue);
        //4.1设置验证码字体
        g.setFont(new Font("宋体",Font.BOLD|Font.ITALIC,15));
       
        for (int i = 0; i < codes.length; i++) {
            g.drawString(codes[i]+"",15*(i+1),15);
        }
		return buffImg;
	}
	
	/**
	 * 
	 */
	public static String getToken(){
		//随机码
		Random r = new Random();
        Integer itr = r.nextInt(999999); //0 ~ 999999 的随机数
        
        try {
        	//此 MessageDigest 类为应用程序提供信息摘要算法的功能，如 MD5 或 SHA 算法。
        	//信息摘要是安全的单向哈希函数，它接收任意大小的数据，并输出固定长度的哈希值。
            MessageDigest md = MessageDigest.getInstance("md5");
            byte md5[] =  md.digest(itr.toString().getBytes());
            //base64编码--任意二进制编码明文字符   
            BASE64Encoder encoder = new BASE64Encoder();
            return encoder.encode(md5);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
	}
	
}
