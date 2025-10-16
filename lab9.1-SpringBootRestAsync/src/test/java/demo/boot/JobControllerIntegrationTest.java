package demo.boot;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

// Load the full Spring Boot application context for integration testing
@SpringBootTest
@AutoConfigureMockMvc 
class JobControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testPollingPatternSuccessFlow() throws Exception {
        final String USER_ID = "test-runner";
        
        System.out.println("\n--- STARTING ASYNC POLLING FLOW TEST ---");

        // --- 1. INITIATE THE JOB (POST /api/v1/jobs/reports) ---
        System.out.println("1. POST Request: Initiating job...");
        
        MvcResult postResult = mockMvc.perform(post("/api/v1/jobs/reports")
                .header("X-User-ID", USER_ID))
                .andExpect(status().isAccepted()) 
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andReturn();
        
        String pollingUrl = postResult.getResponse().getHeader(HttpHeaders.LOCATION);
        String jobId = pollingUrl.substring(pollingUrl.lastIndexOf("/") + 1);
        
        System.out.println("   --> STATUS: 202 Accepted");
        System.out.println("   --> Job ID: " + jobId);
        System.out.println("   --> Polling URL: " + pollingUrl);

        // --- 2. POLL STATUS (GET /api/v1/jobs/{jobId}) ---
        // Expecting 303 Redirect because the job finishes immediately in tests.
        System.out.println("\n2. GET Request: Polling status (Expecting 303 Redirect)...");
        
        MvcResult pollResult = mockMvc.perform(get(pollingUrl))
                .andExpect(status().isSeeOther()) // Assert 303 Redirect
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andReturn();
        
        String finalLocation = pollResult.getResponse().getHeader(HttpHeaders.LOCATION);

        System.out.println("   --> STATUS: 303 See Other (Job is Complete)");
        System.out.println("   --> Download URL: " + finalLocation);

        // Assert that the final Location header points to the download endpoint
        assertTrue(finalLocation.endsWith("/download"), "Final Location must be the download endpoint.");


        // --- 3. DOWNLOAD THE RESULT (GET /api/v1/jobs/reports/{jobId}/download) ---
        // Expects: 200 OK and file-specific headers.
        System.out.println("\n3. GET Request: Downloading final report...");
        
        mockMvc.perform(get(finalLocation))
                .andExpect(status().isOk()) // Assert 200 OK
                .andExpect(content().contentType("text/plain"))
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, 
                        org.hamcrest.Matchers.containsString("filename=\"final_report_" + jobId)));

        System.out.println("   --> STATUS: 200 OK (Report delivered successfully)");
        System.out.println("\n--- TEST COMPLETED SUCCESSFULLY ---\n");
    }
    
    // -----------------------------------------------------------------
    // Additional Test Cases (Logging added for clarity)
    // -----------------------------------------------------------------
    
    @Test
    void testDownloadBeforeCompletion() throws Exception {
        System.out.println("\n--- STARTING DOWNLOAD BEFORE COMPLETION TEST ---");
        final String FAKE_JOB_ID = "00000000-0000-0000-0000-000000000000";
        final String downloadUrl = "/api/v1/jobs/reports/" + FAKE_JOB_ID + "/download";
        
        System.out.println("1. GET Request: Attempting download for non-existent/incomplete Job ID: " + FAKE_JOB_ID);
        
        mockMvc.perform(get(downloadUrl))
                .andExpect(status().isConflict()); // Assert 409 Conflict
                
        System.out.println("   --> STATUS: 409 Conflict (Correctly blocked download)");
        System.out.println("--- TEST COMPLETED SUCCESSFULLY ---");
    }

    @Test
    void testPollForNotFoundJob() throws Exception {
        System.out.println("\n--- STARTING JOB NOT FOUND TEST ---");
        final String NON_EXISTENT_JOB_URL = "/api/v1/jobs/11111111-1111-1111-1111-111111111111";
        
        System.out.println("1. GET Request: Polling for non-existent URL: " + NON_EXISTENT_JOB_URL);
        
        mockMvc.perform(get(NON_EXISTENT_JOB_URL))
                .andExpect(status().isNotFound()); // Assert 404 Not Found
                
        System.out.println("   --> STATUS: 404 Not Found (Correctly handled)");
        System.out.println("--- TEST COMPLETED SUCCESSFULLY ---");
    }
}