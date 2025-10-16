package hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/")
    public String home() {
        return "🌍 Public Home Page (no login required)";
    }

    @GetMapping("/home")
    public String openPage() {
        return "🏠 Open page accessible to everyone";
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "✅ Welcome! You are logged in.";
    }

    @GetMapping("/reports/view")
    public String reports() {
        return "📊 Reports Page (requires REPORT_VIEW authority)";
    }

    @GetMapping("/manage/users")
    public String manageUsers() {
        return "👥 Manage Users Page (requires MANAGE_USERS authority)";
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "🛡️ Admin Dashboard (requires ROLE_ADMIN)";
    }
}
