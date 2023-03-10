// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package PARTSlib2023.PARTS.frc.Utils.Interfaces;

import com.revrobotics.CANSparkMax;

/** Add your docs here. */
public class SparkMaxDistanceValue implements EncoderValueInterface<CANSparkMax> {
    double distPerRot;

    CANSparkMax sparkMax;
    SparkMaxDistanceValue(CANSparkMax sparkMax){
        this.sparkMax = sparkMax;

    }

    @Override
    public double getDistanceRaw() {
        // TODO Auto-generated method stub
        return sparkMax.getEncoder().getPosition();
    }

    @Override
    /**
     * @return in rps
     */
    public double getRate() {
        // TODO Auto-generated method stub
        return sparkMax.getEncoder().getVelocity() / 60;
    }




}
