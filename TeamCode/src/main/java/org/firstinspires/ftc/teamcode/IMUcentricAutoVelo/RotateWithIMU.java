package org.firstinspires.ftc.teamcode.IMUcentricAutoVelo;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
@Disabled
public class RotateWithIMU {

    boolean loopDone = false;
    SimplifyCodeIMU simplifyCode = new SimplifyCodeIMU();

    public void GoToAngle(MyHardware myHardware, Telemetry vTelemetry) {
        loopDone = false;
        YawPitchRollAngles orientation = myHardware.getImu().getRobotYawPitchRollAngles();
        if (orientation.getYaw(AngleUnit.DEGREES)<=myHardware.imuAngle) {
            simplifyCode.setPower(myHardware, -0.5, 0.5);
            while (!loopDone) {
                orientation = myHardware.getImu().getRobotYawPitchRollAngles();
                if (orientation.getYaw(AngleUnit.DEGREES) >= myHardware.imuAngle) {
                    simplifyCode.setPower(myHardware, 0, 0);
                    loopDone = true;
                }
            }
            orientation = myHardware.getImu().getRobotYawPitchRollAngles();

            loopDone = false;
            simplifyCode.setPower(myHardware, 0.1, -0.1);
            while (!loopDone) {
                orientation = myHardware.getImu().getRobotYawPitchRollAngles();
                vTelemetry.addData("angle", orientation.getYaw(AngleUnit.DEGREES));
                if (orientation.getYaw(AngleUnit.DEGREES) <= myHardware.imuAngle) {
                    simplifyCode.setPower(myHardware, 0, 0);
                    loopDone = true;
                }

            }
        }
        else if (orientation.getYaw(AngleUnit.DEGREES)>=myHardware.imuAngle) {
            simplifyCode.setPower(myHardware,0.5,-0.5);
            while (!loopDone) {
                orientation = myHardware.getImu().getRobotYawPitchRollAngles();
                vTelemetry.addData("angle", orientation.getYaw(AngleUnit.DEGREES));
                if (orientation.getYaw(AngleUnit.DEGREES) <= myHardware.imuAngle) {
                    simplifyCode.setPower(myHardware, 0, 0);
                    loopDone = true;
                }


            }
            orientation = myHardware.getImu().getRobotYawPitchRollAngles();

            loopDone = false;
            simplifyCode.setPower(myHardware, -0.1, 0.1);

            while (!loopDone) {
                orientation = myHardware.getImu().getRobotYawPitchRollAngles();
                if (orientation.getYaw(AngleUnit.DEGREES) >= myHardware.imuAngle) {
                    simplifyCode.setPower(myHardware, 0, 0);
                    loopDone = true;
                }

            }
        }
    }


    public void left(MyHardware myHardware, double angle, Telemetry vTelemetry) {
        myHardware.getImu().resetYaw();
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


    public void right(MyHardware myHardware, double angle, Telemetry vTelemetry){
        myHardware.getImu().resetYaw();
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






