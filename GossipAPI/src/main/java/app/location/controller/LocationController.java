package app.location.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import app.location.dao.Location;
import app.location.dao.LocationRepository;

@RestController
@RequestMapping("/gossip/api/v1/locations")
public class LocationController {

	@Autowired
	private LocationRepository locationRepository; 

	/* Create new location */
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<Location> createLocation(@RequestBody Location location,
			UriComponentsBuilder uriComponentsBuilder) {
		locationRepository.save(location);
		// HttpHeaders headers = new HttpHeaders();
		// headers.setLocation(
		// uriComponentsBuilder.path("gossip/api/v1/locations/{id}").buildAndExpand(location.getId()).toUri());
		return new ResponseEntity<Location>(location, HttpStatus.CREATED);
	}

	/* Get all locations */
	@RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Iterable<Location>> getAllLocations() {
		Iterable<Location> locations = locationRepository.findAll();
		if (locations == null) {
			return new ResponseEntity<Iterable<Location>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Iterable<Location>>(locations, HttpStatus.OK);
	}
}
