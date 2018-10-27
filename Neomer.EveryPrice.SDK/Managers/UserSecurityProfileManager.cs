using Neomer.EveryPrice.SDK.Exceptions.Security;
using Neomer.EveryPrice.SDK.Models;
using NHibernate;
using NHibernate.Criterion;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Neomer.EveryPrice.SDK.Managers
{
    public class UserSecurityProfileManager : BaseEntityManager<UserSecurityProfileManager, UserSecurityProfile>
    {
        protected UserSecurityProfileManager()
        {

        }

		public bool CheckUserPassword(ISession session, IUser user, string encryptedPassword)
		{
			var securityProfile = session
				.CreateCriteria<IUserSecurityProfile>()
				.Add(Expression.Eq("Owner", user))
				.UniqueResult<IUserSecurityProfile>();
			if (securityProfile == null)
			{
				throw new ObjectNotFoundException(user, typeof(IUserSecurityProfile));
			}
			return securityProfile.Password == encryptedPassword;
		}
    }
}
