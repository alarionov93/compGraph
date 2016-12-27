package component;

import model.*;

import javax.swing.*;
import java.util.List;

public class Toolbar extends JToolBar {

    private EditorPanel editorPanel;

    private JButton segmentTButton;
    private JButton rectTButton;
    private JButton triangleTButton;
    private JButton circleTButton;


    public Toolbar(EditorPanel editorPanel) {
        this.editorPanel = editorPanel;
        allocateComponents();
    }

    public EditorPanel getEditorPanel() {
        return editorPanel;
    }

    public void setEditorPanel(EditorPanel editorPanel) {
        this.editorPanel = editorPanel;
    }

    private void allocateComponents(){

        addModeSelectionButtons();
        addSeparator();
        JButton delButton = new JButton("Удалить");
        delButton.addActionListener(e-> onDelClick());
        add(delButton);


    }
    private void addModeSelectionButtons(){

        segmentTButton = new JButton(new ImageIcon("src/main/resources/segment.png"));
        segmentTButton.addActionListener(e->onAddClick());
        add(segmentTButton);

        rectTButton = new JButton(new ImageIcon("src/main/resources/rect.png"));
        rectTButton.addActionListener(e->onAddClick());
        add(rectTButton);

        triangleTButton = new JButton(new ImageIcon("src/main/resources/triangle.png"));
        triangleTButton.addActionListener(e->onAddClick());
        add(triangleTButton);

        circleTButton = new JButton(new ImageIcon("src/main/resources/circle.png"));
        circleTButton.addActionListener(e->onAddClick());
        add(circleTButton);
    }

    private void onAddClick(){
        List<Drawable> drawables = editorPanel.getDrawableList();
        double maxX = editorPanel.getWidth();
        double maxY = editorPanel.getHeight();

        if(segmentTButton.getModel().isArmed())
            drawables.add(new Segment(maxX,maxY));
        else if(rectTButton.getModel().isArmed())
            drawables.add(new Rectangle(maxX,maxY));
        else if(triangleTButton.getModel().isArmed())
            drawables.add(new Triangle(maxX,maxY));
        else if(circleTButton.getModel().isArmed())
            drawables.add(new Circle(maxX, maxY));

        editorPanel.repaint();
    }

    private void onDelClick(){
        List<Drawable> drawables = editorPanel.getDrawableList();


        boolean flag = false;
        for(int i = 0; i < drawables.size(); i++)
            if (drawables.get(i).getDrawableState() == DrawableState.selected) {
                drawables.remove(i);
                flag = true;
            }
        if(!flag && drawables.size()>0)
            drawables.remove(drawables.size()-1);
        editorPanel.setDrawableList(drawables);
        editorPanel.repaint();
    }


}
