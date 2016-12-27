package component;

import model.*;
import recorder.component.RecordFrame;
import recorder.model.Record;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MenuBar extends JMenuBar {

    private EditorPanel editorPanel;
    private JToggleButton globalBtn;
    private JMenuItem fileItem;
    private JMenu fileMenu;
    private JToggleButton startRecBtn;
    private JButton saveBtn;

    private Record rec;

    public MenuBar(EditorPanel editorPanel) {
        this.editorPanel = editorPanel;
        editorPanel.setCurrMode(EditorPanel.LOCAL_MODE);
        addFileMenu();
        globalBtn = new JToggleButton(new ImageIcon("src/main/resources/global.png"));
        globalBtn.setToolTipText("Включить глобальные координаты");
        globalBtn.addActionListener(e -> onGlobalSelect());
        add(globalBtn);

        addRecordBtn();
    }

    private void addFileMenu() {
        fileMenu = new JMenu("Файл");
        fileItem = new JMenuItem("Открыть");
        fileItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        fileItem.addActionListener(e -> onOpenClick());
        fileMenu.add(fileItem);
        add(fileMenu);

        addSeparator();
    }

    private void addRecordBtn() {
        startRecBtn = new JToggleButton(new ImageIcon("src/main/resources/rec.png"));
        startRecBtn.setToolTipText("Начать запись");
        startRecBtn.addActionListener(e -> onStartStopRecClick());
        addSeparator();
        add(startRecBtn);
    }

    private void onStartStopRecClick() {
        if (startRecBtn.isSelected()) {
            startRecBtn.setIcon(new ImageIcon("src/main/resources/stopRec.png"));
            startRecBtn.setToolTipText("Закончить запись");
            startRecBtn.setEnabled(false);
            rec = new Record();
            saveBtn = new JButton(new ImageIcon("src/main/resources/save.png"));
            saveBtn.setToolTipText("Сохранить кадр");
            saveBtn.addActionListener(e -> onSaveFrameClick());
            add(saveBtn);
            updateUI();
        } else {
            saveFileDialog();
            startRecBtn.setIcon(new ImageIcon("src/main/resources/rec.png"));
            showRecordFrame(rec);
            remove(saveBtn);
            rec = null;
            updateUI();
        }
    }

    private void showRecordFrame(Record record){
        RecordFrame frame = new RecordFrame(record);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
        frame.setModal(true);
        frame.setLocationRelativeTo(editorPanel.getParent());
    }

    private void saveFileDialog() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Выберите файл для сохранения");
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "*.kgs", "kgs");
        fileChooser.setFileFilter(filter);
        fileChooser.setAcceptAllFileFilterUsed(false);
        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            if(!fileToSave.toString().endsWith(".kgs"))
                fileToSave = new File(fileToSave.toString()+".kgs");
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(fileToSave);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(rec);
                oos.flush();
                oos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e1) {

            }
        }
    }

    private void onSaveFrameClick() {
        List<Drawable> list = new ArrayList<>(editorPanel.getDrawableList());
        for(int i = 0;i<list.size();i++){
            if(list.get(i) instanceof Segment)
                list.set(i, new Segment((Segment) list.get(i)));
            else if(list.get(i) instanceof model.Rectangle) {
                list.set(i, new model.Rectangle((model.Rectangle) list.get(i)));
                System.out.println("rect");
            }
            else if(list.get(i) instanceof Triangle)
                list.set(i, new model.Triangle((model.Triangle) list.get(i)));
            else if (list.get(i) instanceof Circle)
                list.set(i, new Circle((Circle) list.get(i)));
        }
        rec.addList(list);
        if (rec.getFrameCount() > 1)
            startRecBtn.setEnabled(true);
    }

    private void onGlobalSelect() {
        if (globalBtn.isSelected()) {
            editorPanel.setCurrMode(EditorPanel.GLOBAL_MODE);
        } else editorPanel.setCurrMode(EditorPanel.LOCAL_MODE);
    }

    private void addSeparator() {
        JSeparator separator = new JSeparator(JSeparator.VERTICAL);
        add(separator);
    }

    private void onOpenClick() {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "*.kgs", "kgs");
        chooser.setFileFilter(filter);
        chooser.setAcceptAllFileFilterUsed(false);
        int returnVal = chooser.showOpenDialog(editorPanel.getParent());
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            System.out.println("Выберете файл с записью: " +
                    chooser.getSelectedFile().getName());

            FileInputStream fis = null;
            try {
                fis = new FileInputStream(chooser.getSelectedFile());
            ObjectInputStream oin = new ObjectInputStream(fis);
            showRecordFrame((Record) oin.readObject());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }catch (ClassNotFoundException e){

            }
        }
    }


}
