package com.cognizant;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;

import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.ISVNDiffStatusHandler;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNDiffClient;
import org.tmatesoft.svn.core.wc.SVNDiffStatus;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNRevisionRange;
import org.tmatesoft.svn.core.wc.SVNStatusClient;
import org.tmatesoft.svn.core.wc.SVNStatusType;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

public class SVNDiff {
	private static final ISVNDiffStatusHandler ISVNDiffStatusHandler = null;
	private static SVNRepository repository; 
	private static ISVNAuthenticationManager authManager;
	
	
	public static void main(String[] args) throws SVNException, IOException {
DAVRepositoryFactory.setup();
String url="https://PC248889.cts.com/svn/RepoFact/";
String name = "Swati";
String password = "swati";
repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(url));
authManager = SVNWCUtil.createDefaultAuthenticationManager(name,password); 
repository.setAuthenticationManager(authManager); 
String url1="https://PC248889.cts.com/svn/RepoFact/branches";
String url2="https://PC248889.cts.com/svn/RepoFact/trunk";
long rN1 = 0;
long rN2= 0;


SVNClientManager clientManager = SVNClientManager.newInstance();
ByteArrayOutputStream result = new ByteArrayOutputStream(); 
SVNDiffClient diffClient = clientManager.getDiffClient();
SVNStatusClient diffStatus=clientManager.getStatusClient();
diffClient.doDiff(SVNURL.parseURIEncoded(url1), SVNRevision.HEAD,SVNURL.parseURIEncoded(url2), SVNRevision.HEAD,org.tmatesoft.svn.core.SVNDepth.UNKNOWN, true, result);
System.out.println(result);

File baseDirectory = new File("D:\\SVN");
//File wcRoot = new File(baseDirectory, "exampleWC");
SVNRevisionRange rangeToMerge = new SVNRevisionRange(SVNRevision.create(1), SVNRevision.HEAD);
diffClient.doMerge(SVNURL.parseURIEncoded(url1),SVNRevision.HEAD,Collections.singleton(rangeToMerge),baseDirectory, SVNDepth.INFINITY, true, false, false, false);
//diffClient.doDiffStatus(SVNURL.parseURIEncoded(url1), SVNRevision.HEAD,SVNURL.parseURIEncoded(url2), SVNRevision.HEAD, SVNDepth.EMPTY, true,ISVNDiffStatusHandler );

	}
}

