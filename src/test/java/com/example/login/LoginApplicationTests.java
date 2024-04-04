package com.example.login;

import com.example.login.repository.BoardRepository;
import com.example.login.repository.CategoryRepository;
import com.example.login.repository.CommentRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Commit;

import static org.assertj.core.api.Assertions.assertThat;


@DataJdbcTest
@SpringBootTest
@Commit
class LoginApplicationTests {


	@Autowired
	CategoryRepository categoryRepository;

	@PersistenceContext EntityManager em;


	@Autowired
	CommentRepository commentRepository;
	@Autowired
	BoardRepository boardRepository;

/*	@PersistenceContext
	EntityManager em;*/

	@Test
	void status_check_return_ok() {
		TestRestTemplate rest = new TestRestTemplate();
		ResponseEntity<String> res = rest.getForEntity("http://localhost:8080/status", String.class);

		assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(res.getBody()).isEqualTo("ok");
	}

 	}