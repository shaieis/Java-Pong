// DEFINES
// Protocol names
#define POT_NAME "POT1"
#define BUTTON1_NAME "BTN1"
#define BUTTON2_NAME "BTN2"

// Timers
#define INPUT_READ_DELAY_TIME 10

// Pins configuration
const int potPin = A1; // Potentiometer's analog input pin
const int btn1Pin = 3; // Button's digital input pin
const int btn2Pin = 2; // Button's digital input pin


// VARIABLES
// Input state
int lastPotPos = 0;
int lastBtn1State = 0;
int lastBtn2State = 0;


// FUNCTOINS
void initLastInputsState() {
  lastPotPos = -1;
  lastBtn1State = digitalRead(btn1Pin);
  lastBtn2State = digitalRead(btn2Pin);
}

void setup() {
  Serial.begin(9600);
  
  pinMode(btn1Pin, INPUT_PULLUP);
  pinMode(btn2Pin, INPUT_PULLUP);

  initLastInputsState();
}

void loop() {
  // Read current input
  int currPotPos = analogRead(potPin);
  int currBtn1State = digitalRead(btn1Pin);
  int currBtn2State = digitalRead(btn2Pin);
  String tempMsg = "";

  // Trigger events if input has changed
  // Potentiometer
  int tempVal = currPotPos - lastPotPos;
  if (abs(tempVal) > 1)
  {
    tempMsg = POT_NAME;
    tempMsg += ":";
    tempMsg += currPotPos;
    
    Serial.println(tempMsg);
    lastPotPos = currPotPos;
  }

  // First Button
  if (currBtn1State != lastBtn1State)
  {
    tempMsg = BUTTON1_NAME;
    tempMsg += ":";
    tempMsg += !currBtn1State; // Becuase of pull-up, logic is reversed
    
    Serial.println(tempMsg);
    lastBtn1State = currBtn1State;
  }

  // Second Button
  if (currBtn2State != lastBtn2State)
  {
    tempMsg = BUTTON2_NAME;
    tempMsg += ":";
    tempMsg += !currBtn2State; // Becuase of pull-up, logic is reversed
    
    Serial.println(tempMsg);
    lastBtn2State = currBtn2State;
  }

  delay(INPUT_READ_DELAY_TIME);
}
