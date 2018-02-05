import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextToHtml {
                public static void main(String[] args) throws Exception {

                                PrintStream out = new PrintStream(new FileOutputStream("C:\\Users\\514347\\Desktop\\output.html"));


                                try {

                                                File file = new File("C:\\Users\\514347\\Desktop\\clairinput.txt");
                                                FileReader fileReader = new FileReader(file);
                                                BufferedReader bufferedReader = new BufferedReader(fileReader);
                                                
                                                String line = null;

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
                                                String[] value1=value[1].split(" ");
                                                
                                                
                                                out.println("<!doctype html>");
                                                out.println("<h2 align=\"center\" style=\"color:red;\"><b><u><a href=\"javascript:window.location.reload();\">IMAGE=\""  +value1[0]+"\"</a></u></b>");
                                                out.print("</h2>");

                                                out.println("<html lang = \"en\">");
                                                out.println("<head>");
                                                out.println("<meta charset = \"utf-8\">");
                                                out.println("<title>Clair Inputs</title>");
                                                out.println("<link href = \"https://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css\"\" rel = \"stylesheet\">");
                                                out.println("<script src = \"https://code.jquery.com/jquery-1.10.2.js\"></script>");
                                                out.println("<script src = \"https://code.jquery.com/ui/1.10.4/jquery-ui.js\"></script>");
                                                out.println();
                                                
                                                out.println("<script>");
                                                out.println("$(function() {");
                                                out.println("$( \"#tabs-1\" ).tabs();");
                                                out.println("});");
                                                out.println("</script>");
                                                
                                                out.println("<style>");
                                                out.println("#tabs-1{font-size: 14px;}");
                                                out.println(".ui-widget-header {");
                                                out.println("background:#b9cd6d;");
                                                out.println("border: 1px solid #b9cd6d;");
                                                out.println("color: #FFFFFF;");
                                                out.println("font-weight: bold;");
                                                out.println("}");
                                                out.println("</style>");
                                                
                                                out.println("<style>");
                                                out.println("table {");
                                                out.println("border-collapse: collapse;");
                                                out.println("width: 100%;");
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
                                                out.println("background-color: #4CAF50;");
                                                out.println("color: white;");
                                                out.println("}");
                                                out.println("</style>");
                                                out.println("</head>");
                                                out.println("");
                                                
                                                String pattern_severity="High|Medium|Low|Negligible";
                                                Pattern r=Pattern.compile(pattern_severity);

                                                String pattern_id = "(^[CVE-]+[0-9]{4}-[0-9]{4})";
                                                Pattern r1 = Pattern.compile(pattern_id);

                                                String pattern_pack = "(^Package:.*)";
                                                Pattern r2 = Pattern.compile(pattern_pack);

                                                String pattern_link = "(^Link:.*)";
                                                Pattern r3 = Pattern.compile(pattern_link);

                                                String pattern_layer = "(^Layer:.*)";
                                                Pattern r4 = Pattern.compile(pattern_layer);

                                                Matcher m=null;
                                                Matcher m1 = null;
                                                Matcher m2 = null;
                                                Matcher m3 = null;
                                                Matcher m4 = null;                                          
                                                
                                                int i=1;
                                                int countHigh=0;
                                                int countMed=0;
                                                int countLow=0;
                                                int countNeg=0;
                                                
                                                String str_ID[]=new String[stringArr.length];       
                                                String str_Pack[]=new String[stringArr.length];
                                                String str_Link[]=new String[stringArr.length];
                                                String str_Layer[]=new String[stringArr.length];
                                                String str_Severe[]=new String[stringArr.length];
                                                
                                                String str=null;
                                                
                                                
                                                while(i<=stringArr.length-1)
                                                {
                                                                m  =  r.matcher(stringArr[i]);
                                                                m1 = r1.matcher(stringArr[i]);
                                                                m2 = r2.matcher(stringArr[i]);
                                                                m3 = r3.matcher(stringArr[i]);
                                                                m4 = r4.matcher(stringArr[i]);
                                                                
                                                                if(m1.find())
                                                                {
                                                                                str_ID[i]=m1.group();
                                                                                //System.out.println(str_ID[i]);
                                                                }
                                                                
                                                                if(m.find())
                                                                {
                                                                                str_Severe[i]=m.group();
                                                                                //System.out.println(str_Severe[i]);
                                                                }
                                                                
                                                                if(m2.find())
                                                                {
                                                                                str=m2.group();
                                                                                str=str.replaceAll("Package: ", "");           
                                                                                str_Pack[i]=str;
                                                                                //System.out.println(str_Pack[i]);
                                                                }
                                                                
                                                                if(m3.find())
                                                                {
                                                                                str=m3.group();
                                                                                str=str.replaceAll("Link: ", "");
                                                                                str_Link[i]=str;
                                                                                //System.out.println(str_Link[i]);                                                                             
                                                                }                                                                              
                                                                i++;
                                                }
                                                
                                                str_ID = Arrays.stream(str_ID).filter(s -> (s != null && s.length() > 0)).toArray(String[]::new);
                                                str_Severe = Arrays.stream(str_Severe).filter(s -> (s != null && s.length() > 0)).toArray(String[]::new);
                                                str_Pack = Arrays.stream(str_Pack).filter(s -> (s != null && s.length() > 0)).toArray(String[]::new);
                                                str_Link = Arrays.stream(str_Link).filter(s -> (s != null && s.length() > 0)).toArray(String[]::new);

                                                                                
                                                for(int j=0;j<str_ID.length;j++)
                                                {
                                                                System.out.println(str_ID[j]+"     "+str_Severe[j]+"     "+str_Pack[j]+"     "+str_Link[j]);
                                                }

                                                out.println("<body>");
                                                out.println("<h4>Summary</h4>");
                                                out.println("<table>");
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
                                                
                                                out.println("");
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
                                                out.println("<th>ID</th>");
                                                out.println("<th>Description</th>");
                                                out.println("<th>Package</th>");
                                                out.println("<th>Link</th>");
                                                out.println("</tr>");
                                                
                                                int j=0;
                                                while(j<str_ID.length)
                                                {
                                                                if(str_Severe[j].equals("High"))
                                                                {
                                                                out.println("<tr>");
                                                                out.println("<td>"+str_ID[j]+"</td>");
                                                                out.println("<td></td>");
                                                                out.println("<td>"+str_Pack[j]+"</td>");
                                                                out.println("<td>"+str_Link[j]+"</td>");
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
                                                out.println("<th>ID</th>");
                                                out.println("<th>Description</th>");
                                                out.println("<th>Package</th>");
                                                out.println("<th>Link</th>");
                                                out.println("</tr>");
                                                
                                                j=0;
                                                while(j<str_ID.length)
                                                {
                                                                if(str_Severe[j].equals("Medium"))
                                                                {
                                                                out.println("<tr>");
                                                                out.println("<td>"+str_ID[j]+"</td>");
                                                                out.println("<td></td>");
                                                                out.println("<td>"+str_Pack[j]+"</td>");
                                                                out.println("<td>"+str_Link[j]+"</td>");
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
                                                
                                                j=0;
                                                while(j<str_ID.length)
                                                {
                                                                if(str_Severe[j].equals("Low"))
                                                                {
                                                                out.println("<tr>");
                                                                out.println("<td>"+str_ID[j]+"</td>");
                                                                out.println("<td></td>");
                                                                out.println("<td>"+str_Pack[j]+"</td>");
                                                                out.println("<td>"+str_Link[j]+"</td>");
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
                                                
                                                j=0;
                                                while(j<str_ID.length)
                                                {
                                                                if(str_Severe[j].equals("Negligible"))
                                                                {
                                                                out.println("<tr>");
                                                                out.println("<td>"+str_ID[j]+"</td>");
                                                                out.println("<td></td>");
                                                                out.println("<td>"+str_Pack[j]+"</td>");
                                                                out.println("<td>"+str_Link[j]+"</td>");
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
                                                out.println("$('div.high-count').text('"+countHigh+"');");
                                                out.println("$('div.med-count').text('"+countMed+"');");
                                                out.println("$('div.low-count').text('"+countLow+"');");
                                                out.println("$('div.neg-count').text('"+countNeg+"');");
                                                out.println("</script>");
                                                
                                                out.println("</html>");
                                                out.println("");
                                                out.println("");
                                                
                                                
                                                out.close();
                                                
                                }

                                catch (IOException e) {
                                                e.printStackTrace();
                                }

                                catch (ArrayIndexOutOfBoundsException e1) {

                                }


                }

}
