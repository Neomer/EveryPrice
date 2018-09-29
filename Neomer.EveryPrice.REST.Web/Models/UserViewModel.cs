using Neomer.EveryPrice.SDK.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Neomer.EveryPrice.REST.Web.Models
{
    public class UserViewModel
    {
        public UserViewModel()
        {

        }

        public UserViewModel(IUser user)
        {
            Username = user.Username;
            Uid = user.Uid;
        }

        public string Username;

        public Guid Uid;

    }
}