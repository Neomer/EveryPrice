using Neomer.EveryPrice.REST.Web.Models;
using Neomer.EveryPrice.SDK.Exceptions.Managers;
using Neomer.EveryPrice.SDK.Exceptions.Security;
using Neomer.EveryPrice.SDK.Helpers;
using Neomer.EveryPrice.SDK.Managers;
using Neomer.EveryPrice.SDK.Models;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using System.Web.Mvc;

namespace Neomer.EveryPrice.REST.Web.Controllers
{
    public class UserController : ApiController
    {
        /// <summary>
        /// Не использовать!
        /// </summary>
        [Obsolete]
        public void Get()
        {
        }

        /// <summary>
        /// Возвращает пользователя по идентификатору
        /// </summary>
        /// <param name="id">Идентификатор пользователя</param>
        /// <returns></returns>
        public UserViewModel Get(Guid id)
        {
            var entity = UserManager.Instance.Get(id) as IUser;
            if (entity == null)
            {
                throw new NotFoundException();
            }
            return new UserViewModel(entity);
        }

        /// <summary>
        /// Возвращает пользователя по данным для авторизации
        /// </summary>
        /// <param name="authModel"></param>
        /// <returns></returns>
        public async System.Threading.Tasks.Task<UserWithTokenViewModel> PostAsync([FromBody]UserAuthModel authModel)
        {
            using (var contentStream = await Request.Content.ReadAsStreamAsync())
            {
                contentStream.Seek(0, SeekOrigin.Begin);
                using (var sr = new StreamReader(contentStream))
                {
                    string rawContent = sr.ReadToEnd();
                    Logger.Log.Debug(rawContent);
                    // use raw content here
                }
            }

            var user = UserManager.Instance.GetUserByUsername(authModel.Username) as IUser;
            if (user == null)
            {
                throw new SignInFailedException();
            }
            return new UserWithTokenViewModel(SecurityManager.Instance.SignIn(user.Uid));
        }

        /// <summary>
        /// Регистрация нового пользователя
        /// </summary>
        /// <param name="authModel"></param>
        public async System.Threading.Tasks.Task<UserWithTokenViewModel> PutAsync([FromBody]UserAuthModel authModel)
        {
            using (var contentStream = await Request.Content.ReadAsStreamAsync())
            {
                contentStream.Seek(0, SeekOrigin.Begin);
                using (var sr = new StreamReader(contentStream))
                {
                    string rawContent = sr.ReadToEnd();
                    Logger.Log.Debug(rawContent);
                    // use raw content here
                }
            }
            IUser user = new User();
            authModel.ToUser(ref user);
            UserManager.Instance.SaveIsolate(user);

            return new UserWithTokenViewModel(SecurityManager.Instance.SignIn(user.Uid));
        }

        /// <summary>
        /// Удаление пользователя
        /// </summary>
        /// <param name="id"></param>
        public void Delete(int id)
        {
        }
    }
}
