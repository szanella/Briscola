public class GiocatoreUmano extends Giocatore {
	private boolean giocato, inputOn;
	int gioc = 0;
	Object lock;

	public GiocatoreUmano(Mano m, String n) {
		super(m,n);
		inputOn = false;
		giocato = false;
		lock = new Object();
	}

	public boolean puoGiocare() {
		return inputOn;
	}

	public void setGiocata(int i) {
		synchronized(lock) {
			gioc = i;
			giocato = true;
			lock.notifyAll();
		}
		//System.out.println("I PLAYED " + giocato);		
	}

	public Carta gioca(Campo campo, Carta briscola) {
		inputOn = true;
		giocato = false;
		synchronized(lock) {
			while(!giocato) {
				try {
					lock.wait();
				}
				catch(InterruptedException e) {}
			}
		}
		//System.out.println("GOTCHA");
		return mano.giocaCarta(gioc);
	}
}