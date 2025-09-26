package javaAPP;

import javax.swing.*;
import java.awt.*;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Splash extends JFrame {

    Splash(){
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("projectImages/splash.gif"));
        Image i2=i1.getImage().getScaledInstance(700,550, Image.SCALE_DEFAULT);
        ImageIcon i3= new ImageIcon(i2);
        JLabel image=new JLabel(i3);
        image.setBounds(0,0,700,550);
        add(image);

        setSize(700,550);
        setLocation(300,50);
        setLayout(null);
        setVisible(true);

        // Fix: Use a Timer to prevent the GUI from freezing
        Timer timer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new Login();
            }
        });
        timer.setRepeats(false); // Make the timer run only once
        timer.start();
    }
    public static void main(String[] args) {
        new Splash();
    }
}