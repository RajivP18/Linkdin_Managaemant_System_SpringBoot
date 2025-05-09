package org.jsp.linkedin;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {

	private final LinkedinApplication l;

	@Autowired
	private PersonRepository repo;

	PersonController(LinkedinApplication l) {
		this.l = l;
	}

	@PostMapping("/persons")
	public ResponseStructure<Person> SavePerson(@RequestBody Person person) {
		Person savedperson = repo.save(person);
		ResponseStructure<Person> structure = new ResponseStructure<>();

		structure.setStatus(200);
		structure.setMessage("person saved successfully");
		structure.setBody(savedperson);
		return structure;
	}

//	@GetMapping("/persons)
//	public List<Person> findAllPersons() {
//		return repo.findAll();
//	}
	@GetMapping("/persons")
	public ResponseStructure<List<Person>> findAllPersons() {
		List<Person> all = repo.findAll();
		ResponseStructure<List<Person>> structure = new ResponseStructure<>();

		System.out.println(all.size());

		if (all.isEmpty()) {
			structure.setStatus(404);
			structure.setMessage("no person present in DB");
			structure.setBody(null);
			return structure;

		} else {

			structure.setStatus(200);
			structure.setMessage("All persons found successfully");
			structure.setBody(all);
			return structure;

		}
	}

//	@GetMapping("/persons/{id}")
//	public Person findById(@PathVariable int id) {
//		Optional<Person> optional = repo.findById(id);
//		if (optional.isEmpty()) {
//			System.out.println("Invaid Person id");
//			return null;
//		} else {
//			Person person = optional.get();
//			return person;
//		}
		@GetMapping("/persons/{id}")
		public ResponseStructure<Person> findById(@PathVariable int id) {
			ResponseStructure<Person> os = new ResponseStructure<>();
			Optional<Person> optional = repo.findById(id);
			if (optional.isEmpty()) {
				os.setStatus(404);
				os.setMessage("no person present in DB");
				os.setBody(null);
				return os;
			} else {
				Person person = optional.get();
				os.setStatus(200);
				os.setMessage("All persons found successfully");
				os.setBody(person);
				return os;
				
			}

	}

	@GetMapping("/persons/login")
	public Person login(@RequestBody AuthPerson ap) {
		Optional<Person> optional = repo.login(ap.getEmail(), ap.getPassword());
		if (optional.isEmpty()) {
			System.out.println("Invalid Credentials");
		}
		Person person = optional.get();
		return person;
	}

//	@DeleteMapping("/persons/{id}")
//	public String deletePersonbyId(@PathVariable int id) {
//		Optional<Person> optional = repo.findById(id);
//		if (optional.isPresent()) {
//			repo.deleteById(id);
//			return "Person deleted sucessfully";
//		} else {
//			return "invalid id person";
//		}
//	}
	@DeleteMapping("/persons/{id}")
	public String deletePersonbyId(@PathVariable int id) {
		Optional<Person> optional = repo.findById(id);
		if (optional.isPresent()) {
			repo.deleteById(id);
			return "Person deleted sucessfully";
		} else {
			return "invalid id person";
		}
	}
}