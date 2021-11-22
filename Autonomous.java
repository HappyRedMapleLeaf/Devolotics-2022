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
    DcMotor duck;
    DcMotor arm;
    DcMotor intake;
    
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
        duck = hardwareMap.dcMotor.get("duck");
        arm = hardwareMap.dcMotor.get("arm");
        intake = hardwareMap.dcMotor.get("intake");
        
        // AYO DO I NEED TO setZeroPowerBehavior????

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
        
        // actual program
        driveToTarget(5000, 5000, 0.8, 0.8) //forwards to carousel (completely arbitrary numbers btw)
        
        // carousel
        duck.setPower(1.0)
        sleep(3000)
        duck.setPower(0.0)
        
        driveToTarget(-10000, -10000, 1.0, 1.0) //backwards to around the middle
        
        driveToTarget(0, -2000, 0.0, 0.5) //turn towards hub
        
        driveToTarget(5000, 5000, 0.7, 0.7) //go towards hub
        
        // lift arm (i know, it could be done at the same time with driving, but a) lazy, and b) we have extra time for sure)
        arm.setPower(1.0)
        sleep(500)
        arm.setPower(0.0) //this should brake the arm...
        
        driveToTarget(800, 800, 0.2, 0.2) //go towards hub even more
        
        // drop preload box
        intake.setPower(-1.0)
        sleep(300)
        arm.setPower(0.0)
        
        driveToTarget(-800, -800, 0.2, 0.2) //away from hub
        
        // drop arm
        arm.setPower(-1.0)
        sleep(500)
        arm.setPower(0.0)
        
        driveToTarget(-5100, -5100, 1.0, 1.0) //go to wall, then a bit more to square with wall
        
        driveToTarget(2000, 0, 0.5, 0.0) //turn towards warehouse
        
        driveToTarget(5000, 5000, 0.8, 0.8) //go into warehouse hopefully
    }
}
