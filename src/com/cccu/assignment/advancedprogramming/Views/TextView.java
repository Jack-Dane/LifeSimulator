package com.cccu.assignment.advancedprogramming.Views;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JTextArea;

import com.cccu.assignment.advancedprogramming.Observers.DoorObserver;
import com.cccu.assignment.advancedprogramming.Observers.FloorButtonObserver;
import com.cccu.assignment.advancedprogramming.Observers.LiftButtonObserver;
import com.cccu.assignment.advancedprogramming.Observers.ModelObserver;
import com.cccu.assignment.advancedprogramming.Observers.PersonObserver;
import com.cccu.assignment.advancedprogramming.Subjects.Floor;
import com.cccu.assignment.advancedprogramming.Subjects.Person;

public class TextView extends JTextArea implements DoorObserver, FloorButtonObserver, LiftButtonObserver, LiftObserverView, PersonObserver, ModelObserver{
	
	private static final long serialVersionUID = 1L;
	private SimpleDateFormat mDateFormat;
	
	public TextView() {
		mDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	}
	
	@Override
	public void PersonCreated(Person p) {
		synchronized(this){
			this.append(mDateFormat.format(new Date()) + " " + p.Name() + " has been created on floor " + p.Floor().FloorNumber() + "\n");
		}
	}

	@Override
	public void PersonEnteredLift(Person p) {
		synchronized(this){
			this.append(mDateFormat.format(new Date()) + " " + p.Name() + " has enetered the lift\n");
		}
	}

	@Override
	public void PersonExitedLift(Person p) {
		synchronized(this){
			this.append(mDateFormat.format(new Date()) + " " + p.Name() + " has exited the lift\n");
		}
	}

	@Override
	public void DoorClosing(Floor f) {
		synchronized(this){
			this.append(mDateFormat.format(new Date()) + " Doors closing on floor number " + f.FloorNumber() + "\n");
		}
	}

	@Override
	public void DoorOpening(Floor f) {
		synchronized(this){
			this.append(mDateFormat.format(new Date()) + " Doors opening on floor number " + f.FloorNumber() + "\n");
		}
	}

	@Override
	public void LiftGoingToFloor(Floor f) {
		synchronized(this){
			this.append(mDateFormat.format(new Date()) + " Lift is travelling to floor number " +f.FloorNumber() + "\n");
		}
	}

	@Override
	public void TestCaseStarted(String testCaseDetails) {
		synchronized(this){
			this.append(mDateFormat.format(new Date()) + " " + testCaseDetails + "\n");
		}
	}

	@Override
	public void ArrivedAtNewFloor(Floor f) {
		synchronized(this){
			this.append(mDateFormat.format(new Date()) + " Lift arrived at floor number " +f.FloorNumber() + "\n");
		}
	}

	@Override
	public void PersonEnded(Person p) {
		synchronized(this){
			this.append(mDateFormat.format(new Date()) + " " + p.Name() + " has walked to end of the hallway \n");	
		}
	}

	@Override
	public void ButtonReleaseOnFloor(Floor f) {
		synchronized(this){
			this.append(mDateFormat.format(new Date()) + " Button Released on Floor " + f.FloorNumber() + "\n");
		}
	}

	@Override
	public void ButtonPressOnFloor(Floor f) {
		synchronized(this){
			this.append(mDateFormat.format(new Date()) + " Button Pressed on Floor " + f.FloorNumber() + "\n");
		}
	}

	@Override
	public void ButtonPressOnLift(Floor f) {
		synchronized(this){
			this.append(mDateFormat.format(new Date()) + " Button Pressed in lift for floor " + f.FloorNumber() + "\n");
		}
	}

	@Override
	public void ButtonReleaseOnLift(Floor f) {
		synchronized(this){
			this.append(mDateFormat.format(new Date()) + " Button Released on Lift for floor " + f.FloorNumber() + "\n");
		}
	}

	@Override
	public void PersonWaitingForLift(Person p) {
		synchronized(this){
			this.append(mDateFormat.format(new Date()) + " " + p.Name() + " is waiting for the lift \n");
		}
	}
}
