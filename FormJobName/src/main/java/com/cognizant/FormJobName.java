package com.cognizant;

import java.awt.List;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import javax.print.attribute.standard.JobName;

import org.apache.commons.codec.binary.Base64;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class FormJobName {

	public static void main(String[] args) throws IOException, ParseException, org.json.simple.parser.ParseException {
		// TODO Auto-generated method stub
		String name = "jira";
		String password = "jira123";
	 String issue_name=args[0];
		// String Jira_IP=args[2];
		// String Jenkins_IP=args[3];
		//Scanner sc = new Scanner(System.in);
		//String issue_name = sc.next();
		String authString = name + ":" + password;
		byte[] encodedBytes = Base64.encodeBase64(authString.getBytes());
		String encoded = new String(encodedBytes);

		// URL url = new URL("http://"+Jira_IP+"/rest/api/2/issue/" + args[0]);
		URL url = new URL("http://34.211.100.27:8080/rest/api/2/issue/" + issue_name);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");
		conn.setRequestProperty("Authorization", "Basic " + encoded);
		if (conn.getResponseCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
		}

		BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

		String output;
		StringBuffer response = new StringBuffer();

		while ((output = br.readLine()) != null) {
			response.append(output);
		}

		conn.disconnect();
		// String data = response.toString();
		// System.out.println(data);

		JSONParser parser = new JSONParser();
		String id = null;
		String key = null;
		String field = null;
		String custom_field = null;
		String value = null;
		String project = null;
		ArrayList<String> environment = new ArrayList<String>();
		StringBuilder jobname = new StringBuilder();
		Object JSONObject = parser.parse(response.toString());
		JSONArray jsonarray = new JSONArray();
		jsonarray.add(JSONObject);

		Iterator itr = jsonarray.iterator();
		while (itr.hasNext()) {
			org.json.simple.JSONObject object = (org.json.simple.JSONObject) itr.next();
			if (object.get("id") != null) {
				id = object.get("id").toString();

			}

			if (object.get("fields") != null) {
				field = object.get("fields").toString();

			}

			System.out.println("id is " + id);
			// System.out.println("key is "+ key);

			// System.out.println(" field is "+field);
		}
		JSONParser parser1 = new JSONParser();
		Object JSONObject1 = parser1.parse(field);
		JSONArray jsonarray1 = new JSONArray();
		jsonarray1.add(JSONObject1);
		// System.out.println("jsonarray 1 is "+jsonarray1);
		Iterator itr1 = jsonarray1.iterator();
		while (itr1.hasNext()) {
			JSONObject object = (JSONObject) itr1.next();

			if (object.get("customfield_10104") != null) {
				custom_field = object.get("customfield_10104").toString();

			}
			if (object.get("project") != null) {
				project = object.get("project").toString();

			}
			// System.out.println("custom_field is : " + project);
			// System.out.println("custom_field is : " + custom_field);
		}

		JSONParser parser2 = new JSONParser();

		Object JSONObject2 = parser2.parse(project);
		JSONArray jsonarray2 = new JSONArray();
		jsonarray2.add(JSONObject2);
		// JSONArray jsonarray2 = (JSONArray) parser2.parse(project.toString());

		Iterator itr2 = jsonarray2.iterator();
		while (itr2.hasNext()) {
			org.json.simple.JSONObject object = (org.json.simple.JSONObject) itr2.next();
			if (object.get("key") != null) {
				key = object.get("key").toString();
				System.out.println("key is " + key);
				// values.add(value);
				jobname.append(key);
			}

		}
		String env = null;
		JSONParser parser3 = new JSONParser();
		JSONArray jsonarray3 = (JSONArray) parser3.parse(custom_field.toString());

		Iterator itr3 = jsonarray3.iterator();
		while (itr3.hasNext()) {
			org.json.simple.JSONObject object = (org.json.simple.JSONObject) itr3.next();
			if (object.get("value") != null) {
				env = object.get("value").toString();
				System.out.println("environment is " + env);
				environment.add(env);
			}

		}
		GetParameters(encoded, id, jobname, environment);

	}

	private static void GetParameters(String encoded, String id, StringBuilder jobname, ArrayList<String> environment)
			throws ParseException, IOException, org.json.simple.parser.ParseException {
		// Trigger api to get name by passing issue id
		// TODO Auto-generated method stub
		// URL url = new
		// URL("http://"+Jira_IP+"/rest/idalko-igrid/1.0/grid/10101/issue/" + id);

		ArrayList<String> comp_name = new ArrayList<String>();
		ArrayList<String> Versions = new ArrayList<String>();
		ArrayList<String> idvalue = new ArrayList<String>();
		URL url = new URL("http://34.211.100.27:8080/rest/idalko-igrid/1.0/grid/10101/issue/" + id);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");
		conn.setRequestProperty("Authorization", "Basic " + encoded);
		if (conn.getResponseCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
		}

		BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

		String output;
		StringBuffer response = new StringBuffer();

		while ((output = br.readLine()) != null) {
			response.append(output);
		}
		// System.out.println("output of second api is " +response.toString());
		conn.disconnect();

		JSONParser parser = new JSONParser();
		String arary_value = null;
		Object JSONObject = parser.parse(response.toString());
		JSONArray jsonarray = new JSONArray();
		jsonarray.add(JSONObject);
		Iterator itr = jsonarray.iterator();
		while (itr.hasNext()) {
			org.json.simple.JSONObject object = (org.json.simple.JSONObject) itr.next();
			if (object.get("values") != null) {
				arary_value = object.get("values").toString();

			}
			// System.out.println("array of values is "+arary_value);
		}
		JSONParser parser1 = new JSONParser();
		JSONArray jsonarray1 = (JSONArray) parser1.parse(arary_value.toString());
		String comp_type = null;
		String com_type_value = null;
		String id_values = null;
		Iterator itr1 = jsonarray1.iterator();
		while (itr1.hasNext()) {
			org.json.simple.JSONObject object = (org.json.simple.JSONObject) itr1.next();

			if (object.get("componenttype") != null) {
				comp_type = object.get("componenttype").toString();
				// System.out.println("value is "+comp_type);
				JSONParser parser2 = new JSONParser();
				Object JSONObject2 = parser2.parse(comp_type);
				JSONArray jsonarray2 = new JSONArray();
				jsonarray2.add(JSONObject2);
				// System.out.println("jsonarray 2 is "+jsonarray2);
				Iterator itr2 = jsonarray2.iterator();
				while (itr2.hasNext()) {
					JSONObject object2 = (JSONObject) itr2.next();

					if (object2.get("value") != null) {
						com_type_value = object2.get("value").toString();
						System.out.println("values in comp_type are : " + com_type_value);
						comp_name.add(com_type_value);
					}
				}
			}

			if (object.get("id") != null) {
				id_values = object.get("id").toString();
				System.out.println("id in values are " + id_values);
				idvalue.add(id_values);
			}
			String version = null;
			String version_value = null;
			if (object.get("version") != null) {
				version = object.get("version").toString();
				// System.out.println("version is "+version);
				JSONParser parser2 = new JSONParser();
				Object JSONObject2 = parser2.parse(version);
				JSONArray jsonarray2 = new JSONArray();
				jsonarray2.add(JSONObject2);
				// System.out.println("jsonarray 2 is "+jsonarray2);
				Iterator itr2 = jsonarray2.iterator();
				while (itr2.hasNext()) {
					JSONObject object2 = (JSONObject) itr2.next();

					if (object2.get("value") != null) {
						version_value = object2.get("value").toString();
						System.out.println("values in version are : " + version_value);
						Versions.add(version_value);
					}
				}
			}
		}
		ArrayList<String> jobName = new ArrayList<String>();
		int length_env = environment.size();
		int length_comp = comp_name.size();
		for (int i = 0; i < length_env; i++) {
			StringBuilder final_jobname = new StringBuilder();
			for (int j = 0; j < length_comp; j++) {
				final_jobname.append(jobname + "_");
				final_jobname.append(comp_name.get(j) + "_");
				final_jobname.append(environment.get(i));
				// System.out.println(final_jobname.toString());
				jobName.add(final_jobname.toString());
				final_jobname.setLength(0);
			}

		}
		for (int i = 0; i < jobName.size(); i++) {
			System.out.println(jobName.get(i));
		}
		String Filename="D://json.text";
		jsonData(comp_name, environment, jobName, Versions, idvalue,Filename);
		//TriggerJob(comp_name, environment, jobName, Versions);
	}

	private static void jsonData(ArrayList<String> comp_name, ArrayList<String> environment, ArrayList<String> jobName,
			ArrayList<String> versions, ArrayList<String> idvalue, String fileName) throws IOException {
		// TODO Auto-generated method stub

		String ans = json(comp_name, environment, jobName, versions, idvalue);
		FileWriter file = new FileWriter(fileName);

		file.write(ans.toString().replaceAll("\\\\", "").replaceAll("}\"", "}").replaceAll("\"\\{", "{"));
		file.flush();
	}

	private static String json(ArrayList<String> comp_name,
			ArrayList<String> environment, ArrayList<String> jobName,
			ArrayList<String> versions, ArrayList<String> idvalue) {
		// TODO Auto-generated method stub
	
		JSONObject obj = null;

		JSONObject obj1 = new JSONObject();
		int j = 0;
		java.util.List out = new ArrayList();

		ArrayList<String> result = new ArrayList<String>();
		int size = comp_name.size();
		for (int i = 0; i < size; i++) {
			result.add(comp_name.get(i) + ";" + idvalue.get(i) + ";" + versions.get(i));

		}
		// System.out.println(result.toString());
		for (int i = 0; i < environment.size(); i++) {

			obj = new JSONObject();

			obj.put("Environment", environment.get(i));
			for (int k = 0; k < result.size(); k++) {
				String[] array = result.get(k).split("\\;");
				String comp = array[0];
				String id = array[1];
				String ver = array[2];
				obj.put("Component", comp);
				obj.put("id", id);
				obj.put("Version", ver);
				out.add(obj.toJSONString());
				obj1.put(jobName.get(j), out.get(j));

				j++;
			}

		}

		return obj1.toJSONString();
	}
	
	private static void TriggerJob(ArrayList<String> comp_name, ArrayList<String> environment,
			ArrayList<String> jobName, ArrayList<String> versions) throws IOException {
		// TODO Auto-generated method stub
		String uname = "jenkins";
		String password = "jenkins123";
		String authString = uname + ":" + password;
		byte[] encodedBytes = Base64.encodeBase64(authString.getBytes());
		String encoded = new String(encodedBytes);
		int i = 0;
		while (i < jobName.size()) {
			for (int j = 0; j < versions.size(); j++) {

				//String url = "http://34.211.100.27:9090//jenkins/job/" + jobName.get(i)
					//	+ "/buildWithParameters?version=" + versions.get(j);
			//	System.out.println(url);
				
				
				  URL obj = new URL("http://34.211.100.27:9090/jenkins/job/"+jobName.get(i)+"/buildWithParameters?version=" + versions.get(j));
			     System.out.println(obj.toString());
				  HttpURLConnection con =
				  (HttpURLConnection) obj.openConnection(); con.setDoOutput(true);
				 con.setRequestMethod("POST");
				 
				 con.setRequestProperty("Authorization", "Basic " + encoded);
				 con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
				 
				  OutputStreamWriter out = new OutputStreamWriter( con.getOutputStream());
				 System.out.println(con.getResponseCode());
				// System.out.println(con.getResponseMessage()); out.close();
				 
				 
				 System.out.println("\nSending 'POST' request to URL");
				 i++;
			}
		}
	}

