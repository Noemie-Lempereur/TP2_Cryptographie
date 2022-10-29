package com.Noemie;

import java.math.BigInteger;
import java.security.SecureRandom;

public class Main {
    static BigInteger clePublique[] = new BigInteger[2];
    static BigInteger clePrivee[] = new BigInteger[2];

    static public String encrypt (String message){
        String crypte = "";
        for(int i = 0; i < message.length(); i++)
        {
            char c = message.charAt(i);
            int ascii = (int)c;
            BigInteger x = BigInteger.valueOf(ascii).modPow(clePublique[0],clePublique[1]);

            crypte += x.toString();

            crypte += " ";
        }
        return crypte;
    }
    static public String decrypt (String message){
        String decrypte = "";
        String c = "";
        for(int i = 0; i < message.length(); i++)
        {
            if(message.charAt(i) != ' '){
                c+=message.charAt(i);
            }
            else {
                BigInteger nombre=new BigInteger(c);
                //System.out.println("nombre : "+nombre);
                BigInteger test = nombre.modPow(clePrivee[0],clePrivee[1]);
                char number = (char)(test.intValue());
                //System.out.println("test : "+test+ " number : "+number);
                decrypte += number;
                c="";
            }
        }
        return decrypte;
    }

    public static void hachage(int[] table) {

        String result ="";

        int k =0;
        while (k< table.length) {
            int[][] tab = new int[100][16];

            for (int i = 0; i < 100; i++) {
                for (int j = 0; j < 16; j++) {
                    k++;
                    if (k < table.length) {
                        tab[i][j] = table[k];
                    }
                }

            }

            for (int i = 0; i < 100; i++) {
                int res = 1;
                for (int j = 0; j < tab[i].length; j++) {
                    res ^= tab[i][j];
                }
                result += res;
            }

        }

    }


    public static void main(String[] args){
        int bitLength = 1024;
        SecureRandom rnd = new SecureRandom();
        BigInteger p = BigInteger.probablePrime(bitLength, rnd);
        BigInteger q = BigInteger.probablePrime(bitLength, rnd);
        BigInteger n = p.multiply(q);
        BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
        BigInteger e = BigInteger.TWO;
        while(e.gcd(phi).compareTo(BigInteger.ONE)!=0){
            e=e.add(BigInteger.ONE);
        }
        BigInteger d = e.modInverse(phi);

        clePublique[0] = e;
        clePublique[1] = n;
        clePrivee[0] = d;
        clePrivee[1] = n;

        String test = encrypt("bonjour");
        System.out.println("Encrypt : "+test);
        test = decrypt(test);
        System.out.println("Decrypt : "+test);
    }
}
