package shared.communication;

import java.util.ArrayList;

import shared.model.Batch;
import shared.model.Field;
import shared.model.Project;

// TODO: Auto-generated Javadoc
/**
 * The Class DownloadBatchResult.
 */
public class DownloadBatchResult {

    /** The batch. */
    private Batch            batch;

    /** The project. */
    private Project          project;

    /** The fields. */
    private ArrayList<Field> fields;

    /** The number of fields. */
    private int              numberOfFields;

    /** The url. */
    private String           url;

    /** The valid user. */
    private boolean          validUser;

    /**
     * Instantiates a new download batch result.
     */
    public DownloadBatchResult() {
        validUser = false;
    }

    /**
     * Instantiates a new download batch result.
     *
     * @param batch
     *            the batch
     * @param project
     *            the project
     * @param fields
     *            the fields
     * @param numberOfFields
     *            the number of fields
     */
    public DownloadBatchResult(Batch batch, Project project,
            ArrayList<Field> fields, int numberOfFields) {
        this.batch = batch;
        this.project = project;
        this.fields = fields;
        this.numberOfFields = numberOfFields;
    }

    /**
     * Gets the batch.
     *
     * @return the batch
     */
    public Batch getBatch() {
        return batch;
    }

    /**
     * Sets the batch.
     *
     * @param batch
     *            the new batch
     */
    public void setBatch(Batch batch) {
        this.batch = batch;
    }

    /**
     * Gets the project.
     *
     * @return the project
     */
    public Project getProject() {
        return project;
    }

    /**
     * Sets the project.
     *
     * @param project
     *            the new project
     */
    public void setProject(Project project) {
        this.project = project;
    }

    /**
     * Gets the fields.
     *
     * @return the fields
     */
    public ArrayList<Field> getFields() {
        return fields;
    }

    /**
     * Sets the fields.
     *
     * @param fields
     *            the new fields
     */
    public void setFields(ArrayList<Field> fields) {
        this.fields = fields;
    }

    /**
     * Gets the number of fields.
     *
     * @return the number of fields
     */
    public int getNumberOfFields() {
        return numberOfFields;
    }

    /**
     * Sets the number of fields.
     *
     * @param numberOfFields
     *            the new number of fields
     */
    public void setNumberOfFields(int numberOfFields) {
        this.numberOfFields = numberOfFields;
    }

    /**
     * Checks if is valid user.
     *
     * @return true, if is valid user
     */
    public boolean isValidUser() {
        return validUser;
    }

    /**
     * Sets the valid user.
     *
     * @param validUser
     *            the new valid user
     */
    public void setValidUser(boolean validUser) {
        this.validUser = validUser;
    }

    /**
     * Gets the url.
     *
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the url.
     *
     * @param url
     *            the new url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * (non-Javadoc).
     *
     * @return the string
     * @see java.lang.Object#toString()
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (validUser) {
            sb.append(this.batch.getID() + "\n");
            sb.append(this.project.getProjInfo().getID() + "\n");
            sb.append(this.url + "/" + this.batch.getFilePath() + "\n");
            sb.append(this.project.getFirstY() + "\n");
            sb.append(this.project.getRecordHeight() + "\n");
            sb.append(this.project.getRecordsPerBatch() + "\n");
            sb.append(this.numberOfFields + "\n");
            int i = 1;
            for (Field f : fields) {
                sb.append(f.getID() + "\n");
                sb.append(i + "\n");
                sb.append(f.getTitle() + "\n");
                sb.append(this.url + "/" + f.getHelp() + "\n");
                sb.append(f.getX() + "\n");
                sb.append(f.getWidth() + "\n");
                if (f.getKnownPath().length() > 0) {
                    sb.append(this.url + "/" + f.getKnownPath() + "\n");
                }
                i++;
            }
        } else {
            sb.append("FAILED\n");
        }
        return sb.toString();
    }
}
