using Giis.Samples.Openapi.Api;
using Giis.Samples.Openapi.Client;
using Giis.Samples.Openapi.Model;
using Newtonsoft.Json;
using NUnit.Framework;
using NUnit.Framework.Legacy;
using System.Collections.Generic;
using System.Net.Http;

namespace Giis.Samples.Openapi.Test.Netcorestandard
{
    public class Tests
    {
        private const string BaseUrl = "http://localhost:8080";
        private readonly PetsApi api = new PetsApi(BaseUrl);

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

        //Los siguientes tests comprueban el json completo devuelto, incluido el tag que solo tiene uno de los pets.
        //El primero serializa los modelos generados por openapi, el segundo usa el json tal cual lo envia el servidor
        [Test]
        public void TestGetAllCheckJson()
        {
            List<Pet> pets = api.ListPets(10);
            //los modelos .NET omiten los valores opcionales que no estan establecidos
            //(a diferencia de los de los clientes java, ver la nota del README)
            string json = JsonConvert.SerializeObject(pets);
            ClassicAssert.AreEqual("[{id:1,name:cat,tag:black},{id:2,name:dog}]", json.Replace("\"", ""));
        }
        [Test]
        public void TestGetAllCheckRawJson()
        {
            string json = GetJson("/pets?limit=10");
            //los modelos del servidor tambien omiten los valores opcionales que no estan
            //establecidos (a partir de openapi-generator 7.24.0, ver la nota del README)
            ClassicAssert.AreEqual("[{id:1,name:cat,tag:black},{id:2,name:dog}]", json.Replace("\"", ""));
        }
        //el cliente de la api devuelve objetos, para obtener el json se hace la peticion directamente
        private string GetJson(string path)
        {
            using (HttpClient client = new HttpClient())
                return client.GetStringAsync(BaseUrl + path).Result;
        }

    }
}