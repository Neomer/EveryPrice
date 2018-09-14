using Neomer.EveryPrice.SDK.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Neomer.EveryPrice.REST.Web.Models
{
    [Serializable]
    public class UserProfileEditModel
    {
        public string Name;

        public DateTime? BirthDate;

        public void ToUserProfile(ref UserProfile userProfile)
        {
            if (userProfile == null)
            {
                return;
            }
            userProfile.Name = this.Name;
            userProfile.BirthDate = this.BirthDate;
        }

    }
}