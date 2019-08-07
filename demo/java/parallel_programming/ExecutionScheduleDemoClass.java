package demo.java.parallel_programming;

class VegetableChopper extends Thread {
    int vegetable_count = 0;
    static boolean chopping = true;

    VegetableChopper(String name) {
        this.setName(name);
    }

    public void run() {
        while(chopping) {
            this.vegetable_count++;
            System.out.println(this.getName()+" chopped a vegetable");
        }
    }
}

public class ExecutionScheduleDemoClass {
    public static void main(String[] args) throws InterruptedException {
        VegetableChopper chopper1 = new VegetableChopper("Chopper 1");
        VegetableChopper chopper2 = new VegetableChopper("Chopper 2");

        chopper1.start();
        chopper2.start();

        Thread.sleep(1000);
        VegetableChopper.chopping = false;

        chopper1.join();
        chopper2.join();

        System.out.println("Chopper 1 chopped "+chopper1.vegetable_count+" vegetables");
        System.out.println("Chopper 2 chopped "+chopper2.vegetable_count+" vegetables");
    }
}
