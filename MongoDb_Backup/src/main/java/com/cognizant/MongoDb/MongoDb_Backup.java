package com.cognizant.MongoDb;
import hudson.Launcher;
import hudson.Extension;
import hudson.FilePath;
import hudson.util.FormValidation;
import hudson.model.AbstractProject;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.tasks.Builder;
import hudson.tasks.BuildStepDescriptor;
import jenkins.tasks.SimpleBuildStep;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.QueryParameter;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import java.io.IOException;

public class MongoDb_Backup extends Builder implements SimpleBuildStep {

	private final String MongoDb_Path;
    private final String BackupFile_Path;
  
    @DataBoundConstructor
    public MongoDb_Backup(String MongoDb_Path,String BackupFile_Path) {
        this.MongoDb_Path = MongoDb_Path;
        this.BackupFile_Path = BackupFile_Path;
    }
    public String getMongoDb_Path() {
        return MongoDb_Path;
    }
       public String getBackupFile_Path() {
        return BackupFile_Path;
    }
       public void perform(Run<?,?> build, FilePath workspace, Launcher launcher, TaskListener listener){
       DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		String date1 = dateFormat.format(date);
		 File file = new File(BackupFile_Path+"\\MongoDbDump"+date1);
	        if (!file.exists()) {
	            if (file.mkdir()) {
	            	listener.getLogger().println("Directory is created!");
	            } else {
	            	listener.getLogger().println("Failed to create directory!");
	            }
	        }
		
	    boolean status = false;
	   
	      String command=MongoDb_Path+"\\mongodump.exe --out "+file;
	   
	    try {
	        Process runtimeProcess = Runtime.getRuntime().exec(command);
	      int processComplete = runtimeProcess.waitFor();
	        
	        if (processComplete == 0) {
	        	listener.getLogger().println("backup: Backup Successfull");
	            status = true;
	       } else 
	        {
	    	   listener.getLogger().println("backup: Backup Failure!");
	        }
	    
	    } catch (IOException ioe) {
	    	listener.getLogger().println("Exception IO");
	        ioe.printStackTrace();
	    } catch (Exception e) {
	    	listener.getLogger().println("Exception");
	        e.printStackTrace();
	    } 

   }
  
    @Override
    public DescriptorImpl getDescriptor() {
        return (DescriptorImpl)super.getDescriptor();
   }

  @Extension // This indicates to Jenkins that this is an implementation of an extension point.
    public static final class DescriptorImpl extends BuildStepDescriptor<Builder> {
    
         public DescriptorImpl() {
            load();
        }
   
        public boolean isApplicable(Class<? extends AbstractProject> aClass) {
            // Indicates that this builder can be used with all kinds of project types 
            return true;
        }

            public String getDisplayName() {
            return "MongoDB_Backup";
        }

    }
}

