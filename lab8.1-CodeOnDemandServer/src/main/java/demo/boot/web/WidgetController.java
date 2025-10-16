package demo.boot.web;



import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")  // Allow client app to call from different port
public class WidgetController {

    @GetMapping("/message")
    public Map<String, String> getMessage() {
        return Map.of("message", "Hello from Code-on-Demand Server!");
    }

    @GetMapping(value = "/widget", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public String getWidgetCode() throws IOException {
        var resource = new ClassPathResource("static/widget.js");
        return StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
    }
}
