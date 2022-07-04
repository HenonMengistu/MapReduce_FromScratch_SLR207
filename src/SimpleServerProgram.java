import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Vector;

public class SimpleServerProgram {

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

//            File myObj = new File(filename);
//            try {
//                myObj.mkdirs();
//                if (myObj.mkdirs()) {
//                    System.out.println("Directory is created!");
//                } else {
//                    System.out.println("Failed to create directory!");
//                }
//
//
//                if (myObj.createNewFile()) {
//                    System.out.println("File created: " + myObj.getName());
//                } else {
//                    System.out.println("File already exists.");
//                }
//
//            } catch (IOException e) {
//                System.out.println("An error occurred.");
//                e.printStackTrace();
//            }


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


    }
}