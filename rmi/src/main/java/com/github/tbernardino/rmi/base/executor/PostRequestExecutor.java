package com.github.tbernardino.rmi.base.executor;

import java.util.concurrent.Future;

public interface PostRequestExecutor {
	
	public <M,V> V executeSync(String path, M body, Class<V> responseClass) throws Exception;
	
	public <M,V> Future<V> executeAsync(String path, M body, Class<V> responseClass) throws Exception;

}
