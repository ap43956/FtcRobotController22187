package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.auto.*;
//@Autonomous(name = "move10", group = "Auto")
public class Move10inches extends LinearOpMode {
    public void runOpMode() {

        MoveRobot moveRobot = new MoveRobot();
        MyHardware myHardware = new MyHardware();
        myHardware.initialize(hardwareMap, telemetry);
        moveRobot.move(myHardware,10,0.25,false,true);

    }
}
