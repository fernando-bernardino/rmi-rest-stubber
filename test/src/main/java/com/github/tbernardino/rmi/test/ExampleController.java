package com.github.tbernardino.rmi.test;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.tbernardino.rmi.base.annotation.RmiStub;
import com.github.tbernardino.rmi.base.jersey.it.Example;

@RmiStub
@RestController
@RequestMapping("/example")
public class ExampleController {
	
	
	@RequestMapping(value = "/{id}/inner")
	public @ResponseBody Example get(@PathVariable String id){
		return new Example();
	}

	@RequestMapping(value = "/{id}/inner", method = RequestMethod.POST)
	public void post(@PathVariable String id, @RequestBody Example example){
	}
}
