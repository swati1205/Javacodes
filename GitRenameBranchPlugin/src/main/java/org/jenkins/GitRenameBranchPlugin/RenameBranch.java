package org.jenkins.GitRenameBranchPlugin;
import hudson.Launcher;
import hudson.Extension;
import hudson.model.Build;
import hudson.model.BuildListener;
import hudson.model.Descriptor.FormException;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.tasks.Builder;
import hudson.util.FormValidation;
import hudson.tasks.BuildStepDescriptor;

import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;

import java.io.IOException;

import javax.management.Descriptor;
import javax.servlet.ServletException;

import net.sf.json.JSONObject;

public class RenameBranch extends Builder {
	String repoPath = null;
	String oldBranchName = null;
	String newBranchName = null;
	String authFileLocation = null;
	String commitMsg = "Creating a new branch in github";
  
  

    public String getRepoPath() {
		return repoPath;
	}

	public void setRepoPath(String repoPath) {
		this.repoPath = repoPath;
	}

	public String getOldBranchName() {
		return oldBranchName;
	}

	public void setOldBranchName(String oldBranchName) {
		this.oldBranchName = oldBranchName;
	}

	public String getNewBranchName() {
		return newBranchName;
	}

	public void setNewBranchName(String newBranchName) {
		this.newBranchName = newBranchName;
	}

	public String getAuthFileLocation() {
		return authFileLocation;
	}

	public void setAuthFileLocation(String authFileLocation) {
		this.authFileLocation = authFileLocation;
	}

	// Fields in config.jelly must match the parameter names in the "DataBoundConstructor"
	@DataBoundConstructor
    public  RenameBranch(String repoPath, String oldBranchName,String newBranchName,String authFileLocation) {
		this.repoPath=repoPath;
		this.oldBranchName=oldBranchName;
		this.newBranchName=newBranchName;
		this.authFileLocation= authFileLocation;
		
	}

	@Override
	public boolean perform(AbstractBuild<?, ?> build,Launcher launcher, BuildListener listener) throws IOException, InterruptedException {
		this.repoPath=repoPath;
		this.oldBranchName=oldBranchName;
		this.newBranchName=newBranchName;
		this.authFileLocation= authFileLocation;
		CommonUtilsGit git=new CommonUtilsGit();
		git.renameBranch(repoPath, oldBranchName, newBranchName, authFileLocation, commitMsg);
		     return true;	
	}
	
	
	
		@Override
	    public DescriptorImpl getDescriptor() {
	        return (DescriptorImpl)super.getDescriptor();
	  }
		
    @Extension 
    public static final class DescriptorImpl extends BuildStepDescriptor<Builder> {
        
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
            return "Rename the Branch";
        }

		
            
    }
	
}


