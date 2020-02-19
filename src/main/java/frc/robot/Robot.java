/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
 
package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import java.sql.Driver;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.revrobotics.CANSparkMax;
import com.revrobotics.EncoderType;
import com.revrobotics.SparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import frc.robot.Subsystems.*;


import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;


import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.*;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  public static DriveTrain driveSub;
  //public static Shooter shooterSub;
  public static Pivot pivotSub;
  public static Elevator elevatorSub;
  public static Intake intakeSub;


  /*----------------------------------
  EXAMPLE SUBSYTEM SETUP
  */
  //public static ExampleSub expSub;

  /*
  public WPI_TalonFX _leftMaster = driveSub._leftMaster;
  public WPI_TalonFX _rightMaster = driveSub._rightMaster;
  public WPI_TalonFX  _leftFollow = driveSub._leftFollow;
  public WPI_TalonFX  _rightFollow = driveSub._rightFollow;

  DifferentialDrive _drive = driveSub._drive;
  */


  //CANSparkMax Pivot = new CANSparkMax(6, MotorType.kBrushless);

  private AddressableLED m_led;
  private AddressableLEDBuffer m_ledBuffer;
  // Store what the last hue of the first pixel is
  private int m_rainbowFirstPixelHue;

  public static OI m_oi;

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    //Pivot.restoreFactoryDefaults();
    //Pivot.getEncoder(EncoderType.kHallSensor, 4096);

    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);

    SmartDashboard.putData("Auto choices", m_chooser);
    System.out.println("robotinit");

     // PWM port 9
    // Must be a PWM header, not MXP or DIO
    //m_led = new AddressableLED(9);

    // Reuse buffer
    // Default to a length of 60, start empty output
    // Length is expensive to set, so only set it once, then just update data
    //m_ledBuffer = new AddressableLEDBuffer(300);
    //m_led.setLength(m_ledBuffer.getLength());

    // Set the data
    //m_led.setData(m_ledBuffer);
    //m_led.start();  
    driveSub = new DriveTrain();
    pivotSub = new Pivot();
    elevatorSub = new Elevator();
    intakeSub = new Intake();


    /*----------------------------------
    EXAMPLE SUBSYTEM INIT
    */
    //expSub = new ExampleSub();

    //ALWAYS LAST!!!!!!!!!!!!!!!!!!!!!!!!!!
    m_oi = new OI();
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Fill the buffer with a rainbow
    //rainbow();
    // Set the LEDs
    //m_led.setData(m_ledBuffer);
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
    //System.out.println("run teleop");

   

  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
    
  }

  private void rainbow() {
    // For every pixel
    for (var i = 0; i < m_ledBuffer.getLength(); i++) {
      // Calculate the hue - hue is easier for rainbows because the color
      // shape is a circle so only one value needs to precess
      final var hue = (m_rainbowFirstPixelHue + (i * 180 / m_ledBuffer.getLength())) % 180;
      // Set the value
      m_ledBuffer.setHSV(i, hue, 255, 128);
      
    }
    // Increase by to make the rainbow "move"
    m_rainbowFirstPixelHue += 3;
    // Check bounds
    m_rainbowFirstPixelHue %= 180;
  }
  /*
  public void pidDrive() {
    final double kP = 0; //change as needed (Speed)
    final double kI = 0; //change as needed
    final double kD = 0; //change as needed

    double setPoint = 10; //change as needed
    double errorSum = 0; //change as needed
    double lastTimestamp = 0; //change as needed
    double lastError = 0; //change as needed


    double sensorPos = (((_leftMaster.getSelectedSensorPosition()+_leftFollow.getSelectedSensorPosition())/2+(_rightMaster.getSelectedSensorPosition() + _rightFollow.getSelectedSensorPosition())/2)/2)/2048;    
    double error = setPoint - sensorPos;
    double iLimit = 1;
    double dT = Timer.getFPGATimestamp() - lastTimestamp;

    double errorRate = (error - lastError)/dT;
    double motorPower = kP * error + kI * errorSum + kD * errorRate;

    if(Math.abs(error) < iLimit){
      errorSum += error * dT;

    }
    _drive.arcadeDrive(motorPower,motorPower);

    lastTimestamp = Timer.getFPGATimestamp();
    lastError = error;
  }
  */
}

