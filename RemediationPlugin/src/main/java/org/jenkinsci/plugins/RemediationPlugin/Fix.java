package org.jenkinsci.plugins.RemediationPlugin;



public class Fix {

	private String fixId;
	private String solution;
	private Cause cause;
	
	
	
	public Cause getCause() {
		return cause;
	}
	public void setCause(Cause cause) {
		this.cause = cause;
	}
	public String getFixId() {
		return fixId;
	}
	public void setFixId(String fixId) {
		this.fixId = fixId;
	}
	public String getSolution() {
		return solution;
	}
	public void setSolution(String solution) {
		this.solution = solution;
	}
	
	
}
