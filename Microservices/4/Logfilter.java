package com.cognizant.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * LogFilter – doc2: Global filter that logs every request passing through the API Gateway.
 *
 * GlobalFilter applies to ALL routes — unlike GatewayFilter which is route-specific.
 *
 * The filter chain is reactive (Project Reactor Mono):
 *   filter() is called for every request
 *   chain.filter(exchange) passes the request to the next filter/service
 *
 * Usage: Once this @Component is registered, Spring Cloud Gateway automatically
 * picks it up and applies it to every incoming request.
 *
 * Expected log output:
 *   ====>Request URL http://localhost:9090/account-service/accounts/123
 *
 * Verify by:
 *   1. Access: curl http://localhost:9090/account-service/accounts/123
 *   2. Check api-gateway console for the log line
 *
 * doc2 step 22: "Try to access the url http://localhost:9090/greet-service/greet.
 *                Check the console of the api-gateway service and you should be
 *                getting the log shown below."
 */
@Component
public class LogFilter implements GlobalFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogFilter.class);

    /**
     * Called for every request routed through the gateway.
     *
     * @param exchange the current server web exchange (request + response wrapper)
     * @param chain    the filter chain – calling chain.filter(exchange) passes to next filter
     * @return Mono<Void> – reactive signal indicating filter completion
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // Log the full incoming request URI
        LOGGER.info("====>Request URL {}", exchange.getRequest().getURI());
        System.out.println("====>Request URL " + exchange.getRequest().getURI());

        // Continue processing – pass the request to the next filter / target service
        return chain.filter(exchange);
    }
}
