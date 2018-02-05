import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
//import org.json.JSONException;
import java.util.Properties;
import java.io.FileInputStream;
import org.json.JSONObject;
import java.util.ArrayList;
import java.io.*;

//import javax.net.ssl.HttpsURLConnection;

public class ProjectClass {

	public static void main(String[] args) throws Exception {
		ProjectClass http = new ProjectClass();

		http.sendGet();
		http.sendPost();
		http.GetJson(null);
		
		
		
	}

	private static void GetJson(String data) throws Exception {
		// TODO Auto-generated method stub

		//JSONObject jsonObj = new JSONObject(jsonStr);
		//String name = jsonObj.getString("name");
		//System.out.println(name);
		// JSONObject json = new JSONObject();
		// int str=json.getInt("id");
		// System.out.println(json);

	}

	private void sendGet() throws Exception {
		// TODO Auto-generated method stub
		Scanner user_input = new Scanner(System.in);
		String ProjName;
		ProjName = user_input.next();
		String url1 = "http://localhost:9000/api/projects";
		String name = "admin";
		String password = "admin";
		String authString = name + ":" + password;
		byte[] encodedBytes = Base64.encodeBase64(authString.getBytes());
		String encoded = new String(encodedBytes);
		// System.out.println("Encoded string is :" + encoded);
		URL obj = new URL(url1);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("Authorization", "Basic " + encoded);

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url1);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(
				con.getInputStream()));
		String inputline;
		StringBuffer response = new StringBuffer();
		while ((inputline = in.readLine()) != null) {
			response.append(inputline);
		}
		in.close();
		// System.out.println(response.toString());
		String data = response.toString();
		System.out.println(data);

        
	}

	private void sendPost() throws Exception {
		// TODO Auto-generated method stub
		String url = "http://localhost:9000/api/qualitygates/select?gateId=3&projectId=8";
		String name = "admin";
		String password = "admin";
		String authString = name + ":" + password;
		byte[] encodedBytes = Base64.encodeBase64(authString.getBytes());
		String encoded = new String(encodedBytes);
		System.out.println("Encoded string is :" + encoded);
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setRequestMethod("POST");
		con.setRequestProperty("Authorization", "Basic " + encoded);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(
				con.getInputStream()));
		String inputline;
		StringBuffer response = new StringBuffer();
		while ((inputline = in.readLine()) != null) {
			response.append(inputline);

		}
		in.close();
		System.out.println(response.toString());
	}
}
