import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class GreedSnake extends View {
  SnakeModel snakeModel = null;

  //keyPressed():按鍵
  public void keyPressed(KeyEvent e) {
    int key = e.getKeyCode();
    switch(key) {
      case KeyEvent.VK_UP:
        snakeModel.state = new Up(snakeModel,1);
        break;
      case KeyEvent.VK_DOWN:
        snakeModel.state = new Down(snakeModel,1);
        break;
      case KeyEvent.VK_LEFT:
        snakeModel.state = new Left(snakeModel,1);
        break;
      case KeyEvent.VK_RIGHT:
        snakeModel.state = new Right(snakeModel,1);
        break;
      case KeyEvent.VK_SPACE:
        snakeModel.changePause();
        break;
      case KeyEvent.VK_ENTER:
        snakeModel.run = false;
        start();
        break;
      default:
    }
  }

  //paint():繪製遊戲
  public void paint() {
    Graphics g = canvas.getGraphics();
    //背景
    g.setColor(Color.DARK_GRAY);
    g.fillRect(0,0,overWidth,overHeight);
    //snake
    g.setColor(Color.WHITE);
    LinkedList list = snakeModel.link;
    Iterator it = list.iterator();
    while(it.hasNext()) {
      Node n = (Node)it.next();
      g.fillRect(n.x * width,n.y * height,
          width - 1,height - 1);
    }
    //food
    g.setColor(Color.yellow);
    Node n = snakeModel.food;
    g.fillRect(n.x*width,n.y*height,width-1,height-1);
    score();
  }

  //score():改變計分牌
  public void score() {
    String s = "得分: " + snakeModel.score;
    score.setText(s);
  }

  //start():遊戲開始,放置貪吃蛇
  public void start() {
    if(snakeModel == null || !snakeModel.run) {
      snakeModel = new SnakeModel(this,overWidth/width,
          overHeight/height);
      //狀態已running呈現
      (new Thread(snakeModel)).start();
    }
  }
}

class SnakeModel extends Model {
  //SnakeModel():初始化遊戲
  public SnakeModel(GreedSnake snake, int maxX, int maxY) {
    this.snake = snake;
    this.maxX = maxX;
    this.maxY = maxY;
    exist = new boolean[maxX][];
    for(int i = 0; i < maxX; i++) {
      exist[i] = new boolean[maxY];
      Arrays.fill(exist[i],false);  //沒有蛇和食物的節點設false
    }
    int length = maxX > 20 ? 10 : maxX / 2;
    for(int i = 0; i < length; i++) {
      x = maxX / 2 + i;
      y = maxY / 2;
      link.addLast(new Node(x,y));
      exist[x][y] = true; //蛇身位置設為true
    }
    food = createFood();
    exist[food.x][food.y] = true; //食物位置設為true
  }

  //move():碰撞事件
  public boolean move() {
    switch (direct) {
      case Up:
        state = new Up(this, 2);
        break;
      case Down:
        state = new Down(this, 2);
        break;
      case Left:
        state = new Left(this, 2);
        break;
      case Right:
        state = new Right(this, 2);
        break;
      default:
    }

    if ((0 <= x && x < maxX) && (0 <= y && y < maxY)) {
      if (exist[x][y]) { //吃到食物或撞到身體
        if (x == food.x && y == food.y) {
          link.addFirst(food);  //在首位加節點
          score += 125 - speed + 5;
          speedUp();
          //重新生成食物
          food = createFood();
          exist[food.x][food.y] = true;
          return true;
        } else return false;
      } else {
        //加頭去尾，運動狀態
        link.addFirst(new Node(x, y));
        exist[x][y] = true;
        Node n = (Node) link.removeLast();
        exist[n.x][n.y] = false;
        return true;
      }
    }
    return false; //撞到牆
  }
}

class Main {
  public static void main(String[] args) {
    GreedSnake greedSnake = new GreedSnake();
    greedSnake.start();
  }
}