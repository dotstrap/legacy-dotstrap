package server.importer;

import java.io.*;
import java.util.*;
import java.util.logging.*;

/*
 *	This is the import you need to use the Apache Commons IO library.
 *	Look at the code marked {/*(**APACHE**)*/}
 //*/
import org.apache.commons.io.*;


import shared.model.*;
import server.database.*;
import server.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;

public class ApacheImporter
{
	public void importData(String xmlFilename) throws Exception
	{
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		File xmlFile = new File(xmlFilename);
		File dest = new File("Records");
		
		File emptydb = new File("database" + File.separator + "empty" + File.separator + "recordindexer.sqlite");
		File currentdb = new File("database" + File.separator + "recordindexer.sqlite");
		
		
		
		
		
		/*
		 * (**APACHE**)
		 */
		try
		{
			//	We make sure that the directory we are copying is not the the destination
			//	directory.  Otherwise, we delete the directories we are about to copy.
			if(!xmlFile.getParentFile().getCanonicalPath().equals(dest.getCanonicalPath()))
				FileUtils.deleteDirectory(dest);
				
			//	Copy the directories (recursively) from our source to our destination.
			FileUtils.copyDirectory(xmlFile.getParentFile(), dest);
			
			//	Overwrite my existing *.sqlite database with an empty one.  Now, my
			//	database is completelty empty and ready to load with data.
			FileUtils.copyFile(emptydb, currentdb);
		}
		catch (IOException e)
		{
			logger.log(Level.SEVERE, "Unable to deal with the IOException thrown", e);
		}
		/*
		 * (**APACHE**)
		 */
		
		
		
		
		
		
		
		File parsefile = new File(dest.getPath() + File.separator + xmlFile.getName());
		Document doc = builder.parse(parsefile);
		
		
		NodeList users = doc.getElementsByTagName("user");
		parseUsers(users);
		
		logger.info("I'm getting to here");
		NodeList projects = doc.getElementsByTagName("project");
		parseProjects(projects);
	}


	private void parseUsers(NodeList usersList)
	{
		/*
		 *	This is where you will iterate through all of the users in the
		 *	usersList NodeList and extract the information to create and insert
		 *	a user into your database.
		 */
	}


	private void parseProjects(NodeList projectsList)
	{
		/*
		 *	This is where you will iterate through all of the users in the
		 *	projectsList NodeList and extract the information to create and
		 *	insert a project into your database.
		 */
	}
}
