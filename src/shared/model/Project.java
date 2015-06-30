/**
 * Project.java
 * JRE v1.8.0_45
 *
 * Created by William Myers on Jun 30, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package shared.model;

/**
 * The Class Project.
 */
public class Project {
  private int projectId;
  private String title;
  private int recordsPerBatch;
  private int firstYCoord;
  private int recordHeight;

  /**
   * Instantiates a new project.
   */
  public Project() {
    title = "";
    projectId = -1;
    recordHeight = -1;
    recordsPerBatch = -1;
    firstYCoord = -1;
  }

  /**
   * Instantiates a new project.
   *
   * @param id the id
   * @param title the title
   * @param recordsPerBatch the records per batch
   * @param firstYCoord the first y coord
   * @param recordHeight the record height
   */
  public Project(int id, String title, int recordsPerBatch, int firstYCoord,
      int recordHeight) {
    projectId = id;
    this.title = title;
    this.recordsPerBatch = recordsPerBatch;
    this.firstYCoord = firstYCoord;
    this.recordHeight = recordHeight;
  }

  /**
   * Instantiates a new project.
   *
   * @param title the title
   * @param recordsPerBatch the records per batch
   * @param firstYCoord the first y coord
   * @param recordHeight the record height
   */
  public Project(String title, int recordsPerBatch, int firstYCoord,
      int recordHeight) {
    this.title = title;
    this.recordsPerBatch = recordsPerBatch;
    this.firstYCoord = firstYCoord;
    this.recordHeight = recordHeight;
  }

  public int getFirstYCoord() {
    return firstYCoord;
  }

  public int getProjectId() {
    return projectId;
  }

  public int getRecordHeight() {
    return recordHeight;
  }

  public int getRecordsPerBatch() {
    return recordsPerBatch;
  }

  public String getTitle() {
    return title;
  }

  public void setFirstYCoord(int firstYCoord) {
    this.firstYCoord = firstYCoord;
  }

  public void setProjectId(int id) {
    projectId = id;
  }

  public void setRecordHeight(int recordHeight) {
    this.recordHeight = recordHeight;
  }

  public void setRecordsPerBatch(int recordsPerBatch) {
    this.recordsPerBatch = recordsPerBatch;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  /*
   * (non-Javadoc)
   *
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return title;
  }

}
