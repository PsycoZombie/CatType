import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.Scanner;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.text.html.HTMLEditorKit;

@SuppressWarnings("resource")
public class CatType
    implements ActionListener, KeyListener, MouseListener, ChangeListener {
  JFrame mainframe;
  Border border;
  JPanel menupanel, newgamepanel, resultpanel ;
  JButton startbutton, highscorebutton;
  JLabel catlabel;
  Color pzcolor, themecolor;
  JSpinner nofwords;
  JToggleButton cap;
  static char choice;
  int diff, flag;
  float time, accuracy, wpm, cps;
  Font me;
  String answerstring, questionstring;
  String[] wquestion;
  String[] wanswer;
  LinkedList<lb> wll;
  noPasteTextField answer;
  public CatType() {
    me = new Font("Comic Sans MS", Font.PLAIN, 16);
    diff = 20;
    flag = 0;
    wll = new LinkedList<>();
    pzcolor = Color.decode("#808080");
    themecolor = Color.decode("#3A3AC5");
    time = 0;
    accuracy = 0;
    wpm = 0;
    cps = 0;
    answerstring = "";
    questionstring = "";
    choice = 'c';
    cap = new JToggleButton();
    Toolkit.getDefaultToolkit().setDynamicLayout(true);
    mainframe = new JFrame();
    border=new LineBorder(Color.decode("#808080"),0,false);
    mainframe.setTitle("CatType");
    mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    mainframe.setSize(1024, 690);
    mainframe.setPreferredSize(new Dimension(1024, 690));
    mainframe.setLocationRelativeTo(null);
    mainframe.setIconImage(new ImageIcon("../res/icon.png").getImage());
    mainframe.getContentPane().setBackground(pzcolor);
    cap.setText("Capitalise");
    mainframe.setLayout(null);
    mainframe.setResizable(false);
    mainframe.setVisible(true);
    nofwords = new JSpinner();
    menupanel = new JPanel();
    newgamepanel = new JPanel();
    resultpanel = new JPanel();
    answer = new noPasteTextField();
  }

  public JButton createbutton(String title) {
    JButton button = new JButton();
    button.setText(title);
    button.setBackground(Color.decode("#BABADF"));
    button.setHorizontalTextPosition(JLabel.LEFT);
    button.setHorizontalAlignment(JLabel.LEFT);
    button.setVerticalTextPosition(JLabel.CENTER);
    button.setForeground(themecolor);
    button.setFont(me);
    button.setBorder(border);
    button.setContentAreaFilled(true);
    button.setOpaque(true);
    button.setFocusable(false);
    button.setLayout(null);
    return button;
  }

  public JPanel createpanel(String color) {
    JPanel panel = new JPanel();
    panel.setLayout(null);
    panel.setBackground(Color.decode(color));
    panel.setSize(1024, 960);
    panel.setOpaque(true);
    panel.setBorder(null);
    panel.setLayout(null);
    return panel;
  }

  public static void main(String[] args) throws InterruptedException {
    System.out.println("enter t for \033[5mTUI\033[0m or any other for \033[5mGUI\033[0m");
    if (new Scanner(System.in).next().charAt(0) == 't') {
          wpmgame.main(args);
          System.exit(0);
    }
    System.out.println("initiating GUI");
      CatType session = new CatType();
      session.menu();
    }

  public void menu() {
    clear();
    menupanel = createpanel("#808080");
    startbutton = createbutton("New Game");
    startbutton.setContentAreaFilled(false);
    startbutton.setBorder(null);
    catlabel = new JLabel();
    catlabel.setIcon(new ImageIcon("../res/catdown.png"));
    catlabel.setBounds(250, 180, 200, 200);
    catlabel.addMouseListener(this);
    menupanel.add(catlabel);
    startbutton.setBounds(450, 250, 300, 55);
    startbutton.setFont(new Font("Comic Sans MS", Font.BOLD, 50));
    startbutton.setActionCommand("start");
    startbutton.addActionListener(this);
    highscorebutton = createbutton("High Scores");
    highscorebutton.setBounds(500, 280, 300, 30);
    highscorebutton.setActionCommand("highscore");
    highscorebutton.addActionListener(this);
    highscorebutton.setFont(me);
    mainframe.add(menupanel);
    menupanel.add(startbutton);
    mainframe.repaint();
  }

  public void newgame() {
    clear();
    questionstring = "";
    answerstring = "";
    wquestion = new wpmgame(diff).question(flag);
      for (String wquestion1 : wquestion) {
          questionstring += wquestion1 + " ";
      }
    newgamepanel = createpanel("#808080");
    JPanel questionpanel = createpanel("#808080");
    questionpanel.setBounds(15, 10, 980, 225);
    JTextArea question = new JTextArea(questionstring);
    question.setTransferHandler(null);
    question.setEditable(false);
    question.setBackground(pzcolor);
    question.getCaret().deinstall(question);
    question.setLineWrap(true);
    question.setFont(me);
    question.setVisible(true);
    question.setOpaque(true);
    question.setBounds(5, 5, 970, 200);
    questionpanel.add(question);
    answer.setText("");
    answer.setBounds(5, 5, 970, 200);
    answer.setVisible(true);
    answer.setOpaque(true);
    answer.setBackground(Color.decode("#696969"));
    answer.setFont(me);
    answer.setForeground(Color.decode("#FFFFFF"));
    answer.setCaretColor(Color.BLACK);
    answer.setLineWrap(true);
    JLabel dialog = new JLabel("Start Typing!");
    dialog.setFont(me);
    dialog.setForeground(themecolor);
    dialog.setBounds(20, 225, 980, 25);
    mainframe.add(dialog);
    nofwords = new JSpinner(new SpinnerNumberModel(diff, 5, 123, 5));
    nofwords.setBounds(50, 550, 120, 34);
    nofwords.setVisible(true);
    nofwords.setFont(me);
    nofwords.setOpaque(true);
    nofwords.setForeground(themecolor);
    nofwords.setFocusable(false);
    nofwords.addChangeListener(this);
    nofwords.setBorder(border);
    nofwords.getEditor().getComponent(0).setBackground(Color.decode("#BABADF"));
    nofwords.getEditor().getComponent(0).setForeground(themecolor);
    nofwords.getEditor().getComponent(0).setName("jdf");
    nofwords.getEditor().getComponent(0).setFont(me);
    startbutton.setText("refresh");
    startbutton.setFont(me);
    startbutton.setBorder(border);
    startbutton.setContentAreaFilled(true);
    startbutton.setHorizontalAlignment(JButton.CENTER);
    startbutton.setBounds(450, 550, 120, 34);
    startbutton.addActionListener(this);
    JPanel answerpanel = createpanel("#808080");
    answerpanel.setBounds(15, 255, 980, 225);
    answerpanel.add(answer);
    catlabel.setIcon(new ImageIcon("../res/catup.png"));
    catlabel.setOpaque(false);
    catlabel.setBounds(730, 454, 200, 200);
    catlabel.setLayout(null);
    catlabel.setVisible(true);
    cap.setBounds(250, 550, 120, 34);
    cap.setBorder(border);
    cap.setFocusable(false);
    cap.setFont(me);
    cap.setBackground(Color.decode("#BABADF"));
    cap.setForeground(themecolor);
    cap.addActionListener(this);
    cap.setActionCommand("on");
    newgamepanel.add(cap);
    newgamepanel.add(nofwords);
    newgamepanel.add(startbutton);
    newgamepanel.add(questionpanel);
    newgamepanel.add(answerpanel);
    newgamepanel.add(catlabel);
    mainframe.add(newgamepanel);
    answer.addKeyListener(this);
    mainframe.repaint();
  }

  public void result() {
    clear();
    resultpanel = createpanel("#808080");
    resultpanel.setBounds(0, 0, 1024, 690);
    resultpanel.setBackground(pzcolor);
    JPanel analysis = createpanel("#808080");
    analysis.setBounds(15, 255, 980, 225);
    JEditorPane pane = new JEditorPane();
    pane.setFocusable(false);
    pane.setBackground(Color.decode("#696969"));
    pane.setEditorKit(new HTMLEditorKit());
    pane.setEditable(false);
    pane.setText(calcresult(wquestion, wanswer));
    pane.setBounds(5, 5, 980, 225);
    analysis.add(pane);
    JEditorPane wpmpane = new JEditorPane();
    wpmpane.setFocusable(false);
    wpmpane.setBackground(pzcolor);
    wpmpane.setEditorKit(new HTMLEditorKit());
    wpmpane.setEditable(false);
    wpmpane.setText(
        "<html><body><p><span style=\"font-family: Comic Sans MS; font-size: 30; color: #6A5ACD ;\"<span style=\"font-size: 96;\">"
        + String.format("\n%.2f", wpm)
        + "</span> wpm</span></p></body></html>");
    wpmpane.setBounds(15, 10, 475, 225);
    JEditorPane cpspane = new JEditorPane();
    cpspane.setFocusable(false);
    cpspane.setBackground(pzcolor);
    cpspane.setEditorKit(new HTMLEditorKit());
    cpspane.setEditable(false);
    cpspane.setText(
        "<html><body><p><span style=\"font-family: Comic Sans MS; font-size: 30; color: #6A5ACD ;\"<span style=\"font-size: 96;\">"
        + String.format("\n%.2f", cps)
        + "</span> cps</span></p></body></html>");
    cpspane.setBounds(500, 10, 475, 225);
    resultpanel.add(wpmpane);
    resultpanel.add(cpspane);
    startbutton.setText("New Game");
    startbutton.setSize(180, 30);
    startbutton.setFont(me);
    startbutton.setLocation(280, 590);
    highscorebutton.setLocation(580, 590);
    highscorebutton.addActionListener(this);
    highscorebutton.setHorizontalAlignment(JButton.CENTER);
    highscorebutton.setSize(169, 30);
    startbutton.addActionListener(this);
    resultpanel.add(startbutton);
    resultpanel.add(highscorebutton);
    analysis.add(pane);
    resultpanel.add(analysis);
    mainframe.add(resultpanel);
    wpm = 0;
    cps = 0;
    accuracy = 0;
    time = 0;
    mainframe.repaint();
  }

  public String calcresult(String[] q, String[] a) {
    clear();
    int k , words = 0, chars = 0;
    String ans =
        "<html><body><b><span style=\" font-family: Comic Sans MS; font-size: 15;\">";
    for (int i = 0; i < Math.min(q.length, a.length); i++) {
      for (k = i; " ".equals(a[k]); k++) {
      }
      if (q[i].equals(a[k])) {
        words++;
      }
    }
    chars += words;
    for (int i = 0; i < Math.min(a.length, q.length); i++) {
      for (int j = 0; j < Math.min(a[i].length(), q[i].length()); j++) {
        if (a[i].charAt(j) == q[i].charAt(j)) {
          chars++;
          ans += ("<span style=\" color: #69FF42;\">" + a[i].charAt(j)
              + "</span>");
        } else
          ans +=
              ("<span style=\" text-decoration: underline; color: #FF6942;\">"
                  + a[i].charAt(j) + "</span>");
      }
      ans += "  ";
    }
    ans += "</span></b></html></body>";
    accuracy = 100 * ((float) chars / (float) questionstring.length());
    wpm = (float) (60 * words) / (float) time;
    cps = (float) chars / (float) time;
    addtolb(wpm, cps, accuracy, time);
    return ans;
  }

  public void addtolb(float wpm, float cps, float accuracy, float time) {
    lb curr;
    curr = new lb(wpm, cps, accuracy, time);
    if (choice != 'h' && accuracy >= 50.0 && time >= 3.0) {
      float s = (float) (curr.wpm);
      for (int j = 0; j <= wll.size(); j++) {
        if (wll.isEmpty()) {
          wll.add(j, curr);
          break;
        } else if (s <= wll.get(j).wpm) {
          wll.add(j, curr);
          break;
        } else if (j + 1 == wll.size()) {
          wll.add(j + 1, curr);
          break;
        }
        if (s <= wll.get(j + 1).wpm) {
          wll.add(j + 1, curr);
          break;
        }
      }
    }
  }

  public void highscore() {
    clear();
    JTable table = new JTable();
    DefaultTableModel model = (DefaultTableModel) table.getModel();
    JTableHeader header = table.getTableHeader();
    model.addColumn("WPM");
    model.addColumn("CPS");
    model.addColumn("ACCURACY");
    model.addColumn("TIME");
    Object[] row;
    for (int i = wll.size() - 1; i >= 0; i--) {
      row = new Object[4];
      row[0] = wll.get(i).wpm;
      row[1] = wll.get(i).cps;
      row[2] = wll.get(i).accuracy;
      row[3] = wll.get(i).time;
      model.addRow(row);
    }
    header.setFont(me);
    header.setBackground(Color.WHITE);
    header.setForeground(Color.black);
    table.setOpaque(true);
    table.setBackground(Color.GRAY);
    table.setForeground(Color.BLACK);
    table.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
    table.setVisible(true);
    table.setBounds(222, 40, 590, 530);
    header.setBounds(222, 10, 590, 30);
    header.setFocusable(false);
    mainframe.add(header);
    mainframe.add(table);
    mainframe.pack();
    startbutton.setSize(130, 30);
    startbutton.setFont(me);
    startbutton.addActionListener(this);
    startbutton.setLocation(450, 600);
    mainframe.add(startbutton);
    mainframe.repaint();
  }

  public void clear() {
    mainframe.getContentPane().removeAll();
  }

  /**
   * 
   */
  public void gotonext() {
    switch (choice) {
      case 'n' :
          startbutton.removeActionListener(this);
          highscorebutton.removeActionListener(this);
          answer.removeKeyListener(this);
          cap.removeKeyListener(this);
          clear();
          newgame();
          break;
      case 'h' :
          highscorebutton.removeActionListener(this);
          startbutton.removeActionListener(this);
          answer.removeKeyListener(this);
          clear();
          highscore();
          break;
      case 'r' :
          answer.removeKeyListener(this);
          highscorebutton.removeActionListener(this);
          startbutton.removeActionListener(this);
          clear();
          result();
          break;
    }
  }

  @Override
  public void actionPerformed(ActionEvent act) {
    if ("start".equals(act.getActionCommand())) {
      choice = 'n';
      gotonext();
    }
    if ("highscore".equals(act.getActionCommand())) {
      choice = 'h';
      gotonext();
    }
    if ("on".equals(act.getActionCommand())) {
      if (cap.isSelected()) {
        flag = 1;
        cap.setText("Abc");
      } else {
        flag = 0;
        cap.setText("abc");
      }
    }
  }

  @Override
  public void keyTyped(KeyEvent e) {
    if (time == 0.0)
      time = (float) LocalTime.now().toNanoOfDay();
    if ("qwertasdfghzxcvb123456".contains(String.valueOf(e.getKeyChar())))
      catlabel.setIcon(new ImageIcon("../res/catleft.png"));
    else if ("7890-=yuiop[]\\jkl;'nm,../res/".contains(
                 String.valueOf(e.getKeyChar())))
      catlabel.setIcon(new ImageIcon("../res/catright.png"));
    else
      catlabel.setIcon(new ImageIcon("../res/catdown.png"));
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == 10) {
      if (time != 0) {
        time = LocalTime.now().toNanoOfDay() - time;
        time /= 1000000000.0;
        choice = 'r';
        answerstring = answer.getText();
        wanswer = answerstring.split("\\s+");
        gotonext();
      } else if (time == 0) {
        answer.setText(null);
        choice = 'n';
        gotonext();
      }
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    catlabel.setIcon(new ImageIcon("../res/catup.png"));
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    try {
      java.awt.Desktop.getDesktop().browse(
          new URI("https://www.youtube.com/watch?v=4PTXSKdr40k&t=19s"));
    } catch (IOException | URISyntaxException e1) {
    }
  }

  @Override
  public void mousePressed(MouseEvent e) {
    catlabel.setIcon(new ImageIcon("../res/catdown.png"));
    mainframe.pack();
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    catlabel.setIcon(new ImageIcon("../res/catup.png"));
    mainframe.pack();
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    catlabel.setIcon(new ImageIcon("../res/catup.png"));
    mainframe.pack();
  }

  @Override
  public void mouseExited(MouseEvent e) {
    catlabel.setIcon(new ImageIcon("../res/catdown.png"));
    mainframe.pack();
  }

  @Override
  public void stateChanged(ChangeEvent e) {
    diff = (int) nofwords.getValue();
  }
}
