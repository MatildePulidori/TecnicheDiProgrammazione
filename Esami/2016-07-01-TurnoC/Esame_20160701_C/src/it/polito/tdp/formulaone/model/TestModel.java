package it.polito.tdp.formulaone.model;

public class TestModel {

	public static void main(String[] args) {
	
		Model model = new Model();
		Constructor c =model.getConstructors().get(0);
		System.out.println(model.getBest(c).toString());

	}

}
