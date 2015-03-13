public abstract class Giocatore {
	protected Mano mano;
	protected String nome;

	public Giocatore(Mano m, String n) {
		mano = new Mano(m);
		nome = n;
	}

	public void prendiCarta(Carta c) {
		mano.aggiungi(c);
	}

	public Mano getMano() {
		return mano;
	}

	public String getNome() {
		return nome;
	}

	public abstract Carta gioca(Campo campo, Carta briscola);

	public String toString() {
		return nome+"\n"+mano.toString();
	}
}