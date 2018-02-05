import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import org.json.JSONArray;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class AssignPrj {
	
	Properties p = new Properties();
	InputStream input = null;
	public static void main(String[] args) throws Exception {
		String ProjKey = args[0];
		String GateName = args[1];
		AssignPrj obj = new AssignPrj();
		String PrjId = obj.GetPrjId(ProjKey);
		int GateId = obj.GetGate(GateName);
		obj.GetPrjId(ProjKey);
		obj.GetGate(GateName);
		obj.GetProject(PrjId, GateId);

	}


	public String GetPrjId(String ProjKey) throws Exception {
		
		String PrjId = null;
		try {
			input = new FileInputStream("config.properties");
			p.load(input);
			String name = p.getProperty("name");
			String password = p.getProperty("password");
			String authString = name + ":" + password;
			String Prjurl = p.getProperty("Prjurl");
			byte[] encodedBytes = Base64.encodeBase64(authString.getBytes());
			String encoded = new String(encodedBytes);
			URL obj = new URL(Prjurl);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			con.setRequestMethod("GET");
			con.setRequestProperty("Authorization", "Basic " + encoded);

			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'GET' requests to URL : " + Prjurl);

			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputline;
			StringBuffer response = new StringBuffer();
			while ((inputline = in.readLine()) != null) {
				response.append(inputline);
			}
			System.out.println(response);
			in.close();
			ArrayList<String> list = new ArrayList<>();
            

			JSONParser parser = new JSONParser();
			Object JSONObject = parser.parse(response.toString());
			org.json.simple.JSONArray jsonarray = (org.json.simple.JSONArray) JSONObject;
			System.out.println("JSONArray is : " + jsonarray);
			Iterator itr = jsonarray.iterator();
			while (itr.hasNext()) {
				org.json.simple.JSONObject object = (org.json.simple.JSONObject) itr
						.next();
				//if (object.get("k").equals(ProjKey)) {
				//PrjId = (String) object.get("id");
				//	System.out.print(PrjId);
				//}
				
				if (object.get("nm") != null) {
					PrjId = (String) object.get("nm");
				    list.add(PrjId);
						
					}
				
			}
			System.out.println("List of project is"+list);		
			

		} catch (IOException e) {
			e.printStackTrace();
		}
		return PrjId;
	}

	public int GetGate(String GateName) throws Exception {
		
		int GateId = 0;
		try {
			input = new FileInputStream("config.properties");
			p.load(input);
			String name = p.getProperty("name");
			String password = p.getProperty("password");
			String authString = name + ":" + password;
			String Sonarurl = p.getProperty("Sonarurl");
			byte[] encodedBytes = Base64.encodeBase64(authString.getBytes());
			String encoded = new String(encodedBytes);
			
			URL obj = new URL(Sonarurl);

			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Authorization", "Basic " + encoded);
			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'GET' requests to URL : " + Sonarurl);
			// System.out.println("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputline;
			StringBuffer response = new StringBuffer();
			while ((inputline = in.readLine()) != null) {
				response.append(inputline);
			}
			in.close();
			// System.out.println(response.toString());
			JSONParser parser = new JSONParser();
			org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) parser
					.parse(response.toString());
			org.json.simple.JSONArray jsonarray = (org.json.simple.JSONArray) jsonObject
					.get("qualitygates");
			System.out.println("Quality Gate List : " + jsonarray);
			Iterator itr = ((List) jsonarray).iterator();
			while (itr.hasNext()) {
				org.json.simple.JSONObject object = (org.json.simple.JSONObject) itr
						.next();
				if (object.get("name").equals(GateName)) {
					GateId = Integer.parseInt(object.get("id").toString());
					// GateId=object.get("id");
					System.out.print(GateId);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return GateId;

	}

	public void GetProject(String PrjId, int GateId) throws Exception {
		
		try {
			input = new FileInputStream("config.properties");
			p.load(input);
			String name = p.getProperty("name");
			String password = p.getProperty("password");
			String authString = name + ":" + password;
			String finalurl = p.getProperty("finalurl");
			byte[] encodedBytes = Base64.encodeBase64(authString.getBytes());
			String encoded = new String(encodedBytes);
			
			URL obj = new URL(finalurl);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			con.setRequestMethod("POST");
			con.setRequestProperty("Authorization", "Basic " + encoded);
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

			String urlParameters = "gateId=" + GateId + "&projectId=" + PrjId;

			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();

			int responseCode = con.getResponseCode();

			System.out.println("\nSending 'POST' request to URL : " + finalurl);
			System.out.println("Post parameters : " + urlParameters);
			System.out.println("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
