package bgh.practice.spring.message;

public class GetById extends BaseMessage {

    private int requestedId;
    private static final long serialVersionUID = 1L;

    public GetById() {
    }

    public GetById(int requestedId) {
        this.requestedId = requestedId;
    }

    public int getRequestedId() {
        return requestedId;
    }

    public void setRequestedId(int requestedId) {
        this.requestedId = requestedId;
    }
}
