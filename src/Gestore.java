import java.net.URL;
import java.awt.Toolkit;
import java.awt.MediaTracker;
import javax.swing.JFrame;
import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.Dimension;
import java.io.File;
import java.awt.Color;
import java.awt.Rectangle;
import javax.swing.JOptionPane;
import java.util.Scanner;

public class Gestore extends JFrame implements Runnable{
	private int DELAY = 500;
	private Giocatore[] giocatori;
	private int[] punti;
	private Campo campo;
	private boolean turno;
	private Image carte, coperta, freccia;
	private Carta briscola, brisc;

	public Gestore () {
		super("Briscola by Baioke");
		addMouseListener(new Gestore.Mouse());
		addKeyListener(new Gestore.Tastiera());
		setSize(770,695);
		setPreferredSize(new Dimension(770,695));
		setResizable(false);
		setBackground(new Color(28,111,29));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		giocatori = new Giocatore[2];
		campo = new Campo();
		punti = new int[2];
		punti[0] = 0;
		punti[1] = 0;
		briscola = campo.pesca();
		brisc = briscola;
		turno = ((Math.floor((Math.random()*2))==0))?true:false;
		MediaTracker mt = new MediaTracker(this);
		try {
			
			//carte = Toolkit.getDefaultToolkit().getImage(new URL(Carta.class.getClassLoader().getResource("../img").toString() + "/carte.jpg"));
			carte = Toolkit.getDefaultToolkit().getImage("../img/carte.jpg");
			mt.addImage(carte,0);
			mt.waitForID(0);
			//coperta = Toolkit.getDefaultToolkit().getImage(new URL(Carta.class.getClassLoader().getResource("../img").toString() + "/0.png"));
			coperta = Toolkit.getDefaultToolkit().getImage("../img/0.png");
			mt.addImage(coperta,1);
			mt.waitForID(1);
			
		}
		catch(Exception e) {
		System.out.println("INIZIO");
			e.printStackTrace();
			System.exit(0);
		}
		giocatori[0] = new GiocatoreUmano(new Mano(new Carta[]{campo.pesca(),campo.pesca(),campo.pesca()}),"Giocatore");
		giocatori[1] = new GiocatoreCPU(new Mano(new Carta[]{campo.pesca(),campo.pesca(),campo.pesca()}),"CPU2");
		try {
			Thread.currentThread().sleep(DELAY);
		}
		catch(Exception e) {}
		Thread t = new Thread(this);
		t.start();
		setVisible(true);
	}
	public void run() {
		while(!campo.mazzoVuoto()) {
			repaint();
			try {
				Thread.currentThread().sleep(DELAY);
			}
			catch(Exception e) {}
			campo.giocaCarta(giocatori[(turno)?0:1].gioca(campo,briscola));
			repaint();
			try {
				Thread.currentThread().sleep(DELAY);
			}
			catch(Exception e) {}
			campo.giocaCarta(giocatori[(turno)?1:0].gioca(campo,briscola));
			repaint();
			try {
				Thread.currentThread().sleep(DELAY);
			}
			catch(Exception e) {}
			assegnaPunti(campo,briscola,turno);
			turno = determinaTurno(campo,briscola,turno);
			campo.svuota();
			try {
				Thread.currentThread().sleep(DELAY);
			}
			catch(Exception e) {}
			giocatori[(turno)?0:1].prendiCarta(campo.pesca());
			try {
				Thread.currentThread().sleep(DELAY);
			}
			catch(Exception e) {}
			repaint();
			if(!campo.mazzoVuoto())
				giocatori[(turno)?1:0].prendiCarta(campo.pesca());
			else {
				giocatori[(turno)?1:0].prendiCarta(brisc);
				brisc = null;
			}
			try {
				Thread.currentThread().sleep(DELAY);
			}
			catch(Exception e) {}
			repaint();
		}
		for(int i=0; i < 3; i++) {
			Scanner s = new Scanner(System.in); //
			System.out.println("Turno: " + turno); //
			try {
				Thread.currentThread().sleep(DELAY);
			}
			catch(Exception e) {}
			campo.giocaCarta(giocatori[(turno)?0:1].gioca(campo,briscola));
			System.out.println("Carta giocata: " + campo.getCartaCampo(0)); //
			repaint();
			try {
				Thread.currentThread().sleep(DELAY);
			}
			catch(Exception e) {}
			campo.giocaCarta(giocatori[(turno)?1:0].gioca(campo,briscola));
			System.out.println("Carta giocata: " + campo.getCartaCampo(1)); //
			repaint();
			try {
				Thread.currentThread().sleep(DELAY);
			}
			catch(Exception e) {}
			assegnaPunti(campo,briscola,turno);
			turno = determinaTurno(campo,briscola,turno);
			campo.svuota();
			try {
				Thread.currentThread().sleep(DELAY);
			}
			catch(Exception e) {}
			repaint();
		}
		if(punti[0] != punti[1])
			JOptionPane.showMessageDialog(null,giocatori[(punti[0]>punti[1])?1:0].getNome()+" vince!");
		else
			JOptionPane.showMessageDialog(null,"Pareggio!");
		System.exit(0);
	}

	private void assegnaPunti(Campo c, Carta briscola, boolean turno) {
		try {
		if(campo.getCartaCampo(1).prende(campo.getCartaCampo(0),briscola))
			punti[(turno)?0:1] += c.getCartaCampo(0).getPunti()+c.getCartaCampo(1).getPunti();
		else
			punti[(turno)?1:0] += c.getCartaCampo(0).getPunti()+c.getCartaCampo(1).getPunti();
		}
		catch(NullPointerException e) {
			System.out.println(e.getMessage());
			System.out.println("Carta 2: " + campo.getCartaCampo(1) + "\nCarta 1: " + campo.getCartaCampo(0));
		}
	}

	private boolean determinaTurno(Campo c, Carta briscola, boolean turno) {
		return (c.getCartaCampo(1).prende(c.getCartaCampo(0),briscola))?!turno:turno;
	}

	public void paint(Graphics g) {
		g.setColor(new Color(28,111,29));
		((Graphics2D)g).fill(new Rectangle(0,0,770,695));
		for (int i=0; i < 3; i++) {
			if(giocatori[1].getMano().getCarta(i)!=null) {
				giocatori[1].getMano().getCarta(i).disegna(g, true, 370+(i*120), 50, carte, coperta);
			}
		}
		for (int i=0; i < 3; i++) {
			if(giocatori[0].getMano().getCarta(i)!=null)
				giocatori[0].getMano().getCarta(i).disegna(g,false, 370+(i*120), 520, carte, coperta);
		}
		if(campo.nCarte()==1)
			campo.getCartaCampo(0).disegna(g,false,410,285, carte, coperta);
		if(campo.nCarte()==2) {
			campo.getCartaCampo(0).disegna(g,false,410,285, carte, coperta);
			campo.getCartaCampo(1).disegna(g,false,570,285, carte, coperta);
		}
		if(!campo.mazzoVuoto())
			(new Carta(0,"")).disegna(g,true,60,285,carte,coperta);
		if(brisc != null)
			brisc.disegna(g,false,150,285,carte,coperta);
		if(!turno)
			g.setColor(Color.red);
		else
			g.setColor(Color.black);
		g.drawString(giocatori[1].getNome()+": "+punti[0], 105, 50);
		if(turno)
			g.setColor(Color.red);
		else
			g.setColor(Color.black);
		g.drawString(giocatori[0].getNome()+": "+punti[1], 105, 595);
	}
	class Mouse extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			for (int i=0; i < 3; i++) {
				if((new Rectangle(370+(i*120),520, 60, 125)).contains(e.getX(),e.getY()))
					if(giocatori[0].getMano().getCarta(i) != null)
						if(((GiocatoreUmano)giocatori[0]).puoGiocare())
							((GiocatoreUmano)giocatori[0]).setGiocata(i);
			}
		}
	}

	class Tastiera extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_1) {
				if(giocatori[0].getMano().getCarta(0) != null)
					if(((GiocatoreUmano)giocatori[0]).puoGiocare())
						((GiocatoreUmano)giocatori[0]).setGiocata(0);
			}
			else if(e.getKeyCode() == KeyEvent.VK_2) {
				if(giocatori[0].getMano().getCarta(1) != null)
					if(((GiocatoreUmano)giocatori[0]).puoGiocare())
						((GiocatoreUmano)giocatori[0]).setGiocata(1);
			}
			else if(e.getKeyCode() == KeyEvent.VK_3) {
				if(giocatori[0].getMano().getCarta(2) != null)
					if(((GiocatoreUmano)giocatori[0]).puoGiocare())
						((GiocatoreUmano)giocatori[0]).setGiocata(2);
			}
		}
	}
}