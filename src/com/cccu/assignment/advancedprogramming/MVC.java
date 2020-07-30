package com.cccu.assignment.advancedprogramming;

public class MVC{	
	public static void main(String args[]) {
		Model model = new Model();
		
		@SuppressWarnings("unused")
		Controller c = new Controller(model);
	}
}
