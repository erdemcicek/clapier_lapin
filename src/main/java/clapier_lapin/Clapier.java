package clapier_lapin;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;


public class Clapier {
	
	public static class Lapin{
		
		public Lapin() {
			enfants = new HashSet<>();
		}
		 
		//! I used this part just for demonstration purposes
//		public Lapin(String name) {
//			this();
//			this.name = name;
//		}
//		
//		String name;
//		public String toString() {
//			return name;
//		}

		Lapin pere;
		
		public void addChild(Lapin enfant) {
			this.enfants.add(enfant);
			// Cette attribution en fait casse le principe "Single Responsibility"
			enfant.pere = this;
		}
		
		/*
		Je n'ai pas voulu changer votre code 
		Parce qu'on avait utilisé les attributions 
			telles que "enfant = enfant.pere" pour faire itération dans la méthode "estAncestre"
		
		//! Dans ce setter, on fait une attribution et on appelle une autre méthode
		//! Ceci est, donc, une implementation plus pertinent à cette règle (SRP) à mon avis

		public void setPere(Lapin pere) {
			this.pere = pere;
			addChild();
		}
		
		public void addChild() {
			this.enfants.add(this);
		}
		
		*/
		
		Set<Lapin> enfants;
		
		public Set<Lapin> getAllLevelChildren(){
			Set<Lapin> set_a_retourner = new HashSet<>();
			
			Queue<Lapin> q = new LinkedList<>(enfants);
			
			while(!q.isEmpty()) {
				Lapin front = q.poll();
				set_a_retourner.add(front);
				
				if(!front.enfants.isEmpty()) {
					for(Lapin lapin : front.enfants) {
						q.add(lapin);
					}
				}
			}
			
			return set_a_retourner;
		}
		
	}
	
	public static boolean estAncestre(Lapin enfant, Lapin ancestre) {
		// Solution with Iterative Approach
		
		if(enfant == null || ancestre == null) return false;
		
		while(enfant.pere != null) {
			if(enfant.pere == ancestre) return true;
			enfant = enfant.pere;
		}
		
		return false;
		
		/*  Solution with Recursive Approach
		
		if(enfant == null || ancestre == null) return false;
		
		if(enfant.pere == ancestre) return true;
		
		return estAncestre(enfant.pere, ancestre);
		*/
	}
	
	public static boolean estCyclique(Lapin l1, Lapin l2) {
		
		if(l1 == null || l2 == null) return false;
		
		Lapin oneStepIterator = l1;
		Lapin twoStepIterator = l1;
		// Puisqu'on contrôle l'existence d'un cycle juste en tenant compte de "l1"
		// Il faut aussi contrôler si "l2" est aussi sur ce cercle cyclique
		boolean is_l2_on_the_cyclic_path = false;
				
		// S'il y a un cercle, les deux iterators vont finalement se croiser 
		// Sinon on va quitter le boucle while, parce que l'un des valeurs ci-dessous serait égale à null
		while(twoStepIterator != null &&  twoStepIterator.pere != null) {
			// Il faut checker si "l2" est sur le cercle cyclique
			if(oneStepIterator == l2 || twoStepIterator == l2 ) is_l2_on_the_cyclic_path = true;
			
			oneStepIterator = oneStepIterator.pere;
			twoStepIterator = twoStepIterator.pere.pere;
			
			if(oneStepIterator == twoStepIterator) {
				// Ce contrôle est très important
				// Si on combine ces deux "if" comme if(oneStepIterator == twoStepIterator && is_l2_on_the_cyclic_path) 
				// Cela générerait un boucle infini dans le cas où les iterators se croisent mais "l2" n'est pas sur le cercle cyclique
				if(is_l2_on_the_cyclic_path) return true;
				// Si les deux iterators se croisent mais "l2" n'est pas sur le chemin
				// On retourne simplement false
				return false;
			}
		}
		
		return false;
	}
	
	public static List<Lapin> estAncestre(List<Lapin> enfants, Lapin ancestre){
		return ancestre.getAllLevelChildren().stream().filter(element -> enfants.contains(element)).collect(Collectors.toList());
	}
	
	/*
	  
	 					Lap1 					Lap9 ( Ce Lapin n'a aucune relation )
	 			Lap2		Lap3		Lap4
	 				    Lap5    Lap6           Lap7
	 								Lap8
	 												
	 	Lap1 ==>	 père de 1,3,4
	 	Lap2 ==>	 aucun enfant
	 	Lap3 ==>	 père de 5,6
	 	Lap4 ==>	 père de 7
	 	Lap5 ==>	 aucun enfant
	 	Lap6 ==>	 père de 7
	 	Lap7 ==>	 père de 8
	 	Lap8 ==>	 aucun enfant
	 	Lap9 ==> 	 n'a aucune relation
	 */
	/*
	
	Si j'ai bien compris lors de l'entretien ;
	Si je passe une Liste de {Lap5, Lap6, Lap7, Lap2, Lap8, Lap9} avec Lap1 comme ancestre
	Cela doit retourner {Lap5, Lap6, Lap7, Lap2, Lap8}
	Si je passe une Liste de {Lap5, Lap7, Lap2, Lap9} avec Lap3 comme ancestre
	Cela doit retourner {Lap5}, parce qu'il n'y a pas Lap6, l'autre enfant de Lap3, dans la liste
	
	 */
	
	
}

