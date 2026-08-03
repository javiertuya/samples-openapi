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

        [SetUp]
        public void SetUp()
        {
            //restaura el contenido inicial de los pets para que los tests sean independientes
            api.ResetPets();
        }

        [Test]
        public void TestGetAllPets()
        {
            List<Pet> pets = api.ListPets(10);
            ClassicAssert.AreEqual(2, pets.Count);
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
            //el id lo asigna el servidor y se devuelve en el pet creado
            Pet newPet = api.CreatePetQuery("mouse");
            ClassicAssert.AreEqual("3", newPet.Id.ToString());
            ClassicAssert.AreEqual("mouse", newPet.Name);
            Pet pet = api.ShowPetById("3");
            ClassicAssert.AreEqual("3", pet.Id.ToString());
            ClassicAssert.AreEqual("mouse", pet.Name);
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