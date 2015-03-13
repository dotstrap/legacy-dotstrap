/**
 * DataImporter.java
 * JRE v1.7.0_76
 *
 * Created by William Myers on Mar 10, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.*;

import server.database.*;
import shared.model.*;

// TODO: Auto-generated Javadoc
/**
 * The Class DataImporter.
 */
public class DataImporter {

    /** The batch id. */
    private static int      batchID;

    /** The db. */
    private static Database db;

    /** The field id. */
    private static int      fieldID;

    /** The project id. */
    private static int      projectID;

    /** The logger. */
    private static Logger logger;

    static {
        try {
            initLog();
        } catch (IOException e) {
            System.out.println("Could not initialize log: " + e.getMessage());
        }
    }

    /**
     * Inits the log.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private static void initLog() throws IOException {

        final String logDir = "logs";
        final String logName = "indexer-importer";
        final String logFile = logName + ".log";
        Level logLevel = Level.FINE;

        File newDir = new File(logDir);
        if (!newDir.exists()) {
            newDir.mkdir();
        }

        logger = Logger.getLogger(logName);
        logger.setLevel(logLevel);
        logger.setUseParentHandlers(false);

        Handler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(logLevel);
        consoleHandler.setFormatter(new SimpleFormatter());
        logger.addHandler(consoleHandler);

        FileHandler fileHandler = new FileHandler(logDir + "/" + logFile, true);
        fileHandler.setLevel(logLevel);
        fileHandler.setFormatter(new SimpleFormatter());
        logger.addHandler(fileHandler);
    }

    /**
     * The main method.
     *
     * @param args the arguments
     * @throws Exception the exception
     */
    @SuppressWarnings("static-access")
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            return;
        }

        // prepare database to be filled
        Database.initialize();
        Database.initializeTables();
        db = new Database();
        db.startTransaction();

        // Download all associated files
        File src = new File(args[0]);
        File dest = new File("Records");
        if (!src.getParentFile().getCanonicalPath().equals(dest.getCanonicalPath())) {
            FileUtils.deleteDirectory(dest);
        }
        FileUtils.copyDirectory(src.getParentFile(), dest);

        DocumentBuilder builder = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder();
        Document doc = builder.parse(src);

        parseUsers(doc);
        parseProjects(doc);

        db.endTransaction(true);
    }

    /**
     * Parses the fields.
     *
     * @param projElem the proj elem
     * @return the array list
     */
    public static ArrayList<Field> parseFields(Element projElem) {
        NodeList fieldList = projElem.getElementsByTagName("field");
        for (int j = 0; j < fieldList.getLength(); ++j) {
            Field field = new Field();

            Element fieldElem = (Element) fieldList.item(j);

            // Get Field elements
            Element ftitleElem = (Element) fieldElem.getElementsByTagName("title")
                    .item(0);
            Element xElem = (Element) fieldElem.getElementsByTagName("xcoord").item(
                    0);
            Element widthElem = (Element) fieldElem.getElementsByTagName("width")
                    .item(0);
            Element helpElem = (Element) fieldElem.getElementsByTagName("helphtml")
                    .item(0);
            Element knownElem = (Element) fieldElem
                    .getElementsByTagName("knowndata").item(0);

            // Get field info
            String ftitle = ftitleElem.getTextContent();
            int xCoord = Integer.parseInt(xElem.getTextContent());
            int width = Integer.parseInt(widthElem.getTextContent());
            String helphtml = helpElem.getTextContent();
            String knowndata = "";
            if (knownElem != null) {
                knowndata = "Records/" + knownElem.getTextContent();
            }

            // create field
            field.setTitle(ftitle);
            field.setX(xCoord);
            field.setWidth(width);
            field.setHelp("Records/" + helphtml);
            field.setKnownPath(knowndata);
            field.setProjectID(projectID);

            // add field to database
            FieldDAO fieldDAO = db.getFieldDAO();
            fieldID = fieldDAO.add(field);
        }
        return db.getFieldDAO().getAll();
    }

    /**
     * Parses the images.
     *
     * @param projElem the proj elem
     * @param fields the fields
     */
    public static void parseImages(Element projElem, ArrayList<Field> fields) {
        NodeList imageList = projElem.getElementsByTagName("image");
        for (int t = 0; t < imageList.getLength(); ++t) {
            Batch image = new Batch();

            // get batch element
            Element imageElem = (Element) imageList.item(t);

            // get file element
            Element fileElem = (Element) imageElem.getElementsByTagName("file")
                    .item(0);

            // get batch info
            String imageFile = fileElem.getTextContent();

            // create batch
            image.setFilePath("Records/" + imageFile);
            image.setProjectID(projectID);

            // add batch to database
            BatchDAO batchDAO = db.getBatchDAO();
            batchID = batchDAO.create(image);

            parseRecords(imageElem, fields);
        }
    }

    /**
     * Parses the projects.
     *
     * @param doc the doc
     */
    public static void parseProjects(Document doc) {
        NodeList projectsList = doc.getElementsByTagName("projects");
        for (int i = 0; i < projectsList.getLength(); ++i) {
            NodeList projectList = doc.getElementsByTagName("project");
            for (int k = 0; k < projectList.getLength(); ++k) {
                Project project = new Project();

                Element projElem = (Element) projectList.item(k);

                // Get Project Elements
                Element titleElem = (Element) projElem.getElementsByTagName("title")
                        .item(0);
                Element rpiElem = (Element) projElem.getElementsByTagName(
                        "recordsperimage").item(0);
                Element firstYElem = (Element) projElem.getElementsByTagName(
                        "firstycoord").item(0);
                Element recordElem = (Element) projElem.getElementsByTagName(
                        "recordheight").item(0);

                // Get Project info
                String title = titleElem.getTextContent();
                int recordsPerImage = Integer.parseInt(rpiElem.getTextContent());
                int firstYCoord = Integer.parseInt(firstYElem.getTextContent());
                int recordHeight = Integer.parseInt(recordElem.getTextContent());

                // Create project
                ProjectInfo p = new ProjectInfo(title);
                project.setProjInfo(p);
                project.setRecordsPerBatch(recordsPerImage);
                project.setFirstY(firstYCoord);
                project.setRecordHeight(recordHeight);

                // Add project to database
                ProjectDAO projectDAO = db.getProjectDAO();
                projectID = projectDAO.add(project);

                ArrayList<Field> fields = parseFields(projElem);

                parseImages(projElem, fields);
            }
        }
    }

    /**
     * Parses the records.
     *
     * @param imageElem the image elem
     * @param fields the fields
     */
    public static void parseRecords(Element imageElem, ArrayList<Field> fields) {
        NodeList recordList = imageElem.getElementsByTagName("record");
        int t = 0;
        for (int s = 0; s < recordList.getLength(); ++s) {
            Element imageRecordElem = (Element) recordList.item(s);

            NodeList valueList = imageRecordElem.getElementsByTagName("value");
            int fieldIndex = fields.size() - valueList.getLength();
            Field field = fields.get(fieldIndex);
            fieldID = field.getID();
            for (int r = 0; r < valueList.getLength(); ++r) {
                Record record = new Record();

                // get value element
                Element valueElem = (Element) valueList.item(r);

                // get value info as upper case
                String value = valueElem.getTextContent().toUpperCase();

                // create record
                record.setData(value);
                record.setBatchID(batchID);
                record.setFieldID(fieldID);
                record.setRecordNumber(t + 1);

                // add record to database
                RecordDAO recordDAO = db.getRecordDAO();
                recordDAO.add(record);
                fieldID++;
            }
            t++;
        }
    }

    /**
     * Parses the users.
     *
     * @param doc the doc
     */
    public static void parseUsers(Document doc) {
        NodeList usersList = doc.getElementsByTagName("users");
        for (int k = 0; k < usersList.getLength(); ++k) {
            NodeList userList = doc.getElementsByTagName("user");
            for (int i = 0; i < userList.getLength(); ++i) {
                User user = new User();
                Element userElem = (Element) userList.item(i);

                // Get User elements
                Element usernameElem = (Element) userElem.getElementsByTagName(
                        "username").item(0);
                Element passElem = (Element) userElem.getElementsByTagName(
                        "password").item(0);
                Element firstElem = (Element) userElem.getElementsByTagName(
                        "firstname").item(0);
                Element lastElem = (Element) userElem.getElementsByTagName(
                        "lastname").item(0);
                Element emailElem = (Element) userElem.getElementsByTagName("email")
                        .item(0);
                Element indexedElem = (Element) userElem.getElementsByTagName(
                        "indexedrecords").item(0);

                // Get user information into string
                String username = usernameElem.getTextContent();
                String password = passElem.getTextContent();
                String firstname = firstElem.getTextContent();
                String lastname = lastElem.getTextContent();
                String email = emailElem.getTextContent();
                int indexed = Integer.parseInt(indexedElem.getTextContent());

                // Create user
                Credentials creds = new Credentials(username, password);
                UserInfo userInfo = new UserInfo(firstname, lastname, email);
                user.setCreds(creds);
                user.setUserInfo(userInfo);
                user.setRecordCount(indexed);
                user.setCurrentBatch(0);

                // Add user to database
                UserDAO userDAO = db.getUserDAO();
                userDAO.add(user);
            }
        }
    }
}
