package org.firstinspires.ftc.teamcode.IMUcentricAutoVelo;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
@Disabled
public class MoveWithIMU {
    double correction;


    SimplifyCodeIMU simplifyCode = new SimplifyCodeIMU();
    public void strafe(MyHardware myHardware, double rotations, double speed, boolean left, boolean inches,boolean align){
        if(align) {
            simplifyCode.align(myHardware);
        }
        speed=speed*2360;
        simplifyCode.MotorMode(myHardware,"reset");
        double data = 538*rotations;
        if (inches) {
            data = 46.2*rotations;//exact number is 45.36
        }
        int value = (int)data;
        if(left){
            myHardware.getFrontRight().setTargetPosition(value);
            myHardware.getBackLeft().setTargetPosition(value);
            myHardware.getFrontLeft().setTargetPosition(-value);
            myHardware.getBackRight().setTargetPosition(-value);
        } else {
            myHardware.getFrontRight().setTargetPosition(-value);
            myHardware.getBackLeft().setTargetPosition(-value);
            myHardware.getFrontLeft().setTargetPosition(value);
            myHardware.getBackRight().setTargetPosition(value);
        }
        simplifyCode.MotorMode(myHardware,"pos");
        if(left){
            myHardware.getFrontRight().setVelocity(speed);
            myHardware.getBackLeft().setVelocity(speed);
            myHardware.getFrontLeft().setVelocity(-speed);
            myHardware.getBackRight().setVelocity(-speed);
        } else {
            myHardware.getFrontRight().setVelocity(-speed);
            myHardware.getBackLeft().setVelocity(-speed);
            myHardware.getFrontLeft().setVelocity(speed);
            myHardware.getBackRight().setVelocity(speed);
        }
        while(myHardware.getBackLeft().isBusy() && myHardware.getFrontLeft().isBusy()&&myHardware.getFrontRight().isBusy()&&myHardware.getBackRight().isBusy()){
            //determining if corrections should be made
            if (myHardware.getImu().getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES)+myHardware.imuAngle == 0) {
                correction = 0;
            }
            else {
                correction = -myHardware.getImu().getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES)+myHardware.imuAngle;
            }
            //adding robot specific offset
            correction = correction * 0.1;
            //applying corrections to motors
            if (left){
                myHardware.getFrontRight().setVelocity(speed+correction);
                myHardware.getBackLeft().setVelocity(speed-correction);
                myHardware.getFrontLeft().setVelocity(-speed-correction);
                myHardware.getBackRight().setVelocity(-speed+correction);
            } else {
                myHardware.getFrontRight().setVelocity(speed-correction);
                myHardware.getBackLeft().setVelocity(speed+correction);
                myHardware.getFrontLeft().setVelocity(-speed+correction);
                myHardware.getBackRight().setVelocity(-speed-correction);
            }
        }

        simplifyCode.setPower(myHardware,0,0);
        simplifyCode.MotorMode(myHardware, "run");
    }

    public void move(MyHardware myHardware, double rotations, double speed, boolean back, boolean inches, boolean align){
        if (align) {
            simplifyCode.align(myHardware);
        }

        simplifyCode.MotorMode(myHardware,"reset");
        double data = 538*rotations;
        if (inches) {
            data = 46.2*rotations;//exact number is 45.36
        }
        int value = (int)data;
        speed=speed*2360;

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
        while(myHardware.getBackLeft().isBusy() && myHardware.getFrontLeft().isBusy()&&myHardware.getFrontRight().isBusy()&&myHardware.getBackRight().isBusy()){
            //determining if corrections should be made
            if (myHardware.getImu().getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES)+myHardware.imuAngle == 0) {
                correction = 0;
            }
            else {
                correction = -myHardware.getImu().getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES)+myHardware.imuAngle;
            }
            //adding robot specific offset
            correction = correction * 0.1;
            //applying corrections to motors
            if (back){
                simplifyCode.setPower(myHardware, speed+correction, speed-correction);
            } else {
                simplifyCode.setPower(myHardware, speed - correction, speed + correction);
            }
//            if(data-myHardware.getBackLeft().getCurrentPosition()<=data/4){
//                if(back){
//                    simplifyCode.setPower(myHardware, -speed/2,-speed/2);
//                } else {
//                    simplifyCode.setPower(myHardware, speed/2,speed/2);
//                }
//            }
        }
        simplifyCode.setPower(myHardware,0,0);
        simplifyCode.MotorMode(myHardware, "run");
    }
}
