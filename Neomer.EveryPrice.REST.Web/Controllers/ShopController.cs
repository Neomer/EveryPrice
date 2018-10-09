using Neomer.EveryPrice.REST.Web.Models;
using Neomer.EveryPrice.SDK.Core;
using Neomer.EveryPrice.SDK.Exceptions.Managers;
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

namespace Neomer.EveryPrice.REST.Web.Controllers
{
    public class ShopController : ApiController
    {
        public List<ShopViewModel> Get(double lat, double lng, double distance)
        {
			return Get(lat, lng, distance, null);
        }


		public List<ShopViewModel> Get(double lat, double lng, double distance, Guid? tagUid)
		{
			var user = SecurityManager.Instance.GetUserByToken(Request.Headers);
			if (user == null)
			{
				return null;
			}
			ITag tag = null;
			if (tagUid != null)
			{
				tag = TagManager.Instance.Get((Guid)tagUid) as ITag;
			}

			var shopList = ShopManager.Instance.GetShopsNear(new Location() { Latitude = lat, Longtitude = lng }, distance, tag);

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
            var entity = ShopManager.Instance.Get(id) as IShop;
            if (entity == null)
            {
                throw new NotFoundException();
            }
            return new ShopViewModel(entity);
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
        public async System.Threading.Tasks.Task<ShopViewModel> PostAsync(Guid id, [FromBody]ShopEditModel editModel)
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
        public async System.Threading.Tasks.Task<ShopViewModel> PutAsync([FromBody]ShopEditModel editModel)
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
