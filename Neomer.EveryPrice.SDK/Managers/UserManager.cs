using Neomer.EveryPrice.SDK.Exceptions.Managers;
using Neomer.EveryPrice.SDK.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Neomer.EveryPrice.SDK.Managers
{
    public class UserManager : BaseEntityManager<IUser>
    {
        public override void Save(IEntity entity)
        {
            var user = entity as IUser;

            if (user == null)
            {
                throw new UnsupportedEntityException();
            }

            if (user.Username.Length < 3)
            {
                throw new FormatException("Имя не может быть короче 3 символов");
            }

            base.Save(entity);
        }
    }
}
