/**
 * DownloadBatchResponse.java
 * JRE v1.8.0_45
 *
 * Created by William Myers on Jun 30, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package shared.communication;

import java.net.URL;
import java.util.List;

import shared.model.Batch;
import shared.model.Field;
import shared.model.Project;
import shared.model.Record;

/**
 * The Class DownloadBatchResponse.
 */
public class DownloadBatchResponse implements Response {
  public Batch batch;
  private Project project;
  private List<Field> fields;
  private Record[][] recordValues;
  private URL urlPrefix;

  /**
   * Instantiates a new download batch response.
   */
  public DownloadBatchResponse() {
    batch = null;
    project = null;
    fields = null;
    urlPrefix = null;
  }

  /**
   * Instantiates a new download batch response.
   *
   * @param batch the batch
   * @param project the project
   * @param fields the fields
   * @param url the url
   */
  public DownloadBatchResponse(Batch batch, Project project, List<Field> fields,
      URL url) {
    this.batch = batch;
    this.project = project;
    this.fields = fields;
    urlPrefix = url;
  }

  public Batch getBatch() {
    return batch;
  }

  public List<Field> getFields() {
    return fields;
  }

  public Project getProject() {
    return project;
  }

  public Record[][] getRecordValues() {
    return recordValues;
  }

  public URL getUrlPrefix() {
    return urlPrefix;
  }

  public void setBatch(Batch batch) {
    this.batch = batch;
  }

  public void setFields(List<Field> fields) {
    this.fields = fields;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public void setRecordValues(Record[][] records) {
    recordValues = records;
  }

  public void setUrlPrefix(URL url) {
    urlPrefix = url;
  }

  /*
   * (non-Javadoc)
   *
   * Mandatory toString implementation for automatic pass-off driver
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
