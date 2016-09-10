package tbernardino.rmi.base.jersey;

import java.util.concurrent.Future;

import com.sun.jersey.api.client.ClientResponse;

import tbernardino.rmi.base.executor.PostRequestExecutor;

public class JerseyPostRequestExecutor extends JerseyRequestExecutor implements PostRequestExecutor {

	@Override
	public <M,V> V executeSync(String path, M body, Class<V> responseClass) throws Exception {
		
		String inputData = mapper.writeValueAsString(body);
		
		return executeRequest(
					() -> getWebResource(path)
							.accept("application/json")
							.post(ClientResponse.class, inputData), 
					responseClass);
	}

	@Override
	public <M,V> Future<V> executeAsync(String path, M body, Class<V> responseClass) throws Exception {
		
		return executor.submit(() -> executeSync(path, body, responseClass));
		
	}
}
