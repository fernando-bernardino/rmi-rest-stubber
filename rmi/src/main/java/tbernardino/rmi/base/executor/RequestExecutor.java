package tbernardino.rmi.base.executor;

public interface RequestExecutor {
	
	public static String baseUrl = "http://localhost";
	
	public default String getFullPath(String path){
		return baseUrl + path;
	}
}
