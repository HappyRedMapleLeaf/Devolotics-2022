import edu.wpi.first.wpilibj.AnalogInput;

//Create the Analog Object
public AnalogInput sharp;
public DeviceMapping(java.lang.Class<DEVICE_TYPE> deviceTypeClass) //not sure what device_type is
//Constuct a new instance
// sharp = new AnalogInput(port); // still have to figure this out

rangeSensor = HardwareMap.DeviceMapping<DEVICE_TYPE extends HardwareDevice>  // this - idk if this works
//Create an accessor method
public double range = getDistance()
{
    return (Math.pow(sharp.getAverageVoltage(), -1.2045)) * 27.726; // The accessor method will output the range in cm.
}

 while (opModeIsActive()) {
       telemetry.addData("Range:", range);
 }
