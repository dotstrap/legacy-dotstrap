package shared.communication;

import java.net.URL;
import java.util.List;

import shared.model.Record;

/**
 * The Class SearchResult.
 */
public class SearchResult {
    private List<Record> foundRecords;
    private List<URL> urls;

    /**
     * Instantiates a new SearchResult.
     *
     */
    public SearchResult() {
        this.foundRecords = null;
    }

    /**
     * @param foundRecords
     */
    public SearchResult(List<Record> foundRecords) {
        this.foundRecords = foundRecords;
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

    public List<URL> getUrls() {
        return this.urls;
    }

    public void setUrls(List<URL> urls) {
        this.urls = urls;
    }

    /**
     * @return the string
     * @see java.lang.Object#toString()
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < foundRecords.size(); ++i) {
            sb.append(this.foundRecords.get(i).getBatchID() + "\n");
            sb.append(urls.get(i) + "\n");
            sb.append(foundRecords.get(i).getRowNum() + "\n");
            sb.append(foundRecords.get(i).getFieldID() + "\n");
        }
        return sb.toString();
    }

}
