package com.jbhunt.copyartifacts;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.log4j.Logger;

import java.util.Properties;
import java.io.FileNotFoundException;


/**
 * Java program to copy artifacts from Jenkins workspace to respective folder of
 * CIDeliverables
 * 
 * @author Cognizant
 */

public class copyartifacts {

	// Initialize the variable and logger

	private static org.apache.log4j.Logger logger = Logger
			.getLogger(copyartifacts.class);

	private final String version;
	private final String classifier;
	private final String environment;
	private static final int NUM_ARGS = 3;

	/**
	 * Constructor to set the values.
	 * 
	 * @param version
	 *            The version number of the artifact to be copied
	 * @param classifier
	 *            Classifier of the artifact
	 */

	public copyartifacts(final String version, final String classifier,
			final String environment) {

		this.version = version;
		this.classifier = classifier;
		this.environment = environment;

	}

	/**
	 * Copy the artifacts and returns if it is a success.
	 * 
	 * @return Boolean true if Copy process is a success
	 */

	public boolean copy() {

		// Reading values from properties file

		String spath;
		try {
			spath = getPropData("spath");
			String tpath = getPropData("tpath");

			// absolute path for source file to be copied

			String source = spath;

			File dir = new File(spath);
			FileFilter fileFilter = new WildcardFileFilter("*." + classifier);

			// directory where file will be copied

			String target = tpath + "/" + environment + "/";

			File[] files = dir.listFiles(fileFilter);
			for (int i = 0; i < files.length; i++) {
				String name = files[i].getName();
				File sourceFile = new File(source + name);
				File targetFile = new File(target + name);
				System.out.println("Copying file from : " + source + name
						+ " is started");
				FileUtils.copyFile(sourceFile, targetFile);
				System.out.println("Copying of file from : " + source + name
						+ "  to  " + target + name + " is completed");
			}

		} catch (FileNotFoundException e1) {

			e1.printStackTrace();
		} catch (IOException e1) {

			e1.printStackTrace();
		}

		return true;
	}

	/**
	 * getPropData method - Load the CopyArtifacts Properties file
	 * 
	 * @param properties
	 *            FileName and properties data
	 * @throws IOException
	 * @throws FileNotFoundException
	 */

	public static String getPropData(String text) throws FileNotFoundException,
			IOException {
		// using properties class to load the properties file
		Properties properties = new Properties();
		properties.load(new FileInputStream("copyartifacts.properties"));
		try {
			String propText = null;
			if (properties.getProperty(text) != null) {
				propText = properties.getProperty(text);
				logger.info(text + " data is " + propText);

			} else {

				logger.error("No data available in properties file for " + text);
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return properties.getProperty(text);
	}

	public static void main(final String[] args) {

		// Case where all information is received from command line
		if (args.length != NUM_ARGS) {
			System.out
					.println("Command should be of form\n\tjava -jar copyartifacts.jar "
							+ "<Version> <Classifier> <environment>");
			System.exit(1);
		}

		String version = args[0];
		String classifier = args[1];
		String environment = args[2];

		// Verify if all the development versions are snapshot
		if (((version.endsWith("-SNAPSHOT")) && (environment
				.equalsIgnoreCase("Dev")))) {

			copyartifacts ca = new copyartifacts(version, classifier,
					environment);
			boolean isSuccess = ca.copy();
			if (isSuccess) {
				System.out.println("Done.");
			} else {
				System.out.println("Failed.");
				System.exit(1);
			}
		} else if ((!version.endsWith("-SNAPSHOT"))
				&& (!environment.equalsIgnoreCase("Dev"))) {
			copyartifacts ca = new copyartifacts(version, classifier,
					environment);
			boolean isSuccess = ca.copy();
			if (isSuccess) {
				System.out.println("Done.");
			} else {
				System.out.println("Failed.");
				System.exit(1);
			}
		}

	}
}
