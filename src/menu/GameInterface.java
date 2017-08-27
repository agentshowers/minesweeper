package menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;
import javax.swing.border.LineBorder;

import scores.ScoreKeeper;

import base.Board;

public class GameInterface extends JFrame implements Observer{

	private static final long serialVersionUID = 1L;
	
	private ImageIcon ICON_EMPTY = new ImageIcon(getClass().getResource("/icons/empty.png"));
	private ImageIcon ICON_FLAG = new ImageIcon(getClass().getResource("/icons/flag.png"));
	private ImageIcon ICON_CLICKED_MINE = new ImageIcon(getClass().getResource("/icons/clicked_mine.png"));
	private ImageIcon ICON_UNCLICKED_MINE = new ImageIcon(getClass().getResource("/icons/unclicked_mine.png"));
	private ImageIcon ICON_PLAY = new ImageIcon(getClass().getResource("/icons/play.png"));
	private ImageIcon ICON_WIN = new ImageIcon(getClass().getResource("/icons/win.png"));
	private ImageIcon ICON_LOSE = new ImageIcon(getClass().getResource("/icons/lose.png"));
	private ImageIcon ICON_TIMER = new ImageIcon(getClass().getResource("/icons/timer.png"));
	private ImageIcon ICON_1 = new ImageIcon(getClass().getResource("/icons/1.png"));
	private ImageIcon ICON_2 = new ImageIcon(getClass().getResource("/icons/2.png"));
	private ImageIcon ICON_3 = new ImageIcon(getClass().getResource("/icons/3.png"));
	private ImageIcon ICON_4 = new ImageIcon(getClass().getResource("/icons/4.png"));
	private ImageIcon ICON_5 = new ImageIcon(getClass().getResource("/icons/5.png"));
	private ImageIcon ICON_6 = new ImageIcon(getClass().getResource("/icons/6.png"));
	private ImageIcon ICON_7 = new ImageIcon(getClass().getResource("/icons/7.png"));
	private ImageIcon ICON_8 = new ImageIcon(getClass().getResource("/icons/8.png"));
	private ImageIcon ICON_WRONG_FLAG = new ImageIcon(getClass().getResource("/icons/wrong_flag.png"));
	
	private JLabel labelMines;
	private JLabel labelTime;
	private JLabel labelMineIcon;
	private JLabel labelTimeIcon;
	private JButton buttonStatus;

	private Board board;
	private int gameMode;
	private JButton [][] botoes;
	
	private Timer timer;
	private int seconds = 0;
	
	private ScoreKeeper keeper;
	
	
	public GameInterface (int modo){
		gameMode = modo;
		
		if (modo == Board.NOOB)
			build (Board.NOOB_W,Board.NOOB_H,Board.NOOB_M);
		
		if (modo == Board.NORMAL)
			build (Board.NORMAL_W,Board.NORMAL_H,Board.NORMAL_M);
		
		if (modo == Board.PRO)
			build (Board.PRO_W,Board.PRO_H,Board.PRO_M);
	}
	
	public GameInterface(int width, int heigth, int mines){
		build (width,heigth,mines);
		gameMode = Board.CUSTOM;
	}
	
	private void build (int width, int heigth, int mines){
		board = new Board(width,heigth,mines);
		board.addObserver(this);
		
		int sizeW = width*20;
		int sizeH = heigth*20;
		
		setTitle("Mines");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		getContentPane().setLayout(null);
		setMinimumSize(new java.awt.Dimension(sizeW+14,sizeH+100));
		setLocationRelativeTo(null);
		
		criaMenus();
		
		labelMines = new JLabel (""+board.getRemainingMines(),JLabel.CENTER);
		labelMines.setBounds(28,6,40,25);
		labelMines.setFont(new Font("Serif", Font.BOLD, 14));
		labelMines.setBorder(new LineBorder(Color.BLACK));
		getContentPane().add(labelMines);
		
		labelMineIcon = new JLabel(ICON_CLICKED_MINE);
		labelMineIcon.setBounds (4,6,20,25);
		getContentPane().add(labelMineIcon);
		
		labelTime = new JLabel ("0",JLabel.CENTER);
		labelTime.setBounds(sizeW-60,6,40,25);
		labelTime.setFont(new Font("Serif", Font.BOLD, 14));
		labelTime.setBorder(new LineBorder(Color.BLACK));
		getContentPane().add(labelTime);
		
		labelTimeIcon = new JLabel(ICON_TIMER);
		labelTimeIcon.setBounds (sizeW-16,6,20,25);
		getContentPane().add(labelTimeIcon);
		
		buttonStatus = new JButton (ICON_PLAY);
		buttonStatus.setBounds(44+((sizeW-105)/2),6,25,25);
		buttonStatus.setOpaque(false);
		buttonStatus.setContentAreaFilled(false);
		buttonStatus.setBorderPainted(false);
		buttonStatus.setFocusPainted(false);
		buttonStatus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (board.getGameStatus()!=Board.PLAYING) resetBoard();
			}
		});
		getContentPane().add(buttonStatus);
		
		timer = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	             seconds++;
	             labelTime.setText(seconds+"");
			}
	    });
		
		criaBotoes(width,heigth);
		
		keeper = new ScoreKeeper();
		
		timer.start();
	}
	
	private void criaMenus(){
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFicheiro = new JMenu("Ficheiro");
		mnFicheiro.setMnemonic(KeyEvent.VK_F);
		menuBar.add(mnFicheiro);
		
		JMenuItem mntmNovoJogo = new JMenuItem("Novo Jogo");
		mntmNovoJogo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetBoard();
			}
		});
		mntmNovoJogo.setAccelerator(KeyStroke.getKeyStroke("F2"));
		mnFicheiro.add(mntmNovoJogo);
		
		JMenuItem mntmOpcoes = new JMenuItem("Opções");
		mntmOpcoes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				opcoes();
			}
		});
		mntmOpcoes.setAccelerator(KeyStroke.getKeyStroke("F5"));
		mnFicheiro.add(mntmOpcoes);
		
		JMenuItem mntmPontuacoes= new JMenuItem("Pontuações");
		mntmPontuacoes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				(new MenuScores(keeper)).setVisible(true);
			}
		});
		mntmPontuacoes.setAccelerator(KeyStroke.getKeyStroke("F8"));
		mnFicheiro.add(mntmPontuacoes);
		
		JMenuItem mntmSair = new JMenuItem("Sair");
		mntmPontuacoes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sair();
			}
		});
		mnFicheiro.add(mntmSair);
	}
	
	private void sair(){
		this.dispose();
	}

	private void criaBotoes(int width, int heigth){
		int sizeW = width*20;
		int sizeH = heigth*20;
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(4,37,sizeW,sizeH);
		panel.setBorder(new LineBorder(Color.BLACK));
		
		getContentPane().add(panel);
		
		botoes = new JButton [heigth][width];
		
		for (int i=0;i<heigth;i++){
			for (int j=0;j<width;j++){
				botoes[i][j] = new JButton(ICON_EMPTY);
				botoes[i][j].setBounds(20*j,20*i,20,20);
				botoes[i][j].setOpaque(false);
				botoes[i][j].setContentAreaFilled(false);
				botoes[i][j].setBorderPainted(false);
				botoes[i][j].setFocusPainted(false);
				botoes[i][j].addMouseListener(new MouseClick (board,i,j));
				panel.add(botoes[i][j]);
			}
		}
	}
	
	private void resetBoard(){
		board.reset();
		update(null,null);
		seconds = 0;
		labelTime.setText("0");
		timer.start();
	}
	
	private void opcoes(){
		(new MenuOptions(this,board.getWidth(),board.getHeigth(),board.getMines())).setVisible(true);
	}
	
	private void doHighScore(){
		if (gameMode!=Board.CUSTOM){
			if (keeper.checkHighScore(seconds, gameMode)){
				String nome = JOptionPane.showInputDialog(this, "Insira o nome:","Nova pontuação alta!", JOptionPane.INFORMATION_MESSAGE);
				if (nome == null) return;
				if (nome.isEmpty()) return;
				keeper.addHighScore(nome, seconds, gameMode);
				(new MenuScores(keeper)).setVisible(true);
			}
		}
	}
	
	public void update(Observable arg0, Object arg1) {
		
		labelMines.setText(board.getRemainingMines()+"");
		
		int estados [][] = board.getEstados();
		
		for (int i=0;i<board.getHeigth();i++){
			for (int j=0;j<board.getWidth();j++){
				switch (estados[i][j]){
					case Board.ST_BANDEIRA:
						botoes[i][j].setIcon(ICON_FLAG);
						break;
					case Board.ST_MINA_NAO_CLICKADA:
						botoes[i][j].setIcon(ICON_UNCLICKED_MINE);
						break;
					case Board.ST_MINA_CLICKADA:
						botoes[i][j].setIcon(ICON_CLICKED_MINE);
						break;
					case Board.ST_VAZIO:
						botoes[i][j].setIcon(null);
						break;
					case Board.ST_NADA:
						botoes[i][j].setIcon(ICON_EMPTY);
						break;
					case Board.ST_BANDEIRA_ERRADA:
						botoes[i][j].setIcon(ICON_WRONG_FLAG);
						break;
					case 1:
						botoes[i][j].setIcon(ICON_1);
						break;
					case 2:
						botoes[i][j].setIcon(ICON_2);
						break;
					case 3:
						botoes[i][j].setIcon(ICON_3);
						break;
					case 4:
						botoes[i][j].setIcon(ICON_4);
						break;
					case 5:
						botoes[i][j].setIcon(ICON_5);
						break;
					case 6:
						botoes[i][j].setIcon(ICON_6);
						break;
					case 7:
						botoes[i][j].setIcon(ICON_7);
						break;
					case 8:
						botoes[i][j].setIcon(ICON_8);
						break;
				}
			}
		}
		
		if (board.getGameStatus()==Board.WIN) {
			buttonStatus.setIcon(ICON_WIN);
			timer.stop();
			doHighScore();
		}
		else if (board.getGameStatus()==Board.LOSE) {
			buttonStatus.setIcon(ICON_LOSE);
			timer.stop();
		}
		else buttonStatus.setIcon(ICON_PLAY);
		
	}
	
	public static void main (String args[]){
		try {
	        UIManager.setLookAndFeel(
	            UIManager.getSystemLookAndFeelClassName());
	    } 
	    catch (Exception e) {
	    	System.out.println("Erro look and feel:\n"+e.getMessage());
	    }
		GameInterface g = new GameInterface(Board.NOOB);
		g.setVisible(true);
	}

}
