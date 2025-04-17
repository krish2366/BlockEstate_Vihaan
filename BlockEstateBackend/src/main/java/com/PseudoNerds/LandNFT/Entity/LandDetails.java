package com.PseudoNerds.LandNFT.Entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Table(name = "Land")
public class LandDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//@Column(nullable = true)
    private String propertyId;

    @Column(nullable = true)
    private String locationDescription;
    private String latitudeLongitude;
    private long areaSqMeters;
    private String ownerWalletAddress;
    @Column(nullable = true)
    private String tokenURI;
//    @Column(nullable = true)
//    private Long tokenId;
    private LocalDateTime registrationDate;

    public LandDetails(long areaSqMeters, Long id, String latitudeLongitude, String ownerWalletAddress, String propertyId, LocalDateTime registrationDate, Long tokenId, String tokenURI) {
        this.areaSqMeters = areaSqMeters;
        this.id = id;
        this.latitudeLongitude = latitudeLongitude;
        this.ownerWalletAddress = ownerWalletAddress;
        this.propertyId = propertyId;
        this.registrationDate = registrationDate;
//        this.tokenId = tokenId;
        this.tokenURI = tokenURI;
    }

    public long getAreaSqMeters() {
        return areaSqMeters;
    }

    public void setAreaSqMeters(long areaSqMeters) {
        this.areaSqMeters = areaSqMeters;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLatitudeLongitude() {
        return latitudeLongitude;
    }

    public void setLatitudeLongitude(String latitudeLongitude) {
        this.latitudeLongitude = latitudeLongitude;
    }

    public String getLocationDescription() {
        return locationDescription;
    }

    public void setLocationDescription(String locationDescription) {
        this.locationDescription = locationDescription;
    }

    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public String getOwnerWalletAddress() {
        return ownerWalletAddress;
    }

    public void setOwnerWalletAddress(String ownerWalletAddress) {
        this.ownerWalletAddress = ownerWalletAddress;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

//    public Long getTokenId() {
//        return tokenId;
//    }
//
//    public void setTokenId(Long tokenId) {
//        this.tokenId = tokenId;
//    }

    public String getTokenURI() {
        return tokenURI;
    }

    public void setTokenURI(String tokenURI) {
        this.tokenURI = tokenURI;
    }
}
