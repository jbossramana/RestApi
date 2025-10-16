package demo.boot.model;

public class JobStatus {
    private String id;
    private String status; // PENDING, IN_PROGRESS, COMPLETED, FAILED
    private String resultUri; 

    // Constructors
    public JobStatus() {
    }

    public JobStatus(String id, String status, String resultUri) {
        this.id = id;
        this.status = status;
        this.resultUri = resultUri;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getResultUri() { return resultUri; }
    public void setResultUri(String resultUri) { this.resultUri = resultUri; }
}