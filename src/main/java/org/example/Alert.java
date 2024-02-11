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
