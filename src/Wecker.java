import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Wecker extends JFrame {

    private static final long serialVersionUID = 1L;
    public static int minClicks = 1800;
    public static Timer timer;
    JLabel zeit;
    boolean display5Next = true;
    ImageIcon icon = new ImageIcon("D:\\programming\\timer\\lerning timer\\Resources\\sanduhr.png");
    private JButton plusTen, plusFive, plusOne, minusTen, minusFive, minusOne, start, reset, pause;
    private JPanel minusSide, plusSide, startReset;

    public Wecker() {
        bauen();
        addButton();
        methoden();
    }

    public static void main(String[] args) {
        JFrame frame = new Wecker();
        frame.setVisible(true);
    }

    public void bauen() {
        this.setSize(380, 330);
        this.setLocationRelativeTo(null);
        this.setTitle("Timer");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setAlwaysOnTop(false);
        this.setFocusable(true);
        this.requestFocus();
        this.setIconImage(icon.getImage());

        plusTen = new JButton("+ 10");
        plusTen.setFont(new Font("Verdana", Font.BOLD, 24));
        plusTen.addActionListener(new plusTenAction());
        plusFive = new JButton("+ 5");
        plusFive.setFont(new Font("Verdana", Font.BOLD, 24));
        plusFive.addActionListener(new plusFiveAction());
        plusOne = new JButton("+ 1");
        plusOne.setFont(new Font("Verdana", Font.BOLD, 24));
        plusOne.addActionListener(new plusOneAction());
        minusTen = new JButton("- 10");
        minusTen.setFont(new Font("Verdana", Font.BOLD, 24));
        minusTen.addActionListener(new minusTenAction());
        minusFive = new JButton("- 5");
        minusFive.setFont(new Font("Verdana", Font.BOLD, 24));
        minusFive.addActionListener(new minusFiveAction());
        minusOne = new JButton("- 1");
        minusOne.setFont(new Font("Verdana", Font.BOLD, 24));
        minusOne.addActionListener(new minusOneAction());
        pause = new JButton("Pause");
        pause.setFont(new Font("Verdana", Font.BOLD, 14));
        pause.addActionListener(new PauseAction());
        pause.setEnabled(false);

        start = new JButton("Start");
        start.setFont(new Font("Verdana", Font.BOLD, 14));
        start.addActionListener(new startAction());
        reset = new JButton("Reset");
        reset.setFont(new Font("Verdana", Font.BOLD, 14));
        reset.addActionListener(new resetAction());
        zeit = new JLabel("30:00");
        zeit.setHorizontalAlignment(JLabel.CENTER);
        zeit.setFont(new Font("Verdana", Font.PLAIN, 42));
        zeit.setOpaque(true);
        zeit.setBackground(null);
    }

    public void addButton() {

        minusSide = new JPanel();
        minusSide.setLayout(new GridLayout(3, 1));
        minusSide.add(minusTen);
        minusSide.add(minusFive);
        minusSide.add(minusOne);

        plusSide = new JPanel();
        plusSide.setLayout(new GridLayout(3, 1));
        plusSide.add(plusTen);
        plusSide.add(plusFive);
        plusSide.add(plusOne);

        startReset = new JPanel();
        startReset.setLayout(new GridLayout(1, 2));
        startReset.add(start);
        startReset.add(reset);

        getContentPane().add(startReset, BorderLayout.PAGE_START);
        getContentPane().add(plusSide, BorderLayout.LINE_START);
        getContentPane().add(minusSide, BorderLayout.LINE_END);
        getContentPane().add(pause, BorderLayout.PAGE_END);
        getContentPane().add(zeit, BorderLayout.CENTER);
    }

    public void methoden() {
        Wecker.timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Wecker.minClicks--;
                    zeit.setText(displayTime());
                if (minClicks == 3) {
                    setState(NORMAL);
                    setAlwaysOnTop(true);
                }
                if (minClicks == 2) {
                    java.awt.Toolkit.getDefaultToolkit().beep();
                }
                if (minClicks == 1) {
                    java.awt.Toolkit.getDefaultToolkit().beep();
                }
                if (minClicks == 0) {
                    Wecker.timer.stop();
                    start.setBackground(null);
                    pause.setEnabled(false);
                    minusTen.setEnabled(true);
                    minusFive.setEnabled(true);
                    minusOne.setEnabled(true);
                    setAlwaysOnTop(false);
                    java.awt.Toolkit.getDefaultToolkit().beep();
                    if(display5Next){
                        minClicks = 5*60;
                        display5Next = !display5Next;
                    }else{
                        minClicks = 30*60;
                        display5Next = !display5Next;
                    }
                    zeit.setText(displayTime());
                }
            }
        });
    }

    public String displayTime() {
        if (minClicks % 60 != 0) {
            if (minClicks % 60 >= 10) return ("" + minClicks / 60 + ":" + minClicks % 60);
            else return ("" + minClicks / 60 + ":0" + minClicks % 60);
        } else return ("" + minClicks / 60 + ":00");
    }

    private class plusTenAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            minClicks += 600;
            zeit.setText(displayTime());
            if (minClicks > 0) {
                start.setBackground(Color.green);
            }

        }
    }

    private class plusFiveAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            minClicks += 300;
            zeit.setText(displayTime());
            if (minClicks > 0) {
                start.setBackground(Color.green);
            }
        }
    }

    private class plusOneAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            minClicks += 60;
            zeit.setText(displayTime());
            if (minClicks > 0) {
                start.setBackground(Color.green);
            }
        }
    }

    private class minusTenAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (minClicks >= 1) {
                minClicks -= 600;
            }
            if (minClicks == 0) {
                start.setBackground(null);
            }
            zeit.setText(displayTime());
        }
    }

    private class minusFiveAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (minClicks >= 1) {
                minClicks -= 300;
            }
            if (minClicks == 0) {
                start.setBackground(null);
            }
            zeit.setText(displayTime());
        }
    }

    private class minusOneAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (minClicks >= 1) {
                minClicks -= 60;
            }
            if (minClicks == 0) {
                start.setBackground(null);
            }
            zeit.setText(displayTime());
        }
    }

    private class resetAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Wecker.timer.stop();
            minusTen.setEnabled(true);
            minusFive.setEnabled(true);
            minusOne.setEnabled(true);
            pause.setEnabled(false);
            minClicks = 0;
            start.setBackground(null);
            zeit.setText(displayTime());
        }
    }

    private class startAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (minClicks > 0) {
                zeit.setText(displayTime());
                timer.start();
                minusTen.setEnabled(false);
                minusFive.setEnabled(false);
                minusOne.setEnabled(false);
                pause.setEnabled(true);
            }
        }
    }

    private class PauseAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            if (timer.isRunning()) {
                timer.stop();
                zeit.setText("<html>" + displayTime() + "<br>Paused</html>");
                pause.setText("Resume");
            } else {
                timer.start();
                zeit.setText(displayTime());
                pause.setText("Pause");
            }
        }
    }

}