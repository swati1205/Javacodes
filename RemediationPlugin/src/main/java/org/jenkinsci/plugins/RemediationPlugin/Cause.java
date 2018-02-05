package org.jenkinsci.plugins.RemediationPlugin;
import java.util.List;
public class Cause {

	private String causeId;
	private String causeDescription;
	private Issue issue;
	private List<Fix> fixs;
	
	
	public List<Fix> getFixs() {
		return fixs;
	}
	public void setFixs(List<Fix> fixs) {
		this.fixs = fixs;
	}
	public Issue getIssue() {
		return issue;
	}
	public void setIssue(Issue issue) {
		this.issue = issue;
	}
	public String getCauseId() {
		return causeId;
	}
	public void setCauseId(String causeId) {
		this.causeId = causeId;
	}
	public String getCauseDescription() {
		return causeDescription;
	}
	public void setCauseDescription(String causeDescription) {
		this.causeDescription = causeDescription;
	}
}