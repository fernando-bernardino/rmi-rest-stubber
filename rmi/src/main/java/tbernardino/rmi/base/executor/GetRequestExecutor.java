package tbernardino.rmi.base.executor;

import java.util.concurrent.Future;

public interface GetRequestExecutor {
	
	public <M,V> V executeSync(String path, Class<V> responseClass) throws Exception;
	
	public <M,V> Future<V> executeAsync(String path, Class<V> responseClass) throws Exception;

}
