package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="ProvsTeleop", group="Devolotics")

public class Nengjiabeingdumb extends LinearOpMode {
    
	public DcMotor leftDrive;
	public DcMotor rightDrive;
	public DcMotor armMotor;
	public DcMotor duckMotor;
	public DcMotor intakeMotor;

	@Override
	public void runOpMode() {
	leftDrive = hardwareMap.dcMotor.get("leftDrive");
	rightDrive = hardwareMap.dcMotor.get("rightDrive");
	armMotor = hardwareMap.dcMotor.get("armMotor");
	duckMotor = hardwareMap.dcMotor.get("duckMotor");
	intakeMotor = hardwareMap.dcMotor.get("intake");

	telemetry.addData("Hello", "Nengjia the carry");
	telemetry.update();
	waitForStart();
	telemetry.addData("Status:", "Getting assasinated by robot");
	telemetry.update();

	//motor settings
	rightDrive.setDirection(DcMotor.Direction.REVERSE);
	armMotor.setDirection(DcMotor.Direction.REVERSE);
	armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
	armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
	armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
	leftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
	rightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
	//leftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
	//rightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

	int armMin = 0;
	int armMax = 420;
	int armTarget = -1000; //where the arm wants to stay stationary

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
		    } else if (gamepad1.right_trigger > 0) {
			intakeMotor.setPower(-0.7);
		    }
		    intakeMotor.setPower(gamepad1.right_trigger - gamepad1.left_trigger); //left is out, right is in

		    //duck motor, only need one direction
		    if ( gamepad1.dpad_left || gamepad1.dpad_right) {
			duckMotor.setPower(-0.4);
		    } else if ( gamepad1.dpad_down || gamepad1.dpad_up ) {
			duckMotor.setPower(-1.0);
		    } else {
			duckMotor.setPower(0.0);
		    }


		    //arm movement
		    int armPosition = armMotor.getCurrentPosition();
		    double armPower = 0;

		    if (gamepad1.left_bumper) {             //down
			    armTarget = -1000;
			    if (armPosition < armMin + 100) {
					armPower = 0; //if almost too low then slow down
				} else {
					armPower = -0.2; //usual speed
				}
			} else if (gamepad1.right_bumper) {     //up
				armTarget = -1000;
				if (armPosition > armMax) {
					armPower = 0; //if too high then stop
				} else if (armPosition > armMax - 100) {
					armPower = 0.15; //if almost too high slow down
				} else {
					armPower = 0.3; //usual speed
				}
			} else {
				if (armTarget == -1000) {
					armTarget = armPosition;
				}
				if (armPosition >= armTarget) {
					armPower = 0; //stop the arm if it's too high, and let it fall
				} else {
					armPower = 0.12; //slowly raise the arm if it's too low
				}
			}

			armMotor.setPower(armPower);

		    // Send telemetry
		    telemetry.addData("armPosition", armPosition);
		    telemetry.addData("armTarget", armTarget);
		    telemetry.update();
		}
	}
}
