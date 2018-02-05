package com.cognizant.BuildCounterPlugin;

import hudson.Launcher;
import hudson.Extension;
import hudson.FilePath;
import hudson.util.FormValidation;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.BuildListener;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.tasks.Builder;
import hudson.tasks.BuildStepDescriptor;
import jenkins.tasks.SimpleBuildStep;
import net.sf.json.JSONObject;
import hudson.model.Action;
import hudson.model.Actionable;
import org.apache.commons.codec.binary.Base64;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.QueryParameter;

import javax.servlet.ServletException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BuildCounter extends Builder {

	private final boolean buildCounter;

	// Fields in config.jelly must match the parameter names in the
	// "DataBoundConstructor"
	@DataBoundConstructor
	public BuildCounter(boolean buildCounter) {
		this.buildCounter = buildCounter;

	}

	public boolean isBuildCounter() {
		return buildCounter;
	}

	@Override
	public boolean perform(AbstractBuild<?, ?> build, Launcher launcher,
			BuildListener listener) throws IOException, InterruptedException {
		if (buildCounter) {
			listener.getLogger().println("Welcome");
			try {
				URL url = new URL("http://10.219.193.108:8080/api/json");
				String username = "sneha";
				String password = "sneha";
				listener.getLogger().println("Welcome");

				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
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
								+ (new String(Base64.encodeBase64((username
										+ ":" + password).getBytes()))));

				int responseCode = conn.getResponseCode();
				listener.getLogger().println("Response Code : " + responseCode);

				BufferedReader in = new BufferedReader(new InputStreamReader(
						conn.getInputStream()));
				String inputLine = null;

				while ((inputLine = in.readLine()) != null) {
					projectInfo.append(inputLine);
					// listener.getLogger().println(inputLine);
				}

				in.close();
				//listener.getLogger().println("Project Info : " + projectInfo);
				listener.getLogger().println("hello");
				JSONObject obj1 = (JSONObject) JSONValue.parse(projectInfo
						.toString());
				listener.getLogger().println("hello");
				JSONArray jsonArray = (JSONArray) obj1.get("jobs");
				listener.getLogger().println(jsonArray);
				Iterator iterator = jsonArray.iterator();
				while (iterator.hasNext()) {
					JSONObject object = (JSONObject) iterator.next();
					jobname.add((String) (object.get("name")).toString());
					listener.getLogger().println("hello");
				}
				listener.getLogger().println("Job names");
				/*
				 * for(String job : jobname) { System.out.println(job); }
				 */

				for (String job : jobname) {
					listener.getLogger().println(job);
					URL url1 = new URL("http://10.219.193.108:8080/job/" + job
							+ "/api/json");
					HttpURLConnection conn1 = (HttpURLConnection) url1
							.openConnection();
					StringBuffer jobInfo = new StringBuffer();

					// conn1.setConnectTimeout(50000);
					conn1.setRequestProperty("Accept-Language",
							"en-US,en;q=0.5");
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

					BufferedReader in1 = new BufferedReader(
							new InputStreamReader(conn1.getInputStream()));
					String inputLine1 = null;

					while ((inputLine1 = in1.readLine()) != null) {
						jobInfo.append(inputLine1);
						listener.getLogger().println("Hello");
						JSONObject obj2 = (JSONObject) JSONValue.parse(jobInfo
								.toString());
						JSONArray jsonArray2 = (JSONArray) obj2.get("builds");
						JSONObject number = (JSONObject) jsonArray2.get(0);
						String buildNo = number.get("number").toString();
						// System.out.println("build : " + build);
						// System.out.println("Number : " + number);
						buildNumber.add(Integer.parseInt(buildNo));
					}

					in1.close();
				}

				listener.getLogger().println("Build Number");
				for (Integer num : buildNumber)
					listener.getLogger().println(num);
				addAction(build, jobname, buildNumber);
			}

			catch (Exception ex) {
				// logger.error("Exception occured:", ex);

			}
		}
		return true;

	}

	public Action addAction(Run<?, ?> build, List<String> jobname,
			List<Integer> buildNumber) {
		// TODO Auto-generated method stub
		ViewAction action = new ViewAction(build, jobname, buildNumber);
		build.addAction(action);
		return action;

	}

	@Override
	public DescriptorImpl getDescriptor() {
		return (DescriptorImpl) super.getDescriptor();
	}

	@Extension
	public static final class DescriptorImpl extends
			BuildStepDescriptor<Builder> {

		public DescriptorImpl() {
			load();
		}

		public boolean isApplicable(Class<? extends AbstractProject> aClass) {
			// Indicates that this builder can be used with all kinds of project
			// types
			return true;
		}

		/**
		 * This human readable name is used in the configuration screen.
		 */
		public String getDisplayName() {
			return "Build Count Of Jobs";
		}
	}
}
