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
       // sleep(100);
        //move forward to tall junction
        moveRobot.move(myHardware, 4.2, 0.6, false, false);
        linearMove( 3650, 1, false);
        //strafe left at junction
        moveRobot.strafe(myHardware, 1, 0.5, false, false);
       // sleep(100);
        //drop cone on junction
        linearMove(500,1,true);
        sleep(100);
        myHardware.getClaw().setPosition(1);
        linearMove(500,1,false);
        //going back to postion to grab cones
        moveRobot.strafe(myHardware, 1.25, 0.5, true, false);
        linearMove(3750, 1, true);
        //turn left to grab cones
        rotateRobot.left(myHardware, 90,telemetry);


//    strafe to align to pick up stack
        moveRobot.strafe(myHardware, 0.1, 0.4, false, false);
        linearMove(770, 0.6, false); // changed from 830->800->770
        moveRobot.move(myHardware, 1.550 , 0.3, false, false);
        sleep(200);
        myHardware.getClaw().setPosition(0);
        sleep(400);
        linearMove(200, 0.6, false);
        moveRobot.move(myHardware, 0.3, 0.4, true, false);
        linearMove(500, 0.6, false);

        //go back with the grabbed cone
        moveRobot.move(myHardware, 1.125, 0.6,  true, false);
        sleep(100);
        moveRobot.strafe(myHardware, 0.1, 0.4, true, false);
        sleep(200);
        rotateRobot.right(myHardware,90, telemetry);
        sleep(200);
        linearMove(2330, 1, false);
        //strafe left at junction
        moveRobot.strafe(myHardware, 1.3, 0.5, false, false); //Changed 1.1->1.3
        sleep(100);
        //drop cone on junction
        myHardware.getClaw().setPosition(1);



    }
}

