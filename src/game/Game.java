package game;
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Field;

public class Game extends JFrame implements ActionListener,MouseListener{
	JToggleButton[][] jb;
	JLabel lab[];
	int i,j;
	int mines[][];
	int X,Y,flag=0,prevX,prevY,minecount=0,dcount=0,f=0,tx,ty,k,l;
	int color[][];
	LinkedList ll;
	String u;
	
	public Game(){
		super("Minesweeper");
		mines = new int[20][20];
		color = new int[20][20];
		ll = new LinkedList();
		for(i=0;i<20;i++)
			for(j=0;j<20;j++)
				color[i][j] = 1;
		jb = new JToggleButton[20][20];
		setSize(1366,768);
		setLayout(new GridLayout(20,20));
		initialise_matrix();
		for(i=0;i<20;i++){
			for(j=0;j<20;j++){
				jb[i][j] = new JToggleButton();
				String t = new String(Integer.toString(mines[i][j]));
				//jb[i][j].setText(i+"i"+j);
				jb[i][j].setName(i+"i"+j);
				jb[i][j].setOpaque(true);
				jb[i][j].setIcon(new ImageIcon("src\\game\\sq.jpg"));
				jb[i][j].addActionListener(this);
				jb[i][j].addMouseListener(this);
				add(jb[i][j]);	
				
			}
		}
		
	}
	
	public void initialise_matrix(){
		int i,j,k,l,temp;
		for(i=0;i<20;i++){
			for(j=0;j<20;j++){
				mines[i][j] = 0;
				}
			}	
		for(i=0;i<20;i++){
			for(j=0;j<20;j++){
				temp = (int)(10*Math.random());
				if(temp>7){
					mines[i][j] = -1;
					minecount++;
					for(k=-1;k<2;k++){
						for(l=-1;l<2;l++){
							if( ((i+k) < 0) || ((j+l) < 0) || ((i+k) > 19 ) || ((j+l) > 19) )
								continue;
							if(mines[i+k][j+l]!=-1){
								mines[i+k][j+l]++;
								}
							}
						}
					}
				}
			}
	}
	
	public void gameover(){
		int i,j;
		f=1;
		
		for(i=0;i<20;i++){
			for(j=0;j<20;j++){
				if(i==X && j==Y){
					continue;
				}
				else{
					
					if(mines[i][j]!=-1){
						jb[i][j].setIcon(null);
						jb[i][j].setOpaque(true);
						jb[i][j].setBackground(Color.YELLOW);
						if(mines[i][j]!=0)
							jb[i][j].setText(Integer.toString(mines[i][j]));
						//jb[i][j].setForeground(Color.YELLOW);
					}
					else{
						//jb[i][j].setForeground(Color.RED);
						jb[i][j].setOpaque(true);
						//jb[X][Y].setBackground(null);
						//jb[X][Y].setForeground(null);
						jb[i][j].setIcon(new ImageIcon("src\\game\\normal.jpg"));
					}
					
					jb[i][j].setEnabled(false);
				}
			}
		}
	}
	
	public void userwin(){
		setVisible(false);
		new Win().setVisible(true);
	}
	
	public void btn3work(){
		
			if(jb[X][Y].getText().equals("")){
				jb[X][Y].setText("!");
				jb[X][Y].setIcon(null);
				jb[X][Y].setOpaque(true);
				jb[X][Y].setBackground(Color.magenta);
				
				//jb[X][Y].setForeground(Color.magenta);
				if(mines[X][Y]==-1){
					dcount++;
				}
				
				if(dcount==minecount){
					userwin();
				}
			}
			else if(jb[X][Y].getText().equals("!")){
				jb[X][Y].setText("");
				//jb[X][Y].setBackground(null);
				//jb[X][Y].setForeground(null);
				jb[X][Y].setIcon(new ImageIcon("src\\game\\sq.jpg"));
				if(mines[X][Y]==-1){
					dcount--;
				}
				
			}
	}
	
	public void btn1work(){
		if(mines[X][Y] == -1){
			jb[X][Y].setOpaque(false);
			//jb[X][Y].setBackground(null);
			//jb[X][Y].setForeground(null);
			jb[X][Y].setIcon(new ImageIcon("src\\game\\icon.jpg"));
			jb[X][Y].setEnabled(false);
			gameover();
		}
		else{
			
			if(mines[X][Y]!=0){
				jb[X][Y].setIcon(null);
				jb[X][Y].setOpaque(true);
				jb[X][Y].setBackground(Color.YELLOW);
				//jb[X][Y].setForeground(Color.YELLOW);
				jb[X][Y].setText(Integer.toString(mines[X][Y]));
				jb[X][Y].setEnabled(false);
			}
			else{
				jb[X][Y].setIcon(null);
				jb[X][Y].setOpaque(true);
				jb[X][Y].setBackground(Color.YELLOW);
				jb[X][Y].setEnabled(false);
				color[X][Y] = 2;
				ll.addLast(X+"i"+Y);
				while(!ll.isEmpty()){
					u = (String) ll.getFirst();
					String[] d = u.split("i");
					tx = Integer.parseInt(d[0]);
					ty = Integer.parseInt(d[1]);
					ll.removeFirst();
					for(k=-1;k<2;k++){
						for(l=-1;l<2;l++){
							if( ( (tx+k)<0 ) || ( (ty+l)<0 ) || ( (tx+k)>19 ) || ( (ty+l)>19 ) )
								continue;
							else if((tx+k)==(ty+l))
								continue;
							else if(color[tx+k][ty+l] == 1){
								color[tx+k][ty+l] = 2;
								int t1,t2;
								t1 = tx + k;
								t2 = ty + l;
								if(mines[tx+k][ty+l] == 0){
									jb[t1][t2].setIcon(null);
									jb[t1][t2].setOpaque(true);
									jb[t1][t2].setBackground(Color.YELLOW);
									jb[t1][t2].setEnabled(false);
									ll.addLast(t1 + "i" + t2);
								}
								else{
									jb[t1][t2].setIcon(null);
									jb[t1][t2].setOpaque(true);
									jb[t1][t2].setBackground(Color.YELLOW);
									jb[t1][t2].setText(Integer.toString(mines[t1][t2]));
									jb[t1][t2].setEnabled(false);
								}
							}
						}
					}
					color[tx][ty] = 3;
				}
			}
		}
	}
	
	public void actionPerformed(ActionEvent ae){
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		String g = arg0.getComponent().getName();
		String[] d = g.split("i");
		X = Integer.parseInt(d[0]);
		Y = Integer.parseInt(d[1]);
		if(f == 1){}else{
		int btn = arg0.getButton();
		if(btn  == MouseEvent.BUTTON1){
			btn1work();
		}
		if(btn == MouseEvent.BUTTON3){
			btn3work();
		}
		
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
				
	}
	
}
