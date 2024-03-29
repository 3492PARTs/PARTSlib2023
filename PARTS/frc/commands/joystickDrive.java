// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package PARTSlib2023.PARTS.frc.commands;

import java.util.concurrent.Callable;

import PARTSlib2023.PARTS.frc.Utils.Controls.beanieController;
import PARTSlib2023.PARTS.frc.Utils.Interfaces.beanieDriveTrain;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj2.command.CommandBase;



public class joystickDrive extends CommandBase {
    beanieDriveTrain bDriveTrain;
    beanieController controller;
    double speedFactor = .5;
    Callable<Double> leftStick;
    Callable<Double> rightStick;
    SlewRateLimiter speedLimiter = new SlewRateLimiter(.5, -1 , 0);
    SlewRateLimiter angularSpeedLimiter = new SlewRateLimiter(.25);

  /** Creates a new joystickDrive. */

  /**
   * 
   * @param bDriveTrain the drivetrain object
   * @param beanieController the driver controller

   */
  public joystickDrive(beanieDriveTrain bDriveTrain, beanieController controller) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(bDriveTrain);
    this.bDriveTrain = bDriveTrain;
    this.controller = controller;


  }

  public joystickDrive(beanieDriveTrain bDriveTrain, beanieController controller, double sf) {
    addRequirements(bDriveTrain);
    this.bDriveTrain = bDriveTrain;
    this.controller = controller;
    this.speedFactor = sf;
  }

  // Called when the command is initially scheduled.
    @Override
    public void initialize() {
      System.out.println("initialized");
    }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {


    try {
      if(controller.getLeftBumper().getAsBoolean() == true) {
        speedFactor = 0.5;
      } else {
        speedFactor = 1;
      }
      bDriveTrain.moveCurvature(-speedLimiter.calculate(controller.getLeftYAxis() * speedFactor), -.5*(controller.getRightXAxis()));
      

    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    bDriveTrain.stop();
  }
  

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
