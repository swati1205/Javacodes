package org.jenkins.SonarProject;
import hudson.Launcher;
import hudson.Extension;
import hudson.FilePath;
import hudson.util.FormValidation;
import hudson.util.ListBoxModel;
import hudson.util.ListBoxModel.Option;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.Action;
import hudson.model.Descriptor;
import hudson.model.BuildListener;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.tasks.BuildStepMonitor;
import hudson.tasks.Builder;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Publisher;
import jenkins.tasks.SimpleBuildStep;
import hudson.tasks.Recorder;

import javax.servlet.ServletException;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.codec.binary.Base64;
//import org.jenkinsci.plugins.RemediationPlugin.ViewIssuesAction;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;

import antlr.collections.List;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
//import java.util.Base64;
import java.io.*;

public class JenkinsSonarPrj extends Publisher {
private String goalType;
    private String SonarUrl;
   // private String SonarUrl2;
    // Fields in config.jelly must match the parameter names in the "DataBoundConstructor"
	@DataBoundConstructor
    public  JenkinsSonarPrj(String goalType,String SonarUrl) {
		//this.goalType=goalType;
		this.SonarUrl=SonarUrl;
	}
/*public String getGoalType()
{
return goalType;	
}*/
	
    public String getSonarUrl() {
		return SonarUrl;
	}

	public void setSonarUrl(String sonarUrl) {
		SonarUrl = sonarUrl;
	}

	@Override
	public boolean perform(AbstractBuild<?, ?> build,Launcher launcher, BuildListener listener) throws IOException, InterruptedException {
    	try
    	{
    		/*listener.getLogger().println(goalType);
    		
    		listener.getLogger().println(SonarUrl);
    		if(goalType.contentEquals("Sonar Url1"))
    		{
            this.SonarUrl = SonarUrl;
            listener.getLogger().println(SonarUrl);
    		}
    		if(goalType.contentEquals("Sonar Url2"))
    		{
            this.SonarUrl = SonarUrl;
            listener.getLogger().println(SonarUrl);
        }*/

        String name = "admin";
		String password = "admin";
		String authString = name + ":" + password;
		byte[] encodedBytes = Base64.encodeBase64(authString.getBytes());
		String encoded = new String(encodedBytes);
		URL obj = new URL(SonarUrl);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("Authorization", "Basic " + encoded);
		BufferedReader in = new BufferedReader(new InputStreamReader(
				con.getInputStream()));
		String inputline;
		StringBuffer response = new StringBuffer();
		while ((inputline = in.readLine()) != null) {
			response.append(inputline);
		}
		listener.getLogger().println("Response:"+response.toString());
		ArrayList<String> list = new ArrayList<String>();
		JSONParser parser = new JSONParser();
		Object JSONObject = parser.parse(response.toString());
		listener.getLogger().println("JsonObject:"+JSONObject.toString());
		org.json.simple.JSONArray jsonarray = (org.json.simple.JSONArray) JSONObject;
		Iterator itr = jsonarray.iterator();
		while (itr.hasNext()) {
			org.json.simple.JSONObject object = (org.json.simple.JSONObject) itr.next();
			if (object.get("nm") != null) {
			String	PrjName = (String) object.get("nm");
			list.add(PrjName);
		}
		
		}
		
		addAction(build, list);   	
    	}
    	
    	 catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return true;
    }
    
	public Action addAction(Run<?, ?> build, ArrayList<String> list) { 
		ViewIssuesAction action=new ViewIssuesAction(build, list);
		build.addAction(action);
		return action;
		
	}

   
    @Override
    //public Descriptor<Publisher> getDescriptor() {
    //    return (Descriptor<Publisher>)super.getDescriptor();
   // }
    public DescriptorImpl getDescriptor() {
		return (DescriptorImpl) super.getDescriptor();
	}
    @Extension 
    public static final class DescriptorImpl extends BuildStepDescriptor<Publisher> {
        
        public DescriptorImpl() {
            load();
        }


        public boolean isApplicable(Class<? extends AbstractProject> aClass) {
            // Indicates that this builder can be used with all kinds of project types 
            return true;
        }

        /**
         * This human readable name is used in the configuration screen.
         */
        public String getDisplayName() {
            return "Display Sonar Projects";
        }
            }

	@Override
	public BuildStepMonitor getRequiredMonitorService() {
		// TODO Auto-generated method stub
		return BuildStepMonitor.NONE;
	}
}

