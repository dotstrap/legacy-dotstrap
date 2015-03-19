package shared.communication;

import java.util.ArrayList;
import java.util.List;

import shared.model.Batch;
import shared.model.Field;
import shared.model.Project;

// TODO: Auto-generated Javadoc
/**
 * The Class DownloadBatchResult.
 */
public class DownloadBatchResult {
    private Batch            batch;
    private Project          project;
    private List<Field> fields;

    /**
     * Instantiates a new download batch result.
     */
    public DownloadBatchResult() {
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
    }

    /**
     * Sets the number of fields.
     *
     * @param numberOfFields
     *            the new number of fields
     */


    /**
     * @return the batch
     */
    public Batch getBatch() {
        return batch;
    }

    /**
     * @param batch the batch to set
     */
    public void setBatch(Batch batch) {
        this.batch = batch;
    }

    /**
     * @return the project
     */
    public Project getProject() {
        return project;
    }

    /**
     * @param project the project to set
     */
    public void setProject(Project project) {
        this.project = project;
    }

    /**
     * @return the fields
     */
    public List<Field> getFields() {
        return fields;
    }

    /**
     * @param fields the fields to set
     */
    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    /**
     * (non-Javadoc).
     *
     * @return the string
     * @see java.lang.Object#toString()
     */
    //public String toString() {
        //StringBuilder sb = new StringBuilder();
        //if (validUser) {
            //sb.append(this.batch.getBatchID() + "\n");
            //sb.append(this.project.getProjInfo().getID() + "\n");
            //sb.append(this.url + "/" + this.batch.getFilePath() + "\n");
            //sb.append(this.project.getFirstY() + "\n");
            //sb.append(this.project.getRecordHeight() + "\n");
            //sb.append(this.project.getRecordsPerBatch() + "\n");
            //sb.append(this.numberOfFields + "\n");
            //int i = 1;
            //for (Field f : fields) {
                //sb.append(f.getID() + "\n");
                //sb.append(i + "\n");
                //sb.append(f.getTitle() + "\n");
                //sb.append(this.url + "/" + f.getHelp() + "\n");
                //sb.append(f.getX() + "\n");
                //sb.append(f.getWidth() + "\n");
                //if (f.getKnownPath().length() > 0) {
                    //sb.append(this.url + "/" + f.getKnownPath() + "\n");
                //}
                //i++;
            //}
        //} else {
            //sb.append("FAILED\n");
        //}
        //return sb.toString();
    //}

}
