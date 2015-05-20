package com.zhangpeng.util;

import java.util.ArrayList;


/**
 * ȫ����㷨
 * @author zhangpeng
 * @date 2015-04-8 
 */
public class SortUtil {
	//��NUM����Ϊ����������ĳ��ȼ�ʵ��ȫ����
	private static int NUM = 2;

	/**
	 * �ݹ��㷨�������ݷ�Ϊ�����֣��ݹ齫���ݴ�������Ҳ�ʵ��ȫ����
	 *
	 * @param datas
	 * @param target
	 */


	public static void main(String[] args) {
		ArrayList<String> groups = new ArrayList<String>();
		String str[] = { "A", "B", "C", "D", "E","F" };
		int nCnt = str.length;

		int nBit = (0xFFFFFFFF >>> (32 - nCnt));
		for (int i = 1; i <= nBit; i++) {
			StringBuffer item = new StringBuffer();
			for (int j = 0; j < nCnt; j++) {
				if ((i << (31 - j)) >> 31 == -1) {
					item.append(str[j]+" ");
				}
			}
			if(item.toString().trim().split(" ").length == NUM){
				groups.add(item.toString());
			}
			
			for(String key:groups){
				System.out.println(key);
			}
		}
	}
}


