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


    public static void main(String args[]) throws IOException, InterruptedException {

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
                String mapPath = "/tmp/"+userid+"/maps/UM"+idx+".txt";
                String shufflePath = "/tmp/"+userid+"/shufflesreceived";
                os.newLine();
                os.flush();
                String op = args[0];

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
                    switch (Integer.parseInt(op)) {
                    case 0:
                    map(filename,Integer.parseInt(idx));
                    break;
                    case 1:
                    shuffle(mapPath,Integer.parseInt(idx),serverString);
                    break;
                    case 2:
                    reduce(shufflePath,Integer.parseInt(idx));
                    break;
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
        
    }



    public static void map(String filesPath, int id) throws IOException, InterruptedException {
    	String lines = new String(Files.readAllBytes(Paths.get(filesPath)));
        createDirectory("/maps");
        lines.replace("\n", " ");
        String [] wordLst = lines.split(" ");
            FileWriter fileUM = new FileWriter("/tmp/hlamboro-21/" + "/UM" + id + ".txt");
            for (String data: wordLst){
              
                String datasplit = data + " 1";
                fileUM.write(String.valueOf(datasplit.getBytes(StandardCharsets.UTF_8)));

            }


    }
    
    public static void shuffle (String filesPath, int id, String servers) {
    	   ArrayList<String> lines = new ArrayList<String>(Files.readAllLines(Paths.get(filesPath)));
    	   HashMap<Integer,String> hashedVals = new HashMap<Integer,String>(); 
    	   String[] serverList = servers.split(" ");
    	   createDirectory("/shuffles");
    	   createDirectory("/shufflesreceived");
    	   String hostname = java.net.InetAddress.getLocalHost().getHostName();
    	   int hash =0;
    	   int serverSize = serverList.length;
    	   for (String line: lines ) {
    	     line = line.split(" ")[0];
    	     if(line.hashCode()==Integer.MIN_VALUE) {hash =0;} else {hash = Math.abs(line.hashCode());}
    	     
    	     String shuffledFile = "/tmp/"+userid+"/shuffles/"+hash+"-"+hostname+".txt";
    	     Files.createFile(Paths.get(shuffledFile));
    	     Files.writeString(Path.of(shuffledFile),line+" 1");
    	     hashedVals.put(hash, line);		
    	     int mod_key = hash%serverSize;
    	     String Dest = "/tmp/"+userid+"/shufflesreceived/"+hash+"-"+hostname+".txt";
    	     String Host = serverList[mod_key];
    	     String args = "scp -o StrictHostKeyChecking=no -o UserKnownHostsFile=/dev/null -q " +shuffledFile + " " + userid + "@" + Host + ":" + Dest ;
    	         //System.out.println("Running command : " + args);
    	         Process p = new ProcessBuilder(args.split(" ")).start();
    	         p.waitFor();
    	     }

    	     // asynchronous ; we run them all THEN we wait for them all
    	   
    	   System.out.println("Shuffling completed");
    	   
    	   
    	 }
    
    public static void reduce(String filesPath, int id) {
    	String[] pathnames;

      
        File f = new File(filesPath);
        HashMap<String, Integer> pairings = new HashMap<String, Integer>();
        // Populates the array with names of files and directories
        pathnames = f.list();
        createDirectory("/reduce");
        String reduceFile = "/tmp/"+userid+"/reduce/R"+id+".txt";
        Files.createFile(Paths.get(reduceFile));
        // For each pathname in the pathnames array
        for (String pathname : pathnames) {
            
        	String line = new String(Files.readAllBytes(Paths.get(pathname)));
        	if (!pairings.containsKey(line)) {
        		pairings.put(line, 1);
        		
        	} else {
        		int key = pairings.get(line);
        		key+=1;
        		pairings.replace(line, key);
        	}
        	
        }
        for (String word : pairings.keySet()) {
            int val = pairings.get(word);
            String line = word + " " + val;
        	Files.writeString(Path.of(reduceFile),line);
        	
        }
    	
    }


}