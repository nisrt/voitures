package hash;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import hash.Trajet;

//Packages à importer afin d'utiliser l'objet File


public class Utils {


	Trajet[] lecture(String file,int size) throws IOException {
		Trajet[] listeTrajets = new Trajet[size];
		FileReader myFile = new FileReader(file);
		try (BufferedReader br = new BufferedReader(myFile)) {

			String line;
			int indice = 0;
			line = br.readLine();
			while ((line = br.readLine()) != null) {
	            List<Integer> splittedLine = Arrays.stream(line.split(" "))
	                    .map((x) -> Integer.parseInt(x))
	                    .collect(Collectors.toList());
	            Trajet t = new Trajet(indice,splittedLine.get(0),splittedLine.get(1),splittedLine.get(2),splittedLine.get(3),splittedLine.get(4),splittedLine.get(5));
	            listeTrajets[indice] = t;
	            indice++;
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		myFile.close();
	    return listeTrajets;
	}	
	
	
	//Lit la premiere ligne du fichier dentree
	LinkedList<Integer> read_infos(String file) throws IOException{
		LinkedList<Integer> infos = new LinkedList<Integer>();
		FileReader myFile = new FileReader(file);
		try (BufferedReader br = new BufferedReader(myFile)) {

			String line;
			
			if ((line = br.readLine()) != null) {
	            List<Integer> splittedLine = Arrays.stream(line.split(" "))
	                    .map((x) -> Integer.parseInt(x))
	                    .collect(Collectors.toList());
	            for(int elt : splittedLine) {
	            	infos.add(elt);
	            }
			}
		br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		myFile.close();
	    return infos;
	}
	void ecrireResultat(Voiture[] tabVoiture, String name) throws IOException {

	        BufferedWriter output = null;
	        try {
	            File file = new File(name);
	            output = new BufferedWriter(new FileWriter(file));
	            for(Voiture v : tabVoiture) {
	            	if(v.trajetsEffectuees.size() > 0) {
		            	output.write(""+v.trajetsEffectuees.size());
		            	for(int tr : v.trajetsEffectuees) {
			            	output.write(" "+tr);		            		
		            	}
		            	output.write("\n");
	            	}
	            }
	        } catch ( IOException e ) {
	            e.printStackTrace();
	        } finally {
	          if ( output != null ) {
	            output.close();
	          }
	        }
	}
	

	
	
	static int dist(int x1, int x2, int y1, int y2) {
		return Math.abs(x1-x2) + Math.abs(y1-y2);
	}
	
	static int bonus(int temps,Voiture v,Trajet t ) {
	     if(temps+dist(v.x,t.startX,v.y,t.startY)-t.earliestStart <=0) {
	    	 return 1;
	     }
	     return 0;
	}

	static int gain_trajet(Trajet t) {
	    return dist(t.startX,t.finishX,t.startY,t.finishY);
	}
	
	static boolean trajet_faisable(int temps,Voiture v,Trajet t) {
	    return (temps+dist(v.x,t.startX,v.y,t.startY)+dist(t.startX,t.startY,t.finishX,t.finishY)-t.latestFinish)<= 0;
	}

	
}
