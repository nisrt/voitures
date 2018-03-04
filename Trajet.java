package hash;

public class Trajet implements Comparable<Trajet> {
	int id;
	int startX;
	int startY;
	int finishX;
	int finishY;
	int earliestStart;
	int latestFinish;
	int effectue;
	
	
	public Trajet(int indice, int sX, int sY, int fX, int fY, int eS, int lF) {
		id = indice;
		startX = sX;
		startY = sY;
		finishX = fX;
		finishY = fY;
		earliestStart = eS;
		latestFinish = lF;
		effectue = 0;
	}
	

	@Override
	public int compareTo(Trajet item) {
		return Integer.compare(this.earliestStart, item.earliestStart);
	}
}
