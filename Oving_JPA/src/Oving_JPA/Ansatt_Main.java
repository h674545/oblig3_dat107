package Oving_JPA;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.Scanner;

public class Ansatt_Main {

    private static EntityManagerFactory emf;

    static {
        emf = Persistence.createEntityManagerFactory("OvingJPA-PU");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Skriv inn ansatt ID: ");
        Long ansattId = scanner.nextLong();

        Ansatt ansatt = finnAnsattById(ansattId);

        if (ansatt != null) {
            System.out.println("Ansatt funnet: " + ansatt);
        } else {
            System.out.println("Ingen ansatt med denne ID-en.");
        }

        scanner.close();
    }

    private static Ansatt finnAnsattById(Long ansattId) {
        System.out.println("Kobler til database...");
        EntityManager em = emf.createEntityManager();
        
        try {
            return em.find(Ansatt.class, ansattId);
        } finally {
            em.close();
        }
    }
}