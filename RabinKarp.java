public class RabinKarp {
	static int kBase = 26, kMod = 997;
	
	public static void main(String args[]) {
		String t = "GACGCCA";
		String s = "CGC";
		System.out.println(rabinKarp(t,s));
	}
	
	// Returns the index of the first character of the substring if found, -1 otherwise.
	public static int rabinKarp(String t, String s) {
		// s is not a substring of t.
		if (s.length() > t.length()) {
			return -1;
		}
		// Hash codes for the substring of t and s.
		int tHash = 0, sHash = 0;
		// The modulo result of kBase^|s|.
		int powerS = 1;
		for (int i = 0; i < s.length(); i++) {
			powerS = i > 0 ? powerS * kBase % kMod : 1;
			tHash = (tHash * kBase + t.charAt(i)) % kMod;
			sHash = (sHash * kBase + s.charAt(i)) % kMod;
		}
		for (int i = s.length(); i < t.length(); i++) {
			// In case of hash collision but two strings are not equal, checks
			// the two substrings are actually equal or not.
			if (tHash == sHash && t.substring(i - s.length(), i).equals(s)) {
				return i - s.length();
			}
			// Uses rolling hash to compute the new hash code.
			tHash -= (t.charAt(i - s.length()) * powerS) % kMod;
			if (tHash < 0) {
				tHash += kMod;
			}
			tHash = (tHash * kBase + t.charAt(i)) % kMod;
		}
		// Tries to match s and t[t.size() - s.size() : t.size() - 1].
		if (tHash == sHash
				&& t.subSequence(t.length() - s.length(), t.length()).equals(s)) {
			return t.length() - s.length();
		}
		// s is not a substring of t.
		return -1;
	}
}
