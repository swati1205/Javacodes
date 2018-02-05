
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.util.*;

public class SelfService {
public static void main(String[] args) throws FileNotFoundException, IOException, ParseException, JSONException {
		// TODO Auto-generated method stub
//String RepoName=args[0];
//String SCMType=args[1];
//String filePath= "D:\\apache-tomcat-7.0.53\\webapps\\demo.json";
SelfService obj=new SelfService();
//obj.Copydata(RepoName,SCMType,filePath);
obj.Copydata();
		}
public static void Copydata() throws FileNotFoundException, IOException, ParseException, JSONException
{
	JSONParser parser=new JSONParser();
	Object obj=parser.parse(new FileReader("D:\\apache-tomcat-7.0.53\\webapps\\demo.json"));
	JSONObject jsonObject=(JSONObject) obj;
	String name=(String) jsonObject.get("name");
	System.out.println("Name :" +name);
}
}
