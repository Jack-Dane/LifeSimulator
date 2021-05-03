package com.cccu.assignment.advancedprogramming;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;


public class AnimationPanel extends JPanel implements DoorObserver, FloorButtonObserver, LiftObserverView, PersonObserver, ModelObserver{
	
	private static final long serialVersionUID = 1L;
	private ArrayList<ButtonGraphic> mButtons = new ArrayList<ButtonGraphic>();
	private ArrayList<DoorGraphic> mDoors = new ArrayList<DoorGraphic>();
	private ArrayList<LiftDisplay> mLiftDisplay = new ArrayList<LiftDisplay>();
	private Map<Person, PersonGraphic> mPeople = new HashMap<Person, PersonGraphic>();
	private LiftGraphic mLift;
	private TextGraphic testCaseText;
	
	private Color mWallColor1 = new Color(255, 215, 215);
	private Color mWallColor2 = new Color(255, 250, 196);
	
	private enum DIRECTION{
		LEFT,
		RIGHT
	}
	
	private enum OPERATION{
		OPENING,
		CLOSING,
		OPEN,
		CLOSED
	}
	
	public AnimationPanel(){
		this.setVisible(true);
		
		mLiftDisplay.add(new LiftDisplay(640, 215, "G"));
		mLiftDisplay.add(new LiftDisplay(640, 27, "1"));
		
		testCaseText = new TextGraphic();
	    
		mLift = new LiftGraphic(600, 235);
		
	    mDoors.add(new DoorGraphic(600, 235));
	    mButtons.add(new ButtonGraphic(550, 310));
	    
	    mDoors.add(new DoorGraphic(600, 47));
	    mButtons.add(new ButtonGraphic(550, 120));
	    
	    this.setBackground(Color.WHITE);
	    
	    Timer timer = new Timer(50, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				repaint();
			}
	    	
	    });
	    timer.start();
	}
	
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
	    
		//set background
		for(int i=0; i<15; i++) {
			for(int x=0; x<25; x++) {
				if((x % 2 == 0 && i % 2 == 0) || (x % 2 != 0 && i % 2 != 0)) {
					g.setColor(mWallColor1);
				}else {
					g.setColor(mWallColor2);
				}
				g.fillRect(x * 30, i * 30, 30, 30);
			}
		}
		
		//draw second floor
		g.setColor(Color.BLACK);
		g.fillRect(0, this.getHeight()/2, this.getWidth(), 25);
	    
	    //draw the lift
	    mLift.DrawLift(g);
	    
	    //draw all the buttons
	    for(ButtonGraphic bg : mButtons) {
	    	bg.drawButton(g);
	    }
	    
	    for(LiftDisplay ld : mLiftDisplay) {
	    	ld.DrawObject(g);
	    }
	    
	    synchronized(mPeople) {
		    //if person is not walking, eg in lift, then draw them behind doors
		    for(PersonGraphic pg : mPeople.values()) {
		    	if(pg.inLift) {
		    		pg.DrawPerson(g);
		    	}
		    }
	    }
	    
	    //draw all the doors
	    for(DoorGraphic dg : mDoors) {
	    	if(dg.operation == OPERATION.CLOSING) {
	    		dg.Closing();
	    	}else if(dg.operation == OPERATION.OPENING) {
	    		dg.Opening();
	    	}
	    	dg.drawDoor(g);
	    }
	    
	    synchronized(mPeople) {
		    //if person id walking, draw them in front of doors
		    for(PersonGraphic pg : mPeople.values()) {
		    	if(pg.IsWalking()) {
		    		pg.Move();
		    	}
		    	if(!pg.inLift) {
		    		pg.DrawPerson(g);
		    	}
		    }
	    }
	    
	    testCaseText.DrawText(g);
	}
	
	public class LiftDisplay{
		private String displayString = "";
		private int x,y;
		private int flashCounter = 0;
		private boolean flashing = false;
		
		public LiftDisplay(int x, int y, String display) {
			displayString = display;
			this.x = x;
			this.y = y;
		}
		
		public void startFlashing(String displayText) {
			if(displayText.equals("0")) {
				displayText = "G";
			}
			displayString = displayText;
			flashing = true;
		}
		
		public void stopFlashing(String displayText) {
			if(displayText.equals("0")) {
				displayText = "G";
			}
			displayString = displayText;
			flashing = false;
			flashCounter = 0;
		}
		
		public void DrawObject(Graphics g) {
			
			g.setColor(Color.BLACK);
			g.fillRect(this.x, this.y, 20, 20);
			
			if(!flashing || flashCounter < 10) {
				g.setColor(Color.RED);
				g.drawString(displayString, this.x + 7, this.y + 15);
			}
			
			flashCounter++;
			
			if(flashCounter > 20) {
				flashCounter = 0;
			}
		}
	}
	
	public class TextGraphic{
		private String text = "";
		
		public TextGraphic() {
		}
		
		public void DrawText(Graphics g) {
			g.setColor(Color.BLACK);
			g.drawString(text, 3, 15);
		}
		
		public void ChangeText(String text) {
			this.text = text;
		}
	}
	
	public class LiftGraphic{
		private int x, y;
		private int width = 100;
		private int height = 140;
		private ArrayList<PersonGraphic> peopleInLift = new ArrayList<PersonGraphic>();
		private Color lightColor = new Color(255, 255, 255, 100);
		
		public LiftGraphic(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		public void DrawLift(Graphics g) {
			g.setColor(Color.DARK_GRAY);
			g.fillRect(x, y, width, height);
			
			//draw inside rectangle
			g.setColor(Color.BLACK);
			g.drawRect(x + 30, y + 30, width - 60, height - 60);
			
			//draw inside connecting lines to inner rectangle 
			g.drawLine(x, y, x + 30, y + 30);
			g.drawLine(x + width - 1, y, x + width - 30, y + 30);
			g.drawLine(x, y + height - 1, x + 30, y + height - 30);
			g.drawLine(x + width - 1, y + height - 1, x + width - 30, y + height - 30);
			
			//draw the light
			g.setColor(lightColor);
			g.fillArc(x, y - height, width, height * 2, 225, 90);
			
			//draw the people in the lift
			int size = peopleInLift.size();
			for(int loopCount = 0; loopCount < size; loopCount++) {
				PersonGraphic p = peopleInLift.get(loopCount);
				p.SetCoords(this.x + (loopCount * 10), this.y + p.height/2 - 10);
			}
		}
		
		public void MoveToFloor(int floorNumber) {
			if(floorNumber == 0) {
				this.y = 235;
			}else {
				this.y = 47;
			}
			
			for(PersonGraphic pg : peopleInLift) {
				pg.SetDirection(DIRECTION.LEFT);
			}
		}
		
		public void AddPerson(PersonGraphic p) {
			peopleInLift.add(p);
		}
		
		public void ExitLift(PersonGraphic p) {
			p.SetCoords(this.x, this.y + p.height/2);
			peopleInLift.remove(p);
		}
	}
	
	public class DoorGraphic{
		private int leftx, y, rightx;//y is the same as they are always on same level
		private int width = 50;
		private int height = 140;
		private OPERATION operation = OPERATION.CLOSED; 
		
		public DoorGraphic(int x, int y) {
			this.leftx = x;
			this.y = y;
			this.rightx = leftx + 50;
		}
		
		public void drawDoor(Graphics g) {
			g.setColor(Color.GRAY);
			g.fillRect(this.leftx, this.y, this.width, this.height);
			g.setColor(Color.BLACK);
			g.drawRect(this.leftx, this.y, this.width, this.height);
			g.setColor(Color.GRAY);
			g.fillRect(this.rightx, this.y, this.width, this.height);
			g.setColor(Color.BLACK);
			g.drawRect(this.rightx, this.y, this.width, this.height);
		}
		
		public void Open() {
			operation = OPERATION.OPENING;
		}
		
		public void Close() {
			operation = OPERATION.CLOSING;
		}
		
		public void Opening() {
			if(this.width > 10) {
				this.width -= 5;
				this.rightx += 5;
			}else {
				operation = OPERATION.OPEN;
			}
		}
		
		public void Closing() {
			if(this.width < 50) {
				this.width += 5;
				this.rightx -= 5;
			}else {
				operation = OPERATION.CLOSED;
			}
		}
	}
	
	public class ButtonGraphic{
		private int x,y;
		private Color color;
		
		public ButtonGraphic(int x, int y) {
			this.x = x;
			this.y = y;
			color = Color.GREEN;
		}
		
		public void pressButton() {
			color = Color.RED;
		}
		
		public void releaseButton() {
			color = Color.GREEN;
		}
		
		public void drawButton(Graphics g) {
			g.setColor(color);
			g.fillOval(x, y, 10, 10);
		}
	}
	
	public class PersonGraphic{
		private int x = 0;
		private int y = 0;
		private DIRECTION travelDirection = DIRECTION.RIGHT;//walking direction
		private boolean walking = true;
		private boolean inLift = false;
		private int height = 100;
		private int width = 70;
		private int xDestination = 550;//where to wait when pushed button
		private int imageNumber = 0;
		private ImageIcon img;
		private int speed = 6;
		
		public PersonGraphic(int floorNumber) {
			switch(floorNumber) {
			case 0:
				this.y = 283;
				break;
			case 1:
				this.y = 93;
				break;
			}
		}
		
		public void SetCoords(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		public boolean IsWalking() {
			return walking;
		}
		
		public void SetDirection(DIRECTION d) {
			travelDirection = d;
		}
		
		public void Move() {
			if(travelDirection == DIRECTION.RIGHT) {
				if(this.x <= xDestination) {
					this.x += speed;
				}else {
					if(this.x > 600) {
						//entering lift
						EnterLift();
					}else {
						//waiting for lift
						this.walking = false;
					}
				}
			}else {
				this.x -= speed;//walk till deleted
			}
			imageNumber ++;
		}
		
		public void WalkToLift() {
			xDestination = 600;
			walking = true;
		}
		
		public void EnterLift() {
			inLift = true;
			walking = false;
			mLift.AddPerson(this);
		}
		
		public void ExitLift() {
			inLift = false;
			walking = true;
			travelDirection = DIRECTION.LEFT;
			mLift.ExitLift(this);
		}
		
		public void Waiting() {
			imageNumber = 0;
			walking = false;
		}
		
		public void DrawPerson(Graphics g) {
			//change walking image every 3 ticks on the timer
			int igNumber = Math.floorDiv(imageNumber, 3);
			igNumber += 1;
			
			if(travelDirection == DIRECTION.LEFT) {
				if(walking) {
					img = new ImageIcon(getClass().getResource("Assets/LeftWalk" + igNumber + ".png"));
				}else {
					img = new ImageIcon(getClass().getResource("Assets/WaitLeft.png"));
				}
			}else if(travelDirection == DIRECTION.RIGHT) {
				if(walking) {
					img = new ImageIcon(getClass().getResource("Assets/RightWalk" + igNumber + ".png"));
				}else {
					img = new ImageIcon(getClass().getResource("Assets/WaitRight.png"));
				}
			}
			
			if(imageNumber >= 29) {
				imageNumber = 0;
			}
			
			Image png = img.getImage();
			g.drawImage(png, x, y, width, height, null);
		}
	}

	@Override
	public void TestCaseStarted(String testCaseDetails) {
		this.testCaseText.ChangeText(testCaseDetails);
	}

	@Override
	public void PersonCreated(Person p) {
		PersonGraphic pg = new PersonGraphic(p.Floor().FloorNumber());
		synchronized(mPeople) {
			this.mPeople.put(p, pg);
		}
	}

	@Override
	public void PersonEnteredLift(Person p) {
		synchronized(mPeople) {
			mPeople.get(p).WalkToLift();
		}
	}

	@Override
	public void PersonExitedLift(Person p) {
		synchronized(mPeople) {
			mPeople.get(p).ExitLift();	
		}
	}

	@Override
	public void PersonEnded(Person p) {
		synchronized(mPeople) {
			mPeople.remove(p);
		}
	}

	@Override
	public void DoorClosing(Floor f) {
		mDoors.get(f.FloorNumber()).Close();
	}

	@Override
	public void DoorOpening(Floor f) {
		mDoors.get(f.FloorNumber()).Open();
	}

	@Override
	public void LiftGoingToFloor(Floor f) {
		for(LiftDisplay ld: mLiftDisplay) {
			ld.startFlashing(Integer.toString(f.FloorNumber()));
		}
	}

	@Override
	public void ArrivedAtNewFloor(Floor f) {
		mLift.MoveToFloor(f.FloorNumber());
		for(LiftDisplay ld: mLiftDisplay) {
			ld.stopFlashing(Integer.toString(f.FloorNumber()));
		}
	}

	@Override
	public void ButtonReleaseOnFloor(Floor f) {
		mButtons.get(f.FloorNumber()).releaseButton();
	}

	@Override
	public void ButtonPressOnFloor(Floor f) {
		mButtons.get(f.FloorNumber()).pressButton();
	}

	@Override
	public void PersonWaitingForLift(Person p) {
		synchronized(mPeople) {
			mPeople.get(p).Waiting();	
		}
	}
}
