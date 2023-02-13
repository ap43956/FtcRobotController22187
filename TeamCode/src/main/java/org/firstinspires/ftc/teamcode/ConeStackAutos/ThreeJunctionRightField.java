package org.firstinspires.ftc.teamcode.ConeStackAutos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.teamcode.auto.MoveRobot;
import org.firstinspires.ftc.teamcode.auto.MyHardware;
import org.firstinspires.ftc.teamcode.auto.RotateRobot;

import java.util.List;
@Autonomous(name = "ThreeJunctionLeft", group = "Sensor")
public class ThreeJunctionRightField extends LinearOpMode {
    private MoveRobot moveRobot = new MoveRobot();
    private RotateRobot rotateRobot = new RotateRobot();
    private MyHardware myHardware = new MyHardware();

    public void linearMove(int rotations, double speed, boolean down) {
        myHardware.getLinear().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //linear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //telemetry.addData("GoalTicks", rotations);
        myHardware.getLinear().setTargetPosition(rotations);
        myHardware.getLinear().setMode(DcMotor.RunMode.RUN_TO_POSITION);

        if (down) {
            myHardware.getLinear().setPower(-speed);
        } else {
            myHardware.getLinear().setPower(speed);
        }
        while (myHardware.getLinear().isBusy()) {

        }
        myHardware.getLinear().setPower(0);
        myHardware.getLinear().setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

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


        // Use loadModelFromAsset() if the TF Model is built in as an asset by Android Studio
        // Use loadModelFromFile() if you have downloaded a custom team model to the Robot Controller's FLASH.
        //tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABELS);


        tfod.loadModelFromFile(TFOD_MODEL_FILE, LABELS);
    }


    @Override
    public void runOpMode() throws InterruptedException {
        myHardware.initialize(hardwareMap, telemetry);

        //inits vuforia
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
            tfod.setZoom(1.5, 16.0 / 9.0);
            telemetry.addData("setzoom", "");
        } else {
            telemetry.addData("tfod is null", "");
        }

        /** Wait for the game to begin */
        telemetry.addData(">", "Press Play to start op mode");
        telemetry.update();
        boolean read = true; //change autos VERY IMPORTANT

        boolean Scanned = false;
        telemetry.addLine("Pre Start");
        waitForStart();
        telemetry.addData("started", "");
        int i = 0;
        int l = 0;
        int b = 0;
        String scan = null;
        if (opModeIsActive()) {
            if (read) {
                scan = null;
                while (opModeIsActive() && !Scanned && b < 64285) {
                    b++;

                    telemetry.update();
                    scan = null;
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
                            scan = recognition.getLabel();
                            telemetry.addData("- Position (Row/Col)", "%.0f / %.0f", row, col);
                            telemetry.addData("- Size (Width/Height)", "%.0f / %.0f", width, height);
                            Scanned = true;
                        }
                        telemetry.update();
                        // }
                    } else {
                        telemetry.addData("tfod is null again", "");
                    }
//                if (b > 100000) {
//                    Scanned = true;
//                }
                }
                // Auto Start -------------------------------------------------------------------------------------------------------------------------------------------------------------
                //grab the cone
                myHardware.getClaw().setPosition(0);
                sleep(900);
                linearMove(-1850, 1, false);
                //move to and place cone on low junction
               moveRobot.strafe(myHardware, 7.5, 0.5, true, true);
               moveRobot.move(myHardware,7.5,0.5,false,true);
                sleep(20);
                myHardware.getClaw().setPosition(1);
                //move to cone stack
                moveRobot.strafe(myHardware, 12.25, 0.5, false, true);
                moveRobot.move(myHardware, 47.75, 0.5, false, true);
                moveRobot.move(myHardware, 5, 0.5,true,true);
                rotateRobot.right(myHardware, 90, telemetry);
                linearMove(525, 1, false);
                moveRobot.move(myHardware, 24.45, 0.25, false, true);

                myHardware.getClaw().setPosition(0);
                sleep(200);
                //Previous Program / first cone stack grab
                // myHardware.getLinear().setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                moveRobot.move(myHardware, 1, 0.5, true, true);
                linearMove(-1400, 1, false);
                moveRobot.move(myHardware, 21, 0.5, true, true);

                // linearMove(-1000,1,false);
                moveRobot.strafe(myHardware, 13.25, 0.5, false, true);
                myHardware.getClaw().setPosition(1);
                sleep(75 );
                moveRobot.strafe(myHardware,1,0.5,false,true);
                //third iteration (middle junction)
                moveRobot.move(myHardware, 2, 1, true, true);
                //myHardware.getLinear().setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                linearMove(1150,1,false);
                moveRobot.strafe(myHardware, 11.75, 0.5, true, true);
                moveRobot.move(myHardware, 23, 0.65, false, true);
                myHardware.getClaw().setPosition(0);
                sleep(200);
                //Move to middle junction
                //myHardware.getLinear().setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                moveRobot.move(myHardware, 1, 0.5, true, true);
                //corrects degree of rotation
                if (myHardware.getImu().getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) < 90) {
                    rotateRobot.right(myHardware, 90, telemetry);
                } else if (myHardware.getImu().getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) > 90) {
                    rotateRobot.left(myHardware, 90, telemetry);
                }
                sleep(100);
                linearMove(-3200, 1, false);


                moveRobot.move(myHardware, 43.5, 0.5, true, true);
                //corrects degree of rotation
                if (myHardware.getImu().getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) < 90) {
                    rotateRobot.right(myHardware, 90, telemetry);
                } else if (myHardware.getImu().getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) > 90) {
                    rotateRobot.left(myHardware, 90, telemetry);
                }
                sleep(100);
                moveRobot.strafe(myHardware, 12.0, 0.5, false, true);
                sleep(100);
                myHardware.getClaw().setPosition(1);

                //Moves over and parks
                moveRobot.strafe(myHardware, 15.5, 0.5, false , true);
                if (scan == "1") {
                    telemetry.addLine("VUFORIA 1");
                    moveRobot.move(myHardware, 42, 0.5, false, true);
                } else if (scan == "2") {
                    telemetry.addLine("VUFORIA 2");
                    moveRobot.move(myHardware, 20, 0.5, false, true);
                } else if (scan == "3") {
                    telemetry.addLine("VUFORIA 3");
                    moveRobot.move(myHardware,2,0.5,true,true);
                } else {
                    telemetry.addLine("FAILED VUFORIA");
                    moveRobot.move(myHardware, 20, 0.5, false, true);
                }
            }


        }


    }
}
