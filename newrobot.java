package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Nenjia Pov", group="Devolotics")

public class Nenjiadumbbot extends LinearOpMode {
    
    public DcMotor leftDrive;
    public DcMotor rightDrive;
    public DcMotor armMotor1;
    public DcMotor armMotor2;
//     public DcMotor duckMotor;

    @Override
    public void runOpMode() {
        leftDrive = hardwareMap.dcMotor.get("leftDrive");
        rightDrive = hardwareMap.dcMotor.get("rightDrive");
        armMotor1 = hardwareMap.dcMotor.get("armMotor1");
        armMotor2 = hardwareMap.dcMotor.get("armMotor2");
//         duckMotor = hardwareMap.dcMotor.get("duckMotor");

        telemetry.addData("Hello", "Bay the Gamer");
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        telemetry.addData("Status:", "Gaming");
        telemetry.update();
        
        //motor settings
        leftDrive.setDirection(DcMotor.Direction.REVERSE);
        armMotor1.setDirection(DcMotor.Direction.REVERSE);
        
        armMotor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armMotor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        
        leftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        armMotor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        armMotor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        
        final int armMin = 0;
        final int armMax = 380;
        final int armTop = 190;
        final int armSpeed = 5;
        int armTarget = 0;
        
        while (opModeIsActive()) {
            
            if (Math.abs(gamepad1.right_stick_x) > 0.05) { //right stick is touched (some tolerance is given)
                //turn
                leftDrive.setPower(gamepad1.right_stick_x);
                rightDrive.setPower(-gamepad1.right_stick_x);
                
            } else { //right stick is untouched
                leftDrive.setPower(-gamepad1.left_stick_y);
                rightDrive.setPower(-gamepad1.left_stick_y);
            }
            
            //intake motor
            /*
            if (gamepad1.left_trigger > 0){
                intakeMotor.setPower(0.9);
            } else if (gamepad1.right_trigger > 0){
                intakeMotor.setPower(-0.7);
            }
            intakeMotor.setPower(gamepad1.right_trigger - gamepad1.left_trigger); //left is out, right is in
            */
            
            //duck motor, only need one direction
            /*
            if ( gamepad1.dpad_left || gamepad1.dpad_right) {
                duckMotor.setPower(-0.4);
            } else if ( gamepad1.dpad_down || gamepad1.dpad_up ){
                duckMotor.setPower(-1.0);
            } else {
                duckMotor.setPower(0.0);
            }
            */

            
            //arm movement
            int armPosition = (armMotor1.getCurrentPosition() + armMotor2.getCurrentPosition()) / 2;
            
            //manual movement
            if (gamepad1.left_bumper) {                 //down
                armTarget -= armSpeed;
            } else if (gamepad1.right_bumper) {         //up
                armTarget += armSpeed;
            }
            
            //gravity is dumb. if this code is too complicated blame gravity and newton and einstein.
            if (armPosition >= armTop) {
                if (armPosition >= armTarget) {
                    armMotor1.setPower(-((armPosition - armTarget) * (armPosition - armTop)) / 36100);
                    armMotor2.setPower(-((armPosition - armTarget) * (armPosition - armTop)) / 36100);
                } else {
                    armMotor1.setPower(0);
                    armMotor2.setPower(0);
                }
            } else {
                if (armPosition >= armTarget) {
                    armMotor1.setPower(0);
                    armMotor2.setPower(0);
                } else {
                    armMotor1.setPower(((armPosition - armTarget) * (armPosition - armTop)) / 36100);
                    armMotor2.setPower(((armPosition - armTarget) * (armPosition - armTop)) / 36100);
                }
            }
            break;
            
            // Send telemetry
            telemetry.addData("armPosition", armPosition);
            telemetry.addData("armTarget", armTarget);
            telemetry.update();
        }
    }
}
