import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.Random;

public abstract class View extends KeyAdapter {
  //遊戲介面
  JFrame mainFrame;
  Canvas canvas;
  JLabel score;
  //活動範圍
  public static final int overWidth = 420;
  public static final int overHeight = 252;
  //節點大小
  public static final int width = 12;
  public static final int height = 12;
  //Applications實作
  public abstract void keyPressed(KeyEvent e);
  public abstract void paint();
  public abstract void score();
  public abstract void start();

  public View() {
    mainFrame = new JFrame("貪吃蛇");
    mainFrame.setLocation(550,250);
    score = new JLabel("得分:", JLabel.CENTER);
    mainFrame.add(score, BorderLayout.NORTH);
    canvas = new Canvas();
    Container contain = mainFrame.getContentPane();
    canvas.setSize(overWidth+1,overHeight+1);
    canvas.addKeyListener(this);
    contain.add(canvas, BorderLayout.CENTER);

    JPanel panel = new JPanel();  //規劃幫助資訊版面
    panel.setLayout(new BorderLayout());
    JLabel help;
    help = new JLabel("按 Enter 鍵可重新開始遊戲", JLabel.CENTER);
    panel.add(help, BorderLayout.CENTER);
    help = new JLabel("按 SPACE 鍵可暫停遊戲", JLabel.CENTER);
    panel.add(help, BorderLayout.SOUTH);
    mainFrame.add(panel, BorderLayout.SOUTH);

    mainFrame.addKeyListener(this); //鍵盤處理
    mainFrame.pack();  //介面尺寸
    mainFrame.setResizable(false);  //設定視窗大小不能變化
    mainFrame.setVisible(true); //視窗可見
    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //退出應用程式
  }
}

abstract class Model implements Runnable {
  View snake;
  Node food;
  LinkedList link = new LinkedList(); //snake body

  int speed = 120;
  //最大範圍
  int maxX;
  int maxY;
  int x = 0;
  int y = 0;
  int score = 0;
  boolean[][] exist;  //判斷該位置是否有節點
  //遊戲狀態
  boolean run = false;
  boolean pause = false;
  State state;
  StateEnum direct = StateEnum.Up;
  //Applications實作
  public abstract boolean move();

  //run():貪吃蛇運動執行緒
  public void run() {
    run = true;
    while(run) {
      try {
        //設定速度
        Thread.sleep(speed);
      }
      catch(Exception e) {
        break;
      }

      if(!pause) {
        if(move())  //暫停
          snake.paint();
        else { //遊戲結束
          JOptionPane.showMessageDialog(null,
              "分數 : " + score + "\nGAME OVER",
              "Game Over", JOptionPane.ERROR_MESSAGE);
          break;
        }
      }
    }
    run = false;
  }

  //createFood():生成食物
  Node createFood() {
    int x = 0;
    int y = 0;
    do {
      Random rand = new Random();
      x = rand.nextInt(maxX);
      y = rand.nextInt(maxY);
    } while(exist[x][y]);
    return new Node(x,y);
  }

  public void speedUp() {
    speed -= 5;
  }

  public void changePause() {
    pause = !pause;
  }
}

//生成圖片基本元素
class Node {
  int x;
  int y;
  public Node(int x,int y) {
    this.x = x;
    this.y = y;
  }
}