import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class SubmissionStats {
    private AtomicInteger successfulSubmissions;
    private AtomicInteger failedSubmissions;
    private AtomicLong startTime;
    private AtomicLong endTime;

    public SubmissionStats() {
        this.successfulSubmissions = new AtomicInteger(0);
        this.failedSubmissions = new AtomicInteger(0);
        this.startTime = new AtomicLong(0);
        this.endTime = new AtomicLong(0);
    }

    public void reset() {
        successfulSubmissions.set(0);
        failedSubmissions.set(0);
        startTime.set(0);
        endTime.set(0);
    }

    public void increaseSuccessfulSubmission() {
        successfulSubmissions.incrementAndGet();
    }

    public void increaseFailedSubmission() {
        failedSubmissions.incrementAndGet();
    }

    public void setStartTime() {
        startTime.set(System.currentTimeMillis());
    }

    public void setEndTime() {
        endTime.set(System.currentTimeMillis());
    }

    public int getSuccessfulSubmissions() {
        return successfulSubmissions.get();
    }

    public int getFailedSubmissions() {
        return failedSubmissions.get();
    }

    public long getTotalTimeMillis() {
        return endTime.get() - startTime.get();
    }

    public int getTotalSubmissions() {
        return successfulSubmissions.get() + failedSubmissions.get();
    }

    public double getSuccessRate() {
        int total = getTotalSubmissions();
        if (total == 0) return 0.0;
        return ((double) getSuccessfulSubmissions() / total) * 100;
    }

    public void displayStats() {
        System.out.println("\n========== SUBMISSION STATISTICS ==========");
        System.out.println("Total Students Processed: " + getTotalSubmissions());
        System.out.println("Successful Submissions: " + getSuccessfulSubmissions());
        System.out.println("Failed Submissions: " + getFailedSubmissions());
        System.out.println("Total Time Taken: " + getTotalTimeMillis() + " ms (" + 
                          (getTotalTimeMillis() / 1000.0) + " seconds)");
        System.out.println("Success Rate: " + String.format("%.2f", getSuccessRate()) + "%");
        System.out.println("==========================================\n");
    }
}

