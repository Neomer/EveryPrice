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
    public class UserProfileController : ApiController
    {
        public UserProfile Get(Guid id)
        {
            return UserProfileManager.Instance.Get(id) as UserProfile;
        }
    }
}
