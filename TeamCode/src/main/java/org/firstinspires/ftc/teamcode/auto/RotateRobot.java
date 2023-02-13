package org.firstinspires.ftc.teamcode.auto;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

public class RotateRobot {

    boolean loopDone = false;
    SimplifyCode simplifyCode = new SimplifyCode();

    public void left(MyHardware myHardware,double angle, Telemetry vTelemetry) {
       // myHardware.getImu().resetYaw();
        simplifyCode.setPower(myHardware, -0.5, 0.5);
        while (!loopDone) {
            YawPitchRollAngles orientation = myHardware.getImu().getRobotYawPitchRollAngles();
            vTelemetry.addData("angle", orientation.getYaw(AngleUnit.DEGREES));
            if (orientation.getYaw(AngleUnit.DEGREES) >= angle) {
                simplifyCode.setPower(myHardware, 0, 0);
                loopDone = true;
            }

        }
        YawPitchRollAngles orientation = myHardware.getImu().getRobotYawPitchRollAngles();

            loopDone = false;
            simplifyCode.setPower(myHardware, 0.1, -0.1);
            while (!loopDone) {
                orientation = myHardware.getImu().getRobotYawPitchRollAngles();
                vTelemetry.addData("angle", orientation.getYaw(AngleUnit.DEGREES));
                if (orientation.getYaw(AngleUnit.DEGREES) <= angle) {
                    simplifyCode.setPower(myHardware, 0, 0);
                    loopDone = true;
                }

            }
            loopDone = false;
        }


    public void right(MyHardware myHardware,double angle,Telemetry vTelemetry){
      //  myHardware.getImu().resetYaw();
        loopDone = false;
        simplifyCode.setPower(myHardware,0.5,-0.5);
        while (!loopDone) {
            YawPitchRollAngles orientation = myHardware.getImu().getRobotYawPitchRollAngles();
            vTelemetry.addData("angle", orientation.getYaw(AngleUnit.DEGREES));
            if (orientation.getYaw(AngleUnit.DEGREES) <= -angle) {
                simplifyCode.setPower(myHardware, 0, 0);
                loopDone = true;
            }


        }
        YawPitchRollAngles orientation = myHardware.getImu().getRobotYawPitchRollAngles();

            loopDone = false;
            simplifyCode.setPower(myHardware, -0.1, 0.1);

            while (!loopDone) {
                orientation = myHardware.getImu().getRobotYawPitchRollAngles();
                if (orientation.getYaw(AngleUnit.DEGREES) >= -angle) {
                    simplifyCode.setPower(myHardware, 0, 0);
                    loopDone = true;
                }

            }
            loopDone = false;
        }
    }






