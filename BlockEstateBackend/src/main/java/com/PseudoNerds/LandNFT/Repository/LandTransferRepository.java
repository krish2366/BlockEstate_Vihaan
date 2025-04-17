package com.PseudoNerds.LandNFT.Repository;

import com.PseudoNerds.LandNFT.Entity.LandTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LandTransferRepository extends JpaRepository<LandTransfer,Long> {
}
