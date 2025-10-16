package demo.boot.service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import demo.boot.model.JobStatus;

@Service
public class ReportService {

    // Simulating a persistent store for job statuses (use DB/Redis in production)
    private final Map<String, JobStatus> jobStatuses = new ConcurrentHashMap<>();

    public JobStatus initiateReportGeneration(String userId) {
        String jobId = UUID.randomUUID().toString();
        
        // IMPORTANT: The resultUri MUST match the path in JobController's download method
        String resultUri = "/api/v1/jobs/reports/" + jobId + "/download"; 
        
        JobStatus status = new JobStatus(jobId, "PENDING", resultUri);
        jobStatuses.put(jobId, status);
        
        // Start the long-running task asynchronously
        generateReportAsync(jobId, userId); 
        
        return status;
    }

    public Optional<JobStatus> getJobStatus(String jobId) {
        return Optional.ofNullable(jobStatuses.get(jobId));
    }

    @Async // Tells Spring to run this method in a separate thread pool
    public void generateReportAsync(String jobId, String userId) {
        JobStatus status = jobStatuses.get(jobId);
        
        // Set to IN_PROGRESS
        status.setStatus("IN_PROGRESS");
        jobStatuses.put(jobId, status);

        try {
            // --- SIMULATED LONG RUNNING TASK (5 to 10 seconds) ---
            long waitTime = ThreadLocalRandom.current().nextLong(5, 11);
            TimeUnit.SECONDS.sleep(waitTime); 
            
            // Simulating a 10% chance of failure for demonstration
            if (ThreadLocalRandom.current().nextInt(10) == 0) {
                throw new RuntimeException("Simulated report generation failure.");
            }
            // --- END SIMULATED TASK ---

            // On success: update status
            status.setStatus("COMPLETED");

        } catch (Exception e) {
            System.err.println("Job " + jobId + " failed: " + e.getMessage());
            status.setStatus("FAILED");
        } finally {
            jobStatuses.put(jobId, status);
        }
    }
}