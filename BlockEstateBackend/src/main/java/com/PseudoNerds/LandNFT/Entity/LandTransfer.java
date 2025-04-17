package com.PseudoNerds.LandNFT.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "LandTransfer")
public class LandTransfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String propertyId;

    private Long tokenId;
    private String fromAddress;
    private String toAddress;

    @Column(nullable = true)
    private LocalDateTime transferTimestamp;

    public LandTransfer(String fromAddress, Long id, String toAddress, Long tokenId, LocalDateTime transferTimestamp) {
        this.fromAddress = fromAddress;
        this.id = id;
        this.toAddress = toAddress;
        this.tokenId = tokenId;
        this.transferTimestamp = transferTimestamp;
    }

    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public Long getTokenId() {
        return tokenId;
    }

    public void setTokenId(Long tokenId) {
        this.tokenId = tokenId;
    }

    public LocalDateTime getTransferTimestamp() {
        return transferTimestamp;
    }

    public void setTransferTimestamp(LocalDateTime transferTimestamp) {
        this.transferTimestamp = transferTimestamp;
    }
}

