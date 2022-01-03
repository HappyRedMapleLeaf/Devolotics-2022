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
        
        const int armMin = 10; //0
        const int armMax = 370; //380
        const int armTop = 190;
        int armFreezeTarget = 0;
        
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
//             if (gamepad1.left_trigger > 0){
//                 intakeMotor.setPower(0.9);
//             } else if (gamepad1.right_trigger > 0){
//                 intakeMotor.setPower(-0.7);
//             }
            //intakeMotor.setPower(gamepad1.right_trigger - gamepad1.left_trigger); //left is out, right is in
            
            
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
            double armTargetPower = 0;
            
            //manual movement
            if (gamepad1.left_bumper) { //down
                armTargetPower = -0.2;
                armFreezeTarget = -1000;
            } else if (gamepad1.right_bumper) { //up
                armTargetPower = 0.2;
                armFreezeTarget = -1000;
            } else {
                armTargetPower = 0;
            }
            
            if (armTargetPower > 0) { //trying to move up
                
                //if the motor is close to the max, stop
                if (armPosition > armMax - 20) {
                    armTargetPower = 0;
                //if the motor is close to the top, slow down
                } else if (armPosition > armTop - 10) {
                    armTargetPower = 0.1; //slow down
                }
                
            } else if (armTargetPower < 0) { //trying to move down
                
                //close to the min, stop
                if (armPosition < armMin + 20) {
                    armTargetPower = 0;
                //close to the top, slow down
                } else if (armPosition < armTop + 10) {
                    armTargetPower = -0.1; //slow down
                }
                
            } else {
                if (armFreezeTarget == -1000) {
                    armFreezeTarget = armPosition;
                }
            }

            //making sure that the arm is at the freeze target
            if (armFreezeTarget != -1000) {
                if (armPosition > armFreezeTarget) {
                    //stop the arm if it's too high, and let it fall
                    armMotor1.setPower(0);
                    armMotor2.setPower(0);
                } else if (armPosition < armFreezeTarget) {
                    //slowly raise the arm if it's too low
                    armMotor1.setPower(0.12); 
                    armMotor2.setPower(0.12);
                }
            } else {
                armMotor1.setPower(armTargetPower);
                armMotor2.setPower(armTargetPower);
            }
            
            // Send telemetry
            telemetry.addData("armPosition", armPosition);
            telemetry.addData("freezeTarget", armFreezeTarget);
            telemetry.update();
        }
    }
}
