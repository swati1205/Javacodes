package org.jenkins.SonarProject;

import hudson.model.Action;
import hudson.model.Run;

import java.util.ArrayList;

public class ViewIssuesAction implements Action {
	
	private Run<?, ?> build;
	ArrayList<String> list;
	public ViewIssuesAction(Run<?, ?> build, ArrayList<String> list) {
		this.build=build;
		this.list = list;
	}
	public ArrayList<String> getList()
	{
		return list;
		
	}
	public void setList(ArrayList<String> list)
	{
		this.list=list;
		
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
