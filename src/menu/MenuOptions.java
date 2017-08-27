package menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import base.Board;
import base.IncorrectValueException;

public class MenuOptions extends JFrame{

	private static final long serialVersionUID = 1L;
	
	private static final int B_NOOB = 0;
	private static final int B_NORMAL = 1;
	private static final int B_PRO = 2;
	private static final int B_CUSTOM = 3;
	
	private GameInterface iface;
	
	private JTextField txtHeigth;
	private JTextField txtWidth;
	private JTextField txtMines;
	
	private JRadioButton btnNoob;
	private JRadioButton btnNormal;
	private JRadioButton btnPro;
	private JRadioButton btnCustom;
	
	public MenuOptions (GameInterface i, int curW, int curH, int curM){
		iface = i;
		
		setTitle("Options");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		getContentPane().setLayout(null);
		setMinimumSize(new java.awt.Dimension(270,260));
		setLocationRelativeTo(null);
		
		JLabel lblHeigth = new JLabel ("<html>Altura:<br>("+Board.MIN_H+" - "+Board.MAX_H+")");
		lblHeigth.setBounds(105,45,60,40);
		getContentPane().add(lblHeigth);
		
		JLabel lblWidth = new JLabel ("<html>Largura:<br>("+Board.MIN_W+" - "+Board.MAX_W+")");
		lblWidth.setBounds(105,90,60,40);
		getContentPane().add(lblWidth);
		
		JLabel lblMines = new JLabel ("<html>Minas:<br>("+Board.MIN_M+" - "+Board.MAX_M+")");
		lblMines.setBounds(105,135,60,40);
		getContentPane().add(lblMines);
		
		txtHeigth = new JTextField();
		txtHeigth.setBounds(170,50,80,30);
		txtHeigth.setEnabled(false);
		getContentPane().add(txtHeigth);
		
		txtWidth = new JTextField();
		txtWidth.setBounds(170,95,80,30);
		txtWidth.setEnabled(false);
		getContentPane().add(txtWidth);
		
		txtMines = new JTextField();
		txtMines.setBounds(170,140,80,30);
		txtMines.setEnabled(false);
		getContentPane().add(txtMines);
		
		JButton btnOK = new JButton ("OK");
		btnOK.setBounds(90,190,60,25);
		btnOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pressButton();
			}
		});
		getContentPane().add(btnOK);
		
		createButtons();
		
		if (curW == Board.NOOB_W && curH == Board.NOOB_H && curM == Board.NOOB_M)
			btnNoob.setSelected(true);
		
		else if (curW == Board.NORMAL_W && curH == Board.NORMAL_H && curM == Board.NORMAL_M)
			btnNormal.setSelected(true);
		
		else if (curW == Board.PRO_W && curH == Board.PRO_H && curM == Board.PRO_M)
			btnPro.setSelected(true);
		
		else {
			btnCustom.setSelected(true);
			txtHeigth.setText(curH+"");
			txtWidth.setText(curW+"");
			txtMines.setText(curM+"");
			txtHeigth.setEnabled(true);
			txtWidth.setEnabled(true);
			txtMines.setEnabled(true);
		}
	}
	
	private void pressButton (){
		
		if (btnNoob.isSelected()){
			iface.dispose();
			(new GameInterface(Board.NOOB)).setVisible(true);
			this.dispose();
		}
		
		else if (btnNormal.isSelected()){
			iface.dispose();
			(new GameInterface(Board.NORMAL)).setVisible(true);
			this.dispose();
		}
		
		else if (btnPro.isSelected()){
			iface.dispose();
			(new GameInterface(Board.PRO)).setVisible(true);
			this.dispose();
		}
		
		else {
			int heigth = 0, width = 0, mines = 0;
			
			try {
				heigth = Integer.parseInt(txtHeigth.getText());
				width = Integer.parseInt(txtWidth.getText());
				mines = Integer.parseInt(txtMines.getText());
			}
			catch (Exception e){
				JOptionPane.showMessageDialog(this, "Insira um valor numérico","Erro",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			try {
				Board.validateHeigth(heigth);
			}
			catch (IncorrectValueException e){
				JOptionPane.showMessageDialog(this, e.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE);
				txtHeigth.setText(e.getCorrectValue()+"");
				return;
			}
			
			try {
				Board.validateWidth(width);
			}
			catch (IncorrectValueException e){
				JOptionPane.showMessageDialog(this, e.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE);
				txtWidth.setText(e.getCorrectValue()+"");
				return;
			}
			
			try {
				Board.validateMines(width,heigth,mines);
			}
			catch (IncorrectValueException e){
				JOptionPane.showMessageDialog(this, e.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE);
				txtMines.setText(e.getCorrectValue()+"");
				return;
			}
			
			iface.dispose();
			(new GameInterface(width,heigth,mines)).setVisible(true);
			this.dispose();
		}
		
	}
	
	private void createButtons (){
		btnNoob = new JRadioButton("<html>Noob<br>"+Board.NOOB_H+" x "+Board.NOOB_W+"<br>"+Board.NOOB_M+" minas");
		btnNoob.setBounds(5,10,80,50);
		btnNoob.setFocusPainted(false);
		btnNoob.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectRadio(B_NOOB);
			}
		});
		getContentPane().add(btnNoob);
		
		btnNormal = new JRadioButton("<html>Normal<br>"+Board.NORMAL_H+" x "+Board.NORMAL_W+"<br>"+Board.NORMAL_M+" minas");
		btnNormal.setBounds(5,70,80,50);
		btnNormal.setFocusPainted(false);
		btnNormal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectRadio(B_NORMAL);
			}
		});
		getContentPane().add(btnNormal);
		
		btnPro = new JRadioButton("<html>Pro<br>"+Board.PRO_H+" x "+Board.PRO_W+"<br>"+Board.PRO_M+" minas");
		btnPro.setBounds(5,130,80,50);
		btnPro.setFocusPainted(false);
		btnPro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectRadio(B_PRO);
			}
		});
		getContentPane().add(btnPro);
		
		btnCustom = new JRadioButton ("Personalizado: ");
		btnCustom.setBounds(95,25,120,20);
		btnCustom.setFocusPainted(false);
		btnCustom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectRadio(B_CUSTOM);
			}
		});
		getContentPane().add(btnCustom);
	}
	
	private void selectRadio (int button){
		btnNoob.setSelected(button==B_NOOB);
		btnNormal.setSelected(button==B_NORMAL);
		btnPro.setSelected(button==B_PRO);
		btnCustom.setSelected(button==B_CUSTOM);
		txtHeigth.setEnabled(button==B_CUSTOM);
		txtWidth.setEnabled(button==B_CUSTOM);
		txtMines.setEnabled(button==B_CUSTOM);
	}
	
	
}
