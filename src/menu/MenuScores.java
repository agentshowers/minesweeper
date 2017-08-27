package menu;

import scores.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class MenuScores extends JFrame{
	private static final long serialVersionUID = 1L;
	

	public MenuScores (ScoreKeeper keeper) {
		setTitle("Pontuações Máximas");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		getContentPane().setLayout(null);
		setMinimumSize(new java.awt.Dimension(420,150));
		setLocationRelativeTo(null);
		
		JLabel lblNoob = new JLabel ("Noob:");
		lblNoob.setBounds(10,10,80,15);
		getContentPane().add(lblNoob);
		
		JLabel lblNormal = new JLabel ("Normal:");
		lblNormal.setBounds(145,10,80,15);
		getContentPane().add(lblNormal);
		
		JLabel lblPro = new JLabel ("Pro:");
		lblPro.setBounds(280,10,80,15);
		getContentPane().add(lblPro);
		
		String titulos [] = {"Nome","Tempo"};
		
		criaTabelaNoob(titulos,keeper);
		criaTabelaNormal(titulos,keeper);
		criaTabelaPro(titulos,keeper);
	}
	
	@SuppressWarnings("serial")
	private void criaTabelaNoob (String [] titulos,ScoreKeeper keeper){
		JTable tableNoob = new JTable();
		Object dadosNoob [][] = new Object[ScoreKeeper.MAX][2];
		Score scoresNoob [] = keeper.getScoresNoob();
		
		for (int i=0;i<ScoreKeeper.MAX;i++){
			if (scoresNoob[i]!=null){
				dadosNoob[i][0] = scoresNoob[i].getNome();
				dadosNoob[i][1] = scoresNoob[i].getTempo();
			}
			else {
				dadosNoob[i][0] = "";
				dadosNoob[i][1] = "";
			}
		}
		
		DefaultTableModel model = new DefaultTableModel (dadosNoob,titulos) {
			public boolean isCellEditable(int row, int column) {
			       return false;
			}
		};
		
		tableNoob.setModel(model);
		tableNoob.setShowGrid(false);
		tableNoob.setRowSelectionAllowed(false);
		tableNoob.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		TableColumn col = tableNoob.getColumnModel().getColumn(0);
		col.setPreferredWidth(75);
		
		col = tableNoob.getColumnModel().getColumn(1);
		col.setPreferredWidth(48);
		
		JScrollPane scrollNoob = new JScrollPane();
		scrollNoob.setBounds(10,35,125,75);
		getContentPane().add(scrollNoob);
		
		scrollNoob.setViewportView(tableNoob);
	}
	
	@SuppressWarnings("serial")
	private void criaTabelaNormal (String [] titulos,ScoreKeeper keeper){
		JTable tableNormal = new JTable();
		Object dadosNormal [][] = new Object[ScoreKeeper.MAX][2];
		Score scoresNormal [] = keeper.getScoresNormal();
		
		for (int i=0;i<ScoreKeeper.MAX;i++){
			if (scoresNormal[i]!=null){
				dadosNormal[i][0] = scoresNormal[i].getNome();
				dadosNormal[i][1] = scoresNormal[i].getTempo();
			}
			else {
				dadosNormal[i][0] = "";
				dadosNormal[i][1] = "";
			}
		}
		
		DefaultTableModel model = new DefaultTableModel (dadosNormal,titulos) {
			public boolean isCellEditable(int row, int column) {
			       return false;
			}
		};
		
		tableNormal.setModel(model);
		tableNormal.setShowGrid(false);
		tableNormal.setRowSelectionAllowed(false);
		tableNormal.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		TableColumn col = tableNormal.getColumnModel().getColumn(0);
		col.setPreferredWidth(75);
		
		col = tableNormal.getColumnModel().getColumn(1);
		col.setPreferredWidth(48);
		
		JScrollPane scrollNormal = new JScrollPane();
		scrollNormal.setBounds(145,35,125,75);
		getContentPane().add(scrollNormal);
		
		scrollNormal.setViewportView(tableNormal);
	}
	
	@SuppressWarnings("serial")
	private void criaTabelaPro (String [] titulos,ScoreKeeper keeper){
		JTable tablePro = new JTable();
		Object dadosPro [][] = new Object[ScoreKeeper.MAX][2];
		Score scoresPro [] = keeper.getScoresPro();
		
		for (int i=0;i<ScoreKeeper.MAX;i++){
			if (scoresPro[i]!=null){
				dadosPro[i][0] = scoresPro[i].getNome();
				dadosPro[i][1] = scoresPro[i].getTempo();
			}
			else {
				dadosPro[i][0] = "";
				dadosPro[i][1] = "";
			}
		}
		
		DefaultTableModel model = new DefaultTableModel (dadosPro,titulos) {
			public boolean isCellEditable(int row, int column) {
			       return false;
			}
		};
		
		tablePro.setModel(model);
		tablePro.setShowGrid(false);
		tablePro.setRowSelectionAllowed(false);
		tablePro.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		TableColumn col = tablePro.getColumnModel().getColumn(0);
		col.setPreferredWidth(75);
		
		col = tablePro.getColumnModel().getColumn(1);
		col.setPreferredWidth(48);
		
		JScrollPane scrollPro = new JScrollPane();
		scrollPro.setBounds(280,35,125,75);
		getContentPane().add(scrollPro);
		
		scrollPro.setViewportView(tablePro);
	}
}
