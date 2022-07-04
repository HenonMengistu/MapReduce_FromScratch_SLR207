import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.stream.Collectors;

public class SimpleClient {

    static String username = "hlamboro-21";
    public static String servers = "tp-t310-08 tp-t310-09 tp-t310-10";

    public static void main(String[] args) throws IOException, InterruptedException {

        // Server Host
        // final String serverHost = "tp-1a226-28.enst.fr";
        List<Socket> socketOfClient = new ArrayList<>();
        final List<BufferedWriter> os = new ArrayList<>();
        final List<BufferedReader> is = new ArrayList<>();
       // String username = "hlamboro-21";


        ArrayList<String> ip = new ArrayList<>();
//        ip.add("tp-1a226-27");
//        ip.add("tp-1a226-22");
//        ip.add("tp-1a226-30");
        ip.add("tp-t310-08");
        ip.add("tp-t310-09");
        ip.add("tp-t310-10");

        //int index = Integer.parseInt(args[1]);


        // System.out.println( splitsDirectory.listFiles());

        ip.forEach((n) -> {
            try {
                Socket socket = new Socket(n, 8787);
                socketOfClient.add(socket);
                os.add(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
                is.add(new BufferedReader(new InputStreamReader(socket.getInputStream())));


            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        ArrayList<String> splits = new ArrayList<>();
        File splitsDirectory = new File("/Users/henonkb/Sockets/splits");
        for (File file : Objects.requireNonNull(splitsDirectory.listFiles())) {
            splits.add(file.getAbsolutePath());
        }
        copySplits(splits, ip,os);
    }


    static void copySplits(ArrayList<String> splits, ArrayList<String> ips,List<BufferedWriter> os ) throws IOException, InterruptedException {
        System.out.println("Copying splits...");
//        for (String ip : ips) {
        for (int fileindex = 0; fileindex < ips.size(); fileindex++) {

            try {
                String filenameS = "/Users/henonkb/Sockets/splits/S" + fileindex + ".txt";
                File myObj = new File(filenameS);
                Scanner myReader = new Scanner(myObj);
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();

                    os.get(fileindex).write(filenameS);
                    os.get(fileindex).newLine();
                    os.get(fileindex).write(servers);
                    os.get(fileindex).write(data);
                    os.get(fileindex).newLine();
                    os.get(fileindex).flush();


                    System.out.println(data);
                }

                os.get(fileindex).write("QUIT");
                os.get(fileindex).newLine();
                os.get(fileindex).flush();

                myReader.close();

            } catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
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