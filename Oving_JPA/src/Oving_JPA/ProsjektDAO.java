package Oving_JPA;

import java.util.List;
import java.util.Scanner;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

public class ProsjektDAO {
    
    private static EntityManagerFactory emf;

    static {
        emf = Persistence.createEntityManagerFactory("OvingJPA-PU");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Legge til nytt prosjekt. Skriv inn prosjektnavn: ");
        String prosjektnavn = scanner.nextLine();

        System.out.print("Skriv inn prosjektbeskrivelse: ");
        String prosjektbeskrivelse = scanner.nextLine();

        leggTilNyttProsjekt(prosjektnavn, prosjektbeskrivelse);
        
        System.out.print("Legg til ansatt i prosjekt. Skriv inn ansattid: ");
        int ansattid = scanner.nextInt();
        
        System.out.print("Skriv inn prosjektid: ");
        int prosjektid = scanner.nextInt();
        
        scanner.nextLine(); 
        
        System.out.print("Skriv inn prosjektrolle den ansatte har: ");
        String prosjektrolle = scanner.nextLine();
        
        System.out.print("Skriv inn antall timer den ansatte har på prosjektet så langt: ");
        int prosjekttimer = scanner.nextInt();
        
        leggTilAnsattIProsjekt(ansattid, prosjektid, prosjektrolle, prosjekttimer);

        scanner.close();
    }

    public static void leggTilNyttProsjekt(String prosjektnavn, String prosjektbeskrivelse) {
        System.out.println("Kobler til database...");
        EntityManager em = emf.createEntityManager();
        
        try {
            em.getTransaction().begin();

            TypedQuery<Prosjekt> query = em.createQuery(
                    "SELECT p FROM Prosjekt p WHERE p.prosjektnavn = :prosjektnavn", Prosjekt.class);
            query.setParameter("prosjektnavn", prosjektnavn);
            List<Prosjekt> eksisterendeProsjekter = query.getResultList();

            if (!eksisterendeProsjekter.isEmpty()) {
                System.out.println("Prosjektet eksisterer allerede: " + prosjektnavn);
            } else {
                Prosjekt nyttProsjekt = new Prosjekt();
                nyttProsjekt.setProsjektnavn(prosjektnavn);
                nyttProsjekt.setProsjektbeskrivelse(prosjektbeskrivelse);
                
                em.persist(nyttProsjekt);
                em.getTransaction().commit();
                
                System.out.println("Prosjektet '" + prosjektnavn + "' ble lagt til.");
            }

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback(); 
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public static void leggTilAnsattIProsjekt(int ansattid, int prosjektid, String prosjektrolle, int prosjekttimer) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();


            Ansatt ansatt = em.find(Ansatt.class, ansattid);
            Prosjekt prosjekt = em.find(Prosjekt.class, prosjektid);

            if (ansatt == null || prosjekt == null) {
                System.out.println("Fant ikke ansatt eller prosjekt med angitt ID.");
                return;
            }


            Prosjekt_Ansatt pa = new Prosjekt_Ansatt();
            pa.setAnsatt(ansatt);
            pa.setProsjekt(prosjekt);
            pa.setProsjektrolle(prosjektrolle);
            pa.setProsjekttimer(prosjekttimer);
            
            em.persist(pa);

            em.getTransaction().commit();
            System.out.println("Ansatt " + ansatt.getFornavn() + " lagt til i prosjekt '" + prosjekt.getProsjektnavn() + "' som " + prosjektrolle);
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}
