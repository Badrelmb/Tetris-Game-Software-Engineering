import javax.swing.JFrame;
//게임 조작
/*보드에 현재 블럭이 생성되면, 이 블럭은 아무 조작이 없어도 1초에 1칸씩 밑으로 떨어져야 함. (구현)
• 일정 수 이상의 블럭이 생성되거나, 줄이 삭제되면 떨어지는 속도가 증가해야 함. (구현)
• 현재 블럭을 키보드 입력에 따라 다음과 같이 조작 가능해야 함. (구현)
• 좌, 우, 아래로 한 칸씩 이동시킬 수 있어야 함. (구현)
• 시계방향으로 90도씩 회전시킬 수 있어야 함. (구현)
• 한 번에 끝까지 밑으로 떨어뜨릴 수 있어야 함. (구현)
• 반복된 키 입력이 무시되지 않고 블럭이 즉시, 정확하게 반응해야 함. (구현)
• 게임 도중 게임을 잠시 중단/재개할 수 있는 키가 존재해야 함. (구현)
• 게임 진행/중지 상태에서 게임을 종료할 수 있는 방법이 존재해야 함 (alt-f4 등 OS를 이용한 방법 제외). (구현)
*/

// 점수 계산
/*기본적으로 블럭이 1칸 떨어질 때마다 한 단위의 점수를 획득. (구현)
• 자동/수동 조작에 무관하게 획득함.
• 바뀐 점수는 실시간으로 게임 화면에 표시되어야 함. (구현)
• 블럭이 떨어지는 속도가 초기 속도보다 빨라진 경우 추가적인 점수를 획득.(구현)
• 그 외 다양한 방식의 점수를 가산/감산하는 방식을 추가할 수 있음.
 -- 줄이 삭제될때마다 추가적인 점수 획득 
 -- 1분이 지날떄마다 추가 점수 획득
• 적어도 1개 이상의 직접 제시되지 않은 방식을 구현할 것*/
public class WindowGame {
	
	
	
	private JFrame window;
	private Board board;
	public static final int WIDTH =445, HEIGHT = 640;
	
	
	public WindowGame() {
		window = new JFrame("Tetris");
		window.setSize(WIDTH,HEIGHT);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(true);
		window.setLocationRelativeTo(null);
		
		board = new Board();
		window.add(board);
		window.addKeyListener(board);
		window.setVisible(true);
	}
	
	public static void main(String[] args) {
		new WindowGame();
		
	}
}
