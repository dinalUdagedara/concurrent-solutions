# Concurrent Programming Coursework - Planning Document

## Overview
- **Total Marks**: 100 (60% code, 40% vodcast)
- **Due Date**: Before 13:00, 7 January 2026
- **Qualifying Mark**: 30%
- **Deliverables**: 
  - Java source code (ZIP file: `surname_CW.zip`)
  - Vodcast (~10 minutes) demonstrating and explaining solutions

---

## Scenario 1: University Submission System (30 Marks)

### Problem Statement
- Handle 5,000 to 100,000+ simultaneous student submissions
- Current system: sequential processing causing 20-30 minute waits
- Need: Scalable, concurrent solution

### Task 1: SubmissionStats Class (10 marks)

#### Requirements:
- **Thread-safe statistics tracking**
- Track:
  - Successful submissions count
  - Failed submissions count
- **Thread-safe data structures** (consider: `AtomicInteger`, `ConcurrentHashMap`, `synchronized` blocks)
- **Display method** showing:
  - Total time taken
  - Total students processed
  - Number of successful submissions
  - Number of failed submissions
  - Success rate percentage

#### Design Considerations:
- Use `AtomicInteger` for counters (lock-free, high performance)
- Or use `synchronized` methods/blocks for simpler approach
- Consider `volatile` for visibility guarantees
- Thread-safe collection for storing individual results if needed

#### Key Concurrency Mechanisms to Consider:
- **AtomicInteger**: Lock-free increment operations
- **Synchronized methods/blocks**: Mutual exclusion
- **Volatile variables**: Visibility guarantees
- **ReentrantLock**: More flexible locking

---

### Task 2: NewSubmissionSystem Class (20 marks)

#### Requirements:
- Handle **5,000 to 100,000+ students** submitting simultaneously
- **Exception handling**: Graceful failure handling (don't crash entire system)
- **Wait for completion**: Use `CountDownLatch`, `ExecutorService.awaitTermination()`, or `CompletableFuture`
- **Measure execution time**: Start/end timestamps
- **Display individual results**: Success/failure messages per submission

#### Design Considerations:

**1. Thread Pool Management:**
- Use `ExecutorService` with appropriate pool size
- Consider `ThreadPoolExecutor` for fine-grained control
- Fixed thread pool vs. cached thread pool vs. work-stealing pool
- **Justification needed**: Why chosen pool type?

**2. Task Submission:**
- `ExecutorService.submit()` or `invokeAll()` for batch processing
- `CompletableFuture` for async processing with callbacks
- `ForkJoinPool` for divide-and-conquer approach

**3. Synchronization Points:**
- `CountDownLatch`: Wait for all submissions to complete
- `CyclicBarrier`: Synchronize multiple phases
- `Phaser`: Advanced synchronization for multiple phases
- `ExecutorService.awaitTermination()`: Wait for pool to finish

**4. Exception Handling:**
- Try-catch in each submission task
- Update failure statistics appropriately
- Don't let one failure crash the system
- Consider `Future.get()` exception handling

**5. Performance Considerations:**
- Minimize contention on shared resources
- Use lock-free data structures where possible
- Consider batching if needed

#### Architecture Options:

**Option A: ExecutorService with CountDownLatch**
```
- Create ExecutorService with N threads
- Submit all tasks
- Use CountDownLatch to wait for completion
- Shutdown executor
```

**Option B: CompletableFuture**
```
- Create list of CompletableFutures
- Use allOf() to wait for all
- Handle exceptions per future
```

**Option C: ForkJoinPool**
```
- Divide students into batches
- Process batches concurrently
- Join results
```

---

## Scenario 2: Hospital A&E System (30 Marks)

### Problem Statement
- Legacy system: Sequential processing, single-threaded
- Need: Multiple consultants working simultaneously
- 24/7 operation with shift rotations
- Random patient arrivals
- Speciality matching required

### Component 1: Continuous Patient Arrivals (10 marks)

#### Requirements:
- **Random arrivals** throughout day/night
- Handle arrivals **while consultants are working**
- Each patient requires **specific speciality**:
  - Paediatrician
  - Surgeon
  - Cardiologist

#### Design Considerations:

**1. Patient Generation:**
- Separate thread for generating patients
- Random intervals between arrivals
- Random speciality assignment
- Use `ScheduledExecutorService` or `Thread.sleep()` with random delays

**2. Patient Queue Management:**
- **Thread-safe queue** for each speciality
- Options:
  - `BlockingQueue` (ArrayBlockingQueue, LinkedBlockingQueue)
  - `ConcurrentLinkedQueue` with manual blocking
  - Priority queue if urgency matters
- Consider separate queues per speciality vs. single queue with filtering

**3. Patient Data Structure:**
- Patient class with: ID, arrival time, speciality, urgency (optional)

---

### Component 2: Automated Simulated Shift Management (10 marks)

#### Requirements:
- **Day shift**: 3 consultants (12-hour shift)
- **Night shift**: 3 consultants (12-hour shift)
- **Automatic rotation** between shifts
- **Smooth handover** without losing patients

#### Design Considerations:

**1. Shift Representation:**
- Enum for shift types (DAY, NIGHT)
- Track current shift
- Track time elapsed in current shift

**2. Consultant Management:**
- Consultant threads that start/stop based on shift
- Use `ScheduledExecutorService` for shift changes
- Or use time-based conditions in main loop

**3. Shift Transition:**
- **Smooth handover**: Ensure no patients lost during transition
- Options:
  - Overlap period (both shifts active briefly)
  - Complete current patients before transition
  - Transfer queue ownership
- Use synchronization to prevent race conditions during transition

**4. Consultant Threads:**
- Each consultant runs in separate thread
- Consultant checks if it's their shift
- Consultant processes patients from their speciality queue
- Consultant sleeps/wait when not on shift

#### Architecture Options:

**Option A: Time-based Shift Switching**
```
- Main loop checks current time
- Start/stop consultant threads based on shift
- Use synchronization for handover
```

**Option B: Scheduled Tasks**
```
- ScheduledExecutorService schedules shift changes
- Consultants check shift status before processing
- Atomic shift state management
```

---

### Component 3: Concurrent Processing (10 marks)

#### Requirements:
- **Multiple consultants work simultaneously**
- Each consultant treats **patients matching their speciality**
- **Thread-safe queue management**
- **No data corruption** during concurrent access

#### Design Considerations:

**1. Consultant Threads:**
- Each consultant is a separate thread
- Consultant continuously polls their speciality queue
- Use `BlockingQueue.take()` for blocking wait
- Process patient when available

**2. Thread Safety:**
- **Queue access**: Use `BlockingQueue` (inherently thread-safe)
- **Statistics tracking**: Similar to Scenario 1
- **Shift state**: Use `AtomicReference` or `volatile` with synchronization
- **Patient processing**: Ensure atomic operations

**3. Speciality Matching:**
- Each consultant has assigned speciality
- Consultant only takes from their speciality queue
- Or: Single queue with filtering (less efficient)

**4. Resource Sharing:**
- Shared queues (one per speciality)
- Shared statistics
- Shared shift state
- All must be thread-safe

#### Key Concurrency Mechanisms:

**1. BlockingQueue:**
- `ArrayBlockingQueue`: Fixed size, array-based
- `LinkedBlockingQueue`: Unbounded or bounded, linked-list based
- `PriorityBlockingQueue`: Priority-based ordering
- **Justification**: Why chosen type?

**2. Synchronization:**
- `synchronized` blocks for critical sections
- `ReentrantLock` for more control
- `ReadWriteLock` if read-heavy operations
- `Semaphore` for resource limiting

**3. Thread Coordination:**
- `wait()`/`notify()` for custom coordination
- `Condition` objects with locks
- `CountDownLatch` for initialization
- `CyclicBarrier` for phase synchronization

---

## Technical Deep Dive: Concurrency Mechanisms to Justify

### For Scenario 1:

**1. ExecutorService vs. Manual Thread Management:**
- **ExecutorService advantages**: 
  - Thread pool reuse (efficiency)
  - Built-in lifecycle management
  - Exception handling framework
  - Scalability
- **Justification needed**: Why ThreadPoolExecutor vs. ForkJoinPool vs. CachedThreadPool?

**2. CountDownLatch vs. CompletableFuture.allOf():**
- **CountDownLatch**: Lower-level, explicit countdown
- **CompletableFuture**: Higher-level, functional style, better exception handling
- **Justification**: Why chosen?

**3. AtomicInteger vs. Synchronized Counters:**
- **AtomicInteger**: Lock-free, CAS operations, better performance under contention
- **Synchronized**: Simpler, but potential contention bottleneck
- **Justification**: Performance vs. simplicity trade-off

### For Scenario 2:

**1. BlockingQueue vs. Manual Queue with Synchronization:**
- **BlockingQueue**: Built-in blocking, thread-safe, efficient
- **Manual**: More control but more error-prone
- **Justification**: Why ArrayBlockingQueue vs. LinkedBlockingQueue?

**2. Shift Management: AtomicReference vs. Volatile + Synchronized:**
- **AtomicReference**: Lock-free updates, good for simple state
- **Volatile + Synchronized**: More control for complex transitions
- **Justification**: Complexity vs. performance

**3. Producer-Consumer Pattern:**
- Patient generator (producer) → Queue → Consultants (consumers)
- Classic concurrency pattern
- **Justification**: Why this pattern fits the problem

---

## Safety and Liveness Properties

### Safety Properties (What must NOT happen):
1. **Race conditions**: Multiple threads modifying shared state simultaneously
2. **Data corruption**: Inconsistent state due to partial updates
3. **Lost updates**: One thread's update overwritten by another
4. **Deadlock**: Threads waiting for each other indefinitely

### Liveness Properties (What MUST happen):
1. **Progress**: System continues processing (no deadlock)
2. **Fairness**: All threads get opportunity to execute
3. **Bounded waiting**: Threads don't wait indefinitely
4. **Termination**: System eventually completes

### How to Ensure:

**Safety:**
- Use thread-safe data structures
- Synchronize access to shared mutable state
- Use atomic operations where possible
- Immutable objects where applicable

**Liveness:**
- Avoid nested locks (deadlock prevention)
- Use timeouts on blocking operations
- Ensure wait conditions can be satisfied
- Use fair locks if fairness required

---

## Implementation Plan

### Phase 1: Scenario 1 - Submission System

1. **Create SubmissionStats class**
   - AtomicInteger for success/failure counts
   - Thread-safe display method
   - Test with multiple threads

2. **Create Student/Submission class**
   - Represent student submission
   - Simulate submission processing (with potential failures)

3. **Create NewSubmissionSystem class**
   - Generate test students (5000-100000)
   - Create ExecutorService
   - Submit all tasks
   - Use CountDownLatch/CompletableFuture to wait
   - Handle exceptions
   - Display results

4. **Testing**
   - Test with various student counts
   - Test exception scenarios
   - Verify thread safety
   - Measure performance

### Phase 2: Scenario 2 - Hospital System

1. **Create Patient class**
   - ID, arrival time, speciality enum

2. **Create Consultant class/thread**
   - Speciality assignment
   - Process patients from queue
   - Shift awareness

3. **Create PatientGenerator thread**
   - Random arrival generation
   - Add to appropriate queue

4. **Create ShiftManager**
   - Track current shift
   - Manage consultant threads
   - Handle transitions

5. **Create HospitalSystem main class**
   - Initialize queues
   - Start patient generator
   - Start consultants
   - Manage shifts
   - Display statistics

6. **Testing**
   - Test patient arrivals
   - Test shift transitions
   - Test concurrent processing
   - Verify no data corruption
   - Test edge cases (empty queues, full queues)

### Phase 3: Vodcast Preparation

1. **Script Outline** (NOT to be read verbatim):
   - Introduction (30s)
   - Scenario 1 demonstration (2-3 min)
   - Scenario 1 technical explanation (3-4 min)
   - Scenario 2 demonstration (2-3 min)
   - Scenario 2 technical explanation (3-4 min)
   - Conclusion (30s)

2. **Key Points to Cover:**

**Scenario 1:**
- Why ExecutorService? (thread pool efficiency, lifecycle management)
- Why AtomicInteger? (lock-free, performance under contention)
- Why CountDownLatch/CompletableFuture? (coordination, waiting for completion)
- Safety: How thread-safety is ensured
- Liveness: How progress is guaranteed
- Performance: Scalability considerations

**Scenario 2:**
- Why BlockingQueue? (thread-safe, blocking operations, producer-consumer pattern)
- Why separate queues per speciality? (reduces contention, better performance)
- How shift management ensures smooth transitions (synchronization, state management)
- Safety: Queue thread-safety, shift transition atomicity
- Liveness: No deadlocks, consultants always making progress
- Producer-Consumer pattern justification

3. **Technical Depth Required:**
- Explain CAS (Compare-And-Swap) for AtomicInteger
- Explain happens-before relationships
- Explain memory visibility (volatile, synchronized)
- Explain lock contention and why it matters
- Explain why chosen mechanisms are better than alternatives

---

## Project Structure

```
CW/
├── Scenario1/
│   ├── src/
│   │   ├── SubmissionStats.java
│   │   ├── NewSubmissionSystem.java
│   │   ├── Student.java (optional helper class)
│   │   └── Main.java
│   └── README.md (if needed)
├── Scenario2/
│   ├── src/
│   │   ├── Patient.java
│   │   ├── Consultant.java
│   │   ├── PatientGenerator.java
│   │   ├── ShiftManager.java
│   │   ├── HospitalSystem.java
│   │   └── Speciality.java (enum)
│   └── README.md (if needed)
└── COURSEWORK_PLAN.md (this file)
```

---

## Key Questions to Answer in Your Implementation

### Scenario 1:
1. What thread pool size? (CPU cores? Fixed number? Dynamic?)
2. How to handle 100,000 students? (Batch processing? All at once?)
3. What happens if a submission takes too long? (Timeout?)
4. How to ensure all submissions complete? (CountDownLatch count = student count)

### Scenario 2:
1. How to simulate 12-hour shifts? (Time compression? Real-time?)
2. How many patients per hour? (Realistic simulation?)
3. What happens if queue is full? (Block? Reject? Expand?)
4. How to ensure smooth shift transition? (Overlap? Complete current patients?)
5. What if consultant finishes shift mid-treatment? (Complete or handover?)

---

## Common Pitfalls to Avoid

1. **Not being thread-safe**: Shared mutable state without synchronization
2. **Deadlocks**: Nested locks, circular dependencies
3. **Race conditions**: Check-then-act patterns without synchronization
4. **Resource leaks**: Not shutting down ExecutorService
5. **Infinite loops**: Blocking operations without proper conditions
6. **Lost updates**: Non-atomic operations on shared counters
7. **Surface-level explanations**: "It uses threads" is not enough
8. **Reading script**: Must demonstrate understanding, not memorization

---

## Success Criteria

### Code (60 marks):
- ✅ Thread-safe implementations
- ✅ Handles required scale (5000-100000 for Scenario 1)
- ✅ Proper exception handling
- ✅ Clean, readable code
- ✅ Appropriate concurrency mechanisms chosen
- ✅ Works without modification by marker

### Vodcast (40 marks):
- ✅ Clear demonstration of working code
- ✅ Technical explanations (not surface-level)
- ✅ Justification of chosen mechanisms
- ✅ Discussion of safety and liveness properties
- ✅ Student visible on screen
- ✅ Not reading from script
- ✅ ~10 minutes duration
- ✅ Appropriate technical depth for Level 6

---

## Next Steps

1. **Read and understand** this planning document
2. **Research** concurrency mechanisms mentioned
3. **Design** class structures and interactions
4. **Implement** Scenario 1 first (simpler)
5. **Test** thoroughly with various inputs
6. **Implement** Scenario 2
7. **Test** Scenario 2 thoroughly
8. **Prepare** vodcast script outline (not to read verbatim)
9. **Practice** explaining technical concepts
10. **Record** vodcast with clear demonstrations
11. **Package** code in ZIP file
12. **Submit** before deadline

---

## Resources for Research

- Java Concurrency in Practice (book)
- Oracle Java Concurrency Tutorial
- Java.util.concurrent package documentation
- BlockingQueue documentation
- ExecutorService documentation
- Atomic classes documentation
- Happens-before relationships
- CAS (Compare-And-Swap) operations
- Memory visibility and volatile keyword

---

**Remember**: The vodcast is worth 40% and requires deep technical understanding. You must be able to explain WHY you chose specific mechanisms and why they're better than alternatives. Surface-level explanations will lose marks.

