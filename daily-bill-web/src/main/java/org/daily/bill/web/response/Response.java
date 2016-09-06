package org.daily.bill.web.response;

/**
 * Created by vano on 6.9.16.
 */
public class Response {


    private String code;
    private String status;
    private String message;
    private Object object;

    public Response(String code, String status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }

    public Response(String code, String status, String message, Object object) {
        this.message = message;
        this.status = status;
        this.code = code;
        this.object = object;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
