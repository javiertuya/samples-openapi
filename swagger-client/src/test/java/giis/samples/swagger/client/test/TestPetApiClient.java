package giis.samples.swagger.client.test;

import giis.samples.swagger.ApiClient;
import giis.samples.swagger.ApiException;
import giis.samples.swagger.api.PetsApi;
import giis.samples.swagger.model.Pet;
import giis.samples.swagger.model.Pets;
import org.junit.Test;
import org.junit.rules.TestName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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
    public void TestGetAllPets() throws ApiException {
		Pets pets = api.listPets(10);
		assertTrue(2<=pets.size()); //puede haber mas de dos si se ejecuta antes el metodo que crea pets
		assertEquals("1", pets.get(0).getId().toString());
		assertEquals("cat", pets.get(0).getName());
		assertEquals("2", pets.get(1).getId().toString());
		assertEquals("dog", pets.get(1).getName());
    }
    @Test
    public void TestGetExistingPet() throws Exception {
        Pet pet = api.showPetById("2");
		assertEquals("2", pet.getId().toString());
		assertEquals("dog", pet.getName());
    }
    @Test
    public void TestGetNotExistingPet() throws Exception {
        Pet pet = api.showPetById("0");
		assertNull(pet);
    }
    @Test
    public void TestPostAndGet() throws Exception {
    	//para que sea repetible el id y name se calculan como el siguiente valor de la secuencia de ids existentes 
    	//(pues no se hace un reset de los pets en setup)
    	int newId=api.listPets(10).size()+1;
    	String newName="mouse"+newId;
        api.createPetQuery(newId, newName);
		Pet pet = api.showPetById(String.valueOf(newId));
		assertEquals(String.valueOf(newId), pet.getId().toString());
		assertEquals(newName, pet.getName());
    }

}
