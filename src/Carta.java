import java.awt.Image;
import java.awt.Graphics;

public class Carta {
	private int numero;
	private String seme;

	public Carta(int num, String s) {
		numero = num;
		seme = s;
	}

	public Carta(Carta c) {
		numero = c.getNumero();
		seme = c.getSeme();
	}

	public int getNumero() {
		return numero;
	}

	public String getSeme() {
		return seme;
	}

	public int getPunti() {
		int ret = 0;
		switch(numero) {
			case 1:
				ret = 11;
			break;
			case 3:
				ret = 10;
			break;
			case 8:
				ret = 2;
			break;
			case 9:
				ret = 3;
			break;
			case 10:
				ret = 4;
			break;
			default:
				ret = 0;
			break;
		}
		return ret;
	}

	public int getValore() {
		int ret = 0;
		switch(numero) {
			case 1:
				ret = 10;
			break;
			case 3:
				ret = 9;
			break;
			case 10:
				ret = 8;
			break;
			case 9:
				ret = 7;
			break;
			case 8:
				ret = 6;
			break;
			case 7:
				ret = 5;
			break;
			case 6:
				ret = 4;
			break;
			case 5:
				ret = 3;
			break;
			case 4:
				ret = 2;
			break;
			case 2:
				ret = 1;
			break;
		}
		return ret;
	}

	public boolean prende(Carta altra, Carta briscola) {
		return (((seme == altra.getSeme()) && (getValore() > altra.getValore())) || ((seme == briscola.getSeme())&&(altra.getSeme() != briscola.getSeme())));
	}

	public String toString() {
		String s = "";
		switch(numero) {
			case 1:
				s += "Asso";
			break;
			case 10:
				s += "Re";
			break;
			case 9:
				s += "Cavallo";
			break;
			case 8:
				s += "Fante";
			break;
			default:
				s += "" + numero;
			break;
		}
		s += " di " + seme;
		return s;
	}

	public void disegna(Graphics g, boolean cpu, int x, int y, Image carte, Image coperta) {
		if(cpu)
			g.drawImage(coperta, x, y, 60, 125, null);
		else {
			int yy = 0;
			if(seme=="bastoni")
				yy=0;
			else if(seme=="coppe")
				yy=1;
			else if(seme=="denari")
				yy=2;
			else if(seme=="spade")
				yy=3;
			g.drawImage(carte, x, y, x+60, y+125, (numero-1)*60, yy*125, numero*60, (yy+1)*125, null);
		}
	}
}