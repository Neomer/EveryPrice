﻿using Neomer.EveryPrice.REST.Web.Models;
using Neomer.EveryPrice.SDK.Helpers;
using Neomer.EveryPrice.SDK.Managers;
using Neomer.EveryPrice.SDK.Models;
using System;
using System.Collections.Generic;
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
        public User Get(Guid id)
        {
            return UserManager.Instance.Get(id) as User;
        }

        /// <summary>
        /// Возвращает пользователя по данным для авторизации
        /// </summary>
        /// <param name="authModel"></param>
        /// <returns></returns>
        public User Post([FromBody]UserAuthModel authModel)
        {
            return UserManager.Instance.GetUserByUsername(authModel.Username) as User;
        }

        /// <summary>
        /// Регистрация нового пользователя
        /// </summary>
        /// <param name="authModel"></param>
        public void Put([FromBody]UserAuthModel authModel)
        {
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
