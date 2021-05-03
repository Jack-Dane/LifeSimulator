package com.cccu.assignment.advancedprogramming.Observers;

import com.cccu.assignment.advancedprogramming.Subjects.Person;

public interface PersonObserver {
	public void PersonCreated(Person p);
	public void PersonEnded(Person p);
	public void PersonWaitingForLift(Person p);
	public void PersonEnteredLift(Person p);
	public void PersonExitedLift(Person p);
}
