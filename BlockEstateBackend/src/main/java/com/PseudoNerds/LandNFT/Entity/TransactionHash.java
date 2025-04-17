package com.PseudoNerds.LandNFT.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Transactions")
@AllArgsConstructor
@NoArgsConstructor
public class TransactionHash {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long id;
    private String TransactionHash;
    private  String propertyId;

    public TransactionHash(String propertyId, String transactionHash) {
        this.propertyId = propertyId;
        TransactionHash = transactionHash;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public String getTransactionHash() {
        return TransactionHash;
    }

    public void setTransactionHash(String transactionHash) {
        TransactionHash = transactionHash;
    }
}
