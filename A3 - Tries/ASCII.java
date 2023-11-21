import java.io.*;
import java.util.*;

public class ASCII {
	public static void main(String [] args) {
/*
		String line = "the";
		int[] array = new int[line.length()];
		for (int i = 0; i < line.length(); i++) {
			int ASCII = line.charAt(i);
			array[i] = ASCII;
		}
		
		for (int i = 0; i < array.length; i++) {
			System.out.println(array[i]);
		}
		
		System.out.println();
*/

/*
		StringBuilder str = new StringBuilder();

		for (int i = 97; i < 123; i++) {
			char index = (char) i;
			String s = String.format("%c", index);
			str.append("'");
			str.append(s);
			str.append("'");
			str.append(", ");
		}

		System.out.print("All words: " + str);
		System.out.println();
*/
/*
		String s = "umbrella igloo";
		String[] chars = s.split(" ");
		System.out.println(chars[1]);
*/
		String str = "te";
		String s = "e";
		str = str.substring(1);
		s = s.substring(1);
		System.out.println(str);
		System.out.println();
		System.out.println(s);
/*
		for (String character : chars) {
			for (int i = 0; i < character.length(); i++) {
				System.out.print(character.charAt(i));
			}
			System.out.println();
		}
*/		
/*
		for (String word : words) {
			System.out.print(word);
			System.out.print(", ");
		}
*/
//		StringBuilder str = new StringBuilder();
/*
		str.append("'GRG'");
		str.append(", ");
		str.append("'MOMOM'");
		str.append(", ");
		str.append("'THETE'");
		System.out.print("All words: ");
		System.out.print(str);
		
*/
/*
		String s = "thope";
		for (int i = 0; i < s.length(); i++) {
			s = s.substring(1);
			System.out.println(s);
		}
*/
	}
}