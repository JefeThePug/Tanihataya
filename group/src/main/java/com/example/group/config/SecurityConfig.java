package com.example.group.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.formLogin(form -> form
				.loginPage("/user/login") // ログイン画面のURL
				.loginProcessingUrl("/authenticate") // ユーザー名とパスワードを送信するURL
				.usernameParameter("email") // フォームのemailをusernameとして渡す
				.defaultSuccessUrl("/user/loginsuccess", true) // 認証成功後のリダイレクト先URL
				.failureUrl("/user/login?failure") // 認証失敗後のリダイレクト先URL
				.permitAll() // ログイン画面は未認証でもアクセス可能
		).logout(logout -> logout
				.logoutSuccessUrl("/user/login?logout") // ログアウト成功後のリダイレクト先URL
		).authorizeHttpRequests(authz -> authz
				.requestMatchers(
						"/info/*",
						"/purchases",
						"/purchase/success",
						"/item/purchase",
						"/item/purchase/success",
						"/item/success",
						"/item/add_item",
						"/user/info",
						"/user/update")
				.authenticated() // ログイン必須のURL
				.requestMatchers(
						"/",
						"/c/*",
						"/item/*",
						"/user/login",
						"/user/register",
						"/css/**",
						"/js/**",
						"/images/**")
				.permitAll() // 誰でもアクセス可能なURL
				.anyRequest().authenticated() // その他は全て認証必須
		).exceptionHandling(ex -> ex
				.accessDeniedPage("/error") // エラー発生時に表示する画面
		);
		return http.build();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		// System.out.println(new BCryptPasswordEncoder().encode("admin"));
		return new BCryptPasswordEncoder();
	}
}
