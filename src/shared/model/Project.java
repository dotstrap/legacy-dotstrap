package shared.model;

// TODO: Auto-generated Javadoc
/**
 * The Class Project.
 */
public class Project {

    /** The proj info. */
    private ProjectInfo projInfo;

    /** The records per batch. */
    private int         recordsPerBatch;

    /** The first y. */
    private int         firstY;

    /** The record height. */
    private int         recordHeight;

    /**
     * Instantiates a new project.
     */
    public Project() {

    }

    /**
     * Instantiates a new project.
     *
     * @param pi
     *            the pi
     * @param rpb
     *            the rpb
     * @param fY
     *            the f y
     * @param rH
     *            the r h
     */
    public Project(ProjectInfo pi, int rpb, int fY, int rH) {
        projInfo = pi;
        recordsPerBatch = rpb;
        firstY = fY;
        recordHeight = rH;
    }

    /**
     * Gets the proj info.
     *
     * @return the proj info
     */
    public ProjectInfo getProjInfo() {
        return projInfo;
    }

    /**
     * Sets the proj info.
     *
     * @param projInfo
     *            the new proj info
     */
    public void setProjInfo(ProjectInfo projInfo) {
        this.projInfo = projInfo;
    }

    /**
     * Gets the records per batch.
     *
     * @return the records per batch
     */
    public int getRecordsPerBatch() {
        return recordsPerBatch;
    }

    /**
     * Sets the records per batch.
     *
     * @param recordsPerBatch
     *            the new records per batch
     */
    public void setRecordsPerBatch(int recordsPerBatch) {
        this.recordsPerBatch = recordsPerBatch;
    }

    /**
     * Gets the first y.
     *
     * @return the first y
     */
    public int getFirstY() {
        return firstY;
    }

    /**
     * Sets the first y.
     *
     * @param firstY
     *            the new first y
     */
    public void setFirstY(int firstY) {
        this.firstY = firstY;
    }

    /**
     * Gets the record height.
     *
     * @return the record height
     */
    public int getRecordHeight() {
        return recordHeight;
    }

    /**
     * Sets the record height.
     *
     * @param recordHeight
     *            the new record height
     */
    public void setRecordHeight(int recordHeight) {
        this.recordHeight = recordHeight;
    }

}
