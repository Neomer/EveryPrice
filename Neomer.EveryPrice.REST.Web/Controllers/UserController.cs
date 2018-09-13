using Neomer.EveryPrice.REST.Web.Models;
using Neomer.EveryPrice.SDK.Managers;
using Neomer.EveryPrice.SDK.Models;
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
        public IUser Get()
        {
            IUser user = new User();
            user.Uid = Guid.NewGuid();
            user.Username = "Test username";

            return user;
        }

        public void Post([FromBody]UserAuthModel username)
        {
            IUser user = new User();
            user.Uid = Guid.NewGuid();
            username.ToUser(ref user);

            var userManager = new UserManager();
            userManager.Save(user);
        }

        public void Put(int id, [FromBody]string value)
        {
        }

        public void Delete(int id)
        {
        }
    }
}
