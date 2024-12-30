package csc281;

public class RSA_Encryption_System {

    private static final long a = 48271;
    private static final long c = 37;
    private static final long m = 65536;

    //FIRST METHOD
    public int[] LCG(int seed, int quantity) {
        System.out.println("Hi there! This is LCG method, I am called with");
        System.out.println("(seed=" + seed + " quantity=100)");
        System.out.println("and I have those initialized local variables:");
        System.out.println("(A = " + a + " C = " + c + " M = " + m + ")");

        int[] randomNums = new int[quantity];
        int x = seed;

        for (int i = 0; i < quantity; i++) {
            x = (int) ((a * x + c) % m); // LCG = (ax + c) mod m
            randomNums[i] = x;
        }

        System.out.print("I generated 100 random numbers, and I made them all positives!\n");
        for (int j : randomNums) {
            System.out.print(j + ",");
        }
        System.out.println("\nbye now! --LCG method");
        return randomNums;
    }//end method LCG

    //SECOUND METHOD
    public static boolean millerRabinTest(long n, int k) {
        if (n < 2) {
            return false;
        }
        if (n == 2) {
            return true;
        }
        if (n % 2 == 0) {
            return false;
        }
        // Step 1: Write n as 2^r * d + 1 with d odd
        long d = n - 1;
        int r = 0;
        while (d % 2 == 0) {
            d /= 2;
            r++;
        }
        // Step 2:Choose a random integer 'a' such that 1 < a < n-1
        for (int i = 0; i < k; i++) {
            long a = 2 + (long) (Math.random() * (n - 4));

            // Step 3: Compute a^d mod n and store it in 'x'
            long x = modularExponentiation(a, d, n);

            // If x is 1 or n-1, n may be prime, so continue to the next iteration
            if (x == 1 || x == n - 1) {
                continue;
            }

            int j;
            for (j = 0; j < r - 1; j++) {
                x = modularExponentiation(x, 2, n); // x = x^2 mod n

                if (x == n - 1) {
                    break;
                }
            }
            if (j == r - 1) {
                return false;
            }
        }

        return true;
    }//END METHOD millerRabinTest

    // HELPING METHOD Extended Euclidean Algorithm
    private long extendedEuclideanAlgorithm(long e, long m) {
        long x = 0, y = 1, lastX = 1, lastY = 0, temp;
        while (m != 0) {
            long quotient = e / m;
            long remainder = e % m;
            e = m;
            m = remainder;

            temp = x;
            x = lastX - quotient * x;
            lastX = temp;

            temp = y;
            y = lastY - quotient * y;
            lastY = temp;
        }
        return lastX < 0 ? lastX + m : lastX;
    }

    // THIRD METHOD RSA Key Generation
    public KeyPair generateKeys() {

        System.out.println("Hi there! This is generateKeys method");
        System.out.println("I will be generating 100 random numbers using LCG method");
        System.out.println("calling LCG...");

        int seed = 3;
        int[] randomNums = LCG(seed, 100);

        System.out.println("back to generateKeys, now I will examine the random numbers and assign p to the first number that passes millerRabinTest");
        System.out.println("q to the second number (if it is not equal to p... duh!)");

        long p = 0, q = 0;
        int pIndex = -1, qIndex = -1;

        for (int i = 0; i < randomNums.length; i++) {
            if (p == 0 && millerRabinTest(randomNums[i], 5)) {
                p = randomNums[i];
                pIndex = i;
            } else if (q == 0 && millerRabinTest(randomNums[i], 5) && randomNums[i] != p) {
                q = randomNums[i];
                qIndex = i;
                break;
            }
        }

        System.out.println("p is " + p + " this is the " + (pIndex + 1) + "th element in the random list");
        System.out.println("q is " + q + " this is the " + (qIndex + 1) + "th element in the random list");

        long n = p * q;
        long phiN = (p - 1) * (q - 1);
        System.out.println("I calculated phi:" + phiN);

        int e = 65537;
        System.out.println("I set e:" + e);

        int d = (int) extendedEuclideanAlgorithm(e, phiN);
        System.out.println("I called extendedEuclideanAlgorithm, and got d to be: " + d);
        System.out.println("finally, I am creating an instance of KeyPair class as:");
        System.out.println("KeyPair(new PublicKey(n, e), new PrivateKey(n, d))");

        System.out.println("My PublicKey is :");
        PublicKey pubKey = new PublicKey(n, e);
        System.out.println(pubKey.toString());

        System.out.println("My PrivateKey is :");
        PrivateKey privKey = new PrivateKey(n, d);
        System.out.println(privKey.toString());

        // return new PublicKey(n, e) and PrivateKey(n, d)
        System.out.println("and returning it. Bye now! --generateKeys method");
        return new KeyPair(e, n, d);

    }// END METHOD generateKeys

    static long[] encrypt(String message, long d, long n) {
        System.out.println("Hi there! This is encrypt method");
        System.out.println("converting my string to int:");

        long[] arr = new long[message.length()];
        long[] stringtonum = String_to_longArray(message);
        for (int i = 0; i < message.length(); i++) {
            System.out.print(stringtonum[i] + "   ");
            long base = stringtonum[i];
            long encrypted = modularExponentiation(base, d, n);
            arr[i] = encrypted;
        }
        System.out.println("\nencryptedValues:");
        for (long val : arr) {
            System.out.print(val + " ");
        }
        System.out.println("\nbye now! --encrypt method");
        return arr;
    }// END METHOD encrypt

    public static String decrypt(long[] ciphertext, long d, long n) {
        System.out.println("Hi there! This is decrypt method");
        long[] plaintext = new long[ciphertext.length];
        System.out.println("decryptedValues:");
        for (int i = 0; i < plaintext.length; i++) {
            plaintext[i] = modularExponentiation(ciphertext[i], d, n);
            System.out.print(plaintext[i] + "   ");
        }

        String decryptedText = LongArray_to_String(plaintext);
        System.out.println("\nArrayToString:" + decryptedText);
        System.out.println("bye now! --decrypt method");

        return decryptedText;
    }// END METHOD decrypt

    public static long[] String_to_longArray(String txt) {
        char[] A = Alpabet_indices_array();
        txt = txt.replace(" ", "");
        txt = txt.toLowerCase();
        long[] arrayOfLong = new long[txt.length()];

        for (int i = 0; i < arrayOfLong.length; i++) {
            for (int j = 0; j < A.length; j++) {
                if (A[j] == txt.charAt(i)) {
                    arrayOfLong[i] = j + 1L;
                    break;
                }
            }
        }
        return arrayOfLong;
    }

    public static String LongArray_to_String(long[] arrayOfLong) {

        char[] A = Alpabet_indices_array();
        String txt = "";

        for (int i = 0; i < arrayOfLong.length; i++) {
            txt = txt + String.valueOf(A[(int) arrayOfLong[i] - 1]);
        }

        return txt;
    }

    public static long modularExponentiation(long base, long exponent, long modulus) {
        // special case when base=0
        if (base == 0) {
            return 0;
        }
        // special case when base=1 or base and modulus=1
        if (base == 1) {
            if (modulus == 1) {
                return 0;
            }
            return 1;
        }

        long x = 1;
        long power = base % modulus;

        while (exponent > 0) {
            if (exponent % 2 == 1) {
                x = (x * power) % modulus;
            }
            power = (power * power) % modulus;
            exponent = exponent / 2;// shifting to right to right to ensure that we went through each bit 
        }
        return x;
    }

    public static char[] Alpabet_indices_array() {
        char[] A = new char[26];
        char character = 'a';
        for (int i = 0; i < A.length; i++) {
            A[i] = character++;
        }
        return A;
    }

    public static void main(String[] args) {
        RSA_Encryption_System rsa = new RSA_Encryption_System();

        System.out.println("Hi there! This is the main method");
        System.out.println("calling generateKeys");

        RSA_Encryption_System.KeyPair keys = rsa.generateKeys();

        String plaintext = "Norah";
        System.out.println("setting plaintext to: " + plaintext);
        System.out.println("calling encrypt...");
        long publicKeyExponent = keys.getPublicKey().getExponent();
        long publicKeyModulus = keys.getPublicKey().getModulus();
        long[] encrypted = rsa.encrypt(plaintext, publicKeyExponent, publicKeyModulus);

        System.out.println("calling decrypt on encrypt output...");
        long privateKeyExponent = keys.getPrivateKey().getExponent();
        long privateKeyModulus = keys.getPrivateKey().getModulus();
        String decrypted = rsa.decrypt(encrypted, privateKeyExponent, privateKeyModulus);

        System.out.println("making sure decryptedText and plaintext are equalsIgnoreCase...");
        if (decrypted.equalsIgnoreCase(plaintext)) {
            System.out.println("Yes! they are");
        } else {
            System.out.println("No! they are not the same");
        }

        System.out.println("bye now! --main method");
    }

    public static class PublicKey {

        private long modulus;
        private long exponent;

        public PublicKey(long modulus, long exponent) {
            this.modulus = modulus;
            this.exponent = exponent;
        }

        public long getModulus() {
            return modulus;
        }

        public long getExponent() {
            return exponent;
        }

        @Override
        public String toString() {
            return "PublicKey{" + "modulus=" + modulus + ", exponent=" + exponent + '}';
        }

    }

    public static class PrivateKey {

        private long modulus;
        private long exponent;

        public PrivateKey(long modulus, long exponent) {
            this.modulus = modulus;
            this.exponent = exponent;
        }

        public long getModulus() {
            return modulus;
        }

        public long getExponent() {
            return exponent;
        }

        @Override
        public String toString() {
            return "PrivateKey{" + "modulus=" + modulus + ", exponent=" + exponent + '}';
        }

    }

    public static class KeyPair {

        private PublicKey publicKey;
        private PrivateKey privateKey;

        public KeyPair(long e, long n, long d) {
            this.publicKey = new PublicKey(n, e);
            this.privateKey = new PrivateKey(n, d);
        }

        public PublicKey getPublicKey() {
            return publicKey;
        }

        public PrivateKey getPrivateKey() {
            return privateKey;
        }
    }

}//end class
