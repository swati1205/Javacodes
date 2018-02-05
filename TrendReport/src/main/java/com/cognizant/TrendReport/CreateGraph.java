package com.cognizant.TrendReport;

import hudson.model.AbstractBuild;
import hudson.model.Run;
import hudson.util.DataSetBuilder;
import hudson.util.ShiftedCategoryAxis;

import java.awt.BasicStroke;
import java.awt.Color;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.ui.RectangleInsets;

import hudson.util.ChartUtil.NumberOnlyBuildLabel;

public class CreateGraph {
	 public static JFreeChart buildChart( AbstractBuild<?, ?> build_no, String File_Path) throws IOException, InterruptedException{
		 JFreeChart lineChart = null;
			System.out.println("in Jfree chart: " + File_Path);
			lineChart = ChartFactory.createLineChart(null, "Build Number",
					"Number of Severities", createDataset(build_no, File_Path),
					PlotOrientation.VERTICAL, true, true, false);

			lineChart.setBackgroundPaint(Color.white);

			CategoryPlot plot = lineChart.getCategoryPlot();
			plot.setBackgroundPaint(Color.WHITE);
			plot.setOutlinePaint(null);
			plot.setForegroundAlpha(0.8f);
			plot.setRangeGridlinesVisible(true);
			plot.setRangeGridlinePaint(Color.black);

			CategoryAxis domainAxis = new ShiftedCategoryAxis(null);
			plot.setDomainAxis(domainAxis);
			domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);
			domainAxis.setLowerMargin(0.0);
			domainAxis.setUpperMargin(0.0);
			domainAxis.setCategoryMargin(0.0);

			LineAndShapeRenderer r2 = (LineAndShapeRenderer) plot.getRenderer();

			// plot.setRenderer((CategoryItemRenderer) r2);
			r2.setSeriesPaint(0, Color.RED);
			r2.setSeriesPaint(1, Color.BLUE);
			r2.setSeriesPaint(2, Color.GREEN);
			r2.setSeriesPaint(3, Color.DARK_GRAY);
			// plot.getRenderer().setSeriesPaint(0, Color.RED);
			r2.setSeriesStroke(0, new BasicStroke(2.0f));
			r2.setSeriesStroke(1, new BasicStroke(2.0f));
			r2.setSeriesStroke(2, new BasicStroke(2.0f));
			r2.setSeriesStroke(3, new BasicStroke(2.0f));
			NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
			rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

			// crop extra space around the graph
			plot.setInsets(new RectangleInsets(0, 0, 0, 5.0));

			return lineChart;
		}
public static CategoryDataset createDataset(AbstractBuild<?, ?> build_no, String File_Path) throws IOException   {
   	 //BuildAction action = (BuildAction) lastAction;
   	

   	 //DataSetBuilder<String, NumberOnlyBuildLabel> builder = new DataSetBuilder<String, NumberOnlyBuildLabel>();
    // NumberOnlyBuildLabel buildLabel = new NumberOnlyBuildLabel(action.getBuild());
   	DataSetBuilder builder = new DataSetBuilder();
     
     Properties props = new Properties();
		System.out.println("in createDataset: " + File_Path);
		FileInputStream fis = new FileInputStream(File_Path);
		props.load(fis);
		Set<Object> keys = props.keySet();
		System.out.println("keys are"+keys);
		List listKeys = new ArrayList();
		

		listKeys.addAll(keys);
		Collections.sort(listKeys);
		//System.out.println("listkeys are"+listKeys);
		
		int sizeOfLoop = keys.size();
		//System.out.println("Size of loop: " + sizeOfLoop);
 
		
		

		for (int i = listKeys.size()-1; i > (listKeys.size() -6); i--) {
			String value = props.getProperty((String) listKeys.get(i)).trim();
			System.out.println("Value 1 is :"+value);
			int key = Integer.parseInt((String) listKeys.get(i));
			String[] array = value.split("\\,");
			 System.out.println("Array 1 is: "+array);
			for (int j = 0; j < 4; j++) {
				int high = Integer.parseInt(array[0].trim());
				int med = Integer.parseInt(array[1].trim());
				int low = Integer.parseInt(array[2].trim());
				int neg = Integer.parseInt(array[3].trim());
				builder.add(high, "High", build_no);
				// System.out.println("This: "+high+"High"+key);
				builder.add(med, "Medium",  build_no);
				builder.add(low, "Low",  build_no);
				builder.add(neg, "Negligible",  build_no);
			}
			fis.close();

		}

		System.out.println("Inside datset");

		
   	 
		
     

           return builder.build();
		
}
}


