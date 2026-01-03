public class HospitalSystemSimulation {
    private static final long SIMULATION_DURATION_MS = 60 * 1000;
    
    public static void main(String[] args) {
        HospitalSystem hospitalSystem = new HospitalSystem();
        
        try {
            hospitalSystem.start();
            
            System.out.println("⏱️  Simulation will run for " + 
                             (SIMULATION_DURATION_MS / 1000) + " seconds");
            System.out.println("   (Press Ctrl+C to stop early)\n");
            
            Thread statusThread = new Thread(() -> {
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        Thread.sleep(10000);
                        hospitalSystem.displayQueueStatus();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
            statusThread.start();
            
            Thread.sleep(SIMULATION_DURATION_MS);
            
            statusThread.interrupt();
            statusThread.join(1000);
            
            hospitalSystem.stop();
            
            System.out.println("\n========================================");
            System.out.println("  Simulation Complete");
            System.out.println("========================================");
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("⚠️ Simulation interrupted");
            try {
                hospitalSystem.stop();
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
            try {
                hospitalSystem.stop();
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

