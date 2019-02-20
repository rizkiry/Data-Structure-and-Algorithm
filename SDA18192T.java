import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class SDA18192T {
    
    public static int maxGem;
    public static int _key;
    public static int totalHit = 1;
    public static boolean result = false;
    public static int max_s = 0;
      
    public static void main(String[] args) throws IOException {
        LinkedList ll = new LinkedList(); 
        int totalGem;
  
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        
        String New[] = reader.readLine().split(" ");
        totalGem = Integer.parseInt(New[0]);
        maxGem = Integer.parseInt(New[1]);
        
        String New1[] = reader.readLine().split(" ");
        String [] gem = new String [totalGem];
        System.arraycopy(New1, 0, gem, 0, totalGem);
        _key = totalGem;
        
        for (int l = 0; l < gem.length; l++) {
             DataItems data = new DataItems (l, gem[l]);
             ll.insertNode(data);
        }
        
        ll.reverseLinkedList();
        EventHit(ll);
        
        String hit = reader.readLine();
        totalHit = Integer.parseInt(hit);
        
        for (int k = 0; k < totalHit; k++) {
            String hits [] = reader.readLine().split(" ");   
            int index = Integer.parseInt(hits[0]);
            String value = hits[1];
            max_s++;
            Hit(_key+1, value, index, ll);
        }
    }
    
    // check result (Menang atau Kalah)
    private static void CheckWin(LinkedList ll){
        if (ll.size() >= maxGem && max_s == totalHit) {
            System.out.println("KALAH");
        }
        if(ll.size() <= 3){
            result = true; 
        }
        if(max_s == totalHit && ll.size() > 3 && ll.size() < maxGem && result == false){
            System.out.println(ll.size());
        }
        if(result && max_s == totalHit){
            System.out.println("MENANG");
        }  
    }
    // check index value (four adjacent gems) 
    private static void CheckEvent(LinkedList ll){
        int a = ll.size()-1;
        for(int i = 0; i < ll.size(); i++){
            DataItems data = ll.searchByNode(i);
            DataItems dataPrev;
            DataItems dataNext;
            int index;
            int indexPrev;
            int indexNext;
            int flag = 0;
            index = i;
            
            // gets data value before index
            if (i == 0){
                dataPrev = ll.searchByNode(ll.size()-1);
                indexPrev = ll.size()-1;
            }else{
                dataPrev = ll.searchByNode(i-1);
                indexPrev = i-1;
            }
            
            // gets data value after index
            if(i == ll.size()-1){
                dataNext = ll.searchByNode(0);
                indexNext = 0;
                flag = 1;
            }else{
                dataNext = ll.searchByNode(i+1);
                indexNext = i+1;
            }
            
            String abc = data.getValue();
            String abcd = dataNext.getValue();
            String abce = dataPrev.getValue();
            
             // gets adjacent data value 
            if(data.getValue().equals(dataPrev.getValue()) && data.getValue().equals(dataNext.getValue())){
                ll.deleteNodeAtIndex(index);
                ll.deleteNodeAtIndex(indexPrev); 
                if (flag == 1){
                    ll.deleteNodeAtIndex(0);
                }else{
                    ll.deleteNodeAtIndex(indexNext-2);
                    ll.deleteNodeAtIndex(indexNext-2);
                }
                break;
            }
        }
    }
    
    // check index value (three adjacent gems) 
    private static void EventHit(LinkedList ll){
        int a = ll.size()-1;
        for(int i=0; i<ll.size(); i++){
            DataItems data = ll.searchByNode(i);
            DataItems dataPrev;
            DataItems dataNext;
            int index;
            int indexPrev;
            int indexNext;
            int flag = 0;
            index = i;
            
            // gets data value before index
            if (i == 0){
                dataPrev = ll.searchByNode(ll.size()-1);
                indexPrev = ll.size()-1; 
            }else{
                dataPrev = ll.searchByNode(i-1);
                indexPrev = i-1;
            }
            
            // gets data value after index
            if(i == ll.size()-1){ 
                dataNext = ll.searchByNode(0);
                indexNext = 0;
                flag = 1;
            }else{
                dataNext = ll.searchByNode(i+1);
                indexNext = i+1;
            }
            
            String abc = data.getValue();
            String abcd = dataNext.getValue();
            String abce = dataPrev.getValue();
            
            // gets adjacent data value  
            if(data.getValue().equals(dataPrev.getValue()) && data.getValue().equals(dataNext.getValue())){
                ll.deleteNodeAtIndex(index);
                ll.deleteNodeAtIndex(indexPrev); 
                if (flag == 1) {
                    ll.deleteNodeAtIndex(0);
                }else{
                    ll.deleteNodeAtIndex(indexNext-2);    
                }
                break;
            }
        }
        CheckEvent(ll);
        CheckWin(ll);
    } 
      
    private static void Hit(int key, String value,int index,LinkedList ll){
        DataItems hit = new DataItems(key,value);
        ll.insertNodeAtIndex(index, hit);
        EventHit(ll);  
    }
}

class LinkedList {
    HeadNode head;
    
    public LinkedList(){
        head = new HeadNode();
    }
    public void insertNode(DataItems _data){
        Node newNode = new Node();
        newNode.setDataItems(_data);
        Node nextNode = head.getNextNode();
        head.setNextNode(newNode);
        newNode.setNextNode(nextNode);
    }
    public void deleteNode(){
        Node toBeDeletedNode = head.getNextNode();
        if(toBeDeletedNode != null){
            Node nextNode = toBeDeletedNode.getNextNode();
            head.setNextNode(nextNode);
            toBeDeletedNode.setNextNode(null);
        } 
   } 
     
    public DataItems searchByNode(int _node){
        int i = 0;
        DataItems dataReturn = null;
        DataItems data = dataAtNodeIndex(i);
        while(data != null){
            if(i == _node){
                dataReturn = data;
                return dataReturn;
            }
            i++;
            data = dataAtNodeIndex(i);
        }
        return dataReturn;
    }
   
    // reverse order of linked list
    public void reverseLinkedList(){
        int sizeOfList = size();
        Node lastNode = nodeAtIndex(sizeOfList-1);
        Node snode, tnode;
        for(int i = sizeOfList-2; i >= 0; i--){
            snode = nodeAtIndex(i);
            tnode = snode.getNextNode();
            tnode.setNextNode(snode);
        }
        nodeAtIndex(0).setNextNode(null);
        head.setNextNode(lastNode);
    }

    // insert a node at index
    public void insertNodeAtIndex(int _index, DataItems _data){
        Node newNode = new Node();
        newNode.setDataItems(_data);
        if(_index==0){
            insertNode(_data);
        } else{
            Node prevNode = nodeAtIndex(_index-1);
            if(prevNode != null){
                Node nextNode = prevNode.getNextNode();
                newNode.setNextNode(nextNode);
                prevNode.setNextNode(newNode);
            }
        }
    }

    // delete a node at index
    public void deleteNodeAtIndex(int _index){
        if(_index==0){
            deleteNode();
        } else{
            Node prevNode = nodeAtIndex(_index-1);
            if(prevNode != null){
                Node targetNode = prevNode.getNextNode();
                Node nextNode = targetNode.getNextNode();
                targetNode.setNextNode(null);
                prevNode.setNextNode(nextNode);
            }
        }
    }

    // return data item at particular node
    public DataItems dataAtNodeIndex(int _index){
        Node nodes = nodeAtIndex(_index);
        if(nodes != null){
            return nodes.getDataItems();
        } else{
            return null;
        }
    }

    // return node at particular index
    private Node nodeAtIndex(int _index){
        if(_index < 0){
            return null;
        } else{
            Node nodes = head.getNextNode();
            int i = 0;
            while(i < _index && nodes != null){
                nodes = nodes.getNextNode();
                i++;
            }
            return nodes;
        }
    }

    // return the size of linked list
    public int size(){
        int count = 0;
        Node nodes = nodeAtIndex(count);
        while(nodes!=null){
            nodes = nodeAtIndex(++count);
        }
        return count;
    }    
}

class Node {
    private DataItems dataItems;
    private Node nextNode;
    private Node prevNode;
    
    public void setNextNode(Node _nextNode){
        this.nextNode = _nextNode;
    }
    public void setPrevNode(Node _preNode){
        this.prevNode = _preNode;
    }
    public Node getNextNode(){
        return nextNode;
    }
    public DataItems getDataItems(){
        return dataItems;
    }
    public void setDataItems(DataItems _dataItems){
        this.dataItems = _dataItems;
    }
}

class DataItems {
    private final int key;
    private final String value;
    
    public DataItems(int _key, String _value){
        this.key = _key;
        this.value = _value;
    }
    public int getKey(){
       return key;
    }
    public String getValue(){
        return value;
    }
    @Override
    public String toString(){
        return "("+getKey()+","+getValue()+")";
    }
}

class HeadNode {
    Node nextNode;
    
    public void setNextNode(Node _nextNode){
        nextNode = _nextNode;
    }
    public Node getNextNode(){
        return nextNode;
    }  
}
