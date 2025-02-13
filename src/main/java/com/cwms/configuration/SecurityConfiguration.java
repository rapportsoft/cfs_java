package com.cwms.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import com.cwms.security.JwtAuthenticationEntryPoint;
import com.cwms.security.JwtAuthenticationFilter;

@Configuration
public class SecurityConfiguration {

    @Autowired
    private JwtAuthenticationEntryPoint point;
    @Autowired
    private JwtAuthenticationFilter filter;

    
    @SuppressWarnings("deprecation")
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
        MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);

        http.csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Enables CORS with the provided configuration source
            .authorizeRequests(auth -> auth       		
                		.requestMatchers(mvcMatcherBuilder.pattern("/unclaim/**")).permitAll()
                		.requestMatchers(mvcMatcherBuilder.pattern("/combineRepresentative/**")).permitAll()
                		.requestMatchers(mvcMatcherBuilder.pattern("/dashboard/**")).permitAll()
                		.requestMatchers(mvcMatcherBuilder.pattern("/payment/**")).permitAll()
                		.requestMatchers(mvcMatcherBuilder.pattern("/auth/**")).permitAll()
                		.requestMatchers(mvcMatcherBuilder.pattern("/predictable/**")).permitAll()
                		.requestMatchers(mvcMatcherBuilder.pattern("/rights")).permitAll()
                		.requestMatchers(mvcMatcherBuilder.pattern("/gateinout/**")).permitAll()
                		 .requestMatchers(mvcMatcherBuilder.pattern("/scan/**")).permitAll()
                		.requestMatchers(mvcMatcherBuilder.pattern("/ShippingDetails/**")).permitAll()
                		.requestMatchers(mvcMatcherBuilder.pattern("/jar/addJar/**")).permitAll()
                		.requestMatchers(mvcMatcherBuilder.pattern("/partyLoa/**")).permitAll()
                		.requestMatchers(mvcMatcherBuilder.pattern("/jar/**")).permitAll()
                		.requestMatchers(mvcMatcherBuilder.pattern("/forwardout/**")).permitAll()
                		.requestMatchers(mvcMatcherBuilder.pattern("/forwardin/**")).permitAll()
                		.requestMatchers(mvcMatcherBuilder.pattern("/defaultparty/**")).permitAll()
                		.requestMatchers(mvcMatcherBuilder.pattern("/externaluserrights/**")).permitAll()
                		.requestMatchers(mvcMatcherBuilder.pattern("/externalParty/**")).permitAll()
                		.requestMatchers(mvcMatcherBuilder.pattern("/represent/**")).permitAll()
                		.requestMatchers(mvcMatcherBuilder.pattern("/importpc/**")).permitAll()
                		.requestMatchers(mvcMatcherBuilder.pattern("/importsub/**")).permitAll()
                		.requestMatchers(mvcMatcherBuilder.pattern("/history/**")).permitAll()
                		.requestMatchers(mvcMatcherBuilder.pattern("/importmain/**")).permitAll()
                		.requestMatchers(mvcMatcherBuilder.pattern("/exportsub/**")).permitAll()
                		.requestMatchers(mvcMatcherBuilder.pattern("/jardetail/**")).permitAll()
                		.requestMatchers(mvcMatcherBuilder.pattern("/representive/**")).permitAll()
                		.requestMatchers(mvcMatcherBuilder.pattern("/barcodeGenerater/**")).permitAll()
                		.requestMatchers(mvcMatcherBuilder.pattern("/externalparty/**")).permitAll()
                		.requestMatchers(mvcMatcherBuilder.pattern("/api/processnextids/**")).permitAll()
                		.requestMatchers(mvcMatcherBuilder.pattern("/holiday/**")).permitAll()
                		.requestMatchers(mvcMatcherBuilder.pattern("/exportpc/**")).permitAll()
                		.requestMatchers(mvcMatcherBuilder.pattern("/export1/**")).permitAll()
                		.requestMatchers(mvcMatcherBuilder.pattern("/sbtransactions/**")).permitAll()
                		.requestMatchers(mvcMatcherBuilder.pattern("/UserCreation/**")).permitAll()
                		.requestMatchers(mvcMatcherBuilder.pattern("/excelupload/**")).permitAll()
                		.requestMatchers(mvcMatcherBuilder.pattern("/export/**")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/user/**")).authenticated()
                		.requestMatchers(mvcMatcherBuilder.pattern("/detention/**")).permitAll()
                		.requestMatchers(mvcMatcherBuilder.pattern("/detention-history/**")).permitAll()
                		.requestMatchers(mvcMatcherBuilder.pattern("/parties/**")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/api/**")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/Invoice/**")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/invoicetaxdetails/**")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/importHeavy/**")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/import/**")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/import/tpdate")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/api1/**")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/api2/**")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/Airline/**")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("service/**")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("cfstarrif/**")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/tarrif/**")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/range/**")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/NewReprentative/**")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/externalParty/**")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/home/**")).authenticated()
                        .requestMatchers(mvcMatcherBuilder.pattern("/party/**")).authenticated()
                        .requestMatchers(mvcMatcherBuilder.pattern("/api/yardblockcells/**")).authenticated()
                        .requestMatchers(mvcMatcherBuilder.pattern("/vessel/**")).authenticated()
                        .requestMatchers(mvcMatcherBuilder.pattern("/voyage/**")).authenticated()
                        .requestMatchers(mvcMatcherBuilder.pattern("/port/**")).authenticated()
                        .requestMatchers(mvcMatcherBuilder.pattern("/IsoContainer/**")).authenticated()
                        .requestMatchers(mvcMatcherBuilder.pattern("/api/profitcentres/**")).authenticated()
                        .requestMatchers(mvcMatcherBuilder.pattern("/service/**")).authenticated()
                        .requestMatchers(mvcMatcherBuilder.pattern("/cfigm/**")).authenticated()
                        .requestMatchers(mvcMatcherBuilder.pattern("/api/tax/**")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/gateIn/**")).authenticated()
                        .requestMatchers(mvcMatcherBuilder.pattern("/equipmentActivity/**")).authenticated()
                        .requestMatchers(mvcMatcherBuilder.pattern("/destuff/**")).authenticated()
                        .requestMatchers(mvcMatcherBuilder.pattern("/importGatePass/**")).authenticated()
                        .requestMatchers(mvcMatcherBuilder.pattern("/api/cfbondnoc/**")).authenticated()
                        .requestMatchers(mvcMatcherBuilder.pattern("/api/cfinbondcrg/**")).authenticated()
                        .requestMatchers(mvcMatcherBuilder.pattern("/api/cfexbondcrg/**")).authenticated()
                        .requestMatchers(mvcMatcherBuilder.pattern("/api/cfbondgatepass/**")).authenticated()
                        .requestMatchers(mvcMatcherBuilder.pattern("/api/cfinbondgrid/**")).authenticated()
                        .requestMatchers(mvcMatcherBuilder.pattern("/api/gateOutController/**")).authenticated()
                        .requestMatchers(mvcMatcherBuilder.pattern("/api/exbondgrid/**")).authenticated()
                        .requestMatchers(mvcMatcherBuilder.pattern("/api/bondingReport/**")).authenticated()
                        .requestMatchers(mvcMatcherBuilder.pattern("/api/cfexbondcrgEditController/**")).authenticated()
                        .requestMatchers(mvcMatcherBuilder.pattern("/importGateOut/**")).authenticated()
                        .requestMatchers(mvcMatcherBuilder.pattern("/emptyOrder/**")).authenticated()
                        .requestMatchers(mvcMatcherBuilder.pattern("/commonGatePass/**")).authenticated()
                        .requestMatchers(mvcMatcherBuilder.pattern("/manualContainerGateIn/**")).authenticated()
                        .requestMatchers(mvcMatcherBuilder.pattern("/holdDetail/**")).authenticated()
                        .requestMatchers(mvcMatcherBuilder.pattern("/excelUpload/**")).authenticated()
                        .requestMatchers(mvcMatcherBuilder.pattern("/ssr/**")).authenticated()
                        .requestMatchers(mvcMatcherBuilder.pattern("/stuffTally/**")).authenticated()
                        .requestMatchers(mvcMatcherBuilder.pattern("/exportGatePass/**")).authenticated()
                        .requestMatchers(mvcMatcherBuilder.pattern("/exportBackToTown/**")).authenticated()
                        .requestMatchers(mvcMatcherBuilder.pattern("/portReturn/**")).authenticated()
                        .requestMatchers(mvcMatcherBuilder.pattern("/api/importReports/**")).authenticated()
                        .requestMatchers(mvcMatcherBuilder.pattern("/api/exportOperationalReports/**")).authenticated()
                        .requestMatchers(mvcMatcherBuilder.pattern("/importReports/**")).authenticated()
                        .requestMatchers(mvcMatcherBuilder.pattern("/assessment/**")).authenticated()
                        .requestMatchers(mvcMatcherBuilder.pattern("/api/commonReports/**")).authenticated()
                        .requestMatchers(mvcMatcherBuilder.pattern("/api/financeReports/**")).authenticated()
                        .requestMatchers(mvcMatcherBuilder.pattern("/api/dashboard/**")).authenticated()
                        .requestMatchers(mvcMatcherBuilder.pattern("/servicemapping/**")).authenticated()
                        .requestMatchers(mvcMatcherBuilder.pattern("/receipt/**")).authenticated()
                        .requestMatchers(mvcMatcherBuilder.pattern("/importinvoiceprint/**")).authenticated()
                        .requestMatchers(mvcMatcherBuilder.pattern("/receiptprint/**")).authenticated()
                        .requestMatchers(mvcMatcherBuilder.pattern("/proforma/**")).authenticated()

                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex.authenticationEntryPoint(point))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
        
        
    }

    
//    @Bean
//    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true);
//        config.addAllowedOriginPattern("http://103.189.88.215:84");
//      //  config.addAllowedOriginPattern("http://localhost:3000");
//        config.addAllowedHeader("*");
//        config.addAllowedMethod("*");
//        config.setMaxAge(3600L);
//        source.registerCorsConfiguration("/**", config);
//        return source;
//    }
    
    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // Enable cookies and credentials
        config.addAllowedOriginPattern("*"); // Accept any origin
        config.addAllowedHeader("*"); // Allow all headers
        config.addAllowedMethod("*"); // Allow all HTTP methods (GET, POST, PUT, DELETE, etc.)
        config.setMaxAge(3600L); // Cache the preflight request for 3600 seconds
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    
    
    
}
