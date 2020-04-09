import java.io.FileWriter;
import java.io.IOException;

public class HashTable {

    //the individual entries into the hashtable
    public class Entry {

        String word; //key
        Integer frequency; //value
        Entry next;


        public Entry(String word, Integer frequency) {
            this.word = word;
            this.frequency = frequency;
            next = null;
        }
    }

    Entry[] table;
    Integer tableSize;

    //starting the table off with a power of 2 size
    public HashTable() {
        tableSize = 16; //average length of a single English sentence is about 15 words, so 16 is a good size to start
        table = new Entry[tableSize];
    }

    //add entry to table
    public void add(String word, int freq) {
        Entry newEntry = new Entry(word, freq);
        //the hashed index
        int index = Math.abs(word.hashCode()) % tableSize;

        //if there are no other elements at that index, simply put it there
        if (table[index] == null)
            table[index] = newEntry;

        //if there is one or more elements, add this element to the end of the list of elements
        else {
            //pointer to track position
            Entry pointer = new Entry(null, null);
            pointer.next = table[index];

            //while we aren't at the end of the list, go down
            while (pointer.next != null) {
                pointer = pointer.next;
            }

            //once the end of list is reached, add entry
            pointer.next = newEntry;
        }
    }



    public boolean inTable(String word) {
        //find which bucket its in
        int index = Math.abs(word.hashCode()) % tableSize;

        //if there's nothing in the bucket this word would be in, it's not in the table
        if (table[index] == null) {
            return false;
        }

        //if the starting entry in this bucket contains the desired word, it is in the bucket
        if (table[index].word.equals(word)) {
            return true;
        }

        //if not found yet, check rest of bucket's entries by using a pointer to iterate over entries
        Entry curEntry = table[index];

        //while there are still indexes to check for matches, check them
        while (curEntry.next != null) {
            curEntry = curEntry.next;
            if (curEntry.word.equals(word)) {
                return true;
            }

        }
        //if nothing is found from above, this word is not in the bucket
        return false;
    }

    //print the hash table's contents to a file
    public void printToFile(String output_file) throws IOException {
        FileWriter writer = new FileWriter(output_file);

        //write the average collision length at the beginning of file
        writer.write(tableStats());

        //iterate over entire table
        for (int i = 0; i < table.length; i++) {

            //just to make output document look more readable
            if (i % 8 == 0)
                writer.write("\n");

            //if there is something at this index, write what it is
            if (table[i] != null) {
                Entry curEntry = table[i];
                writer.write("(" + escapeSpecialCharacter(curEntry.word) + " : " + curEntry.frequency + ") ");

                //if this bucket has more than one element, print those
                while (curEntry.next != null) {
                    curEntry = curEntry.next;
                    writer.write("(" + escapeSpecialCharacter(curEntry.word) + " : " + curEntry.frequency + ") ");
                }
            }
        }
        writer.close();
    }

    //increases the table size
    public void incSize() {
        //save old table, adjust table size
        Entry[] oldTable = table;
        tableSize *= 2;

        //increase the table size
        table = new Entry[tableSize];

        //fill table back up by iterating over old table
        for (int i = 0; i  < oldTable.length; i++) {

            if (oldTable[i] != null) {

                //add to new table by rehashing each element so they go to the appropriate index
                add(oldTable[i].word, oldTable[i].frequency);
            }
        }
    }

    //prints the average collision length
    public String tableStats() {

        //total number of elements in the table
        int counter = 0;

        //iterate over table
        for (int i = 0; i < table.length; i++) {

            //if there is something at this index, count it
            if (table[i] != null) {
                counter++;

                //check further into bucket and count those entries
                Entry pointer = table[i];
                while (pointer.next != null) {
                    counter++;
                    pointer = pointer.next;
                }
            }
        }
        return "The average collision list length, including null spaces, is: " + (double)counter / (double)tableSize;
    }

    //
    public boolean gettingFull() {
        int counter = 0;
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null)
                counter++;
        }
        return counter > .95 * tableSize ;
    }


    //can be used to see the entire table, with null spaces included
    public void printTableWithNulls() {
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null) {
                Entry curEntry = table[i];
                System.out.println(curEntry.word + " : " + curEntry.frequency);
                while (curEntry.next != null) {
                    curEntry = curEntry.next;
                    System.out.println(curEntry.word + " : " + curEntry.frequency);
                }
            }
            else
                System.out.println("null");
        }
    }

    //Yuchen's method to format special characters
    public static String escapeSpecialCharacter(String x) {
        StringBuilder sb = new StringBuilder();
        for (char c : x.toCharArray()) {
            if (c >= 32 && c < 127) sb.append(c);
            else sb.append(" [0x" + Integer.toOctalString(c) + "]");
        }
        return sb.toString();
    }
}
