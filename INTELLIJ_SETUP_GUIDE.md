# IntelliJ IDEA Setup Guide

## Quick Start - Open Both Projects

### Method 1: Open Each Scenario Separately (Recommended)

#### Scenario 1:
1. Open IntelliJ IDEA
2. **File → Open** (or **Open** from welcome screen)
3. Navigate to: `CW/Scenario1`
4. Click **OK**
5. IntelliJ should auto-detect Java files
6. If `src` folder is not marked as Sources Root:
   - Right-click `src` folder
   - **Mark Directory as → Sources Root**
7. Run: Right-click `SubmissionSystemSimulation.java` → **Run 'SubmissionSystemSimulation.main()'**

#### Scenario 2:
1. **File → New → Project from Existing Sources**
2. Navigate to: `CW/Scenario2`
3. Follow same steps as Scenario 1
4. Run: Right-click `HospitalSystemSimulation.java` → **Run 'HospitalSystemSimulation.main()'**

---

### Method 2: Open Parent Folder (Both at Once)

1. **File → Open**
2. Navigate to: `CW` folder (parent folder containing both scenarios)
3. IntelliJ will detect both projects
4. You'll see both `Scenario1` and `Scenario2` folders
5. Mark `src` folders as Sources Root in each
6. Run each simulation separately

---

## Project Structure in IntelliJ

After opening, you should see:

```
CW/
├── Scenario1/
│   └── src/
│       ├── SubmissionStats.java
│       ├── Student.java
│       ├── NewSubmissionSystem.java
│       └── SubmissionSystemSimulation.java
│
└── Scenario2/
    └── src/
        ├── Speciality.java
        ├── Patient.java
        ├── PatientGenerator.java
        ├── Shift.java
        ├── Consultant.java
        ├── ShiftManager.java
        ├── HospitalSystem.java
        └── HospitalSystemSimulation.java
```

---

## Troubleshooting

### Issue: "Cannot resolve symbol" errors
**Solution:**
- Right-click `src` folder → **Mark Directory as → Sources Root**
- **File → Invalidate Caches / Restart**

### Issue: "Main class not found"
**Solution:**
- Right-click the main class file → **Run 'ClassName.main()'**
- Or: **Run → Edit Configurations** → Add new configuration → Set main class

### Issue: Code doesn't compile
**Solution:**
- **File → Project Structure** → Check Java SDK is set
- **Build → Rebuild Project**

### Issue: Output is too fast/too slow
**Solution:**
- Edit the simulation files to adjust:
  - Scenario 1: Change `numOfStudents` in `SubmissionSystemSimulation.java`
  - Scenario 2: Change `SIMULATION_DURATION_MS` in `HospitalSystemSimulation.java`

---

## Running the Code

### Scenario 1: Submission System
1. Open `SubmissionSystemSimulation.java`
2. Click green ▶️ button next to `main()` method
3. Or: Right-click file → **Run 'SubmissionSystemSimulation.main()'**
4. Watch the output in the console

**Expected Output:**
- Processing messages for each student
- Final statistics showing:
  - Total students processed
  - Successful/Failed submissions
  - Total time taken
  - Success rate

### Scenario 2: Hospital System
1. Open `HospitalSystemSimulation.java`
2. Click green ▶️ button next to `main()` method
3. Or: Right-click file → **Run 'HospitalSystemSimulation.main()'**
4. Watch the output in the console

**Expected Output:**
- Patient arrivals
- Consultant treatments
- Shift changes
- Queue status updates
- Final statistics

**To Stop:** Press `Ctrl+C` or click stop button in IntelliJ

---

## Testing Different Configurations

### Scenario 1 - Test Different Student Counts:
Edit `SubmissionSystemSimulation.java`:
```java
int numOfStudents = 5000;   // Test with 5000
int numOfStudents = 10000;  // Test with 10000
int numOfStudents = 100000; // Test with 100000
```

### Scenario 2 - Test Different Durations:
Edit `HospitalSystemSimulation.java`:
```java
private static final long SIMULATION_DURATION_MS = 30 * 1000; // 30 seconds
private static final long SIMULATION_DURATION_MS = 120 * 1000; // 2 minutes
```

---

## Code Navigation Tips

### View All Classes:
- **View → Tool Windows → Project** (or press `Alt+1`)
- Expand `src` folder to see all classes

### Navigate Between Classes:
- **Ctrl+Click** on class name to jump to definition
- **Ctrl+B** to go to declaration
- **Alt+Left/Right** to navigate back/forward

### Search in Code:
- **Ctrl+Shift+F** to search in all files
- **Ctrl+F** to search in current file

---

## Preparing for Submission

### Before Submitting:
1. ✅ Test both scenarios run without errors
2. ✅ Verify output is clear and informative
3. ✅ Check all files are in correct folders
4. ✅ Ensure code compiles without warnings
5. ✅ Test with different configurations

### Creating ZIP File:
1. Select both `Scenario1` and `Scenario2` folders
2. Right-click → **Compress** (Mac) or **Send to → Compressed folder** (Windows)
3. Rename to: `yourSurname_CW.zip`
4. Verify ZIP contains both scenarios

---

## Need Help?

- Check `VIVA_PREPARATION_GUIDE.md` for explanations
- Review code comments (they explain design decisions)
- Read `README.md` files in each scenario folder

---

**You're all set! Open IntelliJ and start exploring your code! 🚀**

