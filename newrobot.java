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
        armMotor2.setDirection(DcMotor.Direction.REVERSE);
        
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
        // run until the end of the match (driver presses STOP)
        int armMin = 20; //0
        int armMax = 700; //456
        int arm1FreezeTarget = 0;
        int arm2FreezeTarget = 0;
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
            if ( gamepad1.dpad_left || gamepad1.dpad_right) {
                duckMotor.setPower(-0.4);
            } else if ( gamepad1.dpad_down || gamepad1.dpad_up ){
                duckMotor.setPower(-1.0);
            } else {
                duckMotor.setPower(0.0);
            }

            
            //arm movement
            int armPosition1 = armMotor1.getCurrentPosition();
            int armPosition2 = armMotor2.getCurrentPosition();
            double arm1TargetPower = 0;
            double arm2TargetPower = 0;
            
//             if (gamepad1.left_bumper) {
//                 armMotor1.setPower(-0.7);
//                 armMotor2.setPower(-0.7);
//             } else if (gamepad1.right_bumper) {
//                 armMotor1.setPower(0.7);
//                 armMotor2.setPower(0.7);
//             } else {
//                 armMotor1.setPower(0);
//                 armMotor2.setPower(0);
//             }

            if (gamepad1.left_bumper) { //down
                armTargetPower = -0.3;
                armFreezeTarget = -1000; //explained later
            } else if (gamepad1.right_bumper) { //up
                armTargetPower = 0.3;
                armFreezeTarget = -1000;
            } else {
                armTargetPower = 0;
            }
            
            if (armTargetPower > 0) { //trying to move up
                if (armPosition > armMax) { //but too high
                    armTargetPower = 0; //stop
                } else if (armPosition > armMax - 100) { //almost too high
                    armTargetPower = 0.2; //slow down
                }
            } else if (armTargetPower < 0) { //trying to move down
                if (armPosition < armMin + 100) { //almost too low
                    armTargetPower = 0; //"stopping" the motor, but really, this just drops it slowly
                }
            } else {
                //kinda complex: armFreezeTarget is only set for the *first time* that targetpower is 0
                //this is done by setting armFreezeTarget to -1000 whenever a non-zero target power is set
                //so if the next loop comes and sees that armFreezeTarget isn't -1000, it knows not to change it
                //because otherwise the "target" would keep moving and be useless
                if (armFreezeTarget == -1000) {
                    armFreezeTarget = armPosition;
                }
            }
            
            //if the preset buttons are pressed, the freeze target changes
            if (gamepad1.a) { armFreezeTarget = 180; }
            if (gamepad1.x) { armFreezeTarget = 290; }
            
            //making sure that the arm is at the freeze target
            if (armFreezeTarget != -1000) { //it shouldn't try going to the target if a non-zero target power is set
                if (armPosition > armFreezeTarget) {
                    armMotor.setPower(0); //stop the arm if it's too high, and let it fall
                } else if (armPosition < armFreezeTarget) {
                    armMotor.setPower(0.12); //slowly raise the arm if it's too low
                }
            } else {
                armMotor.setPower(armTargetPower);
            }    
            
            // Send telemetry
            telemetry.addData("armPosition1", armPosition1);
            telemetry.addData("armPosition2", armPosition2);
            telemetry.update();
        }
    }
}
