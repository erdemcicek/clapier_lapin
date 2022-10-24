package clapier_lapin;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class ClapierTest {
	
	@Test
	public void estAncestre_whenThereIsDirectParentChildRelationship_returnsTrue() {
		Clapier.Lapin enfant = new Clapier.Lapin();
		Clapier.Lapin parent = new Clapier.Lapin();
		enfant.pere = parent;
		assertTrue(Clapier.estAncestre(enfant, parent));
	}
	
	@Test
	public void estAncestre_whenAddChildMethodIsUsed_receiveParentOfChildIsAssigned() {
		Clapier.Lapin enfant = new Clapier.Lapin();
		Clapier.Lapin parent = new Clapier.Lapin();
		parent.addChild(enfant);
		assertTrue(Clapier.estAncestre(enfant, parent));
	}
	
	@Test
	public void estAncestre_whenThereIsNoParentChildRelationship_returnsFalse() {
		Clapier.Lapin enfant = new Clapier.Lapin();
		Clapier.Lapin other = new Clapier.Lapin();
		assertFalse(Clapier.estAncestre(enfant, other));
	}
	
	@Test
	public void estAncestre_whenOneOfTheLapinsIsNull_returnsFalse() {
		Clapier.Lapin enfant = new Clapier.Lapin();
		Clapier.Lapin nullLapin = null;
		assertFalse(Clapier.estAncestre(enfant, nullLapin));
		assertFalse(Clapier.estAncestre(nullLapin, enfant));
	}
	
	
	@Test
	public void estAncestre_whenThereIsTwoDegreeParentChildRelationship_returnsTrue() {
		Clapier.Lapin enfant = new Clapier.Lapin();
		Clapier.Lapin parent = new Clapier.Lapin();
		enfant.pere = parent;
		Clapier.Lapin grandParent = new Clapier.Lapin();
		parent.pere = grandParent;
		assertTrue(Clapier.estAncestre(enfant, grandParent));
	}
	
	@Test
	public void estAncestre_whenThereIsMoreThanTwoDegreeParentChildRelationship_returnsTrue() {
		Clapier.Lapin enfant = new Clapier.Lapin();
		Clapier.Lapin parent = new Clapier.Lapin();
		enfant.pere = parent;
		
		Clapier.Lapin grandParent = new Clapier.Lapin();
		parent.pere = grandParent;
		
		Clapier.Lapin grandGrandParent = new Clapier.Lapin();
		grandParent.pere = grandGrandParent;
		
		assertTrue(Clapier.estAncestre(enfant, grandGrandParent));
	}
	
	@Test
	public void estCyclique_whenThereIsNoCyclicRelationship_returnsFalse() {
		Clapier.Lapin enfant = new Clapier.Lapin();
		Clapier.Lapin parent = new Clapier.Lapin();
		enfant.pere = parent;
		
		Clapier.Lapin grandParent = new Clapier.Lapin();
		parent.pere = grandParent;
		
		Clapier.Lapin grandGrandParent = new Clapier.Lapin();
		grandParent.pere = grandGrandParent;
		
		assertFalse(Clapier.estCyclique(enfant, grandGrandParent));
	}
	
	@Test
	public void estCyclique_whenThereIsCyclicRelationship_returnsTrue() {
		
		// Non-Cyclic Part
		Clapier.Lapin enfant = new Clapier.Lapin();
		Clapier.Lapin parent = new Clapier.Lapin();
		enfant.pere = parent;
		
		Clapier.Lapin grandParent = new Clapier.Lapin();
		parent.pere = grandParent;
		
		Clapier.Lapin grandGrandParent = new Clapier.Lapin();
		grandParent.pere = grandGrandParent;
		
		// With this assignment, it becomes Cyclic
		grandGrandParent.pere = enfant;
		
		assertTrue(Clapier.estCyclique(enfant, grandGrandParent));
	}
	
	@Test
	public void estCyclique_whenThereIsCycleButNotBetweenTheParametersBeingPassed_returnsFalse() {
		// Non-Cyclic Part
		Clapier.Lapin enfant = new Clapier.Lapin();
		Clapier.Lapin parent = new Clapier.Lapin();
		enfant.pere = parent;
		
		Clapier.Lapin grandParent = new Clapier.Lapin();
		parent.pere = grandParent;
		
		Clapier.Lapin grandGrandParent = new Clapier.Lapin();
		grandParent.pere = grandGrandParent;
		
		// With this assignment, it becomes Cyclic
		grandGrandParent.pere = enfant;
		
		// Here a brand new Lapin that does not involve in the cycle
		Clapier.Lapin other = new Clapier.Lapin();

		assertFalse(Clapier.estCyclique(other, grandGrandParent));
		assertFalse(Clapier.estCyclique(grandGrandParent, other));

	}
	
	@Test
	public void estCyclique_whenOneOfTheLapinsIsNull_returnsFalse() {
		Clapier.Lapin enfant = new Clapier.Lapin();
		Clapier.Lapin nullLapin = null;
		assertFalse(Clapier.estCyclique(enfant, nullLapin));
		assertFalse(Clapier.estCyclique(nullLapin, enfant));
	}
	
	// One_Exactly_Match
	@Test
	public void estAncestre_whenThereIsOneDegreeRelationshipAndFamilyTreeAndParameterListAreExactlyMatched_returnsSetTheSameAsSetOfChildren() {
		
		Clapier.Lapin lap1 = new Clapier.Lapin();
		Clapier.Lapin lap2 = new Clapier.Lapin();
		Clapier.Lapin lap3 = new Clapier.Lapin();
		// Lap1 est le pere de Lap2 et Lap3
		lap1.addChild(lap2);
		lap1.addChild(lap3);
		
		assertTrue(lap1.enfants.containsAll(Clapier.estAncestre(List.of(lap2, lap3), lap1)));

	}
	
	// One_Family_Tree_Has_More
	@Test
	public void estAncestre_whenThereIsOneDegreeRelationshipAndFamilyTreeHasMoreElementThanParameterList_returnsSetAsSubsetOfSetOfChildren() {
		
		Clapier.Lapin lap1 = new Clapier.Lapin();
		Clapier.Lapin lap2 = new Clapier.Lapin();
		Clapier.Lapin lap3 = new Clapier.Lapin();
		// Lap1 est le pere de Lap2 et Lap3
		lap1.addChild(lap2);
		lap1.addChild(lap3);
		
		//Clapier.Lapin lap4 = new Clapier.Lapin();

		assertTrue(lap1.enfants.containsAll(Clapier.estAncestre(List.of(lap2), lap1)));
	}
	
	// One_List_Extra
	@Test
	public void estAncestre_whenThereIsOneDegreeRelationshipAndFamilyTreeHasMoreElementThanParameterListAndListHasExtraElement_returnsSetAsSubsetOfSetOfChildren() {
		
		Clapier.Lapin lap1 = new Clapier.Lapin();
		Clapier.Lapin lap2 = new Clapier.Lapin();
		Clapier.Lapin lap3 = new Clapier.Lapin();
		// Lap1 est le pere de Lap2 et Lap3
		lap1.addChild(lap2);
		lap1.addChild(lap3);
		
		Clapier.Lapin lap4 = new Clapier.Lapin();
		Clapier.Lapin lap5 = new Clapier.Lapin();


		assertTrue(lap1.enfants.containsAll(Clapier.estAncestre(List.of(lap2, lap4, lap5), lap1)));
	}
	
	// Two_Exactly_Match
	@Test
	public void estAncestre_whenThereIsTwoDegreeRelationshipAndFamilyTreeAndParameterListAreExactlyMatched_returnsSetTheSameAsSetOfChildren() {
		
		Clapier.Lapin lap1 = new Clapier.Lapin();
		Clapier.Lapin lap2 = new Clapier.Lapin();
		Clapier.Lapin lap3 = new Clapier.Lapin();
		// Lap1 est le pere de Lap2 et Lap3
		lap1.addChild(lap2);
		lap1.addChild(lap3);
		
		Clapier.Lapin lap4 = new Clapier.Lapin();
		Clapier.Lapin lap5 = new Clapier.Lapin();
		// Lap 2 est le pere de Lap4
		lap2.addChild(lap4);
		// Lap 3 est le pere de Lap5
		lap3.addChild(lap5);
		
		Set<Clapier.Lapin> setAttendu = lap1.getAllLevelChildren();

		assertTrue(setAttendu.containsAll(Clapier.estAncestre(List.of(lap2, lap3, lap4, lap5), lap1)));

	}
	
	// Two_Family_Tree_Has_More
	@Test
	public void estAncestre_whenThereIsTwoDegreeRelationshipAndFamilyTreeHasMoreElementThanParameterList_returnsSetAsSubsetOfSetOfChildren() {
		Clapier.Lapin lap1 = new Clapier.Lapin();
		Clapier.Lapin lap2 = new Clapier.Lapin();
		Clapier.Lapin lap3 = new Clapier.Lapin();
		// Lap1 est le pere de Lap2 et Lap3
		lap1.addChild(lap2);
		lap1.addChild(lap3);
		
		Clapier.Lapin lap4 = new Clapier.Lapin();
		Clapier.Lapin lap5 = new Clapier.Lapin();
		// Lap 2 est le pere de Lap4
		lap2.addChild(lap4);
		// Lap 3 est le pere de Lap5
		lap3.addChild(lap5);
		
		Set<Clapier.Lapin> setAttendu = lap1.getAllLevelChildren();

		assertTrue(setAttendu.containsAll(Clapier.estAncestre(List.of(lap2, lap5), lap1)));
	}
	
	// Two_List_Extra
	@Test
	public void estAncestre_whenThereIsTwoDegreeRelationshipAndFamilyTreeHasMoreElementThanParameterListAndListHasExtraElement_returnsSetAsSubsetOfSetOfChildren() {
		
		Clapier.Lapin lap1 = new Clapier.Lapin();
		Clapier.Lapin lap2 = new Clapier.Lapin();
		Clapier.Lapin lap3 = new Clapier.Lapin();
		// Lap1 est le pere de Lap2 et Lap3
		lap1.addChild(lap2);
		lap1.addChild(lap3);
		
		Clapier.Lapin lap4 = new Clapier.Lapin();
		Clapier.Lapin lap5 = new Clapier.Lapin();
		// Lap 2 est le pere de Lap4
		lap2.addChild(lap4);
		// Lap 3 est le pere de Lap5
		lap3.addChild(lap5);
		
		Clapier.Lapin lap6 = new Clapier.Lapin();
		Clapier.Lapin lap7 = new Clapier.Lapin();

		Set<Clapier.Lapin> setAttendu = lap1.getAllLevelChildren();
		
		assertTrue(setAttendu.containsAll(Clapier.estAncestre(List.of(lap2, lap3, lap4, lap6, lap7), lap1)));

	}

}
