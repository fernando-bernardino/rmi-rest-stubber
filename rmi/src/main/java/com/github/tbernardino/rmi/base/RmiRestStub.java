package com.github.tbernardino.rmi.base;

import java.util.Map;
import java.util.Map.Entry;

import com.github.tbernardino.rmi.base.executor.GetRequestExecutor;
import com.github.tbernardino.rmi.base.executor.PostRequestExecutor;
import com.github.tbernardino.rmi.base.jersey.JerseyGetRequestExecutor;
import com.github.tbernardino.rmi.base.jersey.JerseyPostRequestExecutor;

public class RmiRestStub {
	
	ServerProperties serverProperties = new ServerProperties();

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
		
		return getFullPath(path);
	}
	
	public String getFullPath(String path){
		return serverProperties.getBaseUrl() + path;
	}
	
	public void setServerProperties(ServerProperties serverProperties) {
		this.serverProperties = serverProperties;
	}

	public void setGetExecutor(GetRequestExecutor getExecutor) {
		this.getExecutor = getExecutor;
	}

	public void setPostExecutor(PostRequestExecutor postExecutor) {
		this.postExecutor = postExecutor;
	}
}