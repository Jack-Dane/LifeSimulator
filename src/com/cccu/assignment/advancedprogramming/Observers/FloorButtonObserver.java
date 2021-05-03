package com.cccu.assignment.advancedprogramming.Observers;

import com.cccu.assignment.advancedprogramming.Subjects.Floor;

public interface FloorButtonObserver {
	public void ButtonReleaseOnFloor(Floor f);
	public void ButtonPressOnFloor(Floor f);
}
