import edu.wpi.first.wpilibj.AnalogInput;

//Create the Analog Object
public AnalogInput sharp;

//Constuct a new instance
// sharp = new AnalogInput(port); // still have to figure this out
rangeSensor = hardwareMap.DeviceMapping("rangeSensor") // this - idk if this works
//Create an accessor method
public double range = getDistance()
{
    return (Math.pow(sharp.getAverageVoltage(), -1.2045)) * 27.726; // The accessor method will output the range in cm.
}

 while (opModeIsActive()) {
       telemetry.addData("Range:", range);
 }
