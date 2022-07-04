import java.io.*;
import java.util.*;

public class CountWord {

    public static void main(String[] args) throws IOException {
        Map<String,Integer> words=new HashMap<>();
//        List<String> wordsById;
//        wordsById = new ArrayList<>(words.values());
        CountWords("Input/input.txt",words);
       // Collections.sort(wordsById);
        System.out.println(words);
    }


    static void CountWords(String filename, Map<String, Integer> words) throws FileNotFoundException
        {
            Scanner file=new Scanner (new File(filename));
            while(file.hasNext()){
                String word=file.next();

                Integer count=words.get(word);
                if(count!=null)
                    count++;
                else
                    count=1;
                words.put(word,count);
            }
            file.close();
        }
    }

//        File file = new File("Input/input.txt");
//        FileReader fileI = new FileReader(file);
//        BufferedReader bufferI = new BufferedReader(fileI);
//
//
//        Scanner sc =new Scanner("Input/input.txt").useDelimiter(" ");
//        // Declaring the String
//        String str = sc.toString();
//        // Declaring a HashMap of <String, Integer>
//        Map<String, Integer> hashMap = new HashMap<>();
//
//        // Splitting the words of string
//        // and storing them in the array.
//        //String[] words = str.split(" ");
//
//        System.out.println(Arrays.toString(Arrays.stream(sc).toArray()));
//        for (String word : words) {
//
//            // Asking whether the HashMap contains the
//            // key or not. Will return null if not.
//            Integer integer = hashMap.get(word);
//
//            if (integer == null)
//                // Storing the word as key and its
//                // occurrence as value in the HashMap.
//                hashMap.put(word, 1);
//
//            else {
//                // Incrementing the value if the word
//                // is already present in the HashMap.
//                hashMap.put(word, integer + 1);
//            }
//        }
//        System.out.println(hashMap);
//    }
//


















//    public static void main(String[] args) {
//
//
//// Path class creates a dir and a file if they didn't exist at the runtime
//        try {
//
//            Path path = Paths.get("/Users/henonkb/Sockets/Input/mine");
//
//            //java.nio.file.Files;
//            Files.createDirectories(path);
//
//            System.out.println(path + " Directory is created!");
//
//        } catch (IOException e) {
//
//            System.err.println("Failed to create directory!" + e.getMessage());
//
//        }
//    }

