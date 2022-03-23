package com.macchiarini.lorenzo.litto_backend.filters;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.macchiarini.lorenzo.litto_backend.commondto.TokenIDDto;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ServiceResponseFilter implements ContainerResponseFilter {

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {
		
		List<String> pathsNoToken = Arrays.asList("gql/user", "gql/user/login", "ogm/user", "ogm/user/login");
		String authHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
		if (responseContext.getEntity() != null) {
			if (pathsNoToken.contains(requestContext.getUriInfo().getPath())
					&& requestContext.getMethod().equals("POST")) {
				TokenIDDto ti = TokenIDDto.class.cast(responseContext.getEntity());
				responseContext.setEntity(ti.getId());
				responseContext.getHeaders().add(HttpHeaders.AUTHORIZATION, "Bearer " + ti.getToken());
				responseContext.setStatus(200);
			} else if (requestContext.getMethod().equals("DELETE")
					|| requestContext.getUriInfo().getPath().contains("logout")) {
				responseContext.setStatus(200);
			} else {
				responseContext.setStatus(200);
				responseContext.getHeaders().add(HttpHeaders.AUTHORIZATION, authHeader);
			}
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
		responseContext.getHeaders().add("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
	}
}
