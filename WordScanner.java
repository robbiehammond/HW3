import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

// @TODO - Grab Yuchen's method for displaying wierd characters, put it at the bottom of hashTable, use it when printing to file

//class to scan file, get words and frequencies, then put these into a hash table.
public class WordScanner {
    //contents of file
    String contents;

    //table to store words and frequencies
    HashTable hash = new HashTable();

    //list all of the words inside of file (including repeating words)
    ArrayList<String> wordList = new ArrayList<>();

    public WordScanner(String inputFile) throws IOException {
        //get contents of file, load into variable
        contents = fileContents(inputFile); //.txt
    }

    //generates the list of words inside of the file
    public void getWords() {
        StringTokenizer token = new StringTokenizer(contents);

        //while there are more contents to iterate over
        while (token.hasMoreTokens()) {
            String curWord = token.nextToken();

            //if this isn't just one word
            if (!isSingleWord(curWord)) {

                //Get only the alphabetical characters
                String[] words = curWord.split("\\P{Alpha}");

                    /*iterate over each word inside of this multi-word string, check if
                    they are not blank (which is sometimes caused from characters being removed)
                    and check if they're not already in the table
                     */
                for (String word : words) {
                    if (word.length() > 0 && !hash.inTable(word)) {
                        wordList.add(word);
                    }
                }
            }

            //if not a single word, add it to list without breaking it up
            else {
                wordList.add(curWord);
            }
        }
    }

    //adds words and their frequencies to the hashtable
    public void addToTable() {
        //iterate over all of the words in document
        for (String s : wordList) {


            //add only the words not currently in the table
            if (!hash.inTable(s))  {

                //frequency of word begin searched for in the document
                int frequency = 0;
                StringTokenizer token = new StringTokenizer(contents);

                //while there are more contents to iterate over
                while (token.hasMoreTokens()) {

                    //save word currently being examined
                    String curWord = token.nextToken();

                    //if it is a single word and it equals the word being searched for, increase frequency
                    if (isSingleWord(curWord) && curWord.equals(s)) {
                        frequency++;
                    }

                    //if not a single word, break up into single words
                    else if (!isSingleWord(curWord)) {
                        String[] multiWord = curWord.split("\\P{Alpha}");

                        //compare all of these words from the broken string to the word being searched for. If it matches, increase frequency
                        for (String singleWord : multiWord) {
                            if (singleWord.equals(s))
                                frequency++;
                        }
                    }
                }
                //once frequency is fully obtained, add it to hash
                hash.add(s, frequency);
            }
        }
    }


    //Read file contents
    //Yes this is almost identical to my Programming Project 2 fileContents function, since this project has us do basically the same thing
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

        //loop through all characters, see if any aren't letters
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] < 'a' || arr[i] > 'z')
                return false;
        }
        return true;
    }
}
