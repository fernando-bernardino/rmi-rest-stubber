package com.github.tbernardino.rmi.generator;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.PackageElement;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

public class ClassWrapper {
	private Element classElement;
	
	private String [] restPath;
	
	private List<? extends Element> postMethods;
	
	private List<? extends Element> getMethods;
	
	public ClassWrapper(Element classElement){
		this.classElement = classElement;
		
		this.restPath = getRestPathFromController();
	}
	
	private String [] getRestPathFromController() {
		return isAnnotatedWithController(classElement) ?
				getControllerPath() :
				null;
	}
	private String [] getControllerPath() {
		RequestMapping m = classElement.getAnnotation(RequestMapping.class);
		return m.value();
	}

	private boolean isAnnotatedWithController(Element classElement) {
		return classElement.getAnnotation(RestController.class) != null ||
				classElement.getAnnotation(Controller.class) != null;
	}
	
	public boolean isSpringController() {
		return restPath != null;
	}
	
	public String [] getPath(){
		return this.restPath;
	}
	
	public List<String> getMethods() {
		return classElement.getEnclosedElements().stream()
				.filter(method -> method.getKind() == ElementKind.METHOD)
				.filter(method -> method.getAnnotation(RequestMapping.class) != null)
				.map(method -> method.getSimpleName().toString())
				.collect(Collectors.toList());
	}
	
	public String getPackage(){
		return ((PackageElement) classElement
				.getEnclosingElement())
				.getQualifiedName()
				.toString();
	}
	
	public String getSimpleName(){
		return classElement.getSimpleName().toString();
	}
}
