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

import hudson.Extension;
import hudson.model.Job;
import hudson.plugins.git.util.BuildData;
import hudson.views.ListViewColumnDescriptor;
import hudson.views.ListViewColumn;

import org.kohsuke.stapler.DataBoundConstructor;

/**
 * Job list column that displays the last built Git branch.
 * 
 * @author Bernard MIEGEMOLLE
 * @version 1.0
 * @since 1.0
 */
public class LastBuiltBranch extends ListViewColumn {

    /**
     * Default constructor.
     * 
     * @since 1.0
     */
    @DataBoundConstructor
    public LastBuiltBranch() {
        super();
    }

    /**
     * Returns the last built Git branch associated with the given job.
     * 
     * @param job the job.
     * @return the last built Git branch associated with the given job, or "N/A" if this information is not available.
     * @since 1.0
     */
    public String getLastBuiltBranch(@SuppressWarnings("rawtypes") Job job) {
        try {
            BuildData buildData = job.getLastBuild().getAction(BuildData.class);
            if (buildData.getLastBuiltRevision().getBranches().size() != 1) {
                return "N/A";
            } else {
                return buildData.getLastBuiltRevision().getBranches().iterator().next().getName();
            }
        } catch (Exception e) {
            return "N/A";
        }
    }

    /**
     * The descriptor of the last built branch column.
     * 
     * @author Bernard MIEGEMOLLE
     * @version 1.0
     * @since 1.0
     */
    @Extension
    public static class DescriptorImpl extends ListViewColumnDescriptor {

        @Override
        public boolean shownByDefault() {
            return false;
        }

        @Override
        public String getDisplayName() {
            return "Last built branch";
        }

    }

}
