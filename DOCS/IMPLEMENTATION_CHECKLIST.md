# Implementation Checklist

## Pre-Implementation
- [ ] Read coursework requirements thoroughly
- [ ] Understand concurrency concepts (threads, synchronization, thread-safety)
- [ ] Research Java concurrency mechanisms
- [ ] Set up IDE (NetBeans or agreed IDE)
- [ ] Create project structure

## Scenario 1: University Submission System

### Task 1: SubmissionStats Class (10 marks)
- [ ] Create SubmissionStats class
- [ ] Implement thread-safe counters (AtomicInteger or synchronized)
- [ ] Track successful submissions
- [ ] Track failed submissions
- [ ] Implement display method with:
  - [ ] Total time taken
  - [ ] Total students processed
  - [ ] Number of successful submissions
  - [ ] Number of failed submissions
  - [ ] Success rate percentage
- [ ] Test thread-safety with multiple threads
- [ ] Verify no race conditions

### Task 2: NewSubmissionSystem Class (20 marks)
- [ ] Create NewSubmissionSystem class
- [ ] Implement student/submission generation (5000-100000)
- [ ] Choose and implement ExecutorService (justify choice)
- [ ] Submit all student tasks concurrently
- [ ] Implement exception handling (graceful failures)
- [ ] Implement waiting mechanism (CountDownLatch/CompletableFuture)
- [ ] Measure total execution time
- [ ] Display individual submission results
- [ ] Test with 5000 students
- [ ] Test with 10000+ students
- [ ] Test with 100000 students (if feasible)
- [ ] Verify all submissions complete
- [ ] Verify no crashes on exceptions

## Scenario 2: Hospital A&E System

### Component 1: Continuous Patient Arrivals (10 marks)
- [ ] Create Patient class (ID, arrival time, speciality)
- [ ] Create Speciality enum (Paediatrician, Surgeon, Cardiologist)
- [ ] Implement PatientGenerator thread
- [ ] Random arrival intervals
- [ ] Random speciality assignment
- [ ] Add patients to appropriate queues
- [ ] Test continuous generation
- [ ] Verify thread-safety of queue operations

### Component 2: Automated Shift Management (10 marks)
- [ ] Create Shift enum (DAY, NIGHT)
- [ ] Implement shift tracking
- [ ] Create Consultant class/thread
- [ ] Implement day shift (3 consultants, 12 hours)
- [ ] Implement night shift (3 consultants, 12 hours)
- [ ] Implement automatic shift rotation
- [ ] Implement smooth handover (no lost patients)
- [ ] Test shift transitions
- [ ] Verify no patients lost during transition
- [ ] Verify consultants start/stop correctly

### Component 3: Concurrent Processing (10 marks)
- [ ] Create thread-safe queues (BlockingQueue per speciality)
- [ ] Implement consultant threads processing patients
- [ ] Ensure consultants only process their speciality
- [ ] Implement concurrent processing (multiple consultants simultaneously)
- [ ] Verify thread-safety of queue access
- [ ] Verify no data corruption
- [ ] Test with multiple consultants working
- [ ] Verify all patients are eventually processed

### Integration Testing
- [ ] Test full system (patients arriving + consultants working + shift changes)
- [ ] Test edge cases (empty queues, full queues, no patients)
- [ ] Test exception scenarios
- [ ] Verify system runs continuously
- [ ] Verify no deadlocks
- [ ] Verify no race conditions

## Code Quality
- [ ] Code is clean and readable
- [ ] Appropriate comments (not excessive)
- [ ] Proper class structure
- [ ] No hardcoded values (use constants)
- [ ] Proper exception handling
- [ ] Code runs without modification
- [ ] All required classes implemented
- [ ] Main methods for easy execution

## Vodcast Preparation (40 marks)

### Content Planning
- [ ] Outline script (NOT to read verbatim)
- [ ] Plan demonstrations for Scenario 1
- [ ] Plan demonstrations for Scenario 2
- [ ] Prepare technical explanations:
  - [ ] Why ExecutorService chosen
  - [ ] Why AtomicInteger chosen
  - [ ] Why CountDownLatch/CompletableFuture chosen
  - [ ] Why BlockingQueue chosen
  - [ ] Why separate queues per speciality
  - [ ] How thread-safety is ensured
  - [ ] How liveness is guaranteed
  - [ ] Safety properties discussion
  - [ ] Liveness properties discussion
  - [ ] Performance considerations
  - [ ] Why chosen mechanisms better than alternatives

### Technical Depth
- [ ] Explain CAS (Compare-And-Swap) operations
- [ ] Explain happens-before relationships
- [ ] Explain memory visibility
- [ ] Explain lock contention
- [ ] Explain producer-consumer pattern
- [ ] Explain thread pool benefits
- [ ] Go beyond surface-level explanations

### Recording
- [ ] Set up screen recording
- [ ] Ensure student visible on screen
- [ ] Practice explanations (don't read script)
- [ ] Record clear demonstrations
- [ ] Show appropriate outputs
- [ ] Keep to ~10 minutes
- [ ] Review recording for clarity
- [ ] Ensure technical depth is adequate

## Final Submission
- [ ] Package Scenario 1 code
- [ ] Package Scenario 2 code
- [ ] Create ZIP file: `surname_CW.zip`
- [ ] Verify ZIP contains all required files
- [ ] Verify code runs without modification
- [ ] Upload ZIP to Blackboard
- [ ] Upload vodcast to Blackboard
- [ ] Submit before 13:00, 7 January 2026

## Viva Preparation
- [ ] Understand every line of code
- [ ] Be able to explain design decisions
- [ ] Be able to explain concurrency mechanisms
- [ ] Be able to answer "why" questions
- [ ] Be able to discuss alternatives
- [ ] Be able to explain safety/liveness properties
- [ ] Practice explaining without looking at code

---

## Key Reminders

⚠️ **Vodcast Requirements:**
- Student MUST be visible on screen
- MUST NOT read from script
- ~10 minutes long
- Technical depth required (Level 6 standard)
- Failure to meet requirements = forfeit marks

⚠️ **Code Requirements:**
- Must run without modification
- Submit full project structure
- Both scenarios in ZIP file
- Thread-safe implementations
- Handle required scale

⚠️ **Late Submission:**
- Within 24 hours: -10 marks
- More than 24 hours: 0 marks (unless MC accepted)

⚠️ **Viva:**
- May be invited for viva
- Must attend if invited
- Failure to attend = fail grade
- Marks based on individual understanding

