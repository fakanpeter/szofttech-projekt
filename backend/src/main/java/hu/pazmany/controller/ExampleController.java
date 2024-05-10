package hu.pazmany.controller;

import hu.pazmany.dto.DetailedDogDTO;
import hu.pazmany.dto.DogDTO;
import hu.pazmany.service.DogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "https://vau-vau.web.app/")
public class ExampleController {
	private final DogService dogService;

	@Autowired
	public ExampleController(DogService dogService) {
		this.dogService = dogService;
	}

	@GetMapping("/dogs")
	public List<DogDTO> getAllDogs(){
		return dogService.getAllDogs();
	}

	@GetMapping("/dogs/{id}")
	public ResponseEntity<?> getDogById(@PathVariable Integer id) {
		return dogService.get(id)
				.map(dogEntity -> ResponseEntity.ok(new DetailedDogDTO(
						dogEntity.getId(),
						dogEntity.getName(),
						dogEntity.getPicture(),
						dogEntity.getAge(),
						dogEntity.getBreed())))
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping("/newdog")
	public ResponseEntity<?> addNewDog(@RequestBody DetailedDogDTO dto) {
		dogService.addNewDog(dto);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PostMapping("/dogs/{id}/edit")
	public ResponseEntity<?> editDog(@PathVariable Integer id, @RequestBody DetailedDogDTO dto) {
		// Retrieve the dog entity from the database
		Optional<DetailedDogDTO> optionalDog = dogService.get(id);
		if (optionalDog.isPresent()) {

			// Save the updated dog entity
			dogService.editDog(id,dto);

			return ResponseEntity.ok("Dog attributes updated successfully");
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/dogs/{id}")
	public ResponseEntity<?> deleteDog(@PathVariable Integer id) {
		dogService.deleteDog(id);
		return ResponseEntity.ok().build();
	}

}
