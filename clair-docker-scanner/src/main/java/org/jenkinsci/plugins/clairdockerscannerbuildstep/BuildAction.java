package org.jenkinsci.plugins.clairdockerscannerbuildstep;

import java.io.Serializable;

import org.kohsuke.stapler.StaplerProxy;

import hudson.model.Action;
import hudson.model.AbstractBuild;
import hudson.model.Run;
import hudson.util.ChartUtil.NumberOnlyBuildLabel;

public class BuildAction implements Action, Serializable {
	public static final String URL_NAME = "sloccountResult";

    private AbstractBuild<?,?> build;
	public BuildAction(AbstractBuild build) {
		
		this.build=build;
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

	
	BuildAction getPreviousAction(){
        if(this.build == null){
            return null;
        }
System.out.println("inside build action");
        AbstractBuild<?,?> previousBuild = this.build.getPreviousBuild();

        while(previousBuild != null){
         BuildAction action = previousBuild.getAction(BuildAction.class);

           // if (action != null) {
               // SloccountResult result = action.getResult();
                
               // if(result != null && !result.isEmpty()) {
                    return action;
               // }
            }

            previousBuild = previousBuild.getPreviousBuild();
            

        return null;
    }

	public Run<?, ?> getBuild() {
		
		return this.build;
	}
	
}
