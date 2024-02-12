package org.example;

public class Alert {

    String alertType;
    String alertTitle;
    String alertMessage;
    String alertIcon;

    public Alert(String alertType, String alertTitle, String alertMessage, String alertIcon) {
        this.alertType = alertType;
        this.alertTitle = alertTitle;
        this.alertMessage = alertMessage;
        this.alertIcon = alertIcon;
    }

    public static Alert dangerAlert(String alertTitle, String alertMessage) {
        return new Alert("danger", alertTitle, alertMessage, "exclamation-triangle");
    }

    public static Alert successAlert(String alertTitle, String alertMessage) {
        return new Alert("success", alertTitle, alertMessage, "check-circle");
    }

    public String getAlertType() {
        return alertType;
    }

    public String getAlertTitle() {
        return alertTitle;
    }

    public String getAlertMessage() {
        return alertMessage;
    }

    public String getAlertIcon() {
        return alertIcon;
    }
}
