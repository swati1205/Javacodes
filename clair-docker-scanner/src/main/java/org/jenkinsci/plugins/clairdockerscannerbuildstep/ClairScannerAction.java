package org.jenkinsci.plugins.clairdockerscannerbuildstep;

import java.io.IOException;

import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

import hudson.model.Action;
import hudson.model.AbstractBuild;
import hudson.model.BuildListener;
import hudson.util.ChartUtil;

public class ClairScannerAction implements Action {
	//BuildListener listener;
	private String resultsUrl;
	private AbstractBuild<?, ?> build;
	private String artifactSuffix;
	private String localImage;
	public static final int CHART_WIDTH = 500;
    public static final int CHART_HEIGHT = 200;
	public ClairScannerAction(AbstractBuild<?, ?> build, String artifactSuffix, String artifactName,
			String localImage) {
		this.build = build;
		this.artifactSuffix = artifactSuffix;
		this.resultsUrl = "../artifact/" + artifactName;
		this.localImage = localImage;
	}
	@Override
	public String getIconFileName() {
		// return the path to the icon file
		return "/plugin/clair-docker-scanner/images/clair.png";
	}

	@Override
	public String getDisplayName() {
		// return the label for your link
		return "Clair Docker Scanner - " + localImage;
	}

	@Override
	public String getUrlName() {
		// defines the suburl, which is appended to ...jenkins/job/jobname
		if (artifactSuffix == null) {
			return "clair-results";
		} else {
			return "clair-results-" + artifactSuffix;
		}
	}

	public AbstractBuild<?, ?> getBuild() {
		return this.build;
	}

	public String getResultsUrl() {
		return this.resultsUrl;
	}
	
	
}
