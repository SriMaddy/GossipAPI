package app.user.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import app.user.dao.User;
import app.user.dao.UserRepository;

@RestController
@RequestMapping("/gossip/api/v1/users")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	/* Create new user */
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<User> createUser(@RequestBody User user, UriComponentsBuilder uriComponentsBuilder) {
		userRepository.save(user);
		// HttpHeaders headers = new HttpHeaders();
		// headers.setLocation(
		// uriComponentsBuilder.path("gossip/api/v1/users/{id}").buildAndExpand(user.getId()).toUri());
		return new ResponseEntity<User>(user, HttpStatus.CREATED);
	}

	/* Get all users */
	@RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Iterable<User>> getAllUsers() {
		Iterable<User> users = userRepository.findAll();
		if (users == null) {
			return new ResponseEntity<Iterable<User>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Iterable<User>>(users, HttpStatus.OK);
	}

	/* Get all users by specific location */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/locations/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<User>> getAllUsersByLocationId(@PathVariable("id") long locationId) {
		Iterable<User> users = userRepository.findAll();

		List<User> usersList = new ArrayList<>();
		Iterator<User> iterator = users.iterator();

		while (iterator.hasNext()) {
			usersList.add(iterator.next());
		}

		if (users != null) {
			for (User user : usersList) {
				if (user.getLocationId() == locationId) {
					usersList.remove(user);
				}
			}
			return new ResponseEntity<List<User>>(usersList, HttpStatus.OK);
		}
		return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);
	}

	/* Get single user by Id */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> getUser(@PathVariable("id") long id) {
		User user = userRepository.findOne(id);
		if (user == null) {
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	/* Delete single user by Id */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> deleteUser(@PathVariable("id") long id) {
		User user = userRepository.findOne(id);
		if (user == null) {
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
		userRepository.delete(id);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
}
