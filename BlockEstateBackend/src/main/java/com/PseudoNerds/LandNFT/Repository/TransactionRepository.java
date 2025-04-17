package com.PseudoNerds.LandNFT.Repository;

import com.PseudoNerds.LandNFT.Entity.TransactionHash;
import jnr.a64asm.OP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionHash,Long> {

    Optional<TransactionHash> findByPropertyId(String propertyId);

}
