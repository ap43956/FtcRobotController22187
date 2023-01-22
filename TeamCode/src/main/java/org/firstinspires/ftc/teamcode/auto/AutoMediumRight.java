package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "AutoMediumRight", group = "Auto")
public class AutoMediumRight extends LinearOpMode {
    MyHardware myHardware = new MyHardware();
    RotateRobot rotateRobot = new RotateRobot();
    MoveRobot moveRobot = new MoveRobot();
    Camera camera = new Camera();

    public void linear2Base(){
                    myHardware.getLinear().setTargetPosition(0);
            myHardware.getLinear().setMode(DcMotor.RunMode.RUN_TO_POSITION);
            myHardware.getLinear().setPower(-0.7);
            while(myHardware.getLinear().isBusy()){

            }
            myHardware.getLinear().setPower(0);
            myHardware.getLinear().setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    public void linearMove(int rotations, double speed, boolean down) {
            myHardware.getLinear().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            telemetry.addData("GoalTicks", rotations);
            if (down) {
                myHardware.getLinear().setTargetPosition(rotations);
                myHardware.getLinear().setMode(DcMotor.RunMode.RUN_TO_POSITION);
                myHardware.getLinear().setPower(speed);
            } else {
                myHardware.getLinear().setTargetPosition(-rotations);
                myHardware.getLinear().setMode(DcMotor.RunMode.RUN_TO_POSITION);
                myHardware.getLinear().setPower(-speed);
            }
            while (myHardware.getLinear().isBusy()) {

            }
            myHardware.getLinear().setPower(0);
            myHardware.getLinear().setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }


    @Override
    public void runOpMode() {
        myHardware.initialize(hardwareMap, telemetry);
        waitForStart();
        myHardware.closeClaw();
        moveRobot.strafe(myHardware, 4, 0.4, true, true);
        moveRobot.move(myHardware, 25.5, 0.4, false, true);
        moveRobot.strafe(myHardware, 15.5, 0.4, true, true);
        linearMove(2900, 1, false);
        moveRobot.move(myHardware, 1, 0.2, false, true);
        sleep(1000);
        myHardware.openClaw();
        moveRobot.move(myHardware, 1, 0.2, true, true);
//        linearMove(0, 0, false);
        moveRobot.strafe(myHardware, 15.5, 0.4, false, true);


    }
}
