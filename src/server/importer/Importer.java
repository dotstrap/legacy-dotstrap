/**
 * Importer.java
 * JRE v1.8.0_40
 *
 * Created by William Myers on Mar 23, 2015.
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
  private static String importDir;
  private static String importFileName;

  /**
   * The main method
   *
   * @param args the xml file to import into the database
   */
  public static void main(String[] args) {
    try {
      final FileInputStream is = new FileInputStream("logging.properties");
      LogManager.getLogManager().readConfiguration(is);
      logger = Logger.getLogger("importer");
    } catch (final IOException e) {
      Logger.getAnonymousLogger().severe("ERROR: unable to load logging properties file...");
      Logger.getAnonymousLogger().severe(e.getMessage());
    }

    if (args.length != 1) {
      logger.log(Level.SEVERE, "ERROR: Invalid arguements specified...");
      return;
    }

    // try opening file
    final File xmlImportFile = new File(args[0]);
    if (!xmlImportFile.exists()) {
      logger.log(Level.SEVERE, "ERROR: specified import file does not exist...");
      return;
    } else {
      importFileName = xmlImportFile.getAbsolutePath();
      importDir = xmlImportFile.getParentFile().getName();
    }

    try {// @formatter:off
//      if (!xmlImportFile.getParentFile().getCanonicalPath()
//          .equals(destImportDir.getCanonicalPath())) {
//        FileUtils.deleteDirectory(destImportDir);
//      }
//      FileUtils.copyDirectory(xmlImportFile.getParentFile(), destImportDir); // @formatter:on
      Database.initDriver();

      final Database db = new Database();
      db.startTransaction();
      db.initTables();
      db.endTransaction(true);

      logger.info("Creating new database from template: " + Database.DB_TEMPLATE);
      final File activeDB = new File(Database.DB_FILE);
      final File templateDB = new File(Database.DB_TEMPLATE);
      FileUtils.copyFile(activeDB, templateDB);

      final DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
      final DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
      final Document doc = docBuilder.parse(xmlImportFile);
      doc.getDocumentElement().normalize();
      final Element root = doc.getDocumentElement();

      logger.info("Importing: " + importFileName + "...");
      new Importer().importData(root);

    } catch (final Exception e) {
      logger.log(Level.SEVERE, e.toString());
      logger.log(Level.FINE, "STACKTRACE: ", e);
    }
    logger.info("Importing complete.");
    return;
  }

  private String getValue(Element elem) {
    String result = "";
    final Node child = elem.getFirstChild();
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
    final ArrayList<Element> result = new ArrayList<Element>();
    final NodeList children = node.getChildNodes();

    for (int i = 0; i < children.getLength(); i++) {
      final Node child = children.item(i);
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
    final ArrayList<Element> rootElems = getChildElements(root);

    logger.info("Parsing and importing all users etc. to: " + Database.DB_FILE + "...");
    for (final Element curr : getChildElements(rootElems.get(0))) {
      loadUsers(curr);
    }
    logger.info("Parsing and importing all projects etc. to: " + Database.DB_FILE + "...");
    for (final Element curr : getChildElements(rootElems.get(1))) {
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
    final Element usernameElem = (Element) userElem.getElementsByTagName("username").item(0);
    final Element passElem = (Element) userElem.getElementsByTagName("password").item(0);
    final Element firstElem = (Element) userElem.getElementsByTagName("firstname").item(0);
    final Element lastElem = (Element) userElem.getElementsByTagName("lastname").item(0);
    final Element emailElem = (Element) userElem.getElementsByTagName("email").item(0);
    final Element indexedElem = (Element) userElem.getElementsByTagName("indexedrecords").item(0);

    // Get all User primitives from User Elements
    final String username = usernameElem.getTextContent();
    final String password = passElem.getTextContent();
    final String firstName = firstElem.getTextContent();
    final String lastName = lastElem.getTextContent();
    final String email = emailElem.getTextContent();
    final int indexedRecords = Integer.parseInt(indexedElem.getTextContent());

    // Create new User and add it to the database
    final Database db = new Database();
    try {
      db.startTransaction();

      final User newUser =
          new User(username, password, firstName, lastName, email, indexedRecords, 0);
      db.getUserDAO().create(newUser);

      db.endTransaction(true);
    } catch (final DatabaseException e) {
      logger.log(Level.SEVERE, e.toString());
      logger.log(Level.FINE, "STACKTRACE: ", e);
    }
  }

  /**
   * Inserts a Project element into database
   */
  private void loadProjects(Element projectElem) {
    // Get Project Elements
    final Element titleElem = (Element) projectElem.getElementsByTagName("title").item(0);
    final Element recPerImgElem =
        (Element) projectElem.getElementsByTagName("recordsperimage").item(0);
    final Element firstYElem = (Element) projectElem.getElementsByTagName("firstycoord").item(0);
    final Element recordElem = (Element) projectElem.getElementsByTagName("recordheight").item(0);

    // Get Project primitives from Project elements
    final String title = titleElem.getTextContent();
    final int recordsPerImage = Integer.parseInt(recPerImgElem.getTextContent());
    final int firstYCoord = Integer.parseInt(firstYElem.getTextContent());
    final int recordHeight = Integer.parseInt(recordElem.getTextContent());

    int projectId = -1;
    final Database db = new Database();
    // Create new project and add it to the database
    try {
      db.startTransaction();

      final Project newProject = new Project(title, recordsPerImage, firstYCoord, recordHeight);
      projectId = db.getProjectDAO().create(newProject);
      assert (projectId > 0);

      db.endTransaction(true);
    } catch (final DatabaseException e) {
      logger.log(Level.SEVERE, e.toString());
      logger.log(Level.FINE, "STACKTRACE: ", e);
    }

    // Get project fields and images
    final ArrayList<Element> children = getChildElements(projectElem);
    final ArrayList<Element> fields = getChildElements(children.get(4));
    final ArrayList<Element> images = getChildElements(children.get(5));

    // Add fields to database
    int colNum = 1;
    for (final Element curr : fields) {
      loadFields(curr, projectId, colNum++);
    }
    // Add images to database
    for (final Element curr : images) {
      loadBatches(curr, projectId);
    }
  }

  /**
   * Inserts a Field element into database
   */
  private void loadFields(Element fieldElem, int projectId, int colNum) {
    // Get Field elements
    final Element titleElem = (Element) fieldElem.getElementsByTagName("title").item(0);
    final Element xCoordElem = (Element) fieldElem.getElementsByTagName("xcoord").item(0);
    final Element knownDataElem = (Element) fieldElem.getElementsByTagName("knowndata").item(0);
    final Element helpElem = (Element) fieldElem.getElementsByTagName("helphtml").item(0);
    final Element widthElem = (Element) fieldElem.getElementsByTagName("width").item(0);

    // Get Field primitives from Field elements
    final String title = titleElem.getTextContent();
    final int xCoord = Integer.parseInt(xCoordElem.getTextContent());
    String knownData = "";
    final String helpHtml = importDir + "/" + helpElem.getTextContent();
    final int width = Integer.parseInt(widthElem.getTextContent());

    if (knownDataElem != null) {
      knownData = importDir + "/" + knownDataElem.getTextContent();
    }

    final Database db = new Database();
    try {
      db.startTransaction();

      final Field newField =
          new Field(projectId, title, knownData, helpHtml, xCoord, width, colNum);
      db.getFieldDAO().create(newField);

      db.endTransaction(true);
    } catch (final DatabaseException e) {
      logger.log(Level.SEVERE, e.toString());
      logger.log(Level.FINE, "STACKTRACE: ", e);
    }
  }

  /**
   * Inserts an Image element into database
   */
  private void loadBatches(Element batchElem, int projectId) {
    // get file element
    final Element batchFileElem = (Element) batchElem.getElementsByTagName("file").item(0);

    // get Batch primitive from batch file element
    final String batchUrl = importDir + "/" + batchFileElem.getTextContent();

    int batchId = -1;
    ArrayList<Element> records = null;
    final Database db = new Database();
    try {
      db.startTransaction();

      final Batch newBatch = new Batch(batchUrl, projectId, Batch.INCOMPLETE, -1);
      batchId = db.getBatchDAO().create(newBatch);
      assert (batchId > 0);

      db.endTransaction(true);
    } catch (final DatabaseException e) {
      logger.log(Level.SEVERE, e.toString());
      logger.log(Level.FINE, "STACKTRACE: ", e);
    }

    if (contains(batchElem, "records")) {
      final ArrayList<Element> children = getChildElements(batchElem);
      records = getChildElements(children.get(1));

      int rowNum = 1;
      for (final Element curr : records) {
        loadRecords(curr, projectId, batchId, batchUrl, rowNum++);
      }
    }
  }

  /**
   * Inserts a Record element into the database
   */
  private void loadRecords(Element recordElem, int projectId, int batchId, String batchUrl,
      int rowNum) {
    final ArrayList<Element> children = getChildElements(recordElem);
    final ArrayList<Element> records = getChildElements(children.get(0));

    final int colNum = 1;
    for (final Element curr : records) {
      final String recordData = getValue(curr);
      final Database db = new Database();
      int fieldId = -1;
      try {
        db.startTransaction();

        fieldId = db.getFieldDAO().getFieldId(projectId, colNum);
        assert (fieldId > 0);
        final Record newRecord = new Record(fieldId, batchId, batchUrl, recordData, rowNum, colNum);
        db.getRecordDAO().create(newRecord);

        db.endTransaction(true);
      } catch (final DatabaseException e) {
        logger.log(Level.SEVERE, e.toString());
        logger.log(Level.FINE, "STACKTRACE: ", e);
      }
    }
  }
}
