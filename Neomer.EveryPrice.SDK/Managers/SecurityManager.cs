using Neomer.EveryPrice.SDK.Exceptions.Security;
using Neomer.EveryPrice.SDK.Helpers;
using Neomer.EveryPrice.SDK.Models;
using NHibernate.Criterion;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http.Headers;
using System.Text;
using System.Threading.Tasks;

namespace Neomer.EveryPrice.SDK.Managers
{
    public class SecurityManager : BaseEntityManager<SecurityManager, IUser>
    {

        protected SecurityManager()
        {

        }

        /// <summary>
        /// Авторизовывает пользователя в системе и присваивает ему новый токен безопасности
        /// </summary>
        /// <param name="userId"></param>
        /// <returns></returns>
        public IUser SignIn(Guid userId)
        {
            var user = Get(userId) as IUser;

            if (user == null)
            {
                return null;
            }

            user.Token = Guid.NewGuid();
            user.TokenExpirationDate = DateTime.UtcNow.AddHours(2);

            SaveIsolate(user);

            return user;
        }

        /// <summary>
        /// Возвращает пользователя по переданному токену безопасности
        /// </summary>
        /// <param name="token"></param>
        /// <returns></returns>
        public IUser GetUserByToken(Guid token)
        {
            return NHibernateHelper.Instance.CurrentSession.CreateCriteria<IUser>()
                .Add(Expression.Eq("Token", token))
                .Add(Expression.Le("TokenExpirationDate", DateTime.Now))
                .UniqueResult<IUser>();
        }

        /// <summary>
        /// Возвращает пользователя по переданному токену безопасности
        /// </summary>
        /// <param name="token"></param>
        /// <returns></returns>
        public IUser GetUserByToken(HttpRequestHeaders headers)
        {
            if (!headers.Contains("Token"))
            {
                throw new TokenNotFoundException();
            }
            var tokenString = headers.GetValues("Token").FirstOrDefault();
            if (tokenString == null)
            {
                throw new TokenNotFoundException();
            }
            Guid token;
            if (!Guid.TryParse(tokenString, out token))
            {
                throw new InvalidTokenException();
            }

            var user = NHibernateHelper.Instance.CurrentSession.CreateCriteria<IUser>()
                .Add(Expression.Eq("Token", token))
                .Add(Expression.Ge("TokenExpirationDate", DateTime.UtcNow))
                .UniqueResult<IUser>();

            if (user == null)
            {
                throw new InvalidTokenException();
            }
            return user;
        }

    }
}
