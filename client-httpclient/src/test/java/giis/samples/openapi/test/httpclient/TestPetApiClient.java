package giis.samples.openapi.test.httpclient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import giis.samples.openapi.api.PetsApi;
import giis.samples.openapi.invoker.ApiClient;
import giis.samples.openapi.invoker.ApiException;
import giis.samples.openapi.model.Pet;

public class TestPetApiClient {
	private final static Logger log=LoggerFactory.getLogger(TestPetApiClient.class);
    private final PetsApi api = new PetsApi(new ApiClient().setBasePath("http://localhost:8080"));

    @BeforeEach
    public void setUp(TestInfo testInfo) {
    	log.info(testInfo.getDisplayName());
    	//restaura el contenido inicial de los pets para que los tests sean independientes
    	api.resetPets();
    }
    @Test
    public void testGetAllPets() {
		List<Pet> pets = api.listPets(10);
		assertEquals(2, pets.size());
		assertEquals("1", pets.get(0).getId().toString());
		assertEquals("cat", pets.get(0).getName());
		assertEquals("2", pets.get(1).getId().toString());
		assertEquals("dog", pets.get(1).getName());
    }
    @Test
    public void testGetExistingPet() {
        Pet pet = api.showPetById("2");
		assertEquals("2", pet.getId().toString());
		assertEquals("dog", pet.getName());
    }
    @Test
    public void testPostAndGet() {
		//el id lo asigna el servidor y se devuelve en el pet creado
		Pet newPet = api.createPetQuery("mouse");
		assertEquals("3", newPet.getId().toString());
		assertEquals("mouse", newPet.getName());
		Pet pet = api.showPetById("3");
		assertEquals("3", pet.getId().toString());
		assertEquals("mouse", pet.getName());
    }
    @Test
    public void testGetNotExistingPet() {
    	try {
        	api.showPetById("0");
        	fail("should return excepton code 404 not found");
     	} catch (ApiException e) {
     		assertEquals(404, e.getCode());
     	}
    }

}
