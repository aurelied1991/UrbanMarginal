package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import modele.Mur;

/**
 * Classe pour faire un test unitaire sur la collision entre deux murs
 */
class MurTest {

	/**
	 * Mur 1
	 */
	Mur mur1 = new Mur();
	/**
	 * Mur 2
	 */
	Mur mur2 = new Mur();
	
	/**
	 * Test avec mur2 qui touche à gauche de mur1
	 */
	@Test
	void testToucheObjetGauche() {
		mur1.setPosX(50);
		mur1.setPosY(50);
		mur2.setPosX(40);
		mur2.setPosY(50);
		assertTrue(mur1.toucheObjet(mur2));
	}
	
	/**
	 * Test avec mur2 qui touche à droite de mur1
	 */
	@Test
	void testToucheObjetDroite() {
		mur1.setPosX(50);
		mur1.setPosY(50);
		mur2.setPosX(60);
		mur2.setPosY(50);
		assertTrue(mur1.toucheObjet(mur2));
	}

	/**
	 * Test avec mur2 qui touche au dessus de mur1
	 */
	@Test
	void testToucheObjetHaut() {
		mur1.setPosX(50);
		mur1.setPosY(50);
		mur2.setPosX(50);
		mur2.setPosY(40);
		assertTrue(mur1.toucheObjet(mur2));
	}

	/**
	 * Test avec mur2 qui touche en dessous de mur1
	 */
	@Test
	void testToucheObjetBas() {
		mur1.setPosX(50);
		mur1.setPosY(50);
		mur2.setPosX(50);
		mur2.setPosY(60);
		assertTrue(mur1.toucheObjet(mur2));
	}
	
	/**
	 * Test avec mur2 qui ne touche pas à gauche de mur1
	 */
	@Test
	void testNonToucheObjetGauche() {
		mur1.setPosX(50);
		mur1.setPosY(50);
		mur2.setPosX(10);
		mur2.setPosY(50);
		assertFalse(mur1.toucheObjet(mur2));
	}
	
	/**
	 * Test avec mur2 qui ne touche pas à droite de mur1
	 */
	@Test
	void testNonToucheObjetDroite() {
		mur1.setPosX(50);
		mur1.setPosY(50);
		mur2.setPosX(90);
		mur2.setPosY(50);
		assertFalse(mur1.toucheObjet(mur2));
	}

	/**
	 * Test avec mur2 qui ne touche pas au dessus de mur1
	 */
	@Test
	void testNonToucheObjetHaut() {
		mur1.setPosX(50);
		mur1.setPosY(50);
		mur2.setPosX(50);
		mur2.setPosY(10);
		assertFalse(mur1.toucheObjet(mur2));
	}

	/**
	 * Test avec mur2 qui ne touche pas en dessous de mur1
	 */
	@Test
	void testNonToucheObjetBas() {
		mur1.setPosX(50);
		mur1.setPosY(50);
		mur2.setPosX(50);
		mur2.setPosY(90);
		assertFalse(mur1.toucheObjet(mur2));
	}

}
