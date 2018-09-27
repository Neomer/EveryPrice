using Neomer.EveryPrice.REST.Web.Models;
using Neomer.EveryPrice.SDK.Core;
using Neomer.EveryPrice.SDK.Managers;
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
        public List<Shop> Get(double lat, double lng, double distance)
        {
            var user = SecurityManager.Instance.GetUserByToken(Request.Headers);
            if (user == null)
            {
                return null;
            }
            return ShopManager.Instance.GetShopsNear(new Location() { Latitude = lat, Longtitude = lng }, distance) as List<Shop>;
        }

        public Shop Get([FromUri] Guid id)
        {
            var user = SecurityManager.Instance.GetUserByToken(Request.Headers);
            if (user == null)
            {
                return null;
            }
            return ShopManager.Instance.Get((Guid)id) as Shop;
        }

        public List<ShopViewModel> Get(Guid tagUid, string tagName)
        {
            var user = SecurityManager.Instance.GetUserByToken(Request.Headers);
            if (user == null)
            {
                return null;
            }
            var tag = TagManager.Instance.Get((Guid)tagUid) as ITag;
            if (tag == null || tag.Shops == null)
            {
                return null;
            }
            return tag.Shops
                .Select(_ => new ShopViewModel(_))
                .ToList<ShopViewModel>();
        }

        /// <summary>
        /// Редактирование информации о мазагазине
        /// </summary>
        /// <param name="id">Идентификатор</param>
        /// <param name="editModel">Модель данных</param>
        /// <returns></returns>
        public Shop Post(Guid id, [FromBody]ShopEditModel editModel)
        {
            var user = SecurityManager.Instance.GetUserByToken(Request.Headers);
            if (user == null)
            {
                return null;
            }
            var shop = ShopManager.Instance.Get(id) as IShop;
            if (shop == null)
            {
                return null;
            }
            editModel.ToShop(ref shop);
            ShopManager.Instance.SaveIsolate(shop);

            return shop as Shop;
        }

        /// <summary>
        /// Добавляем новый магазин
        /// </summary>
        /// <param name="editModel"></param>
        /// <returns></returns>
        public Shop Put([FromBody]ShopEditModel editModel)
        {
            var user = SecurityManager.Instance.GetUserByToken(Request.Headers);
            if (user == null)
            {
                return null;
            }

            IShop shop = new Shop()
            {
                CreationDate = DateTime.UtcNow,
                Creator = user
            };

            editModel.ToShop(ref shop);

            ShopManager.Instance.SaveIsolate(shop);

            return shop as Shop;
        }


    }
}
