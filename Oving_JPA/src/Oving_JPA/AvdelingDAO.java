package Oving_JPA;

import java.util.List;
import java.util.Scanner;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

public class AvdelingDAO {
	
	 private static EntityManagerFactory emf;
	 
	   static {
	        emf = Persistence.createEntityManagerFactory("OvingJPA-PU");
	    }
	   
	   public static void main(String[]args) {
		   
		   Scanner scanner = new Scanner(System.in);
		   
		   System.out.println("Skriv inn avdelingens ID: ");
		   Integer avdelingid = scanner.nextInt();
		   
		      System.out.println("Kobler til database...");
		      
		      System.out.println("Skriv inn hvilken avdeling en ansatt skal flyttes til (først avd, så ansatt)");
		      Integer avdeling = scanner.nextInt();
		      scanner.nextLine();
		      
		      String nyavdeling = scanner.nextLine();
		      String ansatt1 = scanner.nextLine();
		      oppdaterAvdeling(avdeling, nyavdeling, ansatt1);
		      
		      System.out.println("Legg til avdeling");
		      String avdnavn = scanner.nextLine();
		      Integer ansatt2 = scanner.nextInt();
		      leggTilAvdeling(avdnavn, ansatt2);
		      
		    	
		      scanner.close();
		        if (avdelingid != null)
					System.out.println("Avdeling funnet: " + avdelingid);
	
		        
		        List<Ansatt> ansatte = hentAnsattePåAvdeling(avdelingid);

		        if (ansatte != null && !ansatte.isEmpty()) {
		            System.out.println("Ansatte i avdelingen:");
		            for (Ansatt ansatt : ansatte) {
		                if (ansatt.getAvdelingAnsatt().getAnsattSjef().getAnsattId() == ansatt.getAnsattId()) {
		                    System.out.println("* " + ansatt.getFornavn() + ansatt.getEtternavn() + " (Sjef)");
		                } else {
		                    System.out.println("* " + ansatt.getFornavn() + ansatt.getEtternavn());
		                }
		            }
		        } else {
		            System.out.println("Ingen ansatte funnet i denne avdelingen.");
		        }
		        
		        System.out.println("Liste over alle avdelinger med sjef:");
		        System.out.println(skrivUtAlle());
		        
		        
	   }
		   
	
	public static Avdeling finnAvdelingMedID(int avdelingid) {
		
		 EntityManager em = emf.createEntityManager();
		
		try {
			return em.find(Avdeling.class, avdelingid);
		} finally { 
			em.close();
		}
	}
    private static List<Avdeling> skrivUtAlle() {
    	EntityManager em = emf.createEntityManager();
    	
    	try {
    		TypedQuery<Avdeling> query = em.createQuery(
    				"SELECT a FROM Avdeling a", Avdeling.class);
    		return query.getResultList();
    	}finally {
    		em.close();
    	}
    }
    
    public static List<Ansatt> hentAnsattePåAvdeling(int avdelingid) {
    	   EntityManager em = emf.createEntityManager();
    	    try {
    	        String queryStr = "SELECT a FROM Ansatt a WHERE a.avdeling.avdelingID = :avdelingid";
    	        TypedQuery<Ansatt> query = em.createQuery(queryStr, Ansatt.class);
    	        query.setParameter("avdelingid", avdelingid);
    	        return query.getResultList();
    	    } finally {
    	        em.close();
    	    }
    }
    
    public static Avdeling oppdaterAvdeling(Integer avdelingid, String nyAvdeling, String ansatt) {
        System.out.println("Kobler til database...");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            Avdeling avdeling = em.find(Avdeling.class, avdelingid);
            if (avdeling == null) {
                System.out.println("Avdeling med ID " + avdelingid + " ble ikke funnet.");
                return null;
            }

            Ansatt sjef = avdeling.getAnsattSjef();
            if (sjef != null && sjef.getFornavn().equalsIgnoreCase(ansatt)) { 
                System.out.println("Ansatt " + ansatt + " er sjef og kan ikke bytte avdeling.");
                return null;
            }

            avdeling.setAvdelingNavn(nyAvdeling);
            em.getTransaction().commit();

            System.out.println("Avdeling " + avdeling.getAvdelingNavn() + " oppdatert for ansatt " + ansatt + "!");
            return avdeling;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }
   
    
    public static Avdeling leggTilAvdeling(String avdelingnavn, Integer sjefid) {
        System.out.println("Kobler til database...");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin(); 

            Ansatt sjef = em.find(Ansatt.class, sjefid);
            if (sjef == null) {
                System.out.println("Ansatt med ID " + sjefid + " ble ikke funnet.");
                return null;
            }

            Query query = em.createQuery("SELECT a FROM Avdeling a WHERE a.ansattSjef.ansattId = :sjefid");
            query.setParameter("sjefid", sjefid);
            @SuppressWarnings("unchecked")
			List<Avdeling> eksisterendeAvdeling = query.getResultList();

            if (!eksisterendeAvdeling.isEmpty()) {
                System.out.println("Ansatt " + sjef.getFornavn() + " er allerede sjef for en annen avdeling.");
                return null;
            }

            Avdeling nyAvdeling = new Avdeling();
            nyAvdeling.setAvdelingNavn(avdelingnavn);
            nyAvdeling.setAnsattSjef(sjef);

            em.persist(nyAvdeling);

            sjef.setAvdelingAnsatt(nyAvdeling);

            em.getTransaction().commit(); 

            System.out.println("Ny avdeling '" + avdelingnavn + "' opprettet med sjef " + sjef.getFornavn() + ".");
            return nyAvdeling;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }
}
