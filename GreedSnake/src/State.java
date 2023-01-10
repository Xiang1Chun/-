interface State {
  void change(Model snake); //避免衝突
  void coordinate(Model snake); //改變snake x,y
}

class Up implements State {
  public Up(Model snake, int i) {
    if(i == 1)
      change(snake);
    else if(i == 2)
      coordinate(snake);
  }

  public void change(Model snake) {
    if(snake.direct != StateEnum.Down) {
      snake.direct = StateEnum.Up;
    }
  }

  public void coordinate(Model snake) {
    Node n = (Node)snake.link.getFirst();
    int y = n.y;
    y--;
    snake.x = n.x;
    snake.y = y;
  }
}

class Down implements State {
  public Down(Model snake, int i) {
    if(i == 1)
      change(snake);
    else if(i == 2)
      coordinate(snake);
  }

  public void change(Model snake) {
    if(snake.direct != StateEnum.Up) {
      snake.direct = StateEnum.Down;
    }
  }

  public void coordinate(Model snake) {
    Node n = (Node)snake.link.getFirst();
    int y = n.y;
    y++;
    snake.x = n.x;
    snake.y = y;
  }
}

class Left implements State {
  public Left(Model snake, int i) {
    if(i == 1)
      change(snake);
    else if(i == 2)
      coordinate(snake);
  }

  public void change(Model snake) {
    if(snake.direct != StateEnum.Right) {
      snake.direct = StateEnum.Left;
    }
  }

  public void coordinate(Model snake) {
    Node n = (Node)snake.link.getFirst();
    int x = n.x;
    x--;
    snake.x = x;
    snake.y = n.y;
  }
}

class Right implements State {
  public Right(Model snake, int i) {
    if(i == 1)
      change(snake);
    else if(i == 2)
      coordinate(snake);
  }

  public void change(Model snake) {
    if(snake.direct != StateEnum.Left) {
      snake.direct = StateEnum.Right;
    }
  }

  public void coordinate(Model snake) {
    Node n = (Node)snake.link.getFirst();
    int x = n.x;
    x++;
    snake.x = x;
    snake.y = n.y;
  }
}
