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
		int armTarget = -1000; //0 is on ground, positive is higher. -1000 means no target.
		int armMin = 0; //arm must be on ground before 
		int armMax = 1000; //tbd
		int armFreezeTarget = 0; //where the arm wants to stay stationary

        /* Initialize the hardware variables.
           The init() method of the hardware class does all the work here */
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "Hello Driver");
        telemetry.update();
        
        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        
        leftDrive.setDirection(DcMotor.Direction.REVERSE); // reversing left motor because robot dumdum
        robot.armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
		
        leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

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
			
			//arm stuff, manual movement overrides preset
			double armTargetPower = 0;
			int armPosition = robot.armMotor.getCurrentPosition() //reduces clutter
			
			//arm manual movement
			if gamepad1.left_bumper { //arm manual movement
				//down
				armTargetPower = -0.5;
				armFreezeTarget = -1000;
				armTarget = -1000;
			else if gamepad1.right_bumper {
				//up
				armTargetPower = 0.5;
				armFreezeTarget = -1000;
				armTarget = -1000;
			} else {
				if gamepad1.x {
					armTarget = 300; //tbd
				}
				if gamepad1.y {
					armTarget = 600; //tbd
				}
				if armPosition > armTarget + 10 { //arm too high, giving some tolerance
					armTargetPower = -0.5;
					armFreezeTarget = -1000;
				} else if armPosition < armTarget - 10 { //arm too low
					armTargetPower = 0.5; //arm on target
					armFreezeTarget = -1000;
				} else {
					armTargetPower = 0;
					//kinda complex: armfreezetarget is only set for the first time that targetpower is 0
					//this is done by setting freezetarget to -1000 whenever a non-zero target power is set
					if armFreezeTarget == -1000 {
						armFreezeTarget = armPosition;
					}
				}
			}
			
			//keeping arm stable
				
			if armPosition >= armMax {
				//if too high, let it drop. if too low, raise it back
				//use armmax as "freeze target"
				if armPosition >= armMax {
					robot.armMotor.setPower(0);
				} else if armPosition < armMax {
					robot.armMotor.setPower(0.1);
				}
			} else if armPosition <= armMin {
				//stop the motor. the arm is on the ground.
				robot.armMotor.setPower(0);
					
			} else if armTargetPower == 0 {
				//if too high, push it down. if too low, raise it back
				//use freezetarget
				if armPosition > armFreezeTarget {
					robot.armMotor.setPower(-0.05);
				} else if armPosition < armFreezeTarget {
					robot.armMotor.setPower(0.1);
				} else {
					//still apply some force to counter gravity
					robot.armMotor.setPower(0.05);
				}
					
			} else {
				robot.armMotor.setPower(armTargetPower); //if none of the weird conditions are true, set the power
			}
			
			// Send telemetry message to show armMotor;
			telemetry.addData("Say", robot.armMotor.getCurrentPosition());
			telemetry.update();
			
			//there was a delay here before but i think its useless lol
        }
    }
}
