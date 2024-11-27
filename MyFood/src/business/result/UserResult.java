package business.result;

public class UserResult {
    public enum USER_RESULT {UPDATED_PASSWORD, OLD_PASSWORD, WRONG_PASSWORD, EMPTY_NEW_PASSWORD, NOT_ENOUGH_INFO, UPDATED_INFO}

    private UserResult.USER_RESULT userResult;
    private String message;

    public UserResult.USER_RESULT getUserResult() {
        return userResult;
    }

    public void setUserResult(UserResult.USER_RESULT userResult) {
        this.userResult = userResult;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
