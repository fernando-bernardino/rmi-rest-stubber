package com.github.tbernardino.rmi.base.jersey;

import java.util.concurrent.Future;

import com.github.tbernardino.rmi.base.executor.PostRequestExecutor;
import com.sun.jersey.api.client.ClientResponse;

public class JerseyPostRequestExecutor extends JerseyRequestExecutor implements PostRequestExecutor {

	@Override
	public <M,V> V executeSync(String path, M body, Class<V> responseClass) throws Exception {
		
		String inputData = mapper.writeValueAsString(body);
		
		return executeRequest(
					() -> doPost(path, inputData), 
					responseClass);
	}

	private ClientResponse doPost(String path, String inputData) {
		return getJsonRequestBuilder(path)
				.post(ClientResponse.class, 
						inputData);
	}

	@Override
	public <M,V> Future<V> executeAsync(String path, M body, Class<V> responseClass) throws Exception {
		
		return executor.submit(() -> executeSync(path, body, responseClass));
		
	}
}
