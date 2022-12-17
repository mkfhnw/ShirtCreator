package ShirtCreator.Persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository <Address, Integer> {
    Optional<Address> findByStreetAndPlzAndLocation(String street, int pli, String location);
}
