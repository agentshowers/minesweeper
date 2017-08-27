package menu;

import base.Board;

public class MouseClick extends java.awt.event.MouseAdapter{
    private Board board;
    private int x;
    private int y;

    public MouseClick (Board b,int i, int j){
        board = b;
        x=i;
        y=j;
    }
    
    @Override
    public void mouseClicked(java.awt.event.MouseEvent evt) {
        if(evt.getModifiers()==16)
        	 board.leftClick(x, y);

           
        else
        	board.rigthClick(x, y);
    }
}
