package com.cccu.assignment.advancedprogramming.Subjects;
import java.util.ArrayList;

import com.cccu.assignment.advancedprogramming.Observers.DoorObserver;
import com.cccu.assignment.advancedprogramming.Observers.FloorButtonObserver;
import com.cccu.assignment.advancedprogramming.Observers.LiftButtonObserver;
import com.cccu.assignment.advancedprogramming.Observers.ModelObserver;
import com.cccu.assignment.advancedprogramming.Observers.PersonObserver;
import com.cccu.assignment.advancedprogramming.Utils.ButtonFactory;
import com.cccu.assignment.advancedprogramming.Views.LiftObserverView;

public class Model extends Thread implements Subject{	
	//items
	private ArrayList<LiftButton> mLiftButtons = new ArrayList<LiftButton>();
	private ArrayList<FloorButton> mFloorButtons = new ArrayList<FloorButton>();
	private ArrayList<Door> mDoors = new ArrayList<Door>();
	private ArrayList<Floor> mFloors = new ArrayList<Floor>();
	private Lift mLift;
	
	//observer lists
	private ArrayList<ModelObserver> mModelObservers = new ArrayList<ModelObserver>();
	private ArrayList<PersonObserver> mPersonObservers = new ArrayList<PersonObserver>();
	
	private String mTestText;
	
	public Model() {
		//floors
		//to do x amount of floors create final int floor number iterate through and add to list
		Floor firstFloor = new Floor(0);
		mFloors.add(firstFloor);
		Floor secondFloor = new Floor(1);
		mFloors.add(secondFloor);
		
		//floor buttons, factory pattern
		FloorButton firstFloorButton = (FloorButton) ButtonFactory.getButton("Floor", firstFloor);
		mFloorButtons.add(firstFloorButton);
		
		FloorButton secondFloorButton = (FloorButton) ButtonFactory.getButton("Floor", secondFloor);
		mFloorButtons.add(secondFloorButton);
		
		//floor doors
		Door firstFloorDoor = new Door(firstFloor);
		mDoors.add(firstFloorDoor);
		Door secondFloorDoor = new Door(secondFloor);
		mDoors.add(secondFloorDoor);
		
		//lift
		mLift = Lift.GetInstance();
		
		//lift buttons, factory pattern
		LiftButton liftButton1 = (LiftButton) ButtonFactory.getButton("Lift", firstFloor);
		mLiftButtons.add(liftButton1);
		LiftButton liftButton2 = (LiftButton) ButtonFactory.getButton("Lift", secondFloor);
		mLiftButtons.add(liftButton2);
		
		//starting lift thread
		mLift.SetCurrentFloor(firstFloor);
		mLift.start();
	}
	
	public void SinglePersonTest(Person person, int startingFloor, String testName) {
		mTestText = testName;
		NotifyObservers();
		
		for(PersonObserver po : mPersonObservers) {
			person.registerObserver(po);
		}
		
		mLift.SetCurrentFloor(mFloors.get(startingFloor));
		
		person.start();
	}
	
	public void MultiplePersonTest(ArrayList<Person> people, int startingFloor, String testName) {
		mTestText = testName;
		NotifyObservers();
		
		for(Person p : people) {
			for(PersonObserver po : mPersonObservers) {
				p.registerObserver(po);
			}
		}
		
		mLift.SetCurrentFloor(mFloors.get(startingFloor));
		
		people.get(0).start();
		people.remove(0);
		people.get(people.size() - 1).start();
		people.remove(people.size() - 1);
		
		for(Person p : people) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			p.start();
		}
	}
	
	public void registerLiftObserver(LiftObserverView observer) {
		mLift.AddObserver(observer);
	}
	
	public void registerDoorObserver(DoorObserver observer) {
		for(Door door: mDoors) {
			door.AddObserver(observer);
		}
	}
	
	public void registerPersonObserver(PersonObserver observer) {
		mPersonObservers.add(observer);
	}
	
	public void registerFloorButtonObserver(FloorButtonObserver observer) {
		for(FloorButton floorButton : mFloorButtons) {
			floorButton.AddObserver(observer);
		}
	}
	
	public void registerLiftButtonObserver(LiftButtonObserver observer) {
		for(LiftButton liftButton : mLiftButtons) {
			liftButton.AddObserver(observer);
		}
	}
	
	public Floor GetFloor(int n) {
		return mFloors.get(n);
	}

	@Override
	public void NotifyObservers() {
		for(ModelObserver o: mModelObservers) {
			o.TestCaseStarted(mTestText);
		}
	}

	@Override
	public void AddObserver(Object o) {
		this.mModelObservers.add((ModelObserver)o);
	}

	@Override
	public void RemoverObservers(Object o) {
		this.mModelObservers.remove((ModelObserver)o);
	}
}
