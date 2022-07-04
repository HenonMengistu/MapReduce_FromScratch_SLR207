import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class SimpleServerProgram {
    static String username;
    static String userid = "hlamboro-21";
    static String dirmap = "/tmp/hlamboro-21/maps/";
    public String serversString;

    public static void createDirectory(String dirName) throws InterruptedException, IOException {

        String dirPath = "/tmp/" + userid + dirName;
        ProcessBuilder pb = new ProcessBuilder("mkdir", "-p", dirPath);
        pb.redirectErrorStream(true);
        Process process = pb.start();

        int err = process.waitFor();
        if (err == 0) {
            System.out.println("Directory created successfully !");
        } else {
            System.out.println("Cannot create directory !");

        }
    }


    public static void main(String args[]) throws IOException {

        ServerSocket listener = null;
        String line;
        BufferedReader is;
        BufferedWriter os;
        Socket socketOfServer = null;
        //String filename = "/tmp/hlamboro-21/splits/S0.txt)";
        String dirsplits = "/tmp/hlamboro-21/splits/";
        String usname= "hlamboro-21";

        final List<BufferedWriter> osL = new ArrayList<>();
        final List<BufferedReader> isL = new ArrayList<>();





        // Try to open a server socket on port 9999
        // Note that we can't choose a port less than 1023 if we are not
        // privileged users (root)

        try {
            listener = new ServerSocket(8787);

        } catch (IOException e) {
            System.out.println(e);
            System.exit(1);
        }

        try {
            System.out.println("Server is waiting to accept user...");

            // Accept client connection request
            // Get new Socket at Server.
            socketOfServer = listener.accept();
            System.out.println("Accept a client!");

            // Open input and output streams
            is = new BufferedReader(new InputStreamReader(socketOfServer.getInputStream()));
            os = new BufferedWriter(new OutputStreamWriter(socketOfServer.getOutputStream()));

            try {

                Path path1 = Paths.get(dirsplits);
                Files.createDirectories(path1);
                System.out.println(path1 + " Directory is created!");

            } catch (IOException e) {
                System.err.println("Failed to create directory!" + e.getMessage());
            }
                String filename = is.readLine();
                String idx = filename.split("/S")[2].split(".txt")[0];
                FileWriter fileWriter = new FileWriter(dirsplits + "/S" + idx + ".txt");
                String serverString = is.readLine();
                os.newLine();
                os.flush();


            File fileUM = new File("/tmp/hlamboro-21/" + "/UM" + 0 + ".txt");
                while (true) {
                    // Read data to the server (sent from client).
                    line = is.readLine();
                    System.out.println(line);

                    if (line.equals("QUIT")) {
                        os.write(">> OK");
                        os.newLine();
                        os.flush();
                        break;
                    } else {
                        fileWriter.write(line);
                        fileWriter.flush();

                    }
                }
                fileWriter.close();
            } catch(IOException e){
                System.out.println(e);
                e.printStackTrace();
            }
            System.out.println("Sever stopped!");


        ArrayList<String> mapped = new ArrayList<>();
        File splitsDirectory = new File("/Users/henonkb/Sockets/splits");
        for (File file : Objects.requireNonNull(splitsDirectory.listFiles())) {
            mapped.add(file.getAbsolutePath());
        }

        map(mapped,osL);

    }



    public static void map(filename,List<BufferedWriter> os) throws IOException, InterruptedException {
        Path path2 = Paths.get(dirmap);
        createDirectory("/maps");

        for (int i = 0; i < 3 ; i++) {
            String filepathS = "/tmp/hlamboro-21/splits/S"+ i +".txt";
            File myObj = new File(filepathS);
            Scanner myReader = new Scanner(myObj);
            FileWriter fileUM = new FileWriter("/tmp/hlamboro-21/" + "/UM" + 0 + ".txt");
            while (myReader.hasNext()){
                String data = myReader.next();
                String datasplit = data.replace(" ","1");
                fileUM.write(String.valueOf(datasplit.getBytes(StandardCharsets.UTF_8)));

//                os.get(i).write(datasplit);
//                os.get(i).newLine();
//                os.get(i).flush();

            }
        }





    }


}