package com.PseudoNerds.LandNFT.Repository;

import com.PseudoNerds.LandNFT.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Set<Optional<User>> getUserByownerWalletAddress(String ownerWalletAddress);

    Optional<User> findByownerWalletAddress(String ownerWalletAddress);

    Optional<User> getUserByusername(String username);

    User findByusername(String username);
}
