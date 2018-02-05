package com.cognizant.DownloadFile;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;

import hudson.Extension;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.BuildListener;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Builder;
import hudson.util.FormValidation;
import net.sf.json.JSONObject;

public class DownloadFiles extends Builder {

  
	 private String localUrl;
		 private String localFileName;
		 private String repoName;
		 private String remoteFileName;
		 private String authFileLocation;
	 private final	String commitMsg = "Creating a new branch in github";

    // Fields in config.jelly must match the parameter names in the "DataBoundConstructor"
    @DataBoundConstructor
    public DownloadFiles(String localUrl,String localFileName,String repoName,String remoteFileName,String authFileLocation) {
        this.localUrl = localUrl;
        this.localFileName = localFileName;
        this.repoName = repoName;
        this.remoteFileName = remoteFileName;
        this.authFileLocation = authFileLocation;
    }
	

    public String getlocalUrl() {
        return localUrl;
    }
	 public String getlocalFileName() {
        return localFileName;
    }
	 public String getrepoName() {
        return repoName;
    }
	 public String getremoteFileName() {
        return remoteFileName;
    }
	 public String getauthFileLocation() {
        return authFileLocation;
    }

	 public boolean perform(AbstractBuild<?, ?> build,Launcher launcher, BuildListener listener) throws FileNotFoundException, IOException  {        // This is where you 'build' the project.
        // Since this is a dummy, we just say 'hello world' and call that a build.
		try {
			this.localUrl=localUrl;
			this.localFileName = localFileName;
	        this.repoName = repoName;
	        this.remoteFileName = remoteFileName;
	        this.authFileLocation = authFileLocation;
	        listener.getLogger().println(localUrl);
	        listener.getLogger().println(localFileName);
	        listener.getLogger().println(repoName);
	        listener.getLogger().println(remoteFileName);
	        listener.getLogger().println(authFileLocation);	        
			CommonUtilsGit.fileDownload(localUrl, localFileName, repoName, remoteFileName, authFileLocation, listener);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        // This also shows how you can consult the global configuration of the builder
        return true;
    }

    // Overridden for better type safety.
    // If your plugin doesn't really define any property on Descriptor,
    // you don't have to do this.
    @Override
    public DescriptorImpl getDescriptor() {
        return (DescriptorImpl)super.getDescriptor();
    }

    @Extension // This indicates to Jenkins that this is an implementation of an extension point.
    public static final class DescriptorImpl extends BuildStepDescriptor<Builder> {
      
        private boolean useFrench;

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
            return "DownloadFiles";
        }

    
    }
}

