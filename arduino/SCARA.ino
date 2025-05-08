#include <AccelStepper.h>
#include <Servo.h>

// Moba tools.h

#define limitSwitch1 11
#define limitSwitch2 10   
#define limitSwitch3 9    
#define limitSwitch4 A0

const byte enablePin = 8; 

Servo gripperServo; 

const long HOMING_FEEDRATE  = 500;   
const int  HOMING_BACKOFF   = 100;  
const long DEFAULT_MAXSPEED = 1000;  
const long DEFAULT_ACCEL    = 500;  

const long SOFT_LIMIT_POS   = 0;

AccelStepper stepper1(AccelStepper::DRIVER, 2, 5);
AccelStepper stepper2(AccelStepper::DRIVER, 3, 6);
AccelStepper stepperZ(AccelStepper::DRIVER, 4, 7);  
AccelStepper stepper3(AccelStepper::DRIVER, 12, 13);
bool isHomedZ = false;    
bool isHomedStepper3 = false;        

long joint1   = 0;
long joint2   = 0;
long joint3   = 0; 
long zAxis    = 0;
long gripper  = 0;
long userSpeed = 0;
long userAccel = 0;

String inputBuffer;

void setup() {
  Serial.begin(115200);

  pinMode(limitSwitch1, INPUT_PULLUP);
  pinMode(limitSwitch2, INPUT_PULLUP);
  pinMode(limitSwitch3, INPUT_PULLUP);
  pinMode(limitSwitch4, INPUT_PULLUP);

  pinMode(enablePin, OUTPUT);
  digitalWrite(enablePin, LOW);  

  stepper1.setMaxSpeed(DEFAULT_MAXSPEED);
  stepper2.setMaxSpeed(DEFAULT_MAXSPEED);
  stepperZ.setMaxSpeed(DEFAULT_MAXSPEED);  
  stepper3.setMaxSpeed(DEFAULT_MAXSPEED); 
  stepper1.setAcceleration(DEFAULT_ACCEL);
  stepper2.setAcceleration(DEFAULT_ACCEL);
  stepperZ.setAcceleration(DEFAULT_ACCEL);  
  stepper3.setAcceleration(DEFAULT_ACCEL);

  inputBuffer.reserve(150);
  gripperServo.attach(A3, 600, 2500);
  Serial.println("Stepper Motor Controller Ready - starting homing");

  delay(500);
  homeZ();    
  delay(500);
  homeStepper3(); 
  delay(500);   
  homeGripper();            
}

void loop() {
  checkSerial();
  stepper1.run();
  stepper2.run();
  stepperZ.run();  
  stepper3.run(); 
}

void checkSerial() {
  while (Serial.available() > 0) {
    char inChar = (char)Serial.read();
    if (inChar == '\n') {
      processCommand(inputBuffer);
      inputBuffer = "";
    } else {
      inputBuffer += inChar;
    }
  }
}

bool withinSoftLimit(long target) {
  return target >= SOFT_LIMIT_POS;
}

void processCommand(const String &cmd) {
  if (cmd.equalsIgnoreCase("HOME")) {
    Serial.println("Executing homing for every motor");
    homeZ(); homeStepper3();
    return;
  }
  if (cmd.startsWith("HOME:")) {
    String target = cmd.substring(5);

    if (target == "Z") {
      homeZ();
      Serial.println("MOVE_SUCCESS");
      return;
    } else if (target == "Joint3") {
      homeStepper3();
      Serial.println("MOVE_SUCCESS");
      return;
    } else if (target == "Joint1" || target == "Joint2") {
      Serial.print("Homing not implemented for: ");
      Serial.println(target);
      Serial.println("MOVE_FAILED");
      return;
    } else if(target == "Gripper"){
      homeGripper();
      Serial.println("MOVE_SUCCESS");
      return;
    }
    else {
      Serial.print("Unknown HOME target: ");
      Serial.println(target);
      Serial.println("MOVE_FAILED");
      return;
    }
  }
  bool success = true;
  if (cmd.startsWith("Joint1:")) {
    Serial.println("Received Joint1");
    long steps, speed, accel; parseParams(cmd.substring(7), steps, speed, accel);
    stepper1.setMaxSpeed(speed); stepper1.setAcceleration(accel);
    stepper1.move(steps); stepper1.runToPosition();
  } else if (cmd.startsWith("Joint2:")) {
    Serial.println("Received Joint2");
    long steps, speed, accel; parseParams(cmd.substring(7), steps, speed, accel);
    stepper2.setMaxSpeed(speed); stepper2.setAcceleration(accel);
    stepper2.move(steps); stepper2.runToPosition();
  } else if (cmd.startsWith("Z:")) {
    Serial.println("Received Z");
    long steps, speed, accel; parseParams(cmd.substring(2), steps, speed, accel);
    if (!isHomedZ) { Serial.println("MOVE_FAILED"); return; }
    stepperZ.setMaxSpeed(speed); stepperZ.setAcceleration(accel);
    //success = issueMove(stepperZ, steps);
    stepperZ.move(steps); stepperZ.runSpeedToPosition();
  } else if (cmd.startsWith("Joint3:")) {
    Serial.println("Received Joint3");
    long steps, speed, accel; parseParams(cmd.substring(7), steps, speed, accel);
    if (!isHomedStepper3) { Serial.println("MOVE_FAILED"); return; }
    stepper3.setMaxSpeed(speed); stepper3.setAcceleration(accel);
    success = issueMove(stepper3, steps);
  } 
  else if (cmd.startsWith("Gripper:")) {
    int angle = cmd.substring(8).toInt();
    if (angle < 0 || angle > 180) {
      Serial.println("MOVE_FAILED");
      return;
    }
    gripperServo.write(angle);
    Serial.print("Moved gripper to angle: ");
    Serial.println(angle);
    Serial.println("MOVE_SUCCESS");
  return;
}

  else if (cmd.startsWith("CMD:")) {
    long v1,v2,v3,v4,v5,v6,v7; char buf[64]; cmd.substring(4).toCharArray(buf,64);
    sscanf(buf, "%ld,%ld,%ld,%ld,%ld,%ld,%ld", &v1,&v2,&v3,&v4,&v5,&v6,&v7);
    joint1=v1; joint2=v2; joint3=v3; zAxis=v4; gripper = v5; userSpeed=v6; userAccel=v7;
    gripper = constrain(gripper, 0, 180);
    gripperServo.write(gripper);
    Serial.print("Moved gripper to angle: ");
    Serial.println(gripper);
    stepper1.setMaxSpeed(userSpeed);
    stepper1.setAcceleration(userAccel);
    stepper2.setMaxSpeed(userSpeed);
    stepper2.setAcceleration(userAccel);
    stepper3.setMaxSpeed(userSpeed);
    stepper3.setAcceleration(userAccel);
    stepperZ.setMaxSpeed(userSpeed);
    stepperZ.setAcceleration(userAccel);
    success = executeMovement(); 
    }
    Serial.println(success?"MOVE_SUCCESS":"MOVE_FAILED");
}

void parseParams(const String &s, long &steps, long &speed, long &accel) {
  steps = 0; speed = DEFAULT_MAXSPEED; accel = DEFAULT_ACCEL;
  int idx = s.indexOf(','); if (idx>0) { steps=s.substring(0,idx).toInt();
    int idx2 = s.indexOf(',', idx+1); if(idx2>0) {
      speed = s.substring(idx+1,idx2).toInt();
      accel = s.substring(idx2+1).toInt();
    }
  }
  Serial.print("Received: ");
  Serial.print("Initial String: " + s);
  Serial.print(" | Steps: " + String(steps));
  Serial.print(" | Speed: " + String(speed));
  Serial.print(" | Acceleration: " + String(accel));
  Serial.println("");

}

bool issueMove(AccelStepper &st, long steps) {
  long target = st.currentPosition()+steps;
  if (!withinSoftLimit(target)) {  Serial.println("Cannot move, homing exceeded"); return false;}
  st.move(steps);
  st.runToPosition();
  return true;
}

bool executeMovement() {
  bool any=false;
  if (joint1) { Serial.println("Executing joint1"); stepper1.move(joint1); stepper1.runToPosition(); any=true; }
  if (joint2) { Serial.println("Executing joint2"); stepper2.move(joint2); stepper2.runToPosition(); any=true; }
  if (joint3) { Serial.println("Executing joint3"); if(!isHomedStepper3||!issueMove(stepper3,joint3)) return false; any=true; }
  if (zAxis)  { Serial.println("Executing zAxis"); if(!isHomedZ||!issueMove(stepperZ,zAxis)) return false; any=true; }
  return any;
}

void homeZ() {
  Serial.println("Homing Z");
  stepperZ.setMaxSpeed(HOMING_FEEDRATE);
  stepperZ.setAcceleration(HOMING_FEEDRATE*2);
  stepperZ.setSpeed(-HOMING_FEEDRATE);
  while(digitalRead(limitSwitch1)!=1) stepperZ.runSpeed();
  stepperZ.stop(); stepperZ.setCurrentPosition(0);
  stepperZ.move(HOMING_BACKOFF);
  while(stepperZ.distanceToGo()) stepperZ.run();
  stepperZ.setCurrentPosition(0); isHomedZ=true;
  stepperZ.setMaxSpeed(DEFAULT_MAXSPEED);
  stepperZ.setAcceleration(DEFAULT_ACCEL);
  Serial.println("Z homed successfully");
}

void homeStepper3() {
  Serial.println("Homing Joint3...");
  stepper3.setMaxSpeed(HOMING_FEEDRATE);
  stepper3.setAcceleration(HOMING_FEEDRATE*2);
  stepper3.setSpeed(-HOMING_FEEDRATE);
  while(digitalRead(limitSwitch3)!=1) stepper3.runSpeed();
  stepper3.stop(); stepper3.setCurrentPosition(0);
  stepper3.move(HOMING_BACKOFF);
  while(stepper3.distanceToGo()) stepper3.run();
  stepper3.setCurrentPosition(0); isHomedStepper3=true;
  stepper3.setMaxSpeed(DEFAULT_MAXSPEED);
  stepper3.setAcceleration(DEFAULT_ACCEL);
  Serial.println("Joint3 was homed successfully");
}

void homeGripper(){
  Serial.println("Homing Gripper...");
  gripperServo.write(180);
  Serial.println("Gripper was homed successfully");
}