package com.example.adverts;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@TestConfiguration
@Configuration
public class TestConfig {

    @Bean public PasswordEncoder passwordEncoder() {
        return new PasswordEncoderTest();
    }

    static class PasswordEncoderTest implements PasswordEncoder {
        @Override
        public String encode(CharSequence charSequence) {
            return charSequence.toString();
        }

        @Override
        public boolean matches(CharSequence charSequence, String s) {
            return charSequence.toString().equals(s);
        }
    }

}
