public class SubmissionSystemSimulation {
    public static void main(String[] args) throws InterruptedException {
        int poolSize = Runtime.getRuntime().availableProcessors() * 2;
        int numOfStudents = 10000;
        
        System.out.println("========================================");
        System.out.println("  UNIVERSITY SUBMISSION SYSTEM");
        System.out.println("  Scenario 1 - Concurrent Processing");
        System.out.println("========================================\n");
        System.out.println("System Configuration:");
        System.out.println("- Available CPU Cores: " + Runtime.getRuntime().availableProcessors());
        System.out.println("- Thread Pool Size: " + poolSize);
        System.out.println("- Number of Students: " + numOfStudents);
        System.out.println();
        
        NewSubmissionSystem newSubmissionSystem = 
                new NewSubmissionSystem(poolSize, numOfStudents);
        
        newSubmissionSystem.processSubmission();
        newSubmissionSystem.shutdown();
        
        System.out.println("\n========================================");
        System.out.println("  Simulation Complete");
        System.out.println("========================================");
    }
}

