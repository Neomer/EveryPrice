using Neomer.EveryPrice.SDK.Exceptions.Managers;
using Neomer.EveryPrice.SDK.Helpers;
using Neomer.EveryPrice.SDK.Models;
using NHibernate.Criterion;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Neomer.EveryPrice.SDK.Managers
{
    public class UserManager : BaseEntityManager<UserManager, IUser>
    {
        protected UserManager()
        {

        }

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

        public void RegisterUser(IUser user) {

            this.Save(user);

            var userProfile = new UserProfile();
            userProfile.Owner = user;
            userProfile.Name = null;
            userProfile.BirthDate = null;

            UserProfileManager.Instance.Save(userProfile);

            var userSecurityProfile = new UserSecurityProfile();
            userSecurityProfile.Owner = user;

            UserSecurityProfileManager.Instance.Save(userSecurityProfile);
        }

        public IUser GetUserByUsername(string username)
        {
            return NHibernateHelper.Instance.CurrentSession.CreateCriteria<IUser>()
                .Add(Expression.Eq("Username", username))
                .UniqueResult<IUser>();
        }

    }
}
