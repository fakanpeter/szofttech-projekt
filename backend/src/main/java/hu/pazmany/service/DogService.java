package hu.pazmany.service;

import hu.pazmany.dto.DetailedDogDTO;
import hu.pazmany.dto.DogDTO;
import hu.pazmany.jpe.DogEntity;
import hu.pazmany.jpe.DogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class DogService {
    private final DogRepository dogRepository;

    @Autowired
    public DogService(DogRepository dogRepository) {
        this.dogRepository = dogRepository;
    }

    public List<DogDTO> getAllDogs() {
        return dogRepository.findAllDogs();
    }

    public Optional<DetailedDogDTO> get(Integer id) {
        Optional<DogEntity> dogEntityOptional = dogRepository.findById(id);

        return dogEntityOptional.map(dogEntity -> new DetailedDogDTO(dogEntity.getId(), dogEntity.getName(),
                dogEntity.getPicture(), dogEntity.getAge(), dogEntity.getBreed()));
    }

    public void addNewDog(DetailedDogDTO newDogRequest, MultipartFile mpf) throws IOException {
        DogEntity dogEntity = new DogEntity();

        // Set fields from newDogRequest
        dogEntity.setName(newDogRequest.getName());
        dogEntity.setBreed(newDogRequest.getBreed());
        dogEntity.setAge(newDogRequest.getAge());
        if (mpf != null && !mpf.isEmpty()) {
            dogEntity.setPicture(mpf.getBytes());
        }

        // Save the new entity
        dogRepository.save(dogEntity);
    }

    public void editDog(Integer id, DetailedDogDTO editRequest, MultipartFile mpf) throws IOException {
        Optional<DogEntity> optionalDog = dogRepository.findById(id);
        if (optionalDog.isPresent()) {
            DogEntity dogEntity = optionalDog.get();

            // Update only non-null fields from editRequest
            if (editRequest.getName() != null) {
                dogEntity.setName(editRequest.getName());
            }
            if (mpf != null) {
                mpf.getBytes();
                dogEntity.setPicture(mpf.getBytes());
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