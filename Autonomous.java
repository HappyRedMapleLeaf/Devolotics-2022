// helpful: https://stemrobotics.cs.pdx.edu/node/4746

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Pebis", group="Pushbot")

public class Pebis extends LinearOpMode {
    
    /* Public OpMode members. */
    public DcMotor leftDrive;
    public DcMotor rightDrive;
    public DcMotor armMotor;
    public DcMotor intakeMotor;
    //public DcMotor duckMotor;

    /* Declare OpMode members. */
    HardwarePushbot         robot   = new HardwarePushbot();   // Use a Pushbot's hardware
    private ElapsedTime     runtime = new ElapsedTime();

    // moves drive motors the specified number of ticks and does telemetry stuff
    // one rotation is 1440 ticks
    public void driveToTarget(int targetL, int targetR, double powerL, double powerR) {
        // set targets for motors
        leftDrive.setTargetPosition(leftDrive.getCurrentPosition() + targetL);
        rightDrive.setTargetPosition(rightDrive.getCurrentPosition() + targetR);
        
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
        
        // stops the motor, idk if this is necessary, idk what it does, it might even break and make both motors move the same amount of time...
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
        //duckMotor = hardwareMap.dcMotor.get("duckMotor");
        armMotor = hardwareMap.dcMotor.get("armMotor");
        intakeMotor = hardwareMap.dcMotor.get("intake");

        leftDrive.setDirection(DcMotor.Direction.REVERSE);

        // reset encoders
        leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        
        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        // start sequence
        telemetry.addData("Status", "Waiting for start");
        telemetry.update();
        waitForStart();
        telemetry.addData("Status", "Running");
        telemetry.update();
        
        // actual program
        driveToTarget(1900, 1900, 0.7, 0.7); //forwards to carousel
        
        // carousel
        //duckMotor.setPower(1.0);
        sleep(3000);
        //duckMotor.setPower(0.0);
        
        driveToTarget(-10000, -10000, 1.0, 1.0); //backwards to around the middle
        
        driveToTarget(1440, -1440, 0.5, 0.5); //turn towards hub
        
        driveToTarget(1440, 1440, 0.7, 0.7); //go towards hub
        
        // lift arm (i know, it could be done at the same time with driving, but a) lazy, and b) we have extra time for sure)
        armMotor.setPower(0.5);
        sleep(500);
        armMotor.setPower(0.0); //this should brake the arm...
        
        driveToTarget(400, 400, 0.2, 0.2); //go towards hub even more
        
        // drop preload box
        intakeMotor.setPower(-1.0);
        sleep(600);
        intakeMotor.setPower(0.0);
        
        driveToTarget(-400, -400, 0.2, 0.2); //away from hub
        
        // drop arm
        armMotor.setPower(-1.0);
        sleep(400);
        armMotor.setPower(0.0);
        
        driveToTarget(-2900, -2900, 1.0, 1.0); //go to wall, then a bit more to square with wall
        
        driveToTarget(2900, -50, 0.5, 0.1); //turn towards warehouse
        
        driveToTarget(6500, 6500, 0.8, 0.8); //go into warehouse
    }
}
