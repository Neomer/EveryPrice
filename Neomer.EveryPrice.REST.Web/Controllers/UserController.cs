using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace Neomer.EveryPrice.REST.Web.Controllers
{
    public class UserController : ApiController
    {

        public void Post([FromBody]string username, [FromBody]string password)
        {
            
        }

        public void Put(int id, [FromBody]string value)
        {
        }

        public void Delete(int id)
        {
        }
    }
}
