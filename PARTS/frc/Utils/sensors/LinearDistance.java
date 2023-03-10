// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package PARTSlib2023.PARTS.frc.Utils.sensors;

import java.util.Arrays;

import PARTSlib2023.PARTS.frc.Utils.Interfaces.EncoderValueInterface;





/** Add your docs here. */
// deprecated
@Deprecated
public class LinearDistance{
    EncoderValueInterface<?>[] groupOne, groupTwo;
    double[] initialGroupOne, initialGroupTwo ;
    double wheelCircumference;
    double gearRatio;

    double[] groupOneTotals, groupTwoTotals;
    LinearDistance(EncoderValueInterface<?>[] groupOne, EncoderValueInterface<?>[] groupTwo, double wheelCircumference, double gearRatio){
        this.groupOne = groupOne;
        this.groupTwo = groupTwo;
        this.gearRatio = gearRatio;
        this.wheelCircumference = wheelCircumference;

        for (int i = 0; i < groupTwo.length; i++) {
            
        }
    }


    /**
     * 
     * @return the average of the left side of the drive train
     */
    public double getGroupOneAverage() {
        update();
        return -Average(groupOneTotals);// use only for left side drive train
    }
    
    public double getGroupTwoAverage() {
        update();
        return Average(groupTwoTotals);
    }




    private void update() {

        for (int i = 0; i < groupOne.length; i++) {
            groupOneTotals[i] = ((groupOne[i].getDistanceRaw()- initialGroupOne[i]) * wheelCircumference ) / gearRatio;
        }
        for (int i = 0; i < groupTwo.length; i++) {
            groupTwoTotals[i] = ((groupTwo[i].getDistanceRaw() - initialGroupTwo[i]) * wheelCircumference) / gearRatio;
        }
    }


// Average of two highest

    protected double Average(double[] totals) { 
        int sum = 0;
        double[] tempTotal = totals.clone();
        Arrays.sort(tempTotal);
        for(int i = totals.length-1; i>0; i--){
            sum += tempTotal[i];
        }
        
        return sum/(totals.length-1);
    }

}

