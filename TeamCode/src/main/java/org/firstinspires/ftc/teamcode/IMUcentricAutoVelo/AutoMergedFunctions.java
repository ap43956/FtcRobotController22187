package org.firstinspires.ftc.teamcode.IMUcentricAutoVelo;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
@Disabled
public class AutoMergedFunctions {
    double correction;
    SimplifyCodeIMU simplifyCode = new SimplifyCodeIMU();

    public void LinearAndMove(MyHardware myHardware, int LinearRotations, double LinearSpeed, boolean LinearDown, double rotations, double speed, boolean back, boolean inches, boolean align) {
        if (align) {
            simplifyCode.align(myHardware);
        }
        speed=speed*2360;
        simplifyCode.MotorMode(myHardware,"reset");
        double data = 538*rotations;
        if (inches) {
            data = 46.2*rotations;//exact number is 45.36
        }
        int value = (int)data;

        if (back) {
            myHardware.getFrontLeft().setTargetPosition(-value);
            myHardware.getBackLeft().setTargetPosition(-value);
            myHardware.getFrontRight().setTargetPosition(-value);
            myHardware.getBackRight().setTargetPosition(-value);
            simplifyCode.MotorMode(myHardware,"pos");
            simplifyCode.setPower(myHardware,-speed,-speed);
        } else {
            myHardware.getFrontLeft().setTargetPosition(value);
            myHardware.getBackLeft().setTargetPosition(value);
            myHardware.getFrontRight().setTargetPosition(value);
            myHardware.getBackRight().setTargetPosition(value);
            simplifyCode.MotorMode(myHardware,"pos");
            simplifyCode.setPower(myHardware,speed,speed);
        }
        myHardware.getLinear().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        myHardware.getLinear().setTargetPosition(LinearRotations);
        myHardware.getLinear().setMode(DcMotor.RunMode.RUN_TO_POSITION);
        if (LinearDown){
            myHardware.getLinear().setPower(-LinearSpeed);
        } else {
            myHardware.getLinear().setPower(LinearSpeed);
        }
        while(myHardware.getLinear().isBusy() && myHardware.getBackLeft().isBusy() && myHardware.getFrontLeft().isBusy()&&myHardware.getFrontRight().isBusy()&&myHardware.getBackRight().isBusy()){
            if (myHardware.getImu().getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) == 0) {
                correction = 0;
            }
            else {
                correction = -myHardware.getImu().getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
            }
            correction = correction * 0.1;
            if (back){
                simplifyCode.setPower(myHardware, speed+correction, speed-correction);
            } else {
                simplifyCode.setPower(myHardware, speed - correction, speed + correction);
            }
        }
        simplifyCode.setPower(myHardware,0,0);
        simplifyCode.MotorMode(myHardware, "run");
        myHardware.getLinear().setPower(0);
        myHardware.getLinear().setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    public void LinearAndTurn(MyHardware myHardware, Telemetry vTelemetry,int LinearRotations, double LinearSpeed, boolean LinearDown){
        Boolean loopDone = false;
        YawPitchRollAngles orientation = myHardware.getImu().getRobotYawPitchRollAngles();
        myHardware.getLinear().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        myHardware.getLinear().setTargetPosition(LinearRotations);
        myHardware.getLinear().setMode(DcMotor.RunMode.RUN_TO_POSITION);
        if (LinearDown){
            myHardware.getLinear().setPower(-LinearSpeed);
        } else {
            myHardware.getLinear().setPower(LinearSpeed);
        }
        if (orientation.getYaw(AngleUnit.DEGREES)<=myHardware.imuAngle) {
            simplifyCode.setPower(myHardware, -0.5*2360, 0.5*2360);
            while (!loopDone) {
                orientation = myHardware.getImu().getRobotYawPitchRollAngles();
                if (orientation.getYaw(AngleUnit.DEGREES) >= myHardware.imuAngle) {
                    simplifyCode.setPower(myHardware, 0, 0);
                    loopDone = true;
                }
            }
            orientation = myHardware.getImu().getRobotYawPitchRollAngles();

            loopDone = false;
            simplifyCode.setPower(myHardware, 0.1*2360, -0.1*2360);
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
            simplifyCode.setPower(myHardware,0.5*2360,-0.5*2360);
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
            simplifyCode.setPower(myHardware, -0.1*2360, 0.1*2360);

            while (!loopDone) {
                orientation = myHardware.getImu().getRobotYawPitchRollAngles();
                if (orientation.getYaw(AngleUnit.DEGREES) >= myHardware.imuAngle) {
                    simplifyCode.setPower(myHardware, 0, 0);
                    loopDone = true;
                }

            }
        }
        while(myHardware.getLinear().isBusy()){

        }
        myHardware.getLinear().setPower(0);
        myHardware.getLinear().setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

}
