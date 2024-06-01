import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class CatType implements ActionListener{
    JFrame mainframe;
    JPanel menupanel,newgamepanel,resultpanel,highscorepanel;
    JButton startbutton,highscorebutton;
    static char choice;
    public CatType(){
        choice='c';
        Toolkit.getDefaultToolkit().setDynamicLayout(true);
        mainframe=new JFrame();
        mainframe.setTitle("CatType");
        mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainframe.setSize(1024,690);
        mainframe.setIconImage(new ImageIcon("../resources/icon.png").getImage());
        mainframe.getContentPane().setBackground(Color.decode("#808080"));
        mainframe.setLayout(null);
        //mainframe.setResizable(false);
        mainframe.setVisible(true);
        menupanel=new JPanel();
        newgamepanel=new JPanel();
        resultpanel=new JPanel();
        highscorepanel=new JPanel();
    }

    public JButton createbutton(String title){
        JButton button= new JButton();
        button.setText(title);
        button.setHorizontalTextPosition(JLabel.LEFT);
        button.setHorizontalAlignment(JLabel.LEFT);
        button.setVerticalTextPosition(JLabel.CENTER);
        button.setForeground(Color.decode("#5555ff"));
        button.setFont(new Font("Cooper Black", Font.PLAIN, 20));
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setBorderPainted(false);
        button.setFocusable(false);
        button.setLayout(null);
        return button;
    }

    public JPanel createpanel(String color){
        JPanel panel=new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.decode(color));
        panel.setSize(1024,960);
        panel.setOpaque(true);
        panel.setBorder(null);
        panel.setLayout(null);
        return panel;
    }

    @SuppressWarnings("resource")
    public static void main(String[] args) throws InterruptedException {
        System.out.println("enter y for GUI");
        char select='y';//new Scanner(System.in).next().charAt(0);
        if(select!='y'){
        wpmgame.main(args);
        System.exit(0);
        }
        CatType session=new CatType();
        session.menu();
    }

    @SuppressWarnings("resource")
    public void menu() throws InterruptedException{
        menupanel=createpanel("#808080");
        startbutton=createbutton("Start");
        startbutton.setBounds(500,250,180,30);
        startbutton.setActionCommand("start");
        startbutton.addActionListener(this);
        highscorebutton=createbutton("High Scores");
        highscorebutton.setBounds(500,280,180,30);
        mainframe.add(menupanel);
        menupanel.add(startbutton);
        menupanel.add(highscorebutton);
    }

    @SuppressWarnings("resource")
    public  void newgame() throws InterruptedException{
        newgamepanel=createpanel("#0FF000");
        mainframe.add(newgamepanel);
        mainframe.repaint();
        Thread.sleep(1500);
    }

    @SuppressWarnings("resource")
    public void result() throws InterruptedException{
        resultpanel=createpanel("#0F0F0F");
        mainframe.add(resultpanel);
        mainframe.repaint();
    }

    @SuppressWarnings("resource")
    public void highscore() throws InterruptedException{
        highscorepanel=createpanel("#000FF0");
        mainframe.add(highscorepanel);
        mainframe.repaint();
    }
    
    public void gotonext() throws InterruptedException{
        switch(choice){
            case'n':startbutton.removeActionListener(this);
                    mainframe.remove(menupanel);
                    newgame();
                    break;
        }
    }

    @Override
    public void actionPerformed(ActionEvent act){
        if ("start".equals(act.getActionCommand())) {
            System.out.println("djf");
            choice='n';
            try {
                gotonext();
            } catch (InterruptedException e) {
            }
        }
    }

}