package it.polito.tdp.artsmia.model;

public class ArtObject {

	private int id;
	private String classification;
	private String title;
	
	
	public ArtObject(int id, String classification,String title) {
		super();
		this.id = id;
		this.classification = classification;
		this.title = title;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getClassification() {
		return classification;
	}


	public void setClassification(String classification) {
		this.classification = classification;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ArtObject other = (ArtObject) obj;
		if (id != other.id)
			return false;
		return true;
	}


	@Override
	public String toString() {
		return title + "";
	}
	

	
}
