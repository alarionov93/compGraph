import com.alee.laf.WebLookAndFeel;
import form.MainForm;

import javax.swing.*;

public class Main {

    public static void main(String ... args){
        try {
            UIManager.setLookAndFeel ( WebLookAndFeel.class.getCanonicalName () );
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        MainForm form = new MainForm();
        form.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        form.setVisible(true);

    }

}
