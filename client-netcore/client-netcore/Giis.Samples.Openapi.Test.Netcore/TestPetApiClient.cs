using Giis.Samples.Openapi.Api;
using Giis.Samples.Openapi.Client;
using Giis.Samples.Openapi.Model;
using NUnit.Framework;

namespace Giis.Samples.Openapi.Test.Netcore
{
    public class Tests
    {
        private readonly PetsApi api = new PetsApi("http://localhost:8080");
 
        [Test]
        public void TestGetAllPets()
        {
            Pets pets = api.ListPets(10);
            Assert.True(2 <= pets.Count); //puede haber mas de dos si se ejecuta antes el metodo que crea pets
            Assert.AreEqual("1", pets[0].Id.ToString());
            Assert.AreEqual("cat", pets[0].Name);
            Assert.AreEqual("2", pets[1].Id.ToString());
            Assert.AreEqual("dog", pets[1].Name);
        }
        [Test]
        public void TestGetExistingPet()
        {
            Pet pet = api.ShowPetById("2");
            Assert.AreEqual("2", pet.Id.ToString());
            Assert.AreEqual("dog", pet.Name);
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
            Assert.AreEqual(newId.ToString(), pet.Id.ToString());
            Assert.AreEqual(newName, pet.Name);
        }
        [Test]
        public void TestGetNotExistingPet()
        {
            //If spring server returns null, spring client receives null, but apache http client ant netcore does not
            try
            {
                api.ShowPetById("0");
                Assert.Fail("should return excepton code 404 not found");
            }
            catch (ApiException e)
            {
                Assert.AreEqual(404, e.ErrorCode);
            }
        }

    }
}