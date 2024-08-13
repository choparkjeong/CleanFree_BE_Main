package site.cleanfree.be_main.security;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final AuthenticationProvider authenticationProvider;
    private final UserDetailsService userDetailsService;
    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        return request -> {
            var cors = new org.springframework.web.cors.CorsConfiguration();
            cors.setAllowedOriginPatterns(List.of("http://localhost:3000", "https://cleanfree.site",
                "https://www.cleanfree.site", "https://www.cleanfree.site/**",
                "https://clean-free-fe.vercel.app",
                "https://cleanfree.store", "http://cleanfree.store/**"));
            cors.setAllowedMethods(
                List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "PATCH"));
            cors.setAllowedHeaders(List.of("*"));
            cors.setAllowCredentials(true);
            return cors;
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(CsrfConfigurer::disable)
            .authorizeHttpRequests(
                authorizeHttpRequests -> authorizeHttpRequests
                    // 허용 범위
                    .requestMatchers("https://cleanfree.store", "/api/v1/auth/login",
                        "/api/v1/auth/signup", "/api/v1/auth/is-member", "/swagger-ui/**",
                        "/v3/api-docs/**", "/health-check", "/api/v1/cozyquick/**",
                        "/api/v1/createvalue/**", "/api/v1/createeasy/**",
                        "/api/v1/cookingstation/**", "/api/v1/curesilver/**",
                        "/api/v1/carrycabin/**", "/api/v1/access", "/api/v1/clearvisa/**",
                        "/api/v1/cozyhouse/**")
                    .permitAll()
                    .anyRequest()
                    .authenticated()
            )
            .sessionManagement(
                sessionManagement -> sessionManagement
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authenticationProvider(
                authenticationProvider) //등록할때 사용하는 키는 authenticationProvider를 사용
            .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, userDetailsService),
                UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
