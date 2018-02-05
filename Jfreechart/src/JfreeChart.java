import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Paths;




public class JfreeChart {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		FileOutputStream fop = null;
		File file;

		
			file = new File("D:\\EclipseK\\Project\\Jfreechart\\src\\newfile.html");
			PrintStream out = new PrintStream(file);

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			out.println("<!doctype html>");
			out.println("<html>");
			out.println("<body>");
			out.println("<hr size=\"1\">");
			


			out.println("<div id=\"pie\"></div>");
			out.println("<script src=\"d3.min.js\"></script>");
			out.println("<script src=\"d3pie.js\"></script>");
			out.println("<script src=\"https://cdnjs.cloudflare.com/ajax/libs/d3/4.7.2/d3.min.js\"></script>");
		//	out.println("<script type=\"text/javascript\" src=\"D:\\d3pie-0.2.1\\d3pie-0.2.1\\d3pie\\d3pie.js\"></script>");
			
			
			out.println("<script>");
			out.println("var pie = new d3pie(\"pie\", {\"header\": {\"title\": {\"text\": \"Severity Graph\",\"color\": \"#272326\",\"fontSize\": 24},\"subtitle\": {\"color\": \"#999999\",\"fontSize\": 12,\"font\": \"open sans\"},\"location\": \"bottom-right\",\"titleSubtitlePadding\": 9},\"data\": {\"content\": [{\"label\": \"High\",\"value\": 12,\"color\": \"#f8151c\"},{\"label\": \"Medium\",\"value\": 10,\"color\": \"#e1ad37\"},{\"label\": \"Low\",\"value\": 38,\"color\": \"#76c53e\"},{\"label\": \"Negligible\",\"value\": 15,\"color\": \"#3d52e4\"}]},\"labels\": {\"outer\": {\"format\": \"label-value1\",\"pieDistance\": 32},\"mainLabel\": {\"fontSize\": 11},\"percentage\": {\"color\": \"#f8f8f8\",\"decimalPlaces\": 0},\"value\": {\"color\": \"#818188\",\"fontSize\": 11},\"lines\": {\"enabled\": true,\"style\": \"straight\"},\"truncation\": {\"enabled\": true}},\"effects\": {\"pullOutSegmentOnClick\": {\"effect\": \"linear\",\"speed\": 400,\"size\": 8}},\"misc\": {\"gradient\": {\"enabled\": true,\"percentage\": 100}},tooltips: {enabled: true,type: \"placeholder\",string: \"{label}, {value}, {percentage}%\"}});");
			out.println("</script>");
			out.println("<hr size=\"1\" />");
			out.println("</body>");
			out.println("</html>");
			//out.println("");
}
}