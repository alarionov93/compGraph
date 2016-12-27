package recorder.thread;

import model.Drawable;
import recorder.component.RecordPanel;
import recorder.model.Record;

import java.util.List;

/**
 * Created by arsen on 27.10.16.
 */
public class MorfingRunnable implements Runnable {

    RecordPanel recordPanel;

    public MorfingRunnable(RecordPanel rec) {
        this.recordPanel = rec;
    }

    @Override
    public void run() {
        while(!Thread.interrupted()){
            Record record = this.recordPanel.getRecord();
            for(int i = 0; i<= RecordPanel.FPS; i++){
                List<Drawable> drawableList = record.getMorf((double) i/(double) RecordPanel.FPS);
                recordPanel.setDrawables(drawableList);
                try {
                    Thread.sleep((long)(RecordPanel.NANO_IN_SEC/RecordPanel.FPS));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("changed");
            }
            recordPanel.nextFrame();
        }
        Thread.currentThread().interrupt();
    }
}
