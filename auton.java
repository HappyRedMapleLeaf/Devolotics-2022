package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name="provsAutonomous", group="Devolotics")

public class auton extends LinearOpMode {
    
    public DcMotor leftDrive;
    public DcMotor rightDrive;
    public DcMotor armMotor;
    public DcMotor intakeMotor;
    public Servo duckMotor;

    // moves drive motors the specified number of ticks and does telemetry stuff
    // 1440 ticks when 60:1, __ for 20:1 (480??)
    public int ticksPerRotation = 480;
    public void driveToTarget(double rotationL, double rotationR, double powerL, double powerR) {
        // set targets for motors
        int targetL = Math.toIntExact(Math.round(leftDrive.getCurrentPosition() + rotationL * ticksPerRotation));
        int targetR = Math.toIntExact(Math.round(rightDrive.getCurrentPosition() + rotationR * ticksPerRotation));
        leftDrive.setTargetPosition(targetL);
        rightDrive.setTargetPosition(targetR);

        // tell motors that they're supposed to go to the target
        leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // set power of motors. this is when they actually start to move
        leftDrive.setPower(Math.abs(powerL));
        rightDrive.setPower(Math.abs(powerR));

        // wait while the motors are turning
        while (opModeIsActive() && (leftDrive.isBusy() || rightDrive.isBusy())) {}

        // stops the motor
        leftDrive.setPower(0.0);
        rightDrive.setPower(0.0);

        // Turn off RUN_TO_POSITION
        leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    
    @Override
    public void runOpMode() throws InterruptedException
    {
        leftDrive = hardwareMap.dcMotor.get("leftDrive");
        rightDrive = hardwareMap.dcMotor.get("rightDrive");
        armMotor = hardwareMap.dcMotor.get("armMotor");
        intakeMotor = hardwareMap.dcMotor.get("intake");
        duckMotor = hardwareMap.get(Servo.class, "duckMotor");

        //motor settings
        rightDrive.setDirection(DcMotor.Direction.REVERSE);
        armMotor.setDirection(DcMotor.Direction.REVERSE);
        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        telemetry.addData("Hello", "Driver");
        telemetry.update();
        waitForStart();
        telemetry.addData("Status:", "Gaming");
        telemetry.update();

        // actual program. Intial position: halfway between first and second ground tile
        driveToTarget(0.96, 0.56, 1.0, 0.15); //forwards to carousel, turn a bit because duck wheel will hit the metal thingy
        
        // carousel
        duckMotor.setPosition(0.75);
        sleep(3000);
        duckMotor.setPosition(0.5);


        //driveToTarget(-0.86, -0.46, 1.0, 0.15); //back to initial position
        driveToTarget(-3.3, -3.3, 1.0, 1.0); //backwards to around the middle
        driveToTarget(1.04, -1.04, 0.5, 0.5); //turn towards hub
        
        //squaring to hub, measured in time because distance to hub is unknown
        leftDrive.setPower(-0.5);
        rightDrive.setPower(-0.5);
        sleep(900);
        leftDrive.setPower(0);
        rightDrive.setPower(0);

        driveToTarget(0.9, 0.9, 0.7, 0.7); //go towards hub
        
        // lift arm
        armMotor.setPower(0.5);
        //sleep(200); //level 1
        sleep(500); //level 2
        //sleep(700); //level 3
        armMotor.setPower(0.12); //keep arm up

        driveToTarget(0.5, 0.5, 0.1, 0.1); //go towards hub even more

        // drop preload box
        intakeMotor.setPower(0.7);
        sleep(1000);
        intakeMotor.setPower(0.0);

        driveToTarget(-0.28, -0.28, 0.2, 0.2); //away from hub

        // drop arm
        armMotor.setPower(0.0);

        driveToTarget(-2.5, -2.5, 0.3, 0.3); //go to wall, then a bit more to square with wall
        driveToTarget(2.45, -0.1, 0.5, 0.3); //turn towards warehouse

        driveToTarget(3.5, 3.5, 0.9, 0.9); //go into warehouse

        intakeMotor.setPower(-0.9);
        sleep(1500);
        intakeMotor.setPower(0.0);
        
        driveToTarget(-1, -1, 0.2, 0.2); //move to edge of warehouse
    }
}
