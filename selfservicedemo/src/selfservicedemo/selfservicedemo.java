package selfservicedemo;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;



import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonWriter;

import java.io.FileWriter;


public class selfservicedemo {
public static void main(String[] args) throws FileNotFoundException, IOException {
	Scanner scan = new Scanner(System.in);
	String scmType = scan.next();
	String repoName=scan.next();
	JsonParser parser = new JsonParser();
	JsonWriter writer;
	try
	{
    File jsonFile = new File("D:\\apache-tomcat-7.0.53\\webapps\\Form1\\data\\repoinformation\\branchCIData.json");
    String jsonStr = FileUtils.readFileToString(jsonFile);
    JsonElement jelemnt= new JsonParser().parse(jsonStr);
    JsonObject jsonobject= jelemnt.getAsJsonObject();
    JsonObject repo = (JsonObject) jsonobject.get("REPOSITORIES_NAMES");
	
     JsonArray repoarry=new JsonArray();
	repoarry = (JsonArray) repo.get(scmType);
	String jsonString = repoarry.toString();
	//System.out.println(jsonString);
	JsonObject obj= new JsonObject();
	obj.addProperty("name", scmType+"-repo-"+repoName);
	obj.addProperty("value", scmType.toUpperCase()+"-Repository-"+repoName.toUpperCase());
	repoarry.add(obj);
	System.out.println(repoarry);
	//Gson gson=new Gson();
	
	Gson gson=new GsonBuilder().setPrettyPrinting().create();
	String resultingJson = gson.toJson(jelemnt);
	FileUtils.writeStringToFile(jsonFile, resultingJson);
	}
		

catch(Exception e)
{
e.printStackTrace();	
}
}
}

