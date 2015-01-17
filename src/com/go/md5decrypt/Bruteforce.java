package com.go.md5decrypt;

import java.util.Arrays;

public class Bruteforce {

	


	private char[] cs;	// Character Set
	private char[] cg;	// Current Guess

	public Bruteforce(char[] characterSet, int guessLength) {
		cs = characterSet;
		cg = new char[guessLength];
		Arrays.fill(cg, cs[0]);
	}

	public void increment() {
		int index = cg.length - 1;
		while(index >= 0) {
			if (cg[index] == cs[cs.length-1]) {
				if (index == 0) {
					cg = new char[cg.length+1];
					Arrays.fill(cg, cs[0]);
					break;
				} else {
					cg[index] = cs[0];
					index--;
				}
			} else {
				cg[index] = cs[Arrays.binarySearch(cs, cg[index]) + 1];
				break;
			}
		}
	}

	public String toString() {
		return String.valueOf(cg);
	}
}