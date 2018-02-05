package mongo;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;

public class mongo {

	public static void main(String[] args)throws  MongoException {
		// TODO Auto-generated method stub
		 try{
				
	         // To connect to mongodb server
	         MongoClient mongoClient = new MongoClient("10.219.17.145" , 27017 );
				
	         // Now connect to your databases
	         DB db = mongoClient.getDB("employee");
	         System.out.println("Connect to database successfully");
	         //check for collection
	         DBCollection dbcollection = db.getCollection("myEmployee");
	       BasicDBObject basicDBObject=new BasicDBObject();
	       //  basicDBObject.put("id", "2");
	       //    basicDBObject.put("name", "smith");
	            //basicDBObject.put("description", "NoSQL database doesn't have tables");
	         //   dbcollection.insert(basicDBObject);
	         //   DBObject myDoc = dbcollection.findOne(basicDBObject);

	       //     System.out.println(myDoc);  
	            //jenkins
	        	URL url = new URL("http://localhost:8085/api/json");
				String username = "swati";
				String password = "swati";
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				StringBuffer projectInfo = new StringBuffer();
				List<String> jobname = new ArrayList<String>();
				List<Integer> buildNumber = new ArrayList<Integer>();
				
				conn.setConnectTimeout(50000);
				conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
				conn.setDoOutput(true);
				conn.setDoInput(true);
				conn.setRequestMethod("GET");
				// Cloudset user authentication
				conn.setRequestProperty(
						"Authorization",
						"Basic "
								+ (new String(Base64
										.encodeBase64((username + ":" + password)
												.getBytes()))));

				int responseCode = conn.getResponseCode();
				System.out.println("Response Code : " + responseCode);

				BufferedReader in = new BufferedReader(new InputStreamReader(
						conn.getInputStream()));
				String inputLine;

				while ((inputLine = in.readLine()) != null) {
					projectInfo.append(inputLine);
				}
				in.close();

				JSONObject obj1 = (JSONObject) JSONValue.parse(projectInfo
						.toString());
				JSONArray jsonArray = (JSONArray) obj1.get("jobs");
				Iterator iterator = jsonArray.iterator();
				while (iterator.hasNext()) {
					JSONObject object = (JSONObject) iterator.next();
					jobname.add((String) (object.get("name")).toString());

				}
				System.out.println("Job names");
				/*
				 * for(String job : jobname) { System.out.println(job); }
				 */
				int i=1;
				for (String job : jobname) {
					System.out.println(job);
					basicDBObject.put("jobname"+i,job);
					
					//dbcollection.insert(basicDBObject);
					
					i++;
				}
				dbcollection.insert(basicDBObject);
				 DBObject myDoc = dbcollection.findOne(basicDBObject);
		           System.out.println(myDoc);  
					/*URL url1 = new URL("http://localhost:8085/job/" + job
							+ "/api/json");
					HttpURLConnection conn1 = (HttpURLConnection) url1
							.openConnection();
					StringBuffer jobInfo = new StringBuffer();

					// conn1.setConnectTimeout(50000);
					conn1.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
					conn1.setDoOutput(true);
					conn1.setDoInput(true);
					conn1.setRequestMethod("GET");
					// Cloudset user authentication
					conn1.setRequestProperty(
							"Authorization",
							"Basic "
									+ (new String(Base64.encodeBase64((username
											+ ":" + password).getBytes()))));

					// int responseCode1 = conn1.getResponseCode();
					// System.out.println("Response Code : " + responseCode1);

					BufferedReader in1 = new BufferedReader(new InputStreamReader(
							conn1.getInputStream()));
					String inputLine1 = null;*/

				/*	while ((inputLine1 = in1.readLine()) != null) {
						jobInfo.append(inputLine1);
						
						JSONObject obj2 = (JSONObject) JSONValue.parse(jobInfo
								.toString());
						JSONArray jsonArray2 = (JSONArray) obj2.get("builds");
						JSONObject number = (JSONObject) jsonArray2.get(0);
						String build = number.get("number").toString();
						//System.out.println("build : " + build);
						//System.out.println("Number : " + number);
						buildNumber.add(Integer.parseInt(build));
					}
*/
					
					  /*for(int i=0;i<1;i++) {  JSONObject number = (JSONObject)
					  jsonArray2.get(0); // System.out.println(number.toString());
					  
					 String build = number.get("number").toString();
					  buildNumber.add(Integer.parseInt(build)); }*/
					 

					//in1.close();
				

				/*System.out.println("Build Number");
				for (Integer num : buildNumber)
					System.out.println(num);*/

	      }
		 
		 catch(Exception e){
	         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      }
	}

}
