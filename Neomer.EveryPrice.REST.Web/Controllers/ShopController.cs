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
        public List<ShopViewModel> Get(double lat, double lng, double distance)
        {
            var user = SecurityManager.Instance.GetUserByToken(Request.Headers);
            if (user == null)
            {
                return null;
            }
            var shopList = ShopManager.Instance.GetShopsNear(new Location() { Latitude = lat, Longtitude = lng }, distance);

            return shopList == null ? null :
                shopList
                    .Select(_ => new ShopViewModel(_))
                    .ToList<ShopViewModel>();
        }

        public ShopViewModel Get([FromUri] Guid id)
        {
            var user = SecurityManager.Instance.GetUserByToken(Request.Headers);
            if (user == null)
            {
                return null;
            }
            return new ShopViewModel(ShopManager.Instance.Get(id) as IShop);
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
        public ShopViewModel Post(Guid id, [FromBody]ShopEditModel editModel)
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

            return new ShopViewModel(shop);
        }

        /// <summary>
        /// Добавляем новый магазин
        /// </summary>
        /// <param name="editModel"></param>
        /// <returns></returns>
        public ShopViewModel Put([FromBody]ShopEditModel editModel)
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

            return new ShopViewModel(shop);
        }


    }
}
