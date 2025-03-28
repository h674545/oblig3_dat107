package Oving_JPA;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

public class AnsattDAO {
		
		   private static EntityManagerFactory emf;

		    static {
		        emf = Persistence.createEntityManagerFactory("OvingJPA-PU");
		    }

		    public static void main(String[] args) {
		    	Scanner scanner = new Scanner(System.in);

		        // Skriv inn ansatt ID
		        System.out.print("Skriv inn ansatt ID: ");
		        Integer ansattId = scanner.nextInt();

		        Ansatt ansatt = finnAnsattMedId(ansattId);

		        System.out.println("Kobler til database...");

		        if (ansatt != null) {
		            System.out.println("Ansatt funnet: " + ansatt);
		        } else {
		            System.out.println("Det finnes ingen ansatt med denne ID-en");
		        }

		        // Skriv inn ansatt brukernavn
		        System.out.print("Skriv inn ansatt brukernavn (store bokstaver): ");
		        String brukernavn = scanner.next();

		        Ansatt bnAnsatt = finnAnsattMedBrukernavn(brukernavn);

		        if(bnAnsatt != null) {
		            System.out.println("Ansatt funnet: " + bnAnsatt);
		        } else {
		            System.out.println("Det finnes ingen ansatt med dette brukernavnet");
		        }

		        // Oppdater ansatt informasjon
		        System.out.print("Skriv inn ansatt ID for oppdatering: ");
		        ansattId = scanner.nextInt();
		        scanner.nextLine();  // Clear the buffer

		        System.out.print("Skriv inn ny stilling: ");
		        String nyStilling = scanner.nextLine();
		        
		        System.out.print("Skriv inn ny månedslønn: ");
		        int nyLonn = scanner.nextInt();

		        oppdaterAnsatt(ansattId, nyStilling, nyLonn);

		        // Legg til ny ansatt
		        System.out.print("Skriv inn ansatt brukernavn for ny ansatt: ");
		        String brukernavn1 = scanner.next();

		        System.out.print("Skriv inn fornavn: ");
		        String fornavn = scanner.next();

		        System.out.print("Skriv inn etternavn: ");
		        String etternavn = scanner.next();

		        System.out.print("Skriv inn stilling: ");
		        String stilling = scanner.next();

		        System.out.print("Skriv inn månedslønn: ");
		        int maanedslonn = scanner.nextInt();

		        System.out.print("Skriv inn ansettelsesdato (yyyy-mm-dd): ");
		        String datoStr = scanner.next();
		        LocalDate ansettelsesdato = LocalDate.parse(datoStr);

		        System.out.print("Skriv inn avdeling ID: ");
		        Integer avdelingansatt = scanner.nextInt();

		        leggTilNyAnsatt(brukernavn1, fornavn, etternavn, stilling, maanedslonn, ansettelsesdato, avdelingansatt);

		        System.out.println("Liste over alle ansatte: ");
		        System.out.println(skrivUtAlle());

		        scanner.close();
		    }
		    private static Ansatt finnAnsattMedBrukernavn(String brukernavn) {
		           EntityManager em = emf.createEntityManager();

		           try {
		               TypedQuery<Ansatt> query = em.createQuery(
		                       "SELECT a FROM Ansatt a WHERE a.brukernavn = :brukernavn", Ansatt.class);
		               query.setParameter("brukernavn", brukernavn);

		               return query.getResultList().stream().findFirst().orElse(null);
		           } finally {
		               em.close();
		           }
		       }

		    private static Ansatt finnAnsattMedId(Integer ansattId) {
		        EntityManager em = emf.createEntityManager();
		        
		        try {
		            return em.find(Ansatt.class, ansattId);
		        } finally {
		            em.close();
		        }
		    }
		    private static List<Ansatt> skrivUtAlle() {
		    	EntityManager em = emf.createEntityManager();
		    	
		    	try {
		    		TypedQuery<Ansatt> query = em.createQuery(
		    				"SELECT a FROM Ansatt a", Ansatt.class);
		    		return query.getResultList();
		    	}finally {
		    		em.close();
		    	}
		    }
		    
		    private static void oppdaterAnsatt(Integer ansattId, String nyStilling, int nyLonn) {
		        System.out.println("Kobler til database...");
		        EntityManager em = emf.createEntityManager();
		        
		        try {
		            em.getTransaction().begin(); 
		            
		            Ansatt ansatt = em.find(Ansatt.class, ansattId);
		            
		            if (ansatt != null) {
		                ansatt.setStilling(nyStilling);
		                ansatt.setMaanedslonn(nyLonn);
		                
		                em.getTransaction().commit(); 
		                System.out.println(ansatt + " ble oppdatert!");
		            } else {
		                System.out.println("Ansatt med ID " + ansattId + " ble ikke funnet.");
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
		    
		    private static void leggTilNyAnsatt(String brukernavn, String fornavn, String etternavn, String stilling, int maanedslonn, LocalDate ansettelsesdato, Integer avdelingId) {
		        System.out.println("Kobler til database...");
		        EntityManager em = emf.createEntityManager();
		        
		        try {
		            em.getTransaction().begin(); 
		            
		           
		            TypedQuery<Ansatt> query = em.createQuery("SELECT a FROM Ansatt a WHERE a.brukernavn = :brukernavn", Ansatt.class);
		            query.setParameter("brukernavn", brukernavn);
		            List<Ansatt> eksisterendeAnsatte = query.getResultList();
		            
		            if (eksisterendeAnsatte.isEmpty()) {
		
		                Avdeling avdeling = em.find(Avdeling.class, avdelingId);
		                
		                if (avdeling == null) {
		                    System.out.println("Avdeling med ID " + avdelingId + " finnes ikke.");
		                    em.getTransaction().rollback();
		                    return;
		                }
		               
		                Ansatt nyAnsatt = new Ansatt();
		                nyAnsatt.setBrukernavn(brukernavn);
		                nyAnsatt.setFornavn(fornavn);
		                nyAnsatt.setEtternavn(etternavn);
		                nyAnsatt.setStilling(stilling);
		                nyAnsatt.setMaanedslonn(maanedslonn);
		                nyAnsatt.setAnsettelsesdato(ansettelsesdato); 
		                nyAnsatt.setAvdelingAnsatt(avdeling);  
		                
		                em.persist(nyAnsatt); 
		                em.getTransaction().commit(); 
		                System.out.println("Ny ansatt med brukernavn " + brukernavn + " ble lagt til.");
		            } else {
		                System.out.println("En ansatt med brukernavn " + brukernavn + " eksisterer allerede.");
		                em.getTransaction().rollback(); 
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

		}