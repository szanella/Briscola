public class Campo {
	private Mazzo mazzo;
	private Carta[] carte;

	public Campo() {
		mazzo = new Mazzo();
		carte = new Carta[2];
		carte[0] = null;
		carte[1] = null;
	}

	public Carta pesca() {
		return mazzo.pesca();
	}

	public boolean mazzoVuoto() {
		return mazzo.mazzoVuoto();
	}

	public boolean ultimaMano() {
		return mazzo.nCarte() == 1;
	}

	public int nCarte() {
		int n = 0;
		for(int i=0; i < 2; i++)
			if(carte[i]!=null)
				n++;
			else
				return n;
		return n;
	}

	public void giocaCarta(Carta c) {
		if (carte[0] == null)
			carte[0] = c;
		else if(carte[1] == null)
			carte[1] = c;
	}

	public boolean campoVuoto() {
		return (carte[0] == null);
	}

	public Carta getCartaCampo(int i) {
		return carte[i];
	}

	public void svuota() {
		carte[0] = null;
		carte[1] = null;
	}
}