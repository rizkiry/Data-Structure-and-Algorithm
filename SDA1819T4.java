
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.ArrayList;

public class SDA1819T4 {
   
    public static String hieroglyph; 
    public static HashTable hashtable;
    public static HashTable hashtable1;
    public static ArrayList<String> outputList;
    public static ArrayList<String> outputList1;
    public static int order = 0;
    public static int length = 0;
    
    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String x = reader.readLine();
        hieroglyph = x;
        length = hieroglyph.length();
        hashtable = new HashTable(length);
        
         
        int y = Integer.parseInt(reader.readLine());
        ArrayList<String> outputList_a = new ArrayList<String>(y);
        outputList = outputList_a;
        
        ArrayList<String> outputList_b = new ArrayList<String>(hieroglyph.length());
        outputList1 = outputList_b;
        process();
        
        for (int i = 0; i < y; i++) {
            String z = reader.readLine();
            Nav(z);
        }
               
        outputList.stream().forEach((value) -> {
            System.out.println(value);
        });
        System.gc();
        if (order != 1) {
        } else {
            outputList1.stream().forEach((value) -> {
                System.out.println(value);
            });
        }
    }
    
    private static void Nav(String z){
        if (z.split(" ").length > 1) {
            order1(z);
        }else{
            order2();
        }
    }
    
    private static void order1(String z){
        String word = z.split(" ")[1];
        int count=hashtable.Count(word);
        outputList.add(String.valueOf(count));
    }
    
    private static void order2(){
         order = 1;
    }
      
    private static void process(){
        for (int i = 0; i < hieroglyph.split("").length; i++ ){
            insert(i);
        }
    }
    
    private static void insert(int number){
       hashtable1 = new HashTable(length);
       String Value ="";
       String prevValue ="";
        String k[] = hieroglyph.split("");
        for (int i = 0; i < hieroglyph.split("").length; i++ ){
            String value = k[i];
            for (int j = 1; j <= number; j++){
                if (i+number > hieroglyph.split("").length-1){
                    break;
                }else{
                    value = value + k[i+j];
                    
                }
            }
            if (value.split("").length == 1 && number == 0){
                hashtable.put(value, value); 
                hashtable1.put(value, value);
                if (Value.length() == 0) {
                    Value = value;
                }

            }else if (value.split("").length > 1 && number > 0){
                hashtable.put(value, value); 
                hashtable1.put(value, value);
                if (Value.length() == 0) {
                    Value = value;
                }
            } 
        }

        String a = hashtable1.count();
        String count[] = hashtable1.count().split(" ");
        if (Integer.parseInt(count[1])  == 1) {
            outputList1.add(Value + " 1");
        }else{
            outputList1.add(a);
        }
        
    }
    
    private static int HashTableSize(){
        int length = hieroglyph.split("").length; 
        int size = 0;
        for (int i = 0; i < length; i++) {
            size = size + length - i;
        }
        
        return size;
    }
 
}


// Source https://gist.github.com/amadamala/3cdd53cb5a6b1c1df540981ab0245479
class HashTable{ 
    private static final int INITIAL_SIZE = 1000;
    private final HashEntry[] entries = new HashEntry[INITIAL_SIZE];

    HashTable(int length) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public void put(String key, String value) {
        int hash = getHash(key);
        final HashEntry hashEntry = new HashEntry(key, value);
        if(entries[hash] == null) {
            entries[hash] = hashEntry;
        }
        // If there is already an entry at current hash
        // position, add entry to the linked list.
        else {
            HashEntry temp = entries[hash];
            while(temp.next != null) {
                temp = temp.next;
            }
            temp.next = hashEntry;
        }
    }
    
    public String get(String key) {
        int hash = getHash(key);
        if(entries[hash] != null) {
            HashEntry temp = entries[hash];

            // Check the entry linked list for march
            // for the given 'key'
            while( !temp.key.equals(key)
                    && temp.next != null ) {
                temp = temp.next;
            }
            return temp.value;
        }

        return null;
    }
    
    public int getHash(String key) {
        // piggy backing on java string
        // hashcode implementation.
        return key.hashCode() % INITIAL_SIZE;
    }

    public static class HashEntry {
        String key;
        String value;
        // Linked list of same hash entries.
        HashEntry next;

        public HashEntry(String key, String value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }

        @Override
        public String toString() {
            return "[" + key + ", " + value + "]";
        }
    }

    @Override
    public String toString() {
        int bucket = 0;
        StringBuilder hashTableStr = new StringBuilder();
        for (HashEntry entry : entries) {
            if(entry == null) {
                continue;
            }
            hashTableStr.append("\n bucket[")
                    .append(bucket)
                    .append("] = ")
                    .append(entry.toString());
            bucket++;
            HashEntry temp = entry.next;
            while(temp != null) {
                hashTableStr.append(" -> ");
                hashTableStr.append(temp.toString());
                temp = temp.next;
            }
        }
        return hashTableStr.toString();
    }
    
    public String getCount(String key) {
        int hash = getHash(key);
        if(entries[hash] != null) {
            HashEntry temp = entries[hash];

            // Check the entry linked list for march
            // for the given 'key'
            while( temp != null ) {
                temp = temp.next;
            }
            return temp.value;
        }

        return null;
    }

    public String count() {
        int terHigh = 0;
        String val = "";
        StringBuilder hashTableStr = new StringBuilder();
        for (HashEntry entry : entries) {
            if(entry == null) {
                continue;
            }
            
            int count = 1;
            
            HashEntry temp = entry.next;
            while(temp != null) {
                count ++;
                StringBuilder append = hashTableStr.append(temp.key.toString());
                temp = temp.next;
            }
            if (terHigh < count ) {
                terHigh = count;
                val = hashTableStr.toString();
            }
            hashTableStr.setLength(0);
        }
        return val +" "+ terHigh;
    }
    
    public int Count(String key) {
        int count = 0;
        StringBuilder hashTableString;
        hashTableString = new StringBuilder();
        for (HashEntry entry : entries) {
            if(entry == null) {
                continue;
            }
            
            HashEntry temp = entry;
            while(temp != null) {
                if (temp.key.equals(key)) {
                 count++;
                }
                temp = temp.next;
            }
        }
        return count ;
    }
}

