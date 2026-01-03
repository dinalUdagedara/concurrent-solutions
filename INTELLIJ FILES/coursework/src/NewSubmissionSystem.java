import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Main submission system that processes student submissions concurrently.
 */
public class NewSubmissionSystem {
    private final int numOfStudents;
    private final int poolSize;
    private SubmissionStats stats;
    private ExecutorService executor;

    public NewSubmissionSystem(int poolSize, int numOfStudents) {
        this.poolSize = poolSize;
        this.stats = new SubmissionStats();
        this.numOfStudents = numOfStudents;
    }

    public void processSubmission() {
        executor = Executors.newFixedThreadPool(poolSize);
        CountDownLatch countDownLatch = new CountDownLatch(numOfStudents);

        stats.setStartTime();

        System.out.println("Starting submission processing for " + numOfStudents + " students...");
        System.out.println("Thread pool size: " + poolSize);
        System.out.println("Processing submissions concurrently...\n");

        for (int i = 0; i < numOfStudents; i++) {
            final int studentId = i + 1;

            executor.submit(() -> {
                try {
                    Student student = new Student(studentId, "Student " + studentId);
                    boolean success = student.submitExam("Student " + studentId);

                    if (success) {
                        stats.increaseSuccessfulSubmission();
                    } else {
                        stats.increaseFailedSubmission();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    stats.increaseFailedSubmission();
                    System.out.println("Student " + studentId + " submission interrupted");
                } catch (Exception e) {
                    stats.increaseFailedSubmission();
                    System.out.println("Student " + studentId + " submission error: " + e.getMessage());
                } finally {
                    countDownLatch.countDown();
                }
            });
        }

        try {
            System.out.println("\nWaiting for all " + numOfStudents + " submissions to complete...");
            countDownLatch.await();

            stats.setEndTime();

            System.out.println("All submissions finished!\n");
            stats.displayStats();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Main thread interrupted while waiting for submissions");
        }
    }

    public int getNumOfStudents() {
        return numOfStudents;
    }

    public int getPoolSize() {
        return poolSize;
    }

    public void shutdown() throws InterruptedException {
        System.out.println("Shutting down submission system...");

        executor.shutdown();

        if (!executor.awaitTermination(30, TimeUnit.SECONDS)) {
            System.out.println("Tasks did not complete in time, forcing shutdown...");
            executor.shutdownNow();

            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                System.err.println("Executor did not terminate");
            }
        }

        System.out.println("Submission system shut down successfully");
    }
}

