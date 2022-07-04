import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Vector;

public class SimpleServerProgram {
    static String username;

    public static void main(String args[]) {

        ServerSocket listener = null;
        String line;
        BufferedReader is;
        BufferedWriter os;
        Socket socketOfServer = null;
        //String filename = "/tmp/hlamboro-21/splits/splitsfile";
        String dirsplits = "/tmp/hlamboro-21/splits/";
        //String username = "/tmp/hlamboro-21";


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
                FileWriter fileWriter = new FileWriter(dirsplits + "/S" + 0 + ".txt");
                os.newLine();
                os.flush();


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


        String filename = dirsplits;
        username = usname;
        int index = Integer.parseInt(args[0].split("-")[1].split("\\.")[0]);

        map(filename, index);

    }

    static String getBaseDirectory(String subdir) {
        return String.format("/tmp/%s/%s", username, subdir);
    }

    static void map(String filename, int index) {
        StringBuilder output = new StringBuilder();

        BufferedReader bufferedReader;
        Vector<String> mappedElements = new Vector<>();

        try {
            bufferedReader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] words = line.split(" ");

                for (String word : words) {
                    mappedElements.add(word + " " + 1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            mappedElements.forEach((x) -> {
                output.append(x).append("\n");
            });

            String unsortedMapFilePath = String.format("%s/UM-" + index + ".txt", getBaseDirectory("maps"));
            System.out.println(unsortedMapFilePath);

            Files.write(Paths.get(unsortedMapFilePath), output.toString().getBytes(StandardCharsets.UTF_8));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void shuffle(String filepath, int mapId) {
    	ArrrayList 
    }


}