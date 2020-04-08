import java.io.IOException;

public class Tester {

    public static void main(String[] args) throws IOException {
        HashTable hash = new HashTable();
        hash.add("word", 15);
        hash.add("word2", 16);
        WordScanner scan = new WordScanner("testing");
        scan.getWords();
        scan.addToTable();
        System.out.println(scan.wordList);
        scan.table.printTable();

    }
}
