using Neomer.EveryPrice.REST.Web.Models;
using Neomer.EveryPrice.SDK.Helpers;
using Neomer.EveryPrice.SDK.Managers;
using Neomer.EveryPrice.SDK.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using System.Web.Mvc;

namespace Neomer.EveryPrice.REST.Web.Controllers
{
    public class UserController : ApiController
    {
        public void Get()
        {
        }

        public User Get(Guid id)
        {
            return UserManager.Instance.Get(id) as User;
        }

        public User Post([FromBody]UserAuthModel authModel)
        {
            return UserManager.Instance.GetUserByUsername(authModel.Username) as User;
        }

        public void Put(int id, [FromBody]string value)
        {
        }

        public void Delete(int id)
        {
        }
    }
}
