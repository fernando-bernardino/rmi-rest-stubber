package com.github.tbernardino.rmi.base.jersey.it;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.Assert.assertEquals;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.github.tbernardino.rmi.base.RmiRestStub;
import com.github.tbernardino.rmi.base.ServerProperties;
import com.github.tbernardino.rmi.base.jersey.JerseyGetRequestExecutor;
import com.github.tbernardino.rmi.base.jersey.JerseyPostRequestExecutor;
import com.github.tomakehurst.wiremock.junit.WireMockRule;

public class RmiStubTestIT {
	
	RmiRestStub stub;
	
	ObjectMapper mapper = new ObjectMapper();
	
	@Rule
	public WireMockRule wireMockRule = new WireMockRule(wireMockConfig().dynamicPort());
	
	@Before
	public void setup(){
		int port = wireMockRule.port();
		
		 setupStub(port);
	}
	
	@Test
	public void getTest() throws Exception {
		
		String expectedPath = "/example/100/resource";
		String realPath = "/example/{id}/resource";
		
		HashMap<String, String> pathVariables = new HashMap<>();
		pathVariables.put("id", "100");
		
		Example example = createExample();
		
	    stubFor(get(urlEqualTo(expectedPath))
	            .withHeader("Accept", equalTo("application/json"))
	            .willReturn(aResponse()
	                .withStatus(200)
	                .withHeader("Content-Type", "application/json")
	                .withBody(mapper.writeValueAsString(example))));

	    Example returned = stub.doGet(realPath, pathVariables, Example.class);

	    assertEquals(example, returned);
	}

	
	@Test
	public void postTest() throws Exception {
		String expectedPath = "/example/100/resource";
		String realPath = "/example/{id}/resource";
		
		HashMap<String, String> pathVariables = new HashMap<>();
		pathVariables.put("id", "100");
		
		Example example = createExample();
		
	    stubFor(post(urlEqualTo(expectedPath))
	            .withHeader("Accept", equalTo("application/json"))
	            .willReturn(aResponse()
	                .withStatus(200)
	                .withHeader("Content-Type", "application/json")
        			.withBody("201 Location: nowhere")));
	    
	    stub.doPost(realPath, pathVariables, example, Void.class);

	    verify(postRequestedFor(urlMatching("/example/100/resource"))
	            .withHeader("Content-Type", notMatching("application/json")));
	}

	private void setupStub(int port) {
		stub = new RmiRestStub();
		
	    ServerProperties server = new ServerProperties();
	    server.setBaseUrl("http://localhost:" + port);
	    
	    stub.setServerProperties(server);
	    stub.setGetExecutor(new JerseyGetRequestExecutor());
	    stub.setPostExecutor(new JerseyPostRequestExecutor());
	}

	private Example createExample() {
		Example example = new Example();
		example.setId(1);
		example.setName("some");
		example.setDescription("thing");
		return example;
	}
}
