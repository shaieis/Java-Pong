/*
 * @author Shai Eisenbud
 */

package viewPkg;

import java.awt.Color;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JLabel;
import javax.swing.JPanel;

import EnumPkg.PlayersEnum;
import modelPkg.ScreenComponentsParamsConfig;

public class MainGamePanel extends JPanel
{
	private static final int LABEL_Y_POS_PERCENT = 2;
	private static final int LABEL_X_INDENT_PERCENT = 44;


	private JLabel[] _score;
	private JLabel _msgLabel;
	private Rectangle[] playerPaddles;
	private Rectangle ball;

	
	private double _resizeFactorX, _resizeFactorY; // Holds the ratio between real screen resolution and model screen resolution
	private final int  SCREEN_WIDTH, SCREEN_HEIGHT;
	
	// Ctor
	public MainGamePanel(int width, int height)
	{
		SCREEN_WIDTH = width;
		SCREEN_HEIGHT = height;
		
		
		setLayout(null);
		
		//Create paddles and ball
		playerPaddles = new Rectangle[PlayersEnum.values().length];
		playerPaddles[PlayersEnum.playerOne.ordinal()] = new Rectangle();
		playerPaddles[PlayersEnum.playerTwo.ordinal()] = new Rectangle();
		
		ball = new Rectangle();

		 Font labelFont = new Font("Ariel", Font.BOLD, 50);

		 // Create score labels
		_score = new JLabel[PlayersEnum.values().length];
		_score[PlayersEnum.playerOne.ordinal()] = new JLabel("0", JLabel.RIGHT);
		_score[PlayersEnum.playerOne.ordinal()].setForeground(Color.WHITE);
		_score[PlayersEnum.playerOne.ordinal()].setFont(labelFont);
	
		_score[PlayersEnum.playerTwo.ordinal()] = new JLabel("0");
		_score[PlayersEnum.playerTwo.ordinal()].setForeground(Color.WHITE);
		_score[PlayersEnum.playerTwo.ordinal()].setFont(labelFont);
		
		// Create message label
		_msgLabel = new JLabel("", JLabel.CENTER);
		_msgLabel.setForeground(Color.WHITE);
		_msgLabel.setFont(labelFont);
		
		// Position score labels
		_score[PlayersEnum.playerOne.ordinal()].setBounds((SCREEN_WIDTH * LABEL_X_INDENT_PERCENT) / 100 - 50,
														  (SCREEN_HEIGHT * LABEL_Y_POS_PERCENT) / 100, 
				  										  50, 
				  										  50);

		_score[PlayersEnum.playerTwo.ordinal()].setBounds((SCREEN_WIDTH * (100-LABEL_X_INDENT_PERCENT) / 100),
				  										  (SCREEN_HEIGHT * LABEL_Y_POS_PERCENT) / 100, 
				  										  50, 
				  										  50);
		
		_msgLabel.setBounds(((SCREEN_WIDTH / 2) - 200),
				  		    (SCREEN_HEIGHT / 2) - 25, 
				            400, 
				            50);
		
		add(_score[PlayersEnum.playerOne.ordinal()]);
		add(_score[PlayersEnum.playerTwo.ordinal()]);
		add(_msgLabel);
	}
	
	// Public methods =================
	
	public void setParams(ScreenComponentsParamsConfig params)
	{
		
		// Calculate resize factor between X,Y params and actual screen resolution 
		_resizeFactorX = (double) SCREEN_WIDTH / params.MODEL_SCREEN_WIDTH;
		_resizeFactorY = (double) SCREEN_HEIGHT / params.MODEL_SCREEN_HEIGHT;
		
		// Calculate resize factor between input range in params and screen resolution
		

		// Position paddles (exc. y pos which will be submited later by movePaddle)
		playerPaddles[PlayersEnum.playerOne.ordinal()].x = (int) ((params.PADDLE_X_INDENT - params.PADDLE_WIDTH) * _resizeFactorX); 
		playerPaddles[PlayersEnum.playerOne.ordinal()].width = (int) (params.PADDLE_WIDTH * _resizeFactorX);
		playerPaddles[PlayersEnum.playerOne.ordinal()].height = (int) (params.PADDLE_HEIGHT * _resizeFactorY);
		
		
		playerPaddles[PlayersEnum.playerTwo.ordinal()].x = (int) ((params.MODEL_SCREEN_WIDTH - 1 - params.PADDLE_X_INDENT) * _resizeFactorX);
		playerPaddles[PlayersEnum.playerTwo.ordinal()].width = (int) (params.PADDLE_WIDTH * _resizeFactorX);
		playerPaddles[PlayersEnum.playerTwo.ordinal()].height = (int) (params.PADDLE_HEIGHT * _resizeFactorY);	
		
		
		// Position ball
		ball.x =  (int) ((params.BALL_START_X_POS - (params.BALL_WIDTH-1)/2) * _resizeFactorX);
		ball.y =  (int) ((params.BALL_START_Y_POS - (params.BALL_WIDTH-1)/2)* _resizeFactorY);
		ball.width =  (int) (params.BALL_WIDTH * _resizeFactorX);
		ball.height =  (int) (params.BALL_WIDTH * _resizeFactorX);
	}
	
	public void showMsg(String msg)
	{
		_msgLabel.setText(msg);
	}
	
	public void setScore(PlayersEnum player, int score)
	{
		_score[player.ordinal()].setText(score + "");
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);	
		
		setBackground(Color.BLACK);
		g.setColor(Color.WHITE);
		
		super.paintComponent(g);	
		
		setBackground(Color.BLACK);
		g.setColor(Color.WHITE);
		
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.draw(playerPaddles[PlayersEnum.playerOne.ordinal()]);
		g2d.fill(playerPaddles[PlayersEnum.playerOne.ordinal()]);
		
		g2d.draw(playerPaddles[PlayersEnum.playerTwo.ordinal()]);
		g2d.fill(playerPaddles[PlayersEnum.playerTwo.ordinal()]);
		
		g2d.draw(ball);
		g2d.fill(ball);
		
		g.drawLine((SCREEN_WIDTH-1)/2, 0, (SCREEN_WIDTH-1)/2, SCREEN_HEIGHT);
	}
	
	public void movePaddle(PlayersEnum player, int yPos)
	{
		playerPaddles[player.ordinal()].y = (int) (yPos * _resizeFactorY) - (playerPaddles[PlayersEnum.playerTwo.ordinal()].height / 2);
		
		repaint();
	}
	
	public void moveBall(int xPos, int yPos)
	{
		ball.x = (int) (xPos * _resizeFactorX) - (ball.width / 2);
		ball.y = (int) (yPos * _resizeFactorY) - (ball.width / 2);repaint();
		repaint();
	}
	
}