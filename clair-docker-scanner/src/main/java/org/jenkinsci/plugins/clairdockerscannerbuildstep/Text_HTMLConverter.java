package org.jenkinsci.plugins.clairdockerscannerbuildstep;

import hudson.Launcher;
import hudson.EnvVars;
import hudson.Launcher.ProcStarter;
import hudson.FilePath;
import hudson.model.AbstractBuild;
import hudson.model.BuildListener;
import hudson.util.ArgumentListBuilder;










import hudson.util.ChartUtil.NumberOnlyBuildLabel;


import hudson.util.DataSetBuilder;








//import org.apache.http.HttpResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;

//import org.apache.http.HttpResponse;





















import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import hudson.model.Computer;
import hudson.model.ListView.Listener;
import hudson.remoting.Callable;
import hudson.remoting.Channel;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.slaves.Channels;

import java.net.*;

/**
 * This class does the actual execution..
 * 
 */
public class Text_HTMLConverter {
	static int countHigh = 0;
	static int countMed = 0;
	static int countLow = 0;
	static int countNeg = 0;
	static int total;
	static int perHigh;
	static int perMed;
	static int perLow;
	static int perNeg;
	static String FILENAME=null;
	@SuppressWarnings("deprecation")
	public static FilePath text_to_html(File outFile, FilePath outfilFilePath1, PrintStream out, AbstractBuild build, BuildListener listener)
			throws IOException, InterruptedException {
		EnvVars env = build.getEnvironment(listener);
		//System.out.println("list of env"+env);
		
		System.out.println("Build_Number"+env.get("BUILD_NUMBER"));
				String build_no=env.get("BUILD_NUMBER"); 
		File file = new File(outFile.toString());
		String content = new Scanner(new File(outFile.toString())).useDelimiter("\\Z").next();

		FileReader fileReader = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(fileReader);

		String line = null;
		System.setProperty("hudson.model.DirectoryBrowserSupport.CSP", "");

		List<String> list = new ArrayList<String>();
		list.removeAll(Arrays.asList("", null));

		while ((line = bufferedReader.readLine()) != null) {
			list.add(line);
		}

		String[] stringArr = list.toArray(new String[0]);

		for (int j = 0; j < stringArr.length; j++) {
			stringArr[j] = stringArr[j].trim();
		}

		fileReader.close();

		String[] value = stringArr[0].split("Clair report for image ");
		String[] value1 = value[1].split(" ");

		out.println("<!doctype html>");
		out.println(
				"<div id=Heading style=\"background:#33AFFF ;\"><h2 align=\"center\" style=\"color:white;\"><b>Clair Report - "
						+ value1[0] + "<br></b></h2>");
		out.println("</div");

		out.println("<html lang = \"en\">");
		out.println("<head>");
		out.println("<meta charset = \"utf-8\">");
		out.println("<title>Clair Inputs</title>");

		out.println(
				"<link href = \"https://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css\"\" rel = \"stylesheet\">");
		out.println("<script src = \"https://code.jquery.com/jquery-1.10.2.js\"></script>");
		out.println("<script src = \"https://code.jquery.com/ui/1.10.4/jquery-ui.js\"></script>");
		out.println();

		out.println("<script>");
		out.println("$(function() {");
		out.println("$( \"#tabs-1\" ).tabs();");
		out.println("});");
		out.println("</script>");

		out.println(
				"<script type=\"text/javascript\">$(document).ready(function() { $(\"button\").click(function(e) {e.preventDefault();");
		out.println(" var data_type = \'data:application/vnd.ms-excel\';");
		out.println(
				"var table_div = document.getElementById('tabs-2'); var table_div1 = document.getElementById('tabs-3');var table_div2 = document.getElementById('tabs-4');");
		out.println("  var table_div3 = document.getElementById('tabs-5');var table_html = table_div.outerHTML.replace(/ /g, '%20');var table_html1 = table_div1.outerHTML.replace(/ /g, '%20');"
						+ "var table_html2 = table_div2.outerHTML.replace(/ /g, '%20'); var table_html3 = table_div3.outerHTML.replace(/ /g, '%20');"
						+"var a = document.createElement('a');a.href = data_type + ', ' + table_html+table_html1+table_html2+table_html3;a.download = 'Clair_Report' + '.xls';"
						+ " a.click(); });});");

		out.println("</script>");

		out.println("<style>");

		out.println("#tabs-1{font-size: 14px;}");
		out.println(".ui-widget-header {");
		out.println("background:#33AFFF;");

		out.println("border: 1px solid #33AFFF;");
		out.println("color: #FFFFFF;");
		out.println("font-weight: bold;");
		out.println("font-family: Helvetica, Arial, sans-serif;");
		out.println("}");
		out.println("</style>");

		out.println("<style>");

		out.println("table {");
		out.println("border-collapse: collapse;");
		out.println("width: 100%;");
		out.println("font-family: Helvetica, Arial, sans-serif;");
		out.println("}");
		out.println("");

		out.println("th, td {");
		out.println("text-align: left;");
		out.println("padding: 8px;");
		out.println("}");
		out.println("");

		out.println("tr:nth-child(even){background-color: #f2f2f2}");
		out.println("");

		out.println("th {");
		out.println("text-align: left;");
		out.println("background-color: #33AFFF;");
		out.println("color: white; font-weight: bold;");
		out.println("}");
		out.println("</style>");
		out.println("<style>");
		out.println(
				".button { display: inline-block; padding: 10px 20px;font-size: 16px; cursor: pointer; text-align: center;text-decoration: none; outline: none;color: #fff;background-color: #33AFFF;"
						+ " border: none; border-radius: 14px; box-shadow: 0 5px #999; float: right;	}");

		out.println(".button:hover {background-color: #33AFFF}"
				+ ".button:active { background-color: #33AFFF; box-shadow: 0 3px #666; transform: translateY(4px);}");
		out.println("</style>");
		out.println("</head>");
		out.println("");

		String pattern_severity = "High|Medium|Low|Negligible";
		Pattern r = Pattern.compile(pattern_severity);

		String pattern_id = "(^[CVE-]+[0-9]{4}-[0-9]{4})";
		Pattern r1 = Pattern.compile(pattern_id);

		String desc = "(^[CVE]+[-].*)";
		Pattern strDesc = Pattern.compile(desc);

		String pattern_pack = "(^Package:.*)";
		Pattern r2 = Pattern.compile(pattern_pack);

		String pattern_link = "(^Link:.*)";
		Pattern r3 = Pattern.compile(pattern_link);

		String pattern_layer = "(^Layer:.*)";
		Pattern r4 = Pattern.compile(pattern_layer);

		Matcher m = null;
		Matcher m1 = null;
		Matcher m2 = null;
		Matcher m3 = null;
		Matcher m4 = null;
		Matcher d = null;

		int i = 1;

		String str_ID[] = new String[stringArr.length];
		String str_Pack[] = new String[stringArr.length];
		String str_Link[] = new String[stringArr.length];
		String str_Layer[] = new String[stringArr.length];
		String str_Severe[] = new String[stringArr.length];

		String str = null;

		while (i <= stringArr.length - 1) {
			m = r.matcher(stringArr[i]);
			m1 = r1.matcher(stringArr[i]);
			m2 = r2.matcher(stringArr[i]);
			m3 = r3.matcher(stringArr[i]);
			m4 = r4.matcher(stringArr[i]);
			d = strDesc.matcher(stringArr[i]);

			if (d.find()) {
				String strNew = d.group();
				content = content.replace(strNew, "<td>");
			}

			if (m1.find()) {
				str_ID[i] = m1.group();
			}

			if (m.find()) {
				str_Severe[i] = m.group();
			}

			if (m2.find()) {
				str = m2.group();
				content = content.replace(str, "</td>");
				str = str.replaceAll("Package: ", "");
				str_Pack[i] = str;
			}

			if (m3.find()) {
				str = m3.group();
				content = content.replace(stringArr[i], "");
				str = str.replaceAll("Link: ", "");
				str_Link[i] = str;
			}

			if (m4.find()) {
				str = m4.group();
				content = content.replace(str, "");
				str = str.replaceAll("Layer: ", "");
				str_Layer[i] = str;
			}
			i++;
		}

		String[] arr1 = content.split("<td>");

		String strNew = "";
		for (int j = 1; j < arr1.length; j++) {
			strNew = strNew.concat(arr1[j]);
		}

		String[] arr2 = strNew.split("</td>");

		String str_Desc[] = new String[str_ID.length];
		str_Desc = arr2;

		ArrayList<String> listID = new ArrayList<String>();
		for (String s : str_ID)
			if (s != null)
				listID.add(s);

		ArrayList<String> listSevere = new ArrayList<String>();
		for (String s : str_Severe)
			if (s != null)
				listSevere.add(s);

		ArrayList<String> listPack = new ArrayList<String>();
		for (String s : str_Pack)
			if (s != null)
				listPack.add(s);

		ArrayList<String> listLink = new ArrayList<String>();
		for (String s : str_Link)
			if (s != null)
				listLink.add(s);

		str_ID = listID.toArray(new String[listID.size()]);
		str_Severe = listSevere.toArray(new String[listSevere.size()]);
		str_Pack = listPack.toArray(new String[listPack.size()]);
		str_Link = listLink.toArray(new String[listLink.size()]);

		out.println("<body>");
		out.println("<button class=\"button\">Export to Excel</button><br></br>");
		out.println("<h4 style=\"color:#33AFFF ;\"><u><b>Severity Summary</b></u></h4>");
		out.println("<table style=\"width:50%; font-size: 14px;\">");
		out.println("<tr>");
		out.println("<th>High</th>");
		out.println("<th>Medium</th>");
		out.println("<th>Low</th>");
		out.println("<th>Negligible</th>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td><div class=\"high-count\"></div></td>");
		out.println("<td><div class=\"med-count\"></div></td>");
		out.println("<td><div class=\"low-count\"></div></td>");
		out.println("<td><div class=\"neg-count\"></div></td>");
		out.println("</tr>");
		out.println("");

		out.println("</table>");
		out.println("<br><br>");
		out.println("<div id=\"donutchart\" style=\"width: 800px; height: 300px;\"></div>");
		out.println("<p></p>");
		out.println("<p></p>");

		out.println("<div id = \"tabs-1\">");
		out.println("<ul>");
		out.println("<li><a href = \"#tabs-2\">High</a></li>");
		out.println("<li><a href = \"#tabs-3\">Medium</a></li>");
		out.println("<li><a href = \"#tabs-4\">Low</a></li>");
		out.println("<li><a href = \"#tabs-5\">Negligible</a></li>");
		out.println("</ul>");

		out.println("<div id = \"tabs-2\">");
		out.println("<table>");
		out.println("<tr>");
		out.println("<th align=\"center\">ID</th>");
		out.println("<th align=\"center\">Description</th>");
		out.println("<th align=\"center\">Package</th>");
		out.println("<th align=\"center\">Link</th>");
		out.println("</tr>");

		int j = 0;
		while (j < str_ID.length) {
			if (str_Severe[j].equals("High")) {
				out.println("<tr>");
				out.println("<td width=\"10%\">" + "<font color=\"#b30000  \">" + str_ID[j] + "</font>" + "</td>");
				out.println("<td width=\"45%\">" + str_Desc[j] + "</td>");
				out.println("<td width=\"20%\">" + str_Pack[j] + "</td>");
				out.println("<td><a href=\"" + str_Link[j] + "\">" + str_Link[j] + "</a></td>");
				out.println("</tr>");
				countHigh++;
			}
			j++;
		}

		out.println("</table>");
		out.println("");
		out.println("</div>");
		out.println("<div id = \"tabs-3\">");
		out.println("<table>");
		out.println("<tr>");
		out.println("<th align=\"center\">ID</th>");
		out.println("<th align=\"center\">Description</th>");
		out.println("<th align=\"center\">Package</th>");
		out.println("<th align=\"center\">Link</th>");
		out.println("</tr>");

		j = 0;
		while (j < str_ID.length) {
			if (str_Severe[j].equals("Medium")) {
				out.println("<tr>");
				out.println("<td width=\"10%\">" + "<font color=\"#ff6600 \">" + str_ID[j] + "</font>" + "</td>");
				out.println("<td width=\"45%\">" + str_Desc[j] + "</td>");
				out.println("<td width=\"20%\">" + str_Pack[j] + "</td>");
				out.println("<td><a href=\"" + str_Link[j] + "\">" + str_Link[j] + "</a></td>");
				out.println("</tr>");
				countMed++;
			}
			j++;
		}
		out.println("</table>");

		out.println("</div>");
		out.println("<div id = \"tabs-4\">");
		out.println("<table>");
		out.println("<tr>");
		out.println("<th>ID</th>");
		out.println("<th>Description</th>");
		out.println("<th>Package</th>");
		out.println("<th>Link</th>");
		out.println("</tr>");

		j = 0;
		while (j < str_ID.length) {
			if (str_Severe[j].equals("Low")) {
				out.println("<tr>");
				out.println("<td width=\"10%\">" + "<font color=\"#666666\">" + str_ID[j] + "</font>" + "</td>");
				out.println("<td width=\"45%\">" + str_Desc[j] + "</td>");
				out.println("<td width=\"20%\">" + str_Pack[j] + "</td>");
				out.println("<td><a href=\"" + str_Link[j] + "\">" + str_Link[j] + "</a></td>");
				out.println("</tr>");
				countLow++;
			}
			j++;
		}

		out.println("</table>");
		out.println("</div>");
		out.println("<div id = \"tabs-5\">");
		out.println("<table>");
		out.println("<tr>");
		out.println("<th>ID</th>");
		out.println("<th>Description</th>");
		out.println("<th>Package</th>");
		out.println("<th>Link</th>");
		out.println("</tr>");

		j = 0;
		while (j < str_ID.length) {
			if (str_Severe[j].equals("Negligible")) {
				out.println("<tr>");
				out.println("<td width=\"10%\">" + "<font color=\"#000000\">" + str_ID[j] + "</font>" + "</td>");
				out.println("<td width=\"45%\">" + str_Desc[j] + "</td>");
				out.println("<td width=\"20%\">" + str_Pack[j] + "</td>");
				out.println("<td><a href=\"" + str_Link[j] + "\">" + str_Link[j] + "</a></td>");
				out.println("</tr>");
				countNeg++;
			}
			j++;
		}

		out.println("</table>");
		out.println("</div>");
		out.println("</div>");
		out.println("</body>");

		out.println("<script type=\"text/javascript\">");
		out.println("$('div.high-count').text('" + countHigh + "');");
		out.println("$('div.med-count').text('" + countMed + "');");
		out.println("$('div.low-count').text('" + countLow + "');");
		out.println("$('div.neg-count').text('" + countNeg + "');");
		out.println("</script>");

		out.println("<script type=\"text/javascript\" src=\"https://www.gstatic.com/charts/loader.js\"></script>");
		out.println("<script type=\"text/javascript\">");
		out.println("google.charts.load(\"current\", {packages:[\"corechart\"]});"
				+ "google.charts.setOnLoadCallback(drawChart);" + "function drawChart() {");
		out.println("var data = google.visualization.arrayToDataTable([" + "['Type', 'Vulnarability']," + "['High', "
				+ countHigh + "]," + "['Medium'," + countMed + "]," + "['Low'," + countLow + "]," + " ['Negligible', "
				+ countNeg + "]]);");

		out.println("var options = { legend: 'right', title: 'Severity Graph', titleTextStyle :{color:'#33AFFF',fontName:'Helvetica',fontSize:15}, chartArea : { left: 20 }, pieHole: 0.4,};");

		out.println("  var chart = new google.visualization.PieChart(document.getElementById('donutchart'));");
		out.println("chart.draw(data, options); }");
		out.println("  </script>");

		out.println("</html>");
		out.println("");
		out.println("");
		System.out.println("High value" + countHigh);
		System.out.println("Medium" + countMed);
		System.out.println("Low" + countLow);
		System.out.println("Neg" + countNeg);

		Values_File.valuesFile(countHigh,countMed,countLow,countNeg,build_no,build); 
		out.close();
		return outfilFilePath1;

	}
	 public static JFreeChart buildChart(AbstractBuild<?, ?> build) throws IOException, InterruptedException{
		  JFreeChart lineChart = null;
		  System.out.println("FILE NAME IS "+Text_HTMLConverter.FILENAME);
	
    lineChart = ChartFactory.createLineChart(
             null,
             "Build Number","Number of Severities",
             createDataset(),
             PlotOrientation.VERTICAL,
             true,true,false);
	
return lineChart;
	 }
     public static CategoryDataset createDataset() throws IOException   {
    	 
    	 FILENAME="D://values.properties";
    	 Properties props=new Properties();
    	 FileInputStream fis = new FileInputStream(FILENAME);
    	 props.load(fis);
    	 Set<Object> keys = props.keySet();
    	 List<Object> listKeys=new ArrayList<Object>();
    	 listKeys.addAll(keys);
    	 int sizeOfLoop=keys.size();
    	 System.out.println("Size of loop: "+sizeOfLoop);

    	 DataSetBuilder builder = new DataSetBuilder();
    	
    	 for (int i = 0; i < sizeOfLoop; i++) {
    		String value= props.getProperty((String) listKeys.get(i)).trim();
    		int key=Integer.parseInt((String) listKeys.get(i));
    		String[] array= value.split("\\,");
    		//System.out.println("Array is: "+array);
    		for(int j=0;j<4;j++){
    			int high=Integer.parseInt(array[0].trim());
    			int med=Integer.parseInt(array[1].trim());
    			int low=Integer.parseInt(array[2].trim());
    			int neg=Integer.parseInt(array[3].trim());
    		builder.add(high,"High",key);
    		//System.out.println("This: "+high+"High"+key);
    		builder.add(med,"Medium",key);
    		builder.add(low,"Low",key);
    		builder.add(neg,"Negligible",key);
    		}
    		fis.close();
            
		}
      
         	System.out.println("Inside datset");
         	
            return builder.build();
		
     }
	 //second chart stacked graph
     public static JFreeChart buildChartDelta(AbstractBuild<?, ?> build) throws IOException, InterruptedException{
		  JFreeChart lineChart = null;
		  System.out.println("FILE NAME IS "+Text_HTMLConverter.FILENAME);
	
   lineChart = ChartFactory.createStackedAreaChart(
            null,
            "Build Number","Total Count",
            createDataset1(),
            PlotOrientation.VERTICAL,
            true,true,false);
	
    
return lineChart;
	 }
public static CategoryDataset createDataset1() throws IOException   {
    	 
    	 FILENAME="D://values.properties";
    	 Properties props=new Properties();
    	 FileInputStream fis = new FileInputStream(FILENAME);
    	 props.load(fis);
    	 Set<Object> keys = props.keySet();
    	 List<Object> listKeys=new ArrayList<Object>();
    	 listKeys.addAll(keys);
    	 int sizeOfLoop=keys.size();
    	 System.out.println("Size of loop: "+sizeOfLoop);

    	 DataSetBuilder builder = new DataSetBuilder();
    	
    	 for (int i = 0; i < sizeOfLoop; i++) {
    		String value= props.getProperty((String) listKeys.get(i)).trim();
    		int key=Integer.parseInt((String) listKeys.get(i));
    		String[] array= value.split("\\,");
    		//System.out.println("Array is: "+array);
    		//for(int j=0;j<array.length;j++){
    			int total=Integer.parseInt(array[4].trim());
    		builder.add(total,"Total Count",key);
    		//}
    		fis.close();
            
		}
       /*  builder.add(13, "Sevrity", 1);
         builder.add(11, "Sevrity",2);*/
         	//System.out.println("Inside datset");
         	
            return builder.build();
		
     }
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
	public static boolean checkQualityGate(int high, int low, int medium, Boolean Severity, AbstractBuild build) {
		System.out.println(countHigh + "," + Text_HTMLConverter.countMed + "," + Text_HTMLConverter.countLow);
		boolean result = false;
		int c = 0;
		if (Severity != false) {
			if (countHigh > high || countLow > low || countMed > medium) {
				result = true;
			}

		}
		countHigh = 0;
		countLow = 0;
		countMed = 0;
		countNeg = 0;
		return result;
	}
}
