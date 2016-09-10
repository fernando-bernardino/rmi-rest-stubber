package tbernardino.rmi.base.jersey;

import java.util.concurrent.Future;

import com.sun.jersey.api.client.ClientResponse;

import tbernardino.rmi.base.executor.GetRequestExecutor;

public class JerseyGetRequestExecutor  extends JerseyRequestExecutor implements GetRequestExecutor {

	@Override
	public <M, V> V executeSync(String path, Class<V> responseClass) throws Exception {
		
		return executeRequest(
				() -> getWebResource(path)
						.accept("application/json")
						.get(ClientResponse.class), 
				responseClass);
	}

	@Override
	public <M, V> Future<V> executeAsync(String path, Class<V> responseClass) throws Exception {
		
		return executor.submit(() -> executeSync(path, responseClass));
	}

}
