package form;

import com.alee.laf.WebLookAndFeel;
import component.EditorPanel;
import component.MenuBar;
import component.Toolbar;

import javax.swing.*;
import java.awt.*;

public class MainForm extends JFrame{

    private JPanel mainPanel;

    public MainForm(){

        try {
            UIManager.setLookAndFeel(new WebLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        mainPanel = new JPanel(new BorderLayout());
        
        EditorPanel editorPanel = new EditorPanel();
        editorPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        mainPanel.setPreferredSize(new Dimension(500,500));
        mainPanel.add(editorPanel, BorderLayout.CENTER);

        Toolbar toolbar = new Toolbar(editorPanel);
        toolbar.setFloatable(false);
        toolbar.setOrientation(SwingConstants.VERTICAL);
        mainPanel.add(toolbar, BorderLayout.EAST);

        add(mainPanel);

        setJMenuBar(new MenuBar(editorPanel));
        pack();
    }
}
