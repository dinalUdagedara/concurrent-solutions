import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Manages shift rotations for the A&E department.
 */
public class ShiftManager implements Runnable {
    private final AtomicReference<Shift> currentShift;
    private final List<Consultant> consultants;
    private final Object shiftLock;
    private volatile boolean running = true;
    private static final long SHIFT_DURATION_MS = 12 * 1000;
    private long shiftStartTime;

    public ShiftManager(List<Consultant> consultants, Object shiftLock) {
        this.currentShift = new AtomicReference<>(Shift.DAY);
        this.consultants = consultants;
        this.shiftLock = shiftLock;
        this.shiftStartTime = System.currentTimeMillis();
    }

    @Override
    public void run() {
        System.out.println("Shift Manager started - Managing shift rotations...\n");
        System.out.println("Current Shift: " + currentShift.get() + " (12 hours = " + 
                         (SHIFT_DURATION_MS / 1000) + " seconds in simulation)\n");
        
        notifyShiftChange(currentShift.get());
        
        while (running) {
            try {
                long elapsed = System.currentTimeMillis() - shiftStartTime;
                
                if (elapsed >= SHIFT_DURATION_MS) {
                    Shift newShift = (currentShift.get() == Shift.DAY) ? Shift.NIGHT : Shift.DAY;
                    Shift oldShift = currentShift.getAndSet(newShift);
                    
                    System.out.println("\nSHIFT CHANGE: " + oldShift + " -> " + newShift);
                    System.out.println("Shift duration: " + (elapsed / 1000.0) + " seconds\n");
                    
                    notifyShiftChange(newShift);
                    shiftStartTime = System.currentTimeMillis();
                }
                
                Thread.sleep(1000);
                
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Shift Manager interrupted");
                break;
            }
        }
        
        System.out.println("Shift Manager stopped");
    }

    private void notifyShiftChange(Shift newShift) {
        synchronized (shiftLock) {
            for (Consultant consultant : consultants) {
                consultant.updateShift(newShift);
            }
            shiftLock.notifyAll();
        }
        
        System.out.println("All consultants notified of " + newShift + " shift");
    }

    public Shift getCurrentShift() {
        return currentShift.get();
    }

    public void stop() {
        running = false;
    }
}

