package tbernardino.rmi.base;

import java.util.Map;
import java.util.Map.Entry;

import tbernardino.rmi.base.executor.GetRequestExecutor;
import tbernardino.rmi.base.executor.PostRequestExecutor;
import tbernardino.rmi.base.jersey.JerseyGetRequestExecutor;
import tbernardino.rmi.base.jersey.JerseyPostRequestExecutor;

public abstract class RmiRestStub {
	
	GetRequestExecutor getExecutor = new JerseyGetRequestExecutor();
	
	PostRequestExecutor postExecutor = new JerseyPostRequestExecutor();

	public <M,V> V doGet(String path, Map<String, String> pathVariables, Class<V> responseClass) throws Exception {
		
		return getExecutor.executeSync(getPathInstantiation(path, pathVariables),  responseClass);
	}
	
	public <M, V> V doPost(String path, Map<String, String> pathVariables,  M body, Class<V> responseClass) throws Exception {
		
		return postExecutor.executeSync(getPathInstantiation(path, pathVariables),  body, responseClass);
		
	}
	
	protected String getPathInstantiation(String path, Map<String, String> pathVariables){
		
		for(Entry<String, String> entry : pathVariables.entrySet()){
			path = path.replace("{" + entry.getKey() + "}", entry.getValue());
		}
		
		return path;
	}
}