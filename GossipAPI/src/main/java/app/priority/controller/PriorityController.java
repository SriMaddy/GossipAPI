package app.priority.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import app.priority.dao.Priority;
import app.priority.dao.PriorityRepository;

@RestController
@RequestMapping("/gossip/api/v1/priorities")
public class PriorityController {

	@Autowired
	private PriorityRepository priorityRepository;

	/* Create new priority */
	@RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Priority> createPriority(@RequestBody Priority priority,
			UriComponentsBuilder uriComponentsBuilder) {
		priorityRepository.save(priority);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(
				uriComponentsBuilder.path("gossip/api/v1/priorities/{id}").buildAndExpand(priority.getId()).toUri());
		return new ResponseEntity<Priority>(priority, headers, HttpStatus.CREATED);
	}
	
	/* Get all priorities */ 
	@RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Iterable<Priority>> getAllPriorities() {
		Iterable<Priority> priorities = priorityRepository.findAll();
		if (priorities == null) {
			return new ResponseEntity<Iterable<Priority>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Iterable<Priority>>(priorities, HttpStatus.OK);
	}

}
