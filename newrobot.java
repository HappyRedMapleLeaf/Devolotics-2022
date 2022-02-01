package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="ProvsTeleop", group="Devolotics")

public class Nengjiabeingdumb extends LinearOpMode {
    
	public DcMotor leftDrive;
	public DcMotor rightDrive;
	public DcMotor armMotor;
	public DcMotor intakeMotor;
	public Servo duckMotor;

	@Override
	public void runOpMode() {
	leftDrive = hardwareMap.dcMotor.get("leftDrive");
	rightDrive = hardwareMap.dcMotor.get("rightDrive");
	armMotor = hardwareMap.dcMotor.get("armMotor");
	intakeMotor = hardwareMap.dcMotor.get("intake");
	duckMotor = hardwareMap.get(Servo.class, "duckMotor");

	telemetry.addData("Hello", "Driver");
	telemetry.update();
	waitForStart();
	telemetry.addData("Status:", "Gaming");
	telemetry.update();

	//motor settings
	rightDrive.setDirection(DcMotor.Direction.REVERSE);
	armMotor.setDirection(DcMotor.Direction.REVERSE);
	armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
	armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
	armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
	leftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
	rightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

	int armMin = 0;
	int armMax = 420;
	int armTarget = -1000; //where the arm wants to stay stationary

	while (opModeIsActive()) {
		//drive
		if (Math.abs(gamepad1.right_stick_x) > 0.05) { //right stick is touched (some tolerance is given)
			//turn
			leftDrive.setPower(gamepad1.right_stick_x);
			rightDrive.setPower(-gamepad1.right_stick_x);
		} else { //right stick is untouched
			leftDrive.setPower(-gamepad1.left_stick_y);
			rightDrive.setPower(-gamepad1.left_stick_y);
		}

		//intake
		if (gamepad1.left_trigger > 0){
			intakeMotor.setPower(0.9);
		} else if (gamepad1.right_trigger > 0) {
			intakeMotor.setPower(-0.7);
		} else {
			intakeMotor.setPower(-0.7);
		}

		//duck
		if ( gamepad1.dpad_left || gamepad1.dpad_right) {
			duckMotor.setPosition(0.3);
		} else if ( gamepad1.dpad_down || gamepad1.dpad_up ) {
			duckMotor.setPosition(0.0);
		} else {
			duckMotor.setPosition(0.5);
		}

		//arm
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
