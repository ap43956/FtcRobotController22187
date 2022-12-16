package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "MecanumAutoWithStackedCone", group = "Auto")
public class MecanumAutoWithStackedCone extends LinearOpMode {
    MyHardware myHardware = new MyHardware();
    RotateRobot rotateRobot = new RotateRobot();
    MoveRobot moveRobot = new MoveRobot();
    Camera camera = new Camera();

    public void linearMove(int ticks, double speed, boolean down) {
        myHardware.getLinear().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
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
        // grab preloaded cone
        myHardware.getClaw().setPosition(0);
        sleep(1000);
        //move forward to tall junction
        moveRobot.move(myHardware, 4.2, 0.6, false);
        linearMove(3650, 1, false);
        //strafe left at junction
        moveRobot.strafe(myHardware, 1, 0.5, false);
        sleep(1000);
        //drop cone on junction
        myHardware.getClaw().setPosition(1);
        //going back to postion to grab cones
        moveRobot.strafe(myHardware, 1.25, 0.5, true);
        linearMove(3750, 1, true);
        //turn left to grab cones
        rotateRobot.left(myHardware, 90,telemetry);


//    strafe to align to pick up stack
        moveRobot.strafe(myHardware, 0.1, 0.4, false);
        linearMove(830, 0.6, false);
        moveRobot.move(myHardware, 1.615, 0.3, false);
        sleep(2000);
        myHardware.getClaw().setPosition(0);
        sleep(2000);
        linearMove(200, 0.6, false);
        moveRobot.move(myHardware, 0.3, 0.4, true);
        linearMove(500, 0.6, false);

        //go back with the grabbed cone
        moveRobot.move(myHardware, 1.3, 0.6,  true);
        sleep(1000);
        moveRobot.strafe(myHardware, 0.1, 0.4, true);
        sleep(2000);
        rotateRobot.right(myHardware,90, telemetry);
        sleep(2000);
        linearMove(2330, 1, false);
        //strafe left at junction
        moveRobot.strafe(myHardware, 1.1, 0.5, false);
        sleep(1000);
        //drop cone on junction
        myHardware.getClaw().setPosition(1);



    }
}
