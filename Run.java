package hash;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

import hash.Voiture;

public class Run {
	static Trajet[] recupererTrajetsPossibles(int temps,Voiture v,Trajet[] listeTrajets, int nbTrajetsTotal, int nbTrajetsVoulu) {
	    int i=0;
	    Trajet[] tmp = new Trajet[nbTrajetsVoulu];
	    int cpt=0;
	    while(cpt<nbTrajetsVoulu && i < nbTrajetsTotal) {
	        if(listeTrajets[i].effectue == 0 && Utils.trajet_faisable(temps, v, listeTrajets[i])) {
	            tmp[cpt] = listeTrajets[i];
	            cpt++;
	        }
	        i++;
	    }
	    return tmp;
	}


	static Trajet choisir_trajet(int temps,Voiture v,Trajet[] trajetsPossibles,int nbTrajetsVoulu,int bonusGlobal) {
	    int[] tmp = new int[nbTrajetsVoulu];
	    int tmpSize=0;
	    int cpt = 0;
	    int r;
	    for (int i=0; i<nbTrajetsVoulu;i++) {
	    	if(trajetsPossibles[i] == null) {continue;}
	        int my_bonus = Utils.bonus(temps, v, trajetsPossibles[i]);
	        int score = Utils.gain_trajet(trajetsPossibles[i])+my_bonus*bonusGlobal;
	        int val = Math.max(1,score-trajetsPossibles[i].earliestStart*my_bonus);
	        int cpt_tmp = cpt +val;
	        tmp[i] = cpt_tmp;
	        tmpSize++;
	        cpt += cpt_tmp;
		}
	    r = (int) (Math.random()*(cpt + 1));
	    for (int i = 0; i < tmpSize;i++) {
	        if (r < tmp[i]) {
	            return trajetsPossibles[i];
	        }
	    }
		return null;
	}

	static int algo(Trajet[] listeTrajets,int nbTrajetsTotal,Voiture[] listeVoitures,int nbVoitures,  int tempsMax,int bonus) {
	    int score_total = 0;
	    int nbTrajetsRestant = nbTrajetsTotal;
	    int tempsAttente;
	    for(int i = 0; i< tempsMax+1;i++) {
	        for (Voiture v : listeVoitures) {
	            v.tick();
	            if (v.ready()){
	                //POUR LE PREMIER EXEMPLE
	                //nb = len(tab_voiture)
	            	int nbTrajetsVoulu = (int)(nbVoitures/4);
	                Trajet[] trajet_possible = recupererTrajetsPossibles(i,v,listeTrajets,nbTrajetsTotal,nbTrajetsVoulu);
	                if(trajet_possible == null) {
	                    continue;
	                }
	                Trajet trajet = choisir_trajet(i, v,trajet_possible,nbTrajetsVoulu, bonus);
	                if(trajet==null) {
	                	continue;
	                }

	                score_total += Utils.gain_trajet(trajet)+Utils.bonus(i, v, trajet)*bonus;
	                tempsAttente = Math.max(0,trajet.earliestStart-(i+Utils.dist(v.x,trajet.startX,v.y,trajet.startY)));
	                v.tempsIndisponible = Utils.dist(v.x,trajet.startX,v.y,trajet.startY)+Utils.dist(trajet.startX,trajet.finishX,trajet.startY,trajet.finishY)+tempsAttente;
	                v.trajetsEffectuees.add(trajet.id);
	                for(Trajet tr : listeTrajets) {
	                	if(tr.id == trajet.id) {
	                		tr.effectue = 1;
	                		nbTrajetsRestant--;
	                		break;
	                	}
	                }
	                if(nbTrajetsRestant == 0) {
	                    return score_total;
	                }
	            }
	        }
	    }
	    return score_total;
	}
	
	public static void main(String args[]) throws IOException {
	    Utils u = new Utils();
	    
	    Voiture[] listeVoitures;
	    Trajet[] listeTrajets;
	    Voiture[] listeVoituresResultat = null;

	    LinkedList<Integer> infos;
	    int nbRun = 3;
	    int nbLignes,nbColonnes,nbVehicules,nbTrajets,bonusGlobal,tempsMax;
	    String a = "C:\\Users\\nicoa\\eclipse-workspace\\hash\\src\\hash\\a_example.in";
	    String b = "C:\\Users\\nicoa\\eclipse-workspace\\hash\\src\\hash\\b_should_be_easy.in";
	    String c = "C:\\Users\\nicoa\\eclipse-workspace\\hash\\src\\hash\\c_no_hurry.in";
	    String d = "C:\\Users\\nicoa\\eclipse-workspace\\hash\\src\\hash\\d_metropolis.in";
	    String e = "C:\\Users\\nicoa\\eclipse-workspace\\hash\\src\\hash\\e_high_bonus.in";
	    String[] allInput = {c};
	    int scoreTotal=0;
	    for(String to_read : allInput) {
	    	int maxScore=0;
		    for(int i=0; i < nbRun; i++) {
		    	int score = 0;
			    
		    	infos = u.read_infos(to_read);
		    	
		        nbLignes = infos.get(0);
		        nbColonnes = infos.get(1);
		        nbVehicules = infos.get(2);
		        nbTrajets = infos.get(3);
		        bonusGlobal = infos.get(4);
		        tempsMax = infos.get(5);
		        listeTrajets = new Trajet[nbTrajets];
		        
		        listeTrajets = u.lecture(to_read,nbTrajets);
		        Arrays.sort(listeTrajets);
		        listeVoitures = new Voiture[nbVehicules];
		        for(int k = 0; k < nbVehicules;k++) {
		        	listeVoitures[k] = new Voiture();
		        }
		        score=algo(listeTrajets,nbTrajets,listeVoitures,nbVehicules,tempsMax,bonusGlobal);
		        if(score > maxScore) {
		        	maxScore = score;
		        	listeVoituresResultat = listeVoitures;
		        }
		        System.out.println("Iteration "+i+" "+score);
		    }
		    u.ecrireResultat(listeVoituresResultat, to_read+".out");
		    System.out.println("resultat : "+ maxScore);
		    scoreTotal+=maxScore;
	    }
	    System.out.println("Points gagnés : "+scoreTotal);
	}
}
