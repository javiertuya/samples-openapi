package giis.samples.openapi.test.httpclient;

import org.junit.Test;
import org.junit.rules.TestName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import giis.samples.openapi.api.PetsApi;
import giis.samples.openapi.invoker.ApiClient;
import giis.samples.openapi.invoker.ApiException;
import giis.samples.openapi.model.Pet;
import giis.samples.openapi.model.Pets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Rule;

public class TestPetApiClient {
	private final static Logger log=LoggerFactory.getLogger(TestPetApiClient.class);
    private final PetsApi api = new PetsApi(new ApiClient().setBasePath("http://localhost:8080"));
    @Rule public TestName name = new TestName();

    @Before
    public void setUp() {
    	log.info(name.getMethodName());
    }
    @Test
    public void TestGetAllPets() {
		Pets pets = api.listPets(10);
		assertTrue(2<=pets.size()); //puede haber mas de dos si se ejecuta antes el metodo que crea pets
		assertEquals("1", pets.get(0).getId().toString());
		assertEquals("cat", pets.get(0).getName());
		assertEquals("2", pets.get(1).getId().toString());
		assertEquals("dog", pets.get(1).getName());
    }
    @Test
    public void TestGetExistingPet() {
        Pet pet = api.showPetById("2");
		assertEquals("2", pet.getId().toString());
		assertEquals("dog", pet.getName());
    }
    @Test
    public void TestPostAndGet() {
    	//para que sea repetible el id y name se calculan como el siguiente valor de la secuencia de ids existentes 
    	//(pues no se hace un reset de los pets en setup)
    	int newId=api.listPets(10).size()+1;
    	String newName="mouse"+newId;
        api.createPetQuery(newId, newName);
		Pet pet = api.showPetById(String.valueOf(newId));
		assertEquals(String.valueOf(newId), pet.getId().toString());
		assertEquals(newName, pet.getName());
    }
    @Test
    public void TestGetNotExistingPet() {
    	try {
        	api.showPetById("0");
        	fail("should return excepton code 404 not found");
     	} catch (ApiException e) {
     		assertEquals(404, e.getCode());
     	}
    }

}
