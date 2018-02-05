package com.cognizant.Sample;

import hudson.Launcher;
import hudson.Extension;
import hudson.FilePath;
import hudson.util.FormValidation;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.Run;
import hudson.model.BuildListener;
import hudson.model.TaskListener;
import hudson.tasks.BuildStepMonitor;
import hudson.tasks.Builder;
import hudson.tasks.BuildStepDescriptor;
import jenkins.tasks.SimpleBuildStep;
import net.sf.json.JSONObject;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.QueryParameter;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import javax.servlet.ServletException;

import java.io.IOException;
import java.io.InputStream;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class HelloWorldBuilder extends Builder {

	private final String ImageName;
	private final String Path;

	// Fields in config.jelly must match the parameter names in the
	// "DataBoundConstructor"
	@DataBoundConstructor
	public HelloWorldBuilder(String ImageName, String Path) {
		this.ImageName = ImageName;
		this.Path = Path;
	}

	public String getImageName() {
		return ImageName;
	}

	public void setImageName(String ImageName) {
		ImageName = ImageName;
	}

	public String getPath() {
		return Path;
	}

	public void setPath(String Path) {
		Path = Path;
	}

	/**
	 * We'll use this from the {@code config.jelly}.
	 * 
	 * @return
	 */
	/*
	 * public String getName() { return name; }
	 */
	@Override
    public boolean perform(AbstractBuild build, Launcher launcher, BuildListener listener) {
		// This is where you 'build' the project.
		// Since this is a dummy, we just say 'hello world' and call that a
		// build.

		// This also shows how you can consult the global configuration of the
		// builder
		String host = "10.242.138.115";
		String user = "root";
		String password = "123root";
		String command1 = "Path " + "-endpoint "
				+ "http://10.242.138.116:6060 " + "-my-address "
				+ "10.242.138.115 " + "ImageName";
		try {

			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			JSch jsch = new JSch();
			Session session = jsch.getSession(user, host, 22);
			session.setPassword(password);
			session.setConfig(config);
			session.connect();
			listener.getLogger().println("Connected");

			Channel channel = session.openChannel("exec");
			((ChannelExec) channel).setCommand(command1);
			channel.setInputStream(null);
			((ChannelExec) channel).setErrStream(System.err);

			InputStream in = channel.getInputStream();
			channel.connect();
			byte[] tmp = new byte[1024];
			while (true) {
				while (in.available() > 0) {
					int i = in.read(tmp, 0, 1024);
					if (i < 0)
						break;
					listener.getLogger().println(new String(tmp, 0, i));
				}
				if (channel.isClosed()) {
					listener.getLogger().println(
							"exit-status: " + channel.getExitStatus());
					break;
				}
				try {
					Thread.sleep(1000);
				} catch (Exception ee) {
				}
			}
			channel.disconnect();
			session.disconnect();
			listener.getLogger().println("DONE");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;

	}

	@Override
	public DescriptorImpl getDescriptor() {
		return (DescriptorImpl) super.getDescriptor();
	}

	@Extension
	// This indicates to Jenkins that this is an implementation of an extension
	// point.
	public static final class DescriptorImpl extends
			BuildStepDescriptor<Builder> {
		private String Host;

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
			return "Get Clair Report";
		}

		@Override
		public boolean configure(StaplerRequest req, JSONObject formData)
				throws FormException {
			// To persist global configuration information,
			// set that to properties and call save().
			Host = formData.getString(Host);
			// ^Can also use req.bindJSON(this, formData);
			// (easier when there are many fields; need set* methods for this,
			// like setUseFrench)
			save();
			return super.configure(req, formData);
		}

		/*
		 * public boolean getUseFrench() { return useFrench; }
		 */
	}
}
