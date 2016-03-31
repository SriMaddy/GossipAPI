package app.circle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import app.circle.dao.Circle;
import app.circle.dao.CircleRepository;

@RestController
@RequestMapping("/gossip/api/v1/circles")
public class CircleController {

	@Autowired
	private CircleRepository circleRepository;

	/* Create new circle */
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<Circle> createCircle(@RequestBody Circle circle, UriComponentsBuilder uriComponentsBuilder) {
		circleRepository.save(circle);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(
				uriComponentsBuilder.path("gossip/api/v1/circles/{id}").buildAndExpand(circle.getId()).toUri());
		return new ResponseEntity<Circle>(circle, headers, HttpStatus.CREATED);
	}

	/* Get all circles */
	@RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Iterable<Circle>> getAllCircles() {
		Iterable<Circle> circles = circleRepository.findAll();
		if (circles == null) {
			return new ResponseEntity<Iterable<Circle>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Iterable<Circle>>(circles, HttpStatus.OK);
	}

	/* Get single circle by Id */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Circle> getCircle(@PathVariable("id") long id) {
		Circle circle = circleRepository.findOne(id);
		if (circle == null) {
			return new ResponseEntity<Circle>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Circle>(circle, HttpStatus.OK);
	}

	/* Delete single circle by Id */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Circle> deleteUser(@PathVariable("id") long id) {
		Circle circle = circleRepository.findOne(id);
		if (circle == null) {
			return new ResponseEntity<Circle>(HttpStatus.NOT_FOUND);
		}
		circleRepository.delete(id);
		return new ResponseEntity<Circle>(circle, HttpStatus.OK);
	}
}
