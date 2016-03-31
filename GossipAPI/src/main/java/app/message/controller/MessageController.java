package app.message.controller;

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

import app.message.dao.Message;
import app.message.dao.MessageRepository;

@RestController
@RequestMapping("/gossip/api/v1/messages")
public class MessageController {

	@Autowired
	private MessageRepository messageRepository;

	/* Create new message */
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<Message> createMessage(@RequestBody Message message,
			UriComponentsBuilder uriComponentsBuilder) {
		messageRepository.save(message);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(
				uriComponentsBuilder.path("gossip/api/v1/messages/{id}").buildAndExpand(message.getId()).toUri());
		return new ResponseEntity<Message>(message, headers, HttpStatus.CREATED);
	}

	/* Get all messages */
	@RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Iterable<Message>> getAllMessages() {
		Iterable<Message> messages = messageRepository.findAll();
		if (messages == null) {
			return new ResponseEntity<Iterable<Message>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Iterable<Message>>(messages, HttpStatus.OK);
	}

	/* Get single message by Id */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getMessage(@PathVariable("id") long id) {
		Message message = messageRepository.findOne(id);
		if (message == null) {
			return new ResponseEntity<Message>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Message>(message, HttpStatus.OK);
	}

	/* Delete single message by Id */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> deleteMessage(@PathVariable("id") long id) {
		Message message = messageRepository.findOne(id);
		if (message == null) {
			return new ResponseEntity<Message>(HttpStatus.NOT_FOUND);
		}
		messageRepository.delete(id);
		return new ResponseEntity<Message>(message, HttpStatus.OK);
	}
}
