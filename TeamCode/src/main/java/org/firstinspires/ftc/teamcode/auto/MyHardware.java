package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class MyHardware {

    IMU imu;
    private DcMotor frontLeft;
    private DcMotor backLeft;
    private DcMotor frontRight;
    private DcMotor backRight;
    private DcMotor linear;
    private Servo claw;
    HardwareMap hardwareMap;
    Telemetry telemetry;

    public void initialize(HardwareMap hardwareMap, Telemetry vTelemetry){
        telemetry = vTelemetry;
        imu = hardwareMap.get(IMU.class, "imu");
        linear = hardwareMap.get(DcMotor.class, "linear");
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
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
        claw = hardwareMap.get(Servo.class, "claw");
    }

    public IMU getImu() {
        return imu;
    }

    public void setImu(IMU imu) {
        this.imu = imu;
    }

    public DcMotor getFrontLeft() {
        return frontLeft;
    }

    public void setFrontLeft(DcMotor frontLeft) {
        this.frontLeft = frontLeft;
    }

    public DcMotor getBackLeft() {
        return backLeft;
    }

    public void setBackLeft(DcMotor backLeft) {
        this.backLeft = backLeft;
    }

    public DcMotor getFrontRight() {
        return frontRight;
    }

    public void setFrontRight(DcMotor frontRight) {
        this.frontRight = frontRight;
    }

    public DcMotor getBackRight() {
        return backRight;
    }

    public void setBackRight(DcMotor backRight) {
        this.backRight = backRight;
    }

    public DcMotor getLinear() {
        return linear;
    }

    public void setLinear(DcMotor linear) {
        this.linear = linear;
    }

    public Servo getClaw() {
        return claw;
    }

    public void setClaw(Servo claw) {
        this.claw = claw;
    }
}
