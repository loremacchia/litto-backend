package com.macchiarini.lorenzo.litto_backend.filters;

import java.io.IOException;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;

//TODO aggiungi postmatch
@Provider
public class ServiceResponseFilter implements ContainerResponseFilter {

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {
//		List<String> pathsNoToken = Arrays.asList("gql/user", "gql/user/login", "ogm/user", "ogm/user/login");
		if (responseContext.getEntity() != null) {
			responseContext.setStatus(200);
		} else {
			if(requestContext.getMethod().equals("OPTIONS")) {
				responseContext.setStatus(200);
			}
			else {
				responseContext.setStatus(400);
			}
		}     
		responseContext.getHeaders().add("Access-Control-Allow-Origin", "*");
		responseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");
		responseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, DELETE, HEAD, OPTIONS");
		responseContext.getHeaders().add("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, authorization");		
	}
}
