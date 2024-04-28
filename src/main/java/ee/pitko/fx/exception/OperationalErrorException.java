package ee.pitko.fx.exception;


import ee.pitko.fx.client.OprlErrHandling;

public class OperationalErrorException extends RuntimeException {

    public OperationalErrorException(String errorCode, String errorDesc) {
        super("External service responded with error " + errorCode + ", " + errorDesc);
    }

    public static OperationalErrorException fromXML(OprlErrHandling oprlError) {
        return new OperationalErrorException(oprlError.getErr().getPrtry(), oprlError.getDesc());
    }
}
