// helpful: https://stemrobotics.cs.pdx.edu/node/4746

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name="Autonomous")

public class Pebis extends LinearOpMode
{
    // moves drive motors the specified number of ticks and does telemetry stuff
    // one rotations is 1440 ticks i think
    public void driveToTarget(int targetL, int targetR, double powerL, double powerR) {
        // set targets for motors
        leftDrive.setTargetPosition(driveL.getCurrentPosition() + targetL);
        rightDrive.setTargetPosition(driveR.getCurrentPosition() + targetR);
        
        // tell motors that they're supposed to go to the target
        leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        
        // set power of motors. this is when they actually start to move
        leftDrive.setPower(powerL);
        rightDrive.setPower(powerR);
        
        // do telemetry stuff and also wait while the motors are turning
        while (opModeIsActive() && (leftDrive.isBusy() || rightDrive.isBusy))
        {
            telemetry.addData("driveL position:", leftDrive.getCurrentPosition() + "  isBusy=" + leftDrive.isBusy());
            telemetry.addData("driveR position:", rightDrive.getCurrentPosition() + "  isBusy=" + rightDrive.isBusy());
            telemetry.update();
            idle();
        }
        
        // stops the motor, idk if this is necessary, idk what it does, it might even break and make both motors move the same amount of time...
        leftDrive.setPower(0.0);
        rightDrive.setPower(0.0);
        // if it does break, this step would have to be done in the while loop (ez fix)
    }
    
    @Override
    public void runOpMode() throws InterruptedException
    {
        leftDrive = hardwareMap.dcMotor.get("leftDrive");
        rightDrive = hardwareMap.dcMotor.get("rightDrive");
        duckMotor = hardwareMap.dcMotor.get("duckMotor");
        armMotor = hardwareMap.dcMotor.get("armMotor");
        //Don't have assignment for this yet
        intakeMotor = hardwareMap.dcMotor.get("intake");
        
        // AYO DO I NEED TO setZeroPowerBehavior????

        // may or may not need depending on the robot
        //driveL.setDirection(DcMotor.Direction.REVERSE);
        //driveR.setDirection(DcMotor.Direction.REVERSE);

        // reset encoders
        leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        
        // start sequence
        telemetry.addData("Status", "Waiting for start");
        telemetry.update();
        waitForStart();
        telemetry.addData("Status", "Running");
        telemetry.update();
        
        // actual program
        driveToTarget(1900, 1900, 0.7, 0.7); //forwards to carousel
        
        // carousel
        duckMotor.setPower(1.0);
        sleep(3000);
        duckMotor.setPower(0.0);
        
        driveToTarget(-10000, -10000, 1.0, 1.0); //backwards to around the middle
        
        driveToTarget(1440, -1440, 0.5, 0.5); //turn towards hub
        
        driveToTarget(1440, 1440, 0.7, 0.7); //go towards hub
        
        // lift arm (i know, it could be done at the same time with driving, but a) lazy, and b) we have extra time for sure)
        armMotor.setPower(1.0);
        sleep(500);
        armMotor.setPower(0.0); //this should brake the arm...
        
        driveToTarget(400, 400, 0.2, 0.2); //go towards hub even more
        
        // drop preload box
        intakeMotor.setPower(-1.0);
        sleep(600);
        armMotor.setPower(0.0);
        
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
