// variables for each movement?
// helpful: https://stemrobotics.cs.pdx.edu/node/4746

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name="Autonomous")

public class vroom extends LinearOpMode
{
    DcMotor driveL;
    DcMotor driveR;
    
    // do i need to make this public? java is weird idk
    // moves drive motors the specified number of ticks and does telemetry stuff
    // basically just accurate movement, but still requires the wheels to be attached properly, NENGJIA!!! (sry)
    void driveToTarget(int targetL, int targetR, int powerL, int powerR) {
        // set targets for motors
        driveL.setTargetPosition(driveL.getCurrentPosition() + targetL);
        driveR.setTargetPosition(driveR.getCurrentPosition() + targetR);
        
        // tell motors that they're supposed to go to the target
        driveL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        driveR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        
        // set power of motors. this is when they actually start to move
        driveL.setPower(powerL);
        driveR.setPower(powerR);
        
        // do telemetry stuff and also wait while the motors are turning
        while (opModeIsActive() && (motorL.isBusy() || motorR.isBusy))
        {
            telemetry.addData("driveL position:", driveL.getCurrentPosition() + "  isBusy=" + driveL.isBusy());
            telemetry.addData("driveR position:", driveR.getCurrentPosition() + "  isBusy=" + driveR.isBusy());
            telemetry.update();
            idle();
        }
        
        // stops the motor, idk if this is necessary, idk what it does, it might even break and make both motors move the same amount of time...
        driveL.setPower(0.0);
        driveR.setPower(0.0);
        // if it does break, this step would have to be done in the while loop (ez fix)
    }
    
    @Override
    public void runOpMode() throws InterruptedException
    {
        driveL = hardwareMap.dcMotor.get("driveL");
        driveR = hardwareMap.dcMotor.get("driveR");

        // may or may not need depending on the robot
        //driveL.setDirection(DcMotor.Direction.REVERSE);
        //driveR.setDirection(DcMotor.Direction.REVERSE);

        // reset encoders
        driveL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        
        // start sequence
        telemetry.addData("Status", "Waiting for start");
        telemetry.update();
        waitForStart();
        telemetry.addData("Status", "Running");
        telemetry.update();
        
        
    }
}
