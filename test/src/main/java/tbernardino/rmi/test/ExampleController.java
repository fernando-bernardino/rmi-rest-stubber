package tbernardino.rmi.test;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tbernardino.rmi.base.annotation.RmiStub;

@RmiStub
@RestController
@RequestMapping("/api")
public class ExampleController {
	
	private boolean noParametersNoReturnCalled = false;
	private boolean noParametersPostReturnCalled = false;
	
	public void noPath(){
		noParametersNoReturnCalled = true;
	}
	
	@RequestMapping(value = "/noReturn")
	@ResponseBody
	public void noParametersNoReturn(){
		noParametersNoReturnCalled = true;
	}
	
	@RequestMapping(value = "/noReturnPost", method = RequestMethod.POST)
	@ResponseBody
	public void postWithNoReturn(){
		noParametersPostReturnCalled = true;
	}
}
