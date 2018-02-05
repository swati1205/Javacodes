package org.tmatesoft.svn.examples.wc;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNDiffClient;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

public class SVNDiff {
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
System.out.println(repository);
System.out.println(authManager);
String url1="https://PC248889.cts.com/svn/RepoFact/branches";
String url2="https://PC248889.cts.com/svn/RepoFact/trunk";
long rN1 = 0;
long rN2= 0;


SVNClientManager clientManager = SVNClientManager.newInstance();
ByteArrayOutputStream result = new ByteArrayOutputStream(); 
SVNDiffClient diffClient = clientManager.getDiffClient();
diffClient.doDiff(SVNURL.parseURIEncoded(url1), SVNRevision.create(rN1),SVNURL.parseURIEncoded(url2), SVNRevision.create(rN2),org.tmatesoft.svn.core.SVNDepth.UNKNOWN, true, result);
System.out.println(result);
	}

}