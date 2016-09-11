package com.github.tbernardino.rmi.base.jersey;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;

import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import com.github.tbernardino.rmi.base.jersey.JerseyGetRequestExecutor;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource.Builder;

public class JerseyGetRequestExecutorTest {
	
	@Spy
	JerseyGetRequestExecutor getExecutor;
	
	@Mock
	Builder builder;
	
	@Mock
	ClientResponse response;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void executeSync_successfulCall_expectedResult() throws Exception {
		// String path, Class<V> responseClass
		int statusCode = 200;
		String path = "path";
		String expectedResponse = "response";
		
		setupCall(path, expectedResponse, statusCode);
		
		//	when
		String returnedResult = getExecutor.executeSync(path, expectedResponse.getClass());
		
		//	then
		assertEquals(expectedResponse, returnedResult);
	}

	private void setupCall(String path, String expectedResponse, int statusCode) {
		doReturn(expectedResponse).when(response).getEntity(expectedResponse.getClass());
		doReturn(statusCode).when(response).getStatus();
		doReturn(Response.Status.fromStatusCode(statusCode)).when(response).getStatusInfo();
		doReturn(response).when(builder).get(ClientResponse.class);
		doReturn(builder).when(getExecutor).getJsonRequestBuilder(path);
	}
}
