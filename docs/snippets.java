StringBuilder recordLogOutput = new StringBuilder();
int row = 0;
for (Record[] records : recordValues) {
  recordLogOutput.append("\n===================\n(X)row=").append(row)
      .append("\n-------------------");
  int col = 0;
  for (Record r : records) {
    recordLogOutput.append("\n(Y)col=").append(col);
    if (r != null) {
      recordLogOutput.append("\n").append(r.toString());
    } else {
      recordLogOutput.append("\n")
          .append("This row intentionally left blank");
    }
    col++;
  }
  row++;
}
ClientLogManager.getLogger().log(Level.INFO, recordLogOutput.toString());
