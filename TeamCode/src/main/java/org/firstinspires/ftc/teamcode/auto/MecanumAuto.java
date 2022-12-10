package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Autonomous(name = "MecanumAuto", group = "Auto")
public class MecanumAuto extends LinearOpMode {
    MyHardware myHardware = new MyHardware();
    RotateRobot rotateRobot = new RotateRobot();
    MoveRobot moveRobot = new MoveRobot();
    Camera camera = new Camera();

    public void linearMove(int rotations, double speed, boolean down) {
        myHardware.getLinear().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        telemetry.addData("GoalTicks", rotations);
        myHardware.getLinear().setTargetPosition(rotations);
        myHardware.getLinear().setMode(DcMotor.RunMode.RUN_TO_POSITION);
        if (down){
            myHardware.getLinear().setPower(-speed);
        } else {
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
        myHardware.getClaw().setPosition(0);
        sleep(2000);
        linearMove(4000, 0.5, false);
        moveRobot.strafe(myHardware,1.1,0.5, true);
        myHardware.getClaw().setPosition(1);


    }
}
