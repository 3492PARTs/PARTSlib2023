// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package PARTSlib2023.PARTS.frc.Utils.Interfaces;

import java.util.function.DoubleSupplier;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.estimator.DifferentialDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/** Add your docs here. */
public abstract class beanieDriveTrain extends SubsystemBase {

    AHRS gyro;
    protected DifferentialDrive mDrive;
    protected MotorControllerGroup leftControllerGroup, rightControllerGroup;
    protected Rotation2d gyroZrotation;

    public beanieDriveTrain(AHRS gyro, MotorControllerGroup leftControllerGroup,
            MotorControllerGroup rightMotorControllerGroup) {
        this.gyro = gyro;
        Shuffleboard.getTab("Debug").add(gyro);
        Shuffleboard.getTab("Debug").addNumber("Angle", getAngSupplier());
        Shuffleboard.getTab("Debug").addNumber("pitch", getPitchSupplier());
        Shuffleboard.getTab("Debug").addNumber("yaw", getYawSupplier());


        this.leftControllerGroup = leftControllerGroup;
        this.rightControllerGroup = rightMotorControllerGroup;
        rightControllerGroup.setInverted(true);
        mDrive = new DifferentialDrive(leftControllerGroup, rightControllerGroup);
        mDrive.setSafetyEnabled(false);
        this.gyroZrotation = new Rotation2d(0);
    }

    public beanieDriveTrain(AHRS gyro, Rotation2d gryoZRotation, MotorControllerGroup leftControllerGroup,
            MotorControllerGroup rightMotorControllerGroup) {
        this.gyro = gyro;
        Shuffleboard.getTab("Debug").add(gyro);
        Shuffleboard.getTab("Debug").addNumber("Angle", getAngSupplier());


        this.leftControllerGroup = leftControllerGroup;
        this.rightControllerGroup = rightMotorControllerGroup;
        rightMotorControllerGroup.setInverted(true);
        mDrive = new DifferentialDrive(leftControllerGroup, rightControllerGroup);
        this.gyroZrotation = gryoZRotation;
    }

    /**
     * @apiNote only use for auto these inputs are not squared
     * @param left  left percent output
     * @param right right percent output
     */
    public void move(double left, double right) {
        mDrive.tankDrive(left/2, right/2, false);
    }
    public void moveCurvature(double speed, double radiusofrotation){
        mDrive.curvatureDrive(speed/2, radiusofrotation/2, true);
    }

    public double getVelocityGyro() {
        return gyro.getVelocityZ();
    }

    public void calibrateGyro(){
        gyro.calibrate();
    }

    public void moveVolts(double leftVoltage, double rightVoltage) {
        leftControllerGroup.setVoltage(leftVoltage); // TODO: maybe need to denegate.
        rightControllerGroup.setVoltage(rightVoltage);
        mDrive.feed();

    }

    public double getVelocityGyroXY(){
        return Math.sqrt(Math.pow(gyro.getVelocityX(),2) + Math.pow(gyro.getVelocityY(),2));
    }

    public double getPitch() {
        return gyro.getPitch();
    }

    public void moveArcade(double X, double rotation) {
        mDrive.arcadeDrive(X/2, rotation/2);
    }

    public abstract Pose2d currentPose();

    public double getAngle() {
        return gyro.getAngle();
    }

    public double getYaw() {
        return -gyro.getYaw();
    }

    public DoubleSupplier getAngSupplier(){
        DoubleSupplier s = () -> getRotation().getRotations() * 360;
        return s;
    }

    public DoubleSupplier getYawSupplier(){
        DoubleSupplier s = () -> getYaw();
        return s;
    }

    public DoubleSupplier getPitchSupplier(){
        DoubleSupplier s = () -> getPitch();
        return s;
    }

    public double getRoll() {
        return -gyro.getRoll();
    }

    public double getTurningRate() {
        return gyro.getRate();
    }

    public Rotation2d getRotation() {
        return gyro.getRotation2d().times(-1).rotateBy(gyroZrotation);
    }

    public abstract double leftDistance();

    public abstract double rightDistance();

    public void stop() {
        mDrive.tankDrive(0, 0);
    }

    public abstract void autonomousSetup();

    public abstract void teleopSetup();

}
