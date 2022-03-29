package giis.samples.openapi.it;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import giis.samples.openapi.ApiClient;
import giis.samples.openapi.ApiException;
import giis.samples.openapi.api.PetsApi;
import giis.samples.openapi.model.Pets;

public class TestPetApiClient {

	@Test
	public void TestGetAllPets() throws ApiException {
		PetsApi api=new PetsApi(new ApiClient().setBasePath("http://localhost:8080"));
		//PetsApi api=new PetsApi();
		Pets pets=api.listPets(10);
		assertEquals(2, pets.size());
		assertEquals("1", pets.get(0).getId().toString());
		assertEquals("cat", pets.get(0).getName());
		assertEquals("2", pets.get(1).getId().toString());
		assertEquals("dog", pets.get(1).getName());
		
	}
}
