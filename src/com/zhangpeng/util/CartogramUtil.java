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
	      JFreeChart chart = ChartFactory.createPieChart("产品特征",data,true,false,false);
	    //设置百分比
	      PiePlot pieplot = (PiePlot) chart.getPlot();
	      DecimalFormat df = new DecimalFormat("0.00%");//获得一个DecimalFormat对象，主要是设置小数问题
	      NumberFormat nf = NumberFormat.getNumberInstance();//获得一个NumberFormat对象
	      StandardPieSectionLabelGenerator sp1 = new StandardPieSectionLabelGenerator("{0}  {2}", nf, df);//获得StandardPieSectionLabelGenerator对象
	      pieplot.setLabelGenerator(sp1);//设置饼图显示百分比
	  
	      //没有数据的时候显示的内容
	      pieplot.setNoDataMessage("无数据显示");
	      pieplot.setCircular(false);
	      pieplot.setLabelGap(0.02D);
	  
	      pieplot.setIgnoreNullValues(true);//设置不显示空值
	      pieplot.setIgnoreZeroValues(true);//设置不显示负值
	      frame1=new ChartPanel (chart,true);
	      chart.getTitle().setFont(new Font("宋体",Font.BOLD,20));//设置标题字体
	      PiePlot piePlot= (PiePlot) chart.getPlot();//获取图表区域对象
	      piePlot.setLabelFont(new Font("宋体",Font.BOLD,15));//解决乱码
	      chart.getLegend().setItemFont(new Font("黑体",Font.BOLD,15));
	}
    private static DefaultPieDataset getDataSet(int type) {
    	DefaultPieDataset dataset = new DefaultPieDataset();
    	if (type == 0) {
    		dataset.setValue("质量",1210);
            dataset.setValue("做工",712);
            dataset.setValue("款式",549);
            dataset.setValue("面料",311);
            dataset.setValue("合身",600);
            dataset.setValue("服务",400);
            dataset.setValue("物流",800);
		}else {
			dataset.setValue("很好",1198);
            dataset.setValue("不错",740);
            dataset.setValue("满意",703);
            dataset.setValue("很喜欢",333);
            dataset.setValue("  好评",319);
            dataset.setValue("色差",288);
		}
        
        return dataset;
}
    public ChartPanel getChartPanel(){
    	return frame1;
    }
}
