import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Represents a consultant working in the A&E department.
 */
public class Consultant implements Runnable {
    private final String name;
    private final Speciality speciality;
    private final BlockingQueue<Patient> patientQueue;
    private final AtomicInteger patientsTreated;
    private volatile Shift currentShift;
    private volatile boolean running = true;
    private final Object shiftLock;

    public Consultant(String name, Speciality speciality, 
                      BlockingQueue<Patient> patientQueue, Object shiftLock) {
        this.name = name;
        this.speciality = speciality;
        this.patientQueue = patientQueue;
        this.patientsTreated = new AtomicInteger(0);
        this.shiftLock = shiftLock;
    }

    @Override
    public void run() {
        System.out.println(name + " (" + speciality + ") started");
        
        while (running) {
            try {
                if (isMyShift()) {
                    Patient patient = patientQueue.take();
                    long waitingTime = patient.getWaitingTime();
                    
                    System.out.println(name + " treating " + patient + 
                                     " (waited: " + (waitingTime / 1000.0) + "s)");
                    
                    Thread.sleep(1000 + (int)(Math.random() * 2000));
                    
                    int treated = patientsTreated.incrementAndGet();
                    System.out.println(name + " completed treatment of " + patient + 
                                     " (Total treated: " + treated + ")");
                    
                } else {
                    synchronized (shiftLock) {
                        shiftLock.wait();
                    }
                }
                
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println(name + " interrupted");
                break;
            }
        }
        
        System.out.println(name + " finished (Total patients treated: " + 
                          patientsTreated.get() + ")");
    }

    private boolean isMyShift() {
        return currentShift != null;
    }

    public void updateShift(Shift shift) {
        this.currentShift = shift;
        synchronized (shiftLock) {
            shiftLock.notifyAll();
        }
    }

    public void stop() {
        running = false;
        // Interrupt if waiting
        synchronized (shiftLock) {
            shiftLock.notifyAll();
        }
    }

    public String getName() {
        return name;
    }

    public Speciality getSpeciality() {
        return speciality;
    }

    public int getPatientsTreated() {
        return patientsTreated.get();
    }
}

