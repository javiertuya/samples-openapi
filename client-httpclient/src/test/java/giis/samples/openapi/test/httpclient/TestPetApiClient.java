package giis.samples.openapi.test.httpclient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//con jackson 2 esta clase estaba en com.fasterxml.jackson.databind:
//import com.fasterxml.jackson.databind.ObjectMapper;
import tools.jackson.databind.ObjectMapper;

import giis.samples.openapi.api.PetsApi;
import giis.samples.openapi.invoker.ApiClient;
import giis.samples.openapi.invoker.ApiException;
import giis.samples.openapi.model.Pet;

public class TestPetApiClient {
	private final static Logger log=LoggerFactory.getLogger(TestPetApiClient.class);
	private final static String BASE_URL="http://localhost:8080";
    private final PetsApi api = new PetsApi(new ApiClient().setBasePath(BASE_URL));

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

    //Los siguientes tests comprueban el json completo devuelto, incluido el tag que solo tiene uno de los pets.
    //El primero serializa los modelos generados por openapi, el segundo usa el json tal cual lo envia el servidor
    @Test
    public void testGetAllCheckJson() throws Exception {
		List<Pet> pets = api.listPets(10);
		//jackson incluye los valores opcionales que no estan establecidos (a diferencia de los clientes .NET,
		//que los omiten salvo que se genere la api con la opcion optionalEmitDefaultValues)
		String json=new ObjectMapper().writeValueAsString(pets);
		assertEquals("[{id:1,name:cat,tag:black},{id:2,name:dog,tag:null}]",json.replaceAll("\"", ""));
    }
    @Test
    public void testGetAllCheckRawJson() throws Exception {
		String json=getJson("/pets?limit=10");
		//los modelos del servidor si que omiten los valores opcionales que no estan establecidos
		assertEquals("[{id:1,name:cat,tag:black},{id:2,name:dog}]",json.replaceAll("\"", ""));
    }
    //el cliente de la api devuelve objetos, para obtener el json se hace la peticion directamente
    private String getJson(String path) throws Exception {
    	HttpRequest request=HttpRequest.newBuilder().uri(URI.create(BASE_URL + path)).GET().build();
    	return HttpClient.newHttpClient().send(request, BodyHandlers.ofString()).body();
    }

}
