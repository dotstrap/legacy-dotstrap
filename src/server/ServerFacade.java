package server;

import java.util.List;

import server.ServerException;
import server.database.*;
import shared.model.*;
import shared.communication.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 */
public class ServerFacade {
    /** The logger used throughout the project. */
    private static Logger logger;

    public static void initialize() throws ServerException {
        try {
            logger = Logger.getLogger("server");
            Database.initDriver();
        }
        catch (DatabaseException e) {
            throw new ServerException(e.getMessage(), e);
        }
    }

    /**
     * Validates a username/password combination
     * @throws ServerException
     */
    public static ValidateUserResult validateUser(ValidateUserParameters params) throws ServerException {
        Database db = new Database();
        boolean isValid = false;
        String username = params.getUsername();
        String password = params.getPassword();
        User user = null;
        try {
            db.startTransaction();
            user = db.getUserDAO().read(username, password);
            //Preform an additional check on password...
            isValid = user.getPassword().equals(password);
            db.endTransaction(true);
        } catch (DatabaseException e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
            throw new ServerException(e.toString());
        }
        // TODO: should i handle it this way?
        if (!isValid) {
            user = new User();
        }

        ValidateUserResult result = new ValidateUserResult(user, isValid);

        return result;
    }

    /**
     * Gets all the projects in the database
     */
    public static GetProjectsResult getProjects(GetProjectsParameters params) throws ServerException {
        ValidateUserParameters validate = new ValidateUserParameters();
        validate.setUsername(params.getUsername());
        validate.setPassword(params.getPassword());

        boolean isValid = validateUser(validate).isValid();
        if (!isvalid)
            throw new ServerException("Invalid user credentials");

        Database db = new Database();
        List<Project> projects = null;

        try {
            db.startTransaction();
            projects = db.getProjectsDAO().getAllProjects();
            db.endTransaction(true);
        }
        catch (DatabaseException e) {
            db.endTransaction(false);
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
            throw new ServerException(e.toString());
        }

        GetProjectsResult result = new GetProjectsResult();
        result.setAllProjects(projects);

        return result;
    }

    /**
     * Gets a sample image from a project
     */
    public static GetSampleImageResult getSampleImage(GetSampleImageParameters params) throws ServerException {
        ValidateUserParameters validate = new ValidateUserParameters();
        validate.setUsername(params.getUsername());
        validate.setPassword(params.getPassword());
        boolean isValid = validateUser(validate).isValid();

        if (!isValid)
            throw new ServerException("Invalid user credentials");

        Database db = new Database();
        Image image = null;

        try {
            db.startTransaction();
            image = db.getImagesDAO().getSampleImage(params.getProjectId());
            db.endTransaction(true);
        }
        catch (DatabaseException e) {
            db.endTransaction(false);
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
            throw new ServerException(e.toString());
        }

        GetSampleImageResult result = new GetSampleImageResult();
        result.setSampleImage(image);

        return result;
    }

    /**
     * Downloads an incomplete image from a project.
     * This includes information from images, projects, and fields
     */
    public static DownloadBatchResult downloadBatch(DownloadBatchParameters params) throws ServerException {
        ValidateUserParameters validate = new ValidateUserParameters();
        validate.setUsername(params.getUsername());
        validate.setPassword(params.getPassword());
        boolean isValid = validateUser(validate).isValid();

        if (!isValid)
            throw new ServerException("Invalid user credentials");

        Database db = new Database();
        int project_id = params.getProjectId();
        Image image = null;
        Project project = null;
        List<Field> fields = null;

        try {
            db.startTransaction();
            image = db.getImagesDAO().getIncompleteImage(project_id);
            project = db.getProjectsDAO().getProject(project_id);
            fields = db.getFieldsDAO().getAllFields(project_id);
            db.endTransaction(true);
        }
        catch (DatabaseException e) {
            db.endTransaction(false);
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
            throw new ServerException(e.toString());
        }

        DownloadBatchResult result = new DownloadBatchResult();
        result.setImage(image);
        result.setProject(project);
        result.setFields(fields);

        return result;
    }

    /**
     * Submits values from a batch into the database
     */
    public static void submitBatch(SubmitBatchParameters params) throws ServerException {
        ValidateUserParameters validate = new ValidateUserParameters();
        validate.setUsername(params.getUsername());
        validate.setPassword(params.getPassword());
        boolean isValid = validateUser(validate).isValid();

        if (!isValid)
            throw new ServerException("Invalid user credentials");

        // Values have all member variables set except for image_url and field_id
        Database db = new Database();
        int image_id = params.getImageId();
        List<Value> values = params.getNewValues();
        Image image = null;
        String image_url = null;
        int project_id = 0;

        try {
            db.startTransaction();
            image = db.getImagesDAO().getImage(image_id);
            image_url = image.getImageUrl();
            project_id = image.getProjectId();

            for (Value each : values) {
                int field_id = db.getFieldsDAO().getFieldId(project_id, each.getColumnNumber());
                each.setFieldId(field_id);
                each.setImageUrl(image_url);
                db.getValuesDAO().addValue(each);
            }
        }
        catch (DatabaseException e) {
            db.endTransaction(false);
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
            throw new ServerException(e.toString());
        }

        return;
    }

    /**
     * Gets the fields for a project
     */
    public static GetFieldsResult getFields(GetFieldsParameters params) throws ServerException {
        ValidateUserParameters validate = new ValidateUserParameters();
        validate.setUsername(params.getUsername());
        validate.setPassword(params.getPassword());
        boolean isValid = validateUser(validate).isValid();

        if (!isValid)
            throw new ServerException("Invalid user credentials");

        Database db = new Database();
        int project_id = params.getProjectId();
        List<Field> fields = null;

        try {
            db.startTransaction();
            fields = project_id > 0 ? db.getFieldsDAO().getAllFields(project_id) : db.getFieldsDAO().getAllFields();
            db.endTransaction(true);
        }
        catch (DatabaseException e) {
            db.endTransaction(false);
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
            throw new ServerException(e.toString());
        }

        GetFieldsResult result = new GetFieldsResult();
        result.setFields(fields);

        return result;
    }

    /**
     * Searches certain fields for certain values
     */
    public static SearchResult search(SearchParameters params) throws ServerException {
        ValidateUserParameters validate = new ValidateUserParameters();
        validate.setUsername(params.getUsername());
        validate.setPassword(params.getPassword());
        boolean isValid = validateUser(validate).isValid();

        if (!isValid)
            throw new ServerException("Invalid user credentials");

        Database db = new Database();
        List<Value> values = null;

        try {
            db.startTransaction();
            values = db.getValuesDAO().search(params.getFieldIds(), params.getValues());
            db.endTransaction(true);
        }
        catch (DatabaseException e) {
            db.endTransaction(false);
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
            throw new ServerException(e.toString());
        }

        SearchResult result = new SearchResult();
        result.setFoundImages(values);

        return result;
    }

    /**
     * Downloads a file from the server TODO
     */
    public static DownloadFileResult downloadFile(DownloadFileParameters params) throws ServerException {
        ValidateUserParameters validate = new ValidateUserParameters();
        validate.setUsername(params.getUsername());
        validate.setPassword(params.getPassword());
        boolean isValid = validateUser(validate).isValid();

        if (!isValid)
            throw new ServerException("Invalid user credentials");




        return null;
    }
}











