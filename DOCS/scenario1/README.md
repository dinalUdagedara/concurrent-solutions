# Scenario 1: University Submission System

## Overview
A concurrent Java program that processes student exam submissions efficiently, handling 5,000 to 100,000+ simultaneous submissions.

## Project Structure
```
Scenario1/
├── src/
│   ├── SubmissionStats.java          # Thread-safe statistics tracking
│   ├── Student.java                   # Student representation
│   ├── NewSubmissionSystem.java       # Main concurrent processing system
│   └── SubmissionSystemSimulation.java # Main entry point
└── README.md
```

## How to Run in IntelliJ IDEA

### Option 1: Import as Project
1. Open IntelliJ IDEA
2. File → Open → Select `Scenario1` folder
3. Mark `src` folder as Sources Root (Right-click → Mark Directory as → Sources Root)
4. Run `SubmissionSystemSimulation.java`

### Option 2: Create New Project
1. File → New → Project → Java
2. Project name: `Scenario1`
3. Copy all `.java` files to `src` folder
4. Run `SubmissionSystemSimulation.java`

## Configuration
Edit `SubmissionSystemSimulation.java` to change:
- `numOfStudents`: Number of students (default: 10000)
- `poolSize`: Thread pool size (default: CPU cores * 2)

## Key Features
- ✅ True concurrent processing (each student is a separate task)
- ✅ Thread-safe statistics (AtomicInteger)
- ✅ Graceful exception handling
- ✅ Proper resource cleanup
- ✅ Scalable to 100,000+ students

## Concurrency Mechanisms Used
1. **ExecutorService** (Fixed Thread Pool) - Thread management
2. **AtomicInteger** - Thread-safe counters
3. **CountDownLatch** - Synchronization barrier

