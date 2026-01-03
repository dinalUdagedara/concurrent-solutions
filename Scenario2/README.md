# Scenario 2: Hospital A&E System

## Overview
A concurrent Java program simulating a hospital A&E department with continuous patient arrivals, multiple consultants, and automatic shift management.

## Project Structure
```
Scenario2/
├── src/
│   ├── Speciality.java                # Medical speciality enum
│   ├── Patient.java                   # Patient representation
│   ├── PatientGenerator.java          # Continuous patient generation
│   ├── Shift.java                     # Shift type enum
│   ├── Consultant.java                # Consultant thread
│   ├── ShiftManager.java              # Automatic shift management
│   ├── HospitalSystem.java            # Main system coordinator
│   └── HospitalSystemSimulation.java  # Main entry point
└── README.md
```

## How to Run in IntelliJ IDEA

### Option 1: Import as Project
1. Open IntelliJ IDEA
2. File → Open → Select `Scenario2` folder
3. Mark `src` folder as Sources Root (Right-click → Mark Directory as → Sources Root)
4. Run `HospitalSystemSimulation.java`

### Option 2: Create New Project
1. File → New → Project → Java
2. Project name: `Scenario2`
3. Copy all `.java` files to `src` folder
4. Run `HospitalSystemSimulation.java`

## Configuration
Edit `HospitalSystemSimulation.java` to change:
- `SIMULATION_DURATION_MS`: How long to run (default: 60 seconds)

Edit `ShiftManager.java` to change:
- `SHIFT_DURATION_MS`: Shift duration (default: 12 seconds = 12 hours)

## Key Features
- ✅ Continuous patient arrivals (random intervals)
- ✅ Multiple consultants working concurrently
- ✅ Automatic shift rotation (Day/Night)
- ✅ Thread-safe queue management (BlockingQueue)
- ✅ Speciality-based patient routing
- ✅ Smooth shift handover

## Concurrency Mechanisms Used
1. **BlockingQueue** (LinkedBlockingQueue) - Thread-safe queues
2. **Producer-Consumer Pattern** - Patient generation and treatment
3. **AtomicReference** - Shift state management
4. **wait()/notify()** - Shift transition coordination

