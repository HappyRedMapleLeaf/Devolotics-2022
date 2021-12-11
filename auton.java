// actual program. Intial position: halfway between first and second ground tile
        driveToTarget(0.76, 0.56, 1.0, 0.4); //forwards to carousel, turn a bit because duck wheel will hit the metal thingy
        
        
        // carousel
        duckMotor.setPower(-0.5);
        sleep(3000);
        duckMotor.setPower(0.0);
        
        
        driveToTarget(-0.9, -0.83, 0.5, 0.4); //back to initial position
        
        driveToTarget(-2.51, -2.58, 1.0, 1.0); //backwards to around the middle
        
        driveToTarget(1.04, -1.04, 0.5, 0.5); //turn towards hub
        
        leftDrive.setPower(-0.5);
        rightDrive.setPower(-0.5);
        sleep(900);//2000
        leftDrive.setPower(0);
        rightDrive.setPower(0);
        
        driveToTarget(1.25, 1.25, 0.7, 0.7); //go towards hub
        
        // lift arm (i know, it could be done at the same time with driving, but a) lazy, and b) we have extra time for sure)
        armMotor.setPower(-0.5);
        //sleep(200); //level 1
        //sleep(450); //level 2
        sleep(950); //level 3
        armMotor.setPower(-0.1); //too lazy to do encoder stuff, so im just gonna uhm... apply a little power and hope it keeps it up lol
        
        driveToTarget(0.31, 0.31, 0.2, 0.2); //go towards hub even more
        
        // drop preload box
        intakeMotor.setPower(0.7);
        sleep(1000);
        intakeMotor.setPower(0.0);
        
        driveToTarget(-0.28, -0.28, 0.2, 0.2); //away from hub
        
        // drop arm
        armMotor.setPower(0.0);
        
        driveToTarget(-1.6, -1.6, 1.0, 1.0); //go to wall, then a bit more to square with wall
        
        driveToTarget(2.01, -0.03, 0.5, 0.1); //turn towards warehouse
        
        driveToTarget(4, 4, 0.81, 0.8); //go into warehouse
        
        intakeMotor.setPower(-0.9);
        sleep(1000);
        intakeMotor.setPower(0.0);
