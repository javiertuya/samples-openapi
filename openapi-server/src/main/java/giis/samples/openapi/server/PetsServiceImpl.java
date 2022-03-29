package giis.samples.openapi.server;

import giis.samples.openapi.api.*;
import giis.samples.openapi.model.*;

import giis.samples.openapi.model.Pets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

public class PetsServiceImpl extends PetsApiService {
	final static Logger log=LoggerFactory.getLogger(PetsApiService.class);
	static Pets petsDb=null;
	
	private static Pets getPetsDb() {
		if (petsDb==null) {
			petsDb=new Pets();
			petsDb.add(new Pet().id((long)1).name("cat"));
			petsDb.add(new Pet().id((long)2).name("dog"));
		}
		return petsDb;
	}
	
    @Override
    public Response listPets(Integer limit, SecurityContext securityContext) throws NotFoundException {
        log.debug("Call api service: listPets");
        Pets pets=getPetsDb(); 
        log.debug("returning {}", pets.toString());
        //return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "custom implementation!")).build();
        return Response.ok().entity(pets).build();
    }
    @Override
    public Response showPetById(String petId, SecurityContext securityContext) throws NotFoundException {
        log.debug("Call api service: showPetById "+petId);
        for (Pet pet : getPetsDb())
        	if (pet.getId().toString()==petId)
        		return Response.ok().entity(pet).build();
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Pet not found, Id="+petId)).build();
    }

	@Override
	public Response createPetQuery(Integer id, String name, SecurityContext securityContext) throws NotFoundException {
        log.debug("Call api service: createPetQuery");
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "custom implementation!")).build();
	}
}
