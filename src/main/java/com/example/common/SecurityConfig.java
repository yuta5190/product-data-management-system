package com.example.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * spring securityに関するクラス.
 * 
 * @author yoshida_yuuta
 *
 */

@Configuration
public class SecurityConfig {

	/**
	 * このメソッドをオーバーライドすることで、 特定のリクエストに対して「セキュリティ設定」を 無視する設定など全体にかかわる設定ができる.
	 * 具体的には静的リソースに対してセキュリティの設定を無効にする。
	 * 
	 */
	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().requestMatchers("/css/**", "/img/**", "/js/**", "/api/**",
				"/com/example/repository/**");
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		// 認可に関する設定
		http.authorizeHttpRequests()
				.requestMatchers("/", "/login-user","/login-user/**", "/insert-user", "/insert-user/insert",
						"/registerUser", "/registerUser/register", "/showitemlist", "/showitemlist/**","/showcategorylist","/showcategorylist/**")
				.permitAll().anyRequest().authenticated();

		// ログインに関する設定
		http.formLogin().loginPage("/login-user").loginProcessingUrl("/login-user/login")
				.failureUrl("/login-user?error=true").defaultSuccessUrl("/showitemlist", true)
				.usernameParameter("email").passwordParameter("password");

		// ログアウトに関する設定
		http.logout().logoutRequestMatcher(new AntPathRequestMatcher("/login-user/logout"))
				.logoutSuccessUrl("/login-user").deleteCookies("JSESSIONID").invalidateHttpSession(true);

		return http.build();

	}

	/**
	 * アルゴリズムのハッシュ化する実装を行うメソッド.
	 * 
	 * @return bcryptアルゴリズムでハッシュ化する実装オブジェクト
	 */
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
