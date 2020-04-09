import java.io.IOException;

public class Tester {

    static int size = 5;

    public static void main(String[] args) throws IOException {
        wordCount("toyfile.txt", "output.txt");
    }

    public static void wordCount(String input_file, String output_file) throws IOException {
        try {
            WordScanner scan = new WordScanner(input_file);
            scan.getWords();
            scan.addToTable();
            scan.hash.printToFile(output_file);
            System.out.println("Success! Check the outputFile for the list of words\n" +
                    "with their respective frequencies, in addition to the average collision length.");
        }
        catch (Exception e) {
            System.out.println("Something has went wrong. Make sure the input file\n" +
                    "is in the correct directory.");
        }
    }

}
