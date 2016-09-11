package com.github.tbernardino.rmi.base.jersey.it;

public class Example {
	
	int id;
	
	String name;
	
	String description;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public boolean equals(Object object){
		if(object instanceof Example){
			Example other = (Example) object;
			
			return id == other.id 
					&& name.equals(other.name)
					&& description.equals(other.description);
		}
		return false;
	}
	
	@Override
	public int hashCode(){
		int hashCode = 17;
		
		hashCode = hashCode * 31 + id;
		
		if(name != null){
			hashCode = hashCode * 31 + name.hashCode();
		}
		if(description != null) {
			hashCode = hashCode * 31 + description.hashCode();
		}
		
		return hashCode;
	}
}
