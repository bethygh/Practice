package bgh.practice.spring.message;

public class ResponseMessage extends BaseMessage {

    private String status;
    private String payloadClass;
    private Object body;
    private String requestPayloadClass;

    private static final long serialVersionUID = 1L;


    public String getPayloadClass() {
        return payloadClass;
    }

    public void setPayloadClass(String payloadClass) {
        this.payloadClass = payloadClass;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public String getRequestPayloadClass() {
        return requestPayloadClass;
    }

    public void setRequestPayloadClass(String requestPayloadClass) {
        this.requestPayloadClass = requestPayloadClass;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
