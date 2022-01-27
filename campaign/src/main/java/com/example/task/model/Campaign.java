package com.example.task.model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table
public class Campaign {

    @Id
    @SequenceGenerator(
            name = "campaign_sequence",
            sequenceName = "campaign_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "campaign_sequence"
    )
    private long id;
    @Column(unique = true)
    @NotEmpty(message = "Campaign name cannot be empty or null")
    private String name;
    @Min(value = minBidAmount, message = "Minimal bid amount is " + minBidAmount)
    private int bidAmount;
    private final static int minBidAmount = 2000;
    private int fund;
    private boolean status;
    @NotNull(message = "Town cannot be null")
    private Town town;
    private int radiusKm;

    public Campaign(String name, int bidAmount, int fund, boolean status, Town town, int radiusKm) {
        setName(name);
        setBidAmount(bidAmount);
        setFund(fund);
        setStatus(status);
        setTown(town);
        setRadiusKm(radiusKm);
    }

    public Campaign() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBidAmount() {
        return bidAmount;
    }

    public void setBidAmount(int bidAmount) {
        if (bidAmount < minBidAmount)
            throw new IllegalArgumentException("Bid amount must be at least " + minBidAmount);
        this.bidAmount = bidAmount;
    }

    public static int getMinBidAmount() {
        return minBidAmount;
    }

    public int getFund() {
        return fund;
    }

    public void setFund(int fund) {
        this.fund = fund;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Town getTown() {
        return town;
    }

    public void setTown(Town town) {
        if (town == null) {
            throw new IllegalArgumentException("Town cannot be null");
            //System.out.println("Town cannot be null");
        }
        this.town = town;
    }

    public int getRadiusKm() {
        return radiusKm;
    }

    public void setRadiusKm(int radiusKm) {
        this.radiusKm = radiusKm;
    }

    @Override
    public String toString() {
        return "Campaign{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", bidAmount=" + bidAmount +
                ", fund=" + fund +
                ", status=" + status +
                ", town=" + town +
                ", radiusKm=" + radiusKm +
                '}';
    }
}
