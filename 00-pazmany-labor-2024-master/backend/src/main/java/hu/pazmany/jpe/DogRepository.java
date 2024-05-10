package hu.pazmany.jpe;

import hu.pazmany.dto.DogDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DogRepository extends JpaRepository<DogEntity, Integer> {
    @Query("SELECT new hu.pazmany.dto.DogDTO(d.id, d.name, d.picture) FROM DogEntity d")
    List<DogDTO> findAllDogs();
}