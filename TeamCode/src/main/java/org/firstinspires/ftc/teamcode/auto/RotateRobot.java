package org.firstinspires.ftc.teamcode.auto;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

public class RotateRobot {

    boolean loopDone = false;
    SimplifyCode simplifyCode = new SimplifyCode();

    public void left(MyHardware myHardware,int angle){
        myHardware.getImu().resetYaw();
        simplifyCode.setPower(myHardware,-0.5,0.5);
        while (!loopDone) {
            YawPitchRollAngles orientation = myHardware.getImu().getRobotYawPitchRollAngles();
            if (orientation.getYaw(AngleUnit.DEGREES)>=angle){
                simplifyCode.setPower(myHardware,0,0);
                loopDone=true;
            }

            telemetry.addData("Yaw (Z)", "%.2f Deg. (Heading)", orientation.getYaw(AngleUnit.DEGREES));
            telemetry.update();
        }
        loopDone=false;
        simplifyCode.setPower(myHardware,0.1,-0.1);
        while (!loopDone){
            YawPitchRollAngles orientation = myHardware.getImu().getRobotYawPitchRollAngles();
            if (orientation.getYaw(AngleUnit.DEGREES)<=angle){
                simplifyCode.setPower(myHardware,0,0);
                loopDone=true;
            }

            telemetry.addData("Yaw (Z)", "%.2f Deg. (Heading)", orientation.getYaw(AngleUnit.DEGREES));
            telemetry.update();
        }
        loopDone=false;
    }

    public void right(MyHardware myHardware,int angle){
        myHardware.getImu().resetYaw();
        simplifyCode.setPower(myHardware,0.5,-0.5);
        while (!loopDone) {
            YawPitchRollAngles orientation = myHardware.getImu().getRobotYawPitchRollAngles();
            if (orientation.getYaw(AngleUnit.DEGREES)<=-angle){
                simplifyCode.setPower(myHardware,0,0);
                loopDone=true;
            }

            telemetry.addData("Yaw (Z)", "%.2f Deg. (Heading)", orientation.getYaw(AngleUnit.DEGREES));
            telemetry.update();
        }
        loopDone=false;
        simplifyCode.setPower(myHardware,-0.1,0.1);
        while (!loopDone){
            YawPitchRollAngles orientation = myHardware.getImu().getRobotYawPitchRollAngles();
            if (orientation.getYaw(AngleUnit.DEGREES)>=-angle){
                simplifyCode.setPower(myHardware,0,0);
                loopDone=true;
            }

            telemetry.addData("Yaw (Z)", "%.2f Deg. (Heading)", orientation.getYaw(AngleUnit.DEGREES));
            telemetry.update();
        }
        loopDone=false;
    }




}
