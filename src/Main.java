import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.lang.Math;

public
    class Main
    extends Frame {

    private final int MAX_NUM = (800 * 800) * 10;

    public static void main(String[] args) {
        new Main();
    }

    public Main() {
        this.setSize(800, 800);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("S26630Set01");

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        saveToFile();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        drawSpiral(g);
    }

    public void drawSpiral(Graphics g) {
        int x = this.getWidth() / 2;
        int y = this.getHeight() / 2;
        int direction = 0;
        int stepsToTurn = 1;
        int stepCounter = 0;
        int num = 1;
        int space = 1;

        while (num <= MAX_NUM) {
            if (isPrime(num)) {
                g.setColor(Color.BLACK);
            }
            else {
                g.setColor(Color.WHITE);
            }

            g.drawLine(x, y, x, y);

            if (stepCounter == stepsToTurn) {
                direction = (direction + 1) % 4;
                if (direction == 0 || direction == 2) {
                    stepsToTurn++;
                }
                stepCounter = 0;
            }

            switch (direction) {
                case 0 -> x += space; // RIGHT
                case 1 -> y -= space; // DOWN
                case 2 -> x -= space; //LEFT
                case 3 -> y += space; //UP
            }
            stepCounter++;
            num++;
        }
    }

    public boolean isPrime(int n) {
        if (n == 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    public void saveToFile() {

        String fileName = "S26630Set01.bin";
        try {
            DataOutputStream dos = new DataOutputStream(
                    new FileOutputStream(fileName)
            );
            for (int n = 2; n <= MAX_NUM; n++) {
                if (isPrime(n)) {
                    int bytesNeeded = getNumBytes(n);
                    dos.write(n);
                    for (int i = 1; i < bytesNeeded; i++) {
                        dos.write(n >> (8 * i));
                    }
                }
            }
            dos.close();
            System.out.println("Zapisano");
        } catch (IOException e) {
            System.out.println("Błąd przy zapisywaniu: " + e.getMessage());
        }
    }
    public int getNumBytes(int n) {
        if (n < 256) { //2^8
            return 1;
        }
        else if (n < 65536) { //2^16
            return 2;
        }
        else if (n < 16777216) { //2^24
            return 3;
        }
        else return 4;
    }
}
