public class Mazzo {
	private Carta[] mazzo;
	private int nCarte;

	public Mazzo(Carta[] carte) {
		mazzo = new Carta[40];
		for(int i=0; i < 40; i++)
			mazzo[i] = carte[i];
		nCarte = 40;
	}

	private void inizializzaMazzo() {
		for(int i=0; i < 10; i++)
			mazzo[i] = new Carta(i+1,"spade");
		for(int i=0; i < 10; i++)
			mazzo[i+10] = new Carta(i+1,"denari");
		for(int i=0; i < 10; i++)
			mazzo[i+20] = new Carta(i+1,"bastoni");
		for(int i=0; i < 10; i++)
			mazzo[i+30] = new Carta(i+1,"coppe");
		nCarte = 40;
	}

	private void mischiaMazzo() {
		for(int i=0; i < 1000; i++) {
			int a = (int)Math.round(Math.random()*40-0.5);
			int b = (int)Math.round(Math.random()*40-0.5);
			Carta c = new Carta(mazzo[a]);
			mazzo[a] = new Carta(mazzo[b]);
			mazzo[b] = new Carta(c);
		}
	}

	public Mazzo() {
		nCarte = 40;
		mazzo = new Carta[40];
		inizializzaMazzo();
		mischiaMazzo();
	}

	public Carta pesca() {
		return new Carta(mazzo[--nCarte]);
	}

	public String toString() {
		String s = "";
		for (int i=0; i < nCarte; i++)
			s += mazzo[i] + "\n";
		return s;
	}

	public int nCarte() {
		return nCarte;
	}

	public boolean mazzoVuoto() {
		return (nCarte == 0);
	}
}