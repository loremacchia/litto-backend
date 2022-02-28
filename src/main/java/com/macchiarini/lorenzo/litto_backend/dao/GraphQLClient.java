package com.macchiarini.lorenzo.litto_backend.dao;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GraphQLClient {

	private URI url;
	private HttpClient client;

	public GraphQLClient() {
		url = URI.create("http://localhost:4000/");
		client = HttpClient.newHttpClient();
	}

//	public <T> T query(String queryName, String queryBody, Class<T> returnType) {
//		queryBody = "{\"query\":\"query { " + queryName + " " + queryBody + "}\"}";
//		HttpRequest request = HttpRequest.newBuilder().POST(BodyPublishers.ofString(queryBody))
//				.header("Content-Type", "application/json").uri(url).build();
//		HttpResponse<String> response;
//		try {
//			System.out.println(queryBody);
//
//			ObjectMapper mapper = new ObjectMapper();
//			response = client.send(request, BodyHandlers.ofString());
//			System.out.println(response.body());
//			JsonNode node = mapper.readTree(response.body());
//			return mapper.readValue(node.get("data").get(queryName).toString(), returnType);
//		} catch (IOException e) {
//			e.printStackTrace();
//			return null;
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//			return null;
//		}
//	}

	public <T> T query(String entityName, String whereClause, String returnFields, Class<T> returnType) {
		String queryBody = "{\"query\":\"query { " + entityName + " ";
		if (whereClause != null) {
			queryBody += "(where: {" + whereClause + "}) ";
		}
		queryBody += "{ " + returnFields + "}";
		queryBody += "}\"}";

		HttpRequest request = HttpRequest.newBuilder().POST(BodyPublishers.ofString(queryBody))
				.header("Content-Type", "application/json").uri(url).build();
		HttpResponse<String> response;
		try {
			System.out.println(queryBody);

			ObjectMapper mapper = new ObjectMapper();
			response = client.send(request, BodyHandlers.ofString());
			System.out.println(response.body());
			JsonNode node = mapper.readTree(response.body());
			return mapper.readValue(node.get("data").get(entityName).toString(), returnType);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return null;
		}
	}

	public <T> T mutate(String mutationName, String entityName, String updateClause, String whereClause,
			String returnFields, Class<T> returnType) {
		String mutationBody = "{\"query\":\"mutation {" + mutationName;

		if (updateClause != null || whereClause != null) {
			mutationBody += "( ";
			if (updateClause != null) {
				mutationBody += "update: {" + updateClause + "}, ";
			}
			if (whereClause != null) {
				mutationBody += "where: {" + whereClause + "} ";
			}
			mutationBody += ") ";
		}
		mutationBody += "{ " + entityName + " { " + returnFields + " }}}\"}";

		HttpRequest request = HttpRequest.newBuilder().POST(BodyPublishers.ofString(mutationBody))
				.header("Content-Type", "application/json").uri(url).build();
		HttpResponse<String> response;
		try {
			System.out.println(mutationBody);
			ObjectMapper mapper = new ObjectMapper();
			response = client.send(request, BodyHandlers.ofString());
			System.out.println(response.body());
			JsonNode node = mapper.readTree(response.body());
			return mapper.readValue(node.get("data").get(mutationName).get(entityName).toString(), returnType);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return null;
		}
	}

	public <T> T create(String mutationTitle, String mutationName, String entityName, String inputClause,
			String whereClause, String returnFields, Class<T> returnType) {
		String mutationBody = "{\"query\":\"mutation " + mutationTitle + "{" + mutationName;

		if (inputClause != null || whereClause != null) {
			mutationBody += "( ";
			if (inputClause != null) {
				mutationBody += "input: {" + inputClause + "}, ";
			}
			if (whereClause != null) {
				mutationBody += "where: {" + whereClause + "} ";
			}
			mutationBody += ") ";
		}
		mutationBody += "{ " + entityName + " { " + returnFields + " }}}\"}";
		System.out.println(mutationBody);
		HttpRequest request = HttpRequest.newBuilder().POST(BodyPublishers.ofString(mutationBody))
				.header("Content-Type", "application/json").uri(url).build();
		HttpResponse<String> response;
		try {
			System.out.println(mutationBody);
			ObjectMapper mapper = new ObjectMapper();
			response = client.send(request, BodyHandlers.ofString());
			System.out.println(response.body());
			JsonNode node = mapper.readTree(response.body());
			return mapper.readValue(node.get("data").get(mutationName).get(entityName).toString(), returnType);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return null;
		}
	}

	public <T> T update(String mutationTitle, String mutationName, String entityName, String updateClause,
			String whereClause, String returnFields, Class<T> returnType) {
		String mutationBody = "{\"query\":\"mutation " + mutationTitle + "{" + mutationName;

		if (updateClause != null || whereClause != null) {
			mutationBody += "( ";
			if (updateClause != null) {
				mutationBody += "update: {" + updateClause + "}, ";
			}
			if (whereClause != null) {
				mutationBody += "where: {" + whereClause + "} ";
			}
			mutationBody += ") ";
		}
		mutationBody += "{ " + entityName + " { " + returnFields + " }}}\"}";

		HttpRequest request = HttpRequest.newBuilder().POST(BodyPublishers.ofString(mutationBody))
				.header("Content-Type", "application/json").uri(url).build();
		HttpResponse<String> response;
		try {
			System.out.println(mutationBody);
			ObjectMapper mapper = new ObjectMapper();
			response = client.send(request, BodyHandlers.ofString());
			System.out.println(response.body());
			JsonNode node = mapper.readTree(response.body());
			return mapper.readValue(node.get("data").get(mutationName).get(entityName).toString(), returnType);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return null;
		}
	}

	public <T> T plainRequest(String requestBody, String entityName, Class<T> returnType) {
		HttpRequest request = HttpRequest.newBuilder().POST(BodyPublishers.ofString(requestBody))
				.header("Content-Type", "application/json").uri(url).build();
		HttpResponse<String> response;
		try {
			ObjectMapper mapper = new ObjectMapper();
			response = client.send(request, BodyHandlers.ofString());
			System.out.println(response.body());
			JsonNode node = mapper.readTree(response.body());
			return mapper.readValue(node.get("data").get(entityName).toString(), returnType);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return null;
		}
	}
}
