import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static void main(String[] args) {

        int randomNum = ThreadLocalRandom.current().nextInt(0, 100);

        SpigotAlgorithm spigot = new SpigotAlgorithm();
        if (!spigot.setRequestedDigits(100)) return;
        spigot.run();

    }
}
