package giis.samples.openapi.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import giis.samples.openapi.model.Pet;
import giis.samples.openapi.model.Pets;

@Service
public class PetsApiDelegateImpl implements PetsApiDelegate {
	private final static Logger log=LoggerFactory.getLogger(PetsApiDelegateImpl.class);
	static Pets petsDb=null;
	
	private static Pets getPetsDb() {
		if (petsDb==null) {
			petsDb=new Pets();
			petsDb.add(new Pet((long)1, "cat"));
			petsDb.add(new Pet((long)2, "dog"));
		}
		return petsDb;
	}


	@Override
	public ResponseEntity<Pets> listPets(Integer limit) {
        log.debug("Call api service: listPets");
        Pets pets=getPetsDb(); 
        log.debug("returning {}", pets.toString());
        return ResponseEntity.ok(pets);
	}

	@Override
	public ResponseEntity<Pet> showPetById(String petId) {
		log.debug("Call api service: showPetById {}", petId);
		for (Pet pet : getPetsDb())
        	if (pet.getId().toString().equals(petId))
				return ResponseEntity.ok(pet);
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		//return ResponseEntity.ok(null);
	}

	@Override
	public ResponseEntity<Void> createPetQuery(Integer id, String name) {
		log.debug("Call api service: createPetQuery {} {}", id.toString(), name);
		Pets pets=getPetsDb();
		pets.add(new Pet((long)id, name));
		return ResponseEntity.ok(null);
	}

}
