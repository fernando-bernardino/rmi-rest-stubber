package tbernardino.rmi.base.jersey;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Supplier;

import javax.ws.rs.core.Response.Status;

import org.codehaus.jackson.map.ObjectMapper;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import tbernardino.rmi.base.executor.RequestExecutor;

public abstract class JerseyRequestExecutor implements RequestExecutor {
	
	protected static Client client = Client.create();
	protected static ObjectMapper mapper = new ObjectMapper();
	
	protected ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);

	public <M> M getBodyAsObject(ClientResponse response, Class<M> clazz) throws IOException{
		return mapper.readValue(response.getEntityInputStream(), clazz);
	}
	
	public WebResource getWebResource(String path) {
		return client.resource(getFullPath(path));
	}
	
	public <M> M executeRequest(Supplier<ClientResponse> requestExecutor, Class<M> responseClass) throws IOException {
		
		ClientResponse response = requestExecutor.get();
		
		validateStatusCode(response);
		
		return getBodyAsObject(response, responseClass);
	}
	
	public void validateStatusCode(ClientResponse response) {
		
		Status.Family statusFamily = response.getStatusInfo().getFamily();
		
		assert statusFamily == Status.Family.SUCCESSFUL ||
				statusFamily == Status.Family.INFORMATIONAL :
					
					"response returned error " + response.getStatusInfo().getStatusCode() + 
					" - " + response.getStatusInfo().getReasonPhrase();
	}
}
