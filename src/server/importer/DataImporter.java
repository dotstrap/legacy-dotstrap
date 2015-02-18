package server.importer;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import server.database.Database;
import shared.model.Field;

// TODO: Auto-generated Javadoc
/**
 * The Class DataImporter.
 */
public class DataImporter {

    /** The db. */
    private static Database db;

    /** The project id. */
    private static int      projectID;

    /** The field id. */
    private static int      fieldID;

    /** The batch id. */
    private static int      batchID;

    /**
     * The main method.
     *
     * @param args
     *            the arguments
     * @throws Exception
     *             the exception
     */
    @SuppressWarnings("static-access")
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            return;
        }

    }

    /**
     * Parses the users.
     *
     * @param doc
     *            the doc
     */
    public static void parseUsers(Document doc) {
       


    }

    /**
     * Parses the projects.
     *
     * @param doc
     *            the doc
     */
    public static void parseProjects(Document doc) {
       

    }

    /**
     * Parses the fields.
     *
     * @param projElem
     *            the proj elem
     * @return the array list
     */
    public static ArrayList<Field> parseFields(Element projElem) {
     
       return null;
    }

    /**
     * Parses the images.
     *
     * @param projElem
     *            the proj elem
     * @param fields
     *            the fields
     */
    public static void parseImages(Element projElem, ArrayList<Field> fields) {
       
        
    }

    /**
     * Parses the records.
     *
     * @param imageElem
     *            the image elem
     * @param fields
     *            the fields
     */
    public static void parseRecords(Element imageElem, ArrayList<Field> fields) {

    }
}
