package de.unidue.iem.tdr.nis.client;

//Implementiert von Simon Janzon

/**
 * Diese Klasse ermöglicht das Abrufen von Aufgaben vom Server und die
 * Implementierung der dazugehörigen Lösungen.
 * <p>
 * Nähere Informationen zu den anderen Klassen und den einzelnen Aufgabentypen
 * entnehmen Sie bitte der entsprechenden Dokumentation im TMT und den Javadocs
 * zu den anderen Klassen.
 * 
 * @see Connection
 * @see TaskObject
 * 
 */
import java.util.Random;

public class Client implements TaskDefs {
	private Connection con;
	private TaskObject currentTask;

	/* hier bitte die Matrikelnummer eintragen */
	private final int matrikelnr;

	/* hier bitte das TMT-Passwort eintragen */
	private final String password;

	/* Aufgaben, die bearbeitet werden sollen */
	private final int[] tasks = { TASK_CLEARTEXT, TASK_XOR, TASK_MODULO, TASK_FACTORIZATION, TASK_VIGENERE,
			TASK_DES_KEYSCHEDULE, TASK_DES_RBLOCK, TASK_DES_FEISTEL, TASK_DES_ROUND, TASK_AES_GF8,
			TASK_AES_KEYEXPANSION, TASK_AES_MIXCOLUMNS, TASK_AES_TRANSFORMATION, TASK_AES_3ROUNDS, TASK_RC4_LOOP,
			TASK_RC4_KEYSCHEDULE, TASK_RC4_ENCRYPTION, TASK_DIFFIEHELLMAN, TASK_RSA_ENCRYPTION, TASK_RSA_DECRYPTION,
			TASK_ELGAMAL_ENCRYPTION, TASK_ELGAMAL_DECRYPTION };

	/**
	 * Klassenkonstruktor. Baut die Verbindung zum Server auf.
	 */
	public Client() {
		con = new Connection();
		if (con.auth(matrikelnr, password))
			System.out.println("Anmeldung erfolgreich.");
		else
			System.out.println("Anmeldung nicht erfolgreich.");
	}

	/**
	 * Besteht die Verbindung zum Server?
	 * 
	 * @return true, falls Verbindung bereit, andernfalls false
	 */
	public boolean isReady() {
		return con.isReady();
	}

	/**
	 * Beendet die Verbindungs zum Server.
	 */
	public void close() {
		con.close();
	}

	// Implementierung

	// Umwandlungen
	String[] alphabet = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
			"t", "u", "v", "w", "x", "y", "z" };

	private int modulo(int a, int b) {
		while (a < 0) {
			a += b;
		}
		while ((a - b) >= 0) {
			a -= b;
		}
		return a;
	}

	private int moduloPow(int a, int b, int m) {
		int solution = 1;
		for (int i = 0; i < b; i++) {
			solution *= a;
			solution = this.modulo(solution, m);
		}
		return solution;
	}

	private int getPosition(char n) {
		String[][] tmpAlphabet = {
				{ "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
						"u", "v", "w", "x", "y", "z" },
				{ "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
						"U", "V", "W", "X", "Y", "Z" } };

		for (int i = 0; i < 26; i++) {
			if (tmpAlphabet[0][i].charAt(0) == n || tmpAlphabet[1][i].charAt(0) == n) {
				return i;
			}
		}
		return -1;
	}

	private int getASCII(char n) {
		String[][] alphabet = {
				{ "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
						"u", "v", "w", "x", "y", "z" },
				{ "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
						"U", "V", "W", "X", "Y", "Z" } };

		for (int i = 0; i < 26; i++) {
			if (alphabet[0][i].charAt(0) == n)
				return 97 + i;
			if (alphabet[1][i].charAt(0) == n)
				return 65 + i;

		}
		return -1;
	}

	private char getCharfromASCII(int n) {
		char[] alphabet = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
				's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
				'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

		for (int i = 0; i < alphabet.length; i++) {
			if (this.getASCII(alphabet[i]) == n) {
				return alphabet[i];
			}
		}
		return '*';
	}

	private int stringToInt(String string) {
		int tmp = 1;
		int solution = 0;
		for (int i = string.length() - 1; i >= 0; i--) {
			switch (string.charAt(i)) {
			case '0':
				solution += 0 * 10 * tmp;
				break;
			case '1':
				solution += tmp == 1 ? 1 : 1 * tmp;
				break;
			case '2':
				solution += tmp == 1 ? 2 : 2 * tmp;
				break;
			case '3':
				solution += tmp == 1 ? 3 : 3 * tmp;
				break;
			case '4':
				solution += tmp == 1 ? 4 : 4 * tmp;
				break;
			case '5':
				solution += tmp == 1 ? 5 : 5 * tmp;
				break;
			case '6':
				solution += tmp == 1 ? 6 : 6 * tmp;
				break;
			case '7':
				solution += tmp == 1 ? 7 : 7 * tmp;
				break;
			case '8':
				solution += tmp == 1 ? 8 : 8 * tmp;
				break;
			case '9':
				solution += tmp == 1 ? 9 : 9 * tmp;
				break;
			default:
				solution = -1;
			}
			tmp *= 10;
		}
		return solution;
	}

	private String hexToBin(String hex) {
		String solution = "";
		for (int i = 0; i < hex.length(); i++) {
			switch (hex.charAt(i)) {
			case '0':
				solution += "0000";
				break;
			case '1':
				solution += "0001";
				break;
			case '2':
				solution += "0010";
				break;
			case '3':
				solution += "0011";
				break;
			case '4':
				solution += "0100";
				break;
			case '5':
				solution += "0101";
				break;
			case '6':
				solution += "0110";
				break;
			case '7':
				solution += "0111";
				break;
			case '8':
				solution += "1000";
				break;
			case '9':
				solution += "1001";
				break;
			case 'a':
				solution += "1010";
				break;
			case 'b':
				solution += "1011";
				break;
			case 'c':
				solution += "1100";
				break;
			case 'd':
				solution += "1101";
				break;
			case 'e':
				solution += "1110";
				break;
			case 'f':
				solution += "1111";
				break;
			default:
			}
		}
		return solution;
	}

	private String binToHex(String bin) {
		while (this.modulo(bin.length(), 4) != 0) {
			bin = "0" + bin;
		}

		String solution = "";
		String tmp = "";
		for (int i = 0; i < bin.length() / 4; i++) {
			tmp = bin.substring(i * 4, i * 4 + 4);
			switch (tmp) {
			case "0000":
				solution += "0";
				break;
			case "0001":
				solution += "1";
				break;
			case "0010":
				solution += "2";
				break;
			case "0011":
				solution += "3";
				break;
			case "0100":
				solution += "4";
				break;
			case "0101":
				solution += "5";
				break;
			case "0110":
				solution += "6";
				break;
			case "0111":
				solution += "7";
				break;
			case "1000":
				solution += "8";
				break;
			case "1001":
				solution += "9";
				break;
			case "1010":
				solution += "a";
				break;
			case "1011":
				solution += "b";
				break;
			case "1100":
				solution += "c";
				break;
			case "1101":
				solution += "d";
				break;
			case "1110":
				solution += "e";
				break;
			case "1111":
				solution += "f";
				break;
			default:
			}
		}
		return solution;
	}

	private String decimalToByte(int n) {
		String solution = "";

		solution += n / 128 == 1 ? 1 : 0;
		n = this.modulo(n, 128);

		solution += n / 64 == 1 ? 1 : 0;
		n = this.modulo(n, 64);

		solution += n / 32 == 1 ? 1 : 0;
		n = this.modulo(n, 32);

		solution += n / 16 == 1 ? 1 : 0;
		n = this.modulo(n, 16);

		solution += n / 8 == 1 ? 1 : 0;
		n = this.modulo(n, 8);

		solution += n / 4 == 1 ? 1 : 0;
		n = this.modulo(n, 4);

		solution += n / 2 == 1 ? 1 : 0;
		n = this.modulo(n, 2);

		solution += n == 1 ? 1 : 0;
		return solution;
	}

	private int byteToDec(String string) {
		while (string.length() < 8) {
			string = "0" + string;
		}

		int solution = 0;
		for (int i = 0; i < 8; i++) {
			switch (i) {
			case 0:
				solution += string.charAt(i) == '1' ? 128 : 0;
				break;
			case 1:
				solution += string.charAt(i) == '1' ? 64 : 0;
				break;
			case 2:
				solution += string.charAt(i) == '1' ? 32 : 0;
				break;
			case 3:
				solution += string.charAt(i) == '1' ? 16 : 0;
				break;
			case 4:
				solution += string.charAt(i) == '1' ? 8 : 0;
				break;
			case 5:
				solution += string.charAt(i) == '1' ? 4 : 0;
				break;
			case 6:
				solution += string.charAt(i) == '1' ? 2 : 0;
				break;
			case 7:
				solution += string.charAt(i) == '1' ? 1 : 0;
				break;
			}
		}
		return solution;
	}

	private int FBToInt(String string) {
		int solution = 0;
		for (int i = 0; i < 4; i++) {
			switch (i) {
			case 0:
				solution += string.charAt(i) == '1' ? 8 : 0;
				break;
			case 1:
				solution += string.charAt(i) == '1' ? 4 : 0;
				break;
			case 2:
				solution += string.charAt(i) == '1' ? 2 : 0;
				break;
			case 3:
				solution += string.charAt(i) == '1' ? 1 : 0;
				break;
			}
		}
		return solution;
	}

	private String decToFB(int n) {
		String solution = "";
		switch (n) {
		case 0:
			solution += "0000";
			break;
		case 1:
			solution += "0001";
			break;
		case 2:
			solution += "0010";
			break;
		case 3:
			solution += "0011";
			break;
		case 4:
			solution += "0100";
			break;
		case 5:
			solution += "0101";
			break;
		case 6:
			solution += "0110";
			break;
		case 7:
			solution += "0111";
			break;
		case 8:
			solution += "1000";
			break;
		case 9:
			solution += "1001";
			break;
		case 10:
			solution += "1010";
			break;
		case 11:
			solution += "1011";
			break;
		case 12:
			solution += "1100";
			break;
		case 13:
			solution += "1101";
			break;
		case 14:
			solution += "1110";
			break;
		case 15:
			solution += "1111";
			break;
		default:
		}
		return solution;
	}

	// Nebenrechnungen
	private boolean isPrime(int n) {
		for (int i = 2; i < n / 2; i++) {
			if (this.modulo(n, i) == 0) {
				return false;
			}
		}
		return true;
	}

	private char[] shiftKey(char[] key, int number) {
		if (number == 1) {
			char carry = key[0];
			for (int i = 0; i < key.length - 1; i++)
				key[i] = key[i + 1];
			key[key.length - 1] = carry;
		} else
			for (int i = 0; i < number; i++)
				key = this.shiftKey(key, 1);
		return key;
	}

	private String getRoundKey(String key, int round) {
		String keyTotal = "";
		int shiftsForRound = 0;
		int[] shifts = { 1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1 };
		int[] codeCblock = { 57, 49, 41, 33, 25, 17, 9, 1, 58, 50, 42, 34, 26, 18, 10, 2, 59, 51, 43, 35, 27, 19, 11, 3,
				60, 52, 44, 36 };
		int[] codeDblock = { 63, 55, 47, 39, 31, 23, 15, 7, 62, 54, 46, 38, 30, 22, 14, 6, 61, 53, 45, 37, 29, 21, 13,
				5, 28, 20, 12, 4 };
		int[] permutatePC2 = { 14, 17, 11, 24, 1, 5, 3, 28, 15, 6, 21, 10, 23, 19, 12, 4, 26, 8, 16, 7, 27, 20, 13, 2,
				41, 52, 31, 37, 47, 55, 30, 40, 51, 45, 33, 48, 44, 49, 39, 56, 34, 53, 46, 42, 50, 36, 29, 32 };
		char[] Cblock = new char[28];
		char[] Dblock = new char[28];
		char[] shiftedTotal = new char[56];
		for (int i = 0; i < codeCblock.length; i++) {
			Cblock[i] = key.charAt(codeCblock[i] - 1);
			Dblock[i] = key.charAt(codeDblock[i] - 1);
		}
		for (int i = 0; i < shifts.length && i < round; i++)
			shiftsForRound += shifts[i];
		char[] shiftedKeyC = this.shiftKey(Cblock, shiftsForRound);
		char[] shiftedKeyD = this.shiftKey(Dblock, shiftsForRound);
		for (int i = 0; i < shiftedKeyC.length; i++) {
			shiftedTotal[i] = shiftedKeyC[i];
			shiftedTotal[i + Cblock.length] = shiftedKeyD[i];
		}
		for (int i = 0; i < permutatePC2.length; i++)
			keyTotal += shiftedTotal[permutatePC2[i] - 1];
		return keyTotal;
	}

	private char[] sboxes(char[] block) {
		int[][] sBox1 = { { 14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7 },
				{ 0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8 },
				{ 4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0 },
				{ 15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13 } };

		int[][] sBox2 = { { 15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10 },
				{ 3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5 },
				{ 0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15 },
				{ 13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9 } };

		int[][] sBox3 = { { 10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8 },
				{ 13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1 },
				{ 13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7 },
				{ 1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12 } };

		int[][] sBox4 = { { 7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15 },
				{ 13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9 },
				{ 10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4 },
				{ 3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14 } };

		int[][] sBox5 = { { 2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9 },
				{ 14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6 },
				{ 4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14 },
				{ 11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3 } };

		int[][] sBox6 = { { 12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11 },
				{ 10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8 },
				{ 9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6 },
				{ 4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13 } };

		int[][] sBox7 = { { 4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1 },
				{ 13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6 },
				{ 1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2 },
				{ 6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12 } };

		int[][] sBox8 = { { 13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7 },
				{ 1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2 },
				{ 7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8 },
				{ 2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11 } };

		String out = "";
		char[] result = new char[32];
		char[][] blockIn = new char[8][6];

		int counter = 0;
		for (int i = 0; i < blockIn.length; i++)
			for (int j = 0; j < blockIn[0].length; j++) {
				blockIn[i][j] = block[counter];
				counter++;
			}

		out += this.decToFB(sBox1[this.FBToInt("00" + blockIn[0][0] + blockIn[0][5])][this
				.FBToInt("" + blockIn[0][1] + blockIn[0][2] + blockIn[0][3] + blockIn[0][4])]);
		out += this.decToFB(sBox2[this.FBToInt("00" + blockIn[1][0] + blockIn[1][5])][this
				.FBToInt("" + blockIn[1][1] + blockIn[1][2] + blockIn[1][3] + blockIn[1][4])]);
		out += this.decToFB(sBox3[this.FBToInt("00" + blockIn[2][0] + blockIn[2][5])][this
				.FBToInt("" + blockIn[2][1] + blockIn[2][2] + blockIn[2][3] + blockIn[2][4])]);
		out += this.decToFB(sBox4[this.FBToInt("00" + blockIn[3][0] + blockIn[3][5])][this
				.FBToInt("" + blockIn[3][1] + blockIn[3][2] + blockIn[3][3] + blockIn[3][4])]);
		out += this.decToFB(sBox5[this.FBToInt("00" + blockIn[4][0] + blockIn[4][5])][this
				.FBToInt("" + blockIn[4][1] + blockIn[4][2] + blockIn[4][3] + blockIn[4][4])]);
		out += this.decToFB(sBox6[this.FBToInt("00" + blockIn[5][0] + blockIn[5][5])][this
				.FBToInt("" + blockIn[5][1] + blockIn[5][2] + blockIn[5][3] + blockIn[5][4])]);
		out += this.decToFB(sBox7[this.FBToInt("00" + blockIn[6][0] + blockIn[6][5])][this
				.FBToInt("" + blockIn[6][1] + blockIn[6][2] + blockIn[6][3] + blockIn[6][4])]);
		out += this.decToFB(sBox8[this.FBToInt("00" + blockIn[7][0] + blockIn[7][5])][this
				.FBToInt("" + blockIn[7][1] + blockIn[7][2] + blockIn[7][3] + blockIn[7][4])]);

		for (int i = 0; i < 32; i++)
			result[i] = out.charAt(i);
		return result;
	}

	private char[] intitialPermutation(String string) {
		int[] IP = { 58, 50, 42, 34, 26, 18, 10, 2, 60, 52, 44, 36, 28, 20, 12, 4, 62, 54, 46, 38, 30, 22, 14, 6, 64,
				56, 48, 40, 32, 24, 16, 8, 57, 49, 41, 33, 25, 17, 9, 1, 59, 51, 43, 35, 27, 19, 11, 3, 61, 53, 45, 37,
				29, 21, 13, 5, 63, 55, 47, 39, 31, 23, 15, 7 };
		char[] perm = new char[IP.length];

		for (int i = 0; i < IP.length; i++) {
			IP[i] = string.charAt(IP[i] - 1);
		}
		return perm;
	}

	private String multi(String a, String b) {
		String[] solutions = new String[b.length()];
		for (int i = 0; i < solutions.length; i++)
			if (b.charAt(i) == '0')
				solutions[i] = "0";
			else {
				solutions[i] = a;
				for (int j = solutions.length - 1 - i; j > 0; j--)
					solutions[i] += "0";
			}
		for (int i = 1; i < solutions.length; i++)
			solutions[0] = this.taskXOR(solutions[0], solutions[i]);
		return solutions[0];
	}

	private String mx(String string) {
		String mx = "100011011";
		while (string.length() > 0 && string.charAt(0) == '0') {
			string = string.substring(1);
		}
		if (string.length() <= 8) {
			return string;
		} else {
			String solution = "1";
			for (int i = 1; i < string.length() - 8; i++)
				solution += "0";
			solution = this.multi(solution, mx);
			solution = this.taskXOR(solution, string);
			return this.mx(solution);
		}
	}

	private String sBox(String a) {
		String[][] sBox = {
				{ "63", "7c", "77", "7b", "f2", "6b", "6f", "c5", "30", "01", "67", "2b", "fe", "d7", "ab", "76" },
				{ "ca", "82", "c9", "7d", "fa", "59", "47", "f0", "ad", "d4", "a2", "af", "9c", "a4", "72", "c0" },
				{ "b7", "fd", "93", "26", "36", "3f", "f7", "cc", "34", "a5", "e5", "f1", "71", "d8", "31", "15" },
				{ "04", "c7", "23", "c3", "18", "96", "05", "9a", "07", "12", "80", "e2", "eb", "27", "b2", "75" },
				{ "09", "83", "2c", "1a", "1b", "6e", "5a", "a0", "52", "3b", "d6", "b3", "29", "e3", "2f", "84" },
				{ "53", "d1", "00", "ed", "20", "fc", "b1", "5b", "6a", "cb", "be", "39", "4a", "4c", "58", "cf" },
				{ "d0", "ef", "aa", "fb", "43", "4d", "33", "85", "45", "f9", "02", "7f", "50", "3c", "9f", "a8" },
				{ "51", "a3", "40", "8f", "92", "9d", "38", "f5", "bc", "b6", "da", "21", "10", "ff", "f3", "d2" },
				{ "cd", "0c", "13", "ec", "5f", "97", "44", "17", "c4", "a7", "7e", "3d", "64", "5d", "19", "73" },
				{ "60", "81", "4f", "dc", "22", "2a", "90", "88", "46", "ee", "b8", "14", "de", "5e", "0b", "db" },
				{ "e0", "32", "3a", "0a", "49", "06", "24", "5c", "c2", "d3", "ac", "62", "91", "95", "e4", "79" },
				{ "e7", "c8", "37", "6d", "8d", "d5", "4e", "a9", "6c", "56", "f4", "ea", "65", "7a", "ae", "08" },
				{ "ba", "78", "25", "2e", "1c", "a6", "b4", "c6", "e8", "dd", "74", "1f", "4b", "bd", "8b", "8a" },
				{ "70", "3e", "b5", "66", "48", "03", "f6", "0e", "61", "35", "57", "b9", "86", "c1", "1d", "9e" },
				{ "e1", "f8", "98", "11", "69", "d9", "8e", "94", "9b", "1e", "87", "e9", "ce", "55", "28", "df" },
				{ "8c", "a1", "89", "0d", "bf", "e6", "42", "68", "41", "99", "2d", "0f", "b0", "54", "bb", "16" }, };

		return sBox[this.FBToInt(this.hexToBin("" + a.charAt(0)))][this.FBToInt(this.hexToBin("" + a.charAt(1)))];
	}

	private String[][] subBytes(String[][] in) {
		String[][] solution = new String[in.length][in[0].length];
		for (int i = 0; i < solution.length; i++)
			for (int j = 0; j < solution[0].length; j++)
				solution[i][j] = this.sBox(in[i][j]);
		return solution;
	}

	private String[][] shiftRows(String[][] in) {
		String[][] solution = new String[in.length][in[0].length];
		for (int i = 0; i < solution.length; i++)
			for (int j = 0; j < solution[0].length; j++)
				solution[j][i] = in[this.modulo(i + j, 4)][i];
		return solution;
	}

	private int[] split(String string) {
		int size = 1;
		for (int i = 0; i < string.length(); i++)
			if (string.charAt(i) == '_')
				size++;
		int[] table = new int[size];
		String[] tablestring = new String[size];
		int tmp = 0;
		for (int i = 0; i < tablestring.length; i++)
			tablestring[i] = "";
		for (int i = 0; i < string.length(); i++)
			if (string.charAt(i) == '_')
				tmp++;
			else
				tablestring[tmp] += string.charAt(i);
		for (int i = 0; i < tablestring.length; i++)
			table[i] = this.stringToInt(tablestring[i]);
		return table;
	}

	private int[] generationLoop(int n, int[] table) {
		int[] tmpArray = new int[n];
		int x = 0;
		int y = 0;
		int z = 0;
		int tmp = 0;
		for (int i = 0; i < n; i++) {
			x = this.modulo(x + 1, table.length);
			y = this.modulo(y + table[x], table.length);
			tmp = table[x];
			table[x] = table[y];
			table[y] = tmp;
			tmpArray[z] = table[this.modulo(table[x] + table[y], table.length)];
			z++;
		}
		return tmpArray;
	}

	// Tasks
	private String taskXOR(String a, String b) {
		while (a.length() != b.length()) {
			if (a.length() < b.length()) {
				a = 0 + a;
			} else {
				b = 0 + b;
			}
		}
		String solution = "";
		for (int i = 0; i < a.length(); i++) {
			if (a.charAt(i) == b.charAt(i)) {
				solution += "0";
			} else {
				solution += "1";
			}
		}
		return solution;
	}

	private String taskFactorization(int n) {
		if (n > 1) {
			String solution = "";
			while (n > 1)
				for (int i = 2; i <= n; i++)
					if (this.modulo(n, i) == 0 && this.isPrime(i)) {
						if (n != i)
							solution += i + "*";
						else
							solution += i;
						n /= i;
						i = n + 1;
					}
			return solution;
		}
		return "";
	}

	private String taskDESKeyschedule(String key, int round) {
		String keyFinal = "";
		int shiftsFR = 0;
		int[] shifts = { 1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1 };
		int[] codeCblock = { 57, 49, 41, 33, 25, 17, 9, 1, 58, 50, 42, 34, 26, 18, 10, 2, 59, 51, 43, 35, 27, 19, 11, 3,
				60, 52, 44, 36 };
		int[] codeDblock = { 63, 55, 47, 39, 31, 23, 15, 7, 62, 54, 46, 38, 30, 22, 14, 6, 61, 53, 45, 37, 29, 21, 13,
				5, 28, 20, 12, 4 };
		int[] permutatePC2 = { 14, 17, 11, 24, 1, 5, 3, 28, 15, 6, 21, 10, 23, 19, 12, 4, 26, 8, 16, 7, 27, 20, 13, 2,
				41, 52, 31, 37, 47, 55, 30, 40, 51, 45, 33, 48, 44, 49, 39, 56, 34, 53, 46, 42, 50, 36, 29, 32 };

		char[] Cblock = new char[28];
		char[] Dblock = new char[28];
		char[] shifted = new char[56];

		for (int i = 0; i < codeCblock.length; i++) {
			Cblock[i] = key.charAt(codeCblock[i] - 1);
			Dblock[i] = key.charAt(codeDblock[i] - 1);
		}

		for (int i = 0; i < shifts.length && i < round; i++) {
			shiftsFR += shifts[i];
		}

		char[] shiftedKeyC = this.shiftKey(Cblock, shiftsFR);
		char[] shiftedKeyD = this.shiftKey(Dblock, shiftsFR);
		for (int i = 0; i < shiftedKeyC.length; i++) {
			shifted[i] = shiftedKeyC[i];
			shifted[i + Cblock.length] = shiftedKeyD[i];
		}

		for (int i = 0; i < permutatePC2.length; i++)
			keyFinal += shifted[permutatePC2[i] - 1];
		return keyFinal;
	}

	private char[] taskDESRBlock(char[] block, int rounds, String key) {
		int[] permutation = { 16, 7, 20, 21, 29, 12, 28, 17, 1, 15, 23, 26, 5, 18, 31, 10, 2, 8, 24, 14, 32, 27, 3, 9,
				19, 13, 30, 6, 22, 11, 4, 25 };
		int[] expand = { 32, 1, 2, 3, 4, 5, 4, 5, 6, 7, 8, 9, 8, 9, 10, 11, 12, 13, 12, 13, 14, 15, 16, 17, 16, 17, 18,
				19, 20, 21, 20, 21, 22, 23, 24, 25, 24, 25, 26, 27, 28, 29, 28, 29, 30, 31, 32, 1 };
		char[] total = block;
		char[] rBlock = new char[total.length / 2];
		char[] newRBlock;
		char[] rBlockXOR = new char[48];
		String roundkey = key;
		String rBlockPerm = "";
		String rBlockB = "";
		String lBlock = "";

		for (int i = 0; i < total.length / 2; i++) {
			rBlock[i] = total[i + total.length / 2];
			lBlock += total[i];
		}
		for (int i = 0; i < rounds; i++) {
			rBlockB = "";
			rBlockPerm = "";
			for (int j = 0; j < 48; j++)
				rBlockB += rBlock[expand[j] - 1];
			rBlockB = this.taskXOR(rBlockB, roundkey);

			for (int j = 0; j < 48; j++)
				rBlockXOR[j] = rBlockB.charAt(j);
			newRBlock = this.sboxes(rBlockXOR);

			for (int j = 0; j < newRBlock.length; j++)
				rBlockPerm += newRBlock[permutation[j] - 1];
			rBlockPerm = this.taskXOR(rBlockPerm, lBlock);
			lBlock = "";

			for (int j = 0; j < rBlock.length; j++) {
				lBlock += rBlock[j];
				rBlock[j] = rBlockPerm.charAt(j);
			}
		}
		return rBlock;
	}

	private String taskGF8(String a, String b) {
		String bina = this.hexToBin(a);
		String binb = this.hexToBin(b);
		String solution = "";
		solution = this.multi(bina, binb);
		solution = this.mx(solution);
		while (solution.length() < 8)
			solution = "0" + solution;
		return this.binToHex(solution);
	}

	private String[] taskAESKeyExpansion(String key) {
		String[] keys = { "", "", "" };
		String[][] rcon = { { "01", "02", "04", "08", "10", "20", "40", "80", "1b", "36" },
				{ "00", "00", "00", "00", "00", "00", "00", "00", "00", "00" },
				{ "00", "00", "00", "00", "00", "00", "00", "00", "00", "00" },
				{ "00", "00", "00", "00", "00", "00", "00", "00", "00", "00" } };
		String[][] allkeys = new String[16][4];
		for (int i = 0; i < 4; i++) {
			allkeys[0][i] = key.substring(i * 2, i * 2 + 2);
			allkeys[1][i] = key.substring(i * 2 + 8, i * 2 + 10);
			allkeys[2][i] = key.substring(i * 2 + 16, i * 2 + 18);
			allkeys[3][i] = key.substring(i * 2 + 24, i * 2 + 26);
		}
		for (int i = 4; i < allkeys.length; i++)
			for (int j = 0; j < 4; j++)
				if (this.modulo(i, 4) != 0) {
					allkeys[i][j] = this
							.binToHex(this.taskXOR(this.hexToBin(allkeys[i - 4][j]), this.hexToBin(allkeys[i - 1][j])));

				} else {
					String[] sub = { allkeys[i - 1][1], allkeys[i - 1][2], allkeys[i - 1][3], allkeys[i - 1][0] };
					allkeys[i][j] = this.binToHex(this.taskXOR(
							this.taskXOR(this.hexToBin(allkeys[i - 4][j]), this.hexToBin(this.sBox(sub[j]))),
							this.hexToBin(rcon[j][i / 4 - 1])));
				}
		for (int i = 4; i < 8; i++)
			for (int j = 0; j < 4; j++) {
				keys[0] += allkeys[i][j];
				keys[1] += allkeys[i + 4][j];
				keys[2] += allkeys[i + 8][j];
			}
		return keys;
	}

	private String taskAESMixColumns(String string) {
		String[][] cx = { { "02", "03", "01", "01" }, { "01", "02", "03", "01" }, { "01", "01", "02", "03" },
				{ "03", "01", "01", "02" } };
		String[][] input = new String[4][4];
		String[][] output = new String[4][4];
		String result = "";
		for (int i = 0; i < 4; i++) {
			input[0][i] = string.substring(i * 2, i * 2 + 2);
			input[1][i] = string.substring(i * 2 + 8, i * 2 + 10);
			input[2][i] = string.substring(i * 2 + 16, i * 2 + 18);
			input[3][i] = string.substring(i * 2 + 24, i * 2 + 26);
		}
		for (int i = 0; i < output.length; i++)
			for (int j = 0; j < output[0].length; j++)
				output[i][j] = this.binToHex(this.taskXOR(this.hexToBin(this.taskGF8(cx[j][3], input[i][3])),
						this.taskXOR(this.hexToBin(this.taskGF8(cx[j][2], input[i][2])),
								this.taskXOR(this.hexToBin(this.taskGF8(cx[j][0], input[i][0])),
										this.hexToBin(this.taskGF8(cx[j][1], input[i][1]))))));
		for (int i = 0; i < output.length; i++)
			for (int j = 0; j < output[0].length; j++)
				result += output[i][j];
		return result;
	}

	private String taskAESTransformation(String string) {
		String solution = "";
		String[][] in = new String[4][4];
		String[][] out = new String[4][4];
		for (int i = 0; i < 4; i++) {
			in[0][i] = string.substring(i * 2, i * 2 + 2);
			in[1][i] = string.substring(i * 2 + 8, i * 2 + 10);
			in[2][i] = string.substring(i * 2 + 16, i * 2 + 18);
			in[3][i] = string.substring(i * 2 + 24, i * 2 + 26);
		}
		out = this.subBytes(in);
		out = this.shiftRows(out);
		for (int i = 0; i < out.length; i++)
			for (int j = 0; j < out[0].length; j++)
				solution += out[i][j];
		solution = this.taskAESMixColumns(solution);
		return solution;
	}

	private String taskAESThreeRounds(String text, String key) {
		String solution = "";
		String allover = "";
		String[] keys = this.taskAESKeyExpansion(key);
		solution = this.binToHex(this.taskXOR(this.hexToBin(key), this.hexToBin(text)));
		allover += solution;

		for (int i = 0; i < 2; i++) {
			solution = this.taskAESTransformation(solution);
			solution = this.binToHex(this.taskXOR(this.hexToBin(keys[i]), this.hexToBin(solution)));
			allover += "_" + solution;
		}
		return allover;
	}

	private int[] taskRC4Loop() {
		int[] tmp = this.generationLoop(currentTask.getStringArray(1).length(),
				this.split(currentTask.getStringArray(0)));
		return tmp;
	}

	private int[] taskRC4Keyschedule(String key) {
		int[] keytable = this.split(key);
		int[] stable = new int[keytable.length];
		for (int i = 0; i < stable.length; i++)
			stable[i] = i;
		int x = 0;
		int tmp = 0;
		for (int i = 0; i < stable.length; i++) {
			x = this.modulo(x + keytable[i] + stable[i], stable.length);
			tmp = stable[i];
			stable[i] = stable[x];
			stable[x] = tmp;
		}
		return stable;
	}

	private String taskRC4Encryption(String key, String text) {
		String solution = "";
		int[] array = this.generationLoop(text.length(), this.taskRC4Keyschedule(key));
		for (int i = 0; i < text.length(); i++)
			solution += this.taskXOR(this.decimalToByte(this.getASCII(text.charAt(i))), this.decimalToByte(array[i]));
		return solution;
	}

	// Loop
	/**
	 * Durchläuft eine Liste von Aufgaben und fordert diese vom Server an.
	 */
	public void taskLoop() {
		Random random = new Random();
		String solution;
		for (int i = 0; i < tasks.length; i++) {
			switch (tasks[i]) {
			case TASK_CLEARTEXT:
				currentTask = con.getTask(tasks[i]);
				solution = currentTask.getStringArray(0);
				break;

			case TASK_XOR:
				currentTask = con.getTask(tasks[i]);
				String[] hex = { currentTask.getStringArray(0), currentTask.getStringArray(1) };
				while (hex[0].length() != hex[1].length()) {
					if (hex[0].length() < hex[1].length()) {
						hex[0] = 0 + hex[0];
					} else
						hex[0] = 0 + hex[0];
				}
				String[] bins = { this.hexToBin(hex[0]), this.hexToBin(hex[1]) };
				solution = this.taskXOR(bins[0], bins[1]);
				break;

			case TASK_MODULO:
				currentTask = con.getTask(tasks[i]);
				Integer m = new Integer(this.modulo(currentTask.getIntArray(0), currentTask.getIntArray(1)));
				solution = m.toString();
				break;

			case TASK_FACTORIZATION:
				currentTask = con.getTask(tasks[i]);
				solution = this.taskFactorization(currentTask.getIntArray(0));
				break;

			case TASK_VIGENERE:
				currentTask = con.getTask(tasks[i]);
				solution = "";
				String v1 = currentTask.getStringArray(0);
				String v2 = currentTask.getStringArray(1);

				for (int vig = 0; vig < v1.length(); vig++) {
					solution += alphabet[this.modulo(this.getPosition(v1.charAt(vig))
							- this.getPosition(v2.charAt(this.modulo(vig, v2.length()))) + 26, 26)];
				}
				break;

			case TASK_DES_KEYSCHEDULE:
				currentTask = con.getTask(tasks[i]);
				solution = this.taskDESKeyschedule(currentTask.getStringArray(0), currentTask.getIntArray(0));
				break;

			case TASK_DES_RBLOCK:
				currentTask = con.getTask(tasks[i]);
				solution = "";
				char[] perm = this.intitialPermutation(currentTask.getStringArray(0));
				char[] total = this.taskDESRBlock(perm, currentTask.getIntArray(0),
						"000000000000000000000000000000000000000000000000");
				for (int rb = 0; rb < total.length; rb++) {
					solution += total[rb];
				}
				break;

			case TASK_DES_FEISTEL:
				currentTask = con.getTask(tasks[i]);
				String block = currentTask.getStringArray(0);
				char[] splitted = new char[64];
				for (int i1 = 0; i1 < splitted.length; i1++) {
					splitted[i1] = block.charAt(i1);
				}
				char[] feistel = this.taskDESRBlock(splitted, 1, currentTask.getStringArray(1));
				solution = "";
				for (int i1 = 0; i1 < feistel.length; i1++) {
					solution += feistel[i1];
				}
				break;

			case TASK_DES_ROUND:
				currentTask = con.getTask(tasks[i]);
				String block2 = currentTask.getStringArray(0) + currentTask.getStringArray(1);
				char[] splitted2 = new char[64];
				for (int i1 = 0; i1 < splitted2.length; i1++) {
					splitted2[i1] = block2.charAt(i1);
				}
				char[] totalround = this.taskDESRBlock(splitted2, 1,
						this.getRoundKey(currentTask.getStringArray(2), currentTask.getIntArray(0)));
				solution = "";
				for (int i1 = 0; i1 < totalround.length; i1++) {
					solution += totalround[i1];
				}
				solution = currentTask.getStringArray(1) + solution;
				break;

			case TASK_AES_GF8:
				currentTask = con.getTask(tasks[i]);
				solution = this.taskGF8(currentTask.getStringArray(0), currentTask.getStringArray(1));
				break;

			case TASK_AES_KEYEXPANSION:
				currentTask = con.getTask(tasks[i]);
				String[] keys = this.taskAESKeyExpansion(currentTask.getStringArray(0));
				String key = currentTask.getStringArray(0) + "_" + keys[0] + "_" + keys[1];
				solution = key;
				break;

			case TASK_AES_MIXCOLUMNS:
				currentTask = con.getTask(tasks[i]);
				solution = this.taskAESMixColumns(currentTask.getStringArray(0));
				break;

			case TASK_AES_TRANSFORMATION:
				currentTask = con.getTask(tasks[i]);
				solution = this.taskAESTransformation(currentTask.getStringArray(0));
				break;

			case TASK_AES_3ROUNDS:
				currentTask = con.getTask(tasks[i]);
				solution = this.taskAESThreeRounds(currentTask.getStringArray(0), currentTask.getStringArray(1));
				break;

			case TASK_RC4_LOOP:
				currentTask = con.getTask(tasks[i]);
				solution = "";
				for (int j = 0; j < taskRC4Loop().length; j++)
					solution += taskRC4Loop()[j];
				break;

			case TASK_RC4_KEYSCHEDULE:
				currentTask = con.getTask(tasks[i]);
				int[] stable = this.taskRC4Keyschedule(currentTask.getStringArray(0));
				solution = "";
				for (int j = 0; j < stable.length; j++)
					solution += solution == "" ? stable[j] : "_" + stable[j];
				break;

			case TASK_RC4_ENCRYPTION:
				currentTask = con.getTask(tasks[i]);
				solution = this.taskRC4Encryption(currentTask.getStringArray(0), currentTask.getStringArray(1));
				break;

			// Ohne Hilfsmethoden implementiert
			case TASK_DIFFIEHELLMAN:
				currentTask = con.getTask(tasks[i]);
				solution = "";
				int x = currentTask.getIntArray(0);
				int y = currentTask.getIntArray(1);
				int z = random.nextInt(x - 1);
				double doubleArray = currentTask.getDoubleArray(0);
				int modDA = this.moduloPow((int) doubleArray, z, x);
				String[] stringArray = { "" + this.moduloPow(y, z, x) };
				con.sendMoreParams(currentTask, stringArray);
				int[] array = this.split(currentTask.getStringArray(0));
				for (int j = 0; j < array.length; j++) {
					array[j] = this.byteToDec(this.taskXOR(this.decimalToByte(modDA), this.decimalToByte(array[j])));
					solution += this.getCharfromASCII(array[j]);
				}
				break;

			// Ohne Hilfsmethoden implementiert
			case TASK_RSA_ENCRYPTION:
				solution = "";
				currentTask = con.getTask(tasks[i]);
				String text = currentTask.getStringArray(0);
				int encryptA = currentTask.getIntArray(1);
				int encryptB = currentTask.getIntArray(0);
				int[] encryptArray = new int[text.length()];
				for (int encryptI = 0; encryptI < text.length(); encryptI++) {
					encryptArray[encryptI] = this.moduloPow(this.getASCII(text.charAt(encryptI)), encryptA, encryptB);
					if (text.length() == encryptI + 1) {
						solution += encryptArray[encryptI];
					} else {
						solution += encryptArray[encryptI] + "_";
					}
				}
				break;

			// Ohne Hilfsmethoden implementiert
			case TASK_RSA_DECRYPTION:
				solution = "";
				int decryptI = 1;
				int[] primesArray = { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71 };
				int[] decryptA = { 0, 0 };
				int[] decryptB = { 1, 1 };
				while (decryptI == 1
						|| this.modulo(decryptI * decryptB[1], (decryptA[0] - 1) * (decryptA[1] - 1)) != 1) {
					while (decryptA[0] * decryptA[1] < 130 || decryptA[0] == decryptA[1]) {
						decryptA[0] = primesArray[random.nextInt(20)];
						decryptA[1] = primesArray[random.nextInt(20)];
					}
					decryptB[0] = decryptA[0] * decryptA[1];
					decryptB[1] = decryptA[0];
					while (this.modulo(decryptB[0], decryptB[1]) == 0
							|| (decryptA[0] - 1) * (decryptA[1] - 1) < decryptB[1])
						decryptB[1] = primesArray[random.nextInt(20)];
					decryptI = 1;
					while (this.modulo(decryptI * decryptB[1], (decryptA[0] - 1) * (decryptA[1] - 1)) != 1
							&& decryptI < (decryptA[0] - 1) * (decryptA[1] - 1))
						decryptI++;
				}

				String[] decryptString = { "" + decryptB[0], "" + decryptB[1] };
				currentTask = con.getTask(tasks[i], decryptString);
				int[] decryptArrayC = this.split(currentTask.getStringArray(0));
				for (int j = 0; j < decryptArrayC.length; j++) {
					decryptArrayC[j] = this.moduloPow(decryptArrayC[j], decryptI, decryptB[0]);
					solution += this.getCharfromASCII(decryptArrayC[j]);
				}
				break;

			default:
				currentTask = con.getTask(tasks[i]);
				solution = "Nicht implementiert!";
			}

			if (con.sendSolution(solution))
				System.out.println("Aufgabe " + tasks[i] + ": Lösung korrekt");
			else
				System.out.println("Aufgabe " + tasks[i] + ": Lösung falsch");
		}
	}

	public static void main(String[] args) {
		Client c = new Client();
		if (c.isReady()) {
			c.taskLoop();
		}
		c.close();
	}
}
