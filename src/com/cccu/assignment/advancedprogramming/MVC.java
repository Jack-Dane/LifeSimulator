package com.cccu.assignment.advancedprogramming;

import com.cccu.assignment.advancedprogramming.Controllers.Controller;
import com.cccu.assignment.advancedprogramming.Subjects.Model;

public class MVC{	
	public static void main(String args[]) {
		Model model = new Model();
		
		@SuppressWarnings("unused")
		Controller c = new Controller(model);
	}
}
