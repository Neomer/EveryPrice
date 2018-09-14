using Neomer.EveryPrice.REST.Web.Models;
using Neomer.EveryPrice.SDK.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace Neomer.EveryPrice.REST.Web.Controllers
{
    public class ShopController : ApiController
    {
        public Shop Get(double lat, double lng, double distance)
        {
            return null;
        }

        public Shop Get(Guid id)
        {
            return null;
        }

        /// <summary>
        /// Редактирование информации о мазагазине
        /// </summary>
        /// <param name="id">Идентификатор</param>
        /// <param name="editModel">Модель данных</param>
        /// <returns></returns>
        public Shop Post(Guid id, [FromBody]ShopEditModel editModel)
        {
            return null;
        }

        /// <summary>
        /// Добавляем новый магазин
        /// </summary>
        /// <param name="editModel"></param>
        /// <returns></returns>
        public Shop Put([FromBody]ShopEditModel editModel)
        {
            return null;
        }

    }
}
