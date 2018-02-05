package com.cognizant.TrendReport;
import hudson.Launcher;
import hudson.Extension;
import hudson.FilePath;
import hudson.util.FormValidation;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.Action;
import hudson.model.BuildListener;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.tasks.BuildStepMonitor;
import hudson.tasks.Builder;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Publisher;
import jenkins.tasks.SimpleBuildStep;
import net.sf.json.JSONObject;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.QueryParameter;


import javax.servlet.ServletException;

import java.io.IOException;

public class TrendReport extends Publisher  {

    private final String File_Path;

    // Fields in config.jelly must match the parameter names in the "DataBoundConstructor"
    @DataBoundConstructor
    public TrendReport(String File_Path) {
        this.File_Path = File_Path;
    }

   
    public String getFile_Path() {
        return File_Path;
    }

    BuildListener listener;
    
    @Override
    public boolean perform(AbstractBuild<?, ?> build,Launcher launcher, BuildListener listener)  {
       // listener.getLogger().println("File path in perform class"+File_Path);
    	listener.getLogger().println("In the perform class");
    	//build.addAction(new BuildAction(build));
		return true;
    }
    @Override
	 public Action getProjectAction(AbstractProject<?,?> project){

	     System.out.println("inside project action"+ project.getLastBuild().getPreviousBuild()); 
		 return new ProjectAction(project,File_Path);
	    }
    
    @Override
    public DescriptorImpl getDescriptor() {
		return (DescriptorImpl) super.getDescriptor();
	}
   
 @Extension // This indicates to Jenkins that this is an implementation of an extension point.
    public static final class DescriptorImpl extends BuildStepDescriptor<Publisher> {
        public DescriptorImpl() {
            load();
        }
public boolean isApplicable(Class<? extends AbstractProject> aClass) {
            // Indicates that this builder can be used with all kinds of project types 
            return true;
        }

        
        public String getDisplayName() {
            return "Get Trend Reports";
        }
}


	@Override
	public BuildStepMonitor getRequiredMonitorService() {
		// TODO Auto-generated method stub
		return null;
	}
}
