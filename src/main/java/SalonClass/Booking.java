package SalonClass;

public class Booking {
    private int orderId;
    private String serviceName;
    private String serviceType;
    private String masterName;
    private String clientName;
    private String clientEmail;
    private String confirmationStatus;

    private String time; // Добавляем поле для времени

    // Конструктор и геттеры/сеттеры
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    public Booking(int orderId, String serviceName, String serviceType,
                   String masterName, String clientName, String clientEmail,
                   String confirmationStatus, String time) {
        this.orderId = orderId;
        this.serviceName = serviceName;
        this.serviceType = serviceType;
        this.masterName = masterName;
        this.clientName = clientName;
        this.clientEmail = clientEmail;
        this.confirmationStatus = confirmationStatus;
        this.time = time;
    }

    // Геттеры
    public int getOrderId() { return orderId; }
    public String getServiceName() { return serviceName; }
    public String getServiceType() { return serviceType; }
    public String getMasterName() { return masterName; }
    public String getClientName() { return clientName; }
    public String getClientEmail() { return clientEmail; }
    public String getConfirmationStatus() { return confirmationStatus; }
}