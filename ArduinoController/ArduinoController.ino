// DEFINES
// Protocol names
#define POT_NAME "POTENTIOMETER"
#define UPPER_BUTTON_NAME "UPPER_BUTTON"
#define LOWER_BUTTON_NAME "LOWER_BUTTON"

// Timers
#define INPUT_READ_DELAY_TIME 10

// Pins configuration
const int potPin = A1; // Potentiometer's analog input pin
const int upperBtnPin = 3; // Button's digital input pin
const int lowerBtnPin = 2; // Button's digital input pin


// VARIABLES
// Input state
int lastPotPos = 0;
int lastUpperBtnState = 0;
int lastLowerBtnState = 0;


// FUNCTOINS
void initLastInputsState() {
  lastPotPos = -1;
  lastUpperBtnState = digitalRead(upperBtnPin);
  lastLowerBtnState = digitalRead(lowerBtnPin);
}

void setup() {
  Serial.begin(9600);
  
  pinMode(upperBtnPin, INPUT_PULLUP);
  pinMode(lowerBtnPin, INPUT_PULLUP);

  initLastInputsState();
}

void loop() {
  // Read current input
  int currPotPos = analogRead(potPin);
  int currUpperBtnState = digitalRead(upperBtnPin);
  int currLowerBtnState = digitalRead(lowerBtnPin);
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

  // Upper Button
  if (currUpperBtnState != lastUpperBtnState)
  {
    tempMsg = UPPER_BUTTON_NAME;
    tempMsg += ":";
    tempMsg += currUpperBtnState;
    
    Serial.println(tempMsg);
    lastUpperBtnState = currUpperBtnState;
  }

  // Lower Button
  if (currLowerBtnState != lastLowerBtnState)
  {
    tempMsg = LOWER_BUTTON_NAME;
    tempMsg += ":";
    tempMsg += currLowerBtnState;
    
    Serial.println(tempMsg);
    lastLowerBtnState = currLowerBtnState;
  }

  delay(INPUT_READ_DELAY_TIME);
}
