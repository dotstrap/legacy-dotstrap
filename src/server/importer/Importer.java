/**
 * Importer.java
 * JRE v1.8.0_40
 *
 * Created by William Myers on Mar 24, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package server.importer;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

//import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.FileUtils;
import org.w3c.dom.*;

import server.database.Database;
import server.database.DatabaseException;

import shared.model.*;

/**
 * Imports data from an XML file to database
 */
public class Importer {
  private static Logger logger;
  private static String importDirName;
  private static String importFileName;

  /**
   * The main method
   *
   * @param args the xml file to import into the database
   */
  public static void main(String[] args) {
    File logDir = new File("logs");
    if (!logDir.exists()) {
      try {
        logDir.mkdir();
      } catch (SecurityException e) {
        System.out.println("ERROR: do not have permission to create log directory...");
      }
    }

    try {
      FileInputStream is = new FileInputStream("logging.properties");
      LogManager.getLogManager().readConfiguration(is);
      logger = Logger.getLogger("importer");
    } catch (IOException e) {
      System.out.println("ERROR: unable to load logging properties file...");
    }
    logger.info("===============Initialized " + logger.getName() + " log...");

    if (args.length != 1) {
      return;
    }

    // try opening file
    File xmlImportFile = new File(args[0]);
    if (!xmlImportFile.exists()) {
      logger.log(Level.SEVERE, "requested file: " + args[0] + " does not exist...");
      return;
    } else {
      importFileName = xmlImportFile.getAbsolutePath();
      importDirName = xmlImportFile.getParentFile().getName(); // used later in URL prefix
    }

    try {
      File dest = new File(importDirName);
      // does nothing if import directory is same as the destination directory, otherwise copy the
      // directory into the projects' directory structure
      if (!xmlImportFile.getParentFile().getCanonicalPath().equals(dest.getCanonicalPath())) {
        FileUtils.deleteDirectory(dest);
        FileUtils.copyDirectory(xmlImportFile.getParentFile(), dest);
      }

      Database.initDriver();

      Database db = new Database();
      db.startTransaction();
      db.initTables();
      db.endTransaction(true);

      DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
      Document doc = docBuilder.parse(xmlImportFile);
      doc.getDocumentElement().normalize();
      Element root = doc.getDocumentElement();

      logger.info("Importing: " + importFileName + "...");
      new Importer().importData(root);

    } catch (Exception e) {
      logger.log(Level.SEVERE, "STACKTRACE: ", e);
    }
    logger.info("Importing complete.");
    return;
  }

  private String getValue(Element elem) {
    String result = "";
    Node child = elem.getFirstChild();
    result = child.getNodeValue();
    return result;
  }

  /**
   * Checks if an Element contains a certain attribute
   *
   * @param elem the element to check for the attr
   * @param attr the attribute to check
   * @return true if elem > 0
   */
  private boolean contains(Element elem, String attr) {
    return elem.getElementsByTagName(attr).getLength() > 0;
  }

  /**
   * Gets the children elements of a Node
   */
  private ArrayList<Element> getChildElements(Node node) {
    ArrayList<Element> result = new ArrayList<Element>();
    NodeList children = node.getChildNodes();

    for (int i = 0; i < children.getLength(); i++) {
      Node child = children.item(i);
      if (child.getNodeType() == Node.ELEMENT_NODE) {
        result.add((Element) child);
      }
    }
    return result;
  }

  /**
   * Loads record indexer data into memory and the database
   *
   * @param root
   */
  private void importData(Element root) {
    ArrayList<Element> rootElems = getChildElements(root);

    logger.info("Parsing and importing all users etc. to: " + Database.DB_FILE + "...");
    for (Element curr : getChildElements(rootElems.get(0))) {
      loadUsers(curr);
    }
    logger.info("Parsing and importing all projects etc. to: " + Database.DB_FILE + "...");
    for (Element curr : getChildElements(rootElems.get(1))) {
      loadProjects(curr);
    }
  }

  /**
   * Inserts User element into database
   *
   * @param userElem
   */
  private void loadUsers(Element userElem) {
    // Get all User elements
    Element usernameElem = (Element) userElem.getElementsByTagName("username").item(0);
    Element passElem = (Element) userElem.getElementsByTagName("password").item(0);
    Element firstElem = (Element) userElem.getElementsByTagName("firstname").item(0);
    Element lastElem = (Element) userElem.getElementsByTagName("lastname").item(0);
    Element emailElem = (Element) userElem.getElementsByTagName("email").item(0);
    Element indexedElem = (Element) userElem.getElementsByTagName("indexedrecords").item(0);

    // Get all User primitives from User Elements
    String username = usernameElem.getTextContent();
    String password = passElem.getTextContent();
    String firstName = firstElem.getTextContent();
    String lastName = lastElem.getTextContent();
    String email = emailElem.getTextContent();
    int indexedRecords = Integer.parseInt(indexedElem.getTextContent());

    // Create new User and add it to the database
    Database db = new Database();
    try {
      db.startTransaction();

      User newUser = new User(username, password, firstName, lastName, email, indexedRecords, 0);
      db.getUserDAO().create(newUser);

      db.endTransaction(true);
    } catch (DatabaseException e) {
      logger.log(Level.SEVERE, "STACKTRACE: ", e);
    }
  }

  /**
   * Inserts a Project element into database
   */
  private void loadProjects(Element projectElem) {
    // Get Project Elements
    Element titleElem = (Element) projectElem.getElementsByTagName("title").item(0);
    Element recPerImgElem = (Element) projectElem.getElementsByTagName("recordsperimage").item(0);
    Element firstYElem = (Element) projectElem.getElementsByTagName("firstycoord").item(0);
    Element recordElem = (Element) projectElem.getElementsByTagName("recordheight").item(0);

    // Get Project primitives from Project elements
    String title = titleElem.getTextContent();
    int recordsPerImage = Integer.parseInt(recPerImgElem.getTextContent());
    int firstYCoord = Integer.parseInt(firstYElem.getTextContent());
    int recordHeight = Integer.parseInt(recordElem.getTextContent());

    int projectId = -1;
    Database db = new Database();
    // Create new project and add it to the database
    try {
      db.startTransaction();

      Project newProject = new Project(title, recordsPerImage, firstYCoord, recordHeight);
      projectId = db.getProjectDAO().create(newProject);
      assert (projectId > 0);

      db.endTransaction(true);
    } catch (DatabaseException e) {
      logger.log(Level.SEVERE, "STACKTRACE: ", e);
    }

    // Get project fields and images
    ArrayList<Element> children = getChildElements(projectElem);
    ArrayList<Element> fields = getChildElements(children.get(4));
    ArrayList<Element> batches = getChildElements(children.get(5));

    // Add fields to database
    int colNum = 0;
    for (Element curr : fields) {
      loadFields(curr, projectId, colNum++);
    }
    // Add images to database
    for (Element curr : batches) {
      loadBatches(curr, projectId);
    }
  }

  /**
   * Inserts a Field element into database
   */
  private void loadFields(Element fieldElem, int projectId, int colNum) {
    // Get Field elements
    Element titleElem = (Element) fieldElem.getElementsByTagName("title").item(0);
    Element xCoordElem = (Element) fieldElem.getElementsByTagName("xcoord").item(0);
    Element knownDataElem = (Element) fieldElem.getElementsByTagName("knowndata").item(0);
    Element helpElem = (Element) fieldElem.getElementsByTagName("helphtml").item(0);
    Element widthElem = (Element) fieldElem.getElementsByTagName("width").item(0);

    // Get Field primitives from Field elements
    String title = titleElem.getTextContent();
    int xCoord = Integer.parseInt(xCoordElem.getTextContent());
    String knownData = "";
    String helpHtml = importDirName + "/" + helpElem.getTextContent();
    int width = Integer.parseInt(widthElem.getTextContent());

    if (knownDataElem != null) {
      knownData = importDirName + "/" + knownDataElem.getTextContent();
    }

    Database db = new Database();
    try {
      db.startTransaction();

      Field newField = new Field(projectId, title, knownData, helpHtml, xCoord, width, colNum);
      db.getFieldDAO().create(newField);

      db.endTransaction(true);
    } catch (DatabaseException e) {
      logger.log(Level.SEVERE, "STACKTRACE: ", e);
    }
  }

  /**
   * Inserts an Image element into database
   */
  private void loadBatches(Element batchElem, int projectId) {
    // get file element
    Element batchFileElem = (Element) batchElem.getElementsByTagName("file").item(0);

    // get Batch primitive from batch file element
    String batchUrl = importDirName + "/" + batchFileElem.getTextContent();

    int batchId = -1;
    ArrayList<Element> records = null;
    Database db = new Database();
    try {
      db.startTransaction();

      Batch newBatch = new Batch(batchUrl, projectId, Batch.INCOMPLETE, -1);
      batchId = db.getBatchDAO().create(newBatch);
      assert (batchId > 0);

      db.endTransaction(true);
    } catch (DatabaseException e) {
      logger.log(Level.SEVERE, "STACKTRACE: ", e);
    }

    if (contains(batchElem, "records")) {
      ArrayList<Element> children = getChildElements(batchElem);
      records = getChildElements(children.get(1));

      int colNum = 0;
      for (Element curr : records) {
        loadRecords(curr, projectId, batchId, batchUrl, colNum++);
      }
    }
  }

  /**
   * Inserts a Record element into the database
   */
  private void loadRecords(Element recordElem, int projectId, int batchId, String batchUrl,
      int rowNum) {
    ArrayList<Element> children = getChildElements(recordElem);
    ArrayList<Element> records = getChildElements(children.get(0));

    int colNum = 0;
    for (Element curr : records) {
      String recordData = getValue(curr);
      Database db = new Database();
      int fieldId = -1;
      try {
        db.startTransaction();

        fieldId = db.getFieldDAO().getFieldId(projectId, colNum);
        assert (fieldId > 0);
        Record newRecord = new Record(fieldId, batchId, batchUrl, recordData, rowNum, colNum);
        db.getRecordDAO().create(newRecord);

        db.endTransaction(true);
      } catch (DatabaseException e) {
        logger.log(Level.SEVERE, "STACKTRACE: ", e);
      }
      colNum++;
    }
  }
}
