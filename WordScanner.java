import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

// TODO: 4/8/2020 put better comments on getWords, bc I don't even know exactly why it works.

public class WordScanner {
    String contents;
    HashTable table = new HashTable();
    ArrayList<String> wordList = new ArrayList<>();

    public WordScanner(String inputFile) throws IOException {
        contents = fileContents(inputFile + ".txt");
    }


    public void getWords() {
        StringTokenizer token = new StringTokenizer(contents);
        //while there are more contents to iterate over
        while (token.hasMoreTokens()) {
            String curWord = token.nextToken();

            //check if the word is already in the table
            if (!table.inTable(curWord)) {

                //if this isn't just one word
                if (!isSingleWord(curWord)) {
                    //Get only the alphabetical characters
                    String[] words = curWord.split("\\P{Alpha}");
                    //String[] words = curWord.replaceAll("[^A-za-z]", "").split("\\P{Alpha}");
                    for (String word : words) {
                        //keep replaced characters from being added
                        if (word.length() > 0 && !table.inTable(word)) {
                            wordList.add(word);
                        }
                    }
                }
                else
                    wordList.add(curWord);
            }
        }
    }

    public void addToTable() {
        for (String word : wordList) {
            int frequency = 0;
            StringTokenizer token = new StringTokenizer(contents);
            //while there are more contents to iterate over
            while (token.hasMoreTokens()) {
                //word currently being examined
                String curWord = token.nextToken();
                //if it is a single word and it equals the word being searched for, increase frequency
                if (isSingleWord(curWord) && curWord.equals(word)) {
                        frequency++;
                }
                //if not a single word, break up into single words
                else if (!isSingleWord(curWord)) {
                    String[] multiWord = curWord.split("\\P{Alpha}");
                    //compare all of these words from the broken string to the word being searched for. If it matches, increase frequency
                    for (String singleWord : multiWord) {
                        if (singleWord.equals(word))
                            frequency++;
                        }
                    }
                }
            table.add(word, frequency);
        }
    }


    //Read file contents
    //I did just take this from my submission of programming project 2, since we had to do this exact same step
    public String fileContents(String InputFile) throws IOException {
        FileReader fr = new FileReader(InputFile);

        int index;

        //start with nothing
        String st = "";
        //scan while there are characters left to scan, add to end of string
        while ((index = fr.read()) != -1) {
            st += (char) index;
        }
        //return everything lowercase
        return st.toLowerCase();
    }

    //checks to see if a word is just letters
    public boolean isSingleWord(String s) {
        s = s.toLowerCase();
        char[] arr = s.toCharArray();
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] < 'a' || arr[i] > 'z')
                return false;
        }
        return true;
    }
}
