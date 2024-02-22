package org.example;

import org.example.database.MitarbeiterDAO;
import org.example.database.MessageDAO;
import org.example.entities.Mitarbeiter;
import org.example.entities.Message;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.time.LocalDate;
import java.time.DayOfWeek;

public class MessageCreater implements Runnable {

    @Override
    public void run() {
        try {
            //Lösche alle Abgelaufenen Nachrichten
            MessageDAO.deleteAllExpiredMessages();


            // Hole alle Mitarbeiter aus der Datenbank
            List<Mitarbeiter> mitarbeiters = MitarbeiterDAO.fetchAllMitarbeiterForTable();
            List<Message> AdminMessages = new ArrayList<Message>();
            List<Message> MitarbeiterMessages = new ArrayList<Message>();

            List<Message> messages = MessageDAO.fetchAllMessages();



            LocalDate today = LocalDate.now();

            assert mitarbeiters != null;
            for (Mitarbeiter mitarbeiter : mitarbeiters) {
                // Wenn der Mitarbeiter weniger als 50% seiner Wochenstunden erreicht hat und heute Donnerstag, Freitag oder Samstag ist
                if ((today.getDayOfWeek() == DayOfWeek.THURSDAY || today.getDayOfWeek() == DayOfWeek.FRIDAY || today.getDayOfWeek() == DayOfWeek.SATURDAY) && mitarbeiter.getWeekHoursProgress() < 50) {

                    // Erstelle eine neue Nachricht für den Mitarbeiter
                    Message messageForMitarbeiter = new Message();
                    messageForMitarbeiter.setStatus("warning");
                    messageForMitarbeiter.setDatum(today);
                    messageForMitarbeiter.setMessage("<strong>Dies ist eine Warnung – es wird knapp: </strong><br /> Du hast bisher nur die Hälfte deiner Wochenstunden erreicht. Bitte überprüfe und optimiere deine Arbeitszeiten, um dein Wochenziel zu erreichen.");
                    messageForMitarbeiter.setMitarbeiter(mitarbeiter);
                    MitarbeiterMessages.add(messageForMitarbeiter);

                    // Erstelle eine neue Nachricht für den Administrator
                    Message messageForAdmin = new Message();
                    messageForAdmin.setStatus("warning");
                    messageForAdmin.setDatum(today);
                    messageForAdmin.setMessage("<strong>Unvollständige Arbeitsstundenwarnung</strong><br /> Mitarbeiter" + mitarbeiter.getName() + " " + mitarbeiter.getVorname() + " (Personalnummer: " + mitarbeiter.getPersonalNummer() + ") hat weniger als 50% Wochenarbeitsstunden erreicht.");
                    messageForAdmin.setMitarbeiter(mitarbeiter);
                    AdminMessages.add(messageForAdmin);
                }
                if (today.getDayOfWeek() == DayOfWeek.SUNDAY && mitarbeiter.getWeekHoursProgress() < 100) {

                    Message messageForMitarbeiter = new Message();
                    messageForMitarbeiter.setStatus("danger");
                    messageForMitarbeiter.setDatum(today);
                    messageForMitarbeiter.setMessage("<strong>Nicht erreichtes Wochenstundenziel:</strong><br /> Hallo " + mitarbeiter.getVorname() + ", Sie haben Ihr Wochenstundenziel nicht erreicht. Wir erwarten eine Erklärung dafür.");

                    messageForMitarbeiter.setMitarbeiter(mitarbeiter);
                    MitarbeiterMessages.add(messageForMitarbeiter);



                    Message messageForAdmin = new Message();
                    messageForAdmin.setStatus("danger");
                    messageForAdmin.setDatum(today);
                    messageForAdmin.setMessage("<strong>Wochenstundenziel nicht erreicht - Handlungsbedarf</strong><br />Mitarbeiter " + mitarbeiter.getVorname() + " " + mitarbeiter.getName() + " (Personalnummer: " + mitarbeiter.getPersonalNummer() + ") hat sein Wochenstundenziel nicht erreicht. Es wird erwartet, dass entsprechende Maßnahmen ergriffen werden.");
                    messageForAdmin.setMitarbeiter(mitarbeiter);
                    AdminMessages.add(messageForAdmin);
                }

                if (mitarbeiter.getWeekHoursProgress() >= 100) {
                    Message messageForMitarbeiter = new Message();
                    messageForMitarbeiter.setStatus("success");
                    messageForMitarbeiter.setDatum(today);
                    messageForMitarbeiter.setMessage("<strong>Ziel erreicht:</strong><br />Herzlichen Glückwunsch! Sie haben Ihr Wochenstundenziel erreicht.");
                    messageForMitarbeiter.setMitarbeiter(mitarbeiter);
                    MitarbeiterMessages.add(messageForMitarbeiter);
                }
            }

            if (!AdminMessages.isEmpty()) {
                MessageDAO.saveAllMessagesToAdmins(AdminMessages, messages);
            }
            if (!MitarbeiterMessages.isEmpty()) {
                MessageDAO.saveAllMessages(MitarbeiterMessages, messages);
            }

            UpdateMessage.RefreshMessage();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}