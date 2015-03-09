package servertester.views;

public enum ServerOp {
	VALIDATE_USER("Validate User"),	// Username, Password (String, String)
	GET_PROJECTS("Get Projects"), // Username, Password (String, String)
	GET_SAMPLE_IMAGE("Get Sample Image"), // Username, Password, Project (String, String, int)
	DOWNLOAD_BATCH("Download Batch"), // Username, Password, Project (String, String, int)
	GET_FIELDS("Get Fields"), // Username, Password, Project(-1) (String, String, int)
	SUBMIT_BATCH("Submit Batch"), // Username, Password, Batch, Values (String, String, int, String[])
	SEARCH("Search");	// Username, Password, Project, Fields, Values (int, int[], String[])
	
	private final String _description;

	private ServerOp(String description) {
		_description = description;
	}

	@Override
	public String toString() {
		return _description;
	}

}

