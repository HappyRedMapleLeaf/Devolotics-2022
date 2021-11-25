package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Pushbot: Teleop- BAY MODE ", group="Pushbot")

public class NewTeleOp extends LinearOpMode {

    HardwarePushbot robot = new HardwarePushbot();   // Use a Pushbot's hardware

    @Override
    public void runOpMode() {
        int armMin = 20; //0
        int armMax = 440; //456
        int armFreezeTarget = 0; //where the arm wants to stay stationary

        /* Initialize the hardware variables.
           The init() method of the hardware class does all the work here */
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "Hello Bay the Gamer");
        telemetry.update();
        
        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        
        
        //robot.leftDrive.setDirection(DcMotor.Direction.REVERSE); // reversing left motor because robot dumdum
        robot.armMotor.setDirection(DcMotor.Direction.REVERSE);
        robot.armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        
        robot.leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        
        robot.leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
        robot.rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
        

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            
            // apparently bay wants some weird controls but he asked for it so.....
            // left controls all movement, right controls turning only
            
            if (Math.abs(gamepad1.right_stick_x) > 0.05) { //right stick is touched (some tolerance is given)
                
                // only turn
                //one wheel, other wheel is stationary
                //robot.leftDrive.setPower(Math.max(gamepad1.right_stick_x, 0));
                //robot.rightDrive.setPower(Math.max(-gamepad1.right_stick_x, 0));
                
                //both wheels
                robot.leftDrive.setPower(-gamepad1.right_stick_x);
                robot.rightDrive.setPower(gamepad1.right_stick_x);
                
            } else { //right stick is untouched
                
                //if bay actually doesn't want left stick sideways movement to stop other movement, then uncomment the code below
                double drive = gamepad1.left_stick_y;
                double turn  = gamepad1.left_stick_x;
                // Combine drive and turn for blended motion.
                double left  = drive - turn;
                double right = drive + turn;

                // Normalize the values so neither exceed +/- 1.0
                left = Math.min(left, 1.0);
                left = Math.max(left, -1.0);
                right = Math.min(right, 1.0);
                right = Math.max(right, -1.0);

                robot.leftDrive.setPower(left);
                robot.rightDrive.setPower(right);
                
            }
            
            //intake motor
            robot.intakeMotor.setPower(gamepad1.right_trigger - gamepad1.left_trigger); //left is out, right is in
            
            
            //arm stuff
            int armPosition = robot.armMotor.getCurrentPosition(); //reduces clutter
            double armTargetPower = 0;
            
            if (gamepad1.left_bumper) { //arm manual movement
                //down
                armTargetPower = -0.3;
                armFreezeTarget = -1000;
            } else if (gamepad1.right_bumper) {
                //up
                armTargetPower = 0.3;
                armFreezeTarget = -1000;
            } else {
                armTargetPower = 0;
            }
            
            if (armTargetPower > 0) { //trying to move up
                if (armPosition > armMax) {
                    armTargetPower = 0; //stop
                } else if (armPosition > armMax - 100) {
                    armTargetPower = 0.2; //slow down
                }
            } else if (armTargetPower < 0) { //trying to move down
                if (armPosition < armMin + 100) {
                    armTargetPower = 0; //slow down (really just stopping it)
                }
            } else {
                //kinda complex: armfreezetarget is only set for the first time that targetpower is 0
                //this is done by setting freezetarget to -1000 whenever a non-zero target power is set
                if (armFreezeTarget == -1000) {
                    armFreezeTarget = armPosition;
                }
            }
            
            if (gamepad1.a) {
                armFreezeTarget = 180; //tbd
            }
            if (gamepad1.x) {
                armFreezeTarget = 290; //tbd
            }
            
            if (armFreezeTarget != -1000) {
                if (armPosition > armFreezeTarget) {
                    robot.armMotor.setPower(0);
                } else if (armPosition < armFreezeTarget) {
                    robot.armMotor.setPower(0.12);
                }
            } else {
                robot.armMotor.setPower(armTargetPower);
            }
            
            // Send telemetry message to show armMotor;
            telemetry.addData("armPosition", armPosition);
            telemetry.addData("freezeTarget", armFreezeTarget);
            
            telemetry.update();
        }
    }
}
