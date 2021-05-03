package com.cccu.assignment.advancedprogramming.Controllers;
import java.util.ArrayList;

import com.cccu.assignment.advancedprogramming.Subjects.Model;
import com.cccu.assignment.advancedprogramming.Subjects.Person;
import com.cccu.assignment.advancedprogramming.Views.GuiView;

public class Controller implements ControllerInterface{
	
	private Model mModel;
	
	public Controller(Model model) {
		mModel = model;
		
		@SuppressWarnings("unused")
		GuiView view = new GuiView(this, mModel);
	}
	
	public void TestCase1() {
		Person person = new Person("Jack", mModel.GetFloor(0), mModel.GetFloor(1));
		
		Runnable runnable = new Runnable() {
			public void run() {
				mModel.SinglePersonTest(person, 0, "Test case 1 has started");
			}
		};
		
		new Thread(runnable).start();
	}
	
	public void TestCase2() {
		Person person = new Person("Jack", mModel.GetFloor(0), mModel.GetFloor(1));
		
		Runnable runnable = new Runnable() {
			public void run() {
				mModel.SinglePersonTest(person, 1, "Test case 2 has started");
			}
		};
		
		new Thread(runnable).start();
	}
	
	public void TestCase3() {
		Person p1 = new Person("Jack", mModel.GetFloor(0), mModel.GetFloor(1));
		Person p2 = new Person("Sam", mModel.GetFloor(0), mModel.GetFloor(1));
		Person p3 = new Person("Jared", mModel.GetFloor(0), mModel.GetFloor(1));
		Person p4 = new Person("Chris", mModel.GetFloor(1), mModel.GetFloor(0));
		
		ArrayList<Person> sendPeople = new ArrayList<Person>();
		sendPeople.add(p1);
		sendPeople.add(p2);
		sendPeople.add(p3);
		sendPeople.add(p4);
		
		Runnable runnable = new Runnable() {
			public void run() {
				mModel.MultiplePersonTest(sendPeople, 1	, "Test case 3 has started");
			}
		};
		
		new Thread(runnable).start();
	}
}