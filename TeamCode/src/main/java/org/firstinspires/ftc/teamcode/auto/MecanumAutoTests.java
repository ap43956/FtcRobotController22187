package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

@Autonomous(name = "MecanumAutoTests", group = "Auto")
public class MecanumAutoTests extends LinearOpMode {
    MyHardware myHardware = new MyHardware();
    RotateRobot rotateRobot = new RotateRobot();
    MoveRobot moveRobot = new MoveRobot();
    Camera camera = new Camera();

    public void linearMove(int ticks, double speed, boolean down) {
        myHardware.getLinear().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        telemetry.addData("GoalTicks", ticks);
        if (down){
            myHardware.getLinear().setTargetPosition(-ticks);
            myHardware.getLinear().setMode(DcMotor.RunMode.RUN_TO_POSITION);
            myHardware.getLinear().setPower(-speed);
        } else {
            myHardware.getLinear().setTargetPosition(ticks);
            myHardware.getLinear().setMode(DcMotor.RunMode.RUN_TO_POSITION);
            myHardware.getLinear().setPower(speed);
        }
        while(myHardware.getLinear().isBusy()){

        }
        myHardware.getLinear().setPower(0);
        myHardware.getLinear().setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }


    @Override
    public void runOpMode() {
        myHardware.initialize(hardwareMap, telemetry);
        waitForStart();
        int i= 0;
        while(opModeIsActive()) {
            i++;
            YawPitchRollAngles orientation = myHardware.getImu().getRobotYawPitchRollAngles();
            telemetry.addData("angle", orientation.getYaw(AngleUnit.DEGREES));
            telemetry.addData("I", i);
            telemetry.update();
//            if (i>5000){
                myHardware.getImu().resetYaw();
//                i = 0;
//            }

        }



    }
}
