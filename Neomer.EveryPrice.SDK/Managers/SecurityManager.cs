using Neomer.EveryPrice.SDK.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Neomer.EveryPrice.SDK.Managers
{
    public class SecurityManager : BaseEntityManager<SecurityManager, IUser>
    {

        protected SecurityManager()
        {

        }

        public IUser SignIn(Guid userId)
        {
            var user = Get(userId) as IUser;

            if (user == null)
            {
                return null;
            }

            user.Token = Guid.NewGuid();
            user.TokenExpirationDate = DateTime.Now.AddHours(2);

            Save(user);
        }

    }
}
