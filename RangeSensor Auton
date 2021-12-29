package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

//import the Analog Library
import edu.wpi.first.wpilibj.AnalogInput;

@Autonomous(name="Autonomous 2", group="Devolotics")

public class auton extends LinearOpMode {
    
    public DcMotor leftDrive;
    public DcMotor rightDrive;
    public DcMotor armMotor;
    public DcMotor intakeMotor;
    public DcMotor duckMotor;
    public AnalogInput sharp;

    // moves drive motors the specified number of ticks and does telemetry stuff
    
    // 1440 ticks when 60:1, somehow 720 when 40:1
    public int ticksPerRotation = 1000;
    
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
        
        // do telemetry stuff and also wait while the motors are turning
        while (opModeIsActive() && (leftDrive.isBusy() || rightDrive.isBusy()))
        {
            telemetry.addData("Target",  "Running to %7d :%7d", (leftDrive.getCurrentPosition() + targetL),  (rightDrive.getCurrentPosition() + targetR));
            telemetry.addData("Position",  "Running at %7d :%7d",
                                        leftDrive.getCurrentPosition(),
                                        rightDrive.getCurrentPosition());
            telemetry.update();
            idle();
        }
        
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
        duckMotor = hardwareMap.dcMotor.get("duckMotor");
        rangeSensor = hardwareMap.Analoginput.get("sharp");
        
        //motor settings
        leftDrive.setDirection(DcMotor.Direction.REVERSE);
        leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        
        // start sequence
        telemetry.addData("Status:", "Waiting for start");
        telemetry.update();
        waitForStart();
        telemetry.addData("Status", "Running");
        telemetry.update();
        
        
     }
}
