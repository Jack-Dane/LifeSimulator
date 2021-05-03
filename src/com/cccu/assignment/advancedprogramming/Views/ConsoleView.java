package com.cccu.assignment.advancedprogramming.Views;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.cccu.assignment.advancedprogramming.Observers.DoorObserver;
import com.cccu.assignment.advancedprogramming.Observers.FloorButtonObserver;
import com.cccu.assignment.advancedprogramming.Observers.LiftButtonObserver;
import com.cccu.assignment.advancedprogramming.Observers.ModelObserver;
import com.cccu.assignment.advancedprogramming.Observers.PersonObserver;
import com.cccu.assignment.advancedprogramming.Subjects.Floor;
import com.cccu.assignment.advancedprogramming.Subjects.Person;

public class ConsoleView implements DoorObserver, FloorButtonObserver, LiftButtonObserver, LiftObserverView, PersonObserver, ModelObserver{

	private SimpleDateFormat mDateFormat;
	
	public ConsoleView() {
		mDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	}
	
	@Override
	public void PersonCreated(Person p) {
		System.out.println(mDateFormat.format(new Date()) + " " + p.Name() + " has been created on floor " + p.Floor().FloorNumber());
	}

	@Override
	public void PersonEnteredLift(Person p) {
		System.out.println(mDateFormat.format(new Date()) + " " + p.Name() + " has enetered the lift");
	}

	@Override
	public void PersonExitedLift(Person p) {
		System.out.println(mDateFormat.format(new Date()) + " " + p.Name() + " has exited the lift");
	}

	@Override
	public void DoorClosing(Floor f) {
		System.out.println(mDateFormat.format(new Date()) + " Doors closing on floor number " + f.FloorNumber());
	}

	@Override
	public void DoorOpening(Floor f) {
		System.out.println(mDateFormat.format(new Date()) + " Doors opening on floor number " + f.FloorNumber());
	}

	@Override
	public void LiftGoingToFloor(Floor f) {
		System.out.println(mDateFormat.format(new Date()) + " Lift is travelling to floor number " +f.FloorNumber());
	}

	@Override
	public void TestCaseStarted(String testCaseDetails) {
		System.out.println(mDateFormat.format(new Date()) + " " + testCaseDetails);
	}

	@Override
	public void ArrivedAtNewFloor(Floor f) {
		System.out.println(mDateFormat.format(new Date()) + " Lift arrived at floor number " +f.FloorNumber());
	}

	@Override
	public void PersonEnded(Person p) {
		System.out.println(mDateFormat.format(new Date()) + " " + p.Name() + " has walked to end of the hallway");
	}

	@Override
	public void ButtonReleaseOnFloor(Floor f) {
		System.out.println(mDateFormat.format(new Date()) + " Button Released on Floor " + f.FloorNumber());
	}

	@Override
	public void ButtonPressOnFloor(Floor f) {
		System.out.println(mDateFormat.format(new Date()) + " Button Pressed on Floor " + f.FloorNumber());
	}

	@Override
	public void ButtonPressOnLift(Floor f) {
		System.out.println(mDateFormat.format(new Date()) + " Button Pressed in lift for floor " + f.FloorNumber());
	}

	@Override
	public void ButtonReleaseOnLift(Floor f) {
		System.out.println(mDateFormat.format(new Date()) + " Button Released on Lift for floor " + f.FloorNumber());
	}

	@Override
	public void PersonWaitingForLift(Person p) {
		System.out.println(mDateFormat.format(new Date()) + " " + p.Name() + " is waiting for the lift");
	}

}
