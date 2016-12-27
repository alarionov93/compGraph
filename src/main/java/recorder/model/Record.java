package recorder.model;

import model.*;
import model.Point;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.concurrent.*;
import java.util.function.Consumer;

/**
 * Created by arsen on 17.10.16.
 */
public class Record implements Serializable{
    private List<List<Drawable>> record;

    private int currInd;


    public Record() {
        record = new ArrayList<>();
        currInd = 0;
    }

    public Record(Record rec){
        record = rec.record;
        currInd = rec.currInd;
    }

    public void addList(List<Drawable> list){
        record.add(list);
    }

    public Dimension panelDimension(){
        Point maxPoint = new Point(0, 0);
        for(List<Drawable> dList : record)
            for (Drawable d: dList)
                if(maxPoint.compareTo(d.farrestPoint()) == -1)
                    maxPoint = d.farrestPoint();
        return new Dimension((int)maxPoint.getX()+1, (int) maxPoint.getY()+1);
    }

    public List<Drawable> next(){
        currInd = getNextInd();
        return record.get(currInd);
    }

    public List<Drawable> previous(){
        currInd = getPrevInd();
        return record.get(currInd);
    }

    public int getCurrFrameInd(){
        return currInd;
    }

    public int getFrameCount(){
        return record.size();
    }

    public List<Drawable> getCurrent(){
        return record.get(currInd);
    }

    public synchronized List<Drawable> getMorf(double t){
        List<Drawable> curr = record.get(currInd);
        List<Drawable> next = record.get(getNextInd());
        List<Drawable> res = new ArrayList<>();
        for(int i = 0; i<next.size();i++){
            int ind = curr.indexOf(next.get(i));
            if(ind!=-1)
                res.add(curr.get(ind).morf(t, next.get(i)));
//            for (int j = 0; j<curr.size(); j++) {
//                if (curr.get(j).getId() == next.get(i).getId()) {
//                    res.add(curr.get(j).morf(t, next.get(i)));
//                    break;
//                }
//            }
        }
//        for (int i = 0; i<res.size(); i++)
//            for(int j = 0; j<next.size(); j++)
//                if(res.get(i).getId() != next.get(j).getId()) {
//                    res.add(next.get(j).morf(1.-t));
//                    break;
//                }
//        for (int i = 0; i<res.size(); i++)
//            for(int j = 0; j<curr.size(); j++)
//                if(res.get(i).getId() != curr.get(j).getId()) {
//                    res.add(curr.get(j).morf(t));
//                    break;
//                }

        return res;
    }

    private int getNextInd(){
        if (currInd == record.size() - 1)
            return 0;
        else return currInd+1;
    }

    private int getPrevInd(){
        if(currInd==0)
            return record.size()-1;
        else return currInd-1;
    }

}
