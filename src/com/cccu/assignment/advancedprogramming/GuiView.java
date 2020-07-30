package com.cccu.assignment.advancedprogramming;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JPanel;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class GuiView extends JFrame{	
	
	private static final long serialVersionUID = 1L;
	private ControllerInterface mController;
	private Model mModel;
	private ImageIcon mFrameIcon;
	
	//buttons
	private JButton uiTestCase1Button;
	private JButton uiTestCase2Button;
	private JButton uiTestCase3Button;
	private JButton uiClearButton;
	
	//views
	private TextView uiTextArea;
	private AnimationPanel uiAnimationPanel;
	private ConsoleView consoleView;
	
	public GuiView(ControllerInterface controller, Model model) {
		super("Lift Simulator");
		
		mFrameIcon = new ImageIcon(getClass().getResource("LiftIcon.png"));
		this.setIconImage(mFrameIcon.getImage());
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mController = controller;
		mModel = model;
		
		this.setBounds(0, 0, 904, 622);
		getContentPane().setLayout(null);
		
		uiTextArea = new TextView();
		JScrollPane scrollPane = new JScrollPane(uiTextArea);
		scrollPane.setBounds(10, 400, 868, 182);
		
		mModel.registerDoorObserver(uiTextArea);
		mModel.registerPersonObserver(uiTextArea);
		mModel.registerLiftObserver(uiTextArea);
		mModel.registerFloorButtonObserver(uiTextArea);
		mModel.registerLiftButtonObserver(uiTextArea);
		mModel.AddObserver(uiTextArea);
		
		getContentPane().add(scrollPane);
		
		JPanel panel = new JPanel();
		panel.setBounds(747, 11, 131, 378);
		getContentPane().add(panel);
		panel.setLayout(new GridLayout(4, 1, 0, 0));
		
		ButtonPress bp = new ButtonPress();
		
		uiTestCase1Button = new JButton("Test Case 1");
		uiTestCase1Button.addActionListener(bp);
		panel.add(uiTestCase1Button);
		
		uiTestCase2Button = new JButton("Test Case 2");
		uiTestCase2Button.addActionListener(bp);
		panel.add(uiTestCase2Button);
		
		uiTestCase3Button = new JButton("Test Case 3");
		uiTestCase3Button.addActionListener(bp);
		panel.add(uiTestCase3Button);
		
		uiClearButton = new JButton("Clear");
		uiClearButton.addActionListener(bp);
		panel.add(uiClearButton);
		
		uiAnimationPanel = new AnimationPanel();
		uiAnimationPanel.setBounds(10, 11, 727, 378);
		uiAnimationPanel.setVisible(true);
		getContentPane().add(uiAnimationPanel);
		
		mModel.registerDoorObserver(uiAnimationPanel);
		mModel.registerPersonObserver(uiAnimationPanel);
		mModel.registerLiftObserver(uiAnimationPanel);
		mModel.registerFloorButtonObserver(uiAnimationPanel);
		mModel.AddObserver(uiAnimationPanel);
		
		consoleView = new ConsoleView();
		mModel.registerDoorObserver(consoleView);
		mModel.registerPersonObserver(consoleView);
		mModel.registerLiftObserver(consoleView);
		mModel.registerFloorButtonObserver(consoleView);
		mModel.registerLiftButtonObserver(consoleView);
		mModel.AddObserver(consoleView);
		
		this.setVisible(true);
	}
	
	public class ButtonPress implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if((JButton)e.getSource() == uiTestCase1Button) {
				mController.TestCase1();
			}else if((JButton)e.getSource() == uiTestCase2Button) {
				mController.TestCase2();
			}else if((JButton)e.getSource() == uiTestCase3Button) {
				mController.TestCase3();
			}else {
				uiTextArea.setText("");
			}
		}
	}
}
