package shared.communication;

import shared.model.User;

// TODO: Auto-generated Javadoc
/**
 * The Class ValidateUserResult.
 */
public class ValidateUserResult {
    private User    user;
    private boolean isValid;

    /**
     * Instantiates a new validate user result.
     */
    public ValidateUserResult() {
        isValid = false;
    }

    /**
     * Instantiates a new ValidateUserResult.
     *
     * @param user
     * @param isValid
     */
    public ValidateUserResult(User user, boolean isValid) {
        this.user = user;
        this.isValid = isValid;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isValid() {
        return this.isValid;
    }

    public void setValid(boolean isValid) {
        this.isValid = isValid;
    }

    /**
     *
     * (non-Javadoc).
     *
     * @return the string
     * @see java.lang.Object#toString()
     */
    public String toString() {
        StringBuilder output = new StringBuilder();
        if (this.isValid) {
            output.append("TRUE\n");
            output.append(user.getFirst() + "\n");
            output.append(user.getLast() + "\n");
            output.append(user.getRecordCount() + "\n");
        } else {
            output.append("FALSE\n");
        }
        return output.toString();
    }

}
