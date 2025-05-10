package SalonClass;

public class Service {
    private int idService;
    private String NAME;
    private String TYPE;
    private String FIOMASTER;
    private String TIME;
    private String PRICE;

    // Конструктор
    public Service(int idService, String NAME, String TYPE, String FIOMASTER, String TIME, String PRICE) {
        this.idService = idService;
        this.NAME = NAME;
        this.TYPE = TYPE;
        this.FIOMASTER = FIOMASTER;
        this.TIME = TIME;
        this.PRICE = PRICE;
    }

    // Геттеры
    public int getIdService() { return idService; }
    public String getNAME() { return NAME; }
    public String getTYPE() { return TYPE; }
    public String getFIOMASTER() { return FIOMASTER; }
    public String getTIME() { return TIME; }
    public String getPRICE() { return PRICE; }

    // Сеттеры (если необходимо)
    public void setIdService(int idService) { this.idService = idService; }
    public void setNAME(String NAME) { this.NAME = NAME; }
    public void setTYPE(String TYPE) { this.TYPE = TYPE; }
    public void setFIOMASTER(String FIOMASTER) { this.FIOMASTER = FIOMASTER; }
    public void setTIME(String TIME) { this.TIME = TIME; }
    public void setPRICE(String PRICE) { this.PRICE = PRICE; }
}
