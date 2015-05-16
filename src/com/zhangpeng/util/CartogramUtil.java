package com.zhangpeng.util;

import java.awt.Font;
import java.text.DecimalFormat;
import java.text.NumberFormat;


import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;



public class CartogramUtil {
	ChartPanel frame1;
	public CartogramUtil(int type){
		  DefaultPieDataset data = getDataSet(type);
	      JFreeChart chart = ChartFactory.createPieChart("��Ʒ����",data,true,false,false);
	    //���ðٷֱ�
	      PiePlot pieplot = (PiePlot) chart.getPlot();
	      DecimalFormat df = new DecimalFormat("0.00%");//���һ��DecimalFormat������Ҫ������С������
	      NumberFormat nf = NumberFormat.getNumberInstance();//���һ��NumberFormat����
	      StandardPieSectionLabelGenerator sp1 = new StandardPieSectionLabelGenerator("{0}  {2}", nf, df);//���StandardPieSectionLabelGenerator����
	      pieplot.setLabelGenerator(sp1);//���ñ�ͼ��ʾ�ٷֱ�
	  
	      //û�����ݵ�ʱ����ʾ������
	      pieplot.setNoDataMessage("��������ʾ");
	      pieplot.setCircular(false);
	      pieplot.setLabelGap(0.02D);
	  
	      pieplot.setIgnoreNullValues(true);//���ò���ʾ��ֵ
	      pieplot.setIgnoreZeroValues(true);//���ò���ʾ��ֵ
	      frame1=new ChartPanel (chart,true);
	      chart.getTitle().setFont(new Font("����",Font.BOLD,20));//���ñ�������
	      PiePlot piePlot= (PiePlot) chart.getPlot();//��ȡͼ���������
	      piePlot.setLabelFont(new Font("����",Font.BOLD,15));//�������
	      chart.getLegend().setItemFont(new Font("����",Font.BOLD,15));
	}
    private static DefaultPieDataset getDataSet(int type) {
    	DefaultPieDataset dataset = new DefaultPieDataset();
    	if (type == 0) {
    		dataset.setValue("����",1210);
            dataset.setValue("����",712);
            dataset.setValue("��ʽ",549);
            dataset.setValue("����",311);
            dataset.setValue("����",600);
            dataset.setValue("����",400);
            dataset.setValue("����",800);
		}else {
			dataset.setValue("�ܺ�",1198);
            dataset.setValue("����",740);
            dataset.setValue("����",703);
            dataset.setValue("��ϲ��",333);
            dataset.setValue("  ����",319);
            dataset.setValue("ɫ��",288);
		}
        
        return dataset;
}
    public ChartPanel getChartPanel(){
    	return frame1;
    }
}
