package com.training.helper.exception;

/**
 * Class HelperException Response
 *
 * @Author Naveen Raja
 */
public class HelperAppResponse {
    private int status;
    private String message;
//    private String timeStamp;

    public HelperAppResponse() {

    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

//    public String getTimeStamp() {
//        return timeStamp;
//    }
//

    @Override
    public String toString() {
        return "HelperAppResponse{" +
                "status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
//    public void setTimeStamp(String timeStamp) {
//        this.timeStamp = timeStamp;
//    }


}
