using Neomer.EveryPrice.REST.Web.Models;
using Neomer.EveryPrice.SDK.Helpers;
using Neomer.EveryPrice.SDK.Managers;
using Neomer.EveryPrice.SDK.Models;
using Neomer.EveryPrice.SDK.Web.Http;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace Neomer.EveryPrice.REST.Web.Controllers
{
    public class UserProfileController : BaseApiController
	{
        public UserProfile Get(Guid id)
        {
            var user = SecurityManager.Instance.GetUserByToken(CurrentSession, Request.Headers);
            if (user == null)
            {
                return null;
            }
            return UserProfileManager.Instance.Get(CurrentSession, id) as UserProfile;
        }

        /// <summary>
        /// Редактирование профиля
        /// </summary>
        /// <param name="id">Идентификатор профиля</param>
        /// <param name="profileEditModel">Модель</param>
        /// <returns></returns>
        public UserProfile Post(Guid id, [FromBody]UserProfileEditModel profileEditModel)
        {
            var user = SecurityManager.Instance.GetUserByToken(CurrentSession, Request.Headers);
            if  (user == null)
            {
                return null;
            }

            var userProfile = UserProfileManager.Instance.Get(CurrentSession, id) as UserProfile;
            if (userProfile == null)
            {
                return null;
            }
            profileEditModel.ToUserProfile(ref userProfile);

            var transactionId = NHibernateHelper.Instance.BeginTransaction();
            UserProfileManager.Instance.Save(CurrentSession, userProfile);
            NHibernateHelper.Instance.CommitTransaction(transactionId);

            return userProfile;
        }
    }
}
