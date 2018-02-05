package com.cognizant.BuildCounterPlugin;

import hudson.model.Action;
import hudson.model.AbstractBuild;
import hudson.model.Run;

import java.util.List;

public class ViewAction implements Action  {
	private Run<?, ?> build;
	List<String> jobname;
	List<Integer> buildNumber;
	public ViewAction(Run<?, ?> build2, List<String> jobname, List<Integer> buildNumber) {
		// TODO Auto-generated constructor stub
		
	}

	public Run<?, ?> getBuild() {
		return build;
	}

	public void setBuild(Run<?, ?> build) {
		this.build = build;
	}

	public List<String> getJobname() {
		return jobname;
	}

	public void setJobname(List<String> jobname) {
		this.jobname = jobname;
	}

	public List<Integer> getBuildNumber() {
		return buildNumber;
	}

	public void setBuildNumber(List<Integer> buildNumber) {
		this.buildNumber = buildNumber;
	}

	@Override
	public String getIconFileName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDisplayName() {
		// TODO Auto-generated method stub
		return "summary";
	}

	@Override
	public String getUrlName() {
		// TODO Auto-generated method stub
		return null;
	}

}
