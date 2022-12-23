package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
@Autonomous(name = "StackConeInches", group = "Auto")
public class StackConeInches extends LinearOpMode {
    MoveRobot moveRobot = new MoveRobot();
    MyHardware myHardware = new MyHardware();
    RotateRobot rotateRobot = new RotateRobot();

    public void runOpMode() {
        myHardware.initialize(hardwareMap, telemetry);

        moveRobot.strafe(myHardware, 8,0.25,true,true);
        moveRobot.move(myHardware,31,0.25,false,true);
        rotateRobot.left(myHardware,90,telemetry);


    }
}
