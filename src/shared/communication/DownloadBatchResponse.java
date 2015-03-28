/**
 * DownloadBatchResponse.java
 * JRE v1.8.0_40
 *
 * Created by William Myers on Mar 24, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package shared.communication;

import java.net.URL;
import java.util.List;

import shared.model.*;

/**
 * The Class DownloadBatchResponse.
 */
public class DownloadBatchResponse implements Response {

  /** The batch. */
  public Batch        batch;
  private Project     project;
  private List<Field> fields;
  private URL         urlPrefix;

  /**
   * Instantiates a new download batch result.
   */
  public DownloadBatchResponse() {}

  // @formatter:off
  /**
   * Instantiates a new DownloadBatchResponse.
   *
   * @param batch the batch
   * @param project the project
   * @param fields the fields
   * @param url the url
   */
  public DownloadBatchResponse(Batch batch, Project project,
      List<Field> fields, URL url) {
    this.batch = batch;
    this.project = project;
    this.fields = fields;
    urlPrefix = url;
  }

  // @formatter:on
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

  public URL getUrlPrefix() {
    return urlPrefix;
  }

  public void setUrlPrefix(URL url) {
    urlPrefix = url;
  }

  /**
   * (non-Javadoc).
   *
   * @return the string
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    if (batch != null) {
      sb.append(batch.getBatchId() + "\n");
      sb.append(project.getProjectId() + "\n");
      sb.append(urlPrefix + "/" + batch.getFilePath() + "\n");
      sb.append(project.getFirstYCoord() + "\n");
      sb.append(project.getRecordHeight() + "\n");
      sb.append(project.getRecordsPerBatch() + "\n");
      sb.append(fields.size() + "\n");
      int i = 0;
      for (Field f : fields) {
        sb.append(f.getFieldId() + "\n");
        sb.append(i + "\n");
        sb.append(f.getTitle() + "\n");
        sb.append(urlPrefix + "/" + f.getHelpURL() + "\n");
        sb.append(f.getXCoord() + "\n");
        sb.append(f.getWidth() + "\n");
        if (f.getKnownData().length() > 0) {
          sb.append(urlPrefix + "/" + f.getKnownData() + "\n");
        }
        i++;
      }
    } else {
      sb.append("FAILED\n");
    }
    return sb.toString();
  }
}
