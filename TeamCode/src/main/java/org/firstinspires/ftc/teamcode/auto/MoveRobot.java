package org.firstinspires.ftc.teamcode.auto;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class MoveRobot {

    SimplifyCode simplifyCode = new SimplifyCode();
    public void strafe(MyHardware myHardware,double rotations, double speed,boolean left, boolean inches){

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
            myHardware.getFrontRight().setPower(speed);
            myHardware.getBackLeft().setPower(speed);
            myHardware.getFrontLeft().setPower(-speed);
            myHardware.getBackRight().setPower(-speed);
        } else {
            myHardware.getFrontRight().setPower(-speed);
            myHardware.getBackLeft().setPower(-speed);
            myHardware.getFrontLeft().setPower(speed);
            myHardware.getBackRight().setPower(speed);
        }
        while(myHardware.getBackLeft().isBusy() && myHardware.getFrontLeft().isBusy()&&myHardware.getFrontRight().isBusy()&&myHardware.getBackRight().isBusy()){
        myHardware.telemetry.update();
        }
        simplifyCode.setPower(myHardware,0,0);
        simplifyCode.MotorMode(myHardware, "run");
    }

    public void move(MyHardware myHardware,double rotations, double speed, boolean back,boolean inches){

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
        while(myHardware.getBackLeft().isBusy() && myHardware.getFrontLeft().isBusy()&&myHardware.getFrontRight().isBusy()&&myHardware.getBackRight().isBusy()){

        }
        simplifyCode.setPower(myHardware,0,0);
        simplifyCode.MotorMode(myHardware, "run");
    }
}
