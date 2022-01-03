package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Bay POV", group="Devolotics")

public class teleop extends LinearOpMode {
    
    public DcMotor leftDrive;
    public DcMotor rightDrive;
    public DcMotor armMotor1;
    public DcMotor armMotor2;
    public DcMotor intakeMotor;
    public DcMotor duckMotor;

    @Override
    public void runOpMode() {
        leftDrive = hardwareMap.dcMotor.get("leftDrive");
        rightDrive = hardwareMap.dcMotor.get("rightDrive");
        armMotor1 = hardwareMap.dcMotor.get("armMotor1");
        armMotor2 = hardwareMap.dcMotor.get("armMotor2");
        intakeMotor = hardwareMap.dcMotor.get("intake");
        duckMotor = hardwareMap.dcMotor.get("duckMotor");
        
//         int armMin = 20; //0
//         int armMax = 455; //456
//         int armFreezeTarget = 0; //where the arm wants to stay stationary

        telemetry.addData("Hello", "Bay the Gamer");
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        telemetry.addData("Status:", "Gaming");
        telemetry.update();
        
        //motor settings
        leftDrive.setDirection(DcMotor.Direction.REVERSE);
        armMotor.setDirection(DcMotor.Direction.REVERSE);
        
        armMotor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armMotor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        
        leftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//         armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        armMotor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        // run until the end of the match (driver presses STOP)
        
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
            if (gamepad1.left_trigger > 0){
                intakeMotor.setPower(0.9);
            } else if (gamepad1.right_trigger > 0){
                intakeMotor.setPower(-0.7);
            }
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
            double armTargetPower = 0;
            
            
            if (gamepad1.left_bumper) {
                armMotor1.setPower(-0.7);
                armMotor2.setPower(-0.7);
            } else if (gamepad1.right_bumper) {
                armMotor1.setPower(0.7);
                armMotor2.setPower(0.7);
            } else {
                armMotor1.setPower(0);
                armMotor2.setPower(0);
            }
            
            
     
            
            // Send telemetry
            telemetry.addData("armPosition1", armPosition1);
            telemetry.addData("armPosition2", armPosition2);
            telemetry.addData("freezeTarget", armFreezeTarget);
            telemetry.update();
        }
    }
}
