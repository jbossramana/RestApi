package demo.boot.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class GreetingController {

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/greet")
    public Map<String, String> greet(
            Locale locale, // Let Spring resolve it automatically
            @RequestParam(name = "name", required = false) String name
    ) {
        if (name == null || name.isBlank()) {
            throw new RuntimeException("Name parameter is missing");
        }

        String greeting = messageSource.getMessage("greeting.message", null, locale);
        String info = messageSource.getMessage("info.message", null, locale);

        return Map.of(
                "greeting", greeting + " " + name + "!",
                "info", info
        );
    }
}