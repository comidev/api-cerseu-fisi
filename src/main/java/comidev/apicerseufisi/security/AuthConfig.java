package comidev.apicerseufisi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import comidev.apicerseufisi.components.usuario.UsuarioService;
import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class AuthConfig {
    private final UsuarioService userService;

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userService);
        daoAuthenticationProvider.setPasswordEncoder(userService.getBcrypt());
        return new ProviderManager(daoAuthenticationProvider);
    }
}
