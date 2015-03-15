/**
 * ServerOp.java
 * JRE v1.7.0_76
 * 
 * Created by William Myers on Mar 14, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package servertester.views;

// TODO: Auto-generated Javadoc
/**
 * The Enum ServerOp.
 */
public enum ServerOp {

    /** The validate user. */
    VALIDATE_USER("Validate User"), // Username, Password (String, String)
    /** The get projects. */
    GET_PROJECTS("Get Projects"), // Username, Password (String, String)
    /** The get sample image. */
    GET_SAMPLE_IMAGE("Get Sample Image"), // Username, Password, Project
                                          // (String, String, int)
    /** The download batch. */
    DOWNLOAD_BATCH("Download Batch"), // Username, Password, Project (String,
                                      // String, int)
    /** The get fields. */
    GET_FIELDS("Get Fields"), // Username, Password, Project(-1) (String,
                              // String, int)
    /** The submit batch. */
    SUBMIT_BATCH("Submit Batch"), // Username, Password, Batch, Values (String,
                                  // String, int, String[])
    /** The search. */
    SEARCH("Search"); // Username, Password, Project, Fields, Values (int,
                      // int[], String[])

    /** The _description. */
    private final String _description;

    /**
     * Instantiates a new server op.
     *
     * @param description
     *            the description
     */
    private ServerOp(String description) {
        _description = description;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return _description;
    }

}
