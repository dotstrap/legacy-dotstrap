package shared.communication;

public class SubmitBatchResponse implements Response {

  private boolean success;

  public SubmitBatchResponse() {
    success = false;
  }

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    if (success) {
      sb.append("TRUE\n");
    } else {
      sb.append("FAILED\n");
    }
    return sb.toString();
  }
}
