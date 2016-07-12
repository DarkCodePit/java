package intelhex;
//librarii pentru lucru cu fisiere
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Intelhex {
    //functia de convertare din hexazecimal in int
    public static int hex2int(String s){
        return Integer.decode("0x"+s); // se adauga 0x pentru a specifica ca este un numar in baza 16
    }
    //functia pentru parsarea unei linii
    public static int parse_line(String s){
        String c = s.substring(1,3); //taie din sir CC
        String a = s.substring(3,7); //taie din sir AAAA
        String r = s.substring(7,9); //taie din sir RR
        int m = hex2int(c)*2+9; //calculeaza pozitia SS prin convertarea CC in int *2 + 9(pozitii pentru CC AAAA si RR)
        String ss = s.substring(m,m+2).toUpperCase(); //taie di sir SS
        int start = 9; //pozitia de start a bitilor de informatie
        String t; //variabila temporara
        int suma = 0; //initializarea sumei
        for(int i=start;i<hex2int(c)+9;i++){
            t = s.substring(start,start+2); //taie cate 2 biti din DDDD...D
            suma += hex2int(t); //converteaza DD temporar si il aduna la suma
            start+=2; // merge la urmatorii 2 biti
        }
        int s_a = hex2int(s.substring(3,5))+hex2int(s.substring(5,7)); //Din AAAA taie AA si AA converteaza in int si face suma
        suma += s_a+hex2int(c)+hex2int(r); //suma generala
        String c2 = Integer.toHexString(256-suma%256).toUpperCase(); //calculeaza C2
        if(c2.length()<2) // corecteaza eroarea cand dupa conversie din 16 in 10 : 05 => 5 
            c2 = "0"+c2; //verifica daca lungimea C2 <2 adauga 0 in fata
        if(ss.compareTo(c2) == 0) //verifica daca sirurile de caractere sunt egale
            return 1; //cod corect
        else
            return 0; //cod gresit
    }
    //functia pentru citire din fisier si implementarea verizicarii codurilor
    private static void read_file(String path) throws FileNotFoundException{ 
        //FileNotFoundException este adaugat pentru a prelucra eroare daca fisierul nu se gaseste
        File text = new File(path); //selecteaza fisierul dupa locatie path
        Scanner scnr = new Scanner(text); //se creeaza obiectul de tip Scanner
        while(scnr.hasNextLine()){ // se parcurge fiecare linie din fisier
            String line = scnr.nextLine(); //variabila linie primeste valoare liniei din fisier
            if(parse_line(line) == 1) // se apeleaza functia de parsare a liniei 
                System.out.println("[1]"+line); // codul este corect
            else
                System.out.println("[0]"+line); //codul este gresit
        }           
    }
    public static void main(String[] args) throws FileNotFoundException {
        read_file("C:/Users/Picaso/Desktop/in.txt"); //apelarea functiei de citire si verizicare a codurilor
    } 
}
