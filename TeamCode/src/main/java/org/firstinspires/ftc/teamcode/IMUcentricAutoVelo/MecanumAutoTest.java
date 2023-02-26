package org.firstinspires.ftc.teamcode.IMUcentricAutoVelo;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
@Disabled
@Autonomous(name = "MecanumAutoIMUcentricTest", group = "Auto")
public class MecanumAutoTest extends LinearOpMode {
    MyHardware H = new MyHardware();
    RotateWithIMU R = new RotateWithIMU();
    MoveWithIMU M = new MoveWithIMU();
    AutoMergedFunctions F = new AutoMergedFunctions();
    SimplifyCodeIMU S = new SimplifyCodeIMU();
    public void linear(int rotations, double speed, boolean down) {
        H.getLinear().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        telemetry.addData("GoalTicks", rotations);
        H.getLinear().setTargetPosition(rotations);
        H.getLinear().setMode(DcMotor.RunMode.RUN_TO_POSITION);
        if (down){
            H.getLinear().setPower(-speed);
        } else {
            H.getLinear().setPower(speed);
        }
        while(H.getLinear().isBusy()){

        }
        H.getLinear().setPower(0);
        H.getLinear().setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }


    @Override
    public void runOpMode() {
        H.initialize(hardwareMap, telemetry);
        waitForStart();
        H.getBackLeft().setVelocity(236);
        H.getFrontLeft().setPower(0.1);
        while (opModeIsActive()){
            telemetry.addData("backVelocity", H.getBackLeft().getVelocity());
            telemetry.addData("backSpeed", H.getBackLeft().getPower());
            telemetry.addData("frontSpeed", H.getFrontLeft().getVelocity());
            telemetry.addData("frontSpeed", H.getFrontLeft().getPower());
            telemetry.update();
        }

    }
}
