using Neomer.EveryPrice.REST.Web.Models;
using Neomer.EveryPrice.SDK.Helpers;
using Neomer.EveryPrice.SDK.Managers;
using Neomer.EveryPrice.SDK.Models;
using Neomer.EveryPrice.SDK.Web.Http;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace Neomer.EveryPrice.REST.Web.Controllers
{
    public class ProductController : BaseApiController
	{
        public List<ProductViewModel> Get(Guid shopUid)
        {
            var user = SecurityManager.Instance.GetUserByToken(CurrentSession, Request.Headers);
            if (user == null)
            {
                return null;
            }
            var shop = ShopManager.Instance.Get(CurrentSession, shopUid) as IShop;
            if (shop == null)
            {
                return null;
            }

            var products = ProductManager.Instance.GetProductsByShop(CurrentSession, shop);
            if (products == null)
            {
                return null;
            }

            return products
                .Select(_ => new ProductViewModel(_))
                .ToList<ProductViewModel>();
        }

        public async System.Threading.Tasks.Task<ProductViewModel> PostAsync(Guid uid, [FromBody] ProductEditModel productEditModel)
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
            var user = SecurityManager.Instance.GetUserByToken(CurrentSession, Request.Headers);
            if (user == null)
            {
                return null;
            }
            var product = ProductManager.Instance.Get(CurrentSession, uid) as IProduct;
            if (product == null)
            {
                return null;
            }
            productEditModel.ToProduct(ref product);
            ProductManager.Instance.SaveIsolate(CurrentSession, product);

            return new ProductViewModel(product);
        }

        public async System.Threading.Tasks.Task<ProductViewModel> PutAsync(Guid shopUid, [FromBody] ProductEditModel productEditModel)
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
            var user = SecurityManager.Instance.GetUserByToken(CurrentSession, Request.Headers);
            if (user == null)
            {
                return null;
            }
            var shop = ShopManager.Instance.Get(CurrentSession, shopUid) as IShop;
            if (shop == null)
            {
                return null;
            }
            IProduct product = new Product()
            {
                CreationDate = DateTime.UtcNow,
                Creator = user,
                Group = null,
                Shop = shop
            };

            productEditModel.ToProduct(ref product);
            ProductManager.Instance.SaveIsolate(CurrentSession, product);

            return new ProductViewModel(product);
        }
    }
}
