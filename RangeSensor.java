public class SensorMRRangeSensor extends LinearOpMode {

    ModernRoboticsI2cRangeSensor rangeSensor; // initialize

    @Override public void runOpMode() {

        // get a reference to compass
        rangeSensor = hardwareMap.get(ModernRoboticsI2cRangeSensor.class, "range_sensor");

   
        waitForStart();
        
        while (robot.rangeSensor.getDistance(DistanceUnit.INCH) > )//distance from shipping element when driving along wall
        {
            telemetry.addData("cm", "%.2f cm", robot.rangeSensor.getDistance(DistanceUnit.INCH));
            telemetry.update();// prints data
        }
//         while (opModeIsActive()) {
//             telemetry.addData("raw ultrasonic", rangeSensor.rawUltrasonic()); 
//             telemetry.addData("raw optical", rangeSensor.rawOptical());
//             telemetry.addData("cm optical", "%.2f cm", rangeSensor.cmOptical());
//             telemetry.addData("cm", "%.2f cm", rangeSensor.getDistance(DistanceUnit.CM)); // probably will only need this
//             telemetry.update();
            
//         }
    }
}
