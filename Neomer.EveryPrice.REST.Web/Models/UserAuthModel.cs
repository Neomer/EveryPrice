using Neomer.EveryPrice.SDK.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Neomer.EveryPrice.REST.Web.Models
{
    public class UserAuthModel
    {
        public string Username;

        public void ToUser(ref IUser user)
        {
            user.Username = Username;
        }
    }
}