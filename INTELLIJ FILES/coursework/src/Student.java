import java.util.Random;

/**
 * Represents a student submitting an exam.
 */
public class Student {
    private int id;
    private String name;
    private Random random;

    public Student(int id, String name) {
        this.id = id;
        this.name = name;
        this.random = new Random();
    }

    public String getName() {
        return this.name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean submitExam(String name) throws InterruptedException {
        int simulateTime = random.nextInt(100);
        Thread.sleep(simulateTime);

        int randomNumber = random.nextInt(100);
        if (randomNumber < 5) {
            System.out.println("Student " + name + "'s submission FAILED");
            return false;
        } else {
            System.out.println("Student " + name + "'s submission SUCCESSFUL");
            return true;
        }
    }
}

