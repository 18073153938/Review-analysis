package com.zhangpeng.ui;


import javax.swing.JFrame;

import com.zhangpeng.util.CartogramUtil;


public class CountFrame {
	public static void print(int type){  
	    JFrame frame=new JFrame("Java����ͳ��ͼ");  
	    frame.add(new CartogramUtil(type).getChartPanel());           //��ӱ�״ͼ  
	    frame.setBounds(50, 50, 800, 600);  
	    frame.setVisible(true); 
	    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}  
	
	public static void main(String[] args) {
		CountFrame.print(0);
	}
}
