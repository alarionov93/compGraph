package recorder.component;

import model.Drawable;
import recorder.model.Record;
import recorder.thread.MorfingRunnable;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class RecordPanel extends JPanel {
    private Record record;
    private Timer timer;
    private RecordFrame frame;
    public static final int FPS = 120;
    public static final double NANO_IN_SEC = 1000;
    private java.util.List<Drawable> drawables;
    private double sec;

    private Thread thread;

    public RecordPanel(Record record, RecordFrame frame, double sec) {
        this.record = record;
        setPreferredSize(record.panelDimension());
        this.frame = frame;
        this.sec = sec;
        timer = new Timer((int)((NANO_IN_SEC*sec)/FPS),new PaintTimer());
        timer.start();
        drawables = record.getCurrent();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;

        for(Drawable d:drawables)
            d.draw(graphics2D);
    }



    public synchronized void startTimer(){
        stopTimer();
        thread = new Thread(new MorfingRunnable(this));
        thread.setPriority(Thread.NORM_PRIORITY);
        thread.start();
    }

    public synchronized void startReverseTimer(){
        stopTimer();
        timer = new Timer((int)((sec*NANO_IN_SEC)/FPS), new ReverseRecorderActionListener());
        timer.start();
    }

    public synchronized void stopTimer(){
        drawables = record.getCurrent();
        if(thread!=null)
            thread.interrupt();
        repaint();
    }

    public void nextFrame(){
        drawables = record.next();
        frame.updateLabel();
        repaint();

    }

    public void previousFrame(){
        drawables = record.previous();
        frame.updateLabel();
        repaint();
    }

    public Record getRecord() {
        return record;
    }

    private class PaintTimer implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
                repaint();
        }

    }

    public List<Drawable> getDrawables() {
        return drawables;
    }

    public void setDrawables(List<Drawable> drawables) {
        this.drawables = drawables;
    }

    public Thread getThread() {
        return thread;
    }

    private class ReverseRecorderActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            previousFrame();
        }

    }



}