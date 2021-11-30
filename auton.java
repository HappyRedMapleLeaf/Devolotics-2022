package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Autonomous", group="Devolotics")

public class auton extends LinearOpMode {
    
    public DcMotor leftDrive;
    public DcMotor rightDrive;
    public DcMotor armMotor;
    public DcMotor intakeMotor;
    //public DcMotor duckMotor;

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
        //duckMotor = hardwareMap.dcMotor.get("duckMotor");
        
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
        
        
        // actual program. Intial position: halfway between first and second ground tile
        driveToTarget(1100, 800, 1.0, 0.4); //forwards to carousel, turn a bit because duck wheel will hit the metal thingy
        
        // carousel
        duckMotor.setPower(-0.5);
        sleep(3000);
        duckMotor.setPower(0.0);
        
        driveToTarget(-1100, -800, 1.0, 0.4); //back to initial position
        
        driveToTarget(-3900, -4000, 1.0, 1.0); //backwards to around the middle
        
        driveToTarget(1500, -1500, 0.5, 0.5); //turn towards hub
        
        leftDrive.setPower(-0.5);
        rightDrive.setPower(-0.5);
        sleep(2000);
        leftDrive.setPower(0);
        rightDrive.setPower(0);
        
        driveToTarget(1800, 1800, 0.7, 0.7); //go towards hub
        
        // lift arm (i know, it could be done at the same time with driving, but a) lazy, and b) we have extra time for sure)
        armMotor.setPower(-0.5);
        sleep(600);
        armMotor.setPower(-0.1); //too lazy to do encoder stuff, so im just gonna uhm... apply a little power and hope it keeps it up lol
        
        driveToTarget(450, 450, 0.2, 0.2); //go towards hub even more
        
        // drop preload box
        intakeMotor.setPower(1.0);
        sleep(1000);
        intakeMotor.setPower(0.0);
        
        driveToTarget(-400, -400, 0.2, 0.2); //away from hub
        
        // drop arm
        armMotor.setPower(0.0);
        
        driveToTarget(-2700, -2700, 1.0, 1.0); //go to wall, then a bit more to square with wall
        
        driveToTarget(2900, -50, 0.5, 0.1); //turn towards warehouse
        
        driveToTarget(6300, 6300, 0.81, 0.8); //go into warehouse
    }
}
