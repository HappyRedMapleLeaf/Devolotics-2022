package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="snesr!!1!", group="Devolotics")

public class SensorTest extends LinearOpMode {
    
    DistanceSensor rangeLeft;

    @Override
    public void runOpMode() {
        

        telemetry.addData("Hello", "Bay the Gamer");
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        telemetry.addData("Status:", "Gaming");
        telemetry.update();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            telemetry.addData("range", String.format("%.01f mm", rangeLeft.getDistance(DistanceUnit.MM)));
            
        }
    }
}
