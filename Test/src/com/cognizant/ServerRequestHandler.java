package com.cognizant;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.codec.binary.Base64;

/**
 * Send a POST request to SonarQube
 * 
 * @author Cognizant
 * 
 */

public class ServerRequestHandler {

	public static void main(String[] args) throws Exception {

		ServerRequestHandler http = new ServerRequestHandler();

		System.out.println("Sending Http POST request");
		http.sendPost();
	}

	private void sendPost() throws Exception {
		Properties p = new Properties();

		InputStream input = null;

		try {

			input = new FileInputStream("config.properties");

			// load a properties file
			p.load(input);

			String url = p.getProperty("url");

			String name = p.getProperty("name");
			String password = p.getProperty("password");

			String authString = name + ":" + password;
			System.out.println("auth string: " + authString);
			byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
			String authStringEnc = new String(authEncBytes);
			System.out.println("Base64 encoded auth string: " + authStringEnc);

			URL obj = new URL(url);

			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			// add request header
			con.setRequestMethod("POST");
			con.setRequestProperty("Authorization", "Basic " + authStringEnc);
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

			String urlParameters = "metricId=127&projectKey=org.sonarqube:java-simple-sq-scanner&value=3";

			// Send post request
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();

			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'POST' request to URL : " + url);
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

			// print result
			System.out.println(response.toString());

			// get the property value and print it out
			// System.out.println(p.getProperty("database"));
			// System.out.println(p.getProperty("dbuser"));
			// System.out.println(p.getProperty("dbpassword"));

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

}