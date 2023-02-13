package org.firstinspires.ftc.teamcode.auto;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

public class Camera extends LinearOpMode {
     final String TFOD_MODEL_FILE  = "/sdcard/FIRST/tflitemodels/PogCone.tflite";
    private static final String[] LABELS = {
            "1",
            "2",
            "3"
    };
    private static final String VUFORIA_KEY =
            "ASlEG4X/////AAABmY1ivleqDEAOlO+c8WLrjuQ0XPykAjsJdqSDj8SE3Rdi0UitfIUC9nDsxtCi1QiucOpnxiguhOHRIjMXSE3fNiAq8nOVZKz5aEh6xd45UyvSqt4DifeXZqgpAN7n7yzaphd9eC0vmhlLUUt6RX4XtU9IUuumCXEgwqAOfKvJtx9h8LlgNhLgrkifTRm0xAcqwD3N2u7GRmqtpY4WF2D9Y6V3kWLWOLC67x2QGQZuKVdWNq4G+0Ie/JxWqE9RbOI8GOlocLEHlPV1PJNt7Mx7I4lrhf95UJ3A1uNpYeJDjB3kGYkoppWKSgDc6AZCh5KYoXhQRiVARuchnHssrsj3UK6b5cSFTBjN+VhZhj/+ixBU";
    private VuforiaLocalizer vuforia;

    private TFObjectDetector tfod;
    String scan = null;


    public void initVuforia() {
        final String TFOD_MODEL_FILE  = "/sdcard/FIRST/tflitemodels/PogCone.tflite";
        final String[] LABELS = {
                "1",
                "2",
                "3"
        };

         final String VUFORIA_KEY =
                "ASlEG4X/////AAABmY1ivleqDEAOlO+c8WLrjuQ0XPykAjsJdqSDj8SE3Rdi0UitfIUC9nDsxtCi1QiucOpnxiguhOHRIjMXSE3fNiAq8nOVZKz5aEh6xd45UyvSqt4DifeXZqgpAN7n7yzaphd9eC0vmhlLUUt6RX4XtU9IUuumCXEgwqAOfKvJtx9h8LlgNhLgrkifTRm0xAcqwD3N2u7GRmqtpY4WF2D9Y6V3kWLWOLC67x2QGQZuKVdWNq4G+0Ie/JxWqE9RbOI8GOlocLEHlPV1PJNt7Mx7I4lrhf95UJ3A1uNpYeJDjB3kGYkoppWKSgDc6AZCh5KYoXhQRiVARuchnHssrsj3UK6b5cSFTBjN+VhZhj/+ixBU";
         VuforiaLocalizer vuforia;

          TFObjectDetector tfod;
        /*111
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = hardwareMap.get(WebcamName.class, "webcam");

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);
    }
    public void initTfod() {
        //initVuforia();
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
    public void CheckTfod() {
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
        //initTfod();
        int b = 0;
        boolean Scanned = false;
        if (opModeIsActive()) {
            while (opModeIsActive() && !Scanned && b < 150000) {
                b++;

                telemetry.update();

                int l = 0;
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
        }
    }
    public int ReturnTfod() {
        return Integer.parseInt(scan);
    }
    @Override
    public void runOpMode() throws InterruptedException {}
}
