package org.firstinspires.ftc.teamcode.IMUcentricAutoVelo;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;
@Disabled
@Autonomous(name = "MecanumAutoIMUcentric3conesRightVelo", group = "Auto")
public class MecanumAuto3conesLeftField extends LinearOpMode {
    MyHardware H = new MyHardware();
    RotateWithIMU R = new RotateWithIMU();
    MoveWithIMU M = new MoveWithIMU();
    AutoMergedFunctions F = new AutoMergedFunctions();
    SimplifyCodeIMU S = new SimplifyCodeIMU();
    public void linear(int rotations, double speed, boolean down) {
        H.getLinear().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        telemetry.addData("GoalTicks", rotations);
        H.getLinear().setTargetPosition(rotations);
        H.getLinear().setMode(DcMotor.RunMode.RUN_TO_POSITION);
        if (down){
            H.getLinear().setPower(-speed);
        } else {
            H.getLinear().setPower(speed);
        }
        while(H.getLinear().isBusy()){

        }
        H.getLinear().setPower(0);
        H.getLinear().setMode(DcMotor.RunMode.RUN_USING_ENCODER);
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
        H.initialize(hardwareMap, telemetry);
        sleep(500);
        H.getImu().resetYaw();
        sleep(500);
        H.getClaw().setPosition(0);

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
                linear(-2050, 1, false);
                //move to and place cone on low junction
                M.strafe(H, 17.8, 0.5, false, true, false);
                M.move(H, 6, 0.5, false, true, true);
                sleep(20);
                H.getClaw().setPosition(1);
                //move to cone stack
                M.strafe(H, 12, 0.5, true, true, true);
                M.move(H, 47.75, 0.7, false, true,true);
                M.move(H, 3, 0.5,true,true,true);
                //first and only turn 90 left
                H.imuAngle=90;
                F.LinearAndTurn(H,telemetry,650,1,false);
//                R.GoToAngle(H, telemetry);
//                linear(525, 1, false);
                M.move(H, 23.45, 0.4, false, true,true);
                H.getClaw().setPosition(0);
                M.move(H, 2, 0.4, false, true,false);
                sleep(200);
                //Previous Program / first cone stack grab
                // H.getLinear().setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                M.move(H, 1, 0.5, true, true,true);
                linear(-1400, 1, false);
                M.move(H, 19, 0.5, true, true,true);

                // linear(-1000,1,false);
                M.strafe(H, 13 , 0.5, true, true,true);
                H.getClaw().setPosition(1);
                sleep(75 );
                M.strafe(H,1,0.5,true,true,true);
                //third iteration (middle junction)
                M.move(H, 3, 1, true, true,true);
                //H.getLinear().setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                linear(1150,1,false);
                M.strafe(H, 13.5, 0.5, false, true,true);
                M.move(H, 23, 0.7, false, true,true);
                H.getClaw().setPosition(0);
                sleep(200);
                //Move to middle junction
                //H.getLinear().setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                M.move(H, 1, 0.5, true, true,true);
                linear(-3000, 1, false);


                M.move(H, 42.5, 0.5, true, true,true);

                M.strafe(H, 13, 0.5, true, true,true);

                H.getClaw().setPosition(1);
                sleep(750);
                //Moves over and parks
                M.strafe(H, 13, 0.5, true, true,true);
                if (scan == "1") {
                    telemetry.addLine("VUFORIA 1");
                    M.move(H, 44, 0.5, false, true,true);
                } else if (scan == "2") {
                    telemetry.addLine("VUFORIA 2");
                    M.move(H, 21, 0.5, false, true,true);
                } else if (scan == "3") {
                    telemetry.addLine("VUFORIA 3");
                } else {
                    M.move(H, 44, 0.5, false, true,true);
                }
            }


        }


    }
    
}
