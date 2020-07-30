package com.cccu.assignment.advancedprogramming;

public interface Subject {
	public void NotifyObservers();
	public void AddObserver(Object o);
	public void RemoverObservers(Object o);
}
