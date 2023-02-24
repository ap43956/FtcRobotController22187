package org.firstinspires.ftc.teamcode.TestPrograms;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.teamcode.auto.MoveRobot;
import org.firstinspires.ftc.teamcode.auto.MyHardware;
@Autonomous(name = "Strafe Test",group = "test")
public class StrafeTest extends LinearOpMode {

    IMU imu;
    private DcMotor frontLeft;
    private DcMotor backLeft;
    private DcMotor frontRight;
    private DcMotor backRight;
    private DcMotor linear;
    private Servo claw;
    boolean loopDone = false;
    MyHardware myHardware = new MyHardware();
    private static final String TFOD_MODEL_FILE = "/sdcard/FIRST/tflitemodels/PogCone.tflite";


    private static final String[] LABELS = {
            "1",
            "2",
            "3"
    };
    private static final String VUFORIA_KEY =
            "ASlEG4X/////AAABmY1ivleqDEAOlO+c8WLrjuQ0XPykAjsJdqSDj8SE3Rdi0UitfIUC9nDsxtCi1QiucOpnxiguhOHRIjMXSE3fNiAq8nOVZKz5aEh6xd45UyvSqt4DifeXZqgpAN7n7yzaphd9eC0vmhlLUUt6RX4XtU9IUuumCXEgwqAOfKvJtx9h8LlgNhLgrkifTRm0xAcqwD3N2u7GRmqtpY4WF2D9Y6V3kWLWOLC67x2QGQZuKVdWNq4G+0Ie/JxWqE9RbOI8GOlocLEHlPV1PJNt7Mx7I4lrhf95UJ3A1uNpYeJDjB3kGYkoppWKSgDc6AZCh5KYoXhQRiVARuchnHssrsj3UK6b5cSFTBjN+VhZhj/+ixBU";
    private VuforiaLocalizer vuforia;

    private TFObjectDetector tfod;

    private void initVuforia() {
        /*111
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = hardwareMap.get(WebcamName.class, "webcam");

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);
    }

    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minResultConfidence = 0.600f;
        tfodParameters.isModelTensorFlow2 = true;
        tfodParameters.inputSize = 300;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        claw = hardwareMap.get(Servo.class, "claw");

        // Use loadModelFromAsset() if the TF Model is built in as an asset by Android Studio
        // Use loadModelFromFile() if you have downloaded a custom team model to the Robot Controller's FLASH.
        //tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABELS);


        tfod.loadModelFromFile(TFOD_MODEL_FILE, LABELS);
    }

    public void MotorMode(String mode) {
        if (mode == "reset") {
            backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        } else if (mode == "run") {
            frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        } else if (mode == "pos") {
            backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
    }

    public void setPower(double speedl, double speedr) {
        frontLeft.setPower(speedl);
        backLeft.setPower(speedl);
        frontRight.setPower(speedr);
        backRight.setPower(speedr);
    }

    public void strafe(double rotations, double speed, boolean left, boolean blocks) {
        MotorMode("reset");
        double data = 538 * rotations;
        if (blocks) {
            data = 1050*rotations;
        }
        int value = (int) data;
        telemetry.addData("GoalTicks", value);
        if (left) {
            frontRight.setTargetPosition(value);
            backLeft.setTargetPosition(value);
            frontLeft.setTargetPosition(-value);
            backRight.setTargetPosition(-value);
        } else {
            frontRight.setTargetPosition(-value);
            backLeft.setTargetPosition(-value);
            frontLeft.setTargetPosition(value);
            backRight.setTargetPosition(value);
        }
        MotorMode("pos");
        if (left) {
            frontRight.setPower(speed);
            backLeft.setPower(speed);
            frontLeft.setPower(-speed);
            backRight.setPower(-speed);
        } else {
            frontRight.setPower(-speed);
            backLeft.setPower(-speed);
            frontLeft.setPower(speed);
            backRight.setPower(speed);
        }
        while (backLeft.isBusy() && frontLeft.isBusy() && frontRight.isBusy() && backRight.isBusy()) {

        }
        setPower(0, 0);
        MotorMode("run");
    }

    public void move(double rotations, double speed, boolean back, boolean blocks) {
        MotorMode("reset");
        double data = 538 * rotations;
        double inches = 43.75*rotations;
        if (blocks) {
            data = 1050*rotations;
        }
        int value = (int) data;
        telemetry.addData("GoalTicks", value);
        backLeft.setTargetPosition(value);
        frontLeft.setTargetPosition(value);
        frontRight.setTargetPosition(value);
        backRight.setTargetPosition(value);
        MotorMode("pos");
        if (back) {
            setPower(-speed, -speed);
        } else {
            setPower(speed, speed);
        }
        while (backLeft.isBusy() && frontLeft.isBusy() && frontRight.isBusy() && backRight.isBusy()) {

        }
        setPower(0, 0);
        MotorMode("run");
    }

    public void left(int angle) {
        imu.resetYaw();
        setPower(-0.5, 0.5);
        while (!loopDone) {
            YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
            if (orientation.getYaw(AngleUnit.DEGREES) >= angle) {
                setPower(0, 0);
                loopDone = true;
            }

            telemetry.addData("Yaw (Z)", "%.2f Deg. (Heading)", orientation.getYaw(AngleUnit.DEGREES));
            telemetry.update();
        }
        loopDone = false;
        setPower(0.1, -0.1);
        while (!loopDone) {
            YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
            if (orientation.getYaw(AngleUnit.DEGREES) <= angle) {
                setPower(0, 0);
                loopDone = true;
            }

            telemetry.addData("Yaw (Z)", "%.2f Deg. (Heading)", orientation.getYaw(AngleUnit.DEGREES));
            telemetry.update();
        }
        loopDone = false;
    }

    public void right(int angle) {
        imu.resetYaw();
        setPower(0.5, -0.5);
        while (!loopDone) {
            YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
            if (orientation.getYaw(AngleUnit.DEGREES) <= -angle) {
                setPower(0, 0);
                loopDone = true;
            }

            telemetry.addData("Yaw (Z)", "%.2f Deg. (Heading)", orientation.getYaw(AngleUnit.DEGREES));
            telemetry.update();
        }
        loopDone = false;
        setPower(-0.1, 0.1);
        while (!loopDone) {
            YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
            if (orientation.getYaw(AngleUnit.DEGREES) >= -angle) {
                setPower(0, 0);
                loopDone = true;
            }

            telemetry.addData("Yaw (Z)", "%.2f Deg. (Heading)", orientation.getYaw(AngleUnit.DEGREES));
            telemetry.update();
        }
        loopDone = false;
    }

    public void linearMove(int rotations, double speed, boolean down) {
        // linear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //linear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //telemetry.addData("GoalTicks", rotations);
        linear.setTargetPosition(rotations);
        linear.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        if (down){
            linear.setPower(-speed);
        } else {
            linear.setPower(speed);
        }
        //sleep(Math.abs(rotations / 2));

        while (linear.isBusy()) {
            int prevLinear = linear.getCurrentPosition();
            sleep(300);
            if (Math.abs(linear.getCurrentPosition()) >= Math.abs(linear.getTargetPosition())) {
                linear.setTargetPosition(-linear.getCurrentPosition());
                linear.setPower(0);
                linear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            } else if (linear.getCurrentPosition() <= prevLinear || !(linear.getCurrentPosition() > prevLinear + 20)) {
                linear.setPower(0);
                linear.setTargetPosition(-linear.getCurrentPosition());
                linear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }
//    int previousPos = linear.getCurrentPosition();
//    sleep(250);
//    if (down) {
//       if (!(linear.getCurrentPosition() < previousPos)) {
//           linear.setTargetPosition(linear.getCurrentPosition() + 50);
//           sleep(300);
//
//
//       }
//    }
        }
        linear.setPower(0);
        linear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }
    @Override
    public void runOpMode() throws InterruptedException {
        myHardware.initialize(hardwareMap,telemetry);
        waitForStart();
        telemetry.addData("FrontRight1324576", frontRight.getCurrentPosition());
        telemetry.addData("FrontRight",  new Double(frontRight.getCurrentPosition()) / 24855.6);
        telemetry.addData("FrontLeft", new Double(myHardware.getFrontLeft().getCurrentPosition()) / 24855.6);
        telemetry.addData("BackRight", new Double(myHardware.getBackRight().getCurrentPosition()) / 24855.6);
        telemetry.addData("BackLeft", new Double(myHardware.getBackLeft().getCurrentPosition()) / 24855.6);

        telemetry.addData("FrontRight is off by", (new Double(myHardware.getFrontRight().getCurrentPosition()) / 24855.6) - 50);
        telemetry.addData("FrontLeft is off by", (new Double(myHardware.getFrontLeft().getCurrentPosition()) / 24855.6) - 50);
        telemetry.addData("BackRight is off by", (myHardware.getBackRight().getCurrentPosition() / 24855.6) - 50);
        telemetry.addData("BackLeft is off by", (myHardware.getBackLeft().getCurrentPosition() / 24855.6) - 50);
        telemetry.update();
        sleep(500000);

    }
}
