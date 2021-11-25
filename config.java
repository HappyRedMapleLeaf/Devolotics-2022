package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;


public class HardwarePushbot
{
    /* Public OpMode members. */
    public DcMotor  leftDrive;
    public DcMotor  rightDrive;
    public DcMotor armMotor;
    public DcMotor intakeMotor;
    //public DcMotor duckMotor;
    
    /* local OpMode members. */
    HardwareMap hwMap           =  null;
    private ElapsedTime period  = new ElapsedTime();


    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // Define and Initialize Motors
        leftDrive  = hwMap.get(DcMotor.class, "leftDrive");
        rightDrive = hwMap.get(DcMotor.class, "rightDrive");
        armMotor = hwMap.get(DcMotor.class, "armMotor");
        intakeMotor = hwMap.get(DcMotor.class, "intake");
        //duckMotor = hwMap.get(DcMotor.class,"duckMotor");
        
        leftDrive.setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark motors
        rightDrive.setDirection(DcMotor.Direction.REVERSE);

        // Set to FORWARD if using AndyMark motors
        int ArmTarget =0;
        // Set all motors to zero power
        leftDrive.setPower(0);
        rightDrive.setPower(0);
        //duckMotor.setPower(0);
        armMotor.setTargetPosition(0);

        
        
        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.
        leftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        intakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        
        
        
        

    }
 }

