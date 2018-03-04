package hash;
import java.util.*;

public class Voiture {
	
	int x;
	int y;
	int tempsIndisponible;
	LinkedList<Integer> trajetsEffectuees;

	
	public Voiture() {
		x = 0;
		y = 0;
		tempsIndisponible = 0;
		trajetsEffectuees = new LinkedList<Integer>();
	}
	
	void tick() {
		if (tempsIndisponible >0){
			tempsIndisponible -=1;
		}
	}
	
	boolean ready() {
		return tempsIndisponible==0;
	}
}
