package com.github.tbernardino.rmi.base.jersey;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Supplier;

import javax.ws.rs.core.Response.Status;

import org.codehaus.jackson.map.ObjectMapper;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource.Builder;

public abstract class JerseyRequestExecutor {
	
	protected static Client client = Client.create();
	protected static ObjectMapper mapper = new ObjectMapper();
	
	protected ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();

	protected <M> M getBodyAsObject(ClientResponse response, Class<M> clazz) throws IOException{
		return mapper.readValue(response.getEntityInputStream(), clazz);
	}
	
	protected Builder getJsonRequestBuilder(String path) {
		return client.resource(path)
				.accept("application/json");
	}
	
	protected <M> M executeRequest(Supplier<ClientResponse> requestExecutor, Class<M> responseClass) throws IOException {
		
		ClientResponse response = requestExecutor.get();
		
		validateStatusCode(response);
		
		return responseClass == Void.class ?
				null :
				getBodyAsObject(response, responseClass);
	}
	
	private void validateStatusCode(ClientResponse response) {
		
		Status.Family statusFamily = response.getStatusInfo().getFamily();
		
		assert statusFamily == Status.Family.SUCCESSFUL ||
				statusFamily == Status.Family.INFORMATIONAL :
					
					"response returned error " + response.getStatusInfo().getStatusCode() + 
					" - " + response.getStatusInfo().getReasonPhrase();
	}
}
