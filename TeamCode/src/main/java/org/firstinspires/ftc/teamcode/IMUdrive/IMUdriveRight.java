package org.firstinspires.ftc.teamcode.IMUdrive;


import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

//@Autonomous(name = "(IMUdriveR)", group = "Sensor")
public class IMUdriveRight extends LinearOpMode {

    IMU imu;
    private DcMotor frontLeft;
    private DcMotor backLeft;
    private DcMotor frontRight;
    private DcMotor backRight;
    private DcMotor linear;
    private Servo claw;
    boolean loopDone = false;
    Recognition recognition;
    ExposureControl Exposure;

    private static final String TFOD_MODEL_FILE = "/sdcard/FIRST/tflitemodels/ConeDetect.tflite";


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
        /*
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
        tfodParameters.minResultConfidence = 0.750f;
        tfodParameters.isModelTensorFlow2 = true;
        tfodParameters.inputSize = 300;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);

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

    public void setPower(boolean half, double speed, double speed2) {
        if (half) {
            frontRight.setPower(speed);
            backRight.setPower(speed);
            frontLeft.setPower(speed2);
            backLeft.setPower(speed2);
        } else {
            frontLeft.setPower(speed);
            frontRight.setPower(speed);
            backLeft.setPower(speed);
            backRight.setPower(speed);
        }
    }

    public void strafe(double rotations, double speed, boolean left) {
        MotorMode("reset");
        double data = 538 * rotations;
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
        setPower(false, 0, 0);
        MotorMode("run");
    }

    public void move(double rotations, double speed, boolean back) {
        MotorMode("reset");
        double data = 538 * rotations;
        int value = (int) data;
        telemetry.addData("GoalTicks", value);
        backLeft.setTargetPosition(value);
        frontLeft.setTargetPosition(value);
        frontRight.setTargetPosition(value);
        backRight.setTargetPosition(value);
        MotorMode("pos");
        if (back) {
            setPower(false, -speed, 0);
        } else {
            setPower(false, speed, 0);
        }
        while (backLeft.isBusy() && frontLeft.isBusy() && frontRight.isBusy() && backRight.isBusy()) {

        }
        setPower(false, 0, 0);
        MotorMode("run");
    }

    public void left(int angle) {
        imu.resetYaw();
        setPower(true, -0.5, 0.5);
        while (!loopDone) {
            YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
            if (orientation.getYaw(AngleUnit.DEGREES) >= angle) {
                setPower(false, 0, 0);
                loopDone = true;
            }

            telemetry.addData("Yaw (Z)", "%.2f Deg. (Heading)", orientation.getYaw(AngleUnit.DEGREES));
            telemetry.update();
        }
        loopDone = false;
        setPower(true, 0.1, -0.1);
        while (!loopDone) {
            YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
            if (orientation.getYaw(AngleUnit.DEGREES) <= angle) {
                setPower(false, 0, 0);
                loopDone = true;
            }

            telemetry.addData("Yaw (Z)", "%.2f Deg. (Heading)", orientation.getYaw(AngleUnit.DEGREES));
            telemetry.update();
        }
        loopDone = false;
    }

    public void right(int angle) {
        imu.resetYaw();
        setPower(true, 0.5, -0.5);
        while (!loopDone) {
            YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
            if (orientation.getYaw(AngleUnit.DEGREES) <= -angle) {
                setPower(false, 0, 0);
                loopDone = true;
            }

            telemetry.addData("Yaw (Z)", "%.2f Deg. (Heading)", orientation.getYaw(AngleUnit.DEGREES));
            telemetry.update();
        }
        loopDone = false;
        setPower(true, -0.1, 0.1);
        while (!loopDone) {
            YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
            if (orientation.getYaw(AngleUnit.DEGREES) >= -angle) {
                setPower(false, 0, 0);
                loopDone = true;
            }

            telemetry.addData("Yaw (Z)", "%.2f Deg. (Heading)", orientation.getYaw(AngleUnit.DEGREES));
            telemetry.update();
        }
        loopDone = false;
    }

    public void linearMove(double rotations, double speed, boolean down) {
        linear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        double data = 538 * rotations;
        int value = (int) data;
        telemetry.addData("GoalTicks", value);
        telemetry.addData("PreMult", value / 538);
        linear.setTargetPosition(value);
        linear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        if (down) {
            linear.setPower(-speed);
        } else {
            linear.setPower(speed);
        }
        while (linear.isBusy()) {

        }
        linear.setPower(0);
        linear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }


    @Override
    public void runOpMode() throws InterruptedException {
      //  Exposure = vuforia.getCamera().getControl(ExposureControl.class);
        imu = hardwareMap.get(IMU.class, "imu");
        linear = hardwareMap.get(DcMotor.class, "linear");
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        claw = hardwareMap.get(Servo.class, "claw");
        //Exposure.setMode(ExposureControl.Mode.Manual);
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        linear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        linear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        linear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RevHubOrientationOnRobot.LogoFacingDirection logoDirection = RevHubOrientationOnRobot.LogoFacingDirection.UP;
        RevHubOrientationOnRobot.UsbFacingDirection usbDirection = RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD;
        RevHubOrientationOnRobot orientationOnRobot = new RevHubOrientationOnRobot(logoDirection, usbDirection);
        imu.initialize(new IMU.Parameters(orientationOnRobot));
        telemetry.addData("InOpMode", "");
        initVuforia();
        telemetry.addData("loadedvuforia", "");
        initTfod();
        telemetry.addData("RanTfod", "");
        /**
         * Activate TensorFlow Object Detection before we wait for the start command.
         * Do it here so that the Camera Stream window will have the TensorFlow annotations visible.
         **/
        if (tfod != null) {
            telemetry.addData("tfod not null", "");
            tfod.activate();
            telemetry.addData("We activated", "We activated");
            // The TensorFlow software will scale the input images from the camera to a lower resolution.
            // This can result in lower detection accuracy at longer distances (> 55cm or 22").
            // If your target is at distance greater than 50 cm (20") you can increase the magnification value
            // to artificially zoom in to the center of image.  For best results, the "aspectRatio" argument
            // should be set to the value of the images used to create the TensorFlow Object Detection model
            // (typically 16/9).
           // Exposure.setExposure(60, TimeUnit.MILLISECONDS);
            tfod.setZoom(2.0, 16.0 / 9.0);
            telemetry.addData("setzoom", "");
        } else {
            telemetry.addData("tfod is null", "");
        }

        /** Wait for the game to begin */
        telemetry.addData(">", "Press Play to start op mode");
        telemetry.update();

        boolean Scanned = false;
        waitForStart();
        telemetry.addData("started", "");
        int i = 0;
        int l = 0;
        if (opModeIsActive()) {
            String scan = null;
            while (opModeIsActive() && !Scanned) {

                telemetry.update();
                if (tfod != null) {
                    // getUpdatedRecognitions() will return null if no new information is available since
                    // the last time that call was made.
                    List<Recognition> updatedRecognitions = tfod.getRecognitions();
                    //if (updatedRecognitions != null) {
                    telemetry.addData("# Objects Detected", updatedRecognitions.size());

                    // step through the list of recognitions and display image position/size information for each one
                    // Note: "Image number" refers to the randomized image orientation/number
                    for (Recognition recognition : updatedRecognitions) {
                        l++;

                        double col = (recognition.getLeft() + recognition.getRight()) / 2;
                        double row = (recognition.getTop() + recognition.getBottom()) / 2;
                        double width = Math.abs(recognition.getRight() - recognition.getLeft());
                        double height = Math.abs(recognition.getTop() - recognition.getBottom());
                        telemetry.addData("", "");
                        telemetry.addData("", " ");
                        telemetry.addData("Image", "%s (%.0f %% Conf.)", recognition.getLabel(), recognition.getConfidence() * 100);
                        scan = (recognition.getLabel());
                        telemetry.addData("- Position (Row/Col)", "%.0f / %.0f", row, col);
                        telemetry.addData("- Size (Width/Height)", "%.0f / %.0f", width, height);
                        Scanned = true;
                    }
                    telemetry.update();
                }
                //}
                else {
                    telemetry.addData("tfod is null again", "");

                }
            }
            claw.setPosition(0);
            move(0.1, 0.7, false);
            right(90);
            move(1, 0.5, false);
            claw.setPosition(1);
            move(-1, 0.5, true);
            left(90);
            move(-0.1, 0.7, true);
            if (scan == "1") {
                strafe(2, 0.25, true);
                move(2, 0.25, false);
            } else if (scan == "2") {
                move(2, 0.25, false);
            } else if (scan == "3"){
                move(2, 0.25, false);
                strafe(2 , 0.25, false);
            } else {
                move(2,0.25,false);
            }

        }
    }


}
