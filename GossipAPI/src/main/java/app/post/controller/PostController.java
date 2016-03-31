package app.post.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

import app.post.dao.Post;
import app.post.dao.PostRepository;

@RestController
@RequestMapping("/gossip/api/v1/posts")
public class PostController {

	@Autowired
	private PostRepository postRepository;

	/* Create new post */
	@RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Post> createPost(@RequestBody Post post, UriComponentsBuilder uriComponentsBuilder) {
		postRepository.save(post);
//		HttpHeaders headers = new HttpHeaders();
//		headers.setLocation(uriComponentsBuilder.path("gossip/api/v1/posts/{id}").buildAndExpand(post.getId()).toUri());
		return new ResponseEntity<Post>(post, HttpStatus.CREATED);
	}

	/* Get all posts */
	@RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Iterable<Post>> getAllPosts() {
		Iterable<Post> posts = postRepository.findAll();
		if (posts == null) {
			return new ResponseEntity<Iterable<Post>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Iterable<Post>>(posts, HttpStatus.OK);
	}

	/* Get all posts by specific location */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/locations/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Post>> getAllPostsByLocationId(@PathVariable("id") long locationId) {
		Iterable<Post> posts = postRepository.findAll();

		List<Post> postsList = new ArrayList<>();
		Iterator<Post> iterator = posts.iterator();

		while (iterator.hasNext()) {
			postsList.add(iterator.next());
		}

		if (posts != null) {
			for (Post post : postsList) {
				if (post.getLocationId() != locationId) {
					postsList.remove(post);
				}
			}
			return new ResponseEntity<List<Post>>(postsList, HttpStatus.OK);
		}
		return new ResponseEntity<List<Post>>(HttpStatus.NO_CONTENT);
	}

	/* Get all posts by specific user */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Post>> getAllPostsByUserId(@PathVariable("id") long userId) {
		Iterable<Post> posts = postRepository.findAll();

		List<Post> postsList = new ArrayList<>();
		Iterator<Post> iterator = posts.iterator();

		while (iterator.hasNext()) {
			postsList.add(iterator.next());
		}

		if (posts != null) {
			for (Post post : postsList) {
				if (post.getUserId() != userId) {
					postsList.remove(post);
				}
			}
			return new ResponseEntity<List<Post>>(postsList, HttpStatus.OK);
		}
		return new ResponseEntity<List<Post>>(HttpStatus.NO_CONTENT);
	}
}
