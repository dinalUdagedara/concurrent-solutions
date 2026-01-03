import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Main hospital A&E system that coordinates all components.
 */
public class HospitalSystem {
    private final BlockingQueue<Patient> paediatricianQueue;
    private final BlockingQueue<Patient> surgeonQueue;
    private final BlockingQueue<Patient> cardiologistQueue;
    private final List<Consultant> consultants;
    private final Object shiftLock;
    private ShiftManager shiftManager;
    
    private Thread patientGeneratorThread;
    private List<Thread> consultantThreads;
    private Thread shiftManagerThread;
    private PatientGenerator patientGenerator;

    public HospitalSystem() {
        int queueCapacity = 100;
        this.paediatricianQueue = new LinkedBlockingQueue<>(queueCapacity);
        this.surgeonQueue = new LinkedBlockingQueue<>(queueCapacity);
        this.cardiologistQueue = new LinkedBlockingQueue<>(queueCapacity);
        this.shiftLock = new Object();
        this.consultants = new ArrayList<>();
        consultants.add(new Consultant("Dr. Smith", Speciality.PAEDIATRICIAN, 
                                      paediatricianQueue, shiftLock));
        consultants.add(new Consultant("Dr. Jones", Speciality.SURGEON, 
                                      surgeonQueue, shiftLock));
        consultants.add(new Consultant("Dr. Brown", Speciality.CARDIOLOGIST, 
                                      cardiologistQueue, shiftLock));
    }

    public void start() {
        System.out.println("========================================");
        System.out.println("  ROYAL MANCHESTER HOSPITAL A&E");
        System.out.println("  Scenario 2 - Concurrent Processing");
        System.out.println("========================================\n");
        
        patientGenerator = new PatientGenerator(
            paediatricianQueue, surgeonQueue, cardiologistQueue);
        shiftManager = new ShiftManager(consultants, shiftLock);
        
        patientGeneratorThread = new Thread(patientGenerator, "PatientGenerator");
        patientGeneratorThread.start();
        
        consultantThreads = new ArrayList<>();
        for (Consultant consultant : consultants) {
            Thread consultantThread = new Thread(consultant, consultant.getName());
            consultantThreads.add(consultantThread);
            consultantThread.start();
        }
        
        shiftManagerThread = new Thread(shiftManager, "ShiftManager");
        shiftManagerThread.start();
        
        System.out.println("\nHospital system started successfully!");
        System.out.println("System Components:");
        System.out.println("   - Patient Generator: Running");
        System.out.println("   - Consultants: " + consultants.size() + " active");
        System.out.println("   - Shift Manager: Running");
        System.out.println("\nSystem is now processing patients...\n");
    }

    public void stop() throws InterruptedException {
        System.out.println("\n\nShutting down hospital system...\n");
        
        if (patientGenerator != null) {
            patientGenerator.stop();
        }
        
        for (Consultant consultant : consultants) {
            consultant.stop();
        }
        
        if (shiftManager != null) {
            shiftManager.stop();
        }
        
        if (patientGeneratorThread != null) {
            patientGeneratorThread.join(2000);
        }
        
        for (Thread thread : consultantThreads) {
            thread.join(2000);
        }
        
        if (shiftManagerThread != null) {
            shiftManagerThread.join(2000);
        }
        
        displayFinalStats();
        System.out.println("Hospital system shut down successfully");
    }

    private void displayFinalStats() {
        System.out.println("\n========== FINAL STATISTICS ==========");
        System.out.println("Patients Treated by Each Consultant:");
        for (Consultant consultant : consultants) {
            System.out.println("  " + consultant.getName() + " (" + consultant.getSpeciality() + 
                             "): " + consultant.getPatientsTreated() + " patients");
        }
        
        int totalTreated = consultants.stream()
            .mapToInt(Consultant::getPatientsTreated)
            .sum();
        
        System.out.println("\nTotal Patients Treated: " + totalTreated);
        System.out.println("Patients Remaining in Queues:");
        System.out.println("  Paediatrician Queue: " + paediatricianQueue.size());
        System.out.println("  Surgeon Queue: " + surgeonQueue.size());
        System.out.println("  Cardiologist Queue: " + cardiologistQueue.size());
        System.out.println("=====================================\n");
    }

    public void displayQueueStatus() {
        System.out.println("\nCurrent Queue Status:");
        System.out.println("  Paediatrician Queue: " + paediatricianQueue.size() + " patients");
        System.out.println("  Surgeon Queue: " + surgeonQueue.size() + " patients");
        System.out.println("  Cardiologist Queue: " + cardiologistQueue.size() + " patients");
    }
}

