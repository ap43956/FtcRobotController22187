package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.hardware.DcMotor;

public class SimplifyCode {

    public void setPower(MyHardware myHardware, double speedl, double speedr){
        myHardware.getFrontLeft().setPower(speedl);
        myHardware.getBackLeft().setPower(speedl);
        myHardware.getFrontRight().setPower(speedr);
        myHardware.getBackRight().setPower(speedr);
    }

    public void MotorMode(MyHardware myHardware,String mode){
        if (mode=="reset") {
            myHardware.getBackLeft().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            myHardware.getFrontLeft().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            myHardware.getBackRight().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            myHardware.getFrontRight().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        } else if (mode == "run"){
            myHardware.getFrontLeft().setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            myHardware.getFrontRight().setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            myHardware.getBackLeft().setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            myHardware.getBackRight().setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        } else if (mode == "pos"){
            myHardware.getBackLeft().setMode(DcMotor.RunMode.RUN_TO_POSITION);
            myHardware.getFrontLeft().setMode(DcMotor.RunMode.RUN_TO_POSITION);
            myHardware.getFrontRight().setMode(DcMotor.RunMode.RUN_TO_POSITION);
            myHardware.getBackRight().setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
    }
}
