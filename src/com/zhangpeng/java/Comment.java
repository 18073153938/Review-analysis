package com.zhangpeng.java;


import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import com.zhangpeng.util.HttpClientUtil;

/**
 * ��ȡ���ۺͷִʲ���
 * @author MZhang
 * @since 2015-5-7
 *
 */
public class Comment {
	static Vector<String> html;
	public static void main(String[] args) {
		Comment.getCommet("42800618881");
		Comment.getCredit();
	}
	
	//��ȡ����
	public static void  getCommet(String itemId){
		//ʹ����Ʒ����
		File file = new File("D:/comment/"+itemId+".txt"); 
		try {
			//�ļ������� �������� ץȡ����
			if ( ! file.exists()) {
				file.createNewFile();
			}
			BufferedOutputStream buff = new BufferedOutputStream(new FileOutputStream(file));
			int maxPage = 0;
			int currentPage = 0;
			boolean flag = false;
			try {
				String url = "http://rate.taobao.com/feedRateList.htm?_ksTS=1431442217626_1472&callback=jsonp_reviews_list&userNumId="+getUserId(itemId)+"&auctionNumId="+itemId+"&siteID=7&currentPageNum=1&rateType=&orderType=sort_weight&showContent=1&attribute=&ua=";
				for (int i = 1; i < 100; i++) {
				HttpClientUtil httpRespons = HttpClientUtil.sendGet(url);
				url = url.replace("currentPageNum="+i, "currentPageNum="+(i+1));
				//��ȡÿһ�е����ݷ���
				for(String key:httpRespons.getContentCollection().get(1).toString().split(",")){
					if ( ! flag) {
						if (key.contains("maxPage")) {
							maxPage = Integer.parseInt(key.split(":")[1]);
						}
						if (key.contains("currentPageNum")) {
							currentPage = Integer.parseInt(key.split(":")[1]);
						}	
						if (maxPage !=0 && maxPage == currentPage) {
							flag = true;
						}
					}
					if (key.contains("content")) {
						String cont = key.replace("\"content\":", "").replace("\"appendList\":[{", "").replace("\"append\":{", "").replace("\"reply\":{", "").replace("<br/>\\n", "");
						buff.write(cont.getBytes());
						buff.write("\r\n".getBytes());
					}
				}
				if (flag) {
					break;
				}		
			}
			buff.close();
			//д�ļ��ɹ�
			System.out.println("success");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ��ȡ�û�Id
	 * @param itemId
	 * @return
	 */
	public  static String getUserId(String itemId){
		String userId = null;
		String url = "http://item.taobao.com/item.htm?spm=a219r.lm895.14.22.hQJzlA&id="+itemId+"&ns=1&abbucket=6";
		try {
			boolean flag = false;
			HttpClientUtil response = HttpClientUtil.sendGet(url);
			html = response.getContentCollection();
			Pattern pa = Pattern.compile("userid=[0-9]*");
			for(String line:html){
				Matcher ma = pa.matcher(line);
				while (ma.find()) {
			         userId = ma.group().replace("userid=", "");
			         flag = true;
			         break;
			     }
				if (flag) {
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return userId;
	}
	
	public static Map<String, String> getCredit(){
		Map<String, String> creditMap = new HashMap<String, String>();
		List<String> a = new ArrayList<String>();
		boolean flag = false;
		for(String line:html){
			Matcher matcher = Pattern.compile("4\\.[0-9]{1}").matcher(line);
			while(matcher.find()){
				a.add(matcher.group());
				if (a.size() == 3) {
					break;
				}
			}
			if (flag) {
				break;
			}
		}
		for (int i = 0; i < a.size(); i++) {
			System.out.println(a.get(i));
		}
		return null;
		
	}
	
	/**
	 * ���ļ����зִ�
	 * @param fileName
	 * @return
	 */
	public Map<Integer, Map<String, Integer>> splitFile(String fileName){
		//�ִʲ���
		Map<Integer, Map<String, Integer>> splitCommentMap = new HashMap<Integer, Map<String,Integer>>();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileName))));
			String line = null ;
			int i = 0;
			//��ÿһ�н��зִ� 
			while ( (line = reader.readLine())!= null ){
				splitCommentMap.put(i, spiltString(line.toString()));
				i++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return splitCommentMap;
		
	}
	
	/**
	 * ���ַ������зִ�
	 * @param content
	 * @return
	 */
	public static Map<String, Integer> spiltString(String content){
		//�ִʲ���
		Map<String, Integer> wordsFrenMaps = new HashMap<String, Integer>();
		try {
			IKSegmenter ikSegmenter = new IKSegmenter(new StringReader(content), true);
	        Lexeme lexeme;
	        while ((lexeme = ikSegmenter.next()) != null) {
	            if(lexeme.getLexemeText().length()>1){
	                if(wordsFrenMaps.containsKey(lexeme.getLexemeText())){
	                	wordsFrenMaps.put(lexeme.getLexemeText(),wordsFrenMaps.get(lexeme.getLexemeText())+1);
	                }else {
	                	wordsFrenMaps.put(lexeme.getLexemeText(),1);
	                }
	            }
	        }
		} catch (IOException e) {
			e.printStackTrace();
		}
		return wordsFrenMaps;
		
	}
	
	/**
	 * ���д�Ƶͳ������
	 * @param wordsFrenMaps
	 * @return
	 */
	public static  List<Entry<String, Integer>> sortSegmentResult(Map<String,Integer> wordsFrenMaps){
      //���ｫmap.entrySet()ת����list
        List<Entry<String, Integer>> list = new ArrayList<Map.Entry<String,Integer>>(wordsFrenMaps.entrySet());
        //Ȼ��ͨ���Ƚ�����ʵ������
        Collections.sort(list,new Comparator<Map.Entry<String,Integer>>() {
            //��������
			@Override
			public int compare(Entry<String, Integer> o1,
					Entry<String, Integer> o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
        });
		return list;
        
    }
}
