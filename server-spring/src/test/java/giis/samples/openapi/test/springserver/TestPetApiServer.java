package giis.samples.openapi.test.springserver;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

//con jackson 2 estas clases estaban en com.fasterxml.jackson.core/databind:
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import giis.samples.openapi.invoker.OpenApiGeneratorApplication;
import giis.samples.openapi.model.Pet;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes={OpenApiGeneratorApplication.class}, webEnvironment=SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class TestPetApiServer {
	private final static Logger log=LoggerFactory.getLogger(TestPetApiServer.class);
	//Spring Boot 3 diferencia los endpoints que acaban/no acaban en slash
	//(se puede cambiar al comportamiento de la v2: https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-3.0-Migration-Guide
    private static final String PETS_PATH = "/pets";
    private static final String RESET_PATH = "/reset";

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    public void setUp(TestInfo testInfo) throws Exception {
    	log.info(testInfo.getDisplayName());
    	//restaura el contenido inicial de los pets para que los tests sean independientes
    	mvc.perform(post(RESET_PATH).contentType(MediaType.APPLICATION_JSON))
    	   .andExpect(status().isOk());
    }

    //Los siguientes tests son equivalentes a los de los clientes de la api
    @Test
    public void testGetAllPets() throws Exception {
    	mvc.perform(get(PETS_PATH + "?limit=10").contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isOk())
	      .andExpect(jsonPath("$", hasSize(2)))
	      .andExpect(jsonPath("$[0].id", is(1)))
	      .andExpect(jsonPath("$[0].name", is("cat")))
	      .andExpect(jsonPath("$[1].id", is(2)))
	      .andExpect(jsonPath("$[1].name", is("dog")));
    }
    @Test
    public void testGetExistingPet() throws Exception {
    	mvc.perform(get(PETS_PATH + "/2").contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isOk())
	      .andExpect(jsonPath("$.id", is(2)))
	      .andExpect(jsonPath("$.name", is("dog")));
    }
    @Test
    public void testPostAndGet() throws Exception {
    	//el id lo asigna el servidor y se devuelve en el pet creado
    	mvc.perform(post(PETS_PATH).queryParam("name", "mouse").contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isCreated())
	      .andExpect(jsonPath("$.id", is(3)))
	      .andExpect(jsonPath("$.name", is("mouse")));
    	mvc.perform(get(PETS_PATH + "/3").contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isOk())
	      .andExpect(jsonPath("$.id", is(3)))
	      .andExpect(jsonPath("$.name", is("mouse")));
    }
    @Test
    public void testGetNotExistingPet() throws Exception {
    	//al no usar un cliente de la api no hay excepcion, solo se comprueba el codigo de estado
    	mvc.perform(get(PETS_PATH + "/0").contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isNotFound());
    }

    //Los siguientes tests comprueban el json completo devuelto, incluido el tag que solo tiene uno de los pets.
    //El primero serializa los modelos generados por openapi, el segundo usa el json tal cual lo envia el servidor
	@Test
	public void testGetAllCheckJson() throws Exception {
		//en el servidor no hay un cliente de la api, se deserializa la respuesta a los modelos generados
		String response=mvc.perform(get(PETS_PATH + "?limit=10").contentType(MediaType.APPLICATION_JSON))
			    .andExpect(status().isOk())
			    .andReturn().getResponse().getContentAsString();
		List<Pet> pets=new ObjectMapper().readValue(response, new TypeReference<List<Pet>>() {});
		//los modelos del servidor omiten los valores opcionales que no estan establecidos
		//(a partir de openapi-generator 7.24.0, ver la nota del README)
		String json=new ObjectMapper().writeValueAsString(pets);
		assertEquals("[{id:1,name:cat,tag:black},{id:2,name:dog}]",json.replaceAll("\"", ""));
	}
	@Test
	public void testGetAllCheckRawJson() throws Exception {
		ResultActions res=mvc.perform(get(PETS_PATH + "?limit=10")
				.contentType(MediaType.APPLICATION_JSON))
			    .andExpect(status().isOk());
		//en este caso se comparara el contenido completo del json obtenido, que tampoco
		//incluye los valores opcionales que no estan establecidos (ver la nota del README)
		String json=res.andReturn().getResponse().getContentAsString();
		assertEquals("[{id:1,name:cat,tag:black},{id:2,name:dog}]",json.replaceAll("\"", ""));
	}

}
