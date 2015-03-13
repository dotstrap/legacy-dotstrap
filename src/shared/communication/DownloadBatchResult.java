/**
 * DownloadBatchResult.java
 * JRE v1.7.0_76
 *
 * Created by William Myers on Mar 10, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package shared.communication;

import java.util.ArrayList;

import shared.model.*;

// TODO: Auto-generated Javadoc
/**
 * The Class DownloadBatchResult.
 */
public class DownloadBatchResult {

    /** The batch. */
    private Batch            batch;

    /** The fields. */
    private ArrayList<Field> fields;

    /** The number of fields. */
    private int              numberOfFields;

    /** The project. */
    private Project          project;

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
     * @param batch the batch
     * @param project the project
     * @param fields the fields
     * @param numberOfFields the number of fields
     */
    public DownloadBatchResult(Batch batch, Project project,
            ArrayList<Field> fields, int numberOfFields) {
        this.batch = batch;
        this.project = project;
        this.fields = fields;
        this.numberOfFields = numberOfFields;
    }

    public Batch getBatch() {
        return batch;
    }

    public ArrayList<Field> getFields() {
        return fields;
    }

    public int getNumberOfFields() {
        return numberOfFields;
    }

    public Project getProject() {
        return project;
    }

    public String getUrl() {
        return url;
    }

    /**
     * Checks if is valid user.
     */
    public boolean isValidUser() {
        return validUser;
    }

    public void setBatch(Batch batch) {
        this.batch = batch;
    }

    public void setFields(ArrayList<Field> fields) {
        this.fields = fields;
    }

    public void setNumberOfFields(int numberOfFields) {
        this.numberOfFields = numberOfFields;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setValidUser(boolean validUser) {
        this.validUser = validUser;
    }

    /**
     * (non-Javadoc).
     *
     * @return the string
     * @see java.lang.Object#toString()
     */
    //@Override
    //public String toString() {
        //StringBuilder sb = new StringBuilder();
        //if (validUser) {
            //sb.append(batch.getID() + "\n");
            //sb.append(project.getProjInfo().getID() + "\n");
            //sb.append(url + "/" + batch.getFilePath() + "\n");
            //sb.append(project.getFirstY() + "\n");
            //sb.append(project.getRecordHeight() + "\n");
            //sb.append(project.getRecordsPerBatch() + "\n");
            //sb.append(numberOfFields + "\n");
            //int i = 1;
            //for (Field f : fields) {
                //sb.append(f.getID() + "\n");
                //sb.append(i + "\n");
                //sb.append(f.getTitle() + "\n");
                //sb.append(url + "/" + f.getHelp() + "\n");
                //sb.append(f.getX() + "\n");
                //sb.append(f.getWidth() + "\n");
                //if (f.getKnownPath().length() > 0) {
                    //sb.append(url + "/" + f.getKnownPath() + "\n");
                //}
                //i++;
            //}
        //} else {
            //sb.append("FAILED\n");
        //}
        //return sb.toString();
    //}
}
