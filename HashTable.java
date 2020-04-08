public class HashTable {

    public class Entry {

        //put access modifiers later
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
    int tableSize;

    //starting the table off with a power of 2 size
    public HashTable() {
        tableSize = 16;
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
        if (table[index] == null) {
            return false;
        }

        Entry curEntry = table[index];

        if (table[index].word.equals(word)) {
            return true;
        }

        while (curEntry.next != null) {
            if (curEntry.word.equals(word)) {
                return true;
            }
            else {
                curEntry = curEntry.next;
            }
        }

        return false;
    }

    public void printTable() {
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null) {
                Entry curEntry = table[i];
                System.out.println(curEntry.word + " : " + curEntry.frequency);
                while (curEntry.next != null) {
                    curEntry = curEntry.next;
                    System.out.println(curEntry.word + " : " + curEntry.frequency);
                }
            }
        }
    }
}
