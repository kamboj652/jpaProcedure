package example.springdata.jpa.application;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import example.springdata.jpa.storedprocedures.UserRepository;

@EnableJpaRepositories("example.springdata.jpa.storedprocedures") // JPA repositories
@EntityScan("example.springdata.jpa.domain")
@Controller
@EnableAutoConfiguration
public class UserController {

	@Autowired
	UserRepository userRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@RequestMapping("/")
	@ResponseBody
	JSONObject home() {

		StoredProcedureQuery query = entityManager.createStoredProcedureQuery("plus1inout")
				.registerStoredProcedureParameter("arg", Integer.class, ParameterMode.IN)
				.registerStoredProcedureParameter("res", String.class, ParameterMode.OUT).setParameter("arg", 1);

		query.execute();

		JSONObject commentCount = new JSONObject(query.getOutputParameterValue("res"));

		return commentCount;
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(UserController.class, args);
	}
}