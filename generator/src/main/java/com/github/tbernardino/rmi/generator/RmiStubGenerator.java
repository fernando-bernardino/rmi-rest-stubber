package com.github.tbernardino.rmi.generator;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import javax.tools.Diagnostic;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import com.github.tbernardino.rmi.base.annotation.RmiStub;

@SupportedAnnotationTypes("tbernardino.rmi.base.annotation.RmiStub")
@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class RmiStubGenerator extends AbstractProcessor {

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment) {
		
		try {
			ve = createVelocityEngine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("\n\n\n\n\n\n\n\n");
		for (Element classElement : roundEnvironment.getElementsAnnotatedWith(RmiStub.class)) {

			ClassWrapper classWrapper = new ClassWrapper(classElement);
			if(classWrapper.isSpringController()){
				System.out.println("is spring controller " + classElement + " and path is :");
				
				Arrays.stream(classWrapper.getPath()).forEach(System.out::println);
				
				try {
					generateClass(
							((TypeElement) classElement).getQualifiedName().toString(),
							createVelocityContext(
									classWrapper.getPackage(), 
									classWrapper.getSimpleName(),
									classWrapper.getMethods()));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			} else {
				System.out.println("is NOT spring controller " + classElement);
			}
		}
		return true;
	}
	
	private VelocityEngine ve;
	
	public VelocityContext createVelocityContext(String packageName, String className, List<String> methods) throws IOException {
          VelocityContext vc = new VelocityContext();

          
          System.out.println("\n\n\n\n\n");
          System.out.println("Package " + packageName);
          System.out.println("className " + className);
          System.out.println("methods " + methods);
          System.out.println("\n\n\n\n\n");

          vc.put("packageName", packageName);
          vc.put("className", className);
          vc.put("methods", methods);

          ve.getTemplate("stubTemplate.vm");
          
          return vc;
	}

	private VelocityEngine createVelocityEngine() throws IOException {
		Properties props = new Properties();
          URL url = this.getClass().getClassLoader().getResource("velocity.properties");
          props.load(url.openStream());

          VelocityEngine ve = new VelocityEngine(props);
          ve.init();
		return ve;
	}
	
	public void generateClass(String className, VelocityContext vc) throws IOException{
		JavaFileObject jfo = processingEnv.getFiler().createSourceFile(
				className + "Stub");

            processingEnv.getMessager().printMessage(
                Diagnostic.Kind.NOTE,
                "creating source file: " + jfo.toUri());

            Writer writer = jfo.openWriter();

            Template vt = ve.getTemplate("stubTemplate.vm");
            
            processingEnv.getMessager().printMessage(
                Diagnostic.Kind.NOTE,
                "applying velocity template: " + vt.getName());

            vt.merge(vc, writer);
            //CharArrayWriter w = new CharArrayWriter();
            //vt.merge(vc, new PrintWriter(w));
            //
            //System.out.println("\n\n\n\n\n\n\n output = " + w.toString());

            writer.close();		
	}
}