package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

@TeleOp
public class WorldCentricTele extends LinearOpMode {
    BNO055IMU IMU2;
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


        // Reverse the right side motors
        // Reverse left motors if you are using NeveRests
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);


        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        linear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        linear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Retrieve the IMU from the hardware map
        BNO055IMU IMU2 = hardwareMap.get(BNO055IMU.class, "IMU2");
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        // Technically this is the default, however specifying it is clearer
        parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS;
        // Without this, data retrieving from the IMU throws an exception
        IMU2.initialize(parameters);


        waitForStart();

        if (isStopRequested()) return;
        linear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        while (opModeIsActive()) {
          while (gamepad1.right_trigger / gamepad1.right_trigger == 1) {
              if (gamepad2.right_trigger > 0) {
                  linear.setPower(gamepad2.right_trigger / 1.2);
              } else if (gamepad2.left_trigger > 0) {
                  linear.setPower(-gamepad2.left_trigger / 1.2);
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

              double y = -gamepad1.left_stick_y; // Remember, this is reversed!
              double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
              double rx = gamepad1.right_stick_x;

              // Read inverse IMU heading, as the IMU heading is CW positive
              double botHeading = -IMU2.getAngularOrientation().firstAngle;
              telemetry.addData("1st", IMU2.getAngularOrientation().firstAngle);
              telemetry.addData("2nd", IMU2.getAngularOrientation().secondAngle);
              telemetry.addData("3rd", IMU2.getAngularOrientation().thirdAngle);

              double rotX = x * Math.cos(botHeading) - y * Math.sin(botHeading);
              double rotY = x * Math.sin(botHeading) + y * Math.cos(botHeading);

              // Denominator is the largest motor power (absolute value) or 1
              // This ensures all the powers maintain the same ratio, but only when
              // at least one is out of the range [-1, 1]
              double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
              double frontLeftPower = (rotY + rotX + rx) / denominator;
              double backLeftPower = (rotY - rotX + rx) / denominator;
              double frontRightPower = (rotY - rotX - rx) / denominator;
              double backRightPower = (rotY + rotX - rx) / denominator;

              frontLeft.setPower(frontLeftPower);
              backLeft.setPower(backLeftPower);
              frontRight.setPower(frontRightPower);
              backRight.setPower(backRightPower);
          }
            if (gamepad2.right_trigger>0){
                linear.setPower(gamepad2.right_trigger/1.2);
            } else if (gamepad2.left_trigger>0){
                linear.setPower(-gamepad2.left_trigger/1.2);
            } else {
                linear.setPower(0);
                telemetry.addData("noPower", linear.getPower());
            }
            if (gamepad2.a) {
                claw.setPosition(0);
                try {
                    wait(10);
                } catch(Exception E){
                    telemetry.addData("failed", 0);
                }
            } else if (gamepad2.b){
                claw.setPosition(1);
                try {
                    wait(10);
                } catch(Exception E){
                    telemetry.addData("failed", 0);
                }
            }

            double y = -gamepad1.left_stick_y; // Remember, this is reversed!
            double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
            double rx = gamepad1.right_stick_x;

            // Read inverse IMU heading, as the IMU heading is CW positive
            double botHeading = -IMU2.getAngularOrientation().firstAngle - 90;
            telemetry.addData("1st", IMU2.getAngularOrientation().firstAngle);
            telemetry.addData("2nd", IMU2.getAngularOrientation().secondAngle);
            telemetry.addData("3rd", IMU2.getAngularOrientation().thirdAngle);

            double rotX = x * Math.cos(botHeading) - y * Math.sin(botHeading);
            double rotY = x * Math.sin(botHeading) + y * Math.cos(botHeading);

            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio, but only when
            // at least one is out of the range [-1, 1]
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (rotY + rotX + rx) / denominator/2;
            double backLeftPower = (rotY - rotX + rx) / denominator /2;
            double frontRightPower = (rotY - rotX - rx) / denominator/2;
            double backRightPower = (rotY + rotX - rx) / denominator /2;

            frontLeft.setPower(frontLeftPower);
            backLeft.setPower(backLeftPower);
            frontRight.setPower(frontRightPower);
            backRight.setPower(backRightPower);
        }
    }
}