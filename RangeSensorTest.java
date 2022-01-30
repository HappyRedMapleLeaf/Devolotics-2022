import com.qualcomm.robotcore.hardware.AnalogInput;

AnalogChannel rangeSensor = new AnalogChannel(0); 

...

public double range(){
    double sensorVoltage = rangeSensor.getVoltage();
    return sensorVoltage;
}

 waitForStart();
 while (opModeIsActive()) {
       telemetry.addData("Range:", sensorVoltage);
 }
