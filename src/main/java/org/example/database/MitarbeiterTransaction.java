package org.example.database;

import org.example.ServerService;
import org.example.entities.LoginData;
import org.example.entities.Mitarbeiter;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.File;
import java.util.List;

public class MitarbeiterTransaction {


    public static Mitarbeiter getMitarbeiterByPersonalNummer(int personalNummer) {
        try (Session session = ServerService.getSessionFactory().openSession()) {
            return session.get(Mitarbeiter.class, personalNummer);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Mitarbeiter> fetchAllMitarbeiterForTable() {
       try (Session session = ServerService.getSessionFactory().openSession()) {
           return session.createQuery("FROM Mitarbeiter", Mitarbeiter.class).list();
        } catch (Exception e) {
            e.printStackTrace();
           return null;
        }

    }

    public static void deleteSingleMitarbeiter(int personalNummer) {
        try (Session session = ServerService.getSessionFactory().openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();

                Mitarbeiter mitarbeiter = new Mitarbeiter();
                mitarbeiter.setPersonalNummer(personalNummer);
                session.remove(mitarbeiter);

                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateProfilePicture(Mitarbeiter mitarbeiter, String fileName, String appPath) {
        try (Session session = ServerService.getSessionFactory().openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();

                if (!mitarbeiter.getProfilePicture().equals("../resources/img/avatars/default.jpeg")) {

                    String oldPicPath = appPath + File.separator + "resources" + File.separator + "img" + File.separator + "avatars" + File.separator + mitarbeiter.getProfilePicture();

                    File oldPicFile = new File(oldPicPath);
                    if (oldPicFile.exists()) {
                        oldPicFile.delete();
                    }
                }

                mitarbeiter.setProfilePicture("../resources/img/avatars/" + fileName);

                session.merge(mitarbeiter);


                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static void addMitarbeiter(Mitarbeiter mitarbeiter) {
        try (Session session = ServerService.getSessionFactory().openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();

                session.persist(mitarbeiter);

                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateMitarbeiter(Mitarbeiter mitarbeiter) {
        try (Session session = ServerService.getSessionFactory().openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();

                session.merge(mitarbeiter);

                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteLoginDataAndTokens(int personalNummer) {
        try (Session session = ServerService.getSessionFactory().openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();

                Query<LoginData> query = session.createNativeQuery("DELETE FROM LoginData WHERE mitarbeiter_personalNummer = :personalnummer", LoginData.class);
                query.setParameter("personalnummer", personalNummer);
                query.executeUpdate();

                    transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateWeekHoursProgressAndVacationDays(int geleisteteStunden, int leftVacation, Mitarbeiter mitarbeiter) {
        try (Session session = ServerService.getSessionFactory().openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();

                mitarbeiter.setWeekHoursProgress(geleisteteStunden);
                mitarbeiter.setVerbleibendeUrlaubstage(leftVacation);
                session.merge(mitarbeiter);

                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Mitarbeiter> fetchAllMitarbeiterForMitarbeiterRefresh() {
        try (Session session = ServerService.getSessionFactory().openSession()) {
            return session.createQuery("FROM Mitarbeiter m JOIN FETCH m.days", Mitarbeiter.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
