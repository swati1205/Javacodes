/*
The MIT License (MIT)

Copyright (c) 2014 Bernard Miegemolle

Permission is hereby granted, free of charge, to any person obtaining a copy of
this software and associated documentation files (the "Software"), to deal in
the Software without restriction, including without limitation the rights to
use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
the Software, and to permit persons to whom the Software is furnished to do so,
subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package jenkins.plugins.gitbuilddata;

import hudson.model.ProminentProjectAction;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
//import hudson.plugins.git.util.Build;
//import hudson.plugins.git.util.BuildData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

/**
 * Project action (i.e. action displayed on the job summary page) that displays the list of built branches with their
 * status.
 * 
 * @author Bernard MIEGEMOLLE
 * @version 1.0
 * @since 1.0
 */
public class BuiltBranchesProjectAction implements ProminentProjectAction {

    @SuppressWarnings("rawtypes")
    private AbstractProject<? extends AbstractProject, ? extends AbstractBuild> project;

    /**
     * Constructs the project action.
     * 
     * @param project the project for which this action is requested.
     * @see BuiltBranchesProjectActionFactory
     * @since 1.0
     */
    public BuiltBranchesProjectAction(AbstractProject<? extends AbstractProject, ? extends AbstractBuild> project) {
        this.project = project;
    }

    /**
     * Returns the built branches, i.e. a list of {@link BranchStatus} that contains Git branches associated with their
     * last build number and status.
     * 
     * @since 1.0
     */
    public List<BranchStatus> getBuiltBranches() {
        try {
            BuildData buildData = project.getLastBuild().getAction(BuildData.class);
            List<BranchStatus> builtBranches = new ArrayList<BuiltBranchesProjectAction.BranchStatus>();
            for (Entry<String, Build> build : buildData.getBuildsByBranchName().entrySet()) {
                if (project.getBuildByNumber(build.getValue().getBuildNumber()) != null) {
                    BranchStatus branchStatus = new BranchStatus(build.getKey(), build.getValue().getBuildNumber(),
                            project.getBuildByNumber(build.getValue().getBuildNumber()).getUrl(), project
                                    .getBuildByNumber(build.getValue().getBuildNumber()).getResult().color.getImage());
                    builtBranches.add(branchStatus);
                }
            }
            return builtBranches;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Indicates whether the project is a Git project or not.
     * 
     * @since 1.0
     */
    public boolean isGitProject() {
        return (project.getLastBuild() != null) && (project.getLastBuild().getAction(BuildData.class) != null);
    }

    public String getIconFileName() {
        return null;
    }

    public String getDisplayName() {
        return "Built branches";
    }

    public String getUrlName() {
        return null;
    }

    /**
     * A branch status, i.e. the branch name associated with its last build number and status.
     * 
     * @author Bernard MIEGEMOLLE
     * @version 1.0
     * @since 1.0
     */
    public class BranchStatus {

        private String branchName;
        private int buildNumber;
        private String buildUrl;
        private String buildStatus;

        public BranchStatus(String branchName, int buildNumber, String buildUrl, String buildStatus) {
            this.branchName = branchName;
            this.buildNumber = buildNumber;
            this.buildUrl = buildUrl;
            this.buildStatus = buildStatus;
        }

        public String getBranchName() {
            return branchName;
        }

        public int getBuildNumber() {
            return buildNumber;
        }

        public String getBuildUrl() {
            return buildUrl;
        }

        public String getBuildStatus() {
            return buildStatus;
        }

    }

}
