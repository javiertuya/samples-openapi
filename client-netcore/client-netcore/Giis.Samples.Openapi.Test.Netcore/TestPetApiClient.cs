using Giis.Samples.Openapi.Api;
using Giis.Samples.Openapi.Client;
using Giis.Samples.Openapi.Model;
using NUnit.Framework;
using NUnit.Framework.Legacy;
using System.Collections.Generic;

namespace Giis.Samples.Openapi.Test.Netcore
{
    public class Tests
    {
        private readonly PetsApi api = new PetsApi("http://localhost:8080");
 
        [Test]
        public void TestGetAllPets()
        {
            List<Pet> pets = api.ListPets(10);
            ClassicAssert.True(2 <= pets.Count); //puede haber mas de dos si se ejecuta antes el metodo que crea pets
            ClassicAssert.AreEqual("1", pets[0].Id.ToString());
            ClassicAssert.AreEqual("cat", pets[0].Name);
            ClassicAssert.AreEqual("2", pets[1].Id.ToString());
            ClassicAssert.AreEqual("dog", pets[1].Name);
        }
        [Test]
        public void TestGetExistingPet()
        {
            Pet pet = api.ShowPetById("2");
            ClassicAssert.AreEqual("2", pet.Id.ToString());
            ClassicAssert.AreEqual("dog", pet.Name);
        }
        [Test]
        public void TestPostAndGet()
        {
            //para que sea repetible el id y name se calculan como el siguiente valor de la secuencia de ids existentes 
            //(pues no se hace un reset de los pets en setup)
            int newId = api.ListPets(10).Count + 1;
            string newName = "mouse" + newId;
            api.CreatePetQuery(newId, newName);
            Pet pet = api.ShowPetById(newId.ToString());
            ClassicAssert.AreEqual(newId.ToString(), pet.Id.ToString());
            ClassicAssert.AreEqual(newName, pet.Name);
        }
        [Test]
        public void TestGetNotExistingPet()
        {
            //If spring server returns null, spring client receives null, but apache http client ant netcore does not
            try
            {
                api.ShowPetById("0");
                ClassicAssert.Fail("should return excepton code 404 not found");
            }
            catch (ApiException e)
            {
                ClassicAssert.AreEqual(404, e.ErrorCode);
            }
        }

    }
}