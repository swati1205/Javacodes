package org.jenkinsci.plugins.RemediationPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import hudson.model.BuildListener;
import jenkins.model.Jenkins;

public class Issue {

	private String issueId;
	private String issueMessage;
	private List<Cause> causes;

	DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder builder;
	Document document;

	public String getIssueId() {
		return issueId;
	}

	public void setIssueId(String issueId) {
		this.issueId = issueId;
	}

	public String getIssueMessage() {
		return issueMessage;
	}

	public void setIssueMessage(String issueMessage) {
		this.issueMessage = issueMessage;
	}

	public List<Cause> getCauses() {
		return causes;
	}

	public void setCauses(List<Cause> causes) {
		this.causes = causes;
	}

	public List<Issue> getIssueId(String error, BuildListener listener)
			throws ParserConfigurationException, SAXException, IOException {
        listener.getLogger().println(Jenkins.getInstance().getRootDir());
		//File file = new File(Jenkins.getInstance().getRootDir() + "/plugins/RemediationPlugin/issues.xml");
        File file=new File("D:\\EclipseK\\Project\\RemediationPlugin\\properties\\issues.xml");
		listener.getLogger().println("file Path:"+file.getAbsolutePath());
		List<Issue> issues=new ArrayList<Issue>();
		Issue issue=new Issue();
		if (file.exists()) {	
			listener.getLogger().println("Found issues.xml");
			builder = builderFactory.newDocumentBuilder();
			document = builder.parse(file);
			document.getDocumentElement().normalize();
			//listener.getLogger().println(document);
			NodeList list = document.getElementsByTagName("issue");
			listener.getLogger().println(list.getLength());
			for (int i = 0; i < list.getLength(); i++) {
				Node node = list.item(i);
				listener.getLogger().println(node.getNodeType());
				listener.getLogger().println(Node.ELEMENT_NODE);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					//listener.getLogger().println(node.getNodeType());
					//listener.getLogger().println(Node.ELEMENT_NODE);
					listener.getLogger().println(element.getElementsByTagName("error").getLength());
					String msg = element.getElementsByTagName("error").item(0).getTextContent();
					listener.getLogger().println("Error Message:"+msg);
					if (error.contains(msg)) {
				    issue.setIssueId(element.getElementsByTagName("id").item(0).getTextContent());
						issue.setIssueMessage(element.getElementsByTagName("error").item(0).getTextContent());
						issues.add(issue);
				
					}

				}

			}

		} else {
			listener.getLogger().println("issue.xml file not found");
			return null;

		}
		return issues;

	}

	public List<Cause> getCauses(String issueId,BuildListener listener) {
		causes = new ArrayList();

		try {
			//"D:\\EclipseK\\Project\\RemediationPlugin\\properties\\causes.xml"));
			//document = builder.parse(new File(Jenkins.getInstance().getRootDir() + "/plugins/RemediationPlugin/causes.xml"));
			document = builder.parse(new File("D:\\EclipseK\\Project\\RemediationPlugin\\properties\\causes.xml"));
			document.getDocumentElement().normalize();
			Cause cause = new Cause();
			NodeList list = document.getElementsByTagName("cause");
			listener.getLogger().println(list.getLength());
			for (int i = 0; i < list.getLength(); i++) {
				Node node = list.item(i);

				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
                      
					if (element.getAttribute("issueid").equalsIgnoreCase(issueId)) {
						cause.setCauseId(element.getElementsByTagName("causeid").item(0).getTextContent());
						cause.setCauseDescription(
								element.getElementsByTagName("causeDescription").item(0).getTextContent());
						causes.add(cause);

						return causes;

					}

				}
			}

		} catch (SAXException e) {

			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return causes;

	}

	public List<Fix> getfix(String causeId,BuildListener listener ) {

		try {
		//"D:\\EclipseK\\Project\\RemediationPlugin\\properties\\fixes.xml"));
			Fix fix=new Fix();
			List<Fix> listFix=new ArrayList<Fix>();
			builder = builderFactory.newDocumentBuilder();
			document = builder.parse(new File("D:\\EclipseK\\Project\\RemediationPlugin\\properties\\fixes.xml"));
			//document = builder.parse(new File(Jenkins.getInstance().getRootDir() + "/plugins/RemediationPlugin/fixes.xml"));
			document.getDocumentElement().normalize();
			NodeList list = document.getElementsByTagName("fix");
			listener.getLogger().println(list.getLength());
			for (int i = 0; i < list.getLength(); i++) {
				Node node = list.item(i);

				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					if (element.getAttribute("causeid").equalsIgnoreCase(causeId)) {
						fix.setFixId(element.getElementsByTagName("fixid").item(0).getTextContent());
						fix.setSolution(element.getElementsByTagName("fixDescription").item(0).getTextContent());
						listFix.add(fix);
						return listFix;

						
					}

				}
			}
		} catch (SAXException e) {

			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			
			e.printStackTrace();
		}

		return null;

	}

}
