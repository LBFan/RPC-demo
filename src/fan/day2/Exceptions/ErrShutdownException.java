package fan.day2.Exceptions;

/**
 * @author : PF_23
 * @Description : TODO
 * @date : 2022/7/6 21:16.
 */

public class ErrShutdownException extends RuntimeException{

    private ErrorCode errorCode;

    private int causeCode;

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public int getCauseCode() {
        return causeCode;
    }

    public void setCauseCode(int causeCode) {
        this.causeCode = causeCode;
    }

    public ErrShutdownException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrShutdownException(ErrorCode errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }

    public ErrShutdownException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ErrShutdownException(int causeCode, String message) {
        super(message);
        this.causeCode = causeCode;
    }

    public ErrShutdownException(String message) {
        super(message);
        this.causeCode = 50000;
        this.errorCode = ErrorCode.SYSTEM_ERROR;
    }
}

