package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Pushbot: Teleop POV", group="Pushbot")

public class PushBotinear extends LinearOpMode {

    HardwarePushbot robot = new HardwarePushbot();   // Use a Pushbot's hardware

    @Override
    public void runOpMode() {
		double intakeStopRuntime = 0; //runtime when intake should stop moving

        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "Hello Driver");
        telemetry.update();
        
        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        
        leftDrive.setDirection(DcMotor.Direction.REVERSE); // reversing left motor because robot dumdum
        robot.armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
			
            // apparently bay wants some weird controls but he asked for it so.....
			// left controls all movement, right controls turning only
			
			if Math.abs(gamepad1.right_stick_x) > 0.05 { //right stick is touched (some tolerance is given)
				
				// only turn
				//one wheel, other wheel is stationary
				//robot.leftDrive.setPower(Math.max(gamepad1.right_stick_x, 0));
				//robot.rightDrive.setPower(Math.max(-gamepad1.right_stick_x, 0));
				
				//both wheels
				robot.leftDrive.setPower(gamepad1.right_stick_x);
				robot.rightDrive.setPower(-gamepad1.right_stick_x);
				
			} else { //right stick is untouched
				
				if Math.abs(gamepad1.left_stick_x) > 0.1 { //left stick is moved left/right past a certain amount,
														   //stop all movement and turn cuz that's what bay wants
					//one wheel, other wheel is stationary
					//robot.leftDrive.setPower(Math.max(gamepad1.right_stick_x, 0));
					//robot.rightDrive.setPower(Math.max(-gamepad1.right_stick_x, 0));

					//both wheels
					robot.leftDrive.setPower(gamepad1.right_stick_x);
					robot.rightDrive.setPower(-gamepad1.right_stick_x);
				} else {
					robot.leftDrive.setPower(-gamepad1.right_stick_y);
					robot.rightDrive.setPower(-gamepad1.right_stick_y);
				}
				
				/*
				//if bay actually doesn't want left stick sideways movement to stop other movement, then uncomment the code below
				double drive = -gamepad1.left_stick_y;
				double turn  =  gamepad1.left_stick_x;
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
				*/
			}
			
			// duck stuff, commented out because otherwise dumb errors might pop up and make bay go to the washroom
			/*
			if gamepad1.dpad_up || gamepad1.dpad_left {
				robot.duckMotor.setPower(-1.0);
			} else if gamepad1.dpad_down || gamepad1.dpad_right {
				robot.duckMotor.setPower(1.0);
			} else {
				robot.duckMotor.setPower(0.0);
			}
			*/
			
			//intake motor
			if getRuntime() <= intakeStopRuntime { //if no preset is running
				intakeMotor.setPower(0.0);
				
				if gamepad1.a { //intake in for 1s
					intakeStopRuntime = getRuntime() + 1;
					intakeMotor.setPower(1.0);
				}
				if gamepad1.b { //intake out for 1s
					intakeStopRuntime = getRuntime() + 1;
					intakeMotor.setPower(-1.0);
				}
				
				//manual intake control, only works if preset is done
				robot.intakeMotor.setPower(gamepad1.left_trigger - gamepad1.right_trigger); //idk the directions, left is out, right is in
			}
			
			//arm manual movement
			if gamepad1.left_bumper { //idk the directions for this either
				robot.armMotor.setPower(0.7);
			else if gamepad1.right_bumper {
				robot.armMotor.setPower(-0.7);
			} else {
				//lock in place
				robot.armMotor.setPower(0.0);
			}
			
            // Send telemetry message to show armMotor;
            telemetry.addData("Say", robot.armMotor.getCurrentPosition());
            telemetry.update();
			
			//there was a delay here before but i think its useless lol
        }
    }
}
