import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements KeyListener{
	
	private static final long serialVersionUID = 1L;
	
	private static int delay = 300;
	
	public static int STATE_GAME_PLAY = 0;
	public static int STATE_GAME_PAUSE = 1;
	public static int STATE_GAME_OVER = 2;
	
	private int state = STATE_GAME_PLAY;
	
	public static final int BOARD_WIDTH = 10;
	public static final int BOARD_HEIGHT = 20;
	public static final int BLOCK_SIZE = 30;
	
	private Timer looper;	
	private Color[][] board = new Color[BOARD_HEIGHT][BOARD_WIDTH];
	
	private int deletedLine;
	
	private Random random= new Random();
	
	private Color[] colors = {Color.decode("#ed1c24"), Color.decode("#ff7f27"), Color.decode("#fff200"), 
	        Color.decode("#22b14c"), Color.decode("#00a2e8"), Color.decode("#a349a4"), Color.decode("#3f48cc")};
	
	private Shape[] shapes = new Shape[7];
	
	private Shape currentShape, nextShape;
	
	private int score = 0;
	
	
	public Board()
	{	
		
		looper = new Timer(delay, new GameLooper());
		setFocusable(true);
		requestFocusInWindow();
		addKeyListener(this);

		shapes[0] = new Shape(new int[][]{
            {1, 1, 1, 1} // I shape;
        }, this, colors[0]);

        shapes[1] = new Shape(new int[][]{
            {1, 1, 1},
            {0, 1, 0}, // T shape;
        }, this, colors[1]);

        shapes[2] = new Shape(new int[][]{
            {1, 1, 1},
            {1, 0, 0}, // L shape;
        }, this, colors[2]);

        shapes[3] = new Shape(new int[][]{
            {1, 1, 1},
            {0, 0, 1}, // J shape;
        }, this, colors[3]);

        shapes[4] = new Shape(new int[][]{
            {0, 1, 1},
            {1, 1, 0}, // S shape;
        }, this, colors[4]);

        shapes[5] = new Shape(new int[][]{
            {1, 1, 0},
            {0, 1, 1}, // Z shape;
        }, this, colors[5]);

        shapes[6] = new Shape(new int[][]{
            {1, 1},
            {1, 1}, // O shape;
        }, this, colors[6]);
        
		startGame();
	}
	
	private void update()
	{
		if(state == STATE_GAME_PLAY) {
			currentShape.update();
		}
		return;
	}
	
	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {

                if (board[row][col] != null) {
                    g.setColor(board[row][col]);
                    g.fillRect(col * BLOCK_SIZE, row * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                }

            }
        }
        if (nextShape != null) {
            g.setColor(nextShape.getColor());
            for (int row = 0; row < nextShape.getCoords().length; row++) {
                for (int col = 0; col < nextShape.getCoords()[0].length; col++) {
                    if (nextShape.getCoords()[row][col] != 0) {
                        g.fillRect(col * 30 + 320, row * 30 + 50, Board.BLOCK_SIZE, Board.BLOCK_SIZE);
                    }
                }
            }
        }
        if (currentShape != null) {
            currentShape.render(g);
        }

		
		// draw the board
		
		g.setColor(Color.white);
		g.drawString("SCORE", WindowGame.WIDTH - 125, WindowGame.HEIGHT / 2);
		g.drawString(score + "", WindowGame.WIDTH - 125, WindowGame.HEIGHT / 2 + 30);
		g.setColor(Color.WHITE);
		for (int row=0;row<BOARD_HEIGHT;row++) {
			g.drawLine(0, BLOCK_SIZE*row, BLOCK_SIZE*BOARD_WIDTH, BLOCK_SIZE*row);
		}
		for (int col=0;col<BOARD_WIDTH+1;col++) {
			g.drawLine(BLOCK_SIZE*col, 0, col*BLOCK_SIZE, BLOCK_SIZE*BOARD_HEIGHT);
		}
		
		if(state == STATE_GAME_OVER) {
			g.setColor(Color.white);
			g.drawString("GAME OVER", 350, 200);
		}
		if(state == STATE_GAME_PAUSE) {
			g.setColor(Color.white);
			g.drawString("GAME PAUSE", 350, 200);
		}
	}
	public Color[][] getBoard(){
		return board;
	}
	
	public void setNextShape() {
		int index = random.nextInt(shapes.length);
        int colorIndex = random.nextInt(colors.length);
        nextShape = new Shape(shapes[index].getCoords(), this, colors[colorIndex]);
	}
	public void setCurrentShape() {
		currentShape = nextShape;
		setNextShape();
		for (int row = 0; row < currentShape.getCoords().length; row++) {
            for (int col = 0; col < currentShape.getCoords()[0].length; col++) {
                if (currentShape.getCoords()[row][col] != 0) {
                    if (board[currentShape.getY() + row][currentShape.getX() + col] != null) {
                    	state = STATE_GAME_OVER;
                    }
                }
            }
        }
	}
	
	
	// 키 버튼 이벤트
	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// 오른쪽,왼쪽,아래,회전,떨어뜨리기, 일시중지, 게임종료
		if(state== STATE_GAME_PLAY) {
			if(e.getKeyCode()==KeyEvent.VK_RIGHT) {
				currentShape.setDeltaX(1);
			}
			else if(e.getKeyCode()==KeyEvent.VK_LEFT) {
				currentShape.setDeltaX(-1);
			}
			else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
		        currentShape.moveDown(); // VK_DOWN 키를 눌렀을 때 블록을 한 칸 아래로 이동
		    }
			else if(e.getKeyCode()==KeyEvent.VK_UP) {
				currentShape.rotateShape();
			}
			else if(e.getKeyCode()==KeyEvent.VK_SPACE) {
				currentShape.dropDown();
			}
			else if(e.getKeyCode() == KeyEvent.VK_P) {
					state = STATE_GAME_PAUSE;
			}
			else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
		        // 게임 종료
		        System.exit(0);
			}
		}
		// 게임 리플레이
		else if(state == STATE_GAME_OVER) {
			if(e.getKeyCode() == KeyEvent.VK_P) {
				for(int row =0; row<board.length;row++) {
					for(int col=0; col<board[row].length; col++) {
						board[row][col]=null;
					}
				}
			}
			setCurrentShape();
			state = STATE_GAME_PLAY;
		}
		else if(state == STATE_GAME_PAUSE) {
			if(e.getKeyCode() == KeyEvent.VK_P) {
				state = STATE_GAME_PLAY;}
		}
		
	
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}
	 public void startGame() {
	        stopGame();
	        setNextShape();
	        setCurrentShape();
	        state = STATE_GAME_PLAY;
	        looper.start();

	    }
	 public void stopGame() {
	        score = 0;

	        for (int row = 0; row < board.length; row++) {
	            for (int col = 0; col < board[row].length; col++) {
	                board[row][col] = null;
	            }
	        }
	        looper.stop();
	    }
	// 줄 삭제 알고리즘
		public void checkLine() {
		    int deletedLines = 0; // 삭제된 줄 수를 저장할 변수
		    int bottomLine = board.length - 1;
		    for (int topLine = board.length - 1; topLine > 0; topLine--) {
		        int count = 0;
		        for (int col = 0; col < board[0].length; col++) {
		            if (board[topLine][col] != null) {
		                count++;
		            }
		            board[bottomLine][col] = board[topLine][col];
		        }
		        if (count < board[0].length) {
		            bottomLine--;
		        } else {
		        	addScore();
		            deletedLines++; // 삭제된 줄이 있으면 deletedLines를 증가시킴
		        }
		    }
		    // 삭제된 줄의 수를 누적
		    deletedLine += deletedLines;		    
		}
		
		

	 class GameLooper implements ActionListener {
		 	private long lastSpeedIncreaseTime = System.currentTimeMillis();
		    
		    @Override
		    public void actionPerformed(ActionEvent e) {
			    if(!(state ==STATE_GAME_PAUSE)) {
			    	update();
				    repaint();
				        
				     // 시간이 1분 경과했을 때 속도 증가
			        long currentTime = System.currentTimeMillis();
			        
			        if (currentTime - lastSpeedIncreaseTime >= 60000) {
			        	addBonusScore();
			            delay -= 20; // 예를 들어, 50ms 간격으로 속도를 빠르게 설정
			            if (delay <=100) { // 100ms 이하로 감소하지 않도록 보정
			                delay = 100;
			            }
			            lastSpeedIncreaseTime = currentTime;
			        }
			        System.out.println("속도가 빨라지기까지의 시간 : "+(60 - ((currentTime-lastSpeedIncreaseTime)/1000) ) +"초");
			        
			        looper.setDelay(delay);
			        System.out.println("딜레이 : "+delay);
			        System.out.println("삭제된 줄의 수 : "+deletedLine);
			        
			        if(deletedLine>=5) {
				    	delay -= 20;
				    	addBonusScore();
				    	if (delay <=100) { // 100ms 이하로 감소하지 않도록 보정
			                delay = 100;
			            }
				    	deletedLine %=5;
				    }
				}
			   
		    }
		}

	 public void addScore() {
	        score +=2 ;
	       
	 }
	 public void addBonusScore() {
		 score += 200;
	 }
	 
}
