package hu.pazmany.service;

import hu.pazmany.dto.DetailedDogDTO;
import hu.pazmany.dto.DogDTO;
import hu.pazmany.jpe.DogEntity;
import hu.pazmany.jpe.DogRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DogService {
    private DogRepository dogRepository;

    public List<DogDTO> getAllDogs() {
        return dogRepository.findAllDogs();
    }

    public Optional<DetailedDogDTO> get(Integer id) {
        Optional<DogEntity> dogEntityOptional = dogRepository.findById(id);

        return dogEntityOptional.map(dogEntity -> new DetailedDogDTO(dogEntity.getId(), dogEntity.getName(),
                dogEntity.getPicture(), dogEntity.getAge(), dogEntity.getBreed()));
    }

    public void addNewDog(DetailedDogDTO dto) {
        DogEntity newDog = new DogEntity();
        newDog.setId(dogRepository.findAllDogs().size());
        newDog.setName(dto.getName());
        newDog.setPicture(dto.getPicture());
        newDog.setBreed(dto.getBreed());
        newDog.setAge(dto.getAge());
        dogRepository.save(newDog);
    }

    public void editDog(Integer id, DetailedDogDTO editRequest) {
        Optional<DogEntity> optionalDog = dogRepository.findById(id);

        if (optionalDog.isPresent()) {
            DogEntity dogEntity = optionalDog.get();

            // Update only non-null fields from editRequest
            if (editRequest.getName() != null) {
                dogEntity.setName(editRequest.getName());
            }
            if (editRequest.getPicture() != null) {
                dogEntity.setPicture(editRequest.getPicture());
            }
            if (editRequest.getAge() != null) {
                dogEntity.setAge(editRequest.getAge());
            }
            if (editRequest.getBreed() != null) {
                dogEntity.setBreed(editRequest.getBreed());
            }

            // Save the updated entity
            dogRepository.save(dogEntity);
        }
    }

    public void deleteDog(Integer id) {
        dogRepository.deleteById(id);
    }
}