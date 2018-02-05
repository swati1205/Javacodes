package com.cognizant.Plot_Graph;
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
import hudson.tasks.Recorder;
import jenkins.tasks.SimpleBuildStep;
import net.sf.json.JSONObject;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.QueryParameter;

import javax.servlet.ServletException;

import java.io.IOException;
import java.io.Serializable;

public class GraphBuilder extends Recorder implements Serializable{

    private String File_Path;

    // Fields in config.jelly must match the parameter names in the "DataBoundConstructor"
    @DataBoundConstructor
    public GraphBuilder(String File_Path) {
        this.File_Path = File_Path;
    }

    /**
     * We'll use this from the {@code config.jelly}.
     */
    public String getFile_Path() {
		return File_Path;
	}
    BuildListener listener;
    @Override
	 public Action getProjectAction(AbstractProject<?,?> project){
	     System.out.println("inside project action"); 
	     
	     AbstractBuild<?, ?> lastBuild=	project.getLastBuild().getPreviousBuild();
	     System.out.println(lastBuild);
		 return new ProjectAction(project,listener,File_Path,lastBuild );
	    }
    @Override
    public boolean perform(AbstractBuild<?, ?> build,Launcher launcher, BuildListener listener) throws IOException, InterruptedException {
        listener.getLogger().println("File path in perform class"+File_Path);
      
        build.addAction(new BuildAction(build));
        
		return true;
    }

    

	// Overridden for better type safety.
    // If your plugin doesn't really define any property on Descriptor,
    // you don't have to do this.
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

        /**
         * This human readable name is used in the configuration screen.
         */
        public String getDisplayName() {
            return "Display Plot for Clair Docker Scanner";
        }

      

        
    }

    @Override
	public BuildStepMonitor getRequiredMonitorService() {
		// TODO Auto-generated method stub
		return BuildStepMonitor.NONE;
	}
}

