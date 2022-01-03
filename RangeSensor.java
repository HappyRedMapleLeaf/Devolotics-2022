import edu.wpi.first.wpilibj.AnalogInput;

//Create the Analog Object
public AnalogInput sharp;

//Constuct a new instance
sharp = new AnalogInput(port);

//Create an accessor method
public double range = getDistance()
{
    return (Math.pow(sharp.getAverageVoltage(), -1.2045)) * 27.726;
}

 while (opModeIsActive()) {
       telemetry.addData("Range:", range);
 }
