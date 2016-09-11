package com.github.tbernardino.rmi.base.jersey;

import java.util.concurrent.Future;

import com.github.tbernardino.rmi.base.executor.GetRequestExecutor;
import com.sun.jersey.api.client.ClientResponse;

public class JerseyGetRequestExecutor  extends JerseyRequestExecutor implements GetRequestExecutor {

	@Override
	public <M, V> V executeSync(String path, Class<V> responseClass) throws Exception {
		
		return executeRequest(
				() -> doGet(path), 
				responseClass);
	}

	private ClientResponse doGet(String path) {
		return getJsonRequestBuilder(path)
				.get(ClientResponse.class);
	}

	@Override
	public <M, V> Future<V> executeAsync(String path, Class<V> responseClass) throws Exception {
		
		return executor.submit(() -> executeSync(path, responseClass));
	}

}
