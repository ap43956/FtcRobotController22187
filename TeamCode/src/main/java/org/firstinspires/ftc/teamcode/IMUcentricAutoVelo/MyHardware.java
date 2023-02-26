package org.firstinspires.ftc.teamcode.IMUcentricAutoVelo;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Disabled
public class MyHardware {

    IMU imu;
    private DcMotorEx frontLeft;
    private DcMotorEx backLeft;
    private DcMotorEx frontRight;
    private DcMotorEx backRight;
    private DcMotorEx linear;
    private Servo claw;
    HardwareMap hardwareMap;
    Telemetry telemetry;
    double imuAngle = 0;
    public void initialize(HardwareMap hardwareMap, Telemetry vTelemetry){
        telemetry = vTelemetry;
        imu = hardwareMap.get(IMU.class, "imu");
        imu.resetYaw();
        linear = hardwareMap.get(DcMotorEx.class, "linear");
        frontLeft = hardwareMap.get(DcMotorEx.class, "frontLeft");
        backLeft = hardwareMap.get(DcMotorEx.class, "backLeft");
        frontRight = hardwareMap.get(DcMotorEx.class, "frontRight");
        backRight = hardwareMap.get(DcMotorEx.class, "backRight");
        frontLeft.setDirection(DcMotorEx.Direction.REVERSE);
        backLeft.setDirection(DcMotorEx.Direction.REVERSE);
        frontLeft.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        linear.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        frontRight.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        linear.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        linear.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        claw = hardwareMap.get(Servo.class, "claw");
    }
    public IMU getImu() {
        return imu;
    }

    public void setImu(IMU imu) {
        this.imu = imu;
    }

    public DcMotorEx getFrontLeft() {
        return frontLeft;
    }

    public void setFrontLeft(DcMotorEx frontLeft) {
        this.frontLeft = frontLeft;
    }

    public DcMotorEx getBackLeft() {
        return backLeft;
    }

    public void setBackLeft(DcMotorEx backLeft) {
        this.backLeft = backLeft;
    }

    public DcMotorEx getFrontRight() {
        return frontRight;
    }

    public void setFrontRight(DcMotorEx frontRight) {
        this.frontRight = frontRight;
    }

    public DcMotorEx getBackRight() {
        return backRight;
    }

    public void setBackRight(DcMotorEx backRight) {
        this.backRight = backRight;
    }

    public DcMotorEx getLinear() {
        return linear;
    }

    public void setLinear(DcMotorEx linear) {
        this.linear = linear;
    }

    public Servo getClaw() {
        return claw;
    }

    public void setClaw(Servo claw) {
        this.claw = claw;
    }

    public void openClaw(){
        claw.setPosition(1);
    }

    public void closeClaw(){
        claw.setPosition(0);
    }
}
