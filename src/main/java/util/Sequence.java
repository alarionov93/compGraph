package util;

public class Sequence {
    int curr;

    private  static Sequence sequence;

    private Sequence() {
        curr=-1;
    }

    public static Sequence intsance(){
        if(sequence==null)
            sequence = new Sequence();
        return sequence;
    }

    public int getNext(){
        curr++;
        return curr;
    }
}
