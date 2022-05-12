package giis.samples.openapi.test.springserver;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import giis.samples.openapi.invoker.OpenAPI2SpringBoot;


@RunWith(SpringRunner.class)
@SpringBootTest(classes={OpenAPI2SpringBoot.class}, webEnvironment=SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class TestPetApiServer {
	private final static Logger log=LoggerFactory.getLogger(TestPetApiServer.class);
    private static final String PETS_PATH = "/pets/";

    @Autowired
    private MockMvc mvc;

    @Rule public TestName name = new TestName();

    @Before
    public void setUp() {
    	log.info(name.getMethodName());
    }
    
    @Test
    public void testGetAllCheckJsonPath() throws Exception {
    	mvc.perform(get(PETS_PATH).contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isOk())
	      .andExpect(jsonPath("$", hasSize(2)))
	      .andExpect(jsonPath("$[0].id", is(1)))
	      .andExpect(jsonPath("$[0].name", is("cat")))
	      .andExpect(jsonPath("$[1].id", is(2)))
	      .andExpect(jsonPath("$[1].name", is("dog"))); 
    	}
	@Test
	public void testGetAllCheckJson() throws Exception {
		ResultActions res=mvc.perform(get(PETS_PATH)
				.contentType(MediaType.APPLICATION_JSON))
			    .andExpect(status().isOk());
		//en este caso se comparara el contenido completo del json obtenido
		String json=res.andReturn().getResponse().getContentAsString();
		assertEquals("[{id:1,name:cat,tag:null},{id:2,name:dog,tag:null}]",json.replaceAll("\"", ""));
	}
	@Test
	public void testGetSingle() throws Exception {
		ResultActions res=mvc.perform(get(PETS_PATH + "2")
				.contentType(MediaType.APPLICATION_JSON))
			    .andExpect(status().isOk());
		String json=res.andReturn().getResponse().getContentAsString();
		assertEquals("{id:2,name:dog,tag:null}",json.replaceAll("\"", ""));
	}

}
