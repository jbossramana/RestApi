package hello;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // 🔑 Define users with roles + authorities
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.withUsername("admin")
                .password("{noop}password") // {noop} = plain text (only for demo!)
                .roles("ADMIN")             // ROLE_ADMIN
                .authorities("ROLE_ADMIN","REPORT_VIEW", "MANAGE_USERS")
                .build();

        UserDetails user = User.withUsername("user")
                .password("{noop}password")
                .roles("USER")              // ROLE_USER
                .authorities("REPORT_VIEW")
                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }

    // 🔒 Define access rules
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/home").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")              // Role-based
                .requestMatchers("/reports/**").hasAuthority("REPORT_VIEW") // Permission-based
                .requestMatchers("/manage/**").hasAuthority("MANAGE_USERS") // Fine-grained
                .anyRequest().authenticated()
            )
            .formLogin(form -> form.defaultSuccessUrl("/welcome", true))
            .logout(logout -> logout.permitAll());

        return http.build();
    }
}
