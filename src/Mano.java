public class Mano {
	private Carta[] mano;

	public Mano(Carta[] m) {
		mano = new Carta[3];
		for(int i=0; i < 3; i++)
			mano[i] = new Carta(m[i]);
	}

	public Mano(Mano m) {
		mano = new Carta[3];
		for(int i=0; i < 3; i++)
			mano[i] = new Carta(m.getCarta(i));
	}

	public Carta getCarta(int i) {
		return mano[i];
	}

	public Carta giocaCarta(int i) {
		Carta c = mano[i];
		mano[i] = null;
		return c;
	}

	public void aggiungi (Carta c) {
		for(int i=0; i < 3; i++)
			if(mano[i] == null) {
				mano[i] = new Carta(c);
				break;
			}
	}

	public int nCarte() {
		int n=0;
		for(int i=0; i < 3;i++)
			if(mano[i] != null)
				n++;
		return n;
	}

	public String toString() {
		return "1) "+mano[0]+"\n2) "+mano[1]+"\n3) "+mano[2];
	}
}