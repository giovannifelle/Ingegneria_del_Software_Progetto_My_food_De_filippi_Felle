package business.result;

public class LoginResult {
    public enum LOGIN_RESULT {USER_DOESNT_EXIST, WRONG_PASSWORD, LOGIN_OK, USER_BLOCKED}

    private LOGIN_RESULT loginResult;
    private String message;

    public LOGIN_RESULT getLoginResult() {
        return loginResult;
    }

    public void setLoginResult(LOGIN_RESULT loginResult) {
        this.loginResult = loginResult;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}