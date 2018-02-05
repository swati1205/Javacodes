package org.jenkinsci.plugins.RemediationPlugin;


import hudson.model.AbstractBuild;
import hudson.model.Action;
import hudson.model.Run;
import jenkins.model.RunAction2;


public class ViewIssuesAction implements Action {
	
	private String buildNumber;
	private String issueMessage;
	private String causeMessage;
	private String fixMessage;
	private Run<?, ?> build;

	
	
	
	public ViewIssuesAction(Run<?,?> build,String buildNumber, String issueMessage, String causeMessage, String fixMessage) {
		this.build = build;
		this.buildNumber = buildNumber;
		this.issueMessage = issueMessage;
		this.causeMessage = causeMessage;
		this.fixMessage = fixMessage;
	}



	public String getBuildNumber() {
		return buildNumber;
	}



	public void setBuildNumber(String buildNumber) {
		this.buildNumber = buildNumber;
	}



	public String getIssueMessage() {
		return issueMessage;
	}



	public void setIssueMessage(String issueMessage) {
		this.issueMessage = issueMessage;
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


	@Override
	public String getDisplayName() {
		// TODO Auto-generated method stub
		return "summary";
	}



	@Override
	public String getIconFileName() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public String getUrlName() {
		// TODO Auto-generated method stub
		return null;
	}




}
