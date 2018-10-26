using Neomer.EveryPrice.SDK.Exceptions.Managers;
using Neomer.EveryPrice.SDK.Helpers;
using Neomer.EveryPrice.SDK.Models;
using Neomer.EveryPrice.SDK.Session;
using NHibernate;
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

        public override void Save(ISession session, IEntity entity)
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

            base.Save(session, entity);
        }

        public void RegisterUser(ISession session, IUser user, string encryptedPassword) {

			if (encryptedPassword == null || encryptedPassword.Length < 3)
			{
				throw new FormatException();
			}

			this.SaveIsolate(session, user);

			user.Profile = new UserProfile()
			{
				Name = null,
				BirthDate = null,
				Owner = user
			};

			UserProfileManager.Instance.SaveIsolate(session, user.Profile);

			user.SecurityProfile = new UserSecurityProfile()
			{
				Password = encryptedPassword,
				Owner = user
			};

			UserSecurityProfileManager.Instance.SaveIsolate(session, user.SecurityProfile);
        }

        public IUser GetUserByUsername(ISession session, string username)
        {
            return session
				.CreateCriteria<IUser>()
                .Add(Expression.Eq("Username", username))
                .UniqueResult<IUser>();
        }


    }
}
