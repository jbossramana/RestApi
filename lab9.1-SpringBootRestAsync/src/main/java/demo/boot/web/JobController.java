package demo.boot.web;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demo.boot.model.JobStatus;
import demo.boot.service.ReportService;

@RestController
// Base mapping must match the URI structure used in the service
@RequestMapping("/api/v1/jobs") 
public class JobController {

    private final ReportService reportService;

    public JobController(ReportService reportService) {
        this.reportService = reportService;
    }

    // -----------------------------------------------------------------
    // 1. INITIATION: POST /api/v1/jobs/reports
    // -----------------------------------------------------------------
    @PostMapping("/reports")
    public ResponseEntity<Void> initiateReport(@RequestHeader("X-User-ID") String userId) {
        
        JobStatus status = reportService.initiateReportGeneration(userId);
        
        // Location header points to the polling endpoint
        URI location = URI.create("/api/v1/jobs/" + status.getId());
        
        return ResponseEntity.status(HttpStatus.ACCEPTED) // 202 Accepted
                             .header(HttpHeaders.LOCATION, location.toString())
                             .build();
    }
    
    // -----------------------------------------------------------------
    // 2. POLLING: GET /api/v1/jobs/{jobId}
    // -----------------------------------------------------------------
    @GetMapping("/{jobId}")
    public ResponseEntity<JobStatus> getJobStatus(@PathVariable String jobId) {
        Optional<JobStatus> statusOpt = reportService.getJobStatus(jobId);

        if (statusOpt.isEmpty()) {
            return ResponseEntity.notFound().build(); // 404
        }

        JobStatus status = statusOpt.get();

        if ("COMPLETED".equals(status.getStatus())) {
            // Task is done. Redirect to the final resource.
            return ResponseEntity.status(HttpStatus.SEE_OTHER) // 303 See Other
                                 .header(HttpHeaders.LOCATION, status.getResultUri())
                                 .body(status);
        } else if ("FAILED".equals(status.getStatus())) {
            // Task failed
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR) // 500
                                 .body(status);
        } else {
            // Still PENDING or IN_PROGRESS - keep polling
            return ResponseEntity.ok(status); // 200 OK
        }
    }

    // -----------------------------------------------------------------
    // 3. DOWNLOAD: GET /api/v1/jobs/reports/{jobId}/download
    // -----------------------------------------------------------------
    @GetMapping("/reports/{jobId}/download") 
    public ResponseEntity<byte[]> downloadReport(@PathVariable String jobId) {
        
        Optional<JobStatus> statusOpt = reportService.getJobStatus(jobId);

        // Check if job exists and is COMPLETE
        if (statusOpt.isEmpty() || !statusOpt.get().getStatus().equals("COMPLETED")) {
             // Return 404 or 409 Conflict (job not ready/failed)
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); 
        }

        // --- SIMULATED FILE RETRIEVAL ---
        String reportContent = "Report ID: " + jobId + "\n\nThis report is now ready for download.";
        byte[] fileBytes = reportContent.getBytes(StandardCharsets.UTF_8);
        String fileName = "final_report_" + jobId + ".txt";
        // --- END SIMULATED FILE RETRIEVAL ---

        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN) 
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(fileBytes);
    }
}