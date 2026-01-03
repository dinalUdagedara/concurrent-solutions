import java.util.Random;
import java.util.concurrent.BlockingQueue;

/**
 * Generates patients continuously and adds them to appropriate queues.
 */
public class PatientGenerator implements Runnable {
    private final BlockingQueue<Patient> paediatricianQueue;
    private final BlockingQueue<Patient> surgeonQueue;
    private final BlockingQueue<Patient> cardiologistQueue;
    private final Random random;
    private volatile boolean running = true;
    private int patientIdCounter = 1;

    public PatientGenerator(BlockingQueue<Patient> paediatricianQueue,
                           BlockingQueue<Patient> surgeonQueue,
                           BlockingQueue<Patient> cardiologistQueue) {
        this.paediatricianQueue = paediatricianQueue;
        this.surgeonQueue = surgeonQueue;
        this.cardiologistQueue = cardiologistQueue;
        this.random = new Random();
    }

    @Override
    public void run() {
        System.out.println("Patient Generator started - generating patients continuously...\n");
        
        while (running) {
            try {
                int arrivalInterval = 500 + random.nextInt(2500);
                Thread.sleep(arrivalInterval);
                
                Speciality speciality = Speciality.values()[random.nextInt(Speciality.values().length)];
                Patient patient = new Patient(patientIdCounter++, speciality);
                
                switch (speciality) {
                    case PAEDIATRICIAN:
                        paediatricianQueue.put(patient);
                        System.out.println(patient + " arrived and queued for Paediatrician");
                        break;
                    case SURGEON:
                        surgeonQueue.put(patient);
                        System.out.println(patient + " arrived and queued for Surgeon");
                        break;
                    case CARDIOLOGIST:
                        cardiologistQueue.put(patient);
                        System.out.println(patient + " arrived and queued for Cardiologist");
                        break;
                }
                
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Patient Generator interrupted");
                break;
            }
        }
        
        System.out.println("Patient Generator stopped");
    }

    public void stop() {
        running = false;
    }
}

