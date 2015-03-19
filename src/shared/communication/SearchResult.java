package shared.communication;

import java.util.List;

import shared.model.Record;

// TODO: Auto-generated Javadoc
/**
 * The Class SearchResult.
 */
public class SearchResult {
    private List<Record> foundRecords;

    /**
     * @param foundRecords
     */
    public SearchResult(List<Record> foundRecords) {
        this.foundRecords = foundRecords;
    }

    /**
     *
     */
    public SearchResult() {
        this.foundRecords = null;
    }

    /**
     * @return the foundRecords
     */
    public List<Record> getFoundRecords() {
        return foundRecords;
    }

    /**
     * @param foundRecords the foundRecords to set
     */
    public void setFoundRecords(List<Record> foundRecords) {
        this.foundRecords = foundRecords;
    }

    /**
     * Gets the links.
     *
      /**
     *
     * (non-Javadoc).
     *
     * @return the string
     * @see java.lang.Object#toString()
     */
    //public String toString() {
        //StringBuilder sb = new StringBuilder();
        //if (validUser & records.size() > 0) {
            //for (int i = 0; i < records.size(); ++i) {
                //sb.append(records.get(i).getBatchID() + "\n");
                //sb.append(urls.get(i) + "\n");
                //sb.append(records.get(i).getRecordNumber() + "\n");
                //sb.append(records.get(i).getFieldID() + "\n");
            //}
        //} else {
            //sb.append("FAILED\n");
        //}
        //return sb.toString();
    //}

}
