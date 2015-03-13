public class GiocatoreCPU extends Giocatore {
	private final int MAX_PUNTI_SCARTELLA = 0;
	private final int MIN_PUNTI_POCHI_PUNTI = 2;
	private final int MAX_PUNTI_POCHI_PUNTI = 3;
	private final int MIN_PUNTI_MEDI_PUNTI = 4;
	private final int MAX_PUNTI_MEDI_PUNTI = 4;
	private final int MIN_PUNTI_CARICO = 10;
	private final int MAX_VAL_BRISCOLA_BASSA = 4;
	private final int MIN_VAL_BRISCOLA_MEDIA = 5;
	private final int MAX_VAL_BRISCOLA_MEDIA = 7;
	private final int MIN_VAL_BRISCOLA_ALTA = 8;
	private final int P_SCARTELLA = 1;
	private final int P_POCHI_PUNTI = 2;
	private final int P_BRISCOLA_BASSA = 3;
	private final int P_MEDI_PUNTI = 4;
	private final int P_BRISCOLA_MEDIA = 5;
	private final int P_CARICO = 6;
	private final int P_BRISCOLA_ALTA = 7;
	private final int MIN_PUNTI_PRENDERE_TANTO_SENZA_BRISCOLA = 4;
	private final int MIN_PUNTI_PRENDERE_TANTO_CON_BRISCOLA_BASSA = 4;
	private final int MIN_PUNTI_PRENDERE_TANTO_CON_BRISCOLA_ALTA = 10;
	private final int MAX_PUNTI_LASCIARE_POCHI_PUNTI = 5;


	public GiocatoreCPU(Mano m, String n) {
		super(m,n);
	}

	public Mano getMano() {
		return mano;
	}

	//metodi per il calcolo della giocata se si gioca per primi

	public int getPrioritaPrimo(Carta c, Carta briscola) {
		if(c==null)
			return 0;
		//se e` una scartella
		if((c.getSeme()!=briscola.getSeme())&&(c.getPunti()<=MAX_PUNTI_SCARTELLA))
			return P_SCARTELLA;
		//se e` una carta non briscola che vale pochi punti
		if((c.getSeme()!=briscola.getSeme())&&(c.getPunti()>=MIN_PUNTI_POCHI_PUNTI)&&(c.getPunti()<=MAX_PUNTI_POCHI_PUNTI))
			return P_POCHI_PUNTI;
		//se e` una briscola bassa
		if((c.getSeme()==briscola.getSeme())&&(c.getValore()<=MAX_VAL_BRISCOLA_BASSA))
			return P_BRISCOLA_BASSA;
		//se e` una carta non briscola che vale medi punti
		if((c.getSeme()!=briscola.getSeme())&&(c.getPunti()>=MIN_PUNTI_MEDI_PUNTI)&&(c.getPunti()<=MAX_PUNTI_MEDI_PUNTI))
			return P_MEDI_PUNTI;
		//se e` una briscola media
		if((c.getSeme()==briscola.getSeme())&&(c.getValore()>=MIN_VAL_BRISCOLA_MEDIA)&&(c.getValore()<=MAX_VAL_BRISCOLA_MEDIA))
			return P_BRISCOLA_MEDIA;
		//se e` un carico
		if((c.getSeme()!=briscola.getSeme())&&(c.getPunti()>=MIN_PUNTI_CARICO))
			return P_CARICO;
		if((c.getSeme()==briscola.getSeme())&&(c.getValore()>=MIN_VAL_BRISCOLA_ALTA))
			return P_BRISCOLA_ALTA;
		return 0;
	}

	public boolean haScartelle(Carta briscola) {
		for(int i=0; i < 3; i++)
			if(mano.getCarta(i)!=null)
				if(getPrioritaPrimo(mano.getCarta(i),briscola)==P_SCARTELLA)
					return true;
		return false;
	}

	public boolean haPochiPuntiMaxUnaPrioritaMinore(Carta briscola) {
		boolean pochiPunti = false;
		int nPrioritaMinore = 0;
		for(int i=0; i < 3; i++)
			if(mano.getCarta(i)!=null) {
				if(getPrioritaPrimo(mano.getCarta(i),briscola)==P_POCHI_PUNTI)
					pochiPunti = true;
				else if(getPrioritaPrimo(mano.getCarta(i),briscola)>P_POCHI_PUNTI)
					nPrioritaMinore++;
			}
		return ((pochiPunti)&&(nPrioritaMinore<=1));
	}

	public boolean haPochiPuntiDueCartePrioritaMinoreUnaBriscolaBassa(Carta briscola) {
		boolean pochiPunti = false;
		boolean briscolaBassa = false;
		int nPrioritaMinore = 0;
		for(int i=0; i < 3; i++)
			if(mano.getCarta(i)!=null) {
				if(getPrioritaPrimo(mano.getCarta(i),briscola)==P_POCHI_PUNTI)
					pochiPunti = true;
				else if(getPrioritaPrimo(mano.getCarta(i),briscola)>P_POCHI_PUNTI) {
					nPrioritaMinore++;
					if(getPrioritaPrimo(mano.getCarta(i),briscola)==P_BRISCOLA_BASSA)
						briscolaBassa = true;
				}
			}
		return ((pochiPunti)&&(briscolaBassa)&&(nPrioritaMinore==2));
	}

	public boolean haPochiPuntiDueCartePrioritaMinore(Carta briscola) {
		boolean pochiPunti = false;
		int nPrioritaMinore = 0;
		for(int i=0; i < 3; i++)
			if(mano.getCarta(i)!=null) {
				if(getPrioritaPrimo(mano.getCarta(i),briscola)==P_POCHI_PUNTI)
					pochiPunti = true;
				else if(getPrioritaPrimo(mano.getCarta(i),briscola)>P_POCHI_PUNTI)
					nPrioritaMinore++;
			}
		return ((pochiPunti)&&(nPrioritaMinore==2));
	}

	public boolean haDueBriscoleUnaBassa(Carta briscola) {
		int nBriscole = 0;
		boolean briscolaBassa = false;
		for(int i=0; i < 3;i++)
			if(mano.getCarta(i)!=null) {
				if(mano.getCarta(i).getSeme()==briscola.getSeme()) {
					nBriscole++;
					if(getPrioritaPrimo(mano.getCarta(i),briscola)==P_BRISCOLA_BASSA)
						briscolaBassa = true;
				}
			}
		return((nBriscole>=2)&&(briscolaBassa));
	}

	public boolean haTreCarteStessaPriorita(Carta briscola) {
		return ((mano.getCarta(0)!=null)&&(mano.getCarta(1)!=null)&&(mano.getCarta(2)!=null)&&(getPrioritaPrimo(mano.getCarta(0),briscola)==getPrioritaPrimo(mano.getCarta(1),briscola))&&(getPrioritaPrimo(mano.getCarta(0),briscola)==getPrioritaPrimo(mano.getCarta(2),briscola)));
	}

	public boolean haMediPuntiDueCartePrioritaMinoreUguale(Carta briscola) {
		boolean mediPunti = false;
		int nPrioritaMinoreUguale = 0;
		for(int i=0; i<3;i++) {
			if(mano.getCarta(i)!=null) {
				if(getPrioritaPrimo(mano.getCarta(i),briscola)==P_MEDI_PUNTI)
					mediPunti=true;
				if((getPrioritaPrimo(mano.getCarta(i),briscola)>=P_MEDI_PUNTI)||(getPrioritaPrimo(mano.getCarta(i),briscola)==P_BRISCOLA_BASSA))
					nPrioritaMinoreUguale++;
			}
		}
		return ((mediPunti)&&(nPrioritaMinoreUguale>=2));
	}

	public boolean haDueCarichiBriscolaNonAlta(Carta briscola) {
		boolean briscolaNonAlta = false;
		int nCarichi = 0;
		for(int i=0; i < 3;i++)
			if(mano.getCarta(i)!=null) {
				if(getPrioritaPrimo(mano.getCarta(i),briscola)==P_CARICO)
					nCarichi++;
				else if((mano.getCarta(i).getSeme()==briscola.getSeme())&&(getPrioritaPrimo(mano.getCarta(i),briscola)<7))
					briscolaNonAlta = true;
			}
		return((briscolaNonAlta)&&(nCarichi==2));
	}

	public boolean haDueBriscoleMedieCartaPrioritaMinore(Carta briscola) {
		int nBriscoleMedie = 0;
		boolean cartaPrioritaMinore = false;
		for(int i=0; i < 3; i++)
			if(mano.getCarta(i)!=null) {
				if(getPrioritaPrimo(mano.getCarta(i),briscola)==P_BRISCOLA_MEDIA)
					nBriscoleMedie++;
				else if(getPrioritaPrimo(mano.getCarta(i),briscola)>P_BRISCOLA_MEDIA)
					cartaPrioritaMinore = true;
			}
		return((cartaPrioritaMinore)&&(nBriscoleMedie==2));
	}

	public boolean haDueBriscoleAlteBriscolaNonAlta(Carta briscola) {
		int nBriscoleAlte = 0;
		boolean briscolaNonAlta = false;
		for(int i=0; i < 3;i++)
			if(mano.getCarta(i)!=null) {
				if(getPrioritaPrimo(mano.getCarta(i),briscola)==P_BRISCOLA_ALTA)
					nBriscoleAlte++;
				else if((getPrioritaPrimo(mano.getCarta(i),briscola)==P_BRISCOLA_BASSA)||(getPrioritaPrimo(mano.getCarta(i),briscola)==P_BRISCOLA_MEDIA))
					briscolaNonAlta = true;
			}
		return ((briscolaNonAlta)&&(nBriscoleAlte==2));
	}

	public boolean haBriscolaAltaCaricoBriscolaMedia(Carta briscola) {
		boolean briscolaAlta = false;
		boolean carico = false;
		boolean briscolaMedia = false;
		for(int i=0;i<3;i++)
			if(mano.getCarta(i)!=null) {
				if(getPrioritaPrimo(mano.getCarta(i),briscola)==P_BRISCOLA_ALTA)
					briscolaAlta = true;
				else if (getPrioritaPrimo(mano.getCarta(i),briscola)==P_CARICO)
					carico = true;
				else if (getPrioritaPrimo(mano.getCarta(i),briscola)==P_BRISCOLA_MEDIA)
					briscolaMedia = true;
			}
		return((briscolaMedia)&&(carico)&&(briscolaAlta));
	}

	public boolean haDueBriscoleAlteCarico(Carta briscola) {
		int nBriscoleAlte = 0;
		boolean carico = false;
		for(int i=0; i<3;i++)
			if(mano.getCarta(i)!=null) {
				if(getPrioritaPrimo(mano.getCarta(i),briscola)==P_BRISCOLA_ALTA)
					nBriscoleAlte++;
				else if(getPrioritaPrimo(mano.getCarta(i),briscola)==P_CARICO)
					carico = true;
			}
		return ((carico)&&(nBriscoleAlte==2));
	}

	public boolean haDueCarichiBriscolaAlta(Carta briscola) {
		int nCarichi = 0;
		boolean briscolaAlta = false;
		for(int i=0; i<3;i++)
			if(mano.getCarta(i)!=null) {
				if(getPrioritaPrimo(mano.getCarta(i),briscola)==P_BRISCOLA_ALTA)
					briscolaAlta = true;
				else if(getPrioritaPrimo(mano.getCarta(i),briscola)==P_CARICO)
					nCarichi++;
			}
		return ((nCarichi==2)&&(briscolaAlta));
	}

	//metodi per giocare per primo

	public Carta giocaScartella(Carta briscola) {
		int sc = 0;
		if(mano.getCarta(0) == null)
			sc = 1;
		for(int i=0;i<3;i++)
			if(mano.getCarta(i)!=null)
				if((getPrioritaPrimo(mano.getCarta(i),briscola)==P_SCARTELLA)&&((getPrioritaPrimo(mano.getCarta(sc),briscola)!=P_SCARTELLA)||((getPrioritaPrimo(mano.getCarta(sc),briscola)==P_SCARTELLA)&&(mano.getCarta(i).getValore()<=mano.getCarta(sc).getValore()))))
					sc = i;
		return mano.giocaCarta(sc);
	}

	public Carta giocaPochiPunti(Carta briscola) {
		int sc = 0;
		if(mano.getCarta(0) == null)
			sc = 1;
		for(int i=0;i<3;i++)
			if(mano.getCarta(i)!=null)
				if((getPrioritaPrimo(mano.getCarta(i),briscola)==P_POCHI_PUNTI)&&((getPrioritaPrimo(mano.getCarta(sc),briscola)!=P_POCHI_PUNTI)||((getPrioritaPrimo(mano.getCarta(sc),briscola)==P_POCHI_PUNTI)&&(mano.getCarta(i).getValore()<=mano.getCarta(sc).getValore()))))
					return mano.giocaCarta(i);
		return null;
	}

	public Carta giocaBriscolaBassa(Carta briscola) {
		int sc = 0;
		if(mano.getCarta(0) == null)
			sc = 1;
		for(int i=0;i<3;i++)
			if(mano.getCarta(i)!=null)
				if((getPrioritaPrimo(mano.getCarta(i),briscola)==P_BRISCOLA_BASSA)&&((getPrioritaPrimo(mano.getCarta(sc),briscola)!=P_BRISCOLA_BASSA)||((getPrioritaPrimo(mano.getCarta(sc),briscola)==P_BRISCOLA_BASSA)&&(mano.getCarta(i).getValore()<=mano.getCarta(sc).getValore()))))
					return mano.giocaCarta(i);
		return null;
	}

	public Carta giocaCartaPiuBassa(Carta briscola) {
		int val=0;
		int ind=0;
		for(int i=0;i<3;i++)
			if(mano.getCarta(i)!=null)
				if((mano.getCarta(i)!=null)&&(mano.getCarta(i).getValore()>val)) {
					ind = i;
					val = mano.getCarta(i).getValore();
				}
		return mano.giocaCarta(ind);
	}

	public Carta giocaMediPunti(Carta briscola) {
		int sc = 0;
		if(mano.getCarta(0) == null)
			sc = 1;
		for(int i=0;i<3;i++)
			if(mano.getCarta(i)!=null)
				if((getPrioritaPrimo(mano.getCarta(i),briscola)==P_MEDI_PUNTI)&&((getPrioritaPrimo(mano.getCarta(sc),briscola)!=P_MEDI_PUNTI)||((getPrioritaPrimo(mano.getCarta(sc),briscola)==P_MEDI_PUNTI)&&(mano.getCarta(i).getValore()<=mano.getCarta(sc).getValore()))))
					return mano.giocaCarta(i);
		return null;
	}

	public Carta giocaBriscola(Carta briscola) {
		int sc = 0;
		if(mano.getCarta(0) == null)
			sc = 1;
		for(int i=0;i<3;i++)
			if(mano.getCarta(i)!=null)
				if(((mano.getCarta(i)!=null)&&(mano.getCarta(i).getSeme()==briscola.getSeme()))&&((mano.getCarta(sc).getSeme()!=briscola.getSeme())||((mano.getCarta(sc).getSeme()==briscola.getSeme())&&(mano.getCarta(i).getValore()<=mano.getCarta(sc).getValore()))))
					return mano.giocaCarta(i);
		return null;
	}

	public Carta giocaBriscolaMedia(Carta briscola) {
		int sc = 0;
		if(mano.getCarta(0) == null)
			sc = 1;
		for(int i=0;i<3;i++)
			if(mano.getCarta(i)!=null)
				if((getPrioritaPrimo(mano.getCarta(i),briscola)==P_BRISCOLA_MEDIA)&&((getPrioritaPrimo(mano.getCarta(sc),briscola)!=P_BRISCOLA_MEDIA)||((getPrioritaPrimo(mano.getCarta(sc),briscola)==P_BRISCOLA_MEDIA)&&(mano.getCarta(i).getValore()<=mano.getCarta(sc).getValore()))))
					return mano.giocaCarta(i);
		return null;
	}

	public Carta giocaBriscolaAlta(Carta briscola) {
		int sc = 0;
		if(mano.getCarta(0) == null)
			sc = 1;
		for(int i=0;i<3;i++)
			if(mano.getCarta(i)!=null)
				if((getPrioritaPrimo(mano.getCarta(i),briscola)==P_BRISCOLA_ALTA)&&((getPrioritaPrimo(mano.getCarta(sc),briscola)!=P_BRISCOLA_ALTA)||((getPrioritaPrimo(mano.getCarta(sc),briscola)==P_BRISCOLA_ALTA)&&(mano.getCarta(i).getValore()<=mano.getCarta(sc).getValore()))))
					return mano.giocaCarta(i);
		return null;
	}

	public Carta giocaBriscolaNonAlta(Carta briscola) {
		int sc = 0;
		if(mano.getCarta(0) == null)
			sc = 1;
		for(int i=0;i<3;i++)
			if(mano.getCarta(i)!=null)
				if(((getPrioritaPrimo(mano.getCarta(i),briscola)==P_BRISCOLA_MEDIA)||(getPrioritaPrimo(mano.getCarta(i),briscola)==P_BRISCOLA_BASSA))&&(((getPrioritaPrimo(mano.getCarta(sc),briscola)!=P_BRISCOLA_BASSA)&&(getPrioritaPrimo(mano.getCarta(sc),briscola)!=P_BRISCOLA_ALTA))||(((getPrioritaPrimo(mano.getCarta(sc),briscola)==P_BRISCOLA_BASSA)||(getPrioritaPrimo(mano.getCarta(sc),briscola)==P_BRISCOLA_ALTA))&&(mano.getCarta(i).getValore()<=mano.getCarta(sc).getValore()))))
					return mano.giocaCarta(i);
		return null;
	}

	public Carta giocaCarico(Carta briscola) {
		int sc = 0;
		if(mano.getCarta(0) == null)
			sc = 1;
		for(int i=0;i<3;i++)
			if(mano.getCarta(i)!=null)
				if((getPrioritaPrimo(mano.getCarta(i),briscola)==P_CARICO)&&((getPrioritaPrimo(mano.getCarta(sc),briscola)!=P_CARICO)||((getPrioritaPrimo(mano.getCarta(sc),briscola)==P_CARICO)&&(mano.getCarta(i).getValore()<=mano.getCarta(sc).getValore()))))
					return mano.giocaCarta(i);
		return null;
	}

	//metodi per il calcolo della giocata se si gioca per secondi

	public boolean puoPrendereTantiPuntiSenzaBriscola(Carta campo, Carta briscola) {
		for(int i=0; i < 3; i++)
			if(mano.getCarta(i)!=null)
				if((mano.getCarta(i).prende(campo,briscola))&&(mano.getCarta(i).getSeme()!=briscola.getSeme())&&((mano.getCarta(i).getPunti()+campo.getPunti())>=MIN_PUNTI_PRENDERE_TANTO_SENZA_BRISCOLA))
					return true;
		return false;
	}

	public boolean puoPrendereTantiPuntiConBriscolaBassa(Carta campo, Carta briscola) {
		for(int i=0; i < 3; i++)
			if(mano.getCarta(i)!=null)
				if((mano.getCarta(i).prende(campo,briscola))&&((getPrioritaPrimo(mano.getCarta(i),briscola)==P_BRISCOLA_BASSA)||(getPrioritaPrimo(mano.getCarta(i),briscola)==P_BRISCOLA_MEDIA))&&(campo.getPunti()>=MIN_PUNTI_PRENDERE_TANTO_CON_BRISCOLA_BASSA))
					return true;
		return false;
	}

	public boolean puoPrendereTantiPuntiConBriscolaAlta(Carta campo, Carta briscola) {
		for(int i=0; i < 3; i++)
			if(mano.getCarta(i)!=null)
				if((mano.getCarta(i).prende(campo,briscola))&&(getPrioritaPrimo(mano.getCarta(i),briscola)==P_BRISCOLA_ALTA)&&(campo.getPunti()>=MIN_PUNTI_PRENDERE_TANTO_CON_BRISCOLA_BASSA))
					return true;
		return false;
	}

	public boolean puoLasciarePochiPunti(Carta campo, Carta briscola) {
		for(int i=0; i < 3; i++)
			if(mano.getCarta(i)!=null)
				if((!(mano.getCarta(i).prende(campo,briscola)))&&((mano.getCarta(i).getPunti()+campo.getPunti())<=MAX_PUNTI_LASCIARE_POCHI_PUNTI))
					return true;
		return false;
	}

	public boolean puoPrenderePochiPuntiSenzaBriscole(Carta campo, Carta briscola) {
		for(int i=0; i < 3; i++)
			if(mano.getCarta(i)!=null)
				if((mano.getCarta(i).prende(campo,briscola))&&(mano.getCarta(i).getSeme()!=briscola.getSeme())&&((mano.getCarta(i).getPunti()+campo.getPunti())<MIN_PUNTI_PRENDERE_TANTO_SENZA_BRISCOLA))
					return true;
		return false;
	}

	public boolean puoPrenderePochiPuntiConBriscolaBassa(Carta campo, Carta briscola) {
		for(int i=0; i < 3; i++)
			if(mano.getCarta(i)!=null)
				if((mano.getCarta(i).prende(campo,briscola))&&((getPrioritaPrimo(mano.getCarta(i),briscola)==P_BRISCOLA_BASSA)||(getPrioritaPrimo(mano.getCarta(i),briscola)==P_BRISCOLA_MEDIA))&&(campo.getPunti()<MIN_PUNTI_PRENDERE_TANTO_CON_BRISCOLA_BASSA))
					return true;
		return false;
	}

	public boolean puoLasciareTantiPunti(Carta campo, Carta briscola) {
		for(int i=0; i < 3; i++)
			if(mano.getCarta(i)!=null)
				if((!(mano.getCarta(i).prende(campo, briscola)))&&((mano.getCarta(i).getPunti()+campo.getPunti())>=MAX_PUNTI_LASCIARE_POCHI_PUNTI))
					return true;
		return false;
	}

	public boolean puoPrenderePochiPuntiConBriscolaAlta(Carta campo, Carta briscola) {
		for(int i=0; i < 3; i++)
			if(mano.getCarta(i)!=null)
				if((mano.getCarta(i).prende(campo,briscola))&&(getPrioritaPrimo(mano.getCarta(i),briscola)==P_BRISCOLA_ALTA)&&(campo.getPunti()>=MIN_PUNTI_PRENDERE_TANTO_CON_BRISCOLA_ALTA))
					return true;
		return false;
	}

	public Carta prendiSenzaBriscola(Carta campo, Carta briscola) {
		int sc = 0;
		if(mano.getCarta(0) == null)
			sc = 1;
		for(int i=0; i < 3; i++)
			if(mano.getCarta(i) != null)
				if((mano.getCarta(i).getSeme()!=briscola.getSeme())&&(mano.getCarta(i).prende(campo,briscola))&&((!(mano.getCarta(sc).prende(campo,briscola)))||(mano.getCarta(sc).getSeme()==briscola.getSeme())||((mano.getCarta(sc).prende(campo,briscola))&&(mano.getCarta(i).getSeme()!=briscola.getSeme())&&(mano.getCarta(i).getValore()>mano.getCarta(sc).getValore()))))
					sc = i;
		return mano.giocaCarta(sc);
	}

	public Carta prendiConBriscola(Carta campo, Carta briscola) {
		int sc = 0;
		if(mano.getCarta(0) == null)
			sc = 1;
		for(int i=0; i < 3; i++)
			if(mano.getCarta(i) != null)
				if((mano.getCarta(i).prende(campo,briscola))&&(mano.getCarta(i).getSeme()==briscola.getSeme())&&((!(mano.getCarta(sc).prende(campo,briscola)))||(mano.getCarta(sc).getSeme()!=briscola.getSeme())||((mano.getCarta(sc).prende(campo,briscola))&&(mano.getCarta(sc).getSeme()==briscola.getSeme())&&(mano.getCarta(i).getValore()<mano.getCarta(sc).getValore()))))
					sc = i;
		return mano.giocaCarta(sc);
	}

	public Carta lascia(Carta campo, Carta briscola) {
		int sc = 0;
		if(mano.getCarta(0) == null)
			sc = 1;
		for(int i=0; i < 3; i++)
			if(mano.getCarta(i) != null)
				if((!(mano.getCarta(i).prende(campo,briscola)))&&((mano.getCarta(sc).prende(campo,briscola))||((!(mano.getCarta(sc).prende(campo,briscola)))&&(mano.getCarta(i).getValore()<mano.getCarta(sc).getValore()))))
					sc = i;
		return mano.giocaCarta(sc);
	}

	//metodi per il calcolo della giocata se si gioca per primi con due carte

	public boolean haScartelleDueCarte(Carta briscola) {
		for(int i=0; i < 3; i++)
			if(mano.getCarta(i)!=null)
				if(getPrioritaPrimo(mano.getCarta(i), briscola)==P_SCARTELLA)
					return true;
		return false;
	}

	public boolean haPochiPuntiDueCarte(Carta briscola) {
		for(int i=0; i < 3; i++)
			if(mano.getCarta(i)!=null)
				if(getPrioritaPrimo(mano.getCarta(i), briscola)==P_POCHI_PUNTI)
					return true;
		return false;
	}

	public boolean haBriscolaMediPuntiDueCarte(Carta briscola) {
		boolean brisc = false;
		boolean mediPunti = false;
		for(int i=0; i < 3; i++)
			if(mano.getCarta(i)!=null)
				if(mano.getCarta(i).getSeme()==briscola.getSeme())
					brisc = true;
				else if(getPrioritaPrimo(mano.getCarta(i), briscola)==P_MEDI_PUNTI)
					mediPunti = true;
		return ((brisc)&&(mediPunti));
	}

	public boolean haBriscolaBassaCartaPrioritaMinoreDueCarte(Carta briscola) {
		boolean briscolaBassa = false;
		boolean cartaPrioritaMinore = false;
		for(int i=0; i < 3; i++)
			if(mano.getCarta(i)!=null)
				if(getPrioritaPrimo(mano.getCarta(i), briscola)==P_BRISCOLA_BASSA)
					briscolaBassa = true;
				else if(getPrioritaPrimo(mano.getCarta(i),briscola)>P_BRISCOLA_BASSA)
					cartaPrioritaMinore = true;
		return ((briscolaBassa)&&(cartaPrioritaMinore));
	}

	public boolean haDueCartePrioritaUgualeDueCarte(Carta briscola) {
		Carta prima = null;
		for(int i=0; i < 3; i++)
			if(mano.getCarta(i)!=null)
				if(prima == null)
					prima = mano.getCarta(i);
				else if(getPrioritaPrimo(prima,briscola)==getPrioritaPrimo(mano.getCarta(i),briscola))
					return true;
		return false;

	}

	public boolean haBriscolaCaricoDueCarte(Carta briscola) {
		boolean brisc = false;
		boolean carico = false;
		for(int i=0; i < 3; i++)
			if(mano.getCarta(i)!=null)
				if(mano.getCarta(i).getSeme()==briscola.getSeme())
					brisc = true;
				else if(getPrioritaPrimo(mano.getCarta(i), briscola)==P_CARICO)
					carico = true;
		return ((brisc)&&(carico));
	}

	public boolean haMediPuntiCaricoDueCarte(Carta briscola) {
		boolean mediPunti = false;
		boolean carico = false;
		for(int i=0; i < 3; i++)
			if(mano.getCarta(i)!=null)
				if(getPrioritaPrimo(mano.getCarta(i), briscola)==P_MEDI_PUNTI)
					mediPunti = true;
				else if(getPrioritaPrimo(mano.getCarta(i), briscola)==P_CARICO)
					carico = true;
		return ((mediPunti)&&(carico));
	}

	public boolean haBriscolaMediaBriscolaAltaDueCarte(Carta briscola) {
		boolean briscolaBassa = false;
		boolean briscolaAlta = false;
		for(int i=0; i < 3; i++)
			if(mano.getCarta(i)!=null)
				if(getPrioritaPrimo(mano.getCarta(i), briscola)==P_BRISCOLA_BASSA)
					briscolaBassa = true;
				else if(getPrioritaPrimo(mano.getCarta(i),briscola)==P_BRISCOLA_ALTA)
					briscolaAlta = true;
		return ((briscolaBassa)&&(briscolaAlta));
	}

	public Carta giocaUnicaCarta() {
		for(int i=0; i < 3; i++)
			if(mano.getCarta(i) != null)
				return mano.giocaCarta(i);
		return null;
	}
	
	public Carta giocaCartaCaso() {
		int i = -1;
		do{
			i = (int)Math.floor(Math.random()*3);
		}while(mano.getCarta(i) == null);
		return mano.giocaCarta(i);
	}

	//metodo per giocare una carta in qualunque situazione
	public Carta gioca(Campo campo, Carta briscola) {
		if(campo.campoVuoto()) {
			if (mano.nCarte() == 3) {
				if(haScartelle(briscola))
					return giocaScartella(briscola);
				else if((haPochiPuntiMaxUnaPrioritaMinore(briscola))||(haPochiPuntiDueCartePrioritaMinore(briscola)))
					return giocaPochiPunti(briscola);
				else if((haPochiPuntiDueCartePrioritaMinoreUnaBriscolaBassa(briscola))||(haDueBriscoleUnaBassa(briscola)))
					return giocaBriscolaBassa(briscola);
				else if(haDueBriscoleAlteBriscolaNonAlta(briscola))
					return giocaBriscolaNonAlta(briscola);
				else if(haDueCarichiBriscolaNonAlta(briscola))
					return giocaBriscola(briscola);
				else if(haMediPuntiDueCartePrioritaMinoreUguale(briscola))
					return giocaMediPunti(briscola);
				else if((haDueBriscoleMedieCartaPrioritaMinore(briscola))||(haBriscolaAltaCaricoBriscolaMedia(briscola)))
					return giocaBriscolaMedia(briscola);
				else if(haDueCarichiBriscolaAlta(briscola))
					return giocaCarico(briscola);
				else if(haDueBriscoleAlteCarico(briscola))
					return giocaBriscolaAlta(briscola);
				else if(haTreCarteStessaPriorita(briscola))
					return giocaCartaPiuBassa(briscola);
				else
					return giocaCartaCaso();
			}
			else if(mano.nCarte()==2) {
				if(haScartelleDueCarte(briscola))
					return giocaScartella(briscola);
				else if(haPochiPuntiDueCarte(briscola))
					return giocaPochiPunti(briscola);
				else if(haDueCartePrioritaUgualeDueCarte(briscola))
					return giocaCartaPiuBassa(briscola);
				else if(haBriscolaMediPuntiDueCarte(briscola))
					return giocaMediPunti(briscola);
				else if(haBriscolaBassaCartaPrioritaMinoreDueCarte(briscola))
					return giocaBriscolaBassa(briscola);
				else if(haBriscolaCaricoDueCarte(briscola))
					return giocaBriscola(briscola);
				else if(haMediPuntiCaricoDueCarte(briscola))
					return giocaMediPunti(briscola);
				else if(haBriscolaMediaBriscolaAltaDueCarte(briscola))
					return giocaBriscolaMedia(briscola);
				else
					return giocaCartaCaso();
			}
			else
				return giocaUnicaCarta();
		}
		else {
			if(mano.nCarte()!=1) {
				if(puoPrendereTantiPuntiSenzaBriscola(campo.getCartaCampo(0),briscola))
					return prendiSenzaBriscola(campo.getCartaCampo(0),briscola);
				else if((puoPrendereTantiPuntiConBriscolaBassa(campo.getCartaCampo(0),briscola))||(puoPrendereTantiPuntiConBriscolaAlta(campo.getCartaCampo(0),briscola)))
					return prendiConBriscola(campo.getCartaCampo(0),briscola);
				else if(puoLasciarePochiPunti(campo.getCartaCampo(0),briscola))
					return lascia(campo.getCartaCampo(0),briscola);
				else if(puoPrenderePochiPuntiSenzaBriscole(campo.getCartaCampo(0),briscola))
					return prendiSenzaBriscola(campo.getCartaCampo(0),briscola);
				else if(puoPrenderePochiPuntiConBriscolaBassa(campo.getCartaCampo(0),briscola))
					return prendiConBriscola(campo.getCartaCampo(0),briscola);
				else if(puoLasciareTantiPunti(campo.getCartaCampo(0),briscola))
					return lascia(campo.getCartaCampo(0),briscola);
				else if(puoPrenderePochiPuntiConBriscolaAlta(campo.getCartaCampo(0),briscola))
					return prendiConBriscola(campo.getCartaCampo(0),briscola);
				else
					return giocaCartaCaso();
			}
			else
				return giocaUnicaCarta();
		}
	}
}