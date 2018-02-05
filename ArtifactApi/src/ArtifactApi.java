import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import org.apache.commons.codec.binary.Base64;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;
import com.mysql.jdbc.ResultSetMetaData;





import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;


/*import org.json.JSONException;
 import org.json.JSONObject;
 import org.json.JSONString;*/
import sun.net.www.http.HttpClient;

public class ArtifactApi {

	public static void main(String[] args) throws IOException, ParseException,
			SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		int count = 0;
		URL url = new URL("http://35.164.106.34:8081/artifactory/api/storage/"
				+ args[0]);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");
		if (conn.getResponseCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ conn.getResponseCode());
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));

		String output;
		StringBuffer response = new StringBuffer();
		System.out.println("Output from Server .... \n");
		while ((output = br.readLine()) != null) {
			// System.out.println(output);
			response.append(output);
		}

		conn.disconnect();
		String data = response.toString();
		System.out.println(data);

		ArrayList<String> list = new ArrayList();
		JSONParser parser = new JSONParser();
		// org.json.simple.JSONArray jsonArray= (org.json.simple.JSONArray)
		// parser.parse(data);
		String children = null;
		String art_url=null;
		Object JSONObject = parser.parse(response.toString());
		JSONArray jsonarray = new JSONArray();
		jsonarray.add(JSONObject);
		System.out.println("JSONArray is : " + jsonarray);
		Iterator itr = jsonarray.iterator();
		while (itr.hasNext()) {
			// System.out.println(itr.next());
			org.json.simple.JSONObject object = (org.json.simple.JSONObject) itr
					.next();
			if (object.get("children") != null) {
				children = object.get("children").toString();

			}
			if (object.get("uri") != null) {
				art_url = object.get("uri").toString();

			}
			System.out.println("children is " + children);
			System.out.println("url is "+art_url);
		}

		JSONParser parser1 = new JSONParser();
		JSONArray jsonarray1 = (JSONArray) parser1.parse(children);
		String artifact_name = null;
		System.out.println("JSONArray1 is : " + jsonarray1);
		Iterator itr1 = jsonarray1.iterator();
		while (itr1.hasNext()) {

			org.json.simple.JSONObject object = (org.json.simple.JSONObject) itr1
					.next();
			if (object.get("uri") != null) {
				artifact_name = object.get("uri").toString().replace("/", "");

			}
			System.out.println(artifact_name);
			list.add(artifact_name);

		}
		System.out.println(list);
		/*String driver = "com.mysql.jdbc.Driver";
		String dburl = "jdbc:mysql://localhost:3306/artdb";
		String uname = "root";
		String pass = "root123";
		Class.forName(driver);
		System.out.println("Connecting to a selected database...");
		// Setup the connection with the DB
		Connection dbconn = DriverManager.getConnection(dburl, uname, pass);
		System.out.println("Connected database successfully...");
		Statement st = dbconn.createStatement();
		for (int i = 0; i < list.size(); i++) {
			// System.out.println(list.get(i));
			ResultSet rs = st
					.executeQuery("select count(*) from artifactory where artifact_name="
							+ "'" + list.get(i) + "'");
			while (rs.next()) {
				count = rs.getInt(1);
				// System.out.println(count);
			}
			if (count == 0) {
				System.out.println(list.get(i));

				System.out.println("new artifact name inserted");
				st.execute("INSERT INTO artifactory (artifact_name)values("
						+ "'" + list.get(i) + "'" + ")");
			} else
				System.out.println("no new artifacts found");*/

		}
		

		}
		       

	


