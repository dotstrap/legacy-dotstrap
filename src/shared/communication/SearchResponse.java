package shared.communication;

import java.net.URL;
import java.util.List;

import shared.model.Record;

public class SearchResponse implements Response {
  private List<Record> foundRecords;
  private URL urlPrefix;

  public SearchResponse() {
    foundRecords = null;
  }

  public SearchResponse(List<Record> foundRecords) {
    this.foundRecords = foundRecords;
  }

  public List<Record> getFoundRecords() {
    return foundRecords;
  }

  public void setFoundRecords(List<Record> foundRecords) {
    this.foundRecords = foundRecords;
  }

  public URL getUrlPrefix() {
    return urlPrefix;
  }

  public void setUrlPrefix(URL urlPrefix) {
    this.urlPrefix = urlPrefix;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    if (foundRecords.size() > 0) {
      for (int i = 0; i < foundRecords.size(); ++i) {
        sb.append(foundRecords.get(i).getBatchId() + "\n");
        sb.append(urlPrefix + "/" + foundRecords.get(i).getBatchURL() + "\n");
        sb.append(foundRecords.get(i).getRowNum() + "\n");
        sb.append(foundRecords.get(i).getFieldId() + "\n");
      }
    } else {
      sb.append("FAILED\n");
    }
    return sb.toString();
  }
}
