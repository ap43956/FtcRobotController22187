package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class MecanumTele extends LinearOpMode {

    private DcMotor frontLeft;
    private DcMotor backLeft;
    private DcMotor frontRight;
    private DcMotor backRight;
    private DcMotor linear;
    private Servo claw;




    @Override
    public void runOpMode() throws InterruptedException {
        // Declare our motors
        // Make sure your ID's match your configuration
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        linear = hardwareMap.get(DcMotor.class, "linear");
        claw = hardwareMap.get(Servo.class, "claw");
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        linear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        linear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        linear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
       // Thread object = new Thread(new Thread2());
   // object.run();



        waitForStart();

        if (isStopRequested()) return;


        while (opModeIsActive()) {
            while(gamepad1.left_stick_button) {
//            if (gamepad1.x){
//                if (change==2){
//                    change=1.5;
//                }else if (change==1.5){
//                    change = 2;
//                }
//
//            }
                telemetry.addData("backleft", backLeft.getCurrentPosition());
                telemetry.addData("frontRight", frontRight.getCurrentPosition());
                telemetry.addData("frontLeft", frontLeft.getCurrentPosition());
                telemetry.addData("backRight", backRight.getCurrentPosition());
                telemetry.addData("linear", linear.getCurrentPosition());
                telemetry.update();
                if (gamepad2.right_trigger > 0) {
                    linear.setPower(gamepad2.right_trigger * 3  /* /1.2*/);
                } else if (gamepad2.left_trigger > 0) {
                    linear.setPower(-gamepad2.left_trigger * 3/*/1.2*/);
                } else {
                    linear.setPower(0);
                    telemetry.addData("noPower", linear.getPower());
                }
                if (gamepad2.a) {
                    claw.setPosition(0);
                    try {
                        wait(10);
                    } catch (Exception E) {
                        telemetry.addData("failed", 0);
                    }
                } else if (gamepad2.b) {
                    claw.setPosition(1);
                    try {
                        wait(10);
                    } catch (Exception E) {
                        telemetry.addData("failed", 0);
                    }
                }


                double y = -gamepad1.left_stick_y / 1.5; // Remember, this is reversed!
                double x = gamepad1.left_stick_x * 1.1 / 1.5; // Counteract imperfect strafing
                double rx = gamepad1.right_stick_x / 1.5;

                // Denominator is the largest motor power (absolute value) or 1
                // This ensures all the powers maintain the same ratio, but only when
                // at least one is out of the range [-1, 1]
                double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
                double frontLeftPower = (y + x + rx) / denominator;
                double backLeftPower = (y - x + rx) / denominator;
                double frontRightPower = (y - x - rx) / denominator;
                double backRightPower = (y + x - rx) / denominator;

                frontLeft.setPower(frontLeftPower);
                backLeft.setPower(backLeftPower);
                frontRight.setPower(frontRightPower);
                backRight.setPower(backRightPower);

            }
            telemetry.addData("backleft", backLeft.getCurrentPosition());
            telemetry.addData("frontRight", frontRight.getCurrentPosition());
            telemetry.addData("frontLeft", frontLeft.getCurrentPosition());
            telemetry.addData("backRight", backRight.getCurrentPosition());
            telemetry.addData("linear", linear.getCurrentPosition());
            telemetry.update();
            if (gamepad2.right_trigger > 0) {
                linear.setPower(gamepad2.right_trigger * 3/* /1.2*/);
            } else if (gamepad2.left_trigger > 0) {
                linear.setPower(-gamepad2.left_trigger * 3/*/1.2*/);
            } else {
                linear.setPower(0);
                telemetry.addData("noPower", linear.getPower());
            }
            if (gamepad2.a) {
                claw.setPosition(0);
                try {
                    wait(10);
                } catch (Exception E) {
                    telemetry.addData("failed", 0);
                }
            } else if (gamepad2.b) {
                claw.setPosition(1);
                try {
                    wait(10);
                } catch (Exception E) {
                    telemetry.addData("failed", 0);
                }
            }


            double y = -gamepad1.left_stick_y / 3.0; // Remember, this is reversed!
            double x = gamepad1.left_stick_x * 1.1 / 3.0; // Counteract imperfect strafing
            double rx = gamepad1.right_stick_x / 3.0;

            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio, but only when
            // at least one is out of the range [-1, 1]
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;

            frontLeft.setPower(frontLeftPower);
            backLeft.setPower(backLeftPower);
            frontRight.setPower(frontRightPower);
            backRight.setPower(backRightPower);

        }
        }
    }

