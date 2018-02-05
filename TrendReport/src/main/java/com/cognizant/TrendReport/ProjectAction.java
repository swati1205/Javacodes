package com.cognizant.TrendReport;

import java.io.IOException;
import java.io.Serializable;

import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

import hudson.model.AbstractBuild;
import hudson.model.Action;
import hudson.model.Build;
import hudson.model.BuildListener;
import hudson.model.AbstractProject;
import hudson.model.Project;
import hudson.model.Run;
import hudson.util.ChartUtil;

public class ProjectAction implements Action , Serializable{
	public static final String URL_NAME = "Result";

    public static final int CHART_WIDTH = 500;
    public static final int CHART_HEIGHT = 200;
    public AbstractBuild<?, ?> build;
	BuildListener listener;
    public AbstractProject<?,?> project;
    private transient Run<?,?> owner;
    public static String File_Path;
   

	
    //AbstractBuild<?, ?> build_no= build.getPreviousBuild();
	public ProjectAction(AbstractProject<?, ?> project, String File_Path) {
		// TODO Auto-generated constructor stub
		this.project = project;
		this.File_Path=File_Path;
	}

	
	@Override
	public String getDisplayName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getIconFileName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUrlName() {
		// TODO Auto-generated method stub
		return URL_NAME;
	}
	
	AbstractBuild<?, ?> build_no = project.getLastBuild().getPreviousBuild();
	/* public void doIndex(final StaplerRequest request, final StaplerResponse response) throws IOException {
	        AbstractBuild<?, ?> build = getLastFinishedBuild();
	        System.out.println("inside index "+build);
	        if (build != null) {
	            response.sendRedirect2(String.format("../%d/%s", build.getNumber(), BuildAction.URL_NAME));
	        }else{
	            // Click to the link in menu on the job page before the first build
	            response.sendRedirect2("..");
	        }
	    }*/
	 
   /*public AbstractBuild<?, ?> getLastFinishedBuild() {
        AbstractBuild<?, ?> lastBuild = project.getLastBuild();
        
       
        while (lastBuild != null && (lastBuild.isBuilding() || lastBuild.getAction(BuildAction.class) == null)) {
            lastBuild = lastBuild.getPreviousBuild();
        }
        return lastBuild;
    }*/

    
   /* public BuildAction getLastFinishedBuildAction() {
        AbstractBuild<?, ?> lastBuild = getLastFinishedBuild();
        return (lastBuild != null) ? lastBuild.getAction(BuildAction.class) : null;
    }*/
    
	@SuppressWarnings("deprecation")
	public void doTrendMap(final StaplerRequest request, final StaplerResponse response) throws IOException, InterruptedException {
		System.out.println("inside map");
		//AbstractBuild<?,?> lastBuild = this.getLastFinishedBuild();
    // Object lastAction = project.getLastBuild().getPreviousBuild();
	System.out.println("Create Map");
	        ChartUtil.generateClickableMap(
	                request,
	                response,
	                CreateGraph.buildChart(build_no, File_Path),
	                CHART_WIDTH,
	                CHART_HEIGHT);
	    }
	 /**
     * Display the trend map.
     *
     * @param request
     *            Stapler request
     * @param response
     *            Stapler response
     * @throws IOException
     *             in case of an error
     */
		 @SuppressWarnings("deprecation")
		public void doTrend(final StaplerRequest request, final StaplerResponse response) throws IOException, InterruptedException {
			 System.out.println("inside map");
			 //AbstractBuild<?,?> lastBuild = this.getLastFinishedBuild();
		    // Object lastAction =  project.getLastBuild().getPreviousBuild();

		        ChartUtil.generateGraph(
		                request,
		                response,
		                CreateGraph.buildChart(build_no, File_Path),
		                CHART_WIDTH,
		                CHART_HEIGHT);
		    }
		 
		
}
