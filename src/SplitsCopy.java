import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class SplitsCopy {
    static String username;

    public static void main(String[] args) throws IOException, InterruptedException {
        String ip = args[0];
        int index = Integer.parseInt(args[1]);
        String workingMachines = args[2];
        String projectPath = args[3];
        String username = args[4];

        ArrayList<String> splits = new ArrayList<>();

        File splitsDirectory = new File(projectPath + "/splits");

        for (File file : Objects.requireNonNull(splitsDirectory.listFiles())) {
            splits.add(file.getAbsolutePath());
        }
    }


    static void copySplits(ArrayList<String> splits, int index, String ip) throws IOException, InterruptedException {
        System.out.println("Copying splits...");
        ProcessBuilder processBuilder = new ProcessBuilder("scp", splits.get(index),
                String.format("username@%s:/tmp/%s/splits", username, ip, username));

        Process p = processBuilder.start();

        readLines(p);
    }

    private static void readLines(Process p) throws IOException {

        Scanner scanner = new Scanner(p.getInputStream());
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            System.out.println(line);
        }

        scanner = new Scanner(p.getErrorStream());

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            System.out.println(line);
        }

        p.destroy();
    }
}
