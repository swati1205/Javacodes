package com.cognizant.Plot_Graph;

import java.io.IOException;
import java.io.Serializable;

import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

import hudson.model.AbstractBuild;
import hudson.model.Action;
import hudson.model.BuildListener;
import hudson.model.AbstractProject;
import hudson.util.ChartUtil;

public class ProjectAction implements Action , Serializable{
	public static final String URL_NAME = "Result";

    public static final int CHART_WIDTH = 500;
    public static final int CHART_HEIGHT = 200;
    public AbstractBuild<?, ?> build;
	BuildListener listener;
    public AbstractProject<?,?> project;
    AbstractBuild<?, ?> lastBuild;
	public static String File_Path;
    
	public ProjectAction(AbstractProject<?, ?> project,
			BuildListener listener,String File_Path, AbstractBuild<?, ?> lastBuild) {
		this.build=build;
		this.listener=listener;
		this.File_Path=File_Path;
		this.lastBuild= lastBuild;
		// TODO Auto-generated constructor stub
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
	
	
	/* public void doIndex(final StaplerRequest request, final StaplerResponse response) throws IOException {
	        AbstractBuild<?, ?> build = getLastFinishedBuild();
	        if (build != null) {
	            response.sendRedirect2(String.format("../%d/%s", build.getNumber(), BuildAction.URL_NAME));
	        }else{
	            // Click to the link in menu on the job page before the first build
	            response.sendRedirect2("..");
	        }
	    }*/
	 
   /* public AbstractBuild<?, ?> getLastFinishedBuild() {
        AbstractBuild<?, ?> lastBuild = project.getLastBuild();
        System.out.println("inside map");
        while (lastBuild != null && (lastBuild.isBuilding() || lastBuild.getAction(BuildAction.class) == null)) {
            lastBuild = lastBuild.getPreviousBuild();
        }
        return lastBuild;
    }*/

    
    /*public BuildAction getLastFinishedBuildAction() {
        AbstractBuild<?, ?> lastBuild = getLastFinishedBuild();
        return (lastBuild != null) ? lastBuild.getAction(BuildAction.class) : null;
    }*/
   
	/*@SuppressWarnings("deprecation")
	public void TrendMap(final StaplerRequest request, final StaplerResponse response) throws IOException, InterruptedException {
		System.out.println("last build is ");
		
		System.out.println("inside map");
		 AbstractBuild<?, ?> lastBuild = project.getLastBuild();
		System.out.println(lastBuild);
		 while (lastBuild != null && (lastBuild.isBuilding() || lastBuild.getAction(BuildAction.class) == null)) {
	            lastBuild = lastBuild.getPreviousBuild();
	        }
		// AbstractBuild<?,?> lastBuild = this.getLastFinishedBuild();
	      
	      	System.out.println("Create Map");
	        ChartUtil.generateClickableMap(
	                request,
	                response,
	                GraphPoints.buildChart(,File_Path),
	                CHART_WIDTH,
	                CHART_HEIGHT);
	    }*/
	 
		 @SuppressWarnings("deprecation")
		public void doTrend(final StaplerRequest request, final StaplerResponse response) throws IOException, InterruptedException {
			 System.out.println("inside map");
			/* AbstractBuild<?, ?> lastBuild = project.getLastBuild();
			 while (lastBuild != null && (lastBuild.isBuilding() || lastBuild.getAction(BuildAction.class) == null)) {
		            lastBuild = lastBuild.getPreviousBuild();
		        }
			 lastBuild = lastBuild.getPreviousBuild();
		        BuildAction lastAction = lastBuild.getAction(BuildAction.class);*/
		// AbstractBuild lastBuild=	project.getLastBuild();
			 //System.out.println(lastBuild);
			/* AbstractBuild<?, ?> lastBuild=	project.getLastBuild().getPreviousBuild();
			 System.out.println(lastBuild);*/
			 BuildAction lastAction = lastBuild.getAction(BuildAction.class);
				System.out.println("last action is "+lastAction);

		        ChartUtil.generateGraph(
		                request,
		                response,
		                GraphPoints.buildChart(lastAction,File_Path),
		                CHART_WIDTH,
		                CHART_HEIGHT);
		    }

		 public void doTrendDeltaMap(final StaplerRequest request, final StaplerResponse response) throws IOException, InterruptedException {
		        /*AbstractBuild<?,?> lastBuild = this.getLastFinishedBuild();
		        SloccountBuildAction lastAction = lastBuild.getAction(SloccountBuildAction.class);*/

		        ChartUtil.generateClickableMap(
		                request,
		                response,
		                GraphPoints.buildChartDelta(build,File_Path),
		                CHART_WIDTH,
		                CHART_HEIGHT);
		    }

		    
		    public void doTrendDelta(final StaplerRequest request, final StaplerResponse response) throws IOException, InterruptedException {
		       /* AbstractBuild<?,?> lastBuild = this.getLastFinishedBuild();
		        SloccountBuildAction lastAction = lastBuild.getAction(SloccountBuildAction.class);*/

		        ChartUtil.generateGraph(
		                request,
		                response,
		                GraphPoints.buildChartDelta(build,File_Path),
		                CHART_WIDTH,
		                CHART_HEIGHT);
		    }
		 
}
