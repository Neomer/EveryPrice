using Neomer.EveryPrice.SDK.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Neomer.EveryPrice.REST.Web.Models
{
    public class UserWithTokenViewModel : UserViewModel
    {
        public UserWithTokenViewModel()
        {
            
        }

        public UserWithTokenViewModel(IUser user) : base(user)
        {
            Token = user.Token;
            TokenExpirationDate = user.TokenExpirationDate;
        }

        public DateTime? TokenExpirationDate;

        public Guid? Token;

    }
}