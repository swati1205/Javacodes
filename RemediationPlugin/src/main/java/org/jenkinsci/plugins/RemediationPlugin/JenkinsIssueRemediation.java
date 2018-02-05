package org.jenkinsci.plugins.RemediationPlugin;

import hudson.Launcher;
import hudson.EnvVars;
import hudson.Extension;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.Action;
import hudson.model.Actionable;
import hudson.model.BuildListener;
import hudson.model.Run;
import hudson.search.SearchIndex;
import hudson.tasks.Publisher;
import hudson.tasks.Recorder;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.BuildStepMonitor;
import jenkins.model.Jenkins;
import org.kohsuke.stapler.DataBoundConstructor;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class JenkinsIssueRemediation extends Publisher {

	private final boolean issueRemedy;

	private String issueMessage;
	private String BUILD_NUMBER;
	private String causeMessage;
	private String fixMessage;

	public String getIssueMessage() {
		return issueMessage;
	}

	public void setIssueMessage(String issueMessage) {
		this.issueMessage = issueMessage;
	}

	public String getBUILD_NUMBER() {
		return BUILD_NUMBER;
	}

	public void setBUILD_NUMBER(String bUILD_NUMBER) {
		BUILD_NUMBER = bUILD_NUMBER;
	}

	public String getCauseMessage() {
		return causeMessage;
	}

	public void setCauseMessage(String causeMessage) {
		this.causeMessage = causeMessage;
	}

	public String getFixMessage() {
		return fixMessage;
	}

	public void setFixMessage(String fixMessage) {
		this.fixMessage = fixMessage;
	}

	@DataBoundConstructor
	public JenkinsIssueRemediation(boolean issueRemedy) {
		this.issueRemedy = issueRemedy;

	}

	public boolean isIssueRemedy() {
		return issueRemedy;
	}

	@Override
	public boolean perform(AbstractBuild<?, ?> build, Launcher launcher, BuildListener listener)
			throws IOException, InterruptedException {

		Issue issue = new Issue();
		EnvVars envVars = build.getEnvironment(listener);
		listener.getLogger().println(envVars);
		BUILD_NUMBER = envVars.get("BUILD_NUMBER");
listener.getLogger().println(BUILD_NUMBER);
		String buildLog = getBuildLog(build, listener);
		listener.getLogger().println(buildLog);
		try {
			List<Issue> issues = issue.getIssueId(buildLog, listener);
			if(issues.size()>0){
				String issueMessage = issues.get(0).getIssueMessage();
				String issueId = issues.get(0).getIssueId();
				List<Cause> causeId = issue.getCauses(issueId, listener);
				if (!causeId.equals(null)) {
					causeMessage = causeId.get(0).getCauseDescription();
					listener.getLogger().println("Cause Description:" + causeId.get(0).getCauseId().toString());
					List<Fix> fixId = issue.getfix(causeId.get(0).getCauseId(), listener);
					fixMessage = fixId.get(0).getSolution();
					listener.getLogger().println("Fix Description:" + fixMessage);
					addAction(build, BUILD_NUMBER, issueMessage, causeMessage, fixMessage, listener);
				} else {

					listener.getLogger().println("Possible cause in empty");
				}
			}else{
				
				listener.getLogger().println("No Issues Found");
			}
			

		} catch (ParserConfigurationException e) {

			e.printStackTrace();
		} catch (SAXException e) {

			e.printStackTrace();
		}

		return issueRemedy;

	}

	public Action addAction(Run<?, ?> build, String buildNumber, String issue, String cause, String fix,
			BuildListener listener) {
		listener.getLogger().println("Inside add action");
		ViewIssuesAction action = new ViewIssuesAction(build, buildNumber, issue, cause, fix);
	build.addAction(action);
		// build.getActions().add(action);
		listener.getLogger().println("Added Action");
		return action;
	}

	public String getBuildLog(AbstractBuild<?, ?> build, BuildListener listener) throws IOException {

		InputStream in = build.getLogInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		StringBuilder out = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			out.append(line);
		}

		reader.close();
		return out.toString();

	}

	@Override
	public DescriptorImpl getDescriptor() {
		return (DescriptorImpl) super.getDescriptor();
	}

	@Extension // This indicates to Jenkins that this is an implementation of an
				// extension point.
	public static final class DescriptorImpl extends BuildStepDescriptor<Publisher> {

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
			return "Issue Remedy";
		}

	}

	@Override
	public BuildStepMonitor getRequiredMonitorService() {
		return BuildStepMonitor.NONE;
	}
}
