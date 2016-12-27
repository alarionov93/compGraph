package recorder.component;

import recorder.model.Record;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class RecordFrame extends JDialog{

    private JButton nextButton;
    private JButton prevButton;
    private JToggleButton recordButton;
    private JToggleButton recordReverseButton;
    private RecordPanel recordPanel;
    private JLabel label;

    public RecordFrame(Record rec){
        BoxLayout boxLayout = new BoxLayout(getContentPane(), BoxLayout.Y_AXIS);
        getContentPane().setLayout(boxLayout);
        recordPanel = new RecordPanel(rec, this, 2.);
        add(recordPanel);
        createToolbar();
        pack();
        setMinimumSize(getSize());
    }

    public void createToolbar(){
        JToolBar toolbar = new JToolBar(SwingConstants.HORIZONTAL);
        nextButton = new JButton(new ImageIcon("src/main/resources/next.png"));
        nextButton.addActionListener(e->onNextBtnClick());
        prevButton = new JButton(new ImageIcon("src/main/resources/prev.png"));
        prevButton.addActionListener(e->onPrevBtnClick());
        recordButton = new JToggleButton(new ImageIcon("src/main/resources/rec.png"));
        recordButton.addActionListener(e->onRecordButtonClick());
        recordReverseButton = new JToggleButton(new ImageIcon("src/main/resources/recReverse.png"));
        recordReverseButton.addActionListener(e->onReverseRecClick());

        toolbar.add(prevButton);
        toolbar.add(nextButton);
        toolbar.addSeparator();
        toolbar.add(recordReverseButton);
        toolbar.add(recordButton);

        toolbar.setFloatable(false);

        label = new JLabel();
        updateLabel();
        add(label);

        add(toolbar);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                recordPanel.stopTimer();
            }
        });
    }

    private void onNextBtnClick(){
        recordPanel.nextFrame();
    }

    private void onPrevBtnClick(){
        recordPanel.previousFrame();
    }

    private void onRecordButtonClick(){
        if(recordButton.isSelected()) {
            nextButton.setEnabled(false);
            prevButton.setEnabled(false);
            recordReverseButton.setEnabled(false);
            recordButton.setIcon(new ImageIcon("src/main/resources/stopRec.png"));
            recordPanel.startTimer();
        }
        else {
            nextButton.setEnabled(true);
            prevButton.setEnabled(true);
            recordReverseButton.setEnabled(true);
            recordButton.setIcon(new ImageIcon("src/main/resources/rec.png"));
            recordPanel.stopTimer();


        }
    }

    private void onReverseRecClick(){
        if(recordReverseButton.isSelected()) {
            nextButton.setEnabled(false);
            prevButton.setEnabled(false);
            recordButton.setEnabled(false);
            recordReverseButton.setIcon(new ImageIcon("src/main/resources/stopRec.png"));
            recordPanel.startReverseTimer();
        }
        else {
            nextButton.setEnabled(true);
            prevButton.setEnabled(true);
            recordButton.setEnabled(true);
            recordReverseButton.setIcon(new ImageIcon("src/main/resources/recReverse.png"));
            recordPanel.stopTimer();


        }
    }

    public void updateLabel(){
        String s = "Кадр №%s из %s";
        String s1 = String.format(s,
                recordPanel.getRecord().getCurrFrameInd()+1+"",
                recordPanel.getRecord().getFrameCount()+"");
        label.setText(s1);
    }
}
