package business.result;

public class SigninResult {
    public enum SIGNIN_RESULT {USER_EXIST, SIGNIN_OK, EMPTY_BOX}

    private SIGNIN_RESULT signinResult;
    private String message;

    public SIGNIN_RESULT getSigninResult() {
        return signinResult;
    }

    public void setSigninResult(SIGNIN_RESULT signinResult) {
        this.signinResult = signinResult;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}