package com.example.model;

import java.time.LocalDate;
import java.math.BigDecimal;

public class Appointment {
    private int id;
    private String name;
    private LocalDate date;
    private String service;
    private String time;
    private String barber;
    private String addons;
    private BigDecimal price;

    public Appointment() {}

    public Appointment(int id, String name, LocalDate date, String service, String time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.service = service;
        this.time = time;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getService() { return service; }
    public void setService(String service) { this.service = service; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public String getBarber() { return barber; }
    public void setBarber(String barber) { this.barber = barber; }

    public String getAddons() { return addons; }
    public void setAddons(String addons) { this.addons = addons; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", service='" + service + '\'' +
                ", time='" + time + '\'' +
                ", barber='" + barber + '\'' +
                ", addons='" + addons + '\'' +
                ", price=" + price +
                '}';
    }
}
