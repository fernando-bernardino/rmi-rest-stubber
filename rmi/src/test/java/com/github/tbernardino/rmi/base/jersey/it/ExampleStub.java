package com.github.tbernardino.rmi.base.jersey.it;

import java.util.HashMap;

import com.github.tbernardino.rmi.base.RmiRestStub;

public class ExampleStub extends RmiRestStub {
	
	// GET /example/{id}/inner -> Example
	public Example getInnerForExampleWithId(String id) throws Exception {
		
		String path = "/example/{id}/inner";
		
		HashMap<String, String> values = new HashMap<String, String>(1);
		values.put("id", id);
		
		return doGet(path, values, Example.class);
	}
	
	//POST /example/{id}/inner -> Location
	public Void addInnerForExampleWithId(String id, Example example) throws Exception{
		
		String path = "/example/{id}/inner";
		
		HashMap<String, String> values = new HashMap<String, String>(1);
		values.put("id", id);
		
		return doPost(path, values, example, Void.class);
	}
}
